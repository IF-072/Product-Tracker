package com.softserve.if072.mvcapp.service;

import com.softserve.if072.common.model.Storage;
import com.softserve.if072.common.model.dto.StorageDTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.apache.commons.collections.CollectionUtils;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * The StoragePageService class is used to hold business logic
 * and to retrieve appropriate resources from a REST server.
 *
 * @author Roman Dyndyn
 */
@Service
public class StoragePageService {

    private static final Logger LOGGER = LogManager.getLogger(StoragePageService.class);

    @Value("${application.restStorageURL}")
    private String storageUrl;

    @Value("${application.restShoppingListURL}")
    private String shoppingListURL;

    private final RestTemplate restTemplate;
    private final ShoppingListService shoppingListService;
    private final AnalyticsService analyticsService;
    private final MessageService messageService;

    @Autowired
    public StoragePageService(final RestTemplate restTemplate, final ShoppingListService shoppingListService,
                              final AnalyticsService analyticsService, final MessageService messageService) {
        this.restTemplate = restTemplate;
        this.shoppingListService = shoppingListService;
        this.analyticsService = analyticsService;
        this.messageService = messageService;
    }

    /**
     * Make request to a REST server for retrieving all all storage records
     * for current user.
     *
     * @param userId - current user unique identifier
     * @return list of storage records
     */
    public List<Storage> getStorages(final int userId) {
        final String uri = storageUrl + userId;
        final List<Storage> storage = restTemplate.getForObject(uri, List.class);

        if (CollectionUtils.isEmpty(storage)) {
            LOGGER.error("Storage' list of user with id {} is empty", userId);
        } else {
            LOGGER.info("Storage' list of user with id {} has been received.", userId);
        }
        return storage;
    }

    /**
     * Make request to a REST server for updating storage record.
     *
     * @param storageDTO - storage record
     * @param locale     - locale of user
     */
    public String updateAmount(final StorageDTO storageDTO, final String locale) {
        final String uri = storageUrl + "dto";
        Timestamp result = restTemplate.postForObject(uri, storageDTO, Timestamp.class);
        analyticsService.cleanProductStatisticsSessionObject();
        messageService.broadcast("storage.update", locale, storageDTO.getUserId(), storageDTO.getProductName(),
                storageDTO.getAmount());
        if (storageDTO.getAmount() <= 1) {
            messageService.broadcast("storage.insertInList", locale, storageDTO.getUserId(),
                    storageDTO.getProductName());
        }
        String endDate;
        if (result != null) {
            endDate = new SimpleDateFormat("yyyy/MM/dd").format(result);
        } else {
            endDate = "----------";
        }

        LOGGER.info("Return new end date {}", endDate);
        return endDate;

    }

    /**
     * Inserts product in shopping list.
     *
     * @param productId - product unique identifier
     */
    public void addProductToShoppingList(final int productId) {
        if (productId > 0) {
            shoppingListService.addProductToShoppingList(productId);
        }
    }
}

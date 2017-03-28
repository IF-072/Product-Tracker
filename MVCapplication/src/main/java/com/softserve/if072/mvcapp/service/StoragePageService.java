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
    private final AnalyticsPageService analyticsPageService;

    @Autowired
    public StoragePageService(final RestTemplate restTemplate, final ShoppingListService shoppingListService,
                              final AnalyticsPageService analyticsPageService) {
        this.restTemplate = restTemplate;
        this.shoppingListService = shoppingListService;
        this.analyticsPageService = analyticsPageService;
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
     */
    public void updateAmount(final StorageDTO storageDTO) {
        final String uri = storageUrl + "dto";
        restTemplate.put(uri, storageDTO);
        analyticsPageService.cleanProductStatisticsSessionObject();
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

package com.softserve.if072.mvcapp.service;

import com.softserve.if072.common.model.Storage;
import com.softserve.if072.common.model.User;
import com.softserve.if072.common.model.dto.StorageDTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.client.RestTemplate;
import org.apache.commons.collections.CollectionUtils;

import java.util.List;

/**
 * The StoragePageService class is used to hold business logic and to retrieve appropriate resources from a
 * REST server
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

    private RestTemplate restTemplate;
    private ShoppingListService shoppingListService;

    @Autowired
    public StoragePageService(RestTemplate restTemplate, ShoppingListService shoppingListService) {
        this.restTemplate = restTemplate;
        this.shoppingListService = shoppingListService;
    }

    public List<Storage> getStorages(int userId) {
        final String uri = storageUrl + userId;
        List<Storage> storage = restTemplate.getForObject(uri, List.class);

        if (CollectionUtils.isEmpty(storage)) {
            LOGGER.error("Storage' list of user with id {} is empty", userId);
        } else {
            LOGGER.info("Storage' list of user with id {} has been received.", userId);
        }
        return storage;
    }

    public void updateAmount(StorageDTO storageDTO) {
        final String uri = storageUrl + "dto";
        restTemplate.put(uri, storageDTO);
    }

    public void addProductToShoppingList(User user, int productId) {
        if (productId > 0){
            shoppingListService.addProductToShoppingList(user, productId);
        }
    }
}

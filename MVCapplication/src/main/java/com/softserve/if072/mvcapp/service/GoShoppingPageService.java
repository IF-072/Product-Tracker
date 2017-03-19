package com.softserve.if072.mvcapp.service;

import com.softserve.if072.common.model.Cart;
import com.softserve.if072.common.model.ShoppingList;
import com.softserve.if072.common.model.Store;
import com.softserve.if072.mvcapp.dto.FormForCart;
import org.apache.commons.collections.CollectionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

/**
 * The GoShoppingPageService class is used to hold business
 * logic and to retrieve appropriate resources from a REST server.
 *
 * @author Roman Dyndyn
 */
@Service
public class GoShoppingPageService {
    private static final Logger LOGGER = LogManager.getLogger(GoShoppingPageService.class);
    private final RestTemplate restTemplate;

    @Value("${application.restGoShoppingURL}")
    private String goShoppingURL;

    @Autowired
    public GoShoppingPageService(final RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    /**
     * Make request to a REST server for retrieving all records of stores
     * that have product in shopping list for current user.
     *
     * @param userId - current user unique identifier
     * @return list of store records
     */
    public List<Store> getStores(final int userId) {
        final String uri = goShoppingURL + "/stores/" + userId;
        final List<Store> stores = restTemplate.getForObject(uri, List.class);

        if (CollectionUtils.isEmpty(stores)) {
            LOGGER.error("Stores' list of user with id {} is empty", userId);
        } else {
            LOGGER.info("Stores' list of user with id {} has been received.", userId);
        }
        return stores;
    }

    /**
     * Make request to a REST server for retrieving
     * all products records for current store.
     *
     * @param userId  - current user unique identifier
     * @param storeId - store unique identifier
     * @return map of products records list
     */
    public Map<String, List<ShoppingList>> getProducts(final int userId, final int storeId) {
        final String uri = goShoppingURL + "/" + storeId + "/products/" + userId;
        return restTemplate.getForObject(uri, Map.class);
    }

    /**
     * Make request to a REST server for inserting
     * all selected products in the cart.
     *
     * @param formForCart - class containing selected products
     */
    public void addToCart(final FormForCart formForCart) {
        final String uri = goShoppingURL + "/cart";
        formForCart.removeUncheked();
        restTemplate.postForObject(uri, formForCart.getCarts(), Cart.class);
    }
}

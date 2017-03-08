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
 * The GoShoppingPageService class is used to hold business logic and to retrieve appropriate resources from a
 * REST server
 *
 * @author Roman Dyndyn
 */
@Service
public class GoShoppingPageService {
    private static final Logger LOGGER = LogManager.getLogger(GoShoppingPageService.class);
    private RestTemplate restTemplate;

    @Value("${application.restGoShoppingURL}")
    private String goShoppingURL;

    @Autowired
    public GoShoppingPageService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<Store> getStores(int userId){
        final String uri = goShoppingURL + "/stores/" + userId;
        List<Store> stores = restTemplate.getForObject(uri, List.class);

        if (CollectionUtils.isEmpty(stores)){
            LOGGER.error("Stores' list of user with id {} is empty", userId);
        } else {
            LOGGER.info("Stores' list of user with id {} has been received.", userId);
        }
        return stores;
    }

    public Map<String, List<ShoppingList>> getProducts(int userId, int storeId) {
        final String uri = goShoppingURL + "/" + storeId + "/products/" + userId;
        return restTemplate.getForObject(uri, Map.class);
    }

    public void addToCart(FormForCart formForCart){
        final String uri = goShoppingURL + "/cart";
        formForCart.removeUncheked();
        restTemplate.postForObject(uri, formForCart.getCarts(), Cart.class);
    }
}

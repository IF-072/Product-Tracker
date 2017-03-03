package com.softserve.if072.mvcapp.service;

import com.softserve.if072.common.model.Product;
import com.softserve.if072.common.model.Store;
import com.softserve.if072.common.model.User;
import com.softserve.if072.mvcapp.dto.ProductsWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The class contains methods that handle the http requests from the MVC Application and send requests to REST
 * Application
 *
 * @author Nazar Vynnyk
 */

@Service
public class StorePageService {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${application.restStoreURL}")
    private String storeUrl;

    @Value("${application.restProductURL}")
    private String productUrl;

    @Value("${service.user.current}")
    private String getCurrentUser;

    /**
     * Method receives from Rest Controller list of user stores
     *
     * @param userId user whose stores will be returned
     * @return list of stores
     */
    public List getAllStoresByUserId(int userId) {
        final String uri = storeUrl + "/user/{userId}";

        Map<String, Integer> param = new HashMap<>();
        param.put("userId", userId);
        return restTemplate.getForObject(uri, List.class, param);
    }

    /**
     * Method returns new store
     *
     * @return new Store
     */
    public Store addNewStore() {

        return new Store();
    }

    /**
     * This method sends store to Rest Controller for updating Database
     *
     * @param user  owner of this store
     * @param store it will be added to Database
     */
    public void addStore(User user, Store store) {
        final String uri = storeUrl + "/";

        store.setUser(user);
        store.setEnabled(true);
        restTemplate.postForObject(uri, store, Store.class);
    }

    /**
     * Method receives from Rest Controller list of products that are represented in this store
     *
     * @param storeId store which products will be shown
     * @param userId  id of user who is owner of store
     * @return list of products that are represented in store
     */
    public List getAllProductsFromStore(int storeId, int userId) {
        final String uri = storeUrl + "/{storeId}/storeProducts/{userId}";

        Map<String, Integer> param = new HashMap<>();
        param.put("storeId", storeId);
        param.put("userId", userId);

        return restTemplate.getForObject(uri, List.class, param);
    }

    /**
     * This method receives from Rest Controller store by his id
     *
     * @param storeId id if store that will be returned
     * @return store
     */
    public Store getStoreById(int storeId) {
        final String storeUri = storeUrl + "/{storeId}";

        Map<String, Integer> param = new HashMap<>();
        param.put("storeId", storeId);

        return restTemplate.getForObject(storeUri, Store.class, param);
    }

    /**
     * Method receives from Rest Controller list of products that are not represented in this store
     *
     * @param storeId id of store where we look for products
     * @param userId  id of user who is owner of store
     * @return list of products that are not represented in store
     */
    public List<Product> getNotMappedProducts(int storeId, int userId) {
        final String productsUri = storeUrl + "/{storeId}/notMappedProducts/{userId}";

        Map<String, Integer> param = new HashMap<>();
        param.put("storeId", storeId);
        param.put("userId", userId);
        ResponseEntity<List<Product>> productResult = restTemplate.exchange(productsUri, HttpMethod.GET, null,
                new ParameterizedTypeReference<List<Product>>() {
                }, param);

        return productResult.getBody();
    }

    /**
     * Method gets wrapedProducts object which contains list of product id. This all products will be added to
     * store and saved in Database.
     * Method  sends a request to the Rest Controller to add products to store
     *
     * @param userId         id of user who is owner of store
     * @param storeId        id of store where products will be added
     * @param wrapedProducts list of product id that will be added to current store
     */
    public void addProductsToStore(int userId, int storeId, ProductsWrapper wrapedProducts) {
        final String uri = String.format("%s/manyProducts/%d/%d", storeUrl, userId, storeId);

        List<Integer> productsId = wrapedProducts.getProducts();
        restTemplate.postForObject(uri, productsId, List.class);
    }

    /**
     * Method sends to Rest Controller information about product that will be deleted from store
     *
     * @param storeId   store where product will be deleted
     * @param productId product that will be deleted from current store
     */
    public void deleteProductFromStore(int storeId, int productId) {
        final String productUri = storeUrl + "/{storeId}/products/{productId}";
        final String delUri = storeUrl + "/" + storeId + "/products";

        Map<String, Integer> param = new HashMap<>();
        param.put("storeId", storeId);
        param.put("productId", productId);
        Product product = restTemplate.getForObject(productUri, Product.class, param);
        restTemplate.postForObject(delUri, product, Product.class);
    }

    /**
     * Method sends to Rest Controller information about store that will be deleted
     *
     * @param storeId store that will be deleted
     */
    public void deleteStore(int storeId) {
        final String delUri = storeUrl + "/delStore";

        Store store = getStoreById(storeId);
        restTemplate.put(delUri, store, Store.class);
    }

    /**
     * Method sends to Rest Controller information about store that will be edited
     *
     * @param store   store that will replace old store
     * @param storeId id of store that will be changed
     * @param user    user who is owner of store
     */
    public void editStore(Store store, int storeId, User user) {
        final String uri = storeUrl + "/update";

        store.setId(storeId);
        store.setUser(user);
        restTemplate.put(uri, store, Store.class);
    }

    /**
     * Method receives from Rest Controller store with the same name and address as by incoming store
     *
     * @param store store that has fields which we check for duplicates in database
     * @param user  owner of store
     * @return store from
     */
    public Store getStoreByNameAndUserId(Store store, User user) {
        final String getStoreByNameAndUserIdUri = storeUrl + "/byName/" + user.getId();
        ResponseEntity<Store> oldStore = restTemplate.postForEntity(getStoreByNameAndUserIdUri, store, Store.class);
        if (oldStore.getBody() == null) {
            return null;
        } else {
            return oldStore.getBody();
        }
    }

    /**
     * Method checks does exist store with such fields as user want to add and checks if the store is enabled
     *
     * @param store store that has fields which we check for duplicates
     * @param user  owner of store
     * @return true if received store from Rest controller exist and is enabled
     */
    public boolean alreadyExist(Store store, User user) {
        Store existStore = getStoreByNameAndUserId(store, user);
        if (existStore != null && existStore.isEnabled()) {
            return store.getId() != existStore.getId();
        }
        return false;
    }

    /**
     * Method checks does exist store with such fields as user want to add and checks if it was deleted
     *
     * @param store store that user adds
     * @param user  owner of store
     * @return true if received store from Rest controller exist and is not enabled
     */
    public boolean isDeleted(Store store, User user) {
        Store existStore = getStoreByNameAndUserId(store, user);
        return existStore != null && !existStore.isEnabled();
    }

    /**
     * Method retrieves store after his deleting
     *
     * @param storeId store that is retrieving
     */
    public void retrieveStore(int storeId) {
        final String uri = storeUrl + "/retrieve";
        Store store = getStoreById(storeId);
        restTemplate.put(uri, store, Store.class);
    }

}
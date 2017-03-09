package com.softserve.if072.mvcapp.service;

import com.softserve.if072.common.model.Category;
import com.softserve.if072.common.model.Product;
import com.softserve.if072.common.model.Store;
import com.softserve.if072.common.model.Unit;
import com.softserve.if072.common.model.User;
import com.softserve.if072.mvcapp.dto.StoresInProduct;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The class contains methods that handle the http requests from the product's page
 *
 * @author Vitaliy Malisevych
 */
@Service
public class ProductPageService {

    //private static final Logger LOGGER = LogManager.getLogger(ProductPageService.class);

    @Value("${application.restProductURL}")
    private String productUrl;

    @Value("${application.restUnitURL}")
    private String unitUrl;

    @Value("${application.restCategoryURL}")
    private String categoryUrl;

    @Value("${application.restStoreURL}")
    private String storeUrl;

    private RestTemplate restTemplate;

    @Autowired
    public ProductPageService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    /**
     * This method receives all products by user id from RESTful service and returns them.
     *
     * @param userId id of user whose products must be received
     * @return all user's products
     */
    public List<Product> getAllProducts(int userId) {

        final String uri = productUrl + "/user/{userId}";
        Map<String, Integer> param = new HashMap<>();
        param.put("userId", userId);

        return restTemplate.getForObject(uri, List.class, param);

    }

    /**
     * This method receives product by id from RESTful service and returns them.
     *
     * @param productId id of product that must be received
     * @return product
     */
    public Product getProduct(int productId) {

        final String getProductUri = productUrl + "/{productId}";

        Map<String, Integer> param = new HashMap<>();
        param.put("productId", productId);

        return restTemplate.getForObject(getProductUri, Product.class, param);
    }

    /**
     * This method receives all categories by user id from RESTful service and returns them.
     *
     * @param userId id of user whose categories must be received
     * @return all user's categories
     */
    public List<Category> getAllCategories(int userId) {

        final String categoryUri = categoryUrl + "{userId}";

        Map<String, Integer> param = new HashMap<>();
        param.put("userId", userId);

        ResponseEntity<List<Category>> categoriesResponse = restTemplate.exchange(categoryUri, HttpMethod.GET,
                null, new ParameterizedTypeReference<List<Category>>() {
                }, param);

        return categoriesResponse.getBody();

    }

    /**
     * This method receives all units from RESTful service and returns them.
     *
     * @return all user's units
     */
    public List<Unit> getAllUnits() {

        final String unitUri = unitUrl + "/";

        ResponseEntity<List<Unit>> unitsResponse = restTemplate.exchange(unitUri, HttpMethod.GET,
                null, new ParameterizedTypeReference<List<Unit>>() {
                });
        List<Unit> units = unitsResponse.getBody();

        return units;
    }

    /**
     * This method send new product's data to the RESTful service to write them into the DataBase.
     *
     * @param product new product that user want to add to the database
     * @param user    user whose product must be added
     */
    public void addProduct(Product product, User user) {

        final String categoryByIdUri = categoryUrl + "/id/{categoryId}";
        final String UnitByIdUri = unitUrl + "/{unitId}";
        final String addProductUri = productUrl + "/";

        Map<String, Integer> param = new HashMap<>();
        if (product.getCategory().getId() > 0) {
            param.put("categoryId", product.getCategory().getId());
            Category category = restTemplate.getForObject(categoryByIdUri, Category.class, param);
            product.setCategory(category);
        } else {
            product.setCategory(null);
        }

        if (product.getUnit().getId() > 0) {
            param.clear();
            param.put("unitId", product.getUnit().getId());
            Unit unit = restTemplate.getForObject(UnitByIdUri, Unit.class, param);
            product.setUnit(unit);
        } else {
            product.setUnit(null);
        }

        product.setUser(user);
        product.setEnabled(true);
        product.setImage(null);

        restTemplate.postForObject(addProductUri, product, Product.class);
    }

    /**
     * This method send edited product's data to the RESTful service to update them into the DataBase.
     *
     * @param product new product that user want to edit in the database
     * @param user    user whose product must be edited
     */
    public void editProduct(Product product, User user) {

        final String categoryByIdUri = categoryUrl + "/id/{categoryId}";
        final String UnitByIdUri = unitUrl + "/{unitId}";
        final String editProductUri = productUrl + "/";

        Map<String, Integer> param = new HashMap<>();
        if (product.getCategory().getId() > 0) {
            param.put("categoryId", product.getCategory().getId());
            Category category = restTemplate.getForObject(categoryByIdUri, Category.class, param);
            product.setCategory(category);
        } else {
            product.setCategory(null);
        }

        if (product.getUnit().getId() > 0) {
            param.clear();
            param.put("unitId", product.getUnit().getId());
            Unit unit = restTemplate.getForObject(UnitByIdUri, Unit.class, param);
            product.setUnit(unit);
        } else {
            product.setUnit(null);
        }

        product.setUser(user);
        product.setEnabled(true);

        restTemplate.put(editProductUri, product, Product.class);

    }

    /**
     * This method send product's id to the RESTful service to delete them from the DataBase.
     *
     * @param productId ID of product that user want to delete from the database
     */
    public void delProduct(int productId) {

        final String uri = productUrl + "/{productId}";
        Map<String, Integer> param = new HashMap<>();
        param.put("productId", productId);

        restTemplate.delete(uri, param);
    }

    /**
     * This method sends userId to the RESTful service to receive all stores of user
     *
     * @param userId user whose stores must be received
     * @return list of stores
     */
    public List<Store> getAllStores(int userId) {

        final String getAllStoresUri = storeUrl + "/user/{userId}";

        Map<String, Integer> param = new HashMap<>();
        param.put("userId", userId);

        ResponseEntity<List<Store>> rateResponse = restTemplate.exchange(getAllStoresUri, HttpMethod.GET,
                null, new ParameterizedTypeReference<List<Store>>() {
                }, param);
        return rateResponse.getBody();
    }

    /**
     * This method sends userId to the RESTful service to receive IDs of all user's stores
     *
     * @param userId user whose store's IDs must be received
     * @return list of store's IDs
     */
    public Map<Integer, String> getAllStoresId(int userId) {
        Map<Integer, String> allStoresById = new HashMap<>();
        List<Store> allStores = getAllStores(userId);
        if (allStores != null) {
            for (Store s : allStores) {
                allStoresById.put(s.getId(), s.getName() + ", " + s.getAddress());
            }
        }
        return allStoresById;
    }

    /**
     * This method sends userId to the RESTful service to receive all stores, where user can buy
     * this product.
     *
     * @param productId product that user can buy in received stores
     */
    public StoresInProduct getStoresInProduct(int productId) {

        Product product = getProduct(productId);

        Map<Integer, String> storesInProductById = new HashMap<>();
        List<Integer> listStoresInProductById = new ArrayList<>();
        if (product.getStores() != null) {
            for (Store s : product.getStores()) {
                storesInProductById.put(s.getId(), s.getName() + ", " + s.getAddress());
                listStoresInProductById.add(s.getId());
            }
        }

        StoresInProduct storesInProduct = new StoresInProduct();
        storesInProduct.setStoresId(listStoresInProductById);

        return storesInProduct;

    }

    /**
     * This method sends new list of store's IDs mapped on product to the RESTful service
     * to update the information in the DataBase
     *
     * @param productId       ID of product that mapped on stores
     * @param storesInProduct new list of store's IDs mapped on product
     */
    public void updateStoresInProduct(StoresInProduct storesInProduct, int productId) {

        final String getStoreByIdUri = storeUrl + "/{storeId}";
        final String addStoreToProductUri = productUrl + "/stores/";
        final String deleteStoreFromProductUri = productUrl + "/deleteStores/";

        Product product = getProduct(productId);
        List<Store> oldStores = product.getStores();

        Map<String, Integer> param = new HashMap<>();
        List<Store> newStores = new ArrayList<>();

        for (int storeId : storesInProduct.getStoresId()) {
            param.put("storeId", storeId);
            newStores.add(restTemplate.getForObject(getStoreByIdUri, Store.class, param));
        }

        List<Store> storesToAdd = new ArrayList<>();
        List<Store> storesToDelete = new ArrayList<>();
        if (oldStores != null) {
            if (newStores != null) {
                storesToAdd.addAll(newStores);
                storesToDelete.addAll(oldStores);
                storesToAdd.removeAll(oldStores);
                storesToDelete.removeAll(newStores);
            } else {
                storesToDelete.addAll(oldStores);
            }
        } else {
            storesToAdd.addAll(newStores);
        }

        if (!storesToAdd.isEmpty()) {
            product.setStores(storesToAdd);
            restTemplate.postForObject(addStoreToProductUri, product, Product.class);
        }

        if (!storesToDelete.isEmpty()) {
            product.setStores(storesToDelete);
            restTemplate.postForObject(deleteStoreFromProductUri, product, Product.class);
        }
    }

    /**
     * This method sends data about product and user to the RESTful service
     * to receive te product from the DataBase by it's name
     *
     * @param product product that must be received
     * @param user    user whose product must be received
     * @return product
     */
    public Product getProductByNameAndUserId(Product product, User user) {

        final String getProductByNameAndUserIdUri = productUrl + "/{userId}/getByName/{productName}";

        Map<String, String> param = new HashMap<>();
        param.put("productName", product.getName());
        param.put("userId", Integer.toString(user.getId()));

        return restTemplate.getForObject(getProductByNameAndUserIdUri, Product.class, param);

    }

    /**
     * This method checks whether a product with this name already exists in the DataBase
     *
     * @param product product, which is checked
     * @param user    user whose product must be checked
     * @return boolean value
     */
    public boolean isAlreadyExist(Product product, User user) {

        Product existsProduct = getProductByNameAndUserId(product, user);

        if (existsProduct != null && existsProduct.isEnabled()) {
            return !(existsProduct.getId() == product.getId());
        } else {
            return false;
        }

    }

    /**
     * This method checks whether a product with this name already exists in the DataBase and was deleted
     *
     * @param product product, which is checked
     * @param user    user whose product must be checked
     * @return boolean value
     */
    public boolean isDeleted(Product product, User user) {
        Product existsProduct = getProductByNameAndUserId(product, user);

        return existsProduct != null && !existsProduct.isEnabled();

    }

    /**
     * This method restores a product that was deleted in the DataBase
     *
     * @param product product, which was deleted
     */
    public void restoreProduct(Product product) {

        final String restoreProductUri = productUrl + "/restore";

        restTemplate.put(restoreProductUri, product, Product.class);

    }

}

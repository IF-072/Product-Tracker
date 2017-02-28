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

    private static final Logger LOGGER = LogManager.getLogger(ProductPageService.class);

    @Value("${application.restProductURL}")
    private String productUrl;

    @Value("${application.restUnitURL}")
    private String unitUrl;

    @Value("${application.restCategoryURL}")
    private String categoryUrl;

    @Value("${application.restStoreURL}")
    private String storeUrl;

    @Autowired
    RestTemplate restTemplate;

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

    public Product getProduct(int productId) {

        final String getProductUri = productUrl + "/{productId}";

        Map<String, Integer> param = new HashMap<>();
        param.put("productId", productId);
        Product product = restTemplate.getForObject(getProductUri, Product.class, param);

        return product;
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
                null, new ParameterizedTypeReference<List<Category>>(){}, param);

        return categoriesResponse.getBody();

    }

    public List<Unit> getAllUnits() {

        final String unitUri = unitUrl + "/";

        ResponseEntity<List<Unit>> unitsResponse = restTemplate.exchange(unitUri, HttpMethod.GET,
                null, new ParameterizedTypeReference<List<Unit>>(){});
        List<Unit> units = unitsResponse.getBody();

        return units;
    }

    public void addProduct(Product product, User user) {

        final String categoryByIdUri = categoryUrl + "/id/{categoryId}";
        final String UnitByIdUri = unitUrl + "/{unitId}";
        final String addProductUri = productUrl +"/";

        Map<String, Integer> param = new HashMap<>();
        if(product.getCategory().getId() > 0) {
            param.put("categoryId", product.getCategory().getId());
            Category category = restTemplate.getForObject(categoryByIdUri, Category.class, param);
            product.setCategory(category);
        } else {
            product.setCategory(null);
        }

        if(product.getUnit().getId() > 0) {
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

    public void editProduct(Product product, User user) {

        final String categoryByIdUri = categoryUrl + "/id/{categoryId}";
        final String UnitByIdUri = unitUrl + "/{unitId}";
        final String editProductUri = productUrl +"/";

        Map<String, Integer> param = new HashMap<>();
        if(product.getCategory().getId() > 0) {
            param.put("categoryId", product.getCategory().getId());
            Category category = restTemplate.getForObject(categoryByIdUri, Category.class, param);
            product.setCategory(category);
        } else {
            product.setCategory(null);
        }

        if(product.getUnit().getId() > 0) {
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

    public void delProduct(int productId) {

        final String uri = productUrl + "/{productId}";
        Map<String, Integer> param = new HashMap<>();
        param.put("productId", productId);

        restTemplate.delete(uri,param);
    }

    public List<Store> getAllStores(int userId) {

        final String getAllStoresUri = storeUrl + "/user/{userId}";

        Map<String, Integer> param = new HashMap<>();
        param.put("userId", userId);

        ResponseEntity<List<Store>> rateResponse = restTemplate.exchange(getAllStoresUri, HttpMethod.GET,
                null, new ParameterizedTypeReference<List<Store>>(){}, param);
        return rateResponse.getBody();
    }

    public Map<Integer,String> getAllStoresId(int userId) {
        Map<Integer,String> allStoresById = new HashMap<>();
        List<Store> allStores = getAllStores(userId);
        if(allStores != null) {
            for(Store s : allStores) {
                allStoresById.put(s.getId(),s.getName() + ", " + s.getAddress());
            }
        }
        return allStoresById;
    }

    public StoresInProduct getStoresInProduct(int productId) {

        Product product = getProduct(productId);

        Map<Integer,String> storesInProductById = new HashMap<>();
        List<Integer> listStoresInProductById = new ArrayList<>();
        if(product.getStores() != null){
            for(Store s : product.getStores()) {
                storesInProductById.put(s.getId(),s.getName() + ", " + s.getAddress());
                listStoresInProductById.add(s.getId());
            }
        }

        StoresInProduct storesInProduct = new StoresInProduct();
        storesInProduct.setStoresId(listStoresInProductById);

        return storesInProduct;

    }

    public void updateStoresInProduct(StoresInProduct storesInProduct, int productId) {

        final String getStoreByIdUri = storeUrl + "/{storeId}";
        final String addStoreToProductUri = productUrl + "/stores/";
        final String deleteStoreFromProductUri = productUrl + "/deleteStores/";

        Product product = getProduct(productId);
        List<Store> oldStores = product.getStores();

        Map<String, Integer> param = new HashMap<>();
        List<Store> newStores = new ArrayList<>();

        for(int storeId : storesInProduct.getStoresId()) {
            param.put("storeId", storeId);
            newStores.add(restTemplate.getForObject(getStoreByIdUri, Store.class, param));
        }

        List<Store> storesToAdd = new ArrayList<>();
        List<Store> storesToDelete = new ArrayList<>();
        if(oldStores != null) {
            if(newStores != null) {
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

        if(!storesToAdd.isEmpty()) {
            product.setStores(storesToAdd);
            restTemplate.postForObject(addStoreToProductUri, product, Product.class);
        }

        if(!storesToDelete.isEmpty()) {
            product.setStores(storesToDelete);
            restTemplate.postForObject(deleteStoreFromProductUri, product, Product.class);
        }
    }

    public Product getProductByNameAndUserId(Product product, User user) {

        final String getProductByNameAndUserIdUri = productUrl + "/{userId}/getByName/{productName}";

        Map<String, String> param = new HashMap<>();
        param.put("productName", product.getName());
        System.out.println(product.getName());
        param.put("userId", Integer.toString(user.getId()));

        return restTemplate.getForObject(getProductByNameAndUserIdUri, Product.class, param);

    }

    public boolean isAlreadyExist(Product product, User user) {

        Product existsProduct = getProductByNameAndUserId(product, user);

        if(existsProduct != null && existsProduct.isEnabled()) {
            if(existsProduct.getId() == product.getId()) {
                return false;
            } else {
                return true;
            }

        } else {
            return false;
        }

    }

    public boolean isDeleted(Product product, User user) {

        Product existsProduct = getProductByNameAndUserId(product, user);

        if(existsProduct != null && !existsProduct.isEnabled()) {
            return true;
        } else {
            return false;
        }

    }

    public void restoreProduct(Product product) {

        final String restoreProductUri = productUrl + "/restore";

        restTemplate.put(restoreProductUri, product, Product.class);

    }

}

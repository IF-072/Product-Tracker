package com.softserve.if072.restservice.controller;

import com.softserve.if072.common.model.Product;
import com.softserve.if072.common.model.Store;
import com.softserve.if072.restservice.exception.DataNotFoundException;
import com.softserve.if072.restservice.service.StoreService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

/**
 * Serve requests from MVC Application, used for working with Store model
 *
 * @author Nazar Vynnyk
 */

@RestController
@RequestMapping("/api/stores")
public class StoreController {
    public static final Logger LOGGER = LogManager.getLogger(StoreController.class);
    private StoreService storeService;

    @Autowired
    public StoreController(StoreService storeService) {
        this.storeService = storeService;
    }

    /**
     * Returns all user stores
     *
     * @param userId user whose stores will be returned
     * @return list of user stores or null if current user don`t has stores
     */
    @PreAuthorize("#userId != null  && #userId == authentication.user.id")
    @GetMapping("/user/{userId}")
    @ResponseBody
    @ResponseStatus(value = HttpStatus.OK)
    public List<Store> getAllStoresByUserId(@PathVariable int userId) {
        List<Store> stores = storeService.getAllStores(userId);
        LOGGER.info("All Stores were found");
        return stores;
    }

    /**
     * Returns store by id
     *
     * @param id store that will be returned
     * @return store
     * @throws DataNotFoundException - if the store is not found
     */
    @PostAuthorize("returnObject != null && returnObject.user.id == authentication.user.id")
    @GetMapping("/{id}")
    @ResponseBody
    @ResponseStatus(value = HttpStatus.OK)
    public Store getStoreByID(@PathVariable int id) {
        Store store = storeService.getStoreByID(id);
        LOGGER.info(String.format("Store with id %d was retrieved", id));
        return store;
    }

    /**
     * Adds new store
     *
     * @param store will be saved
     * @throws IllegalArgumentException - if the passed store is null or has empty name not found
     */
    @PreAuthorize("#store != null && #store.user != null && #store.user.id == authentication.user.id")
    @PostMapping("/")
    @ResponseStatus(value = HttpStatus.CREATED)
    public void addStore(@RequestBody Store store) {
        storeService.addStore(store);
        LOGGER.info("New Store was created");
    }

    /**
     * This method  updates store that is in DataBase and has the same id as the passed hire.
     *
     * @param store will be saved
     * @throws IllegalArgumentException - if the passed store has empty name field or the updated store is not found
     */
    @PreAuthorize("#store != null && #store.user != null && #store.user.id == authentication.user.id")
    @PutMapping("/update")
    @ResponseBody
    @ResponseStatus(value = HttpStatus.OK)
    public void updateStore(@RequestBody Store store) {
        storeService.updateStore(store);
    }

    /**
     * This method removes store
     *
     * @param store will be removed
     * @throws DataNotFoundException if the store is not found
     */
    @PreAuthorize("#store != null && #store.user != null && #store.user.id == authentication.user.id")
    @PutMapping("/delStore")
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public void deleteStore(@RequestBody Store store) {
        int storeId = store.getId();
        storeService.deleteStore(storeId);
        LOGGER.info(String.format("Store with id %d was deleted", storeId));
    }

    /**
     * This method returns all products from the current store
     *
     * @param storeId store where we look for products
     * @param userId  user whose products will be returned
     * @return list of products of current store
     */
    @PreAuthorize("#userId != null  && #userId == authentication.user.id && " +
            "@storeSecurityService.hasPermissionToAccess(#storeId)")
    @GetMapping("/{storeId}/storeProducts/{userId}")
    @ResponseBody
    @ResponseStatus(value = HttpStatus.OK)
    public List<Product> getAllProductsFromStore(@PathVariable Integer storeId, @PathVariable Integer userId) {
        List<Product> products = storeService.getProductsByStoreId(storeId, userId);
        LOGGER.info("All Products were found");
        return products;
    }

    /**
     * This method returns only one product from the current store
     *
     * @param storeId   current store which product will be returned
     * @param productId product that will be returned
     * @return product
     * @throws DataNotFoundException if result set is empty
     */
    @PostAuthorize("returnObject != null && returnObject.user != null && returnObject.user.id == authentication.user" +
            ".id")
    @GetMapping("/{storeId}/products/{productId}")
    @ResponseBody
    @ResponseStatus(value = HttpStatus.OK)
    public Product getProductFromStore(@PathVariable Integer storeId, @PathVariable Integer productId) {
        Product product = storeService.getProductFromStoreById(storeId, productId);
        LOGGER.info("Product was found");
        return product;
    }

    /**
     * This method removes product that is presented in the shop
     *
     * @param storeId store were the product is presented
     * @param product which will be deleted
     * @throws DataNotFoundException if the product is not presented in this store
     */
    @PreAuthorize("#product != null && #product.user != null && #product.user.id == authentication.user.id && " +
            "@storeSecurityService.hasPermissionToAccess(#storeId)")
    @PostMapping("/{storeId}/products")
    @ResponseStatus(value = HttpStatus.OK)
    public void deleteProductFromStore(@RequestBody Product product, @PathVariable Integer storeId) {
        int productId = product.getId();
        storeService.deleteProductFromStoreById(storeId, productId);
        LOGGER.info(String.format("Product %d from Store %d was deleted", productId, storeId));
    }


    /**
     * This method add product to store
     *
     * @param storeId   store where product will be added
     * @param productId product that will be added to store
     * @throws DataNotFoundException - if product or store is not found
     */
    @PreAuthorize("#userId != null  && #userId == authentication.user.id && @storeSecurityService" +
            ".hasPermissionToAccess(#storeId) && @productSecurityService.hasPermissionToAccess(#productId)")
    @PostMapping("/products/")
    @ResponseStatus(value = HttpStatus.CREATED)
    public void addProductToStore(@PathVariable Integer storeId, @PathVariable Integer productId,
                                  @PathVariable Integer userId) {
        storeService.addProductToStore(storeId, productId);
        LOGGER.info(String.format("Product %d was added to Store %d", productId, storeId));
    }

    /**
     * This method adds products to store
     *
     * @param productsId list of productId that will be added to store
     * @param storeId    store where products will be added
     * @throws DataNotFoundException if list of productId is empty, or store is not found
     */
    @PreAuthorize("#userId != null  && #userId == authentication.user.id && @storeSecurityService" +
            ".hasPermissionToAccess(#storeId)")
    @PostMapping("/manyProducts/{userId}/{storeId}")
    @ResponseStatus(value = HttpStatus.OK)
    public void addProductsToStore(@RequestBody List<Integer> productsId, @PathVariable Integer userId, @PathVariable
            Integer storeId) {
        storeService.addProductsToStore(productsId, storeId);
        LOGGER.info(String.format("Products  where added to Store %d", storeId));
    }

    /**
     * This method retrieves products that are not presented in the store. This
     * method is used for adding products to store
     *
     * @param storeId - store where we look for products, that are not added there yet
     * @param userId  - user whose products and stores we are looking for
     * @return - set of products that are not added to store
     * @throws DataNotFoundException - if result set is empty
     */
    @PreAuthorize("#userId != null  && #userId == authentication.user.id && @storeSecurityService" +
            ".hasPermissionToAccess(#storeId)")
    @GetMapping("/{storeId}/notMappedProducts/{userId}")
    @ResponseBody
    @ResponseStatus(value = HttpStatus.OK)
    public Set<Product> getNotMappedProducts(@PathVariable Integer storeId, @PathVariable Integer userId) {

        Set<Product> notMappedProducts = storeService.getNotMappedProducts(storeId, userId);

        LOGGER.info(String.format("All Products that are not presented in store %d were found", storeId));
        return notMappedProducts;
    }

    /**
     * This method checks does user has store with same name as by received store
     *
     * @param userId owner of store
     * @param store  store that user want save
     * @return store from DataBase which name is equal to received store or if such store is not found - null
     */

    @PreAuthorize("#store != null && #store.user != null && #store.user.id == authentication.user.id")
    @PostMapping("/byName/{userId}")
    @ResponseStatus(value = HttpStatus.OK)
    public Store getStoreByNameAndUser(@PathVariable Integer userId, @RequestBody Store store) {
        Store oldStore = storeService.getStoreByNameAndUser(store.getName(), store.getAddress(), userId);
        LOGGER.info(String.format("Store with name %s was retrieved", store.getName()));

        return oldStore;

    }

    /**
     * This method  retrieves store that is in DataBase.
     *
     * @param store that will be will be retrieved
     */
    @PreAuthorize("#store != null && #store.user != null && #store.user.id == authentication.user.id")
    @PutMapping("/retrieve")
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public void retrieveStore(@RequestBody Store store) {
        storeService.retrieveStore(store.getId());
        LOGGER.info(String.format("Store with id %d was retrieved", store.getId()));
    }

}
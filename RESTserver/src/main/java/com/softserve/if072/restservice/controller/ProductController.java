package com.softserve.if072.restservice.controller;

import com.softserve.if072.common.model.Product;
import com.softserve.if072.common.model.Store;
import com.softserve.if072.restservice.exception.DataNotFoundException;
import com.softserve.if072.restservice.service.ProductService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * The class contains methods to add, read and delete products from database using REST Service
 *
 * @author Vitaliy Malisevych
 */

@RestController
@RequestMapping(value = "/api/product")
public class ProductController {

    public static final Logger LOGGER =  LogManager.getLogger(ProductController.class);
    private ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @Value("${product.notFound}")
    private String productNotFound;

    /**
     * Returns all user's products
     *
     * @param userId user whose products will be returned
     * @return list of user's products
     * @throws DataNotFoundException - if products not found
     */

    @PreAuthorize("#userId == authentication.user.id")
    @GetMapping(value = "/user/{userId}")
    @ResponseBody
    @ResponseStatus(value = HttpStatus.OK)
    public List<Product> getAllProductsByUserId(@PathVariable int userId) {

        try {
            List<Product> products = productService.getAllProducts(userId);
            LOGGER.info("All products were found");
            return products;
        } catch (DataNotFoundException e) {
            LOGGER.error(e.getMessage());
            return new ArrayList<Product>();
        }

    }

    /**
     * Returns product by id
     *
     * @param productId product that will be returned
     * @return product
     * @throws DataNotFoundException - if product is not found
     */

    @PostAuthorize("returnObject != null && returnObject.user != null && returnObject.user.id == authentication.user.id")
    @GetMapping(value = "/{productId}")
    @ResponseBody
    @ResponseStatus(value = HttpStatus.OK)
    public Product getProductById(@PathVariable int productId) throws DataNotFoundException {

        Product product = productService.getProductById(productId);
        LOGGER.info(String.format("Product with id %d was retrieved", productId));
        return product;

    }

    /**
     * Adds new product
     *
     * @param product that must be saved
     *
     */

    @PreAuthorize("#product.user != null && #product.user.id == authentication.user.id")
    @PostMapping(value = "/")
    @ResponseStatus(value = HttpStatus.CREATED)
    public void addProduct(@RequestBody Product product) {
        productService.addProduct(product);
        LOGGER.info("New product was created");
    }

    /**
     * Updates product that is in DataBase
     *
     * @param product will be updated
     *
     */

    @PreAuthorize("#product.user != null && #product.user.id == authentication.user.id")
    @PutMapping(value = "/")
    @ResponseStatus(value = HttpStatus.OK)
    public void update(@RequestBody Product product) throws DataNotFoundException {

        int id = product.getId();
        productService.updateProduct(product);
        LOGGER.info(String.format("Product with id %d was updated", id));

    }

    /**
     * Updates product that is in DataBase with new imageId
     *
     * @param product will be updated
     *
     */

    @PreAuthorize("#product.user != null && #product.user.id == authentication.user.id")
    @PutMapping(value = "/image")
    @ResponseStatus(value = HttpStatus.OK)
    public void updateByImage(@RequestBody Product product) throws DataNotFoundException {

        int id = product.getId();
        productService.updateProductByImage(product);
        LOGGER.info(String.format("Product with id %d was updated with new image", id));

    }

    /**
     * Rremoves product
     *
     * @param productId product which will be removed
     * @throws DataNotFoundException if the product is not found
     */

    @PreAuthorize("@productSecurityService.hasPermissionToAccess(#productId)")
    @DeleteMapping(value = "/{productId}")
    @ResponseStatus(value = HttpStatus.OK)
    public void delete(@PathVariable int productId) throws DataNotFoundException {

        productService.deleteProduct(productId);
        LOGGER.info(String.format("Product with id %d was deleted", productId));

    }

    /**
     * Returns all stores from the current product
     *
     * @param productId product which we can by in stores
     * @param userId  user whose stores will be returned
     * @return list of stores of current product
     * @throws DataNotFoundException if stores are not found
     */

    @PreAuthorize("@productSecurityService.hasPermissionToAccess(#productId)")
    @GetMapping("/{productId}/productStores/{userId}")
    @ResponseBody
    @ResponseStatus(value = HttpStatus.OK)
    public List<Store> getAllStoresFromProduct(@PathVariable int productId, @PathVariable int userId) {
        try {
            List<Store> stores = productService.getStoresByProductId(productId, userId);
            LOGGER.info("All Stores were found");
            return stores;
        } catch (DataNotFoundException e) {
            LOGGER.error(e.getMessage());
            return new ArrayList<Store>();
        }

    }

    /**
     * Adds store to product
     *
     * @param product   product which stores will be added
     */

    @PreAuthorize("#product.user != null && #product.user.id == authentication.user.id")
    @PostMapping("/stores/")
    @ResponseStatus(value = HttpStatus.OK)
    public void addStoresToProduct(@RequestBody Product product) {

        productService.addStoresToProduct(product);
        LOGGER.info(String.format("Stores were added to product %d", product.getId()));

    }

    /**
     * This method removes stores that is presented in the product
     *
     * @param product product which stores will be deleted
     * @throws DataNotFoundException if the store is not presented in this product
     */

    @PreAuthorize("#product.user != null && #product.user.id == authentication.user.id")
    @PostMapping("/deleteStores/")
    @ResponseStatus(value = HttpStatus.OK)
    public void deleteStoreFromProduct(@RequestBody Product product) throws DataNotFoundException {

        productService.deleteStoreFromProductById(product);
        LOGGER.info(String.format("Stores were deleted from product %d", product.getId()));

    }

    /**
     * This method gets product from DataBase by name and user's ID
     * @param productName Name of product, which must be returned
     * @param userId ID of user which product must be returned
     * @return product
     */
    @PreAuthorize("@productSecurityService.hasPermissionToAccess(#productName)")
    @GetMapping("/{userId}/getByName/{productName}")
    @ResponseBody
    @ResponseStatus(value = HttpStatus.OK)
    public Product getProductByNameAndUserId(@PathVariable String productName, @PathVariable String userId) {

        Product product = productService.getProductByNameAndUserId(productName, Integer.parseInt(userId));
        return product;

    }

    /**
     * This method restores the product that was deleted
     * @param product product that was deleted
     */
    @PreAuthorize("#product.user != null && #product.user.id == authentication.user.id")
    @PutMapping("/restore")
    @ResponseStatus(value = HttpStatus.OK)
    public void restoreProduct(@RequestBody Product product) {
        productService.restoreProduct(product);
    }

}

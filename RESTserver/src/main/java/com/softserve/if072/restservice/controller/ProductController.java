package com.softserve.if072.restservice.controller;

import com.softserve.if072.common.model.Product;
import com.softserve.if072.common.model.Store;
import com.softserve.if072.restservice.exception.DataNotFoundException;
import com.softserve.if072.restservice.service.ProductService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * The class contains methods to add, read and delete products from database using REST Service
 *
 * @author Vitaliy Malisevych
 */

@RestController
@RequestMapping(value = "/api/product")
@PropertySource(value = {"classpath:message.properties"})
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
    public List<Product> getAllProductsByUserId(@PathVariable int userId,
                                                HttpServletResponse response) throws DataNotFoundException {

        List<Product> products = productService.getAllProducts(userId);
        LOGGER.info("All products were found");
        return products;

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
    public Product getProductById(@PathVariable int productId,
                                  HttpServletResponse response) throws DataNotFoundException {

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
    public void update(@RequestBody Product product, HttpServletResponse response) throws DataNotFoundException {

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
    public void updateByImage(@RequestBody Product product,
                              HttpServletResponse response) throws DataNotFoundException {

        int id = product.getId();
        productService.updateProductByImage(product);
        LOGGER.info(String.format("Product with id %d was updated with new image", id));

    }

    /**
     * Rremoves product
     *
     * @param userId user whose product will be removed
     * @param productId product which will be removed
     * @throws DataNotFoundException if the product is not found
     */

    @PreAuthorize("#userId == authentication.user.id")
    @DeleteMapping(value = "/{userId}/{productId}")
    @ResponseStatus(value = HttpStatus.OK)
    public void delete(@PathVariable int productId, @PathVariable int userId,
                       HttpServletResponse response) throws DataNotFoundException {

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

    @PreAuthorize("#userId == authentication.user.id")
    @GetMapping("/{productId}/productStores/{userId}")
    @ResponseBody
    @ResponseStatus(value = HttpStatus.OK)
    public List<Store> getAllStoresFromProduct(@PathVariable int productId, @PathVariable int userId,
                                               HttpServletResponse response) {
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
    public void deleteStoreFromProduct(@RequestBody Product product,
                                       HttpServletResponse response) throws DataNotFoundException {

        productService.deleteStoreFromProductById(product);
        LOGGER.info(String.format("Stores were deleted from product %d", product.getId()));

    }

}

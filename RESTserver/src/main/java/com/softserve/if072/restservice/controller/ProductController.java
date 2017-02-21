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

    @PreAuthorize("#userId == authentication.user.id")
    @GetMapping(value = "/user/{userId}")
    @ResponseBody
    @ResponseStatus(value = HttpStatus.OK)
    public List<Product> getAllProductsByUserId(@PathVariable int userId, HttpServletResponse response) {
        try {
            List<Product> products = productService.getAllProducts(userId);
            LOGGER.info("All products were found");
            return products;
        } catch (DataNotFoundException e) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            LOGGER.error(e.getMessage(), e);
            return null;
        }
    }

    @PreAuthorize("#userId == authentication.user.id")
    @GetMapping(value = "/{userId}/{productId}")
    @ResponseBody
    @ResponseStatus(value = HttpStatus.OK)
    public Product getProductById(@PathVariable int userId, @PathVariable int productId, HttpServletResponse response) {
        try {
            Product product = productService.getProductById(productId);
            LOGGER.info(String.format("Product with id %d was retrieved", productId));
            return product;
        } catch (DataNotFoundException e) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            LOGGER.error(String.format(productNotFound, productId), e);
            return null;
        }
    }

    @PreAuthorize("#product.user != null && #product.user.id == authentication.user.id")
    @PostMapping(value = "/")
    @ResponseStatus(value = HttpStatus.CREATED)
    public void addProduct(@RequestBody Product product) {
        productService.addProduct(product);
        LOGGER.info("New product was created");
    }

    @PreAuthorize("#product.user != null && #product.user.id == authentication.user.id")
    @PutMapping(value = "/")
    @ResponseStatus(value = HttpStatus.OK)
    public void update(@RequestBody Product product, HttpServletResponse response) {
        int id = product.getId();
        try {
            productService.updateProduct(product);
            LOGGER.info(String.format("Product with id %d was updated", id));
        } catch (DataNotFoundException e) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            LOGGER.error(String.format("Cannot update Product with id %d", id), e);
        }
    }

    @PreAuthorize("#product.user != null && #product.user.id == authentication.user.id")
    @PutMapping(value = "/image")
    @ResponseStatus(value = HttpStatus.OK)
    public void updateByImage(@RequestBody Product product, HttpServletResponse response) {
        int id = product.getId();
        try {
            productService.updateProductByImage(product);
            LOGGER.info(String.format("Product with id %d was updated with new image", id));
        } catch (DataNotFoundException e) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            LOGGER.error(String.format("Cannot update Product with id %d with new image", id), e);
        }
    }

    @PreAuthorize("#userId == authentication.user.id")
    @DeleteMapping(value = "/{userId}/{productId}")
    @ResponseStatus(value = HttpStatus.OK)
    public void delete(@PathVariable int productId, @PathVariable int userId, HttpServletResponse response) {
        try {
            productService.deleteProduct(productId);
            LOGGER.info(String.format("Product with id %d was deleted", productId));
        } catch (DataNotFoundException e) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            LOGGER.error(String.format(productNotFound, productId), e);
        }
    }

    @PreAuthorize("#userId == authentication.user.id")
    @GetMapping("/{productId}/productStores/{userId}")
    @ResponseBody
    @ResponseStatus(value = HttpStatus.OK)
    public List<Store> getAllStoresFromProduct(@PathVariable int productId,
                                               @PathVariable int userId, HttpServletResponse response) {
        try {
            List<Store> stores = productService.getStoresByProductId(productId, userId);
            LOGGER.info("All Stores were found");
            return stores;
        } catch (DataNotFoundException e) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            LOGGER.error(e.getMessage(), e);
            return null;
        }
    }

    @PreAuthorize("#product.user != null && #product.user.id == authentication.user.id")
    @PostMapping("/stores/")
    @ResponseStatus(value = HttpStatus.OK)
    public void addStoreToProduct(@RequestBody Product product) {

        productService.addStoreToProduct(product);
        LOGGER.info(String.format("Stores were added to product %d", product.getId()));

    }

    @PreAuthorize("#product.user != null && #product.user.id == authentication.user.id")
    @PostMapping("/deleteStores/")
    @ResponseStatus(value = HttpStatus.OK)
    public void deleteStoreFromProduct(@RequestBody Product product, HttpServletResponse response) {
        try {
            productService.deleteStoreFromProductById(product);
            LOGGER.info(String.format("Stores were deleted from product %d", product.getId()));
        } catch (DataNotFoundException e) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            LOGGER.error(e.getMessage());
        }
    }

}

package com.softserve.if072.restservice.controller;

import com.softserve.if072.common.model.Product;
import com.softserve.if072.common.model.Store;
import com.softserve.if072.restservice.exception.DataNotFoundException;
import com.softserve.if072.restservice.service.StoreService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * Serve requests used for working with Store model
 *
 * @author Nazar Vynnyk
 */

@RestController
@RequestMapping("/stores")
public class StoreController {
    public static final Logger LOGGER = LogManager.getLogger(StoreController.class);
    private StoreService storeService;

    @Autowired
    public StoreController(StoreService storeService) {
        this.storeService = storeService;
    }

    @GetMapping("/user/{userId}")
    @ResponseBody
    @ResponseStatus(value = HttpStatus.OK)
    public List<Store> getAllStoresByUserId(@PathVariable int userId, HttpServletResponse response) {
        try {
            List<Store> stores = storeService.getAllStores(userId);
            LOGGER.info("All Stores were found");
            return stores;
        } catch (DataNotFoundException e) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            LOGGER.error(e.getMessage());
            return new ArrayList<Store>();
        }
    }

    @GetMapping("/{id}")
    @ResponseBody
    @ResponseStatus(value = HttpStatus.OK)
    public Store getStoreByID(@PathVariable int id, HttpServletResponse response) {
        try {
            Store store = storeService.getStoreByID(id);
            LOGGER.info(String.format("Store with id %d was retrieved", id));
            return store;
        } catch (DataNotFoundException e) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            LOGGER.error(e.getMessage());
            return null;
        }
    }

    @PostMapping("/")
    @ResponseStatus(value = HttpStatus.CREATED)
    public void addStore(@RequestBody Store store) {
        storeService.addStore(store);
        LOGGER.info("New Store was created");
    }

    @PutMapping("/update")
    @ResponseBody
    @ResponseStatus(value = HttpStatus.OK)
    public void updateStore(@RequestBody Store store, HttpServletResponse response) {
        int storeId1 = store.getId();
        try {
            storeService.updateStore(store);
            LOGGER.info(String.format("Store with id %d was updated", storeId1));

        } catch (IllegalArgumentException e) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            LOGGER.error(String.format("New Store %d has empty name", storeId1), e);
        }
    }

    @PutMapping("/{storeId}")
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public void deleteStore(@PathVariable int storeId, HttpServletResponse response) {
        try {
            storeService.deleteStore(storeId);
            LOGGER.info(String.format("Store with id %d was deleted", storeId));
        } catch (DataNotFoundException e) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            LOGGER.error(e.getMessage());
        }
    }

    /**
     * This method shows all products that sell at the current store
     *
     * @param storeId  current store_id
     * @param response list of products
     * @return list of products that sell at the current store
     */

    @GetMapping("/{storeId}/storeProducts/{userId}")
    @ResponseBody
    @ResponseStatus(value = HttpStatus.OK)
    public List<Product> getAllProductsFromStore(@PathVariable Integer storeId, @PathVariable Integer userId,
                                                 HttpServletResponse response) {
        try {
            List<Product> products = storeService.getProductsByStoreId(storeId, userId);
            LOGGER.info("All Products were found");
            return products;
        } catch (DataNotFoundException e) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            LOGGER.error(e.getMessage());
            return null;
        }
    }

    @GetMapping("/{storeId}/products/{productId}")
    @ResponseBody
    @ResponseStatus(value = HttpStatus.OK)
    public Product getProductFromStore(@PathVariable Integer storeId, @PathVariable Integer productId,
                                       HttpServletResponse response) {
        try {
            Product product = storeService.getProductFromStoreById(storeId, productId);
            LOGGER.info("Product was found");
            return product;
        } catch (DataNotFoundException e) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            LOGGER.error(e.getMessage());
            return null;
        }
    }

    @DeleteMapping("/{storeId}/products/{productId}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteProductFromStore(@PathVariable Integer storeId, @PathVariable Integer
            productId, HttpServletResponse response) {
        try {
            storeService.deleteProductFromStoreById(storeId, productId);
            LOGGER.info(String.format("Product %d from Store %d was deleted", productId, storeId));
        } catch (DataNotFoundException e) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            LOGGER.error(e.getMessage());
        }
    }

    @PostMapping("/products/")
    @ResponseStatus(value = HttpStatus.CREATED)
    public void addProductToStore(@RequestBody Store store, Product product) {
        storeService.addProductToStore(store, product);
        LOGGER.info(String.format("Product %d was added to Store %d", product.getId(), store.getId()));
    }

}

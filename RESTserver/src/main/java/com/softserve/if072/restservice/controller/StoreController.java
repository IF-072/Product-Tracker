package com.softserve.if072.restservice.controller;

import com.softserve.if072.common.model.Product;
import com.softserve.if072.common.model.Store;
import com.softserve.if072.restservice.service.StoreService;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Serve requests used for working with Store model
 */

@Controller
@RequestMapping("/stores")
public class StoreController {
    public static final Logger LOGGER =  LogManager.getLogger(StoreController.class);
    private StoreService storeService;

    @Autowired
    public StoreController(StoreService storeService){
        this.storeService = storeService;
    }

    @GetMapping
    @ResponseBody
    @ResponseStatus(value = HttpStatus.OK)
    public List<Store> getAllStores(HttpServletResponse response) throws IOException {
        try {
            List<Store> stores = storeService.getAllStores();
            LOGGER.info("All Stores were found");
            return stores;
        } catch (RuntimeException e) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            LOGGER.error("Stores were not found: " + e);
            return null;
        }
    }

    @GetMapping("/{id}")
    @ResponseBody
    @ResponseStatus(value = HttpStatus.OK)
        public Store getStoreByID(@PathVariable int id, HttpServletResponse response) throws IOException {
        try {
            Store store = storeService.getStoreByID(id);
            LOGGER.info("Store with id " + id + " was retrieved");
            return store;
        } catch (RuntimeException e) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            LOGGER.error("Store with id " + id + " was not found: " + e);
            return null;
        }
    }

  // @PostMapping("/add")
  @RequestMapping(value = "/add", method = RequestMethod.POST, headers="Accept=application/json")
  @ResponseStatus(value = HttpStatus.CREATED)
   public void addStore(@RequestBody Store store) {
       storeService.addStore(store);
       LOGGER.info("New Store was created");
  }

    @PutMapping("/")
    @ResponseStatus(value = HttpStatus.OK)
    public Store updateStore(@RequestBody Store store, HttpServletResponse response) throws IOException{
        try {
           storeService.updateStore(store);
            LOGGER.info("Stote with id " + store.getId() + " was updated");
            store = storeService.getStoreByID(store.getId());
            return store;
        } catch (RuntimeException e) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            LOGGER.error("Store with id " + store.getId() + " was not found: " + e);
            return null;
        }
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteStore(@PathVariable int id, HttpServletResponse response) throws IOException {
        try {
            storeService.deleteStore(id);
            LOGGER.info("Store with id " + id + " was deleted");
        } catch (RuntimeException e) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            LOGGER.error("Store with id " + id + " was not found: " + e);
        }
    }

    /**
     * This method shows all products that sell at the current store
     *
     * @param id current store_id
     * @param response
     * @return list of products that sell at the current store
     * @throws IOException if current store hasn't any product we inform user
     */

    @GetMapping("/{id}/products")
    @ResponseBody
    @ResponseStatus(value = HttpStatus.OK)
    public List<Product> getAllProducts(@PathVariable int id, HttpServletResponse response) throws IOException {
        try {
            List<Product> products = storeService.getProductsByStoreId(id);
            LOGGER.info("All Products were found");
            return products;
        } catch (RuntimeException e) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            LOGGER.error("Products were not found: " + e);
            return null;
        }
    }


}

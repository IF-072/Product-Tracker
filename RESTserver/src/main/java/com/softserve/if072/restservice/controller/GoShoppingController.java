package com.softserve.if072.restservice.controller;

import com.softserve.if072.common.model.Product;
import com.softserve.if072.common.model.Store;
import com.softserve.if072.restservice.exception.DataNotFoundException;
import com.softserve.if072.restservice.service.ShoppingListService;
import com.softserve.if072.restservice.service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by dyndyn on 11.02.2017.
 */

@RestController
@RequestMapping("/goShopping")
public class GoShoppingController {

    public static final Logger LOGGER =  LogManager.getLogger(ShoppingListController.class);
    private ShoppingListService shoppingListService;
    private StoreService storeService;

    @Autowired
    public GoShoppingController(ShoppingListService shoppingListService, StoreService storeService) {
        this.shoppingListService = shoppingListService;
        this.storeService = storeService;
    }

    @GetMapping("/{userId}")
    @ResponseBody
    @ResponseStatus(value = HttpStatus.OK)
    public List<Store> getShoppingListByUserId(@PathVariable int userId, HttpServletResponse response) {
        try {
//            List<Product> shoppingLists = shoppingListService.getProductsByUserId(userId);
            List<Store> list = storeService.getAllStores(userId);

            for (Store store : list) {
                store.setProducts(storeService.getProductsByStoreId(store.getId()));
            }

            LOGGER.info(String.format("Stores of user id %d was found ", userId));
            return list;
        } catch (DataNotFoundException e) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            LOGGER.error(e.getMessage());
            return null;
        }
    }

}

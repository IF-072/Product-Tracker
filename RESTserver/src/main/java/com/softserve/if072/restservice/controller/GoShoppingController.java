package com.softserve.if072.restservice.controller;

import com.softserve.if072.common.model.Product;
import com.softserve.if072.common.model.Store;
import com.softserve.if072.restservice.exception.DataNotFoundException;
import com.softserve.if072.restservice.service.ShoppingListService;
import com.softserve.if072.restservice.service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletResponse;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.Iterator;

/**
 * Created by dyndyn on 11.02.2017.
 */

@RestController
@RequestMapping("/goShopping")
public class GoShoppingController {

    public static final Logger LOGGER = LogManager.getLogger(GoShoppingController.class);
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
            List<Product> shoppingList = shoppingListService.getProductsByUserId(userId);
            List<Store> list = storeService.getAllStores(userId);

            if(!CollectionUtils.isEmpty(shoppingList) && !CollectionUtils.isEmpty(list)) {
                Iterator<Store> iterator = list.iterator();
                while (iterator.hasNext()) {
                    Store store = iterator.next();
                    if(!CollectionUtils.isEmpty(store.getProducts())) {
                        Set<Product> set = new HashSet<Product>(shoppingList);
                        set.retainAll(store.getProducts());
                        store.setProducts(new LinkedList<Product>(set));
                    } else{
                        iterator.remove();
                    }
                }
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

package com.softserve.if072.restservice.controller;

import com.softserve.if072.common.model.Cart;
import com.softserve.if072.common.model.ShoppingList;
import com.softserve.if072.common.model.Store;
import com.softserve.if072.restservice.exception.DataNotFoundException;
import com.softserve.if072.restservice.service.GoShoppingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The GoShoppingController class handles requests
 * for function "Go shopping" resources.
 *
 * @author Roman Dyndyn
 */

@RestController
@RequestMapping("/api/goShopping")
public class GoShoppingController {

    private static final Logger LOGGER = LogManager.getLogger(GoShoppingController.class);
    private final GoShoppingService goShoppingService;

    @Autowired
    public GoShoppingController(final GoShoppingService goShoppingService) {
        this.goShoppingService = goShoppingService;
    }

    /**
     * Handles requests for retrieving all stores with products
     * that contains in shopping list and current store.
     *
     * @param userId - current user unique identifier
     * @return list of stores
     */
    @PreAuthorize("#userId == authentication.user.id")
    @GetMapping("/stores/{userId}")
    @ResponseBody
    public List<Store> getStores(@PathVariable final int userId,
                                 final HttpServletResponse response) {
        try {
            final List<Store> list = goShoppingService.getStoreByUserId(userId);
            LOGGER.info(String.format("Stores of user id %d was found ", userId));
            response.setStatus(HttpServletResponse.SC_OK);
            return list;
        } catch (DataNotFoundException e) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            LOGGER.error(e.getMessage(), e);
            return new ArrayList<>();
        }
    }

    /**
     * Handles requests for retrieving all products that contains in a
     * chosen store, and products that remains in shopping list.
     *
     * @param userId  - current user unique identifier
     * @param storeId - chosen store unique identifier
     * @return map of shopping list
     */
    @PreAuthorize("#userId == authentication.user.id")
    @GetMapping("/{storeId}/products/{userId}")
    @ResponseBody
    public Map<String, List<ShoppingList>> getProducts(@PathVariable final int userId, @PathVariable final int storeId,
                                                       final HttpServletResponse response) {
        try {
            final Map<String, List<ShoppingList>> map = goShoppingService.getProducts(userId, storeId);
            LOGGER.info(String.format("Product of user id %d was found ", userId));
            response.setStatus(HttpServletResponse.SC_OK);
            return map;
        } catch (DataNotFoundException e) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            LOGGER.error(e.getMessage(), e);
            return new HashMap<>();
        }
    }

    /**
     * Handles requests for inserting products in cart.
     *
     * @param carts - list of cart
     */
    @PreAuthorize("#carts != null &&  #carts[0] != null && #carts[0].user.id == authentication.user.id")
    @PostMapping("/cart")
    @ResponseBody
    @ResponseStatus(value = HttpStatus.OK)
    public void insertInCart(@RequestBody final List<Cart> carts) {

        goShoppingService.insertCart(carts);
        LOGGER.info("Cart was inserted");

    }
}

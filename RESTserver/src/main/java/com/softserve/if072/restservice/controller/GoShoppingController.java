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
import java.util.List;
import java.util.Map;

/**
 * The GoShoppingController class handles requests for function "Go shopping" resources
 *
 * @author Roman Dyndyn
 */

@RestController
@RequestMapping("/api/goShopping")
public class GoShoppingController {

    private static final Logger LOGGER = LogManager.getLogger(GoShoppingController.class);
    private GoShoppingService goShoppingService;

    @Autowired
    public GoShoppingController(GoShoppingService goShoppingService) {
        this.goShoppingService = goShoppingService;
    }

    @PreAuthorize("#userId == authentication.user.id")
    @GetMapping("/stores/{userId}")
    @ResponseBody
    @ResponseStatus(value = HttpStatus.OK)
    public List<Store> getStores(@PathVariable int userId, HttpServletResponse response) {
        try {
            List<Store> list = goShoppingService.getStoreByUserId(userId);
            LOGGER.info(String.format("Stores of user id %d was found ", userId));
            return list;
        } catch (DataNotFoundException e) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            LOGGER.error(e.getMessage());
            return null;
        }
    }

    @PreAuthorize("#userId == authentication.user.id")
    @GetMapping("/{storeId}/products/{userId}")
    @ResponseBody
    @ResponseStatus(value = HttpStatus.OK)
    public Map<String, List<ShoppingList>> getProducts(@PathVariable int userId, @PathVariable int storeId,
                                                       HttpServletResponse response) {
        try {
            Map<String, List<ShoppingList>> map = goShoppingService.getProducts(userId, storeId);
            LOGGER.info(String.format("Product of user id %d was found ", userId));
            return map;
        } catch (DataNotFoundException e) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            LOGGER.error(e.getMessage());
            return null;
        }
    }

    @PreAuthorize("#carts != null &&  #carts[0] != null && " +
            "#carts[0].user.id == authentication.user.id")
    @PostMapping("/cart")
    @ResponseBody
    @ResponseStatus(value = HttpStatus.OK)
    public void insertInCart(@RequestBody List<Cart> carts, HttpServletResponse response) {

        goShoppingService.insertCart(carts);
        LOGGER.info("Cart was inserted");

    }
}

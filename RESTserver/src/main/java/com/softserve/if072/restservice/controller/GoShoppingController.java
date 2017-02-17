package com.softserve.if072.restservice.controller;

import com.softserve.if072.common.model.FormForCart;
import com.softserve.if072.common.model.Product;
import com.softserve.if072.common.model.Store;
import com.softserve.if072.restservice.exception.DataNotFoundException;
import com.softserve.if072.restservice.service.GoShoppingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Created by dyndyn on 11.02.2017.
 */

@RestController
@RequestMapping("/api/goShopping")
public class GoShoppingController {

    public static final Logger LOGGER = LogManager.getLogger(GoShoppingController.class);
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
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @PreAuthorize("#userId == authentication.user.id")
    @PostMapping("/products/{userId}")
    @ResponseBody
    @ResponseStatus(value = HttpStatus.OK)
    public Map<String, List<Product>> getProducts(@PathVariable int userId, @RequestParam String stores, HttpServletResponse response) {
        try {
            String[] arr = stores.trim().replaceAll("\\[", "").replaceAll("\\]", "").split(",");
            Map<String, List<Product>> map = goShoppingService.getProducts(userId, Arrays.asList(arr).stream().mapToInt(Integer::parseInt).toArray());
            LOGGER.info(String.format("Product of user id %d was found ", userId));
            return map;
        } catch (DataNotFoundException e) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            LOGGER.error(e.getMessage());
            return null;
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @PreAuthorize("#cart != null && #cart.carts != null && #cart.carts[0] != null && #cart.carts[0].user.id == authentication.user.id")
    @PostMapping("/cart")
    @ResponseBody
    @ResponseStatus(value = HttpStatus.OK)
    public void getProducts(@RequestBody FormForCart cart, HttpServletResponse response) {
        try {
            goShoppingService.insertCart(cart);
            LOGGER.info(String.format("Cart of user id %d was updated", cart.getUserId()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

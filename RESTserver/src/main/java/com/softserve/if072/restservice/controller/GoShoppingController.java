package com.softserve.if072.restservice.controller;

import com.softserve.if072.common.model.Product;
import com.softserve.if072.common.model.Store;
import com.softserve.if072.restservice.exception.DataNotFoundException;
import com.softserve.if072.restservice.service.GoShoppingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
@RequestMapping("/goShopping")
public class GoShoppingController {

    public static final Logger LOGGER = LogManager.getLogger(GoShoppingController.class);
    private GoShoppingService goShoppingService;

    @Autowired
    public GoShoppingController(GoShoppingService goShoppingService) {
        this.goShoppingService = goShoppingService;
    }

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
}

package com.softserve.if072.restservice.controller;

import com.softserve.if072.common.model.Cart;
import com.softserve.if072.restservice.exception.DataNotFoundException;
import com.softserve.if072.restservice.service.CartService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;


/**
 * The CartController class is used to mapping requests for
 * cart resources
 *
 * @author Igor Kryviuk
 */
@RestController
@RequestMapping("/cart")
public class CartController {
    @Autowired
    private CartService cartService;
    private static final Logger LOGGER = LogManager.getLogger(CartController.class);

    @RequestMapping(method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    public List<Cart> getAll() {
        return cartService.getAll();
    }

    @GetMapping(value = "/user/{userID}")
    @ResponseStatus(value = HttpStatus.OK)
    public List<Cart> getAllByUserId(@PathVariable int userID, HttpServletResponse response) {
        try {
            LOGGER.info("All carts for user with id="+userID+" were found");
            return cartService.getAllByUserId(userID);
        } catch (DataNotFoundException e) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            LOGGER.error("Carts for user with id="+userID+" were not found", e);
            return null;
        }
    }
}

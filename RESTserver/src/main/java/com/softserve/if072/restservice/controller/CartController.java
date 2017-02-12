package com.softserve.if072.restservice.controller;

import com.softserve.if072.common.model.Cart;
import com.softserve.if072.restservice.service.CartService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * The CartController class is used to mapping requests for
 * cart resources
 *
 * @author Igor Kryviuk
 */
@RestController
@RequestMapping("/users/{userID}/carts")
public class CartController {
    @Autowired
    private CartService cartService;
    private static final Logger LOGGER = LogManager.getLogger(CartController.class);

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public List<Cart> getByUserId(@PathVariable int userID) {
        return cartService.getByUserId(userID);
    }

    @PostMapping()
    @ResponseStatus(value = HttpStatus.CREATED)
    public void insert(@RequestBody Cart cart) {
        cartService.insert(cart);
    }

    @PutMapping()
    @ResponseStatus(value = HttpStatus.OK)
    public void update(@RequestBody Cart cart) {
        cartService.update(cart);
    }

    @DeleteMapping()
    @ResponseStatus(value = HttpStatus.OK)
    public void delete(@RequestBody Cart cart) {
        cartService.delete(cart);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String dataNotFound(IllegalArgumentException e) {
        LOGGER.error(e.getMessage());
        return e.getMessage();
    }

    @ExceptionHandler(DataAccessException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String dataAccessException(DataAccessException e) {
        LOGGER.error(e.getMessage());
        return e.getMessage();
    }
}

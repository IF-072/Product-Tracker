package com.softserve.if072.restservice.controller;

import com.softserve.if072.common.model.Cart;
import com.softserve.if072.restservice.exception.DataNotFoundException;
import com.softserve.if072.restservice.service.CartService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * The CartController class is used to mapping requests for
 * cart resources
 *
 * @author Igor Kryviuk
 */
@RestController
@RequestMapping("/cart")
@PropertySource("classpath:message.properties")
public class CartController {
    @Autowired
    private CartService cartService;
    private static final Logger LOGGER = LogManager.getLogger(CartController.class);
    @Value("${cart.notFound}")
    private String cartNotFound;
    @Value("${cart.Found}")
    private String cartFound;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Cart> getAll() {
        return cartService.getAll();
    }

    @GetMapping("/user/{userID}")
    @ResponseStatus(HttpStatus.OK)
    public List<Cart> getAllByUserId(@PathVariable int userID) throws DataNotFoundException {
        List<Cart> carts = cartService.getAllByUserId(userID);
        if (CollectionUtils.isEmpty(carts)) {
            LOGGER.error(String.format(cartNotFound, userID));
            throw new DataNotFoundException(String.format(cartNotFound, userID));
        }
        LOGGER.info(String.format(cartFound, userID));
        return carts;
    }

    @PostMapping(value = "/")
    @ResponseStatus(value = HttpStatus.CREATED)
    public void insert(@RequestBody Cart cart) {

    }






    @ExceptionHandler(DataNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String dataNotFound(DataNotFoundException e) {
        return e.getMessage();
    }
}

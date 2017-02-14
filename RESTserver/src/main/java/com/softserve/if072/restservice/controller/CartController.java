package com.softserve.if072.restservice.controller;

import com.softserve.if072.common.model.Cart;
import com.softserve.if072.restservice.exception.DataNotFoundException;
import com.softserve.if072.restservice.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
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
public class CartController extends ExceptionHandlerController {
    @Autowired
    private CartService cartService;

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
    public void update(@RequestBody Cart cart) throws DataNotFoundException {
        cartService.update(cart);
    }

    @DeleteMapping()
    @ResponseStatus(value = HttpStatus.OK)
    public void delete(@RequestBody Cart cart) throws DataNotFoundException {
        cartService.delete(cart);
    }
}

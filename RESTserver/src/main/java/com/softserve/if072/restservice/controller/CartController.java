package com.softserve.if072.restservice.controller;

import com.softserve.if072.common.model.Cart;
import com.softserve.if072.common.model.dto.CartDTO;
import com.softserve.if072.restservice.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
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
 * The CartController class handles requests for cart resources
 *
 * @author Igor Kryviuk
 */
@RestController
@RequestMapping("api/users/{userId}/carts")
public class CartController {
    @Autowired
    private CartService cartService;

    /**
     * Handles requests for retrieving all cart records for current user
     *
     * @param userId - current user unique identifier
     * @return list of cart records or empty list
     */
    @PreAuthorize("#userId == authentication.user.id")
    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public List<Cart> getByUserId(@PathVariable int userId) {
        return cartService.getByUserId(userId);
    }

    /**
     * Handles requests for purchasing a product
     *
     * @param cartDTO - an object with required information for the product purchase
     */
    @PreAuthorize("#cartDTO != null && #cartDTO.userId == authentication.user.id")
    @PutMapping("/purchase")
    @ResponseStatus(value = HttpStatus.OK)
    public void productPurchase(@RequestBody CartDTO cartDTO) {
        cartService.productPurchase(cartDTO);
    }

    /**
     * Handles requests for deleting a product from the cart of current user
     *
     * @param productId - product unique identifier
     */
    @PreAuthorize("@cartSecurityService.hasPermissionToAccess(#productId)")
    @DeleteMapping("/{productId}")
    @ResponseStatus(value = HttpStatus.OK)
    public void deleteByProductId(@PathVariable int productId) {
        cartService.delete(productId);
    }

    @PostAuthorize("#cart != null && #cart.user != null && #cart.user.id == authentication.user.id")
    @GetMapping("/{productId}")
    @ResponseStatus(HttpStatus.OK)
    public Cart getByProductId(@PathVariable int productId) {
        Cart cart = cartService.getByProductId(productId);
        return cart;
    }

    @PreAuthorize("#cart != null && #cart.user != null && #cart.user.id == authentication.user.id")
    @PostMapping()
    @ResponseStatus(value = HttpStatus.CREATED)
    public void insert(@RequestBody Cart cart) {
        cartService.insert(cart);
    }

    @PreAuthorize("#cart != null && #cart.user != null && #cart.user.id == authentication.user.id")
    @PutMapping()
    @ResponseStatus(value = HttpStatus.OK)
    public void update(@RequestBody Cart cart) {
        cartService.update(cart);
    }
}

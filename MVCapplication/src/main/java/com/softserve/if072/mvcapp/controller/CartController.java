package com.softserve.if072.mvcapp.controller;

import com.softserve.if072.common.model.Cart;
import com.softserve.if072.common.model.dto.CartDTO;
import com.softserve.if072.mvcapp.service.CartService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * The CartController class handles requests for "/cart" and renders appropriate view
 *
 * @author Igor Kryviuk
 */
@Controller
@RequestMapping("/cart")
public class CartController{
    @Autowired
    CartService cartService;

    /**
     * Handles requests for getting all cart records for current user
     *
     * @param model - a map that will be handed off to the view for rendering the data to the client
     * @return string with appropriate view name
     */
    @GetMapping
    public String getCarts(Model model) {
        List<Cart> carts = cartService.getByUserId();
        if (CollectionUtils.isNotEmpty(carts)) {
            model.addAttribute("carts", carts);
            model.addAttribute("cartDTO", new CartDTO());
            return "cart";
        }
        return "emptyCart";
    }

    /**
     * Handles requests for purchasing a product
     *
     * @param cartDTO - an object with required information for the product purchase
     * @return string with appropriate view name
     */
    @PostMapping("/purchase")
    public String productPurchase(CartDTO cartDTO) {
        cartService.productPurchase(cartDTO);
        return "redirect: /cart/";
    }

    /**
     * Handles requests for deleting a product from the cart of current user
     * @param cartDTO - an object with required information for the product delete
     * @return string with appropriate view name
     */
    @GetMapping("/delete")
    public String deleteProductFromCart(CartDTO cartDTO) {
        cartService.deleteProductFromCart(cartDTO);
        return "redirect: /cart/";
    }
}

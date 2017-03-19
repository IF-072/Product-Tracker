package com.softserve.if072.mvcapp.controller;

import com.softserve.if072.common.model.ShoppingList;
import com.softserve.if072.mvcapp.dto.FormForCart;
import com.softserve.if072.mvcapp.service.GoShoppingPageService;
import com.softserve.if072.mvcapp.service.MessageService;
import com.softserve.if072.mvcapp.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

/**
 * The GoShoppingPagesController class handles requests
 * for function "Go shopping" and renders appropriate view.
 *
 * @author Roman Dyndyn
 */

@Controller
public class GoShoppingPagesController {

    private static final Logger LOGGER = LogManager.getLogger(GoShoppingPagesController.class);
    private final GoShoppingPageService goShoppingPageService;
    private final UserService userService;
    private final MessageService messageService;

    @Autowired
    public GoShoppingPagesController(final GoShoppingPageService goShoppingPageService, final UserService userService,
                                     final MessageService messageService) {
        this.goShoppingPageService = goShoppingPageService;
        this.userService = userService;
        this.messageService = messageService;
    }

    /**
     * Handles requests for getting all records of stores
     * that have product in shopping list for current user.
     *
     * @param model - a map that will be handed off to the view
     *              for rendering the data to the client
     * @return string with appropriate view name
     */
    @GetMapping("/goShoppingStores")
    public String getPageWithStores(final ModelMap model) {
        model.addAttribute("stores", goShoppingPageService.getStores(userService.getCurrentUser().getId()));
        return "goShoppingStores";
    }

    /**
     * Handles requests for getting all products records for current store.
     *
     * @param model - a map that will be handed off to the view
     *              for rendering the data to the client
     * @param store - store unique identifier
     * @return string with appropriate view name
     */
    @PostMapping("/goShoppingProducts")
    public String getProductList(final ModelMap model, @RequestParam("stores") final Integer store) {

        final Map<String, List<ShoppingList>> map = goShoppingPageService.getProducts(userService.getCurrentUser().getId(), store);
        if (map != null) {
            model.addAllAttributes(map)
                    .addAttribute("cartForm", new FormForCart(map.get("selected").size()));
        }

        return "goShoppingProducts";
    }

    /**
     * Handles requests for inserting all selected products in the cart.
     *
     * @param form   - class containing selected products
     * @param locale - locale of user
     * @return string with appropriate view name
     */
    @PostMapping("/addToCart")
    public String addToCart(@ModelAttribute("cartForm") final FormForCart form,
                            @CookieValue(value = "myLocaleCookie", required = false) final String locale) {
        form.setUser(userService.getCurrentUser());
        goShoppingPageService.addToCart(form);
        messageService.broadcast("goShopping.start", locale, form.getUserId(), form.getStoreName());
        return "redirect:/cart/";
    }

}

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
 * The GoShoppingPagesController class handles requests for function "Go shopping" and renders appropriate view
 *
 * @author Roman Dyndyn
 */

@Controller
public class GoShoppingPagesController {

    private static final Logger LOGGER = LogManager.getLogger(GoShoppingPagesController.class);
    private GoShoppingPageService goShoppingPageService;
    private UserService userService;
    private MessageService messageService;

    @Autowired
    public GoShoppingPagesController(GoShoppingPageService goShoppingPageService, UserService userService,
                                     MessageService messageService) {
        this.goShoppingPageService = goShoppingPageService;
        this.userService = userService;
        this.messageService = messageService;
    }

    @GetMapping("/goShoppingStores")
    public String getPageWithStores(ModelMap model) {
        model.addAttribute("stores", goShoppingPageService.getStores(userService.getCurrentUser().getId()));
        return "goShoppingStores";
    }


    @PostMapping("/goShoppingProducts")
    public String getProductList(ModelMap model, @RequestParam("stores") Integer store) {

        Map<String, List<ShoppingList>> map = goShoppingPageService.getProducts(userService.getCurrentUser().getId(), store);
        if (map != null) {
            model.addAllAttributes(map);
            model.addAttribute("cartForm", new FormForCart(map.get("selected").size()));
        }

        return "goShoppingProducts";
    }

    @PostMapping("/addToCart")
    public String addToCart(@ModelAttribute("cartForm") FormForCart form,
                            @CookieValue(value="myLocaleCookie", required = false) String locale) {
        form.setUser(userService.getCurrentUser());
        goShoppingPageService.addToCart(form);
        messageService.broadcast("goShopping.start", locale, form.getUserId(), form.getStoreName());
        return "redirect:/cart/";
    }

}

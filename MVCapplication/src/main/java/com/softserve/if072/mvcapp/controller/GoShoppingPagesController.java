package com.softserve.if072.mvcapp.controller;

import com.softserve.if072.common.model.Cart;
import com.softserve.if072.mvcapp.dto.FormForCart;
import com.softserve.if072.common.model.ShoppingList;
import com.softserve.if072.common.model.Store;
import com.softserve.if072.mvcapp.service.GoShoppingPageService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;



@Controller
public class GoShoppingPagesController extends BaseController {

    private static final Logger LOGGER = LogManager.getLogger(GoShoppingPagesController.class);
    private GoShoppingPageService goShoppingPageService;

    @Autowired
    public GoShoppingPagesController(GoShoppingPageService goShoppingPageService) {
        this.goShoppingPageService = goShoppingPageService;
    }

    @GetMapping("/goShoppingStores")
    public String getPageWithStores(ModelMap model) {
        model.addAttribute("stores", goShoppingPageService.getStores(getCurrentUser().getId()));
        return "goShoppingStores";
    }


    @PostMapping("/goShoppingProducts")
    public String getProductList(ModelMap model, @RequestParam("stores") Integer store) {

        Map<String, List<ShoppingList>> map = goShoppingPageService.getProducts(getCurrentUser().getId(), store);
        if (map != null) {
            model.addAllAttributes(map);
        }

        model.addAttribute("cartForm", new FormForCart(map.get("selected").size()));

        return "goShoppingProducts";
    }

    @PostMapping("/addToCart")
    public String addToCart(@ModelAttribute("cartForm") FormForCart form) {
        form.setUser(getCurrentUser());
        goShoppingPageService.addToCart(form);
        return "redirect:/cart/";
    }

}

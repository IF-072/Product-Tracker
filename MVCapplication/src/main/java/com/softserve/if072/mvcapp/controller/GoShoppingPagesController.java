package com.softserve.if072.mvcapp.controller;

import com.softserve.if072.common.model.Cart;
import com.softserve.if072.mvcapp.dto.FormForCart;
import com.softserve.if072.common.model.ShoppingList;
import com.softserve.if072.common.model.Store;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;



@Controller
public class GoShoppingPagesController extends BaseController {

    private static final Logger LOGGER = LogManager.getLogger(GoShoppingPagesController.class);

    @Value("${application.restGoShoppingURL}")
    private String goShoppingURL;

    @GetMapping("/goShoppingStores")
    public String getPageWithStores(ModelMap model) {
        int userId = getCurrentUser().getId();

        final String uri = goShoppingURL + "/stores/" + userId;
        RestTemplate restTemplate = getRestTemplate();
        List<Store> list = restTemplate.getForObject(uri, List.class);

        model.addAttribute("stores", list);
        return "goShoppingStores";

    }


    @PostMapping("/goShoppingProducts")
    public String getProductList(ModelMap model, @RequestParam("stores") Integer stores) {

        int userId = getCurrentUser().getId();

        final String uri = goShoppingURL + "/" + stores + "/products/" + userId;
        RestTemplate restTemplate = getRestTemplate();
        Map<String, List<ShoppingList>> map = restTemplate.getForObject(uri, Map.class);
        if (map != null) {
            model.addAllAttributes(map);
        }

        model.addAttribute("cartForm", new FormForCart(map.get("selected").size()));

        return "goShoppingProducts";
    }

    @PostMapping("/addToCart")
    public String addToCart(@ModelAttribute("cartForm") FormForCart form) {
        form.setUser(getCurrentUser());

        final String uri = goShoppingURL + "/cart";
        RestTemplate restTemplate = getRestTemplate();
        form.removeUncheked();
        restTemplate.postForObject(uri, form.getCarts(), Cart.class);
        return "redirect:/cart/";
    }

}

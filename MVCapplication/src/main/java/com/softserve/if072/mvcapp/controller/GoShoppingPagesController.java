package com.softserve.if072.mvcapp.controller;

import com.softserve.if072.common.model.Cart;
import com.softserve.if072.common.model.FormForCart;
import com.softserve.if072.common.model.Product;
import com.softserve.if072.common.model.Store;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import java.util.*;


@Controller
@PropertySource(value = {"classpath:application.properties"})
public class GoShoppingPagesController {

    private static final Logger LOGGER = LogManager.getLogger(GoShoppingPagesController.class);

    @Value("${application.restGoShoppingURL}")
    private String goShoppingURL;

    @GetMapping("/goShoppingStores")
    public String getPageWithStores(ModelMap model, @RequestParam(value = "user_id", required = false) Integer userId) {
        if (userId == null)
            userId = 2;

        final String uri = goShoppingURL + "/stores/" + userId;
        RestTemplate restTemplate = new RestTemplate();
        List<Store> list = restTemplate.getForObject(uri, List.class);

        model.addAttribute("list", list);
        return "goShoppingStores";

    }


    @PostMapping("/goShoppingProducts")
    public String getProductList(ModelMap model, @RequestParam("stores") Integer stores[], @RequestParam(value = "userId", required = false) Integer userId) {
        if (userId == null)
            userId = 2;

        MultiValueMap<String, Integer[]> params = new LinkedMultiValueMap<String, Integer[]>();
        params.set("stores", stores);

        final String uri = goShoppingURL + "/products/" + userId;
        RestTemplate restTemplate = new RestTemplate();
        Map<String, List<Product>> map = restTemplate.postForEntity(uri, params, Map.class).getBody();
        model.addAllAttributes(map);

        model.addAttribute("cartForm", new FormForCart(map.get("selected").size()));

        return "goShoppingProducts";
    }

    @PostMapping("/addToCart")
    public String addToCart(@ModelAttribute("cartForm") FormForCart form) {
        System.out.println(form);
        return "redirect:/storage";
    }

}

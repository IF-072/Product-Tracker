package com.softserve.if072.mvcapp.controller;

import com.softserve.if072.common.model.FormForCart;
import com.softserve.if072.common.model.Product;
import com.softserve.if072.common.model.Store;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.CollectionUtils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;



@Controller
@PropertySource(value = {"classpath:application.properties"})
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

        model.addAttribute("list", list);
        return "goShoppingStores";

    }


    @PostMapping("/goShoppingProducts")
    public String getProductList(ModelMap model, @RequestParam("stores") Integer stores[]) {

        int userId = getCurrentUser().getId();

        MultiValueMap<String, Integer[]> params = new LinkedMultiValueMap<String, Integer[]>();
        params.set("stores", stores);

        final String uri = goShoppingURL + "/products/" + userId;
        RestTemplate restTemplate = getRestTemplate();
        Map<String, List<Product>> map = restTemplate.postForEntity(uri, params, Map.class).getBody();
        if (!CollectionUtils.isEmpty(map)) {
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
        restTemplate.postForObject(uri, form, FormForCart.class);
        return "redirect:/storage";
    }

}

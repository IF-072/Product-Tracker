package com.softserve.if072.mvcapp.controller;

import com.softserve.if072.common.model.Product;
import com.softserve.if072.common.model.ShoppingList;
import com.softserve.if072.common.model.Store;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;


@Controller
@PropertySource(value = {"classpath:application.properties"})
public class GoShoppingPagesController {

    @Value("${application.restShoppingListURL}")
    private String shoppingListURL;

    @Value("${application.restGoShoppingURL}")
    private String goShoppingURL;

    private static final Logger LOGGER = LogManager.getLogger(StoragePageController.class);

    @GetMapping("/goShoppingStores")
    public ModelAndView getPageWithStores(@RequestParam(value = "user_id", required = false) Integer userId) {
        if (userId == null)
            userId = 2;

        ModelAndView model = new ModelAndView("goShoppingStores");
        final String uri = new String(goShoppingURL + "/" + userId);
        RestTemplate restTemplate = new RestTemplate();
        List<Store> list = restTemplate.getForObject(uri, List.class);

        model.addObject("list", list);
        return model;

    }

}

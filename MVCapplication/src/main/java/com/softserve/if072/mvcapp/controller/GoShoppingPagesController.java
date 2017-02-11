package com.softserve.if072.mvcapp.controller;

import com.softserve.if072.common.model.Store;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import java.util.List;


@Controller
@PropertySource(value = {"classpath:application.properties"})
public class GoShoppingPagesController {

    private static final Logger LOGGER = LogManager.getLogger(StoragePageController.class);

    @Value("${application.restShoppingListURL}")
    private String shoppingListURL;

    @Value("${application.restGoShoppingURL}")
    private String goShoppingURL;

    @GetMapping("/goShoppingStores")
    public String getPageWithStores(ModelMap model, @RequestParam(value = "user_id", required = false) Integer userId) {
        if (userId == null)
            userId = 2;

        final String uri = new String(goShoppingURL + "/" + userId);
        RestTemplate restTemplate = new RestTemplate();
        List<Store> list = restTemplate.getForObject(uri, List.class);

        model.addAttribute("list", list);
        return "goShoppingStores";

    }

}

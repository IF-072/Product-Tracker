package com.softserve.if072.mvcapp.controller;

import com.softserve.if072.common.model.Store;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *  The class contains methods that handle the http requests from the stores`s page
 *
 * @author Nazar Vynnyk
 */

@Controller
@RequestMapping("/stores")
@PropertySource(value = {"classpath:application.properties"})
public class StorePageController {

    public static final Logger LOGGER = LogManager.getLogger(StorePageController.class);

    @Value("${application.restStoreURL}")
    private String storeUrl;

    @GetMapping("/")
    public String getAllStoresByUserId(Model model) {

        int userId =1;

        final String uri = new String(storeUrl + "/user/{userId}");
        Map<String, Integer> param = new HashMap<String, Integer>();
        param.put("userId", userId);

        RestTemplate restTemplate = new RestTemplate();
        List<Store> stores = restTemplate.getForObject(uri, List.class, param);

        model.addAttribute("stores", stores);
        return "allStores";
    }

    @GetMapping("/add")
    public String addStore (Model model){
        model.addAttribute("store", new Store());
        return "addStore";
    }

}

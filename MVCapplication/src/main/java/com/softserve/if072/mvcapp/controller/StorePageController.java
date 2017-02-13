package com.softserve.if072.mvcapp.controller;


import com.softserve.if072.common.model.Store;
import com.softserve.if072.common.model.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *  The class contains methods that handle the http requests from the stores`s page
 *
 * @author Nazar Vynnyk
 */

@Controller
@PropertySource(value = {"classpath:application.properties"})
public class StorePageController {

    public static final Logger LOGGER = LogManager.getLogger(StorePageController.class);

    @Value("${application.restStoreURL}")
    private String storeUrl;

    @GetMapping("/stores/")
    public String getAllStoresByUserId(Model model) {

        int userId =1;
try {
    final String uri = storeUrl + "/user/{userId}";
    Map<String, Integer> param = new HashMap<String, Integer>();
    param.put("userId", userId);

    RestTemplate restTemplate = new RestTemplate();
    List<Store> stores = restTemplate.getForObject(uri, List.class, param);

    model.addAttribute("stores", stores);
      LOGGER.info(String.format("Stores of user with id %d were found", userId));
    return "allStores";
}
catch (Exception e){
    System.out.println(Arrays.toString(e.getStackTrace()));
    return "redirect:/home";

}
    }

    @GetMapping("/addStore")
    public String addStore (Model model){
        model.addAttribute("store", new Store());
        LOGGER.info("Adding Store");
        return "addStore";
    }

    @PostMapping(value = "/addStore")
    public String addStore(@ModelAttribute("store") Store store) {
        final String uri = storeUrl + "/";
        RestTemplate restTemplate = new RestTemplate();

        try {
        User user = new User();
        user.setId(1);
        store.setUser(user);
        restTemplate.postForObject(uri, store, Store.class);
            LOGGER.info(String.format("Store of user %d was added", user.getId()));
            return "redirect:/stores/";

        } catch (HttpClientErrorException e) {
           return "addStore";
        }
    }

    @GetMapping("/stores/storeProducts")
    public String getAllProductsByStoreId(@RequestParam("storeId") String storeId, ModelMap model) {

        final String uri = storeUrl + "/{storeId}/storeProducts/{userId}";
        Integer userId = 1;
        try {
            Map<String, Integer> param = new HashMap<String, Integer>();
            param.put("storeId", Integer.parseInt(storeId));
            param.put("userId", userId);

            RestTemplate restTemplate = new RestTemplate();
            List products = restTemplate.getForObject(uri, List.class, param);

            model.addAttribute("products", products);
            LOGGER.info(String.format("Products from store %s were found", storeId));

            return "product";
        }

        catch (Exception e){
            LOGGER.error(String.format("Products user %d from store %s were not found", userId, storeId));
            return "redirect:/stores/";
        }

    }
}

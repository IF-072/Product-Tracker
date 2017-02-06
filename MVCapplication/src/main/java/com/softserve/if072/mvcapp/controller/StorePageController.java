package com.softserve.if072.mvcapp.controller;

import com.softserve.if072.common.model.Store;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Nazar Vynnyk
 */

@Controller
@RequestMapping("/stores")
public class StorePageController {


    @GetMapping("/user/{userId}")
    public String getAllStoresByUserId(@PathVariable("userId") int userId, Model model) {

        final String uri = "http://localhost:8080/server/stores/user/{userId}";
        Map<String, Integer> param = new HashMap<String, Integer>();
        param.put("userId", userId);

        RestTemplate restTemplate = new RestTemplate();

        Store[] result = restTemplate.getForObject(uri, Store[].class, param);
        List<Store> stores = Arrays.asList(result);

        model.addAttribute("stores", stores);
        return "allStores";
    }

    @GetMapping("/")
    public String addStore (Model model){
        model.addAttribute("store", new Store());
        return "addStore";
    }





//    @PostMapping("/")
//    public String addStore(@ModelAttribute("Store") Store store) {
//
//        final String uri = "http://localhost:8080/server/stores/";
//
//        RestTemplate restTemplate = new RestTemplate();
//        restTemplate.postForObject(uri, store, Store.class);
//
//        return "redirect:/stores/user/{userId}";
//
//    }

}

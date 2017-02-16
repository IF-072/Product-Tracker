package com.softserve.if072.mvcapp.controller;

import com.softserve.if072.common.model.Product;
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
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The class contains methods that handle the http requests from the stores`s page
 *
 * @author Nazar Vynnyk
 */

@Controller
@PropertySource(value = {"classpath:application.properties"})
public class StorePageController extends BaseController {

    public static final Logger LOGGER = LogManager.getLogger(StorePageController.class);

    private int idStore;
    @Value("${application.restStoreURL}")
    private String storeUrl;

    @Value("${application.restProductURL}")
    private String productUrl;

    @Value("${service.user.current}")
    private String getCurrentUser;

    @GetMapping("/stores/")
    public String getAllStoresByUserId(Model model) {
        final String uri = storeUrl + "/user/{userId}";
        RestTemplate restTemplate = getRestTemplate();
        User user = restTemplate.getForObject(getCurrentUser, User.class);
        int userId = user.getId();

        try {
            Map<String, Integer> param = new HashMap<>();
            param.put("userId", userId);
            List stores = restTemplate.getForObject(uri, List.class, param);
            model.addAttribute("stores", stores);
            LOGGER.info(String.format("Stores of user with id %d were found", userId));

            return "allStores";

        } catch (Exception e) {
            LOGGER.error(Arrays.toString(e.getStackTrace()));
            return "redirect:/home";
        }
    }

    @GetMapping("/addStore")
    public String addStore(Model model) {
        model.addAttribute("store", new Store());
        LOGGER.info("Adding Store");
        return "addStore";
    }

    @PostMapping(value = "/addStore")
    public String addStore(@ModelAttribute("store") Store store) {
        final String uri = storeUrl + "/";
        RestTemplate restTemplate = getRestTemplate();
        User user = restTemplate.getForObject(getCurrentUser, User.class);
        int userId = user.getId();

        try {
            store.setUser(user);
            store.setEnabled(true);
            restTemplate.postForObject(uri, store, Store.class);
            LOGGER.info(String.format("Store of user %d was added", user.getId()));

            return "redirect:/stores/";

        } catch (Exception e) {
            LOGGER.error("Stores not added");
            return "addStore";
        }
    }

    @GetMapping("/stores/storeProducts")
    public String getAllProductsByStoreId(@RequestParam("storeId") String storeId, ModelMap model) {
        final String uri = storeUrl + "/{storeId}/storeProducts/{userId}";
        RestTemplate restTemplate = getRestTemplate();
        User user = restTemplate.getForObject(getCurrentUser, User.class);
        int userId = user.getId();

        try {
            Map<String, Integer> param = new HashMap<>();
            param.put("storeId", Integer.parseInt(storeId));
            param.put("userId", userId);
            List products = restTemplate.getForObject(uri, List.class, param);
            model.addAttribute("products", products);
            LOGGER.info(String.format("Products from store %s were found", storeId));

            return "product";

        } catch (Exception e) {
            LOGGER.error(String.format("Products user %d from store %s were not found", userId, storeId));
            return "redirect:/stores/";
        }
    }

    @GetMapping("/addProductsToStore")
    public String addProductsToStore(ModelMap model) {
        final String uri = storeUrl + "/user/{userId}";
        final String productUri = productUrl + "/user/{userId}";
        RestTemplate restTemplate = getRestTemplate();
        User user = restTemplate.getForObject(getCurrentUser, User.class);
        int userId = user.getId();

        try {
            Map<String, Integer> param = new HashMap<>();
            param.put("userId", userId);
            model.addAttribute("myStore", new Store());

            Store[] storeResult = restTemplate.getForObject(uri, Store[].class, param);
            List<Store> stores = Arrays.asList(storeResult);
            Product[] productResult = restTemplate.getForObject(productUri, Product[].class, param);
            List<Product> products = Arrays.asList(productResult);

            model.addAttribute("stores", stores);
            model.addAttribute("products", products);
            LOGGER.info(String.format("Stores and products of user %d found", userId));

            return "addProductsToStore";

        } catch (Exception e) {
            LOGGER.error("Stores and products not found");
            return "redirect:/stores/";
        }
    }

    @PostMapping(value = "/addProductsToStore")
    public String addProductsToStore(@ModelAttribute("newStore") Store store) {

        return "redirect:/stores/";
    }

    @PostMapping(value = "/stores/delStore")
    public String deleteStore(@RequestParam("storeId") int storeId) {
        final String uri = storeUrl + "/{storeId}";
        RestTemplate restTemplate = getRestTemplate();

        try {
            Map<String, Integer> param = new HashMap<>();
            param.put("storeId", storeId);
            restTemplate.put(uri, Store.class, param);
            LOGGER.info(String.format("Store with id %d was deleted", storeId));

            return "redirect:/stores/";

        } catch (Exception e) {
            LOGGER.error(String.format("Store with id %d was not deleted", storeId));
            return "redirect:/stores/";
        }
    }

    @GetMapping("/editStore")
    public String editStore(@RequestParam("storeId") String storeId, ModelMap model) {
        final String uri = storeUrl + "/{storeId}";
        RestTemplate restTemplate = getRestTemplate();
        idStore = Integer.parseInt(storeId);

        try {
            Map<String, Integer> param = new HashMap<>();
            param.put("storeId", Integer.parseInt(storeId));
            Store store = restTemplate.getForObject(uri, Store.class, param);
            model.addAttribute("store", store);
            LOGGER.info("Editing Store         " + idStore);
            return "editStore";

        } catch (Exception e) {
            LOGGER.error(String.format("Store with id %d is not possible to edit", storeId));
            return "redirect:/stores/";
        }
    }

    @PostMapping("/editStore")
    public String editStore(@ModelAttribute("store") Store store) {
        store.setId(idStore);
        int storeId = store.getId();
        final String uri = storeUrl + "/update";
        RestTemplate restTemplate = getRestTemplate();
        User user = restTemplate.getForObject(getCurrentUser, User.class);
        store.setUser(user);

        try {
            restTemplate.put(uri, store, Store.class);
            LOGGER.info(String.format("Store with id %d was updated", store.getId()));
            return "redirect:/stores/";

        } catch (Exception e) {
            LOGGER.error(String.format("Store with id %d was not updated", store.getId()));
            return "redirect:/stores/";
        }
    }

}

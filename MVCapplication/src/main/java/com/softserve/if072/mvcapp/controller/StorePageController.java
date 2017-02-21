package com.softserve.if072.mvcapp.controller;

import com.softserve.if072.common.model.Product;
import com.softserve.if072.common.model.Store;
import com.softserve.if072.common.model.User;
import com.softserve.if072.mvcapp.dto.ProductsWrapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
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

        Map<String, Integer> param = new HashMap<>();
        param.put("userId", userId);
        List stores = restTemplate.getForObject(uri, List.class, param);
        model.addAttribute("stores", stores);
        LOGGER.info(String.format("Stores of user with id %d were found", userId));

        return "allStores";
    }

    @GetMapping("/addStore")
    public String addStore(Model model) {
        model.addAttribute("store", new Store());
        LOGGER.info("Adding Store");
        return "addStore";
    }

    @PostMapping(value = "/addStore")
    public String addStore(@Validated @ModelAttribute("store") Store store, BindingResult result,
                           Model model, HttpServletResponse httpServletResponse) {

        if (result.hasErrors()) {
            model.addAttribute("errorMessages", result.getFieldErrors());
            return "addStore";
        }

        final String uri = storeUrl + "/";
        RestTemplate restTemplate = getRestTemplate();
        User user = restTemplate.getForObject(getCurrentUser, User.class);
        int userId = user.getId();

        store.setUser(user);
        store.setEnabled(true);
        restTemplate.postForObject(uri, store, Store.class);
        LOGGER.info(String.format("Store of user %d was added", user.getId()));

        return "redirect:/stores/";
    }

    @GetMapping("/stores/storeProducts")
    public String getAllProductsByStoreId(@RequestParam("storeId") String storeId, ModelMap model) {
        final String uri = storeUrl + "/{storeId}/storeProducts/{userId}";
        RestTemplate restTemplate = getRestTemplate();
        User user = restTemplate.getForObject(getCurrentUser, User.class);
        int userId = user.getId();

        Map<String, Integer> param = new HashMap<>();
        param.put("storeId", Integer.parseInt(storeId));
        param.put("userId", userId);
        List products = restTemplate.getForObject(uri, List.class, param);
        model.addAttribute("products", products);
        LOGGER.info(String.format("Products from store %s were found", storeId));

        return "product";
    }

    @GetMapping("/addProductsToStore")
    public String addProductsToStore(@RequestParam("storeId") String storeId, ModelMap model) {

        final String productsUri = storeUrl + "/{storeId}/notMappedProducts/{userId}";
        final String storeUri = storeUrl + "/{storeId}";

        RestTemplate restTemplate = getRestTemplate();
        User user = restTemplate.getForObject(getCurrentUser, User.class);
        int userId = user.getId();
        int returnedStoreId = Integer.parseInt(storeId);

        Map<String, Integer> param = new HashMap<>();
        param.put("storeId", returnedStoreId);
        Store myStore = restTemplate.getForObject(storeUri, Store.class, param);

        param.put("userId", userId);
        ResponseEntity<List<Product>> productResult = restTemplate.exchange(productsUri, HttpMethod.GET, null,
                new ParameterizedTypeReference<List<Product>>() {
                }, param);
        List<Product> products = productResult.getBody();

        model.addAttribute("myStore", myStore);

        ProductsWrapper productsWrapper = new ProductsWrapper(new ArrayList<>(products.size()));
        model.addAttribute("products", products);
        model.addAttribute("wrapedProducts", productsWrapper);
        LOGGER.info(String.format("Products of user %d in store %s found", userId, storeId));

        return "addProductsToStore";
    }

    @PostMapping("/addProductsToStore")
    public String addProductsToStore(@RequestParam("storeId") String storeId, @ModelAttribute("wrapedProducts")
            ProductsWrapper wrapedProducts, BindingResult result) {
        RestTemplate restTemplate = getRestTemplate();
        User user = restTemplate.getForObject(getCurrentUser, User.class);
        int userId = user.getId();
        final String uri = storeUrl + "/manyProducts/" + userId + "/" + storeId;

        List<Integer> productsId = wrapedProducts.getProducts();
        if (productsId.isEmpty()) {
            return "redirect:/stores/";
        }
        restTemplate.postForObject(uri, productsId, List.class);
        LOGGER.info(String.format("Products of user %d added in store %s ", userId, storeId));

        return "redirect:/stores/";
    }

    @PostMapping(value = "/stores/delStore")
    public String deleteStore(@RequestParam("storeId") int storeId) {
        final String uri = storeUrl + "/{storeId}";
        final String delUri = storeUrl + "/delStore";

        RestTemplate restTemplate = getRestTemplate();
        Map<String, Integer> param = new HashMap<>();
        param.put("storeId", storeId);
        Store store = restTemplate.getForObject(uri, Store.class, param);

        restTemplate.put(delUri, store, Store.class);
        LOGGER.info(String.format("Store with id %d was deleted", storeId));

        return "redirect:/stores/";
    }

    @GetMapping("/editStore")
    public String editStore(@RequestParam("storeId") String storeId, ModelMap model) {
        final String uri = storeUrl + "/{storeId}";
        RestTemplate restTemplate = getRestTemplate();
        int idStore = Integer.parseInt(storeId);
        Map<String, Integer> param = new HashMap<>();
        param.put("storeId", idStore);
        Store store = restTemplate.getForObject(uri, Store.class, param);
        model.addAttribute("store", store);
        LOGGER.info("Editing Store" + idStore);
        return "editStore";
    }

    @PostMapping("/editStore")
    public String editStore(@Validated @ModelAttribute("store") Store store, @RequestParam("storeId") String storeId,
                            BindingResult result, Model model, HttpServletResponse httpServletResponse) {

        if (result.hasErrors()) {
            model.addAttribute("errorMessages", result.getFieldErrors());
            return "editStore";
        }
        store.setId(Integer.parseInt(storeId));
        final String uri = storeUrl + "/update";
        RestTemplate restTemplate = getRestTemplate();
        User user = restTemplate.getForObject(getCurrentUser, User.class);
        store.setUser(user);

        restTemplate.put(uri, store, Store.class);
        LOGGER.info(String.format("Store with id %d was updated", store.getId()));
        return "redirect:/stores/";
    }

}

package com.softserve.if072.mvcapp.controller;

import com.softserve.if072.common.model.Product;
import com.softserve.if072.common.model.Store;
import com.softserve.if072.common.model.User;
import com.softserve.if072.mvcapp.dto.ProductsWrapper;
import com.softserve.if072.mvcapp.service.StorePageService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    StorePageService storePageService;


    public static final Logger LOGGER = LogManager.getLogger(StorePageController.class);

    @Value("${application.restStoreURL}")
    private String storeUrl;

    @Value("${application.restProductURL}")
    private String productUrl;

    @Value("${service.user.current}")
    private String getCurrentUser;

    /**
     * Method for mapping on default store url.
     * shows the view page with user stores. On view page user can add a new store, edit store, delete it, add
     * products to store, and get page with all products that are offered in store
     *
     * @param model contains data for view
     * @return store view page that contains list of stores
     */
    @GetMapping("/stores/")
    public String getAllStoresByUserId(Model model) {
        model.addAttribute("stores", storePageService.getAllStoresByUserId(getCurrentUser().getId()));

        return "allStores";
    }

    /**
     * Method for mapping on adding store url.
     * shows the view page with form of fields for adding information about store.
     *
     * @param model contains empty store entity
     * @return store view page that contains form for creating store
     */
    @GetMapping("/addStore")
    public String addStore(Model model) {
        model.addAttribute("store", new Store());
        LOGGER.info("Adding Store");
        return "addStore";
    }

    /**
     * Method gets completed store entity and  sends a request to the rest-service to update Database
     *
     * @param store completed store entity from method addStore with @GetMapping annotation
     * @param model for sending errorMessage if the store name is empty
     * @return redirect to the store view page that contains list of stores
     */
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

    /**
     * Method for mapping on store products url.
     * shows the view page with products of current store and information about they.
     *
     * @param storeId store which products will be shown
     * @param model   contains data for view
     * @return view page that contains all products that are offered in store
     */
    @GetMapping("/stores/storeProducts")
    public String getAllProductsByStoreId(@RequestParam("storeId") Integer storeId, ModelMap model) {
        final String uri = storeUrl + "/{storeId}/storeProducts/{userId}";
        final String storeUri = storeUrl + "/{storeId}";

        RestTemplate restTemplate = getRestTemplate();
        User user = restTemplate.getForObject(getCurrentUser, User.class);
        int userId = user.getId();

        Map<String, Integer> param = new HashMap<>();
        param.put("storeId", storeId);
        Store store = restTemplate.getForObject(storeUri, Store.class, param);
        param.put("userId", userId);
        List products = restTemplate.getForObject(uri, List.class, param);
        model.addAttribute("store", store);
        model.addAttribute("products", products);

        LOGGER.info(String.format("Products from store %d were found", storeId));

        return "productsInStore";
    }

    /**
     * Method for mapping on products url.
     * shows the view page with all products of current user that are not added to this store. User can chose
     * products which will be added to the store
     *
     * @param storeId id of store where we add products
     * @param model   contains data for view
     * @return view page that contains all products that are not offered in store yet
     */
    @GetMapping("/addProductsToStore")
    public String addProductsToStore(@RequestParam("storeId") Integer storeId, ModelMap model) {

        final String productsUri = storeUrl + "/{storeId}/notMappedProducts/{userId}";
        final String storeUri = storeUrl + "/{storeId}";

        RestTemplate restTemplate = getRestTemplate();
        User user = restTemplate.getForObject(getCurrentUser, User.class);
        int userId = user.getId();

        Map<String, Integer> param = new HashMap<>();
        param.put("storeId", storeId);
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
        LOGGER.info(String.format("Products of user %d in store %d found", userId, storeId));

        return "addProductsToStore";
    }

    /**
     * Method gets wrapedProducts object which contains list of product id. This all products will be added to
     * store and saved in Database. Method  sends a request to the rest-service to update Database
     *
     * @param storeId        id of store where we add products
     * @param wrapedProducts list of product id that will be added to current store
     * @return redirect to the store view page that contains list of stores
     */
    @PostMapping("/addProductsToStore")
    public String addProductsToStore(@RequestParam("storeId") Integer storeId, @ModelAttribute("wrapedProducts")
            ProductsWrapper wrapedProducts) {

        RestTemplate restTemplate = getRestTemplate();
        User user = restTemplate.getForObject(getCurrentUser, User.class);
        int userId = user.getId();
        final String uri = storeUrl + "/manyProducts/" + userId + "/" + storeId;

        List<Integer> productsId = wrapedProducts.getProducts();
        if (productsId.isEmpty()) {
            return "redirect:/stores/";
        }
        restTemplate.postForObject(uri, productsId, List.class);
        LOGGER.info(String.format("Products of user %d added in store %d ", userId, storeId));

        return "redirect:/stores/storeProducts?storeId=" + storeId;
    }

    /**
     * Method for deleting product from store
     *
     * @param storeID   store where product will be deleted
     * @param productID product that will be deleted from current store
     * @return redirect to the store products view page that contains list of products from this store
     */
    @PostMapping(value = "/stores/delProduct")
    public String deleteProductFromStore(@RequestParam("storeID") int storeID, @RequestParam("productID") int
            productID) {

        final String productUri = storeUrl + "/{storeId}/products/{productId}";
        final String delUri = storeUrl + "/" + storeID + "/products";

        RestTemplate restTemplate = getRestTemplate();
        Map<String, Integer> param = new HashMap<>();
        param.put("storeId", storeID);
        param.put("productId", productID);
        Product product = restTemplate.getForObject(productUri, Product.class, param);
        restTemplate.postForObject(delUri, product, Product.class);
        LOGGER.info(String.format("Product %d from tore with id %d was deleted", productID, storeID));

        return "redirect:/stores/storeProducts";
    }

    /**
     * Method for deleting store
     *
     * @param storeId store that will be deleted
     * @return redirect to the store view page that contains list of stores
     */
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

    /**
     * Method for mapping on page for editing store
     *
     * @param storeId store that will be changed
     * @param model   contains data of store for view
     * @return store view page that contains form for editing store
     */
    @GetMapping("/editStore")
    public String editStore(@RequestParam("storeId") Integer storeId, ModelMap model) {
        final String uri = storeUrl + "/{storeId}";
        RestTemplate restTemplate = getRestTemplate();
        Map<String, Integer> param = new HashMap<>();
        param.put("storeId", storeId);
        Store store = restTemplate.getForObject(uri, Store.class, param);
        model.addAttribute("store", store);
        LOGGER.info("Editing Store" + storeId);
        return "editStore";
    }

    /**
     * Method gets edited store and sends a request to the rest-service to update database
     *
     * @param store   completed store that will be changed
     * @param storeId id of store that will be changed
     * @param model   for sending errorMessage if the store name is empty
     * @return redirect to the store view page that contains list of stores
     */
    @PostMapping("/editStore")
    public String editStore(@Validated @ModelAttribute("store") Store store, BindingResult result, @RequestParam
            ("storeId") Integer storeId, Model model, HttpServletResponse httpServletResponse) {

        if (result.hasErrors()) {
            model.addAttribute("errorMessages", result.getFieldErrors());
            return "editStore";
        }
        store.setId(storeId);
        final String uri = storeUrl + "/update";
        RestTemplate restTemplate = getRestTemplate();
        User user = restTemplate.getForObject(getCurrentUser, User.class);
        store.setUser(user);

        restTemplate.put(uri, store, Store.class);
        LOGGER.info(String.format("Store with id %d was updated", storeId));
        return "redirect:/stores/";
    }

}

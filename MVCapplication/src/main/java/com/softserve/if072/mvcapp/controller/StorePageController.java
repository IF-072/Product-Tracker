package com.softserve.if072.mvcapp.controller;

import com.softserve.if072.common.model.Product;
import com.softserve.if072.common.model.Store;
import com.softserve.if072.mvcapp.dto.ProductsWrapper;
import com.softserve.if072.mvcapp.service.StorePageService;
import com.softserve.if072.mvcapp.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * The class contains methods that handle the http requests from the stores`s page
 *
 * @author Nazar Vynnyk
 */

@Controller
public class StorePageController {

    private static final Logger LOGGER = LogManager.getLogger(StorePageController.class);

    private StorePageService storePageService;
    private UserService userService;

    @Value("${store.alreadyExist}")
    private String existMessage;


    @Autowired
    public StorePageController(StorePageService storePageService, UserService userService) {
        this.storePageService = storePageService;
        this.userService = userService;
    }

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
        int userId = userService.getCurrentUser().getId();
        model.addAttribute("stores", storePageService.getAllStoresByUserId(userId));
        LOGGER.info(String.format("Stores of user with id %d were found", userId));
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
        model.addAttribute("store", storePageService.addNewStore());
        LOGGER.info(String.format("Store of user %d is adding", userService.getCurrentUser().getId()));

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
                           Model model) {
        if (result.hasErrors()) {
            model.addAttribute("errorMessages", result.getFieldErrors());
            return "addStore";
        }

        if (storePageService.alreadyExist(store, userService.getCurrentUser())) {
            model.addAttribute("validMessage", existMessage);
            return "addStore";
        }
        if (storePageService.isDeleted(store, userService.getCurrentUser())) {
            model.addAttribute("store", storePageService.getStoreByNameAndUserId(store, userService.getCurrentUser()));
            return "dialogWindow";
        }
        storePageService.addStore(userService.getCurrentUser(), store);
        LOGGER.info(String.format("Store of user %d was added", userService.getCurrentUser().getId()));

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
    public String getAllProductsByStoreId(@RequestParam("storeId") int storeId, ModelMap model) {
        model.addAttribute("store", storePageService.getStoreById(storeId));
        model.addAttribute("products", storePageService.getAllProductsFromStore(storeId, userService.getCurrentUser().getId()));
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
     * @return view page that contains all products that are not represented in this store
     */
    @GetMapping("/addProductsToStore")
    public String addProductsToStore(@RequestParam("storeId") int storeId, ModelMap model) {

        model.addAttribute("myStore", storePageService.getStoreById(storeId));
        List<Product> products = storePageService.getNotMappedProducts(storeId, userService.getCurrentUser().getId());
        model.addAttribute("products", products);
        model.addAttribute("wrapedProducts", new ProductsWrapper(products.size()));
        LOGGER.info(String.format("NotMappedProducts in store %d found and shoved on display", storeId));

        return "addProductsToStore";
    }

    /**
     * Method for adding products to store
     *
     * @param storeId        id of store where we add products
     * @param wrapedProducts list of product id that will be added to current store
     * @return redirect to the store view page that contains list of stores
     */
    @PostMapping("/addProductsToStore")
    public String addProductsToStore(@RequestParam("storeId") int storeId, @ModelAttribute("wrapedProducts")
            ProductsWrapper wrapedProducts) {
        int userId = userService.getCurrentUser().getId();
        if (wrapedProducts.getProducts().isEmpty()) {
            LOGGER.info(String.format("No products have been chosen in store %d ", storeId));
            return String.format("redirect:/stores/storeProducts?storeId=%d", storeId);
        }
        storePageService.addProductsToStore(userId, storeId, wrapedProducts);
        LOGGER.info(String.format("Products of user %d added in store %d ", userId, storeId));

        return String.format("redirect:/stores/storeProducts?storeId=%d", storeId);
    }

    /**
     * Method for deleting product from store
     *
     * @param storeID   store where product will be deleted
     * @param productID product that will be deleted from current store
     * @return redirect to the store products view page that contains list of products from this store
     */
    @GetMapping(value = "/stores/delProduct")
    public String deleteProductFromStore(@RequestParam("storeID") Integer storeID, @RequestParam("productID") Integer
            productID) {
        storePageService.deleteProductFromStore(storeID, productID);
        LOGGER.info(String.format("Product %d from tore with id %d was deleted", productID, storeID));

        return String.format("redirect:/stores/storeProducts?storeId=%d", storeID);

    }

    /**
     * Method for deleting store
     *
     * @param storeId store that will be deleted
     * @return redirect to the store view page that contains list of stores
     */
    @GetMapping(value = "/stores/delStore")
    public String deleteStore(@RequestParam("storeId") int storeId) {
        storePageService.deleteStore(storeId);
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
    public String editStore(@RequestParam("storeId") int storeId, ModelMap model) {
        model.addAttribute("store", storePageService.getStoreById(storeId));
        LOGGER.info(String.format("Editing Store %d", storeId));

        return "editStore";
    }

    /**
     * Method gets edited store and sends a request to the rest-service to update database
     *
     * @param store   store that will replace old store
     * @param storeId id of store that will be changed
     * @param model   for sending errorMessage if the store name is empty
     * @return redirect to the store view page that contains list of stores
     */
    @PostMapping("/editStore")
    public String editStore(@Validated @ModelAttribute("store") Store store, BindingResult result, Model model,
                            @RequestParam ("storeId") int storeId) {
        if (result.hasErrors()) {
           model.addAttribute("store", storePageService.getStoreById(storeId));
            model.addAttribute("errorMessages", result.getFieldErrors());
            return "editStore";
        }

        if (storePageService.alreadyExist(store, userService.getCurrentUser())) {
            model.addAttribute("store", storePageService.getStoreById(storeId));
            model.addAttribute("validMessage", existMessage);
            return "editStore";
        }
        if (storePageService.isDeleted(store, userService.getCurrentUser())) {
            model.addAttribute("store", storePageService.getStoreByNameAndUserId(store, userService.getCurrentUser()));
            return "dialogWindow";
        }
        storePageService.editStore(store, storeId, userService.getCurrentUser());
        LOGGER.info(String.format("Store with id %d was updated", storeId));

        return "redirect:/stores/";
    }

    /**
     * Method for retrieving store
     *
     * @param storeId store that will be retrieved
     * @return redirect to the store view page that contains list of stores
     */
    @GetMapping("/retrieveStore")
    public String retrieveStore(@RequestParam("storeId") int storeId) {
        storePageService.retrieveStore(storeId);
        LOGGER.info(String.format("Store with id %d was successful retrieved", storeId));
        return "redirect:/stores/";
    }

}

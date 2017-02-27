package com.softserve.if072.mvcapp.controller;

import com.softserve.if072.common.model.Category;
import com.softserve.if072.common.model.Product;
import com.softserve.if072.common.model.Store;
import com.softserve.if072.common.model.Unit;
import com.softserve.if072.common.model.User;
import com.softserve.if072.mvcapp.dto.StoresInProduct;
import com.softserve.if072.mvcapp.service.ProductPageService;
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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The class contains methods that handle the http requests from the product's page
 *
 * @author Vitaliy Malisevych
 */

@Controller
@RequestMapping("/product")
@PropertySource(value = {"classpath:application.properties", "classpath:message.properties"})
public class ProductPageController extends BaseController {

    @Autowired
    private ProductPageService productPageService;

    @Value("${product.alreadyExists}")
    private String alreadyExistMessage;

    /**
     * Method returns product's page
     *
     * @param model model with data represented on page
     * @return product
     */

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String getProductPage(ModelMap model) {

        model.addAttribute("products", productPageService.getAllProducts(getCurrentUser().getId()));

        return "product";

    }

    /**
     * Method returns page for adding a product
     *
     * @param model model with data represented on page
     * @return addProduct
     */

    @RequestMapping(value = "/addProduct", method = RequestMethod.GET)
    public String addProduct(Model model){

        model.addAttribute("product", new Product());
        model.addAttribute("units", productPageService.getAllUnits());
        model.addAttribute("categories", productPageService.getAllCategories(getCurrentUser().getId()));

        return "addProduct";
    }

    /**
     * Method sends new product's data to REST service for adding product in DataBase
     *
     * @param model model with data represented on page
     * @return product
     */

    @RequestMapping(value = "/addProduct", method = RequestMethod.POST)
    public String addProduct(@Validated @ModelAttribute("product") Product product, BindingResult result,
                             Model model) {

        if (result.hasErrors()) {
            model.addAttribute("errorMessages", result.getFieldErrors());
            model.addAttribute("units", productPageService.getAllUnits());
            model.addAttribute("categories", productPageService.getAllCategories(getCurrentUser().getId()));

            return "addProduct";
        }

        //if product already exists
        if(productPageService.isAlreadyExist(product, getCurrentUser())) {
            model.addAttribute("errorMessage", alreadyExistMessage);
            model.addAttribute("units", productPageService.getAllUnits());
            model.addAttribute("categories", productPageService.getAllCategories(getCurrentUser().getId()));

            return "addProduct";
        }

        System.out.println("1");

        //if product is deleted
        if (productPageService.isDeleted(product, getCurrentUser())) {
            model.addAttribute("product", productPageService.getProductByNameAndUserId(product, getCurrentUser()));

            return "existsProduct";
        }

        System.out.println("2");

        productPageService.addProduct(product, getCurrentUser());

        return "redirect:/product/";
    }

    /**
     * Method returns page for editing product
     *
     * @param productId product to be edited
     * @param model model with data represented on page
     * @return editProduct
     */

    @RequestMapping(value = "/editProduct", method = RequestMethod.GET)
    public String editProduct(@RequestParam int productId, Model model) {

        model.addAttribute("units",  productPageService.getAllUnits());
        model.addAttribute("categories", productPageService.getAllCategories(getCurrentUser().getId()));
        model.addAttribute("product", productPageService.getProduct(productId));

        return "editProduct";
    }

    /**
     * Method sends editing product's data to REST service for adding product in DataBase
     *
     * @param product product to be edited
     * @param result contents errors for validation
     * @param model model with data represented on page
     * @return redirect to product's page
     */

    @RequestMapping(value = "/editProduct", method = RequestMethod.POST)
    public String editProduct(@Validated @ModelAttribute("product") Product product, BindingResult result,
                              Model model) {

        if (result.hasErrors()) {
            model.addAttribute("errorMessages", result.getFieldErrors());
            model.addAttribute("units", productPageService.getAllUnits());
            model.addAttribute("categories", productPageService.getAllCategories(getCurrentUser().getId()));

            return "editProduct";
        }

        productPageService.editProduct(product, getCurrentUser());

        return "redirect:/product/";
    }

    /**
     * Method sends data to REST service for deleting product
     *
     * @param productId product to be deleted
     * @return redirect to product's page
     */

    @RequestMapping(value = "/delProduct", method = RequestMethod.GET)
    public String delProduct(@RequestParam int productId){

        productPageService.delProduct(productId);

        return "redirect:/product/";

    }

    /**
     * Method returns page to represent stores where user can by this product
     *
     * @param productId product which user can buy in stores
     * @param model model with data represented on page
     * @return productInStores
     */

    @RequestMapping(value = "/stores", method = RequestMethod.GET)
    public String getStoresByProductId(@RequestParam int productId, Model model) {

        model.addAttribute("storesId", productPageService.getAllStoresId(getCurrentUser().getId()));
        model.addAttribute("storesInProduct", productPageService.getStoresInProduct(productId));
        model.addAttribute("product", productPageService.getProduct(productId));

        return "productInStores";

    }

    /**
     * Method sends data about stores where user can buy this product to the REST service
     * to write them into DataBase
     *
     * @param storesInProduct DTO that contains list of ids of checked stores
     * @param productId product which user can buy in checked stores
     * @return redirect to product's page
     */

    @RequestMapping(value = "/stores", method = RequestMethod.POST)
    public String getStoresByProductId(@ModelAttribute("storesInProduct") StoresInProduct storesInProduct,
                                       @RequestParam int productId) {

        productPageService.updateStoresInProduct(storesInProduct, productId);

        return "redirect:/product/";

    }

    /**
     * Method sends data to the REST service for restoring the product that had been deleted
     *
     * @param product product to restore
     * @return redirect to product's page
     */

    @RequestMapping(value = "/restore", method = RequestMethod.POST)
    public String restoreProduct(@ModelAttribute("product") Product product) {

        productPageService.restoreProduct(product);

        return "redirect:/product/";

    }


}
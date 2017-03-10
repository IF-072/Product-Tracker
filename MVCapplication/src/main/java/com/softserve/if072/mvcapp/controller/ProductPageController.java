package com.softserve.if072.mvcapp.controller;

import com.softserve.if072.common.model.Product;
import com.softserve.if072.mvcapp.dto.StoresInProduct;
import com.softserve.if072.mvcapp.service.ProductPageService;
import com.softserve.if072.mvcapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * The class contains methods that handle the http requests from the product's page
 *
 * @author Vitaliy Malisevych
 */
@Controller
@RequestMapping("/product")
@PropertySource(value = {"classpath:application.properties", "classpath:message.properties"})
public class ProductPageController {

    private ProductPageService productPageService;
    private UserService userService;

    @Value("{product.alreadyExists}")
    private String alreadyExistMessage;

    @Autowired
    public ProductPageController(ProductPageService productPageService, UserService userService) {
        this.productPageService = productPageService;
        this.userService = userService;
    }

    /**
     * Method returns product's page
     *
     * @param model model with data represented on page
     * @return product
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String getProductPage(ModelMap model) {

        model.addAttribute("products", productPageService.getAllProducts(userService.getCurrentUser().getId()));

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
        model.addAttribute("categories", productPageService.getAllCategories(userService.getCurrentUser().getId()));

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
            model.addAttribute("categories", productPageService.getAllCategories(userService.getCurrentUser().getId()));

            return "addProduct";
        }

        if(productPageService.isAlreadyExist(product, userService.getCurrentUser())) {
            model.addAttribute("errorMessage", alreadyExistMessage);
            model.addAttribute("units", productPageService.getAllUnits());
            model.addAttribute("categories", productPageService.getAllCategories(userService.getCurrentUser().getId()));

            return "addProduct";
        }

        if (productPageService.isDeleted(product, userService.getCurrentUser())) {
            model.addAttribute("product", productPageService.getProductByNameAndUserId(product, userService.getCurrentUser()));

            return "existsProduct";
        }

        productPageService.addProduct(product, userService.getCurrentUser());

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
        model.addAttribute("categories", productPageService.getAllCategories(userService.getCurrentUser().getId()));
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
            model.addAttribute("categories", productPageService.getAllCategories(userService.getCurrentUser().getId()));

            return "editProduct";
        }

        if(productPageService.isAlreadyExist(product, userService.getCurrentUser())) {
            model.addAttribute("message", alreadyExistMessage);
            model.addAttribute("units", productPageService.getAllUnits());
            model.addAttribute("categories", productPageService.getAllCategories(userService.getCurrentUser().getId()));

            return "editProduct";
        }

        if (productPageService.isDeleted(product, userService.getCurrentUser())) {
            model.addAttribute("product", productPageService.getProductByNameAndUserId(product, userService.getCurrentUser()));

            return "existsProduct";
        }

        productPageService.editProduct(product, userService.getCurrentUser());

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

        model.addAttribute("storesId", productPageService.getAllStoresId(userService.getCurrentUser().getId()));
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
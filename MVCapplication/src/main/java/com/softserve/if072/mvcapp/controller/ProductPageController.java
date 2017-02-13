package com.softserve.if072.mvcapp.controller;

import com.softserve.if072.common.model.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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
@PropertySource(value = {"classpath:application.properties"})
public class ProductPageController {

    @Value("${application.restProductURL}")
    private String productUrl;

    @Value("${application.restUnitURL}")
    private String unitUrl;

    @Value("${application.restCategoryURL}")
    private String categoryUrl;

    private Unit[] unitResult;
    //private Category[] categoryResult;

    @RequestMapping("/")
    public String getProductPage(ModelMap model) {

        int userId = 1;

        final String uri = new String(productUrl + "/user/{userId}");
        Map<String, Integer> param = new HashMap<String, Integer>();
        param.put("userId", userId);

        RestTemplate restTemplate = new RestTemplate();
        List<Product> products = restTemplate.getForObject(uri, List.class, param);

        model.addAttribute("products", products);

        return "product";
    }


    @GetMapping("/addProduct")
    public String addProduct(ModelMap model){

        int userId = 1;

        final String unitUri = new String(unitUrl + "/");
        final String categoryUri = new String(categoryUrl + "{userId}");

        model.addAttribute("product", new Product());
        //model.addAttribute("image", new Image());

        RestTemplate restTemplate = new RestTemplate();

        unitResult = restTemplate.getForObject(unitUri, Unit[].class);
        List<Unit> units = Arrays.asList(unitResult);

        Map<String, Integer> param = new HashMap<String, Integer>();
        param.put("userId", userId);
        Category[] categoryResult = restTemplate.getForObject(categoryUri, Category[].class, param);
        List<Category> categories = Arrays.asList(categoryResult);

        model.addAttribute("units", units);
        model.addAttribute("categories", categories);

        return "addProduct";
    }

    @RequestMapping(value = "/addProduct", method = RequestMethod.POST)
    public String addProduct(@ModelAttribute("product") Product product) {

        final String categoryByIdUri = new String(categoryUrl + "{userId}");

        final String uri = new String(productUrl +"/");

        User user = new User();
        user.setId(1);

        RestTemplate restTemplate = new RestTemplate();

        Map<String, Integer> param = new HashMap<String, Integer>();
        param.put("categoryId", product.getCategory().getId());
        //Category category = restTemplate.getForObject(categoryByIdUri, Category.class, param);

        product.setUser(user);
        product.setEnabled(true);
        product.setCategory(null);
        product.setImage(null);
        product.setUnit(unitResult[product.getUnit().getId()]);

        restTemplate.postForObject(uri, product, Product.class);

        return "redirect:/product/";
    }

    @RequestMapping(value = "/delProduct", method = RequestMethod.POST)
    public String delProduct(@RequestParam int productId){

        final String uri = new String(productUrl + "/{productId}");
        Map<String, Integer> param = new HashMap<String, Integer>();
        param.put("productId", productId);

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.delete(uri,param);

        return "redirect:/product/";
    }
}
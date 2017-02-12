package com.softserve.if072.mvcapp.controller;

import com.softserve.if072.common.model.Product;
import com.softserve.if072.common.model.Unit;
import com.softserve.if072.common.model.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

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

        final String uri = new String(unitUrl + "/");

        model.addAttribute("product", new Product());

        RestTemplate restTemplate = new RestTemplate();
        List<Unit> units = restTemplate.getForObject(uri, List.class);

        model.addAttribute("units", units);

        return "addProduct";
    }

    @RequestMapping(value = "/addProduct", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String addProduct(@RequestBody Product product){

        final String uri = new String(productUrl +"/");

        User user = new User();
        user.setId(1);

        //Product product1 = new Product();
        product.setUser(user);
        /*product1.setName(name);
        product1.setDescription(description);
        product1.setEnabled(true);
        product1.setCategory(null);
        product1.setImage(null);
        product1.setUnit(null);*/

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.postForObject(uri, product, Product.class);

        return "redirect:/product/";
    }

    @RequestMapping(value = "/delProduct", method = RequestMethod.POST)
    public String delProduct(@RequestParam int productId){

        final String uri = new String(productUrl + "/{userId}");
        Map<String, Integer> param = new HashMap<String, Integer>();
        param.put("productId", productId);

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.delete(uri,param);

        return "redirect:/product/";
    }
}
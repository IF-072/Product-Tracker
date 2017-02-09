package com.softserve.if072.mvcapp.controller;

import com.softserve.if072.common.model.Product;
import com.softserve.if072.common.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

/**
 * The class contains methods that handle the http requests from the product's page
 *
 * @author Vitaliy Malisevych
 */

@Controller
@RequestMapping("/product")
public class ProductPageController {
    @RequestMapping("/")
    public String getProductPage(ModelMap model){
        final String uri = "http://localhost:8080/rest/product/user/1";

        RestTemplate restTemplate = new RestTemplate();
        Product[] result = restTemplate.getForObject(uri, Product[].class);
        List products = Arrays.asList(result);

        model.addAttribute("products", products);

        return "product";
    }

    @PostMapping("/add")
    public String addProduct(@RequestParam String name, @RequestParam String description){
        final String uri = "http://localhost:8080/rest/product/";

        User user = new User();
        user.setId(1);

        Product product1 = new Product();
        product1.setUser(user);
        product1.setName(name);
        product1.setDescription(description);
        product1.setEnabled(true);
        product1.setCategory(null);
        product1.setImage(null);
        product1.setUnit(null);

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.postForObject(uri, product1, Product.class);

        return "product";
    }
}

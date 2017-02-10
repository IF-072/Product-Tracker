package com.softserve.if072.mvcapp.controller;

import com.softserve.if072.common.model.Product;
import com.softserve.if072.common.model.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
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

    @Value("${application.restProductURI}")
    private String productUri;

    @RequestMapping("/")
    public String getProductPage(ModelMap model){

        int userId = 1;

        final String uri = new String(productUri + "/user/{userId}");
        Map<String, Integer> param = new HashMap<String, Integer>();
        param.put("userId", userId);

        RestTemplate restTemplate = new RestTemplate();
        List<Product> products = restTemplate.getForObject(uri, List.class, param);

        model.addAttribute("products", products);

        return "product";
    }

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.OK)
    public void addProduct(@RequestParam String name, @RequestParam String description){

        final String uri = new String(productUri +"/");

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
    }
}

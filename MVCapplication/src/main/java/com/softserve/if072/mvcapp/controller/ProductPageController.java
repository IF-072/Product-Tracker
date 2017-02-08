package com.softserve.if072.mvcapp.controller;

import com.softserve.if072.common.model.Product;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

/**
 * The class contains methods that handle the http requests from the product's page
 *
 * @author Vitaliy Malisevych
 */

@Controller
public class ProductPageController {
    @RequestMapping("/product")
    public String getProductPage(ModelMap model){
        final String uri = "http://localhost:8080/rest/product/user/1";

        RestTemplate restTemplate = new RestTemplate();
        Product[] result = restTemplate.getForObject(uri, Product[].class);
        List products = Arrays.asList(result);

        model.addAttribute("products", products);
//        model.addAttribute("title", "MVC application title");
//        model.addAttribute("body", "MVC application body");

        return "product";
    }

}

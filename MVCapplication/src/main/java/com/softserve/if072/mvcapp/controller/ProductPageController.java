package com.softserve.if072.mvcapp.controller;

import com.softserve.if072.common.model.Category;
import com.softserve.if072.common.model.Image;
import com.softserve.if072.common.model.Product;
import com.softserve.if072.common.model.Unit;
import com.softserve.if072.common.model.User;
import com.softserve.if072.common.model.Store;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
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

    @Value("${application.restCategoryURL}")
    private String categoryUrl;

    private int imageIdForEditPage;

    @RequestMapping("/")
    public String getProductPage(ModelMap model) {

        int userId = 1;

        final String uri = productUrl + "/user/{userId}";
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

        final String unitUri = unitUrl + "/";
        final String categoryUri = categoryUrl + "{userId}";

        model.addAttribute("product", new Product());

        RestTemplate restTemplate = new RestTemplate();

        Unit[] unitResult = restTemplate.getForObject(unitUri, Unit[].class);
        List<Unit> units = Arrays.asList(unitResult);

        Map<String, Integer> param = new HashMap<>();
        param.put("userId", userId);
        Category[] categoryResult = restTemplate.getForObject(categoryUri, Category[].class, param);
        List<Category> categories = Arrays.asList(categoryResult);

        model.addAttribute("units", units);
        model.addAttribute("categories", categories);

        return "addProduct";
    }

    @RequestMapping(value = "/addProduct", method = RequestMethod.POST)
    public String addProduct(@ModelAttribute("product") Product product) {

        final String categoryByIdUri = categoryUrl + "/id/{categoryId}";
        final String UnitByIdUri = unitUrl + "/{unitId}";
        final String uri = productUrl +"/";

        User user = new User();
        user.setId(1);

        RestTemplate restTemplate = new RestTemplate();

        Map<String, Integer> param = new HashMap<>();
        param.put("categoryId", product.getCategory().getId());
        Category category = restTemplate.getForObject(categoryByIdUri, Category.class, param);

        param.clear();
        param.put("unitId", product.getUnit().getId());
        Unit unit = restTemplate.getForObject(UnitByIdUri, Unit.class, param);

        product.setUser(user);
        product.setEnabled(true);
        product.setCategory(category);
        product.setImage(null);
        product.setUnit(unit);

        restTemplate.postForObject(uri, product, Product.class);

        return "redirect:/product/";
    }

    @RequestMapping(value = "/editProduct", method = RequestMethod.GET)
    public String editProduct(@RequestParam int id, ModelMap model) {

        int userId = 1;

        final String uri = productUrl + "/{id}";
        final String unitUri = unitUrl + "/";
        final String categoryUri = categoryUrl + "{userId}";

        Map<String, Integer> param = new HashMap<>();
        param.put("id", id);
        RestTemplate restTemplate = new RestTemplate();
        Product product = restTemplate.getForObject(uri, Product.class, param);

        Unit[] unitResult = restTemplate.getForObject(unitUri, Unit[].class);
        List<Unit> units = Arrays.asList(unitResult);

        param.clear();
        param.put("userId", userId);
        Category[] categoryResult = restTemplate.getForObject(categoryUri, Category[].class, param);
        List<Category> categories = Arrays.asList(categoryResult);

        if(product.getImage() == null) {
            imageIdForEditPage = 0;
        } else {
            imageIdForEditPage = product.getImage().getId();
        }

        System.out.println("Method GET" + product);

        model.addAttribute("units", units);
        model.addAttribute("categories", categories);
        model.addAttribute("product", product);

        return "editProduct";
    }

    @RequestMapping(value = "/editProduct", method = RequestMethod.POST)
    public String editProduct(@ModelAttribute("product") Product product) {

        final String categoryByIdUri = categoryUrl + "/id/{categoryId}";
        final String UnitByIdUri = unitUrl + "/{unitId}";
        final String uri = productUrl +"/";

        User user = new User();
        user.setId(1);

        RestTemplate restTemplate = new RestTemplate();

        Map<String, Integer> param = new HashMap<String, Integer>();
        param.put("categoryId", product.getCategory().getId());
        Category category = restTemplate.getForObject(categoryByIdUri, Category.class, param);

        param.clear();
        param.put("unitId", product.getUnit().getId());
        Unit unit = restTemplate.getForObject(UnitByIdUri, Unit.class, param);

        if(imageIdForEditPage != 0) {
            Image image = new Image();
            image.setId(imageIdForEditPage);
            product.setImage(image);
        } else {
            product.setImage(null);
        }

        product.setUser(user);
        product.setEnabled(true);
        product.setCategory(category);
        product.setUnit(unit);

        System.out.println("Method POST" + product);

        restTemplate.put(uri, product, Product.class);

        return "redirect:/product/";
    }

    @RequestMapping(value = "/delProduct", method = RequestMethod.POST)
    public String delProduct(@RequestParam int productId){

        final String uri = productUrl + "/{productId}";
        Map<String, Integer> param = new HashMap<String, Integer>();
        param.put("productId", productId);

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.delete(uri,param);

        return "redirect:/product/";

    }

    @RequestMapping(value = "/stores", method = RequestMethod.GET)
    public String getStoresByProductId(@RequestParam int productId, ModelMap model) {

        final String uri = productUrl + "/{productId}/stores";

        Map<String, Integer> param = new HashMap<>();
        param.put("productId", productId);

        RestTemplate restTemplate = new RestTemplate();
        Store[] result = restTemplate.getForObject(uri, Store[].class, param);
        List<Store> stores = Arrays.asList(result);

        model.addAttribute("stores", stores);

        return "allStores";

    }

}
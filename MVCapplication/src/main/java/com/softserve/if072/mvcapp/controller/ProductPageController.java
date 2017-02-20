package com.softserve.if072.mvcapp.controller;

import com.softserve.if072.common.model.Category;
import com.softserve.if072.common.model.Product;
import com.softserve.if072.common.model.Store;
import com.softserve.if072.common.model.Unit;
import com.softserve.if072.common.model.User;
import com.softserve.if072.mvcapp.dto.StoresInProduct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
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
@PropertySource(value = {"classpath:application.properties"})
public class ProductPageController extends BaseController {

    @Value("${application.restProductURL}")
    private String productUrl;

    @Value("${application.restUnitURL}")
    private String unitUrl;

    @Value("${application.restCategoryURL}")
    private String categoryUrl;

    @Value("${application.restStoreURL}")
    private String storeUrl;

    @RequestMapping("/")
    public String getProductPage(ModelMap model) {

        int userId = getCurrentUser().getId();

        final String uri = productUrl + "/user/{userId}";
        Map<String, Integer> param = new HashMap<String, Integer>();
        param.put("userId", userId);

        RestTemplate restTemplate = getRestTemplate();
        List<Product> products = restTemplate.getForObject(uri, List.class, param);

        model.addAttribute("products", products);

        return "product";
    }


    @GetMapping("/addProduct")
    public String addProduct(ModelMap model){

        int userId = getCurrentUser().getId();

        final String unitUri = unitUrl + "/";
        final String categoryUri = categoryUrl + "{userId}";

        model.addAttribute("product", new Product());

        RestTemplate restTemplate = getRestTemplate();

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
    public String addProduct(@Validated @ModelAttribute("product") Product product, BindingResult result,
                             Model model, HttpServletResponse httpServletResponse) {

        if (result.hasErrors()) {
            model.addAttribute("errorMessages", result.getFieldErrors());
            return "addProduct";
        }

        final String categoryByIdUri = categoryUrl + "/id/{categoryId}";
        final String UnitByIdUri = unitUrl + "/{unitId}";
        final String uri = productUrl +"/";

        User user = getCurrentUser();

        RestTemplate restTemplate = getRestTemplate();

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

        int userId = getCurrentUser().getId();

        final String uri = productUrl + "/{id}";
        final String unitUri = unitUrl + "/";
        final String categoryUri = categoryUrl + "{userId}";

        RestTemplate restTemplate = getRestTemplate();

        Map<String, Integer> param = new HashMap<>();
        param.put("id", id);
        Product product = restTemplate.getForObject(uri, Product.class, param);

        Unit[] unitResult = restTemplate.getForObject(unitUri, Unit[].class);
        List<Unit> units = Arrays.asList(unitResult);

        param.clear();
        param.put("userId", userId);
        Category[] categoryResult = restTemplate.getForObject(categoryUri, Category[].class, param);
        List<Category> categories = Arrays.asList(categoryResult);

        model.addAttribute("units", units);
        model.addAttribute("categories", categories);
        model.addAttribute("product", product);

        return "editProduct";
    }

    @RequestMapping(value = "/editProduct", method = RequestMethod.POST)
    public String editProduct(@ModelAttribute("product") Product newProduct) {

        final String categoryByIdUri = categoryUrl + "/id/{categoryId}";
        final String UnitByIdUri = unitUrl + "/{unitId}";
        final String uri = productUrl +"/";

        User user = getCurrentUser();

        RestTemplate restTemplate = getRestTemplate();

        Map<String, Integer> param = new HashMap<String, Integer>();
        param.put("categoryId", newProduct.getCategory().getId());
        Category category = restTemplate.getForObject(categoryByIdUri, Category.class, param);

        param.clear();
        param.put("unitId", newProduct.getUnit().getId());
        Unit unit = restTemplate.getForObject(UnitByIdUri, Unit.class, param);

        newProduct.setUser(user);
        newProduct.setEnabled(true);
        newProduct.setCategory(category);
        newProduct.setUnit(unit);

        restTemplate.put(uri, newProduct, Product.class);
        return "redirect:/product/";
    }

    @RequestMapping(value = "/delProduct", method = RequestMethod.POST)
    public String delProduct(@RequestParam int productId){

        final String uri = productUrl + "/{productId}";
        Map<String, Integer> param = new HashMap<String, Integer>();
        param.put("productId", productId);

        RestTemplate restTemplate = getRestTemplate();
        restTemplate.delete(uri,param);

        return "redirect:/product/";

    }

    @RequestMapping(value = "/stores", method = RequestMethod.GET)
    public String getStoresByProductId(@RequestParam int productId, ModelMap model) {

        int userId = getCurrentUser().getId();

        final String getStoresUri = productUrl + "/{productId}/productStores/{userId}";
        final String getProductUri = productUrl + "/{productId}";
        final String getAllStoresUri = storeUrl + "/user/{userId}";

        RestTemplate restTemplate = getRestTemplate();

        Map<String, Integer> param = new HashMap<>();
        param.put("productId", productId);
        Product product = restTemplate.getForObject(getProductUri, Product.class, param);

        param.clear();
        param.put("productId", productId);
        param.put("userId", userId);

        Store[] arrStores = restTemplate.getForObject(getStoresUri, Store[].class, param);
        if(arrStores != null) {
            List<Store> stores = Arrays.asList(arrStores);
            product.setStores(stores);
        } else {
            product.setStores(null);
        }

        Store[] arrAllStores = restTemplate.getForObject(getAllStoresUri, Store[].class, param);
        List<Store> allStores = Arrays.asList(arrAllStores);

        Map<Integer,String> storesInProductById = new HashMap<>();
        List<Integer> listStoresInProductById = new ArrayList<>();
        for(Store s : product.getStores()) {
            storesInProductById.put(s.getId(),s.getName() + ", " + s.getAddress());
            listStoresInProductById.add(s.getId());
        }

        Map<Integer,String> allStoresById = new HashMap<>();
        for(Store s : allStores) {
            allStoresById.put(s.getId(),s.getName() + ", " + s.getAddress());
        }

        StoresInProduct storesInProduct = new StoresInProduct();
        storesInProduct.setStoresId(listStoresInProductById);

        model.addAttribute("storesId", allStoresById);
        model.addAttribute("storesInProduct", storesInProduct);
        model.addAttribute("product", product);

        return "productInStores";

    }

    @RequestMapping(value = "/stores", method = RequestMethod.POST)
    public String getStoresByProductId(@ModelAttribute("storesInProduct") StoresInProduct storesInProduct, @RequestParam int productId) {

        final String getStoresUri = productUrl + "/{productId}/productStores/{userId}";
        final String getStoreByIdUri = storeUrl + "/{storeId}";
        final String getProductByIdUri = productUrl + "/{productId}";
        final String addStoreToProductUri = productUrl + "/stores/";
        final String deleteStoreFromProductUri = productUrl + "/deleteStores/";

        int userId = getCurrentUser().getId();

        Map<String, Integer> param = new HashMap<>();
        param.put("productId", productId);
        param.put("userId", userId);

        RestTemplate restTemplate = getRestTemplate();

        Store[] result = restTemplate.getForObject(getStoresUri, Store[].class, param);
        List<Store> oldStores = Arrays.asList(result);

        Product product = restTemplate.getForObject(getProductByIdUri, Product.class, param);

        //Get list of stores named "newStores", that had been checked in the form

        List<Store> newStores = new ArrayList<>();

        for(int storeId : storesInProduct.getStoresId()) {
            param.clear();
            param.put("storeId", storeId);
            newStores.add(restTemplate.getForObject(getStoreByIdUri, Store.class, param));
        }

        List<Store> storesToAdd = new ArrayList<>();
        List<Store> storesToDelete = new ArrayList<>();

        if(newStores != null) {
            outer:for(Store ns : newStores) {
                for(Store os : oldStores) {
                    if(ns.getId() == os.getId()) {
                        continue outer;
                    }
                }
                storesToAdd.add(ns);
            }

            outer:for(Store os : oldStores) {
                for(Store ns : newStores) {
                    if(os.getId() == ns.getId()) {
                        continue outer;
                    }
                }
                storesToDelete.add(os);
            }
        } else {
            storesToDelete.addAll(oldStores);
        }

        if(!storesToAdd.isEmpty()) {
            product.setStores(storesToAdd);
            restTemplate.postForObject(addStoreToProductUri, product, Product.class);
        }

        if(!storesToDelete.isEmpty()) {
            product.setStores(storesToDelete);
            restTemplate.postForObject(deleteStoreFromProductUri, product, Product.class);
        }

        return "redirect:/product/";

    }
}
package com.softserve.if072.mvcapp.controller;

import com.softserve.if072.common.model.Category;
import com.softserve.if072.common.model.Product;
import com.softserve.if072.common.model.Store;
import com.softserve.if072.common.model.Unit;
import com.softserve.if072.common.model.User;
import com.softserve.if072.mvcapp.dto.StoresInProduct;
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

    /**
     * Method returns product's page
     *
     * @param model model with data represented on page
     * @return product
     */

    @RequestMapping(value = "/", method = RequestMethod.GET)
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

    /**
     * Method returns page for adding a product
     *
     * @param model model with data represented on page
     * @return addProduct
     */

    @RequestMapping(value = "/addProduct", method = RequestMethod.GET)
    public String addProduct(Model model){

        int userId = getCurrentUser().getId();

        final String unitUri = unitUrl + "/";
        final String categoryUri = categoryUrl + "{userId}";

        model.addAttribute("product", new Product());

        RestTemplate restTemplate = getRestTemplate();

        ResponseEntity<List<Unit>> unitsResponse = restTemplate.exchange(unitUri, HttpMethod.GET, null, new ParameterizedTypeReference<List<Unit>>(){});
        List<Unit> units = unitsResponse.getBody();

        Map<String, Integer> param = new HashMap<>();
        param.put("userId", userId);
        ResponseEntity<List<Category>> categoriesResponse = restTemplate.exchange(categoryUri, HttpMethod.GET, null, new ParameterizedTypeReference<List<Category>>(){}, param);
        List<Category> categories = categoriesResponse.getBody();

        model.addAttribute("units", units);
        model.addAttribute("categories", categories);

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
                             Model model, HttpServletResponse httpServletResponse) {

        User user = getCurrentUser();

        RestTemplate restTemplate = getRestTemplate();

        final String categoryByIdUri = categoryUrl + "/id/{categoryId}";
        final String UnitByIdUri = unitUrl + "/{unitId}";
        final String uri = productUrl +"/";
        final String unitUri = unitUrl + "/";
        final String categoryUri = categoryUrl + "{userId}";

        if (result.hasErrors()) {
            model.addAttribute("errorMessages", result.getFieldErrors());

            ResponseEntity<List<Unit>> unitsResponse = restTemplate.exchange(unitUri, HttpMethod.GET, null, new ParameterizedTypeReference<List<Unit>>(){});
            List<Unit> units = unitsResponse.getBody();
            model.addAttribute("units", units);

            Map<String, Integer> param = new HashMap<>();
            param.put("userId", user.getId());
            ResponseEntity<List<Category>> categoriesResponse = restTemplate.exchange(categoryUri, HttpMethod.GET, null, new ParameterizedTypeReference<List<Category>>(){}, param);
            List<Category> categories = categoriesResponse.getBody();
            model.addAttribute("categories", categories);

            return "addProduct";
        }

        Map<String, Integer> param = new HashMap<>();
        if(product.getCategory().getId() > 0) {
            param.put("categoryId", product.getCategory().getId());
            Category category = restTemplate.getForObject(categoryByIdUri, Category.class, param);
            product.setCategory(category);
        } else {
            product.setCategory(null);
        }

        if(product.getUnit().getId() > 0) {
            param.clear();
            param.put("unitId", product.getUnit().getId());
            Unit unit = restTemplate.getForObject(UnitByIdUri, Unit.class, param);
            product.setUnit(unit);
        } else {
            product.setUnit(null);
        }

        product.setUser(user);
        product.setEnabled(true);
        product.setImage(null);

        restTemplate.postForObject(uri, product, Product.class);

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

        int userId = getCurrentUser().getId();

        final String uri = productUrl + "/{productId}";
        final String unitUri = unitUrl + "/";
        final String categoryUri = categoryUrl + "{userId}";

        RestTemplate restTemplate = getRestTemplate();

        Map<String, Integer> param = new HashMap<>();
        param.put("productId", productId);
        Product product = restTemplate.getForObject(uri, Product.class, param);

        ResponseEntity<List<Unit>> unitsResponse = restTemplate.exchange(unitUri, HttpMethod.GET, null, new ParameterizedTypeReference<List<Unit>>(){});
        List<Unit> units = unitsResponse.getBody();

        param.clear();
        param.put("userId", userId);
        ResponseEntity<List<Category>> categoriesResponse = restTemplate.exchange(categoryUri, HttpMethod.GET, null, new ParameterizedTypeReference<List<Category>>(){}, param);
        List<Category> categories = categoriesResponse.getBody();

        model.addAttribute("units", units);
        model.addAttribute("categories", categories);
        model.addAttribute("product", product);

        return "editProduct";
    }

    /**
     * Method sends editing product's data to REST service for adding product in DataBase
     *
     * @param newProduct product to be edited
     * @param result contents errors for validation
     * @param model model with data represented on page
     * @return redirect to product's page
     */

    @RequestMapping(value = "/editProduct", method = RequestMethod.POST)
    public String editProduct(@Validated @ModelAttribute("product") Product newProduct, BindingResult result,
                              Model model) {

        User user = getCurrentUser();

        RestTemplate restTemplate = getRestTemplate();

        final String categoryByIdUri = categoryUrl + "/id/{categoryId}";
        final String UnitByIdUri = unitUrl + "/{unitId}";
        final String uri = productUrl +"/";
        final String unitUri = unitUrl + "/";
        final String categoryUri = categoryUrl + "{userId}";

        if (result.hasErrors()) {
            model.addAttribute("errorMessages", result.getFieldErrors());

            ResponseEntity<List<Unit>> unitsResponse = restTemplate.exchange(unitUri, HttpMethod.GET, null, new ParameterizedTypeReference<List<Unit>>(){});
            List<Unit> units = unitsResponse.getBody();
            model.addAttribute("units", units);

            Map<String, Integer> param = new HashMap<>();
            param.put("userId", user.getId());
            ResponseEntity<List<Category>> categoriesResponse = restTemplate.exchange(categoryUri, HttpMethod.GET, null, new ParameterizedTypeReference<List<Category>>(){}, param);
            List<Category> categories = categoriesResponse.getBody();
            model.addAttribute("categories", categories);

            return "editProduct";
        }

        Map<String, Integer> param = new HashMap<>();
        if(newProduct.getCategory().getId() > 0) {
            param.put("categoryId", newProduct.getCategory().getId());
            Category category = restTemplate.getForObject(categoryByIdUri, Category.class, param);
            newProduct.setCategory(category);
        } else {
            newProduct.setCategory(null);
        }

        if(newProduct.getUnit().getId() > 0) {
            param.clear();
            param.put("unitId", newProduct.getUnit().getId());
            Unit unit = restTemplate.getForObject(UnitByIdUri, Unit.class, param);
            newProduct.setUnit(unit);
        } else {
            newProduct.setUnit(null);
        }

        newProduct.setUser(user);
        newProduct.setEnabled(true);

        restTemplate.put(uri, newProduct, Product.class);
        return "redirect:/product/";
    }

    /**
     * Method sends data to REST service for deleting product
     *
     * @param productId product to be deleted
     * @return redirect to product's page
     */

    @RequestMapping(value = "/delProduct", method = RequestMethod.POST)
    public String delProduct(@RequestParam int productId){

        User user = getCurrentUser();

        final String uri = productUrl + "/{userId}/{productId}";
        Map<String, Integer> param = new HashMap<String, Integer>();
        param.put("productId", productId);
        param.put("userId", user.getId());

        RestTemplate restTemplate = getRestTemplate();
        restTemplate.delete(uri,param);

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

        ResponseEntity<List<Store>> storeResponse = restTemplate.exchange(getStoresUri, HttpMethod.GET, null, new ParameterizedTypeReference<List<Store>>(){}, param);
        List<Store> stores = storeResponse.getBody();

        product.setStores(stores);

        ResponseEntity<List<Store>> rateResponse = restTemplate.exchange(getAllStoresUri, HttpMethod.GET, null, new ParameterizedTypeReference<List<Store>>(){}, param);
        List<Store> allStores = rateResponse.getBody();

        Map<Integer,String> storesInProductById = new HashMap<>();
        List<Integer> listStoresInProductById = new ArrayList<>();
        if(product.getStores() != null){
            for(Store s : product.getStores()) {
                storesInProductById.put(s.getId(),s.getName() + ", " + s.getAddress());
                listStoresInProductById.add(s.getId());
            }
        }

        Map<Integer,String> allStoresById = new HashMap<>();
        if(allStores != null) {
            for(Store s : allStores) {
                allStoresById.put(s.getId(),s.getName() + ", " + s.getAddress());
            }
        }

        StoresInProduct storesInProduct = new StoresInProduct();
        storesInProduct.setStoresId(listStoresInProductById);

        model.addAttribute("storesId", allStoresById);
        model.addAttribute("storesInProduct", storesInProduct);
        model.addAttribute("product", product);

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

        ResponseEntity<List<Store>> oldStoresResponse = restTemplate.exchange(getStoresUri, HttpMethod.GET, null, new ParameterizedTypeReference<List<Store>>(){}, param);
        List<Store> oldStores = oldStoresResponse.getBody();

        Product product = restTemplate.getForObject(getProductByIdUri, Product.class, param);

        List<Store> newStores = new ArrayList<>();

        for(int storeId : storesInProduct.getStoresId()) {
            param.clear();
            param.put("storeId", storeId);
            newStores.add(restTemplate.getForObject(getStoreByIdUri, Store.class, param));
        }

        List<Store> storesToAdd = new ArrayList<>();
        List<Store> storesToDelete = new ArrayList<>();
        if(oldStores != null) {
            if(newStores != null) {
                storesToAdd.addAll(newStores);
                storesToDelete.addAll(oldStores);
                storesToAdd.removeAll(oldStores);
                storesToDelete.removeAll(newStores);
            } else {
                storesToDelete.addAll(oldStores);
            }
        } else {
            storesToAdd.addAll(newStores);
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
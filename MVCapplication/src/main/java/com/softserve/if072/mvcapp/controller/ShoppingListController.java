package com.softserve.if072.mvcapp.controller;

import com.softserve.if072.common.model.Product;
import com.softserve.if072.common.model.ShoppingList;
import org.apache.commons.collections.CollectionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * This class provides access to the shopping list page.
 *
 * @author Oleh Pochernin
 */
@Controller
@PropertySource(value = {"classpath:application.properties"})
public class ShoppingListController extends BaseController {
    private static final Logger LOG = LogManager.getLogger(ShoppingListController.class);
    private static final String INFO_LOG_TEMPLATE = "Shopping list element (userId: %d, productId: %d) has been %s.";

    @Value("${service.shoppingList}")
    private String shoppingListUrl;

    @Value("${service.shoppingList.byUser}")
    private String shoppingListByUserUrl;

    @Value("${service.shoppingList.byUserAndProduct}")
    private String shoppingListByUserAndProductUrl;

    @Value("${service.product.id}")
    private String productById;

    /**
     * This method extracts a shopping list model for th shopping list's view.
     *
     * @param model model for view where shopping is put
     * @return shopping list's view url
     */
    @RequestMapping("/shopping_list")
    public String getPage(Model model) {
        RestTemplate restTemplate = new RestTemplate();

        int id = getCurrentUser().getId();

        List<ShoppingList> shoppingList = restTemplate.getForObject(String.format(shoppingListByUserUrl, id), List.class);

        if (CollectionUtils.isNotEmpty(shoppingList)) {
            model.addAttribute("shoppingList", shoppingList);
            LOG.info(String.format("Shopping list with %d elements has been put into model.", shoppingList.size()));
        } else {
            LOG.info("Shopping list has't got any elements yet.");
        }

        return "shopping_list";
    }

    /**
     * This method allows to change product amount in the shopping list and delete
     * element from the shopping list.
     *
     * @param prodId if of the product to be edited
     * @param value  if value is positive product amount is increased by val,
     *               if value is positive product amount is decreased by val.
     * @return redirect to shopping list's view url
     */
    @RequestMapping(value = "/shopping_list/edit", method = RequestMethod.POST)
    @ResponseBody
    public String editShoppingList(@RequestParam("prodId") int prodId,
                                   @RequestParam("val") int value) {
        RestTemplate restTemplate = new RestTemplate();
        ShoppingList shoppingList = restTemplate.getForObject(
                String.format(shoppingListByUserAndProductUrl, getCurrentUser().getId(), prodId), ShoppingList.class);

        HttpEntity<ShoppingList> entity = new HttpEntity<>(shoppingList);

        shoppingList.setAmount(shoppingList.getAmount() + value);
        restTemplate.exchange(shoppingListUrl, HttpMethod.PUT, entity, ShoppingList.class);
        LOG.info(String.format(INFO_LOG_TEMPLATE,
                shoppingList.getUser().getId(), shoppingList.getProduct().getId(), "updated"));

        return String.format("%d %s", shoppingList.getAmount(), shoppingList.getProduct().getUnit().getName());
    }

    /**
     * This method allows to delete product from the shopping list.
     *
     * @param prodId id of the product to be deleted
     * @return redirect to shopping list's view url
     */
    @RequestMapping(value = "/shopping_list/delete", method = RequestMethod.GET)
    public String deleteProductFromShoppingList(@RequestParam("prodId") int prodId) {
        RestTemplate restTemplate = new RestTemplate();
        ShoppingList shoppingList = restTemplate.getForObject(
                String.format(shoppingListByUserAndProductUrl, getCurrentUser().getId(), prodId), ShoppingList.class);
        HttpEntity<ShoppingList> entity = new HttpEntity<>(shoppingList);

        restTemplate.exchange(shoppingListUrl, HttpMethod.DELETE, entity, ShoppingList.class);
        LOG.info(String.format(INFO_LOG_TEMPLATE,
                shoppingList.getUser().getId(), shoppingList.getProduct().getId(), "deleted"));

        return "redirect:/shopping_list/";
    }

    /**
     * This method allows to add product to the shopping list.
     *
     * @param productId if of the product to be added
     * @return redirect to shopping list's view url
     */
    @RequestMapping(value = "/shopping_list/add", method = RequestMethod.POST)
    public String addProductToShoppingList(@RequestParam("productId") int productId) {
        RestTemplate restTemplate = getRestTemplate();
        ShoppingList shoppingList = restTemplate.getForObject(
                String.format(shoppingListByUserAndProductUrl, getCurrentUser().getId(), productId), ShoppingList.class);

        if (shoppingList == null) {
            shoppingList = new ShoppingList();
            Product product = restTemplate.getForObject(
                    String.format(productById, productId), Product.class);

            shoppingList.setUser(getCurrentUser());
            shoppingList.setProduct(product);
            shoppingList.setAmount(1);

            HttpEntity<ShoppingList> entity = new HttpEntity<>(shoppingList);
            restTemplate.exchange(shoppingListUrl, HttpMethod.POST, entity, ShoppingList.class);
            LOG.info(String.format(INFO_LOG_TEMPLATE,
                    shoppingList.getUser().getId(), shoppingList.getProduct().getId(), "added"));
        }

        return "redirect:/product/";
    }
}
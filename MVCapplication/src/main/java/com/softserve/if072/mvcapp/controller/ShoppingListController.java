package com.softserve.if072.mvcapp.controller;

import com.softserve.if072.common.model.ShoppingList;
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

    @Value("${service.shoppingList}")
    private String shoppingListUrl;

    @Value("${service.shoppingList.byUser}")
    private String shoppingListByUserUrl;

    @Value("${service.shoppingList.byUserAndProduct}")
    private String shoppingListByUserAndProductUrl;

    /**
     * This method extracts a shopping list model for th shopping list's view.
     *
     * @return shopping list's view url
     */
    @RequestMapping("/shopping_list")
    public String getPage(Model model) {
        RestTemplate restTemplate = new RestTemplate();

        int id = getCurrentUser().getId();

        List<ShoppingList> shoppingList = restTemplate.getForObject(String.format(shoppingListByUserUrl, id), List.class);
        model.addAttribute("shoppingList", shoppingList);

        return "shopping_list";
    }

    @RequestMapping(value = "/shopping_list/edit", method = RequestMethod.POST)
    public String editProductAmount(@RequestParam("userId") int userId,
                                    @RequestParam("productId") int productId,
                                    @RequestParam("val") int value) {
        RestTemplate restTemplate = new RestTemplate();

        ShoppingList shoppingList = restTemplate.getForObject(String.format(shoppingListByUserAndProductUrl, userId, productId), ShoppingList.class);
        shoppingList.setAmount(shoppingList.getAmount() + value);

        HttpEntity<ShoppingList> entity = new HttpEntity<>(shoppingList);
        restTemplate.exchange(shoppingListUrl, HttpMethod.PUT, entity, ShoppingList.class);

        return "redirect:../shopping_list";
    }
}
package com.softserve.if072.mvcapp.controller;

import com.softserve.if072.common.model.ShoppingList;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
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
public class ShoppingListController {
    private static final Logger LOG = LogManager.getLogger(ShoppingListController.class);

    @Value("${service.shoppinglist}")
    private String shoppingListUrl;

    /**
     * This method extracts a shopping list model for th shopping list's view.
     *
     * @return shopping list's view url
     */
    @RequestMapping("/shopping_list")
    public String getPage(Model model) {
        RestTemplate restTemplate = new RestTemplate();

        //it'll be changed when Igor makes superclass for all controllers containing logic for obtaining current user
        int id = 2;

        List<ShoppingList> shoppingList = restTemplate.getForObject(String.format(shoppingListUrl, id), List.class);
        model.addAttribute("shoppingList", shoppingList);

        return "shopping_list";
    }

    @RequestMapping("/shopping_list/increase")
    public String increaseProductAmount(@RequestParam("userId") int userId, @RequestParam("productId") int productId) {


        return "redirect:../shopping_list";
    }
}
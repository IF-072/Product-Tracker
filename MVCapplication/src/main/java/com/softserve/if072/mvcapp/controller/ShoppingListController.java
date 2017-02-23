package com.softserve.if072.mvcapp.controller;

import com.softserve.if072.mvcapp.service.ShoppingListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * This class provides access to the shopping list page.
 *
 * @author Oleh Pochernin
 */
@Controller
public class ShoppingListController extends BaseController {
    @Autowired
    private ShoppingListService shoppingListService;

    /**
     * This method extracts a shopping list model for the shopping list's view.
     *
     * @param model model for view where shopping is put
     * @return shopping list's view url
     */
    @RequestMapping("/shopping_list")
    public String getPage(Model model) {
        model.addAttribute("shoppingList", shoppingListService.getAllElements(getCurrentUser().getId()));

        return "shopping_list";
    }

    /**
     * This method allows to change product amount in the shopping list.
     *
     * @param prodId id of the editing product
     * @param value  if value is positive product amount is increased by val,
     *               if value is positive product amount is decreased by val.
     * @return JSON containing new amount with units
     */
    @RequestMapping(value = "/shopping_list/edit", method = RequestMethod.POST)
    @ResponseBody
    public String editShoppingList(@RequestParam("prodId") int prodId,
                                   @RequestParam("val") int value) {
        return shoppingListService.editShoppingList(getCurrentUser().getId(), prodId, value);
    }

    /**
     * This method allows to delete product from the shopping list.
     *
     * @param prodId id of the deleting product
     * @return redirect to shopping list's view url
     */
    @RequestMapping(value = "/shopping_list/delete", method = RequestMethod.GET)
    public String deleteProductFromShoppingList(@RequestParam("prodId") int prodId) {
        shoppingListService.deleteProductFromShoppingList(getCurrentUser().getId(), prodId);

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
        shoppingListService.addProductToShoppingList(getCurrentUser(), productId);

        return "redirect:/product/";
    }
}
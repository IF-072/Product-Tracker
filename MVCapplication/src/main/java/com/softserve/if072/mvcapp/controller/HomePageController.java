package com.softserve.if072.mvcapp.controller;

import com.softserve.if072.common.model.ShoppingList;
import com.softserve.if072.common.model.Storage;
import com.softserve.if072.common.model.User;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * The controller contains methods that handle home page access
 *
 * @author Igor Parada
 * @author Pavlo Bendus
 */
@Controller
public class HomePageController extends BaseController {

    @Value("${application.restStorageURL}")
    private String restStorageURL;

    @Value("${application.restShoppingListURL}")
    private String restShoppingListURL;

    /**
     * Returns home page view if user is logged in, otherwise sends redirect to login page
     *
     * @return view name
     */
    @GetMapping({"/", "/home"})
    public String getHomePage(ModelMap model) {
        User user = getCurrentUser();
        if (user != null) {
            RestTemplate restTemplate = getRestTemplate();
            List<Storage> storageList = restTemplate.getForObject(restStorageURL + getCurrentUser().getId(), List.class);
            List<ShoppingList> shoppingListList = restTemplate.getForObject(restShoppingListURL + getCurrentUser().getId(), List.class);

            model.addAttribute("user", user);

            if (CollectionUtils.isNotEmpty(storageList)) {
                model.addAttribute("storage", storageList.size());
            } else {
                model.addAttribute("storage", 0);
            }

            if (CollectionUtils.isNotEmpty(storageList)) {
                model.addAttribute("shoplist", shoppingListList.size());
            } else {
                model.addAttribute("shoplist", 0);
            }

            return "home";
        } else {
            return "redirect:/login";
        }
    }
}

package com.softserve.if072.mvcapp.controller;

import com.softserve.if072.common.model.ShoppingList;
import com.softserve.if072.common.model.Storage;
import com.softserve.if072.common.model.User;
import com.softserve.if072.mvcapp.service.UserService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
public class HomePageController {

    @Value("${application.restStorageURL}")
    private String restStorageURL;

    @Value("${application.restShoppingListURL}")
    private String restShoppingListURL;

    private RestTemplate restTemplate;
    private UserService userService;

    @Autowired
    public HomePageController(RestTemplate restTemplate, UserService userService) {
        this.restTemplate = restTemplate;
        this.userService = userService;
    }

    /**
     * Returns home page view if user is logged in, otherwise sends redirect to login page
     *
     * @return view name
     */
    @GetMapping("/home")
    public String getHomePage(ModelMap model) {
        User user = userService.getCurrentUser();
        if (user != null) {
            List<Storage> storageList = restTemplate.getForObject(restStorageURL + user.getId(), List.class);
            List<ShoppingList> shoppingListList = restTemplate.getForObject(restShoppingListURL + user.getId(), List.class);

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

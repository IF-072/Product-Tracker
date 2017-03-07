package com.softserve.if072.mvcapp.controller;

import com.softserve.if072.common.model.dto.StorageDTO;
import com.softserve.if072.mvcapp.service.ShoppingListService;
import com.softserve.if072.mvcapp.service.StoragePageService;
import com.softserve.if072.mvcapp.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by dyndyn on 05.02.2017.
 */
@Controller
@RequestMapping("/storage")
public class StoragePageController {

    private static final Logger LOGGER = LogManager.getLogger(StoragePageController.class);

    private StoragePageService storagePageService;
    private ShoppingListService shoppingListService;
    private UserService userService;

    @Autowired
    public StoragePageController(StoragePageService storagePageService, ShoppingListService shoppingListService,
                                 UserService userService) {
        this.storagePageService = storagePageService;
        this.shoppingListService = shoppingListService;
        this.userService = userService;
    }

    @GetMapping
    public String getPage(ModelMap model) {
        model.addAttribute("list", storagePageService.getStorages(userService.getCurrentUser().getId()));
        model.addAttribute("storage", new StorageDTO());
        return "storage";

    }

    @PostMapping("/update")
    @ResponseBody
    public String updateAmount(@Validated @ModelAttribute StorageDTO storageDTO, BindingResult result) {
        storageDTO.setUserId(userService.getCurrentUser().getId());
        return storagePageService.updateAmount(storageDTO, result);
    }

    @PostMapping("/addToSL")
    @ResponseStatus(value = HttpStatus.OK)
    public void addToShoppingList(@RequestParam("productId") int productId) {
        shoppingListService.addProductToShoppingList(userService.getCurrentUser(), productId);
    }
}

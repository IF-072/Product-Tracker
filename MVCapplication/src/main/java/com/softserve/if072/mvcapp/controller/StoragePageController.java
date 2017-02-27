package com.softserve.if072.mvcapp.controller;

import com.softserve.if072.common.model.dto.StorageDTO;
import com.softserve.if072.mvcapp.service.ShoppingListService;
import com.softserve.if072.mvcapp.service.StoragePageService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;

/**
 * Created by dyndyn on 05.02.2017.
 */
@Controller
@RequestMapping("/storage")
public class StoragePageController extends BaseController {

    private static final Logger LOGGER = LogManager.getLogger(StoragePageController.class);

    private StoragePageService storagePageService;
    private ShoppingListService shoppingListService;

    @Autowired
    public StoragePageController(StoragePageService storagePageService, ShoppingListService shoppingListService) {
        this.storagePageService = storagePageService;
        this.shoppingListService = shoppingListService;
    }

    @GetMapping
    public String getPage(ModelMap model) {
        model.addAttribute("list", storagePageService.getStorages(getCurrentUser().getId()));
        model.addAttribute("storage", new StorageDTO());
        return "storage";

    }

    @PostMapping("/update")
    @ResponseBody
    public String updateAmount(@Validated @ModelAttribute StorageDTO storageDTO, BindingResult result) {
        storageDTO.setUserId(getCurrentUser().getId());
        return storagePageService.updateAmount(storageDTO, result);
    }

    @PostMapping("/addToSL")
    @ResponseStatus(value = HttpStatus.OK)
    public void addToShoppingList(@RequestParam("productId") int productId) {
        shoppingListService.addProductToShoppingList(getCurrentUser(), productId);
    }
}

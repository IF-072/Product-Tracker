package com.softserve.if072.mvcapp.controller;

import com.softserve.if072.common.model.dto.StorageDTO;
import com.softserve.if072.mvcapp.service.MessageService;
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
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * The StoragePageController class handles requests for "/storage" and renders appropriate view
 *
 * @author Roman Dyndyn
 */
@Controller
@RequestMapping("/storage")
public class StoragePageController {

    private static final Logger LOGGER = LogManager.getLogger(StoragePageController.class);

    private StoragePageService storagePageService;
    private UserService userService;
    private MessageService messageService;

    @Autowired
    public StoragePageController(StoragePageService storagePageService, ShoppingListService shoppingListService,
                                 UserService userService, MessageService messageService) {
        this.storagePageService = storagePageService;
        this.userService = userService;
        this.messageService = messageService;
    }

    @GetMapping
    public String getPage(ModelMap model) {
        model.addAttribute("list", storagePageService.getStorages(userService.getCurrentUser().getId()));
        model.addAttribute("storage", new StorageDTO());
        return "storage";

    }

    @PostMapping("/update")
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public String updateAmount(@Validated @ModelAttribute StorageDTO storageDTO, BindingResult result,
                               @CookieValue(value="myLocaleCookie", required = false) String locale) {
        if (result.hasErrors()) {
            String message = "";
            int i = 0;
            for (FieldError error : result.getFieldErrors()) {
                if (i++ > 0){
                    message += ",\r\n";
                }
                message += error.getDefaultMessage();
            }
            return message;
        }
        storageDTO.setUserId(userService.getCurrentUser().getId());
        storagePageService.updateAmount(storageDTO);
        messageService.broadcast("storage.update", locale, storageDTO.getUserId(), storageDTO.getProductName(),
                storageDTO.getAmount());
        return "";
    }

    @PostMapping("/addToSL")
    @ResponseStatus(value = HttpStatus.OK)
    public void addToShoppingList(@RequestParam("productId") int productId) {
        storagePageService.addProductToShoppingList(userService.getCurrentUser(), productId);
    }
}

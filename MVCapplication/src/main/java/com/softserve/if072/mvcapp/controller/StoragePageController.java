package com.softserve.if072.mvcapp.controller;

import com.softserve.if072.common.model.dto.StorageDTO;
import com.softserve.if072.mvcapp.service.StoragePageService;
import com.softserve.if072.mvcapp.service.UserService;
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

import javax.servlet.http.HttpServletResponse;

/**
 * The StoragePageController class handles requests for
 * "/storage" and renders appropriate view.
 *
 * @author Roman Dyndyn
 */
@Controller
@RequestMapping("/storage")
public class StoragePageController {
    private final StoragePageService storagePageService;
    private final UserService userService;
    private static final String LOCALE_COOKIE = "myLocaleCookie";

    @Autowired
    public StoragePageController(final StoragePageService storagePageService, final UserService userService) {
        this.storagePageService = storagePageService;
        this.userService = userService;
    }

    /**
     * Handles requests for getting all storage records for current user.
     *
     * @param model - a map that will be handed off to the view
     *              for rendering the data to the client
     * @return string with appropriate view name
     */
    @GetMapping
    public String getPage(final ModelMap model) {
        model.addAttribute("list", storagePageService.getStorages(userService.getCurrentUser().getId()))
                .addAttribute("storage", new StorageDTO());
        return "storage";
    }

    /**
     * Handles requests for updating storage record.
     *
     * @param storageDTO - storage record
     * @param locale     - locale of user
     * @return string with error message or empty string
     * if everything is alright
     */
    @PostMapping("/update")
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public String updateAmount(@Validated @ModelAttribute final StorageDTO storageDTO, final BindingResult result,
                               @CookieValue(value = LOCALE_COOKIE, required = false) final String locale,
                               final HttpServletResponse response) {
        if (result.hasErrors()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            StringBuilder message = new StringBuilder();
            int i = 0;
            for (FieldError error : result.getFieldErrors()) {
                if (i++ > 0) {
                    message.append(",\r\n");
                }
                message.append(error.getDefaultMessage());
            }
            return message.toString();
        }
        storageDTO.setUserId(userService.getCurrentUser().getId());
        response.setStatus(HttpServletResponse.SC_OK);
        return storagePageService.updateAmount(storageDTO, locale);
    }

    /**
     * Handles requests for inserting product in shopping list.
     *
     * @param productId - product unique identifier
     */
    @PostMapping("/addToSL")
    @ResponseStatus(value = HttpStatus.OK)
    public void addToShoppingList(@RequestParam("productId") final int productId) {
        storagePageService.addProductToShoppingList(productId);
    }

    /**
     * Handles requests for checking end products in storage.
     *
     * @param locale - locale of user
     */
    @PostMapping("/review")
    @ResponseStatus(value = HttpStatus.OK)
    public void review(@CookieValue(value = LOCALE_COOKIE, required = false) final String locale) {
        storagePageService.reviewStorage(locale, userService.getCurrentUser().getId());
    }
}

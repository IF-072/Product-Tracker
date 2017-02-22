package com.softserve.if072.restservice.controller;

import com.softserve.if072.common.model.ShoppingList;
import com.softserve.if072.restservice.exception.DataNotFoundException;
import com.softserve.if072.restservice.service.ShoppingListService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Serve requests used for working with ShoppingList model
 *
 * @author Nazar Vynnyk
 */

@RestController
@RequestMapping("/api/shoppingList")
public class ShoppingListController {

    public static final Logger LOGGER = LogManager.getLogger(ShoppingListController.class);
    private ShoppingListService shoppingListService;

    @Autowired
    public ShoppingListController(ShoppingListService shoppingListService) {
        this.shoppingListService = shoppingListService;
    }

    @PreAuthorize("#userId != null  && #userId == authentication.user.id")
    @GetMapping("/{userId}")
    @ResponseBody
    @ResponseStatus(value = HttpStatus.OK)
    public List<ShoppingList> getShoppingListByUserId(@PathVariable Integer userId, HttpServletResponse response) throws DataNotFoundException {
        try {
            List<ShoppingList> shoppingLists = shoppingListService.getByUserId(userId);
            LOGGER.info(String.format("Full ShoppingList of user id %d was found ", userId));
            return shoppingLists;
        } catch (DataNotFoundException e) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            LOGGER.error(e.getMessage());
            return null;
        }
    }

    @GetMapping("/{userId}/{productId}")
    @ResponseBody
    @ResponseStatus(value = HttpStatus.OK)
    public ShoppingList getByUserAndProductId(@PathVariable int userId, @PathVariable int productId)
            throws DataNotFoundException {
//        try {
            ShoppingList shoppingList = shoppingListService.getByUserAndProductId(userId, productId);
            LOGGER.info(String.format("ShoppingList of user id %d with product %d was found ", userId, productId));
            return shoppingList;
//        } catch (DataNotFoundException e) {
//            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
//            LOGGER.error(e.getMessage());
//            return null;
//        }
    }

    @PreAuthorize("#shoppingList != null && #shoppingList.user != null && #shoppingList.user.id == authentication.user.id")
    @PostMapping("/")
    @ResponseStatus(value = HttpStatus.CREATED)
    public void addShoppingList(@RequestBody ShoppingList shoppingList) {
        shoppingListService.insert(shoppingList);
        LOGGER.info("New ShoppingList was created");
    }

    @PreAuthorize("#shoppingList != null && #shoppingList.user != null && #shoppingList.user.id == authentication.user.id")
    @PutMapping("/")
    @ResponseBody
    @ResponseStatus(value = HttpStatus.OK)
    public void updateShoppingList(@RequestBody ShoppingList shoppingList) throws IllegalArgumentException {
        int id = shoppingList.getUser().getId();
//        try {
            shoppingListService.update(shoppingList);
            LOGGER.info(String.format("ShoppingList of user id %d was updated", id));
//        } catch (rageServic e) {
//            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
//            LOGGER.error(String.format("Cannot update ShoppingList of user id %d", id), e);
//
//        }
    }

    @PreAuthorize("#shoppingList != null && #shoppingList.user != null && #shoppingList.user.id == authentication.user.id")
    @DeleteMapping("/")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteShoppingList(@RequestBody ShoppingList shoppingList) throws DataNotFoundException {
//        try {
            shoppingListService.delete(shoppingList);
            LOGGER.info("ShoppingList was deleted");
//        } catch (DataNotFoundException e) {
//            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
//            LOGGER.error(e.getMessage());
//        }
    }

}

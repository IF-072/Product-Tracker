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

    /**
     * Returns all user user shoppingLists
     *
     * @param userId user whose shoppingLists are are retrieved
     * @return list of user
     * @throws DataNotFoundException if list of shoppingLists is null
     */
    @PreAuthorize("#userId != null  && #userId == authentication.user.id")
    @GetMapping("/{userId}")
    @ResponseBody
    @ResponseStatus(value = HttpStatus.OK)
    public List<ShoppingList> getShoppingListByUserId(@PathVariable Integer userId) throws DataNotFoundException {
        List<ShoppingList> shoppingLists = shoppingListService.getByUserId(userId);
        LOGGER.info(String.format("Full ShoppingList of user id %d was found ", userId));
        return shoppingLists;
    }

    /**
     * This method returns one shoppingList
     *
     * @param userId    user whose shoppingLists are are retrieved
     * @param productId product from this shoppingList
     * @return shoppingList
     * @throws DataNotFoundException if shoppingLists is null
     */
    @PreAuthorize("#userId != null  && #userId == authentication.user.id && " +
            "@productSecurityService.hasPermissionToAccess(#productId)")
    @GetMapping("/{userId}/{productId}")
    @ResponseBody
    @ResponseStatus(value = HttpStatus.OK)
    public ShoppingList getByUserAndProductId(@PathVariable int userId, @PathVariable int productId)
            throws DataNotFoundException {

        ShoppingList shoppingList = shoppingListService.getByUserAndProductId(userId, productId);
        LOGGER.info(String.format("ShoppingList of user id %d with product %d was found ", userId, productId));
        return shoppingList;
    }

    /**
     * This method adds new shoppingList
     *
     * @param shoppingList this shoppingList will be added
     */
    @PreAuthorize("#shoppingList != null && #shoppingList.user != null &&" +
            "#shoppingList.user.id == authentication.user.id")
    @PostMapping("/")
    @ResponseStatus(value = HttpStatus.CREATED)
    public void addShoppingList(@RequestBody ShoppingList shoppingList) {
        shoppingListService.insert(shoppingList);
        LOGGER.info("New ShoppingList was created");
    }

    /**
     * This method updates shoppingList
     *
     * @param shoppingList this shoppingList will replace old shoppingList with the same id
     * @throws IllegalArgumentException if shoppingList.amount <= 0 or shoppingList.product == null or
     * shoppingList.user() == null
     */
    @PreAuthorize("#shoppingList != null && #shoppingList.user != null &&" +
            " #shoppingList.user.id == authentication.user.id")
    @PutMapping("/")
    @ResponseBody
    @ResponseStatus(value = HttpStatus.OK)
    public void updateShoppingList(@RequestBody ShoppingList shoppingList) throws IllegalArgumentException {
        int id = shoppingList.getUser().getId();
        shoppingListService.update(shoppingList);
        LOGGER.info(String.format("ShoppingList of user id %d was updated", id));
    }

    /**
     *  This method deletes shoppingList
     *
     * @param shoppingList this shoppingList will be deleted
     */
    @PreAuthorize("#shoppingList != null && #shoppingList.user != null && " +
            "#shoppingList.user.id == authentication.user.id")
    @DeleteMapping("/")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteShoppingList(@RequestBody ShoppingList shoppingList) {
        shoppingListService.delete(shoppingList);
        LOGGER.info("ShoppingList was deleted");
    }

}

package com.softserve.if072.restservice.service;

import com.softserve.if072.common.model.Product;
import com.softserve.if072.common.model.ShoppingList;
import com.softserve.if072.restservice.dao.mybatisdao.ShoppingListDAO;
import com.softserve.if072.restservice.exception.DataNotFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.apache.commons.collections.CollectionUtils;

import java.util.List;

/**
 * The ShoppingListService class is used to hold
 * business logic for working with the ShoppingList DAO.
 *
 * @author Roman Dyndyn
 */
@Service
public class ShoppingListService {
    private static final Logger LOGGER = LogManager.getLogger(ShoppingListService.class);
    private final ShoppingListDAO shoppingListDAO;

    @Autowired
    public ShoppingListService(final ShoppingListDAO shoppingListDAO) {
        this.shoppingListDAO = shoppingListDAO;
    }

    /**
     * Make request to a Shopping List DAO for retrieving
     * all shopping list records for current user.
     *
     * @param userId - current user unique identifier
     * @return list of shopping list records
     */
    public List<ShoppingList> getByUserId(final int userId) {
        final List<ShoppingList> list = shoppingListDAO.getByUserID(userId);
        if (CollectionUtils.isNotEmpty(list)) {
            return list;
        } else {
            throw new DataNotFoundException(String.format("ShoppingLists of user with id %d not found", userId));
        }
    }

    /**
     * Make request to a Shopping List DAO for retrieving
     * shopping list record for product.
     *
     * @param productId - product unique identifier
     * @return shopping list record
     */
    public ShoppingList getByProductId(final int productId) {
        return shoppingListDAO.getByProductId(productId);
    }

    /**
     * Make request to a Shopping List DAO for retrieving
     * shopping list records for product and current user.
     *
     * @param productId - product unique identifier
     * @param userId    - current user unique identifier
     * @return shopping list record
     */
    public ShoppingList getByUserAndProductId(final int userId, final int productId) {
        return shoppingListDAO.getByUserAndProductId(userId, productId);
    }

    /**
     * Make request to a Shopping List DAO for retrieving
     * all records of products containing in shopping list for current user.
     *
     * @param userId - current user unique identifier
     * @return list of product records
     */
    public List<Product> getProductsByUserId(final int userId) {
        final List<Product> list = shoppingListDAO.getProductsByUserId(userId);
        if (CollectionUtils.isNotEmpty(list)) {
            return list;
        } else {
            throw new DataNotFoundException(String.format("Product not found of user with id %d in shoppinglist",
                    userId));
        }
    }

    /**
     * Make request to a Shopping List DAO for inserting
     * shopping list record.
     *
     * @param shoppingList - shopping list that must be inserted
     */
    public void insert(final ShoppingList shoppingList) {
        final ShoppingList list = shoppingListDAO.getByClass(shoppingList);
        if (list == null && shoppingList != null) {
            shoppingListDAO.insert(shoppingList);
        }
    }

    /**
     * Make request to a Shopping List DAO for updating
     * shopping list record.
     *
     * @param shoppingList - shopping list that must be updated
     */
    public void update(final ShoppingList shoppingList) {
        if ((shoppingList.getAmount() <= 0) || (shoppingList.getProduct() == null)
                || (shoppingList.getUser() == null)) {
            throw new IllegalArgumentException("Incorrect fields by ShoppingList");
        } else {
            shoppingListDAO.update(shoppingList);
        }
    }

    /**
     * Make request to a Shopping List DAO for deleting
     * shopping list record.
     *
     * @param shoppingList - shopping list that must be deleted
     */
    public void delete(final ShoppingList shoppingList) {
        if (shoppingList != null) {
            shoppingListDAO.delete(shoppingList);
        } else {
            LOGGER.error("Illegal argument: shopppingList == null");
        }
    }

    /**
     * Make request to a Shopping List DAO for deleting
     * shopping list record that is related with current product.
     *
     * @param productId - product unique identifier
     */
    public void delete(final int productId) {
        if (productId > 0) {
            shoppingListDAO.deleteByProductId(productId);
        }
    }
}

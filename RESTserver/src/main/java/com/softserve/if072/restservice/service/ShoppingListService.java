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
 * The ShoppingListService class is used to hold business logic for working with the ShoppingList DAO
 *
 * @author Roman Dyndyn
 */
@Service
public class ShoppingListService {
    private static final Logger LOGGER = LogManager.getLogger(ShoppingListService.class);
    private ShoppingListDAO shoppingListDAO;

    @Autowired
    public ShoppingListService(ShoppingListDAO shoppingListDAO) {
        this.shoppingListDAO = shoppingListDAO;
    }

    public List<ShoppingList> getByUserId(int user_id) throws DataNotFoundException {
        List<ShoppingList> list = shoppingListDAO.getByUserID(user_id);
        if (CollectionUtils.isNotEmpty(list)) {
            return list;
        } else {
            throw new DataNotFoundException(String.format("ShoppingLists of user with id %d not found", user_id));
        }
    }

    public ShoppingList getByProductId(int product_id) {
        return shoppingListDAO.getByProductId(product_id);
    }

    public ShoppingList getByUserAndProductId(int user_id, int product_id) throws DataNotFoundException {
        ShoppingList list = shoppingListDAO.getByUserAndProductId(user_id, product_id);

        return list;
    }

    public List<Product> getProductsByUserId(int user_id) throws DataNotFoundException {
        List<Product> list = shoppingListDAO.getProductsByUserId(user_id);
        if (CollectionUtils.isNotEmpty(list)) {
            return list;
        } else {
            throw new DataNotFoundException(String.format("Product not found of user with id %d in shoppinglist",
                    user_id));
        }
    }


    public void insert(ShoppingList shoppingList) {
        ShoppingList list = shoppingListDAO.getByClass(shoppingList);
        if (list == null) {
            shoppingListDAO.insert(shoppingList);
        }
    }

    public void update(ShoppingList shoppingList) throws IllegalArgumentException {
        if ((shoppingList.getAmount() <= 0) || (shoppingList.getProduct() == null)
                || (shoppingList.getUser() == null)) {
            throw new IllegalArgumentException("Incorrect fields by ShoppingList");
        } else {
            shoppingListDAO.update(shoppingList);
        }
    }

    public void delete(ShoppingList shoppingList) {
        if (shoppingList != null) {
            shoppingListDAO.delete(shoppingList);
        } else {
            LOGGER.error("Illegal argument: shopppingList == null");
        }
    }

    public void delete(int productId) {
        shoppingListDAO.deleteByProductId(productId);
    }
}

package com.softserve.if072.restservice.service;

import com.softserve.if072.common.model.Product;
import com.softserve.if072.common.model.ShoppingList;
import com.softserve.if072.restservice.dao.mybatisdao.ShoppingListDAO;
import com.softserve.if072.restservice.exception.DataNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * The ShoppingListService class is used to hold business logic for working with the ShoppingList DAO
 *
 * @author Roman Dyndyn
 */
@Service
public class ShoppingListService {
    private ShoppingListDAO shoppingListDAO;

    @Autowired
    public ShoppingListService(ShoppingListDAO shoppingListDAO) {
        this.shoppingListDAO = shoppingListDAO;
    }

    public List<ShoppingList> getByUserId(int user_id) throws DataNotFoundException {
        List<ShoppingList> list = shoppingListDAO.getByUserID(user_id);
        if (!CollectionUtils.isEmpty(list)) {
            return list;
        } else {
            throw new DataNotFoundException("ShoppingLists not found");
        }
    }

    public ShoppingList getByProductId(int product_id) {
        return shoppingListDAO.getByProductId(product_id);
    }

    public ShoppingList getByUserAndProductId(int user_id, int product_id) throws DataNotFoundException {
        ShoppingList list = shoppingListDAO.getByUserAndProductId(user_id, product_id);
        if (list != null) {
            return list;
        } else {
            throw new DataNotFoundException("ShoppingList not found");
        }
    }

    public List<Product> getProductsByUserId(int user_id) throws DataNotFoundException {
        List<Product> list = shoppingListDAO.getProductsByUserId(user_id);
        if (!CollectionUtils.isEmpty(list)) {
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

    public void delete(ShoppingList shoppingList) throws DataNotFoundException {
        if (shoppingList != null) {
            shoppingListDAO.delete(shoppingList);
        } else {
            throw new DataNotFoundException("Illegal argument");
        }
    }

    public void delete(int productId) {
        shoppingListDAO.deleteByProductId(productId);
    }
}

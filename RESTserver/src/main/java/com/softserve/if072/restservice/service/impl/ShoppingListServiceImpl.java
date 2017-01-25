package com.softserve.if072.restservice.service.impl;

import com.softserve.if072.common.model.ShoppingList;
import com.softserve.if072.restservice.dao.mybatisdao.ShoppingListDAO;
import com.softserve.if072.restservice.service.ShoppingListService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by dyndyn on 21.01.2017.
 */
public class ShoppingListServiceImpl implements ShoppingListService {
    @Autowired
    private ShoppingListDAO shoppingListDAO;

    @Override
    public List<ShoppingList> getByUserId(int user_id) {
        return shoppingListDAO.getByUserID(user_id);

    }

    @Override
    public ShoppingList getById(int id) {
        return shoppingListDAO.getByID(id);
    }

    @Override
    public void insert(ShoppingList shoppingList) {
        shoppingListDAO.insert(shoppingList);
    }

    @Override
    public void update(ShoppingList shoppingList) {
        shoppingListDAO.update(shoppingList);
    }

    @Override
    public void delete(int id) {
        shoppingListDAO.delete(id);
    }
}

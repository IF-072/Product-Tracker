package com.softserve.if072.restservice.service.impl;

import com.softserve.if072.common.model.ShoppingList;
import com.softserve.if072.restservice.dao.mybatisdao.ShoppingListDAO;
import com.softserve.if072.restservice.exception.DataNotFoundException;
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
    public List<ShoppingList> getByUserId(int user_id) throws DataNotFoundException {
        List<ShoppingList> list = shoppingListDAO.getByUserID(user_id);
        if (list != null && !list.isEmpty()){
            return list;
        } else {
            throw new DataNotFoundException("ShoppingList not found");
        }
    }

    @Override
    public ShoppingList getById(int id) throws DataNotFoundException {
        ShoppingList shoppingList = shoppingListDAO.getByID(id);
        if (shoppingList != null){
            return shoppingList;
        } else {
            throw new DataNotFoundException(String.format("ShoppingList with id %d was not found", id));
        }
    }

    @Override
    public void insert(ShoppingList shoppingList) {
        shoppingListDAO.insert(shoppingList);
    }

    @Override
    public void update(ShoppingList shoppingList) throws DataNotFoundException {
        shoppingListDAO.update(shoppingList);
    }

    @Override
    public void delete(int id) throws DataNotFoundException {
        ShoppingList shoppingList = shoppingListDAO.getByID(id);
        if (shoppingList != null){
            shoppingListDAO.deleteById(id);
        } else {
            throw new DataNotFoundException(String.format("ShoppingList with id %d was not found", id));
        }
    }
}

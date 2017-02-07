package com.softserve.if072.restservice.service;

import com.softserve.if072.common.model.ShoppingList;
import com.softserve.if072.restservice.dao.mybatisdao.ShoppingListDAO;
import com.softserve.if072.restservice.exception.DataNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShoppingListService {

    @Autowired
    private ShoppingListDAO shoppingListDAO;

    public List<ShoppingList> getByUserId(int user_id) throws DataNotFoundException {
        List<ShoppingList> list = shoppingListDAO.getByUserID(user_id);
        if (list != null && !list.isEmpty()){
            return list;
        } else {
            throw new DataNotFoundException("ShoppingList not found");
        }
    }

    public ShoppingList getById(int id) throws DataNotFoundException {
        ShoppingList shoppingList = shoppingListDAO.getByID(id);
        if (shoppingList != null){
            return shoppingList;
        } else {
            throw new DataNotFoundException(String.format("ShoppingList with id %d was not found", id));
        }
    }

    public void insert(ShoppingList shoppingList) {
        shoppingListDAO.insert(shoppingList);
    }

    public void update(ShoppingList shoppingList) throws DataNotFoundException {
        shoppingListDAO.update(shoppingList);
    }

    public void delete(int id) throws DataNotFoundException {
        ShoppingList shoppingList = shoppingListDAO.getByID(id);
        if (shoppingList != null){
            shoppingListDAO.deleteById(id);
        } else {
            throw new DataNotFoundException(String.format("ShoppingList with id %d was not found", id));
        }
    }
}

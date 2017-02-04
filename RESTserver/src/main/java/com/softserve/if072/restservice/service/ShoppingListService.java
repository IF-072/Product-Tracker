package com.softserve.if072.restservice.service;

import com.softserve.if072.common.model.ShoppingList;
import com.softserve.if072.restservice.exception.DataNotFoundException;

import java.util.List;

/**
 * Created by dyndyn on 21.01.2017.
 */
public interface ShoppingListService {
    List<ShoppingList> getByUserId(int user_id) throws DataNotFoundException;

    ShoppingList getById(int id) throws DataNotFoundException;

    void insert(ShoppingList shoppingList);

    void update(ShoppingList shoppingList) throws DataNotFoundException;

    void delete(int id) throws DataNotFoundException;
}

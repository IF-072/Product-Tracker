package com.softserve.if072.restservice.service;

import com.softserve.if072.common.model.ShoppingList;

import java.util.List;

/**
 * Created by dyndyn on 21.01.2017.
 */
public interface ShoppingListService {
    List<ShoppingList> getByUserId(int user_id);

    ShoppingList getById(int id);

    void insert(ShoppingList shoppingList);

    void update(ShoppingList shoppingList);

    void delete(int id);
}

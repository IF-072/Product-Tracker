package com.softserve.if072.restservice.service;

import com.softserve.if072.common.model.ShoppingList;

/**
 * Created by dyndyn on 21.01.2017.
 */
public interface ShoppingListService {
    ShoppingList getByUserId(int user_id);

    void insert(ShoppingList shoppingList);

    void update(ShoppingList shoppingList);

    void delete(int id);
}

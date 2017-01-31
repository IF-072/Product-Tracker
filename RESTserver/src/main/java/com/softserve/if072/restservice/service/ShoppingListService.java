package com.softserve.if072.restservice.service;

import com.softserve.if072.common.model.ShoppingList;
import com.softserve.if072.restservice.Exception.DataSourceException;

import java.util.List;

/**
 * Created by dyndyn on 21.01.2017.
 */
public interface ShoppingListService {
    List<ShoppingList> getByUserId(int user_id) throws DataSourceException;

    ShoppingList getById(int id) throws DataSourceException;

    void insert(ShoppingList shoppingList);

    void update(ShoppingList shoppingList) throws DataSourceException;

    void delete(int id) throws DataSourceException;
}

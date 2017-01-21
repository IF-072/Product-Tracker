package com.softserve.if072.restservice.service.impl;

import com.softserve.if072.common.model.Product;
import com.softserve.if072.common.model.ShoppingList;
import com.softserve.if072.common.model.ShoppingListSimple;
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
    public ShoppingList getByUserId(int user_id) {
        List<ShoppingListSimple> list = shoppingListDAO.getByUserID(user_id);
        return new ShoppingList(list);
    }

    @Override
    public void insert(ShoppingList shoppingList) {
        int i = 0;
        for (Product product : shoppingList.getProducts().keySet()) {
            if (shoppingList.getId(i++) == 0)
                shoppingListDAO.insert(new ShoppingListSimple(shoppingList.getUser(), product, shoppingList.getAmount(product), 0));
        }
    }

    @Override
    public void update(ShoppingList shoppingList) {
        int i = 0;
        for (Product product : shoppingList.getProducts().keySet()) {
            if (shoppingList.getId(i) != 0)
                shoppingListDAO.insert(new ShoppingListSimple(shoppingList.getUser(), product, shoppingList.getAmount(product), shoppingList.getId(i++)));
        }
    }

    @Override
    public void delete(int id) {
        shoppingListDAO.delete(id);
    }
}

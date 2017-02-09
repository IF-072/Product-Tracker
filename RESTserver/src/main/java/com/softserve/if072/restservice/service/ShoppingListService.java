package com.softserve.if072.restservice.service;

import com.softserve.if072.common.model.ShoppingList;
import com.softserve.if072.restservice.dao.mybatisdao.ShoppingListDAO;
import com.softserve.if072.restservice.exception.DataNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShoppingListService {
public class ShoppingListService{
    private ShoppingListDAO shoppingListDAO;

    @Autowired
    public ShoppingListService(ShoppingListDAO shoppingListDAO) {
        this.shoppingListDAO = shoppingListDAO;
    }

    public List<ShoppingList> getByUserId(int user_id) throws DataNotFoundException {
        List<ShoppingList> list = shoppingListDAO.getByUserID(user_id);
        if (list != null && !list.isEmpty()) {
            return list;
        } else {
            throw new DataNotFoundException("ShoppingList not found");
        }
    }

    public void insert(ShoppingList shoppingList) {
        shoppingListDAO.insert(shoppingList);
    }
            return shoppingList;
        } else {
            throw new DataNotFoundException(String.format("ShoppingList with id %d was not found", id));
        }
    }

    public void update(ShoppingList shoppingList) throws DataNotFoundException {
        shoppingListDAO.update(shoppingList);
    }

    public void delete(ShoppingList shoppingList) throws DataNotFoundException {
        if (shoppingList != null) {
            shoppingListDAO.delete(shoppingList);
        } else {
            throw new DataNotFoundException("ShoppingList was not found");
        }
    }
            shoppingListDAO.deleteById(id);
        } else {
            throw new DataNotFoundException(String.format("ShoppingList with id %d was not found", id));
        }
    }
}

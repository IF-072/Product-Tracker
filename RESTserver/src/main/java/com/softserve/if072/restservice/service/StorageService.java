package com.softserve.if072.restservice.service;

import com.softserve.if072.common.model.ShoppingList;
import com.softserve.if072.common.model.Storage;
import com.softserve.if072.restservice.exception.DataNotFoundException;
import com.softserve.if072.restservice.dao.mybatisdao.StorageDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * The StorageService class is used to hold business logic for working with the storage DAO
 *
 * @author Roman Dyndyn
 */
@Service
public class StorageService{
    private StorageDAO storageDAO;
    private ShoppingListService shoppingListService;

    @Autowired
    public StorageService(StorageDAO storageDAO, ShoppingListService shoppingListService) {
        this.storageDAO = storageDAO;
        this.shoppingListService = shoppingListService;
    }

    public List<Storage> getByUserId(int user_id) throws DataNotFoundException {
        List<Storage> list = storageDAO.getByUserID(user_id);
        if (!CollectionUtils.isEmpty(list)) {
            return list;
        } else {
            throw new DataNotFoundException(String.format("Storages of user with id %d not found", user_id));
        }
    }

    public Storage getByProductId(int product_id){
        return storageDAO.getByProductID(product_id);
    }

    public void insert(Storage storage) {
        storageDAO.insert(storage);
    }

    public void insert(int userId, int productId, int amount) {
        storageDAO.insert(userId,productId,amount);
    }

    public void update(Storage storage) throws DataNotFoundException {
        if(storage.getAmount() < 0){
            throw new DataNotFoundException("illegal arguments!");
        }

        if (storage.getEndDate() != null) {
            storageDAO.update(storage);
        } else {
            storageDAO.updateAmount(storage);
        }
        if (storage.getAmount() <= 1){
            shoppingListService.insert(new ShoppingList(storage.getUser(), storage.getProduct(), 1));
        }
    }

    public void delete(Storage storage) throws DataNotFoundException {
        if (storage != null) {
            storageDAO.delete(storage);
        } else {
            throw new DataNotFoundException("Illegal argument");
        }
    }
}

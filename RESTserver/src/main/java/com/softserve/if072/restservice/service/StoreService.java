package com.softserve.if072.restservice.service;

import com.softserve.if072.common.model.Product;
import com.softserve.if072.common.model.Store;
import com.softserve.if072.restservice.dao.mybatisdao.StoreDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class StoreService {

    private final StoreDAO storeDAO;

    @Autowired
    public StoreService(StoreDAO storeDAO) {
        this.storeDAO = storeDAO;
    }

    @Transactional
    public List<Store> getAllStores() {
        List<Store> stores = storeDAO.getAll();
        if (!stores.isEmpty()){
            return stores;
        } else {
            throw new RuntimeException("Stores not found");
        }
    }

    @Transactional
    public Store getStoreByID(int id) {
        Store store = storeDAO.getByID(id);
        if (store != null){
            return store;
        } else {
            throw new RuntimeException("Store not found");
        }
    }

    @Transactional
    public void addStore(Store store) {storeDAO.insert(store);}

    @Transactional
    public void updateStore(Store store) {
        storeDAO.update(store);
    }

    @Transactional
    public void deleteStore(int id) {
        Store store = storeDAO.getByID(id);
        if (store != null){
            storeDAO.delete(id);
        } else {
            throw new RuntimeException("Store not found");
        }
    }

    @Transactional
    public List<Product> getProductsByStoreId(int storeId){
        List<Product> products = storeDAO.getProductsByStoreId(storeId);
        if (!products.isEmpty()){
            return products;
        } else {
            throw new RuntimeException("Products not found");
        }
    }
}


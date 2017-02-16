package com.softserve.if072.restservice.service;


import com.softserve.if072.common.model.Product;
import com.softserve.if072.common.model.Store;
import com.softserve.if072.restservice.dao.mybatisdao.StoreDAO;
import com.softserve.if072.restservice.exception.DataNotFoundException;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@PropertySource(value = {"classpath:message.properties"})
public class StoreService {

    private final StoreDAO storeDAO;

    @Value("${store.notFound}")
    private String storeNotFound;

    @Autowired
    public StoreService(StoreDAO storeDAO) {
        this.storeDAO = storeDAO;
    }

    @Transactional
    public List<Store> getAllStores(int userId) throws DataNotFoundException {
        List<Store> stores = storeDAO.getAllStoresByUser(userId);
        List<Store> enabledStores = new ArrayList<>();
        if (!stores.isEmpty()) {
            for (Store getStore : stores) {
                if (getStore.isEnabled()) {
                    enabledStores.add(getStore);
                }
            }
            return enabledStores;
        } else {
            throw new DataNotFoundException("Stores not found");
        }
    }

    @Transactional
    public Store getStoreByID(int id) throws DataNotFoundException {
        Store store = storeDAO.getByID(id);
        if (store != null) {
            return store;
        } else {
            throw new DataNotFoundException(String.format(storeNotFound, id));
        }
    }

    @Transactional
    public void addStore(Store store) {
        storeDAO.insert(store);
    }

    @Transactional
    public void updateStore(Store store) throws IllegalArgumentException {
        Store oldStore = storeDAO.getByID(store.getId());
        if (oldStore == null || store.getName().isEmpty() || store.getName() == "") {
            throw new IllegalArgumentException("illegal arguments!");
        }
        storeDAO.update(store);
    }

    @Transactional
    public void deleteStore(int id) throws DataNotFoundException {
        Store store = storeDAO.getByID(id);
        if (store != null) {
            storeDAO.deleteById(id);
        } else {
            throw new DataNotFoundException(String.format(storeNotFound, id));
        }
    }

    @Transactional
    public List<Product> getProductsByStoreId(int storeId, int userId) throws DataNotFoundException {
        List<Product> products = storeDAO.getProductsByStoreId(storeId, userId);
        if (CollectionUtils.isNotEmpty(products)) {
            return products;
        } else {
            throw new DataNotFoundException("Products not found");
        }
    }

    @Transactional
    public void deleteProductFromStoreById(int storeId, int productId) throws DataNotFoundException {
        Product product = storeDAO.getProductFromStoreById(storeId, productId);
        if (product != null) {
            storeDAO.deleteProductFromStoreById(storeId, productId);
        } else {
            throw new DataNotFoundException(String.format("Product %d from Store %d not found", productId, storeId));
        }
    }

    @Transactional
    public void addProductToStore(Store store, Product product) {
        storeDAO.addProductToStore(store, product);
    }

    @Transactional
    public Product getProductFromStoreById(int storeId, int productId) throws DataNotFoundException {
        Product product = storeDAO.getProductFromStoreById(storeId, productId);
        if (product != null) {
            return product;
        } else {
            throw new DataNotFoundException(String.format("Product %d from Store %d not found", productId, storeId));
        }
    }

}


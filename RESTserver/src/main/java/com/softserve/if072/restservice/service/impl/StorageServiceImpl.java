package com.softserve.if072.restservice.service.impl;

import com.softserve.if072.common.model.Product;
import com.softserve.if072.common.model.Storage;
import com.softserve.if072.common.model.StorageSimple;
import com.softserve.if072.restservice.dao.mybatisdao.StorageDAO;
import com.softserve.if072.restservice.service.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by dyndyn on 21.01.2017.
 */
@Service
public class StorageServiceImpl implements StorageService {
    @Autowired
    private StorageDAO storageDAO;

    public Storage getByUserId(int user_id) {
        List<StorageSimple> list = storageDAO.getByUserID(user_id);
        return new Storage(list);
    }

    @Override
    public void insert(Storage storage) {
        int i = 0;
        for (Product product : storage.getProducts().keySet()){
            storageDAO.insert(new StorageSimple(storage.getUser(), product, storage.getAmount(product), 0));
        }
    }

    @Override
    public void update(Storage storage) {
        int i = 0;
        for (Product product : storage.getProducts().keySet()){
            storageDAO.update(new StorageSimple(storage.getUser(), product, storage.getAmount(product), storage.getId(i++)));
        }
    }

    @Override
    public void delete(int id) {
        storageDAO.delete(id);
    }
}

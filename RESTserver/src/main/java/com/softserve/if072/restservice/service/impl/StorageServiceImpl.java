package com.softserve.if072.restservice.service.impl;

import com.softserve.if072.common.model.Storage;
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

    public List<Storage> getByUserId(int user_id) {
        return storageDAO.getByUserID(user_id);
    }

    public Storage getById(int id) {
        return storageDAO.getByID(id);
    }

    @Override
    public void insert(Storage storage) {
        storageDAO.insert(storage);
    }

    @Override
    public void update(Storage storage) {
        storageDAO.update(storage);
    }

    @Override
    public void delete(int id) {
        storageDAO.delete(id);
    }
}

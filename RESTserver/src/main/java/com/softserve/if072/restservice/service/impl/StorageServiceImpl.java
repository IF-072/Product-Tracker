package com.softserve.if072.restservice.service.impl;

import com.softserve.if072.common.model.Storage;

import com.softserve.if072.restservice.dao.mybatisdao.StorageDAO;
import com.softserve.if072.restservice.exception.DataNotFoundException;
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

    public List<Storage> getByUserId(int user_id) throws DataNotFoundException {
        List<Storage> list = storageDAO.getByUserID(user_id);
        if (list != null && !list.isEmpty()){
            return list;
        } else {
            throw new DataNotFoundException("Storages not found");
        }
    }

    public Storage getById(int id) throws DataNotFoundException {
        Storage storage = storageDAO.getByID(id);
        if (storage != null){
            return storage;
        } else {
            throw new DataNotFoundException(String.format("Storage with id %d was not found", id));
        }
    }

    @Override
    public void insert(Storage storage){
        storageDAO.insert(storage);
    }

    @Override
    public void update(Storage storage) throws DataNotFoundException {
        storageDAO.update(storage);
    }

    @Override
    public void delete(int id) throws DataNotFoundException {
        Storage storage = storageDAO.getByID(id);
        if (storage != null){
            storageDAO.deleteById(id);
        } else {
            throw new DataNotFoundException(String.format("Storage with id %d was not found", id));
        }
    }
}

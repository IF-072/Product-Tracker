package com.softserve.if072.restservice.service;

import com.softserve.if072.common.model.Storage;
import com.softserve.if072.restservice.exception.DataNotFoundException;
import com.softserve.if072.restservice.dao.mybatisdao.StorageDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by dyndyn on 21.01.2017.
 */
@Service
public class StorageService{
    private StorageDAO storageDAO;

    @Autowired
    public StorageService(StorageDAO storageDAO) {
        this.storageDAO = storageDAO;
    }

    public List<Storage> getByUserId(int user_id) throws DataNotFoundException {
        List<Storage> list = storageDAO.getByUserID(user_id);
        if (list != null && !list.isEmpty()) {
            return list;
        } else {
            throw new DataNotFoundException("Storages not found");
        }
    }

    public void insert(Storage storage) {
        storageDAO.insert(storage);
    }

    public void update(Storage storage) throws DataNotFoundException {
        if (storage.getEndDate() != null) {
            storageDAO.update(storage);
        } else {
            storageDAO.updateAmount(storage);
        }
    }

    public void delete(Storage storage) throws DataNotFoundException {
        if (storage != null) {
            storageDAO.delete(storage);
        } else {
            throw new DataNotFoundException("Storage was not found");
        }
    }
}

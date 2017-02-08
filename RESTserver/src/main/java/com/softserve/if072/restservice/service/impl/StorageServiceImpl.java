package com.softserve.if072.restservice.service.impl;

import com.softserve.if072.common.model.Storage;
import com.softserve.if072.restservice.Exception.DataSourceException;
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
    private StorageDAO storageDAO;

    @Autowired
    public StorageServiceImpl(StorageDAO storageDAO) {
        this.storageDAO = storageDAO;
    }

    public List<Storage> getByUserId(int user_id) throws DataSourceException {
        List<Storage> list = storageDAO.getByUserID(user_id);
        if (list != null && !list.isEmpty()) {
            return list;
        } else {
            throw new DataSourceException("Storages not found");
        }
    }

    @Override
    public void insert(Storage storage) {
        storageDAO.insert(storage);
    }

    @Override
    public void update(Storage storage) throws DataSourceException {
        if (storage.getEndDate() != null) {
            storageDAO.update(storage);
        } else {
            storageDAO.updateAmount(storage);
        }
    }

    @Override
    public void delete(Storage storage) throws DataSourceException {
        if (storage != null) {
            storageDAO.delete(storage);
        } else {
            throw new DataSourceException("Storage was not found");
        }
    }
}

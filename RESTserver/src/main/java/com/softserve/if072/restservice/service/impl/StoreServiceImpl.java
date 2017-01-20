package com.softserve.if072.restservice.service.impl;

import com.softserve.if072.common.model.Store;
import com.softserve.if072.restservice.dao.mybatisdao.StoreDAO;
import com.softserve.if072.restservice.service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StoreServiceImpl implements StoreService {

    @Autowired
    public StoreDAO storeDAO;

    @Override
    public List<Store> getAll() {return storeDAO.getAll();}

    @Override
    public Store getByID(int id) {return storeDAO.getByID(id);}

    @Override
    public void insert(Store store) {storeDAO.insert(store);}

    @Override
    public void update(Store store) {storeDAO.update(store);}

    @Override
    public void delete(int id) {storeDAO.delete(id);}
}

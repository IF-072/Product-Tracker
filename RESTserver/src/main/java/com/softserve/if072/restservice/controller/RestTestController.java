package com.softserve.if072.restservice.controller;

import com.softserve.if072.common.model.Storage;
import com.softserve.if072.restservice.dao.mybatisdao.*;
import com.softserve.if072.common.model.Unit;
import com.softserve.if072.restservice.service.impl.StorageServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class RestTestController {

    @Autowired
    private UnitDAO unitDAO;

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private ProductDAO productDAO;

    @Autowired
    private StorageDAO storageDAO;

    @Autowired
    private StorageServiceImpl service;

    @Autowired
    private StoreDAO storeDAO;

    @RequestMapping(value = "/")
    @ResponseBody
    public List<Unit> getTestUnit(){
        List<Unit> list = unitDAO.getAll();
        return list;
    }

    @RequestMapping(value = "/rdyn")
    @ResponseBody
    public Storage getTestDyn(){
        Storage storage = new Storage();
        storage.setUser(userDAO.getByID(2));
        storage.getProducts().put(productDAO.getByID(2),2);
        service.insert(storage);
        return service.getByUserId(2);
    }
}

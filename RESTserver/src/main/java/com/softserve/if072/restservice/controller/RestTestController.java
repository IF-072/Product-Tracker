package com.softserve.if072.restservice.controller;

import com.softserve.if072.common.model.*;
import com.softserve.if072.restservice.dao.ShoppingListDAO;
import com.softserve.if072.restservice.dao.StorageDAO;
import com.softserve.if072.restservice.dao.UnitDAO;
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
    private ShoppingListDAO shoppingListDAO;

    @RequestMapping(value = "/")
    @ResponseBody
    public List<Unit> getTestUnit(){
        List<Unit> list = unitDAO.getAll();
        return list;
    }

    @RequestMapping(value = "/rdyn")
    @ResponseBody
    public String getTest(){
        return shoppingListDAO.getByID(1).toString();
    }
}

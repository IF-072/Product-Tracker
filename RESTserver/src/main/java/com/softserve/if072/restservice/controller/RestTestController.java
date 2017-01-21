package com.softserve.if072.restservice.controller;

import com.softserve.if072.common.model.Storage;
import com.softserve.if072.restservice.dao.mybatisdao.UnitDAO;
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

    @RequestMapping(value = "/")
    @ResponseBody
    public List<Unit> getTestUnit(){
        List<Unit> list = unitDAO.getAll();
        return list;
    }

}

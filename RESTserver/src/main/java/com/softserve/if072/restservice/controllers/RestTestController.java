package com.softserve.if072.restservice.controllers;

import com.softserve.if072.common.dto.Unit;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class RestTestController {

    @RequestMapping(value = "/")
    @ResponseBody
    public Unit getTestUnit(){
        Unit unit = new Unit(10, "meters");
        return unit;
    }
}

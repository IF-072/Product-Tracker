package com.softserve.academy.controllers;

import com.softserve.academy.mappers.UnitMapper;
import com.softserve.academy.services.UnitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
public class MainPageController {

    @RequestMapping("/")
    public String homePage() {
        return "index";
    }
}
package com.softserve.academy.controllers;

import com.softserve.academy.mappers.UnitMapper;
import com.softserve.academy.services.UnitService;
import com.softserve.academy.services.UnitServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;
import java.util.Observable;

@Controller
public class MainPageController {

    @Autowired
    private UnitMapper unitMapper;

    @Autowired
    private UnitService unitService;

    @RequestMapping("/")
    public String homePage() {
        return "redirect:/index?id=1";
    }


    @RequestMapping("/index")
    public String indexPage(@RequestParam(value = "id") int id,  Map<String, Object> map) {
        String pageTitle = "Spring Page Title";
        String pageBody = "Unit with id =  is " + unitService.getUnitById(id).getName();

        map.put("title", pageTitle);
        map.put("body", pageBody);

        return "index";
    }

    @RequestMapping("/index/all")
    public String getAllUnits(Map<String, Object> map) {
        String pageTitle = "Spring Page Title";
        String pageBody = "Units count: " + unitService.getAllUnits().size();

        map.put("title", pageTitle);
        map.put("body", pageBody);

        return "index";
    }
}
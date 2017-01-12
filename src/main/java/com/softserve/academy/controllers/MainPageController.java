package com.softserve.academy.controllers;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainPageController {
    private static final Logger LOG = Logger.getLogger(MainPageController.class);

    @RequestMapping("/")
    public String homePage() {
        return "index";
    }
}
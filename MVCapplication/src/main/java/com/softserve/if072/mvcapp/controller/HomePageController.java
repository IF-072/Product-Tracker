package com.softserve.if072.mvcapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomePageController {

    @RequestMapping("/")
    public String getHomePage(Model model){
        model.addAttribute("title", "MVC application title");
        model.addAttribute("body", "MVC application body");

        return "index";
    }
}

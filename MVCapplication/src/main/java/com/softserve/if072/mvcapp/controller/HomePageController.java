package com.softserve.if072.mvcapp.controller;

import com.softserve.if072.common.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomePageController extends BaseController {

    @RequestMapping({"/", "/home"})
    public String getHomePage() {
        User user = getCurrentUser();
        if(user != null) {
            return "home";
        }
        else {
            return "redirect:login";
        }
    }
}

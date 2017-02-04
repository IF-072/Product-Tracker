package com.softserve.if072.restservice.controller;

import com.softserve.if072.common.model.User;
import com.softserve.if072.restservice.dao.mybatisdao.UnitDAO;
import com.softserve.if072.restservice.security.authentication.UserAuthenticationProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/api")
public class RestTestController {

    @Autowired
    private UnitDAO unitDAO;

    @RequestMapping(value = "/test")
    @ResponseBody
    public User testController() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth.isAuthenticated()) {
            UserAuthenticationProxy userProxy = (UserAuthenticationProxy) auth;
            if (userProxy != null)
                return userProxy.getUser();
        }
        return null;
    }

    @RequestMapping(value = "/test/key")
    @ResponseBody
    public String testControllerSecond() {
       return "key";
    }

}

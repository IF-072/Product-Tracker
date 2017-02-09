package com.softserve.if072.restservice.controller;

import com.softserve.if072.common.model.User;
import com.softserve.if072.restservice.security.authentication.AuthenticatedUserProxy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/api")
public class SecurityTestController {

    @RequestMapping(value = "/security/user/current")
    @ResponseBody
    public User testController() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth.isAuthenticated()) {
            AuthenticatedUserProxy userProxy = (AuthenticatedUserProxy) auth;
            if (userProxy != null)
                return userProxy.getUser();
        }
        return null;
    }

}

package com.softserve.if072.mvcapp.controller;

import com.softserve.if072.common.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.client.RestTemplate;

@PropertySource({"classpath:application.properties", "classpath:message.properties"})
abstract public class BaseController {

    @Value("${application.authenticationCookieName}")
    private String tokenHeaderName;

    @Value("${service.user.current}")
    private String getCurrentUserURL;

    @Autowired
    protected RestTemplate template;

    protected RestTemplate getRestTemplate(){
          return template;
    }

    protected User getCurrentUser(){
        RestTemplate restTemplate = getRestTemplate();
        User user = restTemplate.getForObject(getCurrentUserURL, User.class);
        return user;
    }



}

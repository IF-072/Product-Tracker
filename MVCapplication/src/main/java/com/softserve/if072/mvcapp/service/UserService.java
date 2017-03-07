package com.softserve.if072.mvcapp.service;

import com.softserve.if072.common.model.User;
import com.softserve.if072.mvcapp.controller.ExceptionHandlerController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Service
public class UserService {

    private RestTemplate restTemplate;

    @Value("${application.authenticationCookieName}")
    private String tokenHeaderName;

    @Value("${service.user.current}")
    private String getCurrentUserURL;

    @Autowired
    public UserService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    /**
     * Method provides access to current logged in {@link User} instance. If current user is not logged in, throws
     * {@link HttpClientErrorException}, which should be caught and handled by {@link ExceptionHandlerController}
     *
     * @return current User instance
     */
    public User getCurrentUser(){
        User user = restTemplate.getForObject(getCurrentUserURL, User.class);
        return user;
    }
}

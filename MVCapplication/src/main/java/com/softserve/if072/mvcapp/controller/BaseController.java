package com.softserve.if072.mvcapp.controller;

import com.softserve.if072.common.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

/**
 * Abstract Controller class that provides access to RestTemplate bean and current logged-in user instance.
 * Should be extended by all the controllers which have access to restricted service URLs
 *
 * @author Igor Parada
 */
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

    /**
     * Method provides access to current logged in {@link User} instance. If current user is not logged in, throws
     * {@link HttpClientErrorException}, which should be caught and handled by {@link ExceptionHandlerController}
     *
     * @return current User instance
     */
    protected User getCurrentUser(){
        RestTemplate restTemplate = getRestTemplate();
        User user = restTemplate.getForObject(getCurrentUserURL, User.class);
        return user;
    }



}

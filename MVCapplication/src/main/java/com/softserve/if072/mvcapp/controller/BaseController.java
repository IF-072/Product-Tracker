package com.softserve.if072.mvcapp.controller;

import com.softserve.if072.common.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@PropertySource({"classpath:application.properties", "classpath:message.properties"})
abstract public class BaseController {

    @Value("${application.authenticationCookieName}")
    private String tokenHeaderName;

    @Value("${login.informationMessage}")
    private String informationMessage;

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


    @ExceptionHandler(HttpClientErrorException.class)
    public String handleRestClientException(HttpClientErrorException e, final RedirectAttributes redirectAttributes) {
        HttpStatus statusCode = e.getStatusCode();

        if(statusCode.equals(HttpStatus.FORBIDDEN)){
            return "403";
        }

        if(statusCode.equals(HttpStatus.NOT_FOUND)){
            return "404";
        }

        if(statusCode.equals(HttpStatus.UNAUTHORIZED)) {
            redirectAttributes.addFlashAttribute(informationMessage, "You must be logged in to view this page");
            return "redirect:/login";
        }

        redirectAttributes.addFlashAttribute(informationMessage, "Something went wrong... Please log in again");
        return "redirect:/login";

    }
}

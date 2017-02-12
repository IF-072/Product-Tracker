package com.softserve.if072.mvcapp.controller.core;

import com.softserve.if072.mvcapp.controller.interceptor.AddTokenRequestInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Collections;

@PropertySource("classpath:application.properties")
public class RestClientController {

    @Value("${application.authenticationCookieName}")
    private String tokenHeaderName;

    @Autowired
    protected RestTemplate template;

    protected RestTemplate getRestTemplate(HttpServletRequest request){
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : request.getCookies()) {
                if (tokenHeaderName.equals(cookie.getName())) {
                    template.setInterceptors(Collections.singletonList(new AddTokenRequestInterceptor(tokenHeaderName,
                            cookie.getValue())));
                }
            }
        }

        return template;
    }


    @ExceptionHandler(HttpClientErrorException.class)
    public String handleException(HttpClientErrorException e, final RedirectAttributes redirectAttributes) {

        HttpStatus statusCode = e.getStatusCode();
        if(statusCode.equals(HttpStatus.UNAUTHORIZED)) {
            redirectAttributes.addFlashAttribute("informationMessage", "You must be logged in to view this page");
        } else {
            redirectAttributes.addFlashAttribute("informationMessage", "Something went wrong... Please log in again");
        }
        return "redirect:login";
    }
}

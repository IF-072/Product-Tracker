package com.softserve.if072.mvcapp.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * The ExceptionHandlerController class is used to provide methods that handler common exceptions
 *
 * @author Igor Kryviuk
 */
@ControllerAdvice
@PropertySource({"classpath:message.properties"})
public class ExceptionHandlerController {

    @Value("${login.informationMessage}")
    private String informationMessage;

    private static final Logger LOGGER = LogManager.getLogger();

    @ExceptionHandler(HttpClientErrorException.class)
    public String handleRestClientException(HttpClientErrorException e, final RedirectAttributes redirectAttributes) {
        HttpStatus statusCode = e.getStatusCode();
        LOGGER.error(e.getMessage());

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

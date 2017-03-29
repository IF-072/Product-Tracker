package com.softserve.if072.mvcapp.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * The ExceptionHandlerController class is used to provide methods that handle common exceptions
 *
 * @author Igor Kryviuk
 * @author Igor Parada
 */
@ControllerAdvice
public class ExceptionHandlerController {
    @Value("login.unauthorized")
    private String unauthorizedAccess;
    @Value("login.generalError")
    private String generalError;
    private static final Logger LOGGER = LogManager.getLogger();

    @ExceptionHandler(HttpClientErrorException.class)
    public String handleRestClientException(Model model, HttpClientErrorException e, final RedirectAttributes redirectAttributes) {

        HttpStatus statusCode = e.getStatusCode();
        if (statusCode.equals(HttpStatus.UNPROCESSABLE_ENTITY)) {
            model.addAttribute("productName", e.getResponseBodyAsString());

            return "emptyAnalytics";
        }

        LOGGER.error(e.getMessage(), e);

        if (statusCode.equals(HttpStatus.FORBIDDEN)) {
            return "403";
        }
        if (statusCode.equals(HttpStatus.NOT_FOUND)) {
            return "404";
        }
        if (statusCode.equals(HttpStatus.UNAUTHORIZED)) {
            redirectAttributes.addFlashAttribute("informationMessage", unauthorizedAccess);
            return "redirect:/login";
        }

        //by default, return general error page
        return "generalError";
    }

    @ExceptionHandler(HttpServerErrorException.class)
    public String handleRestServerException(HttpServerErrorException e) {
        LOGGER.error(e.getMessage(), e);
        return "generalError";
    }
}

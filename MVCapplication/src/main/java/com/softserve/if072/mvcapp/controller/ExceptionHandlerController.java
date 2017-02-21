package com.softserve.if072.mvcapp.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
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
@PropertySource({"classpath:message.properties"})
public class ExceptionHandlerController {

    @Value("${login.unauthorized}")
    private String unathorizedAccess;

    @Value("${login.generalError}")
    private String generalError;

    private static final Logger LOGGER = LogManager.getLogger();

    @ExceptionHandler(HttpClientErrorException.class)
    public String handleRestClientException(HttpClientErrorException e, final RedirectAttributes redirectAttributes) {
        LOGGER.error(e.getMessage());

        HttpStatus statusCode = e.getStatusCode();
        if (statusCode.equals(HttpStatus.FORBIDDEN)) {
            return "403";
        }
        if (statusCode.equals(HttpStatus.NOT_FOUND)) {
            return "404";
        }
        if (statusCode.equals(HttpStatus.UNAUTHORIZED)) {
            redirectAttributes.addFlashAttribute("informationMessage", unathorizedAccess);
            return "redirect:/login";
        }

        redirectAttributes.addFlashAttribute("informationMessage", generalError);
        return "redirect:/login";
    }

    @ExceptionHandler(HttpServerErrorException.class)
    public String handleRestServerException(HttpClientErrorException e) {
        LOGGER.error(e.getMessage());
        return "generalError";
    }
}

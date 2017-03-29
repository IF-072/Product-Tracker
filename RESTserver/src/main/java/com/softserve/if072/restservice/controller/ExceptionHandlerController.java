package com.softserve.if072.restservice.controller;

import com.softserve.if072.restservice.exception.DataNotFoundException;
import com.softserve.if072.restservice.exception.NotEnoughDataException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * The ExceptionHandlerController class is used to provide methods that handler common exceptions
 *
 * @author Igor Kryviuk
 */
@ControllerAdvice
public class ExceptionHandlerController {
    private static final Logger LOGGER = LogManager.getLogger(ExceptionHandlerController.class);

    @ExceptionHandler(DataNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String dataNotFound(DataNotFoundException e) {
        LOGGER.error(e.getMessage(), e);

        return null;
    }

    @ExceptionHandler(DataAccessException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String dataAccessException(DataAccessException e) {
        LOGGER.error(e.getMessage(), e);

        return null;
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public String accessDeniedException(AccessDeniedException e) {
        LOGGER.error(e.getMessage(), e);

        return null;
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.EXPECTATION_FAILED)
    public String IllegalArgumentException(IllegalArgumentException e) {
        LOGGER.error(e.getMessage(), e);

        return null;
    }

    @ExceptionHandler(NotEnoughDataException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ResponseBody
    public String notEnoughDateException(NotEnoughDataException e) {
        LOGGER.error(e.getMessage());

        return e.getProductName();
    }
}

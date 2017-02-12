package com.softserve.if072.restservice.controller;

import com.softserve.if072.common.model.History;
import com.softserve.if072.restservice.service.HistoryService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * The HistoryController class is used to mapping requests for
 * history resources
 *
 * @author Igor Kryviuk
 */
@RestController
@RequestMapping("/users/{userID}/histories")
public class HistoryController {
    @Autowired
    private HistoryService historyService;
    private static final Logger LOGGER = LogManager.getLogger(HistoryController.class);

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public List<History> getByUserId(@PathVariable int userID) {
        return historyService.getByUserId(userID);
    }

    @GetMapping("/products/{productID}")
    @ResponseStatus(HttpStatus.OK)
    public List<History> getByProductId(@PathVariable int userID, @PathVariable int productID) {
        return historyService.getByProductId(userID, productID);
    }

    @PostMapping()
    @ResponseStatus(value = HttpStatus.CREATED)
    public void insert(@RequestBody History history) {
        historyService.insert(history);
    }

    @PutMapping()
    @ResponseStatus(value = HttpStatus.OK)
    public void update(@RequestBody History history) {
        historyService.update(history);
    }

    @DeleteMapping()
    @ResponseStatus(value = HttpStatus.OK)
    public void delete(@RequestBody History history) {
        historyService.delete(history);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String dataNotFound(IllegalArgumentException e) {
        LOGGER.error(e.getMessage());
        return e.getMessage();
    }

    @ExceptionHandler(DataAccessException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String dataAccessException(DataAccessException e) {
        LOGGER.error(e.getMessage());
        return e.getMessage();
    }
}
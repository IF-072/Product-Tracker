package com.softserve.if072.restservice.controller;

/**
 * Created by dyndyn on 21.01.2017.
 */

import com.softserve.if072.common.model.Storage;
import com.softserve.if072.restservice.exception.DataNotFoundException;
import com.softserve.if072.restservice.service.StorageService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(value = "/storage")
public class StorageController {

    public static final Logger LOGGER =  LogManager.getLogger(StorageController.class);
    private StorageService storageService;

    @Autowired
    public StorageController(StorageService storeService){
        this.storageService = storeService;
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public void delete(@PathVariable int id, HttpServletResponse response) {
        try {
            storageService.delete(id);
            LOGGER.info(String.format("Storage with id %d was deleted", id));
        } catch (DataNotFoundException e) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            LOGGER.error(String.format("Cannot deleteById Storage with id %d", id), e);
        }
    }

    @GetMapping(value = "/getByUser/{user_id}")
    @ResponseBody
    @ResponseStatus(value = HttpStatus.OK)
    public List<Storage> getByUserId(@PathVariable int user_id, HttpServletResponse response) {
        try {
            List<Storage> stores = storageService.getByUserId(user_id);
            LOGGER.info("All Storages were found");
            return stores;
        } catch (DataNotFoundException e) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            LOGGER.error("Storages were not found", e);
            return null;
        }
    }

    @GetMapping(value = "/{id}")
    @ResponseBody
    @ResponseStatus(value = HttpStatus.OK)
    public Storage getById(@PathVariable int id, HttpServletResponse response) {
        try {
            Storage store = storageService.getById(id);
            LOGGER.info(String.format("Storage with id %d was retrieved", id));
            return store;
        } catch (DataNotFoundException e) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            LOGGER.error(String.format("Storage with id %d was not found", id), e);
            return null;
        }
    }

    @PostMapping(value = "/")
    @ResponseStatus(value = HttpStatus.OK)
    public void insert(@RequestBody Storage storage){
        storageService.insert(storage);
        LOGGER.info("New Storage was created");
    }

    @PutMapping(value = "/")
    @ResponseStatus(value = HttpStatus.OK)
    public void update(@RequestBody Storage storage, HttpServletResponse response) {
        int id = storage.getId();
        try {
            storageService.update(storage);
            LOGGER.info(String.format("Store with id %d was updated", id));
        } catch (DataNotFoundException e) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            LOGGER.error(String.format("Cannot update Storage with id %d", id), e);
        }
    }
}

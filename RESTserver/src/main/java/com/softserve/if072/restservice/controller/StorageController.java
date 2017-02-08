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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping(value = "/storage")
public class StorageController {

    public static final Logger LOGGER = LogManager.getLogger(StorageController.class);
    private StorageService storageService;

    @Autowired
    public StorageController(StorageService storageService) {
        this.storageService = storageService;
    }

    @DeleteMapping()
    @ResponseStatus(value = HttpStatus.OK)
    public void delete(@RequestBody Storage storage, HttpServletResponse response) {
        try {
            storageService.delete(storage);
            LOGGER.info("Storage was deleted");
        } catch (DataNotFoundException e) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            LOGGER.error("Cannot delete Storage with");
        }
    }

    @GetMapping(value = "/{user_id}")
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


    @PostMapping(value = "/")
    @ResponseStatus(value = HttpStatus.CREATED)
    public void insert(@RequestBody Storage storage) {
        storageService.insert(storage);
        LOGGER.info("New Storage was created");
    }

    @PutMapping(value = "/")
    @ResponseStatus(value = HttpStatus.OK)
    public void update(@RequestBody Storage storage, HttpServletResponse response) {
        try {
            storageService.update(storage);
            LOGGER.info("Store was updated");
        } catch (DataNotFoundException e) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            LOGGER.error("Cannot update Storage", e);
        }
    }
}

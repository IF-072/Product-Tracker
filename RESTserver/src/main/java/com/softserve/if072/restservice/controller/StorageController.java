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
import org.springframework.security.access.prepost.PreAuthorize;
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
@RequestMapping(value = "/api/storage")
public class StorageController {

    private static final Logger LOGGER = LogManager.getLogger(StorageController.class);
    private StorageService storageService;

    @Autowired
    public StorageController(StorageService storageService) {
        this.storageService = storageService;
    }

    @PreAuthorize("#storage.user != null && #storage.user.id == authentication.user.id")
    @DeleteMapping()
    @ResponseStatus(value = HttpStatus.OK)
    public void delete(@RequestBody Storage storage, HttpServletResponse response) {
        try {
            storageService.delete(storage);
            LOGGER.info(String.format("Storage with user's id %d and product's id %d was deleted", storage.getUser().getId(), storage.getProduct().getId()));
        } catch (DataNotFoundException e) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            LOGGER.error(String.format("Cannot delete Storage with user's id %d and product's id %d", storage.getUser().getId(), storage.getProduct().getId()), e);
        }
    }

    @PreAuthorize("#user_id == authentication.user.id")
    @GetMapping(value = "/{user_id}")
    @ResponseBody
    @ResponseStatus(value = HttpStatus.OK)
    public List<Storage> getByUserId(@PathVariable int user_id, HttpServletResponse response) {
        try {
            List<Storage> stores = storageService.getByUserId(user_id);
            LOGGER.info(String.format("All Storages of user with id %d were found", user_id));
            return stores;
        } catch (DataNotFoundException e) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            LOGGER.error(String.format("Storages of user with id %d were not found", user_id), e);
            return null;
        }
    }

    @PreAuthorize("#user_id == authentication.user.id")
    @GetMapping(value = "/{user_id}/{product_id}")
    @ResponseBody
    @ResponseStatus(value = HttpStatus.OK)
    public Storage getByProductId(@PathVariable int user_id, @PathVariable int product_id, HttpServletResponse response) {

        Storage store = storageService.getByProductId(product_id);
        LOGGER.info(String.format("Storage with id %d was found", product_id));
        return store;

    }

    @PreAuthorize("#storage.user != null && #storage.user.id == authentication.user.id")
    @PostMapping(value = "/")
    @ResponseStatus(value = HttpStatus.CREATED)
    public void insert(@RequestBody Storage storage) {
        storageService.insert(storage);
        LOGGER.info("New Storage was created");
    }

    @PreAuthorize("#storage.user != null && #storage.user.id == authentication.user.id")
    @PutMapping(value = "/")
    @ResponseStatus(value = HttpStatus.OK)
    public void update(@RequestBody Storage storage, HttpServletResponse response) {
        try {
            storageService.update(storage);
            LOGGER.info(String.format("Storage with user's id %d and product's id %d was updated", storage.getUser().getId(), storage.getProduct().getId()));
        } catch (DataNotFoundException e) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            LOGGER.error(String.format("Cannot update Storage with user's id %d and product's id %d", storage.getUser().getId(), storage.getProduct().getId()), e);
        }
    }
}

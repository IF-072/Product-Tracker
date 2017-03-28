package com.softserve.if072.restservice.controller;

import com.softserve.if072.common.model.Storage;
import com.softserve.if072.common.model.dto.StorageDTO;
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

import java.sql.Timestamp;
import java.util.List;

/**
 * The StorageController class handles requests for storage resources.
 *
 * @author Roman Dyndyn
 */
@RestController
@RequestMapping(value = "/api/storage")
public class StorageController {

    private static final Logger LOGGER = LogManager.getLogger(StorageController.class);
    private final StorageService storageService;

    @Autowired
    public StorageController(final StorageService storageService) {
        this.storageService = storageService;
    }

    /**
     * Handles requests for deleting a storage record
     * from the storage of current user.
     *
     * @param storage - storage that must be deleted
     */
    @PreAuthorize("#storage.user != null && #storage.user.id == authentication.user.id")
    @DeleteMapping()
    @ResponseStatus(value = HttpStatus.OK)
    public void delete(@RequestBody final Storage storage) {
        storageService.delete(storage);
        LOGGER.info(String.format("Storage with user's id %d and product's id %d was deleted",
                storage.getUser().getId(), storage.getProduct().getId()));
    }

    /**
     * Handles requests for retrieving all storage records for current user.
     *
     * @param userId - current user unique identifier
     * @return list of storage records
     */
    @PreAuthorize("#userId == authentication.user.id")
    @GetMapping(value = "/{userId}")
    @ResponseBody
    @ResponseStatus(value = HttpStatus.OK)
    public List<Storage> getByUserId(@PathVariable final int userId) {

        final List<Storage> storage = storageService.getByUserId(userId);
        LOGGER.info(String.format("All Storages of user with id %d were found", userId));
        return storage;

    }

    /**
     * Handles requests for retrieving storage record for current product.
     *
     * @param userId    - current user unique identifier
     * @param productId - product unique identifier
     * @return storage record
     */
    @PreAuthorize("#userId == authentication.user.id")
    @GetMapping(value = "/{userId}/{productId}")
    @ResponseBody
    @ResponseStatus(value = HttpStatus.OK)
    public Storage getByProductId(@PathVariable final int userId, @PathVariable final int productId) {

        final Storage storage = storageService.getByProductId(productId);
        LOGGER.info(String.format("Storage with id %d was found", productId));
        return storage;

    }

    /**
     * Handles requests for inserting a storage record in the storage.
     *
     * @param storage - storage that must be inserted
     */
    @PreAuthorize("#storage.user != null && #storage.user.id == authentication.user.id")
    @PostMapping(value = "/")
    @ResponseStatus(value = HttpStatus.CREATED)
    public void insert(@RequestBody final Storage storage) {
        storageService.insert(storage);
        LOGGER.info("New Storage was created");
    }

    /**
     * Handles requests for updating a storage record in the storage.
     *
     * @param storage - storage that must be updated
     */
    @PreAuthorize("#storage.user != null && #storage.user.id == authentication.user.id")
    @PutMapping(value = "/")
    @ResponseStatus(value = HttpStatus.OK)
    public void update(@RequestBody final Storage storage) {
        storageService.update(storage);
        LOGGER.info(String.format("Storage with user's id %d and product's id %d was updated",
                storage.getUser().getId(), storage.getProduct().getId()));
    }

    /**
     * Handles requests for updating a storage record in the storage.
     *
     * @param storage - storage that must be updated
     */
    @PreAuthorize("#storage.userId == authentication.user.id")
    @PostMapping(value = "/dto")
    @ResponseBody
    @ResponseStatus(value = HttpStatus.OK)
    public Timestamp updateByDTO(@RequestBody final StorageDTO storage) {
        storageService.update(storage);
        LOGGER.info(String.format("Storage with user's id %d and product's id %d was updated",
                storage.getUserId(), storage.getProductId()));
        return storageService.getByProductId(storage.getProductId()).getEndDate();
    }
}

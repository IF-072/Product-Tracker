package com.softserve.if072.restservice.controller;

import com.softserve.if072.common.model.Image;
import com.softserve.if072.restservice.exception.DataNotFoundException;
import com.softserve.if072.restservice.service.ImageService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * The class contains methods to add, read and delete image from database using REST Service
 *
 * @author Vitaliy Malisevych
 */

@RestController
@RequestMapping("/api/image")
public class  ImageController {

    public static final Logger LOGGER =  LogManager.getLogger(ProductController.class);
    private ImageService imageService;

    @Autowired
    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    @Value("${image.notFound}")
    private String imageNotFound;

    /**
     * Returns image by id
     *
     * @param imageId id of image
     * @return image
     */

    @PreAuthorize("@imageSecurityService.hasPermissionToAccess(#imageId)")
    @RequestMapping(value = "/{imageId}", method = RequestMethod.GET)
    @ResponseBody
    public Image getImageById(@PathVariable("imageId") int imageId, HttpServletResponse response) {

        try {
            Image image = imageService.getById(imageId);
            LOGGER.info("Image was found");
            return image;
        } catch (DataNotFoundException e) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            LOGGER.error(e.getMessage(), e);
            return null;
        }

    }

    /**
     * Uploads image to DataBase
     *
     * @param userId user whose image will be uploaded
     */

    @PreAuthorize("#userId == authentication.user.id")
    @RequestMapping(value = "/upload/{userId}", method = RequestMethod.POST)
    @ResponseBody
    public void uploadImageObject(@RequestBody Image image, @PathVariable int userId) {
        if (image != null) {
            imageService.insert(image);
        }
    }

    /**
     * Deletes image from DataBase
     *
     * @param imageId id of image
     */

    @PreAuthorize("@imageSecurityService.hasPermissionToAccess(#imageId)")
    @RequestMapping(value = "/delete/{imageId}", method = RequestMethod.DELETE)
    @ResponseBody
    public void deleteImage(@PathVariable("imageId") int imageId, HttpServletResponse response) {

        try {
            imageService.delete(imageId);
            LOGGER.info(String.format("Image with id %d was deleted", imageId));
        } catch (DataNotFoundException e) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            LOGGER.error(String.format(imageNotFound, imageId), e);
        }

    }

    /**
     * Returns last inserted id
     *
     * @param userId user whose last inserted image's id will be returned
     * @return id
     */

    @PreAuthorize("#userId == authentication.user.id")
    @RequestMapping(value = "/getLastId/{userId}", method = RequestMethod.GET)
    @ResponseBody
    public int getLastInsertedId(@PathVariable("userId") int userId, HttpServletResponse response) {

        try {
            int lastInsertedId = imageService.getLasrInsertId();
            LOGGER.info("Last inserted ID was found");
            return lastInsertedId;
        } catch (DataNotFoundException e) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            LOGGER.error(e.getMessage(), e);
            return 0;
        }

    }

    /**
     * Updates image
     *
     */

    @PreAuthorize("@imageSecurityService.hasPermissionToAccess(#image)")
    @PutMapping(value = "/")
    @ResponseStatus(value = HttpStatus.OK)
    public void update(@RequestBody Image image, HttpServletResponse response) {
        int id = image.getId();
        try {
            imageService.update(image);
            LOGGER.info(String.format("Image with id %d was updated", id));
        } catch (DataNotFoundException e) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            LOGGER.error(String.format("Cannot update Image with id %d", id), e);
        }
    }

}
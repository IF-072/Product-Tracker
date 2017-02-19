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
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
@RequestMapping("/image")
public class  ImageController {

    public static final Logger LOGGER =  LogManager.getLogger(ProductController.class);
    private ImageService imageService;

    @Autowired
    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    @Value("${image.notFound}")
    private String imageNotFound;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Image getImageById(@PathVariable("id") int id, HttpServletResponse response) {

        try {
            Image image = imageService.getById(id);
            LOGGER.info("Image was found");
            return image;
        } catch (DataNotFoundException e) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            LOGGER.error(e.getMessage(), e);
            return null;
        }

    }

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    @ResponseBody
    public void uploadImageObject(@RequestBody Image image) {
        if (image != null) {
            imageService.insert(image);
        }
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public void deleteImage(@PathVariable("id") int id, HttpServletResponse response) {

        try {
            imageService.delete(id);
            LOGGER.info(String.format("Image with id %d was deleted", id));
        } catch (DataNotFoundException e) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            LOGGER.error(String.format(imageNotFound, id), e);
        }

    }

    @RequestMapping(value = "/getLastId", method = RequestMethod.GET)
    @ResponseBody
    public int getLastInsertedId(HttpServletResponse response) {

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

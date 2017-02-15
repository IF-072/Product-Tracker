package com.softserve.if072.restservice.controller;

import com.softserve.if072.common.model.Image;
import com.softserve.if072.restservice.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
@RequestMapping("/image")
public class  ImageController {

    @Autowired
    private ImageService imageService;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public void getImageById(@PathVariable("id") int id, HttpServletResponse response) throws IOException {
        Image image = imageService.getById(id);
        if (image != null) {
            response.setContentType(image.getContentType());
            response.getOutputStream().write(image.getImageData());
            response.getOutputStream().close();
        }
    }

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    @ResponseBody
    public void uploadImageObject(@RequestBody Image image) {
        if (image != null) {
            imageService.insert(image);
        }
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<String> deleteImage(@PathVariable("id") int id) {
        imageService.delete(id);
        return new ResponseEntity<String>(HttpStatus.OK);
    }

    @RequestMapping(value = "/getByFileName", method = RequestMethod.GET)
    @ResponseBody
    public void getByImageFileName(@RequestParam String fileName, HttpServletResponse response) throws IOException {
        Image image = imageService.getByFileName(fileName);
        if (image != null) {
            response.setContentType(image.getContentType());
            response.getOutputStream().write(image.getImageData());
            response.getOutputStream().close();
        }
    }
}

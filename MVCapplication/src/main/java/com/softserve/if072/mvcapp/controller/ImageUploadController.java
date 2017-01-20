package com.softserve.if072.mvcapp.controller;

import com.softserve.if072.common.model.Image;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;

@Controller
@RequestMapping("/image")
public class ImageUploadController {

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public String uploadImage(@ModelAttribute Image image, final RedirectAttributes redirectAttrs) throws IOException {

        MultipartFile multipartFile = image.getMultipartFile();
        image.setContentType(multipartFile.getContentType());
        image.setFileName(multipartFile.getOriginalFilename());
        image.setImageData(multipartFile.getBytes());

        final String uri = "http://localhost:8080/rest/image/upload";
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.postForObject(uri, image, Image.class);

        redirectAttrs.addFlashAttribute("message", "Your image succesfully uploaded");

        return "redirect:/image/upload";
    }

    @RequestMapping(value = "/upload", method = RequestMethod.GET)
    public String getUploadPage(Model model) {
        String message = (String) model.asMap().get("message");
        model.addAttribute("image", new Image());
        model.addAttribute("message", message);
        return "uploadImage";
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ModelAndView getUploadPage(@PathVariable("id") int id) {
        return new ModelAndView("getImage", "imageId", id);
    }
}

package com.softserve.if072.mvcapp.controller;

import com.softserve.if072.common.model.Image;
import com.softserve.if072.common.model.Product;
import com.softserve.if072.common.model.User;
import com.softserve.if072.mvcapp.service.ImageUploadService;
import com.softserve.if072.mvcapp.service.ProductPageService;
import com.softserve.if072.mvcapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * The class contains methods to send data to the REST Service for upload,
 * get, edit and delete image from DataBase
 *
 * @author Vitaliy Malisevych
 */

@Controller
@RequestMapping("/image")
public class ImageUploadController {

    @Value("${application.restImageURL}")
    private String imageUrl;

    @Value("${application.restProductURL}")
    private String productUrl;

    private ProductPageService productPageService;
    private ImageUploadService imageUploadService;
    private RestTemplate restTemplate;
    private UserService userService;

    @Autowired
    public ImageUploadController(RestTemplate restTemplate, UserService userService,
                                 ProductPageService productPageService, ImageUploadService imageUploadService) {
        this.restTemplate = restTemplate;
        this.userService = userService;
        this.productPageService = productPageService;
        this.imageUploadService = imageUploadService;
    }

    /**
     * Method returns page fo uploading the image
     *
     * @param productId product for load the image
     * @param model model with data represented on page
     * @return addImage
     */

    @RequestMapping(value = "/upload", method = RequestMethod.GET)
    public String getUploadPage(@RequestParam int productId, Model model) {

        model.addAttribute("image", new Image());
        model.addAttribute("product", productPageService.getProduct(productId));

        return "addImage";

    }

    /**
     * Method sends data to REST service for uploading image to DataBase
     *
     * @param productId product for load the image
     * @param image image
     * @return redirect to product's page
     */

    @RequestMapping(value =  "/upload", method = RequestMethod.POST)
    public String uploadImage(@ModelAttribute Image image, @RequestParam int productId) throws IOException {

        imageUploadService.uploadImage(image, productId, userService.getCurrentUser().getId());

        return "redirect:/product/";
    }

    /**
     * Method returns image by id
     *
     * @param id of image
     * @param response contains image's data
     */

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public void getImageById(@PathVariable("id") int id, HttpServletResponse response) throws IOException {

        Image image = imageUploadService.getImageById(id);

        if (image != null) {
            response.setContentType(image.getContentType());
            response.getOutputStream().write(image.getImageData());
            response.getOutputStream().close();
        }

    }

    /**
     * Method returns page for updating the image
     *
     * @param productId product for which image will be edited
     * @param model model with data represented on page
     * @return editImage
     */

    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public String getEditPage(@RequestParam int productId, Model model) {

        model.addAttribute("product", productPageService.getProduct(productId));

        model.addAttribute("image", new Image());

        return "editImage";

    }

    /**
     * Method sends data to REST service for updating the image
     *
     * @param productId product for which image will be edited
     * @param image image for editing
     * @return redirect to product's page
     */

    @RequestMapping(value =  "/edit", method = RequestMethod.POST)
    public String editImage(@ModelAttribute Image image, @RequestParam int productId) throws IOException {

        imageUploadService.editImage(image,productId);

        return "redirect:/product/";

    }

    /**
     * Method sends data to REST service for deleting the image
     *
     * @param id image to be deleted
     * @return redirect to product's page
     */

    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public String deleteImage(@RequestParam int id) {

        imageUploadService.deleteImage(id);

        return "redirect:/product/";

    }

}

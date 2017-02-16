package com.softserve.if072.mvcapp.controller;

import com.softserve.if072.common.model.Image;
import com.softserve.if072.common.model.Product;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/image")
public class ImageUploadController {

    @Value("${application.restImageURL}")
    private String imageUrl;

    @Value("${application.restProductURL}")
    private String productUrl;

    private int imageProductId;

    @RequestMapping(value =  "/upload", method = RequestMethod.POST)
    public String uploadImage(@ModelAttribute Image image, final RedirectAttributes redirectAttrs) throws IOException {

        MultipartFile multipartFile = image.getMultipartFile();
        image.setContentType(multipartFile.getContentType());
        image.setFileName(multipartFile.getOriginalFilename());
        image.setImageData(multipartFile.getBytes());

        final String uri = imageUrl + "/upload";
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.postForObject(uri, image, Image.class);

        redirectAttrs.addFlashAttribute("message", "Your image succesfully uploaded");

        final String getLastIdUri = imageUrl + "/getLastId";
        int imageId = restTemplate.getForObject(getLastIdUri, Integer.class);

        final String getProductUri = productUrl + "/{id}";
        Map<String, Integer> paramProduct = new HashMap<>();
        paramProduct.put("id", imageProductId);
        Product product = restTemplate.getForObject(getProductUri, Product.class, paramProduct);

        Image getImage = new Image();
        getImage.setId(imageId);
        product.setImage(getImage);

        final String updateProductUri = productUrl +"/";
        restTemplate.put(updateProductUri, product, Product.class);

        return "redirect:/product/";
    }

    @RequestMapping(value = "/upload", method = RequestMethod.GET)
    public String getUploadPage(@RequestParam int productId, Model model) {

        imageProductId = productId;

        System.out.println(productId);

        String message = (String) model.asMap().get("message");
        model.addAttribute("image", new Image());
        model.addAttribute("message", message);

        return "addImage";

    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public void getImageById(@PathVariable("id") int id, HttpServletResponse response) throws IOException {

        final String uri = imageUrl + "/{id}";

        RestTemplate restTemplate = new RestTemplate();
        Map<String, Integer> param = new HashMap<>();
        param.put("id", id);

        Image image = restTemplate.getForObject(uri,Image.class,param);

        if (image != null) {
            response.setContentType(image.getContentType());
            response.getOutputStream().write(image.getImageData());
            response.getOutputStream().close();
        }
    }

    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public String getEditPage(@RequestParam int productId, Model model) {

        final String uri = productUrl + "/{id}";
        Map<String, Integer> param = new HashMap<>();
        param.put("id", productId);

        imageProductId = productId;

        RestTemplate restTemplate = new RestTemplate();
        Product product = restTemplate.getForObject(uri, Product.class, param);

        model.addAttribute("product", product);

        model.addAttribute("image", new Image());

        return "editImage";

    }

    @RequestMapping(value =  "/edit", method = RequestMethod.POST)
    public String editImage(@ModelAttribute Image image) throws IOException {

        MultipartFile multipartFile = image.getMultipartFile();
        image.setContentType(multipartFile.getContentType());
        image.setFileName(multipartFile.getOriginalFilename());
        image.setImageData(multipartFile.getBytes());

        final String uri = imageUrl + "/";
        final String getProductUri = productUrl + "/{id}";
        RestTemplate restTemplate = new RestTemplate();
        Map<String, Integer> paramProduct = new HashMap<>();
        paramProduct.put("id", imageProductId);
        Product product = restTemplate.getForObject(getProductUri, Product.class, paramProduct);

        image.setId(product.getImage().getId());

        restTemplate.put(uri, image, Image.class);

        return "redirect:/product/";

    }

    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public String deleteImage(@RequestParam int id) {

        final String uri = imageUrl + "/delete/{id}";
        Map<String, Integer> param = new HashMap<>();
        param.put("id", id);

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.delete(uri, param);

        return "redirect:/product/";

    }

}

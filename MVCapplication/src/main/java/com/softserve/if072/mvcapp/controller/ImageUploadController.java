package com.softserve.if072.mvcapp.controller;

import com.softserve.if072.common.model.Image;
import com.softserve.if072.common.model.Product;
import com.softserve.if072.common.model.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.jws.soap.SOAPBinding;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/image")
public class ImageUploadController extends BaseController {

    @Value("${application.restImageURL}")
    private String imageUrl;

    @Value("${application.restProductURL}")
    private String productUrl;

    @RequestMapping(value =  "/upload", method = RequestMethod.POST)
    public String uploadImage(@ModelAttribute Image image, @RequestParam int productId, final RedirectAttributes redirectAttrs) throws IOException {

        User user = getCurrentUser();

        RestTemplate restTemplate = getRestTemplate();
        Map<String, Integer> param = new HashMap<>();

        MultipartFile multipartFile = image.getMultipartFile();
        image.setContentType(multipartFile.getContentType());
        image.setFileName(multipartFile.getOriginalFilename());
        image.setImageData(multipartFile.getBytes());

        final String uri = imageUrl + "/upload/{userId}";
        param.put("userId", user.getId());
        restTemplate.postForObject(uri, image, Image.class, param);

        redirectAttrs.addFlashAttribute("message", "Your image succesfully uploaded");

        final String getLastIdUri = imageUrl + "/getLastId/{userId}";
        int imageId = restTemplate.getForObject(getLastIdUri, Integer.class, param);

        final String getProductUri = productUrl + "/{userId}/{productId}";

        param.clear();
        param.put("productId", productId);
        param.put("userId", user.getId());
        Product product = restTemplate.getForObject(getProductUri, Product.class, param);

        Image getImage = new Image();
        getImage.setId(imageId);
        product.setImage(getImage);

        final String updateProductUri = productUrl +"/image";
        restTemplate.put(updateProductUri, product, Product.class);

        return "redirect:/product/";
    }

    @RequestMapping(value = "/upload", method = RequestMethod.GET)
    public String getUploadPage(@RequestParam int productId, Model model) {

        User user = getCurrentUser();
        RestTemplate restTemplate = getRestTemplate();

        final String uri = productUrl + "/{userId}/{productId}";
        Map<String, Integer> param = new HashMap<>();
        param.put("productId", productId);
        param.put("userId", user.getId());

        Product product = restTemplate.getForObject(uri, Product.class, param);

        String message = (String) model.asMap().get("message");
        model.addAttribute("image", new Image());
        model.addAttribute("message", message);
        model.addAttribute("product", product);

        return "addImage";

    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public void getImageById(@PathVariable("id") int id, HttpServletResponse response) throws IOException {

        final String uri = imageUrl + "/{userId}/{imageId}";

        User user = getCurrentUser();

        RestTemplate restTemplate = getRestTemplate();
        Map<String, Integer> param = new HashMap<>();
        param.put("userId", user.getId());
        param.put("imageId", id);

        Image image = restTemplate.getForObject(uri,Image.class,param);

        if (image != null) {
            response.setContentType(image.getContentType());
            response.getOutputStream().write(image.getImageData());
            response.getOutputStream().close();
        }
    }

    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public String getEditPage(@RequestParam int productId, Model model) {

        final String uri = productUrl + "/{userId}/{productId}";

        User user = getCurrentUser();

        RestTemplate restTemplate = getRestTemplate();

        Map<String, Integer> param = new HashMap<>();
        param.put("productId", productId);
        param.put("userId", user.getId());

        Product product = restTemplate.getForObject(uri, Product.class, param);

        model.addAttribute("product", product);

        model.addAttribute("image", new Image());

        return "editImage";

    }

    @RequestMapping(value =  "/edit", method = RequestMethod.POST)
    public String editImage(@ModelAttribute Image image, @RequestParam int productId) throws IOException {

        User user = getCurrentUser();

        RestTemplate restTemplate = getRestTemplate();

        MultipartFile multipartFile = image.getMultipartFile();
        image.setContentType(multipartFile.getContentType());
        image.setFileName(multipartFile.getOriginalFilename());
        image.setImageData(multipartFile.getBytes());

        final String uri = imageUrl + "/{userId}";
        final String getProductUri = productUrl + "/{userId}/{productId}";

        Map<String, Integer> param = new HashMap<>();
        param.put("productId", productId);
        param.put("userId", user.getId());

        Product product = restTemplate.getForObject(getProductUri, Product.class, param);

        image.setId(product.getImage().getId());

        param.clear();
        param.put("userId", user.getId());
        HttpEntity<Image> requestEntity = new HttpEntity<>(image);
        restTemplate.exchange(uri, HttpMethod.PUT, requestEntity, Image.class, param);

        return "redirect:/product/";

    }

    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public String deleteImage(@RequestParam int id) {

        final String uri = imageUrl + "/delete/{userId}/{imageId}";

        User user = getCurrentUser();

        RestTemplate restTemplate = getRestTemplate();

        Map<String, Integer> param = new HashMap<>();
        param.put("userId", user.getId());
        param.put("imageId", id);

        restTemplate.delete(uri, param);

        return "redirect:/product/";

    }

}

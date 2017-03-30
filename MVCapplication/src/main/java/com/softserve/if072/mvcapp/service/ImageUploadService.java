package com.softserve.if072.mvcapp.service;

import com.softserve.if072.common.model.Image;
import com.softserve.if072.common.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * The class contains methods to send data to the REST Service for upload,
 * get, edit and delete image from DataBase
 *
 * @author Vitaliy Malisevych
 */
@Service
public class ImageUploadService {

    private RestTemplate restTemplate;
    private ProductPageService productPageService;

    @Value("${application.restImageURL}")
    private String imageUrl;

    @Value("${application.restProductURL}")
    private String productUrl;

    @Autowired
    public ImageUploadService(RestTemplate restTemplate,
                              ProductPageService productPageService) {
        this.restTemplate = restTemplate;
        this.productPageService = productPageService;
    }

    /**
     * This method sends data of product's image to the RESTful service for saving it in DataBase.
     *
     * @param userId id of user whose product's image must be saved
     * @param image product's image
     */
    public void uploadImage(Image image, int productId, int userId) throws IOException {

        Map<String, Integer> param = new HashMap<>();

        MultipartFile multipartFile = image.getMultipartFile();
        image.setContentType(multipartFile.getContentType());
        image.setFileName(multipartFile.getOriginalFilename());
        image.setImageData(multipartFile.getBytes());

        final String uri = imageUrl + "/upload/{userId}";
        param.put("userId", userId);
        int imageId = restTemplate.postForObject(uri, image, Integer.class, param);

        Product product = productPageService.getProduct(productId);

        Image getImage = new Image();
        getImage.setId(imageId);
        product.setImage(getImage);

        final String updateProductUri = productUrl +"/image";
        restTemplate.put(updateProductUri, product, Product.class);

    }

    /**
     * This method sends id of product's image to the RESTful service for receiving it from DataBase.
     *
     * @param id id of product's image
     * @return image
     */
    public Image getImageById(int id) {

        final String uri = imageUrl + "/{imageId}";

        Map<String, Integer> param = new HashMap<>();
        param.put("imageId", id);

        return restTemplate.getForObject(uri, Image.class, param);

    }

    /**
     * This method sends data of product's image to the RESTful service for updating it in DataBase.
     *
     * @param productId id of product whose image must be updated
     * @param image product's image
     */
    public void editImage(Image image, int productId) throws IOException {

        MultipartFile multipartFile = image.getMultipartFile();
        image.setContentType(multipartFile.getContentType());
        image.setFileName(multipartFile.getOriginalFilename());
        image.setImageData(multipartFile.getBytes());

        final String uri = imageUrl + "/";

        Product product = productPageService.getProduct(productId);

        image.setId(product.getImage().getId());

        restTemplate.exchange(uri, HttpMethod.PUT, new HttpEntity<>(image), Image.class);

    }

    /**
     * This method sends id of product's image to the RESTful service for deleting it from DataBase.
     *
     * @param id id of product's image that must be deleted
     */
    public void deleteImage(int id) {

        final String uri = imageUrl + "/delete/{imageId}";

        Map<String, Integer> param = new HashMap<>();
        param.put("imageId", id);

        restTemplate.delete(uri, param);

    }
}

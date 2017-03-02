package com.softserve.if072.mvcapp.service;

import com.softserve.if072.common.model.Image;
import com.softserve.if072.common.model.Product;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

    private static final Logger LOGGER = LogManager.getLogger(ImageUploadService.class);

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

    public void uploadImage(Image image, int productId, int userId) throws IOException {

        Map<String, Integer> param = new HashMap<>();

        MultipartFile multipartFile = image.getMultipartFile();
        image.setContentType(multipartFile.getContentType());
        image.setFileName(multipartFile.getOriginalFilename());
        image.setImageData(multipartFile.getBytes());

        final String uri = imageUrl + "/upload/{userId}";
        param.put("userId", userId);
        restTemplate.postForObject(uri, image, Image.class, param);

        final String getLastIdUri = imageUrl + "/getLastId/{userId}";
        int imageId = restTemplate.getForObject(getLastIdUri, Integer.class, param);

        Product product = productPageService.getProduct(productId);

        Image getImage = new Image();
        getImage.setId(imageId);
        product.setImage(getImage);

        final String updateProductUri = productUrl +"/image";
        restTemplate.put(updateProductUri, product, Product.class);

    }
}

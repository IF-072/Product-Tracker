package com.softserve.if072.mvcapp.service;

import com.softserve.if072.common.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class AnalyticsPageService {

    private RestTemplate restTemplate;
    private ProductPageService productPageService;

    @Autowired
    public AnalyticsPageService(RestTemplate restTemplate, ProductPageService productPageService) {
        this.restTemplate = restTemplate;
        this.productPageService = productPageService;
    }

    public List<Product> getAllProducts(int userID) {
        return productPageService.getAllProducts(userID);
    }
}

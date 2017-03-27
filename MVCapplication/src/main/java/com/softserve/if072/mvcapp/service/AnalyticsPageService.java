package com.softserve.if072.mvcapp.service;

import com.softserve.if072.common.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class AnalyticsPageService {

    private RestTemplate restTemplate;

    @Autowired
    public AnalyticsPageService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<Product> getAllProducts(int userID) {
        return null;
    }
}

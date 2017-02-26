package com.softserve.if072.mvcapp.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Nazar Vynnyk
 */

@Service
public class StorePageService {
    public static final Logger LOGGER = LogManager.getLogger(StorePageService.class);

    @Autowired
    private RestTemplate restTemplate;

    @Value("${application.restStoreURL}")
    private String storeUrl;

    @Value("${application.restProductURL}")
    private String productUrl;

    @Value("${service.user.current}")
    private String getCurrentUser;

    /**
     * This method receives from Rest Controller list of user stores
     *
     * @param userId user whose stores will be returned
     * @return list of stores
     */
    public List getAllStoresByUserId(int userId) {
        final String uri = storeUrl + "/user/{userId}";

        Map<String, Integer> param = new HashMap<>();
        param.put("userId", userId);
        List stores = restTemplate.getForObject(uri, List.class, param);
        LOGGER.info(String.format("Stores of user with id %d were found", userId));
        return stores;
    }
}


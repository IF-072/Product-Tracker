package com.softserve.if072.mvcapp.service;

import com.softserve.if072.common.model.User;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * This service class contains methods that handle user registration process
 *
 * @author Igor Parada
 */
@Service
public class RegistrationService {

    @Value("${service.url.register}")
    private String registerUrl;

    @Value("${service.url.register.alreadyExist}")
    private String registerAlreadyExistsUrl;

    @Autowired
    private RestTemplate restTemplate;

    /**
     * Performs user registration on RESTful service.
     *
     * @param user filled in user instance
     * @return true if user has been successfully registered
     */
    public boolean performRegistration(User user) {
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(registerUrl, user, String.class);
        return responseEntity != null && responseEntity.getStatusCode().equals(HttpStatus.OK);
    }

    /**
     * Checks if the given username are already registered in the system
     *
     * @param username form received from model
     * @return true if such user has found in th DB
     */
    public boolean alreadyExist(String username) {
        if (StringUtils.isBlank(username)) {
            return false;
        }
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(registerAlreadyExistsUrl, username, String.class);
        return responseEntity.hasBody() && responseEntity.getBody().equals(username);
    }
}

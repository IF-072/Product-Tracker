package com.softserve.if072.mvcapp.service;

import com.softserve.if072.common.model.Role;
import com.softserve.if072.common.model.User;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.LinkedHashMap;
import java.util.Map;

@Service
public class RegistrationService {

    @Value("${service.url.getRoleById}")
    private String getRoleByIdUrl;

    @Value("${service.url.roles}")
    private String rolesUrl;

    @Value("${service.url.register}")
    private String registerUrl;

    @Value("${service.url.register.alreadyExist}")
    private String registerAlreadyExistsUrl;

    @Autowired
    private RestTemplate restTemplate;

    /**
     * Retrieves all available roles from RESTful service and returns it as a map
     *
     * @return map with all available for registration roles
     */
    public Map<Integer, String> getAvailableRoles() {
        ResponseEntity<Role[]> responseEntity = restTemplate.getForEntity(rolesUrl, Role[].class);
        Map<Integer, String> rolesMap = new LinkedHashMap<>();
        Role[] roles = responseEntity.getBody();
        for (Role role : roles)
            rolesMap.put(role.getId(), role.getAuthority());

        return rolesMap;
    }

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

    /**
     * Retrieves single role from RESTful service by given ID
     *
     * @return retrieved role
     */
    public Role getRoleByID(int roleId) {
        RestTemplate template = new RestTemplate();
        ResponseEntity<Role> responseEntity = template.getForEntity(String.format(getRoleByIdUrl, roleId), Role.class);
        if (responseEntity == null || !responseEntity.hasBody()) {
            return null;
        }
        return responseEntity.getBody();
    }
}

package com.softserve.if072.mvcapp.service;

import com.softserve.if072.common.model.Role;
import com.softserve.if072.common.model.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.LinkedHashMap;
import java.util.Map;

@Service
public class RegistrationService {

    private static final Logger LOGGER = LogManager.getLogger(RegistrationService.class);

    @Value("${service.url.getRoleById}")
    private String getRoleByIdUrl;

    @Value("${service.url.roles}")
    private String rolesUrl;

    @Value("${service.url.register}")
    private String registerUrl;

    @Value("${registration.alreadyExists}")
    private String alreadyExistMessage;

    @Value("${registration.generalError}")
    private String generalErrorMessage;

    @Value("${registration.successful}")
    private String registrationSuccessfulMessage;

    @Autowired
    private RestTemplate restTemplate;

    /**
     * Retrieves all available roles from RESTful service and returns it as a map
     *
     * @return map with all available for registration roles
     */
    public Map<Integer, String> getAvailableRoles(){
        ResponseEntity<Role[]> responseEntity = restTemplate.getForEntity(rolesUrl, Role[].class);
        Map<Integer, String> rolesMap = new LinkedHashMap<>();
        Role[] roles = responseEntity.getBody();
        for (Role role : roles)
            rolesMap.put(role.getId(), role.getDescription());

        return rolesMap;
    }

    /**
     * Performs user registration on RESTful service. If something gets wrong, puts error messages as a redirect attributes.
     *
     * @param user filled in user instance
     * @param redirectAttributes redirect attributes
     * @return registration page view name
     */
    public String performRegistration(User user, RedirectAttributes redirectAttributes){
        try {
            ResponseEntity<String> responseEntity = restTemplate.postForEntity(registerUrl, user, String.class);
            if (responseEntity.getStatusCode().equals(HttpStatus.OK)) {
                redirectAttributes.addFlashAttribute("successMessage", registrationSuccessfulMessage);
                return "redirect:/login";
            }
        } catch (HttpClientErrorException e) {
            if (e.getStatusCode().equals(HttpStatus.UNPROCESSABLE_ENTITY)) {
                LOGGER.warn("User tried to register with already registered username");
                redirectAttributes.addFlashAttribute("errorMessage", alreadyExistMessage);
            } else {
                redirectAttributes.addFlashAttribute("errorMessage", generalErrorMessage);
            }
            return "redirect:/register";
        }

        return "redirect:/register";
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

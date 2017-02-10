package com.softserve.if072.mvcapp.controller;

import com.softserve.if072.common.model.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
 * This class provides access to the user's profile page.
 *
 * @author Oleh Pochernin
 */
@Controller
@PropertySource(value = {"classpath:application.properties"})
public class UserProfileController {
    private static final Logger LOG = LogManager.getLogger(UserProfileController.class);

    @Value("${service.user.current}")
    private String getCurrentUser;

    /**
     * This method extract a user model for profile view.
     *
     * @return profile's view url
     */
    @RequestMapping("/profile")
    public String getUserProfilePage(HttpServletRequest request, ModelMap model) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.add("X-Token", request.getHeader("X-Token"));

        HttpEntity entity = new HttpEntity(headers);
        HttpEntity<User> response = restTemplate.exchange(getCurrentUser, HttpMethod.GET, entity, User.class);
        User user = response.getBody();
        model.addAttribute("user", user);

        return "profile";
    }

    @ExceptionHandler(HttpClientErrorException.class)
    public String handleException() {
        LOG.warn("User has tried to visit secured page without authorization.");

        return "login";
    }
}

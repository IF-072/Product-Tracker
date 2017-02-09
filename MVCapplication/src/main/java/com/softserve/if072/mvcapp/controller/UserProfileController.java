package com.softserve.if072.mvcapp.controller;

import com.softserve.if072.common.model.User;
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
public class UserProfileController {
    private static final String GET_CURRENT_USER = "http://localhost:8080/rest/api/security/user/current";

    @RequestMapping("/profile")
    public String getUserProfilePage(HttpServletRequest request, ModelMap model) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.add("X-Token", request.getHeader("X-Token"));

        HttpEntity entity = new HttpEntity(headers);
        HttpEntity<User> response = restTemplate.exchange(GET_CURRENT_USER, HttpMethod.GET, entity, User.class);
        User user = response.getBody();
        model.addAttribute("user", user);

        return "profile";
    }

    @ExceptionHandler(HttpClientErrorException.class)
    public String handleException() {

        return "login";
    }
}

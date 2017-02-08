package com.softserve.if072.mvcapp.controller;

import com.softserve.if072.common.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

/**
 *  This class provides access to the user's profile page.
 *
 * @author Oleh Pochernin
 */
@Controller
public class UserProfileController {
    private static final String GET_CURRENT_USER = "localhost:8080/rest/api/security/user/current";

    @RequestMapping("profile")
    public String getUserProfilePage() {
        RestTemplate restTemplate = new RestTemplate();
        User user = restTemplate.getForObject(GET_CURRENT_USER, User.class);

        return "profile";
    }
}

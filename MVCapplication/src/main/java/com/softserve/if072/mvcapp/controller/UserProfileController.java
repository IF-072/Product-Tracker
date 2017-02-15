package com.softserve.if072.mvcapp.controller;

import com.softserve.if072.common.model.User;
import com.softserve.if072.mvcapp.controller.core.BaseController;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;

/**
 * This class provides access to the user's profile page.
 *
 * @author Oleh Pochernin
 */
@Controller
@PropertySource(value = {"classpath:application.properties"})
public class UserProfileController extends BaseController {
    private static final Logger LOG = LogManager.getLogger(UserProfileController.class);

    @Value("${service.user.current}")
    private String getCurrentUser;

    /**
     * This method extracts a user model for the profile view.
     *
     * @return profile's view url
     */
    @RequestMapping("/profile")
    public String getUserProfilePage(HttpServletRequest request, Model model) {
        RestTemplate restTemplate = getRestTemplate();
        User user = restTemplate.getForObject(getCurrentUser, User.class);
        model.addAttribute("user", user);

        return "profile";
    }
}
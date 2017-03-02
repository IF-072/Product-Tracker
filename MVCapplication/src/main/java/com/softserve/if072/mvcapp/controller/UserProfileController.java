package com.softserve.if072.mvcapp.controller;

import com.softserve.if072.common.model.User;
import com.softserve.if072.mvcapp.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * This class provides access to the user's profile page.
 *
 * @author Oleh Pochernin
 */
@Controller
public class UserProfileController {
    private static final Logger LOG = LogManager.getLogger(UserProfileController.class);

    private UserService userService;

    /**
     * This method extracts a user model for the profile view.
     *
     * @return profile's view url
     */
    @RequestMapping("/profile")
    public String getUserProfilePage(Model model) {
        User user = userService.getCurrentUser();
        model.addAttribute("user", user);
        LOG.debug(String.format("User with id %d has been put into model", user.getId()));

        return "profile";
    }
}
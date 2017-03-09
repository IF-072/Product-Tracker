package com.softserve.if072.mvcapp.controller;

import com.softserve.if072.common.model.User;
import com.softserve.if072.mvcapp.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * This class provides access to the user's profile page.
 *
 * @author Oleh Pochernin
 */
@Controller
@RequestMapping("/profile")
public class UserProfileController {
    private static final Logger LOG = LogManager.getLogger(UserProfileController.class);

    private UserService userService;

    @Autowired
    public UserProfileController(UserService userService) {
        this.userService = userService;
    }

    /**
     * This method extracts a user model for the profile view.
     *
     * @return profile's view url
     */
    @GetMapping
    public String getUserProfilePage(Model model) {
        User user = userService.getCurrentUser();
        model.addAttribute("user", user);
        LOG.debug(String.format("User with id %d has been put into model", user.getId()));

        return "profile";
    }

    /**
     * Sets the user account type to 'premium' if the account type is 'regular'
     *
     * @return redirection to homepage
     */
    @GetMapping("/getPremium")
    public String getPremiumAccount(){
        User user = userService.getCurrentUser();
        userService.setPremium(user);

        return "redirect:/home";
    }

    /**
     * Prolongs the premium expired time of user with 'premium' account type
     *
     * @return redirection to homepage
     */
    @GetMapping("/prolongPremium")
    public String prolongPremiumAccount(){
        User user = userService.getCurrentUser();
        userService.prolongPremium(user);

        return "redirect:/home";
    }
}
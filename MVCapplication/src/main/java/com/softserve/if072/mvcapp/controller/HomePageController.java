package com.softserve.if072.mvcapp.controller;

import com.softserve.if072.common.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * The controller contains methods that handle home page access
 *
 * @author Igor Parada
 */
@Controller
public class HomePageController extends BaseController {

    /**
     * Returns home page view if user is logged in, otherwise sends redirect to login page
     *
     * @return view name
     */
    @GetMapping({"/", "/home"})
    public String getHomePage() {
        User user = getCurrentUser();
        if (user != null) {
            return "home";
        } else {
            return "redirect:/login";
        }
    }
}

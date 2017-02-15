package com.softserve.if072.mvcapp.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/logout")
@PropertySource(value = {"classpath:application.properties"})
public class LogoutController {

    private static final Logger LOGGER = LogManager.getLogger(LogoutController.class);

    @Value("${application.authenticationCookieName}")
    private String cookieName;

    @GetMapping(value = "")
    public String logoutAndRedirectToLogin(HttpServletResponse response, final RedirectAttributes redirectAttributes) {
        Cookie cookie = new Cookie(cookieName, null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);

        redirectAttributes.addFlashAttribute("successMessage", "You have been successfully logged out");
        return "redirect:login";
    }
}

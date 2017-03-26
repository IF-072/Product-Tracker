package com.softserve.if072.mvcapp.controller;

import com.softserve.if072.mvcapp.service.LoginService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/logout")
public class LogoutController {

    private LoginService loginService;

    public LogoutController(LoginService loginService) {
        this.loginService = loginService;
    }

    /**
     * Destroys the user's session and redirects him to login page
     *
     * @return redirect to the login page
     */
    @GetMapping
    public String logoutAndRedirectToLogin(HttpSession session, final RedirectAttributes redirectAttributes) {
        loginService.logout(session);
        redirectAttributes.addFlashAttribute("successMessage", "logoutSuccessful");
        return "redirect:/login";
    }


}

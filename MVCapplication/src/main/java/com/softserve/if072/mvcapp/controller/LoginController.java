package com.softserve.if072.mvcapp.controller;

import com.softserve.if072.mvcapp.dto.UserLoginForm;
import com.softserve.if072.mvcapp.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


/**
 * The controller contains methods that handle user login process
 *
 * @author Igor Parada
 */
@Controller
@RequestMapping({"/", "/login"})
public class LoginController {

    @Value("login.invalidCredentials")
    private String invalidCredentialsMessage;

    @Value("${application.sessionAuthName}")
    private String sessionAuthName;

    private LoginService loginService;

    @Autowired
    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    /**
     * Fills in the login page model with empty UserLoginForm instance.
     * Destroys user's session if he has already logged in.
     */
    @GetMapping
    public String getLoginPage(Model model, HttpSession session) {
        loginService.logout(session);
        model.addAttribute("loginForm", new UserLoginForm());
        return "login";
    }

    /**
     * Handles login process
     */
    @PostMapping
    public String postLoginPage(@Validated @ModelAttribute("loginForm") UserLoginForm loginForm, BindingResult result,
                                Model model, HttpServletRequest request, HttpSession session) {
        if (result.hasErrors()) {
            model.addAttribute("errorMessages", result.getFieldErrors());
            return "login";
        }

        String token = loginService.performLogin(loginForm);
        if (token != null) {
            session.invalidate();
            HttpSession newSession = request.getSession(); // create new session
            newSession.setAttribute(sessionAuthName, token);
            return "redirect:/home";
        } else {
            model.addAttribute("loginError", invalidCredentialsMessage);
            return "login";
        }
    }
}

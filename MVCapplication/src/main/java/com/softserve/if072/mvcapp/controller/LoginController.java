package com.softserve.if072.mvcapp.controller;

import com.softserve.if072.mvcapp.dto.UserLoginForm;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@Controller
@RequestMapping("/login")
@PropertySource(value = {"classpath:application.properties"})
public class LoginController {

    private static final Logger LOGGER = LogManager.getLogger(LoginController.class);
    private final String REST_SERVICE_URL;
    private final String AUTHENTICATION_COOKIE_NAME;


    private Environment environment;

    @Autowired
    public LoginController(Environment environment) {
        this.environment = environment;
        this.REST_SERVICE_URL = environment.getProperty("application.restServiceURL");
        this.AUTHENTICATION_COOKIE_NAME = environment.getProperty("application.authenticationCookieName");
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String getLoginPage(HttpServletRequest httpServletRequest, Model model) {
        model.addAttribute("loginForm", new UserLoginForm());
        return "login";
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public String postLoginPage(@Valid @ModelAttribute("loginForm") UserLoginForm loginForm, BindingResult result,
                                @RequestParam(value = "remember", required = false) boolean rememberMe,
                                Model model, HttpServletResponse httpServletResponse) {
        if (result.hasErrors()) {
            model.addAttribute("errorMessages", result.getFieldErrors());
            return "login";
        }
        String url = new String(REST_SERVICE_URL + "/login/");
        RestTemplate template = new RestTemplate();
        MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
        params.set("login", loginForm.getEmail());
        params.set("password", loginForm.getPassword());
        try {
            ResponseEntity<String> response = template.postForEntity(url, params, String.class);
            String responseBody = response.getBody();
            HttpStatus statusCode = response.getStatusCode();

            if (statusCode.equals(HttpStatus.OK) && responseBody != null && !responseBody.isEmpty()) {
                Cookie cookie = new Cookie(AUTHENTICATION_COOKIE_NAME, responseBody);
                if (rememberMe) {
                    cookie.setMaxAge(999999);
                }
                httpServletResponse.addCookie(cookie);
                return "redirect:home";
            }
        } catch (HttpClientErrorException e) {
            model.addAttribute("loginError", "Invalid e-mail or password");
            return "login";
        }

        return "redirect:login";
    }
}
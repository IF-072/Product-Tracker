package com.softserve.if072.mvcapp.controller;

import com.softserve.if072.mvcapp.dto.UserLoginForm;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/login")
@PropertySource(value = {"classpath:application.properties"})
public class LoginController {

    private static final Logger LOGGER = LogManager.getLogger(LoginController.class);

    @Value("${service.url.login}")
    private String loginUrl;

    @Value("${application.authenticationCookieName}")
    private String cookieName;

    @Value("${application.authenticationCookieLifetimeInSeconds}")
    private int cookieLifeTime;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String getLoginPage(Model model) {
        model.addAttribute("loginForm", new UserLoginForm());
        return "login";
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public String postLoginPage(@Validated @ModelAttribute("loginForm") UserLoginForm loginForm, BindingResult result,
                                @RequestParam(value = "remember", required = false) boolean rememberMe,
                                Model model, HttpServletResponse httpServletResponse) {
        if (result.hasErrors()) {
            model.addAttribute("errorMessages", result.getFieldErrors());
            return "login";
        }
        RestTemplate template = new RestTemplate();
        MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
        params.set("login", loginForm.getEmail());
        params.set("password", loginForm.getPassword());
        try {
            ResponseEntity<String> response = template.postForEntity(loginUrl, params, String.class);
            String responseBody = response.getBody();
            HttpStatus statusCode = response.getStatusCode();

            if (statusCode.equals(HttpStatus.OK) && responseBody != null && !responseBody.isEmpty()) {
                Cookie cookie = new Cookie(cookieName, responseBody);
                if (rememberMe) {
                    cookie.setMaxAge(cookieLifeTime);
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

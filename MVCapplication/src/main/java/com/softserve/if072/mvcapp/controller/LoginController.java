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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;


/**
 * The controller contains methods that handle user login process
 *
 * @author Igor Parada
 */
@Controller
@RequestMapping("/login")
@PropertySource({"classpath:application.properties", "classpath:message.properties"})
public class LoginController extends BaseController {

    private static final Logger LOGGER = LogManager.getLogger(LoginController.class);

    @Value("${service.url.login}")
    private String loginUrl;

    @Value("${application.authenticationCookieName}")
    private String cookieName;

    @Value("${application.authenticationCookieLifetimeInSeconds}")
    private int cookieLifeTime;

    @Value("${login.invalidCredentials}")
    private String invalidCredentialsMessage;

    @GetMapping
    public String getLoginPage(Model model) {
        model.addAttribute("loginForm", new UserLoginForm());
        return "login";
    }

    /**
     * Handles login process. In case if the login was successful, puts the received token into user cookie.
     * In case of errors displays to user error messages.
     */
    @PostMapping
    public String postLoginPage(@Validated @ModelAttribute("loginForm") UserLoginForm loginForm, BindingResult result,
                                @RequestParam(value = "remember", required = false) boolean rememberMe,
                                Model model, HttpServletResponse httpServletResponse) {
        if (result.hasErrors()) {
            model.addAttribute("errorMessages", result.getFieldErrors());
            return "login";
        }
        RestTemplate template = getRestTemplate();
        MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
        params.set("login", loginForm.getEmail());
        params.set("password", loginForm.getPassword());
        try {
            ResponseEntity<String> response = template.postForEntity(loginUrl, params, String.class);
            if (HttpStatus.OK.equals(response.getStatusCode()) && response.hasBody() && !response.getBody().isEmpty()) {
                Cookie cookie = new Cookie(cookieName, response.getBody());
                if (rememberMe) {
                    cookie.setMaxAge(cookieLifeTime);
                }
                httpServletResponse.addCookie(cookie);
                return "redirect:/home";
            }
        } catch (HttpClientErrorException e) {
            LOGGER.info(String.format("User %s entered invalid credentials", loginForm.getEmail()));
            model.addAttribute("loginError", invalidCredentialsMessage);
            return "login";
        }

        return "redirect:/login";
    }
}

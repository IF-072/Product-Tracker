package com.softserve.if072.mvcapp.controller;

import com.softserve.if072.common.model.Role;
import com.softserve.if072.common.model.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.LinkedHashMap;
import java.util.Map;

@Controller
@RequestMapping("/register")
@PropertySource(value = {"classpath:application.properties"})
public class RegistrationController {

    private static final Logger LOGGER = LogManager.getLogger(RegistrationController.class);
    private String REST_SERVICE_URL;

    private Environment environment;

    @Autowired
    public RegistrationController(Environment environment) {
        this.environment = environment;
        this.REST_SERVICE_URL = environment.getProperty("application.restServiceURL");
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String getRegisterPage(Model model) {

        String url = new String(REST_SERVICE_URL + "/roles");
        RestTemplate template = new RestTemplate();
        Map<Integer, String> rolesMap = new LinkedHashMap<>();
        try {
           ResponseEntity<Role[]> responseEntity = template.getForEntity(url, Role[].class);
           Role[] roles = responseEntity.getBody();
           for(Role role : roles)
               rolesMap.put(role.getId(), role.getAuthority());
        }  catch (HttpClientErrorException e) {
            LOGGER.error("Can't retrieve roles from REST-service ", e);
            rolesMap.put(2, "ROLE_PREMIUM");
        }

        model.addAttribute("roleMap", rolesMap);
        model.addAttribute("registrationForm", new User());

        return "register";
    }

    @RequestMapping(value = "", method = RequestMethod.POST)

    public String postRegisterPage(@Valid @ModelAttribute("registrationForm") User user, BindingResult result,
                                   Model model, HttpServletResponse httpServletResponse) {
        LOGGER.warn(user);

        return "redirect:register";
    }

//    @RequestMapping(value = "", method = RequestMethod.POST)
//    public String postLoginPage(@Valid @ModelAttribute("loginForm") User user, BindingResult result,
//                                @RequestParam(value = "remember", required = false) boolean rememberMe,
//                                Model model, HttpServletResponse httpServletResponse) {
//        if (result.hasErrors()) {
//            model.addAttribute("errorMessages", result.getFieldErrors());
//            return "login";
//        }
//        String url = new String(REST_SERVICE_URL + "/login/");
//        RestTemplate template = new RestTemplate();
//        MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
//        params.set("login", user.getEmail());
//        params.set("password", user.getPassword());
//        try {
//            ResponseEntity<String> response = template.postForEntity(url, params, String.class);
//            String responseBody = response.getBody();
//            HttpStatus statusCode = response.getStatusCode();
//
//            if (statusCode.equals(HttpStatus.OK) && responseBody != null && !responseBody.isEmpty()) {
//                Cookie cookie = new Cookie("X-Token", responseBody);
//                if (rememberMe) {
//                    cookie.setMaxAge(999999);
//                }
//                httpServletResponse.addCookie(cookie);
//                return "redirect:home";
//            }
//        } catch (HttpClientErrorException e) {
//            model.addAttribute("loginError", "Invalid e-mail or password");
//            return "login";
//        }
//
//        return "redirect:login";
//    }
}

package com.softserve.if072.mvcapp.controller;

import com.softserve.if072.common.model.Role;
import com.softserve.if072.common.model.User;
import com.softserve.if072.mvcapp.dto.UserRegistrationForm;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
               rolesMap.put(role.getId(), role.getDescription());
        }  catch (HttpClientErrorException e) {
            LOGGER.error("Can't retrieve roles from REST-service ", e);
        }

        model.addAttribute("roleMap", rolesMap);
        model.addAttribute("registrationForm", new UserRegistrationForm());

        return "register";
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public String postRegisterPage(@Valid @ModelAttribute("registrationForm") UserRegistrationForm registrationForm,
                                   BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("validationErrors", result.getFieldErrors());
            return "redirect:register";
        }

        Role role = getRoleByID(registrationForm.getRoleId());
        if(role == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "Please select correct account type");
            return "redirect:register";
        }

        User user = new User();
        user.setEmail(registrationForm.getEmail());
        user.setName(registrationForm.getName());
        user.setPassword(registrationForm.getPassword());
        user.setRole(role);
        user.setEnabled(true);

        String url = new String(REST_SERVICE_URL + "/register/");
        RestTemplate template = new RestTemplate();
        try {
            ResponseEntity<String> responseEntity = template.postForEntity(url, user, String.class);
            if (responseEntity.getStatusCode().equals(HttpStatus.OK)){
                redirectAttributes.addFlashAttribute("successMessage", "Your account was successfully created");
                return "redirect:login";
            }
        }  catch (HttpClientErrorException e) {
            if(e.getStatusCode().equals(HttpStatus.UNPROCESSABLE_ENTITY)){
                redirectAttributes.addFlashAttribute("errorMessage", "User with such email already exists");
            } else {
                redirectAttributes.addFlashAttribute("errorMessage", "Something went wrong... Please try one more time");
            }
            return "redirect:register";
        }

       return "redirect:register";
    }

    private Role getRoleByID(int roleId) {
        String url = new String(REST_SERVICE_URL + "/roles/"+roleId);
        RestTemplate template = new RestTemplate();
        ResponseEntity<Role> responseEntity = template.getForEntity(url, Role.class);
        return responseEntity.getBody();
    }

}

package com.softserve.if072.mvcapp.controller;

import com.softserve.if072.common.model.Role;
import com.softserve.if072.common.model.User;
import com.softserve.if072.mvcapp.dto.UserRegistrationForm;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.LinkedHashMap;
import java.util.Map;

@Controller
@RequestMapping("/register")
@PropertySource({"classpath:application.properties", "classpath:message.properties"})
public class RegistrationController {

    private static final Logger LOGGER = LogManager.getLogger(RegistrationController.class);

    @Value("${service.url}")
    private String serviceUrl;

    @Value("${registration.errorMessage}")
    private String errorMessage;


    @RequestMapping(value = "", method = RequestMethod.GET)
    public String getRegisterPage(Model model) {

        String url = new String(serviceUrl + "/roles");
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
    public String postRegisterPage(@Validated @ModelAttribute("registrationForm") UserRegistrationForm registrationForm,
                                   BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("validationErrors", result.getFieldErrors());
            return "redirect:/register";
        }

        Role role = getRoleByID(registrationForm.getRoleId());
        if(role == null) {
            redirectAttributes.addFlashAttribute(errorMessage, "Please select correct account type");
            return "redirect:/register";
        }

        User user = new User();
        user.setEmail(registrationForm.getEmail());
        user.setName(registrationForm.getName());
        user.setPassword(registrationForm.getPassword());
        user.setRole(role);
        user.setEnabled(true);

        String url = new String(serviceUrl + "/register/");
        RestTemplate template = new RestTemplate();
        try {
            ResponseEntity<String> responseEntity = template.postForEntity(url, user, String.class);
            if (responseEntity.getStatusCode().equals(HttpStatus.OK)){
                redirectAttributes.addFlashAttribute("successMessage", "Your account was successfully created");
                return "redirect:/login";
            }
        }  catch (HttpClientErrorException e) {
            if(e.getStatusCode().equals(HttpStatus.UNPROCESSABLE_ENTITY)){
                redirectAttributes.addFlashAttribute(errorMessage, "User with such email already exists");
            } else {
                redirectAttributes.addFlashAttribute(errorMessage, "Something went wrong... Please try one more time");
            }
            return "redirect:/register";
        }

       return "redirect:/register";
    }

    private Role getRoleByID(int roleId) {
        String url = new String(serviceUrl + "/roles/"+roleId);
        RestTemplate template = new RestTemplate();
        ResponseEntity<Role> responseEntity = template.getForEntity(url, Role.class);
        return responseEntity.getBody();
    }

}

package com.softserve.if072.mvcapp.controller;

import com.softserve.if072.common.model.Role;
import com.softserve.if072.common.model.User;
import com.softserve.if072.mvcapp.dto.UserRegistrationForm;
import com.softserve.if072.mvcapp.service.RegistrationService;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * The controller contains methods that handle user registration process
 *
 * @author Igor Parada
 */

@Controller
@RequestMapping("/register")
public class RegistrationController extends BaseController {

    @Value("${registration.incorrectAccountType}")
    private String incorrectAccountTypeMessage;

    @Autowired
    private RegistrationService registrationService;

    /**
     * Creates empty {@link UserRegistrationForm} and puts it with into model.
     * Displays user registration page.
     */
    @GetMapping
    public String getRegisterPage(Model model) {
        model.addAttribute("roleMap", registrationService.getAvailableRoles());
        model.addAttribute("registrationForm", new UserRegistrationForm());

        return "register";
    }

    /**
     * Handles user registration process. In case of errors (such as already existed account,
     * incorrect account type etc.) redirects to register page with displaying error message.
     *
     * @param registrationForm an {@link ModelAttribute} filled in with user's data
     * @param result validation result
     * @param redirectAttributes attributes with custom messages to be displayed on the page
     * @return view name
     */
    @PostMapping
    public String postRegisterPage(@Validated @ModelAttribute("registrationForm") UserRegistrationForm registrationForm,
                                   BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("validationErrors", result.getFieldErrors());
            return "redirect:/register";
        }

        Role role = registrationService.getRoleByID(registrationForm.getRoleId());
        if (role == null) {
            redirectAttributes.addFlashAttribute("errorMessage", incorrectAccountTypeMessage);
            return "redirect:/register";
        }

        User user = new User();
        user.setEmail(registrationForm.getEmail());
        user.setName(registrationForm.getName());
        user.setPassword(registrationForm.getPassword());
        user.setRole(role);
        user.setEnabled(true);

        return registrationService.performRegistration(user, redirectAttributes);
    }



}

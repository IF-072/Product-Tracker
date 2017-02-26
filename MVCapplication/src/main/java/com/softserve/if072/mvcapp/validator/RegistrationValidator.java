package com.softserve.if072.mvcapp.validator;

import com.softserve.if072.mvcapp.dto.UserRegistrationForm;
import com.softserve.if072.mvcapp.service.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * Validates the user input from Registration form
 *
 * @author Igor Parada
 */
@Component
public class RegistrationValidator  implements Validator{

    @Value("${registration.alreadyExists}")
    private String alreadyExistMessage;

    private RegistrationService registrationService;

    @Autowired
    public RegistrationValidator(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return UserRegistrationForm.class.equals(aClass);
    }

    @Override
    public void validate(Object target, Errors errors) {
        UserRegistrationForm userForm = (UserRegistrationForm) target;
        if(registrationService.alreadyExist(userForm.getEmail())) {
            errors.reject("email", alreadyExistMessage);
        }
    }
}

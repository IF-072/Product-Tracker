package com.softserve.if072.mvcapp.dto;

import org.hibernate.validator.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class UserLoginForm {

    @NotEmpty(message = "{error.email.notnull}")
    @Size(min = 5, max = 64, message = "{error.email.size}")
    private String email;

    @NotEmpty(message = "{error.password.notnull}")
    @Size(min = 4, max = 64, message = "{error.password.size}")
    private String password;

    public UserLoginForm() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

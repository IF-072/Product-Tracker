package com.softserve.if072.mvcapp.dto;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.web.util.HtmlUtils;

import javax.validation.constraints.Size;

/**
 * A simple POJO that holds fields required for user login
 *
 * @author Igor Parada
 */
public class UserLoginForm {

    @NotBlank(message = "{error.email.notnull}")
    @Size(min = 5, max = 64, message = "{error.email.size}")
    private String email;

    @NotBlank(message = "{error.password.notnull}")
    @Size(min = 4, max = 64, message = "{error.password.size}")
    private String password;

    public UserLoginForm() { }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = HtmlUtils.htmlEscape(email);
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = HtmlUtils.htmlEscape(password);
    }
}

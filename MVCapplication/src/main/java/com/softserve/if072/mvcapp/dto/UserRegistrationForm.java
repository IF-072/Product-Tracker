package com.softserve.if072.mvcapp.dto;

import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class UserRegistrationForm {

    @NotEmpty(message = "{error.email.notnull}")
    @Size(min = 5, max = 64, message = "{error.email.size}")
    private String email;

    @NotEmpty(message = "{error.password.notnull}")
    @Size(min = 4, max = 64, message = "{error.password.size}")
    private String password;

    @NotEmpty(message = "{error.name.notnull}")
    @Size(min = 4, max = 64, message = "{error.name.size}")
    private String name;

    @NotNull(message = "{error.role.notnull}")
    private Integer roleId;

    public UserRegistrationForm() {
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }
}

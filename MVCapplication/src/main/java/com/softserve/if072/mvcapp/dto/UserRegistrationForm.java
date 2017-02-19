package com.softserve.if072.mvcapp.dto;

import org.hibernate.validator.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * A simple POJO that holds fields required for user registration
 *
 * @author Igor Parada
 */
public class UserRegistrationForm extends UserLoginForm {

    @NotEmpty(message = "{error.name.notnull}")
    @Size(min = 4, max = 64, message = "{error.name.size}")
    private String name;

    @NotNull(message = "{error.role.notnull}")
    private Integer roleId;

    public UserRegistrationForm() { }

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

package com.softserve.if072.common.model;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

/**
 * The class contains information about product's category
 *
 * @author Pavlo Bendus
 */

public class Category {

    private int id;

    @NotBlank(message = "{error.categoryName.notBlank}")
    @Length(min = 3, max = 64, message = "{error.categoryName.length}")
    private String name;
    private User user;
    private boolean isEnabled;

    public Category() {}

    public Category(String name, User user, boolean isEnabled) {
        this.name = name;
        this.user = user;
        this.isEnabled = isEnabled;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean isEnabled() {
        return isEnabled;
    }

    public void setEnabled(boolean enabled) {
        isEnabled = enabled;
    }

    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", user=" + user +
                ", isEnabled=" + isEnabled +
                '}';
    }


}

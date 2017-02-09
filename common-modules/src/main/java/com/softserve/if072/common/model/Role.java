package com.softserve.if072.common.model;

import org.springframework.security.core.GrantedAuthority;

public class Role implements GrantedAuthority {

    private int id;
    private String authority;

    public Role() { }

    public Role(String authority) {
        this.authority = authority;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    @Override
    public String getAuthority() {
        return authority;
    }

    @Override
    public String toString() {
        return "Role{" +
                "id=" + id +
                ", authority='" + authority + '\'' +
                '}';
    }
}

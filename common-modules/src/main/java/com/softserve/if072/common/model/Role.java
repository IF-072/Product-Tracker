package com.softserve.if072.common.model;

import org.springframework.security.core.GrantedAuthority;

/**
 * This enum stores information about user's role. Implements Spring Security {@link GrantedAuthority} interface.
 *
 * @author Igor Parada
 */
public enum Role implements GrantedAuthority {

    ROLE_PREMIUM("ROLE_PREMIUM"),
    ROLE_REGULAR("ROLE_REGULAR");

    private String authority;

    Role(String authority) {
        this.authority = authority;
    }

    public boolean isPremium(){
        return this == ROLE_PREMIUM;
    }

    public boolean isRegular(){
        return this == ROLE_REGULAR;
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
                ", authority='" + authority + '\'' +
                '}';
    }
}

package com.softserve.if072.restservice.security.authentication;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class CustomAuthenticationToken implements Authentication {

    private String token;
    private String userName;
    private long expirateinDate;
    private String confirmationKey;
    private boolean isValid;

    public CustomAuthenticationToken(String token) {
        this.token = token;
    }

    /**
     * Getters and Setters
     */

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public long getExpirateinDate() {
        return expirateinDate;
    }

    public void setExpirateinDate(long expirateinDate) {
        this.expirateinDate = expirateinDate;
    }

    public String getConfirmationKey() {
        return confirmationKey;
    }

    public void setConfirmationKey(String confirmationKey) {
        this.confirmationKey = confirmationKey;
    }

    public boolean isValid() {
        return isValid;
    }

    public void setValid(boolean valid) {
        isValid = valid;
    }

    /**
     * Implementation of methods inherited from Authentication interface.
     */

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getDetails() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return null;
    }

    @Override
    public String getName() {
        return null;
    }

    /**
     * Since we shouldn't use this class for full authentication, isAuthenticated() always returns false.
     *
     * @return false
     */
    @Override
    public boolean isAuthenticated() {
        return false;
    }

    @Override
    public void setAuthenticated(boolean b) throws IllegalArgumentException {
    }
}

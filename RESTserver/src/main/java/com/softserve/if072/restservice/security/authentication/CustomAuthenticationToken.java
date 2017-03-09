package com.softserve.if072.restservice.security.authentication;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * The CustomAuthenticationToken class is simple data transfer class which stores token string
 * and decoded token components. Instances may be not used for authentication, so all the implemented
 * methods returns null or false.
 *
 * @author Igor Parada
 */
public class CustomAuthenticationToken implements Authentication {

    private String token;
    private String userName;
    private long expirationDate;
    private String confirmationKey;
    private boolean isValid;

    public CustomAuthenticationToken(String token) {
        this.token = token;
    }

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

    public long getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(long expirateinDate) {
        this.expirationDate = expirateinDate;
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

    /*
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
        throw new IllegalArgumentException("This operation is not supported for this object type");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CustomAuthenticationToken)) return false;

        CustomAuthenticationToken that = (CustomAuthenticationToken) o;

        return token.equals(that.token);
    }

    @Override
    public int hashCode() {
        return token.hashCode();
    }
}

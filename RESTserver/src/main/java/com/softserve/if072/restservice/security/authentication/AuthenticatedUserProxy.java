package com.softserve.if072.restservice.security.authentication;

import com.softserve.if072.common.model.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * Implementation of {@link Authentication} interface. Stores an instance of {@link User} class, which contains
 * information about current logged in user.
 *
 * @author Igor Parada
 */
public class AuthenticatedUserProxy implements Authentication {

    private User user;
    private CustomAuthenticationToken authenticationToken;
    private boolean isAuthenticated;
    private Collection<? extends GrantedAuthority> authorities;

    public AuthenticatedUserProxy(User user, CustomAuthenticationToken token, boolean isAuthenticated, Collection<? extends GrantedAuthority> authorities) {
        this.user = user;
        this.authenticationToken = token;
        this.isAuthenticated = isAuthenticated;
        this.authorities = authorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public Object getCredentials() {
        if (user != null)
            return user.getPassword();
        return null;
    }

    @Override
    public Object getDetails() {
        return user;
    }

    @Override
    public Object getPrincipal() {
        return user;
    }

    @Override
    public boolean isAuthenticated() {
        return isAuthenticated;
    }

    @Override
    public void setAuthenticated(boolean authenticated) throws IllegalArgumentException {
        if(isAuthenticated && authenticated)
            throw new IllegalArgumentException("Reassign isAuthenticated of already authenticated user is not allowed");
        this.isAuthenticated = authenticated;
    }

    @Override
    public String getName() {
        if (user != null)
            return user.getName();
        return null;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public CustomAuthenticationToken getAuthenticationToken() {
        return authenticationToken;
    }

    public void setAuthenticationToken(CustomAuthenticationToken authenticationToken) {
        this.authenticationToken = authenticationToken;
    }
}

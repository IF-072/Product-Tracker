package com.softserve.if072.restservice.service.security;

import com.softserve.if072.common.model.User;
import com.softserve.if072.restservice.security.authentication.AuthenticatedUserProxy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

/**
 * Base class for security services.
 * Provides basic information about logged in user.
 *
 * @author Igor Parada
 */
@Service
public class BaseSecurityService {

    /**
     * Allows to retrieve current logged in user from Spring Security context
     *
     * @return current user entity or null if user isn't logged in
     */
    public User getCurrentUser(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth.isAuthenticated() && auth instanceof AuthenticatedUserProxy) {
            AuthenticatedUserProxy userProxy = (AuthenticatedUserProxy) auth;
            if (userProxy != null)
                return userProxy.getUser();
        }
        return null;
    }
}

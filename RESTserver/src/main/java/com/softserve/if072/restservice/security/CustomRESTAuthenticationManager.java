package com.softserve.if072.restservice.security;


import com.softserve.if072.common.model.User;
import com.softserve.if072.restservice.security.authentication.AuthenticatedUserProxy;
import com.softserve.if072.restservice.security.authentication.CustomAuthenticationToken;
import com.softserve.if072.restservice.service.TokenService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * The CustomRESTAuthenticationManager is a custom implementation of {@link AuthenticationManager} interface.
 * Performs authentication based on input {@link CustomAuthenticationToken}.
 *
 * @author Igor Parada
 */
@Component
public class CustomRESTAuthenticationManager implements AuthenticationManager {

    private final TokenService tokenService;

    @Autowired
    public CustomRESTAuthenticationManager(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    /**
     * Performs custom authentication process based on input CustomAuthenticationToken.
     *
     * @param authentication {@link CustomAuthenticationToken} instance
     * @return an instance of {@link AuthenticatedUserProxy} with user details
     * @throws AuthenticationException if authentication was unsuccessful
     */

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        CustomAuthenticationToken authenticationToken = (CustomAuthenticationToken) authentication;

        if (authenticationToken == null) {
            throw new BadCredentialsException("Token required to perform authentication");
        }

        tokenService.validate(authenticationToken);
        if(!authenticationToken.isValid()){
            throw new BadCredentialsException("Token is not valid");
        }

        User user = tokenService.getUserByToken(authenticationToken);
        if (user == null) {
            throw new UsernameNotFoundException("Username not found");
        }

        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(user.getRole());
        Authentication authenticatedUser = new AuthenticatedUserProxy(user, authenticationToken, true, authorities);

        return authenticatedUser;
    }
}
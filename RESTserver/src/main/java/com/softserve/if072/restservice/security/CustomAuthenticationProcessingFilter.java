package com.softserve.if072.restservice.security;

import com.softserve.if072.restservice.security.authentication.CustomAuthenticationToken;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CustomAuthenticationProcessingFilter extends AbstractAuthenticationProcessingFilter {

    private static final Logger LOGGER = LogManager.getLogger(CustomAuthenticationProcessingFilter.class);

    public CustomAuthenticationProcessingFilter(String processingURL) {
        super(processingURL);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException, IOException, ServletException {
        String token = request.getHeader("X-Token");

        if(token == null){
            throw new BadCredentialsException("Token not found");
        }

        // Create our Authentication based on received token and let Spring know about it
        Authentication auth = new CustomAuthenticationToken(token);
        SecurityContextHolder.getContext().setAuthentication(auth);

        Authentication authentication = getAuthenticationManager().authenticate(auth);
        authentication.setAuthenticated(false);
        return authentication;
    }
}
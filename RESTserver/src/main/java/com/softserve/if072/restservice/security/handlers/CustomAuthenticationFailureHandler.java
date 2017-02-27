package com.softserve.if072.restservice.security.handlers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Handles authentication errors via catching AuthenticationException.
 * Sets response status code to 401 Unauthorized.
 *
 * @author Igor Parada
 */
public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {

    private static final Logger LOGGER = LogManager.getLogger(CustomAuthenticationFailureHandler.class);

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException e) throws IOException, ServletException {
        LOGGER.warn("Authentication failure from " + request.getRemoteAddr() + ":  " + e.getMessage());
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    }
}

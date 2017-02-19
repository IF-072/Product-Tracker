package com.softserve.if072.restservice.security.handlers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Provides an method for AccessDeniedException handling.
 * Sets response status code to 403 Forbidden.
 *
 * @author Igor Parada
 */
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    private static final Logger LOG = LogManager.getLogger(CustomAccessDeniedHandler.class);

    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                       AccessDeniedException e) throws IOException, ServletException {
        LOG.info("Access denied for " + httpServletRequest.getRemoteAddr());
        httpServletResponse.setStatus(HttpServletResponse.SC_FORBIDDEN);
    }
}

package com.softserve.if072.restservice.security;

import com.softserve.if072.restservice.security.authentication.AuthenticatedUserProxy;
import com.softserve.if072.restservice.security.authentication.CustomAuthenticationToken;
import com.softserve.if072.restservice.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Intercepts all requests to secured context and tries to authenticated them using {@link CustomRESTAuthenticationManager}
 * Throws AccessDeniedException if authentication process fails.
 *
 * @author Igor Parada
 */
public class CustomAuthenticationProcessingFilter extends AbstractAuthenticationProcessingFilter {

    private String tokenHeaderName;

    @Autowired
    private TokenService tokenService;

    public CustomAuthenticationProcessingFilter(String processingURL, String tokenHeaderName) {
        super(processingURL);
        this.tokenHeaderName = tokenHeaderName;
    }

    /**
     * Grabs the token string from input request's header, wraps it with {@link CustomAuthenticationToken} object
     * and tries to authenticate the request using {@link CustomRESTAuthenticationManager}
     *
     * @return authenticated user details as Authentication object
     * @throws AuthenticationException if authentication was unsuccessful
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException, IOException, ServletException {
        String token = request.getHeader(tokenHeaderName);

        if (token == null) {
            throw new BadCredentialsException("Token not found");
        }

        // Create our Authentication instance based on received token and register it in SecurityContext
        Authentication auth = new CustomAuthenticationToken(token);

        //Perform a full authentication by our custom authentication manager
        Authentication authentication = getAuthenticationManager().authenticate(auth);

        //attach updated token value as a response header
        renewToken(response, authentication);

        return authentication;
    }

    /**
     * Adds an updated token as a ServletResponse's header.
     *
     * @param response servlet response
     * @param authentication Spring representation of authenticated user
     */
    private void renewToken(HttpServletResponse response, Authentication authentication) {
        if(authentication != null && authentication instanceof AuthenticatedUserProxy && authentication.isAuthenticated()) {
            CustomAuthenticationToken authenticationToken = ((AuthenticatedUserProxy) authentication).getAuthenticationToken();
            if(authenticationToken.isValid()) {
                response.setHeader(tokenHeaderName, tokenService.renewToken(authenticationToken));
            }
        }
    }
}
package com.softserve.if072.restservice.security;

import com.softserve.if072.common.model.User;
import com.softserve.if072.restservice.security.authentication.AuthenticatedUserProxy;
import com.softserve.if072.restservice.security.authentication.CustomAuthenticationToken;
import com.softserve.if072.restservice.service.TokenService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.test.util.ReflectionTestUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Contains methods for testing CustomAuthenticationProcessingFilter class
 *
 * @author Igor Parada
 */
@RunWith(MockitoJUnitRunner.class)
public class CustomAuthenticationProcessingFilterTest {

    private CustomAuthenticationProcessingFilter customAuthenticationProcessingFilter;
    @Mock
    private TokenService tokenService;
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private AuthenticationManager authenticationManager;

    @Before
    public void setup() {
        customAuthenticationProcessingFilter = new CustomAuthenticationProcessingFilter("/**", "X-Token");
        customAuthenticationProcessingFilter.setAuthenticationManager(authenticationManager);
        when(tokenService.renewToken(any())).thenReturn("NEW_TOKEN");
        ReflectionTestUtils.setField(customAuthenticationProcessingFilter, "tokenService", tokenService);
    }

    @Test(expected = BadCredentialsException.class)
    public void attemptAuthentication_ShouldFailWhenTokenIsNull() throws IOException, ServletException {
        when(request.getHeader(anyString())).thenReturn(null);
        customAuthenticationProcessingFilter.attemptAuthentication(request, response);
    }

    @Test
    public void attemptAuthentication_ShouldAuthenticate() throws IOException, ServletException {
        when(request.getHeader(anyString())).thenReturn("TOKEN");
        CustomAuthenticationToken token = new CustomAuthenticationToken("TOKEN");
        token.setValid(true);
        when(authenticationManager.authenticate(any())).thenReturn(new AuthenticatedUserProxy(new User(),
                token, true, null));
        customAuthenticationProcessingFilter.attemptAuthentication(request, response);
        verify(response).setHeader("X-Token", "NEW_TOKEN");
    }
}

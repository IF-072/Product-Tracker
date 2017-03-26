package com.softserve.if072.restservice.security;

import com.softserve.if072.common.model.Role;
import com.softserve.if072.common.model.User;
import com.softserve.if072.restservice.security.authentication.CustomAuthenticationToken;
import com.softserve.if072.restservice.service.TokenService;
import com.softserve.if072.restservice.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

/**
 * Contains methods for testing CustomRESTAuthenticationManager
 *
 * @author Igor Parada
 */
@RunWith(MockitoJUnitRunner.class)
public class CustomRESTAuthenticationManagerTest {

    private CustomRESTAuthenticationManager customRESTAuthenticationManager;
    @Mock
    private TokenService tokenService;
    @Mock
    private UserService userService;

    @Before
    public void setup() {
        customRESTAuthenticationManager = new CustomRESTAuthenticationManager(tokenService, userService);
    }

    @Test(expected = BadCredentialsException.class)
    public void authenticate_ShouldFailWhenTokenNotFound(){
        customRESTAuthenticationManager.authenticate(null);
    }


    @Test(expected = BadCredentialsException.class)
    public void authenticate_ShouldFailWhenTokenIsInvalid(){
        CustomAuthenticationToken token = new CustomAuthenticationToken("INVALID_TOKEN");
        doNothing().when(tokenService).validate(any(CustomAuthenticationToken.class));

        customRESTAuthenticationManager.authenticate(token);
    }

    @Test(expected = UsernameNotFoundException.class)
    public void authenticate_ShouldFailWhenUsernameNotFound(){
        CustomAuthenticationToken token = new CustomAuthenticationToken("INVALID_TOKEN");
        token.setValid(true);
        doNothing().when(tokenService).validate(any(CustomAuthenticationToken.class));
        when(tokenService.getUserByToken(any(CustomAuthenticationToken.class))).thenReturn(null);

        customRESTAuthenticationManager.authenticate(token);
    }

    @Test
    public void authenticate_ShouldAuthenticateUserWhenAllChecksPassed(){
        User user = new User();
        user.setEmail("a@a.com");
        user.setPassword("password");
        user.setRole(Role.ROLE_PREMIUM);
        CustomAuthenticationToken token = new CustomAuthenticationToken("VALID_TOKEN");
        token.setValid(true);
        doNothing().when(tokenService).validate(any(CustomAuthenticationToken.class));
        when(tokenService.getUserByToken(any(CustomAuthenticationToken.class))).thenReturn(user);
        doNothing().when(userService).verifyPremiumAccountValidity(any(User.class));

        assertNotNull(customRESTAuthenticationManager.authenticate(token));
    }
}

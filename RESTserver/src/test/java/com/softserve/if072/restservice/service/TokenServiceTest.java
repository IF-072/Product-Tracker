package com.softserve.if072.restservice.service;

import com.softserve.if072.common.model.User;
import com.softserve.if072.restservice.configuration.SecurityConfig;
import com.softserve.if072.restservice.configuration.WebConfig;
import com.softserve.if072.restservice.dao.mybatisdao.UserDAO;
import com.softserve.if072.restservice.security.authentication.CustomAuthenticationToken;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ContextConfiguration;

import javax.xml.bind.annotation.adapters.HexBinaryAdapter;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;


/**
 * Contains methods for testing TokenService methods
 *
 * @author Igor Parada
 */
@RunWith(MockitoJUnitRunner.class)
@ContextConfiguration(classes = {WebConfig.class, SecurityConfig.class})
public class TokenServiceTest {

    @Mock
    private Environment environment;
    @Mock
    private UserDAO userDAO;
    private HexBinaryAdapter hexBinaryAdapter;

    private TokenService tokenService;
    private User user;

    @Before
    public void setup(){
        user = new User();
        user.setEmail("test@user.com");
        user.setPassword("test_encoded");
        hexBinaryAdapter = new HexBinaryAdapter();

        when(environment.getProperty("security.tokenEncryptionKey")).thenReturn("somekey");
        when(environment.getProperty("security.tokenDelimiter")).thenReturn(":");
        when(environment.getProperty("security.tokenValidityTimeInSeconds")).thenReturn("3600");
        when(environment.getProperty("security.messageDigestAlgorithm")).thenReturn("MD5");
        when(userDAO.getByUsername("test@user.com")).thenReturn(user);

        tokenService = new TokenService(environment, userDAO, hexBinaryAdapter);
    }




    @Test
    public void testGetUserByTokenFailsWhenTokenIsInvalid() {
        String token = "SOME_INVALID_TOKEN_STRING";
        CustomAuthenticationToken authenticationToken = new CustomAuthenticationToken(token);
        tokenService.validate(authenticationToken);
        assertNull(tokenService.getUserByToken(authenticationToken));
    }

    @Test
    public void testGetUserByTokenWithValidTokenReturnsExpectedUser() {
        String token = tokenService.generateTokenFor(user.getEmail());
        CustomAuthenticationToken authenticationToken = new CustomAuthenticationToken(token);
        tokenService.validate(authenticationToken);
        assertEquals(user, tokenService.getUserByToken(authenticationToken));
    }

    @Test
    public void testGeneratedTokenForUsernameIsValid() {
        String token = tokenService.generateTokenFor("someUserName");
        CustomAuthenticationToken authenticationToken = new CustomAuthenticationToken(token);
        tokenService.validate(authenticationToken);
        assertEquals(authenticationToken.getUserName(), "someUserName");
        assertTrue(authenticationToken.isValid());
    }

    @Test
    public void testRenewTokenForValidToken(){
        CustomAuthenticationToken authenticationToken
                = new CustomAuthenticationToken(tokenService.generateTokenFor(user.getEmail()));
        tokenService.validate(authenticationToken);
        assertNotEquals(authenticationToken, tokenService.renewToken(authenticationToken));
    }
}

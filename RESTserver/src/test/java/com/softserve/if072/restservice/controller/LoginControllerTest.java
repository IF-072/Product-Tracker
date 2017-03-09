package com.softserve.if072.restservice.controller;

import com.softserve.if072.common.model.User;
import com.softserve.if072.restservice.service.TokenService;
import com.softserve.if072.restservice.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

/**
 * Contains tests for LoginController methods
 *
 * @author Igor Parada
 */
@RunWith(MockitoJUnitRunner.class)
public class LoginControllerTest {

    @Mock
    private UserService userService;
    @Mock
    private TokenService tokenService;
    private LoginController loginController;
    private MockMvc mockMvc;
    private User user;

    @Before
    public void setup() throws ClassNotFoundException, NoSuchMethodException {
        loginController = new LoginController(tokenService, userService);
        mockMvc = standaloneSetup(loginController).build();
        user = new User();
        user.setEmail("test@user.com");
        user.setPassword("test_encoded");

        when(userService.getByUsername("test@user.com")).thenReturn(user);
        when(userService.encodePassword("test")).thenReturn("test_encoded");
        when(tokenService.generateTokenFor("test@user.com")).thenReturn("SOME_TOKEN");
    }

    @Test
    public void getAuthenticationToken_ShouldReturnTokenWhenCorrectCredentials() throws Exception {
        mockMvc.perform(post("/login/").param("login", "test@user.com").param("password", "test"))
                .andExpect(status().isOk())
                .andExpect(content().string("SOME_TOKEN"));
        verify(tokenService, times(1)).generateTokenFor(anyString());
    }

    @Test
    public void getAuthenticationToken_ShouldFailWhenNoRequestParamethersPresent() throws Exception {
        mockMvc.perform(post("/login/").param("login", "test@user.com"))
                .andExpect(status().is4xxClientError())
                .andExpect(content().string(""));
        verify(tokenService, times(0)).generateTokenFor(anyString());
    }

    @Test
    public void getAuthenticationToken_ShouldFailWhenCredentialsAreInvalid() throws Exception {
        mockMvc.perform(post("/login/").param("login", "test@user.com").param("password", "wrong_password"))
                .andExpect(status().is4xxClientError())
                .andExpect(content().string("Wrong password"));
        verify(userService, times(1)).getByUsername("test@user.com");
        verifyZeroInteractions(tokenService);
    }
}

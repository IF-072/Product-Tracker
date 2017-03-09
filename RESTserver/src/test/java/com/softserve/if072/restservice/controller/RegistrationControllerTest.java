package com.softserve.if072.restservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.softserve.if072.common.model.Role;
import com.softserve.if072.common.model.User;
import com.softserve.if072.restservice.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

/**
 * Contains tests for RegistrationController methods
 *
 * @author Igor Parada
 */
@RunWith(MockitoJUnitRunner.class)
public class RegistrationControllerTest {

    @Mock
    private UserService userService;
    private RegistrationController registrationController;
    private MockMvc mockMvc;
    private User user;
    private ObjectMapper mapper;

    @Before
    public void setup() throws ClassNotFoundException, NoSuchMethodException {
        registrationController = new RegistrationController(userService);
        mockMvc = standaloneSetup(registrationController).build();
        mapper =  new ObjectMapper();


        user = new User();
        user.setEmail("test@user.com");
        user.setPassword("test");
        user.setName("testname");
        user.setRole(Role.ROLE_REGULAR);

        when(userService.getByUsername("test@user.com")).thenReturn(user);
    }

    @Test
    public void testPerformRegistrationFailWhenRequestBodyIsEmpty() throws Exception {
        mockMvc.perform(post("/register/").contentType(
                MediaType.APPLICATION_JSON).content("")).andExpect(
                status().is4xxClientError());
        verifyZeroInteractions(userService);
    }

    @Test
    public void testPerformRegistrationFailsWhenUserAlreadyExist() throws Exception {

        mockMvc.perform(post("/register/").contentType(
                MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(user))).andExpect(
                status().is4xxClientError());
        verify(userService, times(0)).insert(any());
    }

    @Test
    public void testPerformRegistrationSuccessful() throws Exception {
        User newUser = new User();
        newUser.setEmail("new@user.com");
        newUser.setPassword("newpassword");
        newUser.setName("newname");
        newUser.setRole(Role.ROLE_REGULAR);

        mockMvc.perform(post("/register/").contentType(
                MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(newUser))).andExpect(
                status().isOk());
        verify(userService, times(1)).insert(any());
    }

    @Test
    public void testFindUsernameReturnEmptyStringWhenUsernameNotExists() throws Exception {
        mockMvc.perform(post("/register/findUsername").content("another@user.com"))
                .andExpect(status().isOk())
                .andExpect(content().string(""));
        verify(userService, times(1)).getByUsername("another@user.com");
    }

    @Test
    public void testFindUsernameWhenUsernameAlreadyExists() throws Exception {
        mockMvc.perform(post("/register/findUsername").content("test@user.com"))
                .andExpect(status().isOk())
                .andExpect(content().string("test@user.com"));
        verify(userService, times(1)).getByUsername("test@user.com");
    }

}

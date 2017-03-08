package com.softserve.if072.mvcapp.controller;

import com.softserve.if072.common.model.Role;
import com.softserve.if072.common.model.User;
import com.softserve.if072.mvcapp.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

/**
 * The UserProfileControllerTest class is used to test UserProfile controller class.
 *
 * @author Oleh Pochernin
 */
@RunWith(MockitoJUnitRunner.class)
public class UserProfileControllerTest {

    @Mock
    private UserService userServiceMock;

    private UserProfileController userProfileController;

    private MockMvc mockMvc;


    @Before
    public void setup() {
        userProfileController = new UserProfileController(userServiceMock);
        mockMvc = standaloneSetup(userProfileController)
                .setViewResolvers(new InternalResourceViewResolver("/WEB-INF/views/", ".jsp"))
                .build();
    }

    @Test
    public void getUserProfilePage_shouldReturnUserProfileViewName() throws Exception {
        Role role = new Role();
        role.setDescription("Premium account");

        User user = new User();
        user.setId(1);
        user.setEmail("vasya@gmail.com");
        user.setName("Vasya");
        user.setRole(role);

        when(userServiceMock.getCurrentUser()).thenReturn(user);

        mockMvc.perform(get("/profile"))
                .andExpect(status().isOk())
                .andExpect(forwardedUrl("/WEB-INF/views/profile.jsp"))
                .andExpect(model().attributeExists("user"))
                .andExpect(model().attribute("user", allOf(
                        hasProperty("id", is(1)),
                        hasProperty("email", is("vasya@gmail.com")),
                        hasProperty("name", is("Vasya")),
                        hasProperty("role", hasProperty("description", is("Premium account")))
                )));
    }
}

package com.softserve.if072.mvcapp.controller;

import com.softserve.if072.mvcapp.dto.UserLoginForm;
import com.softserve.if072.mvcapp.service.LoginService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

/**
 * Contains test methods for LoginController class
 *
 * @author Igor Parada
 */
@RunWith(MockitoJUnitRunner.class)
public class LoginControllerTest {

    @Mock
    private LoginService loginService;
    private LoginController loginController;
    private MockMvc mockMvc;
    private UserLoginForm userLoginForm;


    @Before
    public void setup() throws ClassNotFoundException, NoSuchMethodException {
        loginController = new LoginController(loginService);
        mockMvc = standaloneSetup(loginController)
                .setViewResolvers(new InternalResourceViewResolver("/WEB-INF/views/", ".jsp")).build();

        userLoginForm = new UserLoginForm();
        userLoginForm.setEmail("test@user.com");
        userLoginForm.setPassword("testPassword");
        ReflectionTestUtils.setField(loginController, "invalidCredentialsMessage", "some error message");
        ReflectionTestUtils.setField(loginController, "sessionAuthName", "X-Token");
    }

    @Test
    public void getLoginPage_ShouldReturnViewName() throws Exception {
        mockMvc.perform(get("/login"))
                .andExpect(status().isOk())
                .andExpect(view().name("login"))
                .andExpect(model().attributeExists("loginForm"));
    }


    @Test
    public void postLoginPage_ShouldRedirectToHomePageWhenParamsAreValid() throws Exception {
        when(loginService.performLogin(any())).thenReturn("TOKEN_VALUE");
        mockMvc.perform(post("/login").param("email", "test@user.com").param("password", "testPassword"))
                .andExpect(redirectedUrl("/home"));
        verify(loginService, times(1)).performLogin(any());
    }

    @Test
    public void postLoginPage_ShouldHaveErrorsWhenParamsAreInvalid() throws Exception {
        when(loginService.performLogin(any())).thenReturn(nullable(String.class));
        mockMvc.perform(post("/login").param("email", "WRONG").param("password", "WRONG"))
                .andExpect(model().attributeExists("loginError"))
                .andExpect(view().name("login"));
        verify(loginService).performLogin(any());
    }

    @Test
    public void postLoginPage_ShouldHaveErrorsWhenThereAreNoParams() throws Exception {
        mockMvc.perform(post("/login"))
                .andExpect(model().attributeExists("errorMessages"))
                .andExpect(view().name("login"));
        verifyZeroInteractions(loginService);
    }
}

package com.softserve.if072.mvcapp.controller;


import com.softserve.if072.mvcapp.service.LoginService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

/**
 * Contains test methods for LogoutController class
 *
 * @author Igor Parada
 */
@RunWith(MockitoJUnitRunner.class)
public class LogoutControllerTest {

    @Mock
    private LoginService loginService;
    private LogoutController logoutController;
    private MockMvc mockMvc;

    @Before
    public void setup() throws ClassNotFoundException, NoSuchMethodException {
        logoutController = new LogoutController(loginService);
        mockMvc = standaloneSetup(logoutController)
                .setViewResolvers(new InternalResourceViewResolver("/WEB-INF/views/", ".jsp")).build();
    }

    @Test
    public void logoutAndRedirectToLogin_ShouldRedirectTpLogin() throws Exception {
        mockMvc.perform(get("/logout"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login"))
                .andExpect(flash().attributeExists("successMessage"));
    }

}

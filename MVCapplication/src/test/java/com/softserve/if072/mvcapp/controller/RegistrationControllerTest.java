package com.softserve.if072.mvcapp.controller;

import com.softserve.if072.mvcapp.dto.UserRegistrationForm;
import com.softserve.if072.mvcapp.service.RegistrationService;
import com.softserve.if072.mvcapp.validator.RegistrationValidator;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

/**
 * Contains test methods for RegistrationController class
 *
 * @author Igor Parada
 */
@RunWith(MockitoJUnitRunner.class)
public class RegistrationControllerTest {

    @Mock
    private RegistrationService registrationService;
    @Mock
    private RegistrationValidator registrationValidator;
    private RegistrationController registrationController;
    private MockMvc mockMvc;
    private UserRegistrationForm userRegistrationForm;


    @Before
    public void setup() throws ClassNotFoundException, NoSuchMethodException {
        registrationController = new RegistrationController(registrationValidator, registrationService);
        mockMvc = standaloneSetup(registrationController)
                .setViewResolvers(new InternalResourceViewResolver("/WEB-INF/views/", ".jsp")).build();

        userRegistrationForm = new UserRegistrationForm();
        userRegistrationForm.setEmail("test@user.com");
        userRegistrationForm.setPassword("testPassword");
        userRegistrationForm.setConfirmPassword("testPassword");
        userRegistrationForm.setName("name");

        ReflectionTestUtils.setField(registrationController, "generalErrorMessage", "successful");
    }

    @Test
    public void getRegistrationPage_ShouldReturnModelWithEmptyRegistrationForm() throws Exception {
        mockMvc.perform(get("/register"))
                .andExpect(status().isOk())
                .andExpect(view().name("register"))
                .andExpect(model().attributeExists("registrationForm"));
    }

    @Test
    public void postRegistrationPage_ShouldRedirectToLoginWhenParamsAreValid() throws Exception {
        when(registrationService.performRegistration(any())).thenReturn(true);
        mockMvc.perform(post("/register")
                .param("email", "test@user.com")
                .param("password", "testPassword")
                .param("name", "Ivan")
                .param("confirmPassword", "testPassword"))
                .andExpect(model().attributeDoesNotExist("errorMessage", "validationErrors"))
                .andExpect(redirectedUrl("/login"));
    }

    @Test
    public void postRegistrationPage_ShouldFailWhenRegistrationServiceCantPerformUserRegistration() throws Exception {
        when(registrationService.performRegistration(any())).thenReturn(false);
        mockMvc.perform(post("/register")
                .param("email", "test@user.com")
                .param("password", "testPassword")
                .param("name", "Ivan")
                .param("confirmPassword", "testPassword"))
                .andExpect(flash().attributeExists("errorMessage"))
                .andExpect(redirectedUrl("/register"));
    }

    @Test
    public void postRegistrationPage_ShouldHaveValidationErrors() throws Exception {
        mockMvc.perform(post("/register"))
                .andExpect(flash().attributeExists("validationErrors"))
                .andExpect(redirectedUrl("/register"));
        verifyZeroInteractions(registrationService);
    }

}

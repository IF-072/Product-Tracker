package com.softserve.if072.mvcapp.service;

import com.softserve.if072.common.model.User;
import com.softserve.if072.mvcapp.dto.UserRegistrationForm;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.nullable;
import static org.mockito.Mockito.when;

/**
 * Contains test methods for RegistrationService class
 *
 * @author Igor Parada
 */
@RunWith(MockitoJUnitRunner.class)
public class RegistrationServiceTest {

    private RegistrationService registrationService;
    @Mock
    private RestTemplate restTemplate;
    @Mock
    private User user;
    private UserRegistrationForm userRegistrationForm;
    @Mock
    private ResponseEntity responseEntity;

    @Before
    public void setup() throws ClassNotFoundException, NoSuchMethodException {
        registrationService = new RegistrationService(restTemplate);

        userRegistrationForm = new UserRegistrationForm();
        userRegistrationForm.setEmail("test@mail.com");
        userRegistrationForm.setName("name");
        userRegistrationForm.setPassword("test_pwd");
        userRegistrationForm.setConfirmPassword("test_pwd");
    }

    @Test
    public void performRegistration_ShouldReturnTrueWhenRegistrationWereSuccessful(){
        when(responseEntity.getStatusCode()).thenReturn(HttpStatus.OK);
        when(restTemplate.postForEntity(nullable(String.class), any(), any())).thenReturn(responseEntity);
        boolean result = registrationService.performRegistration(user);
        assertTrue(result);
    }

    @Test
    public void performRegistration_ShouldReturnFalseWhenResponseIsNullable(){
        when(restTemplate.postForEntity(nullable(String.class), nullable(User.class), any())).thenReturn(null);
        assertFalse(registrationService.performRegistration(user));
    }

    @Test
    public void performRegistration_ShouldReturnFalseWhenStatusCodeAnotherThan200(){
        when(responseEntity.getStatusCode()).thenReturn(HttpStatus.INTERNAL_SERVER_ERROR);
        when(restTemplate.postForEntity(nullable(String.class), nullable(User.class), any())).thenReturn(responseEntity);
        assertFalse(registrationService.performRegistration(user));
    }

    @Test
    public void alreadyExists_ShouldReturnTrueWhenUserIsAlreadyRegistered() throws Exception {
        String username = "username";
        when(responseEntity.hasBody()).thenReturn(true);
        when(responseEntity.getBody()).thenReturn("username");
        when(restTemplate.postForEntity(nullable(String.class), nullable(String.class), any())).thenReturn(responseEntity);
        assertTrue(registrationService.alreadyExist(username));
    }

    @Test
    public void alreadyExists_ShouldReturnFalseWhenUsernameIsBlank() throws Exception {
        assertFalse(registrationService.alreadyExist(""));
        assertFalse(registrationService.alreadyExist(null));
    }

}

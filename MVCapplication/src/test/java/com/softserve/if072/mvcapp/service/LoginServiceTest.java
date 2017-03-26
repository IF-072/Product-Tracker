package com.softserve.if072.mvcapp.service;

import com.softserve.if072.mvcapp.dto.UserLoginForm;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.nullable;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Contains test methods for LoginService class
 *
 * @author Igor Parada
 */
@RunWith(MockitoJUnitRunner.class)
public class LoginServiceTest {

    private LoginService loginService;
    @Mock
    private RestTemplate restTemplate;
    @Mock
    private HttpServletResponse response;
    @Mock
    private HttpSession session;
    private UserLoginForm userLoginForm;
    @Mock
    private ResponseEntity responseEntity;

    @Before
    public void setup() throws ClassNotFoundException, NoSuchMethodException {
                loginService = new LoginService(restTemplate);
        userLoginForm = new UserLoginForm();
        userLoginForm.setEmail("test@mail.com");
        userLoginForm.setPassword("test_pwd");

        ReflectionTestUtils.setField(loginService, "cookieName", "X-Token");
        ReflectionTestUtils.setField(loginService, "cookieLifeTime", 7200);
    }

    @Test
    public void performLogin_ShouldReturnValidCookie(){
        when(responseEntity.getStatusCode()).thenReturn(HttpStatus.OK);
        when(responseEntity.hasBody()).thenReturn(true);
        when(responseEntity.getBody()).thenReturn("response_body");
        when(restTemplate.postForEntity(nullable(String.class), any(), any())).thenReturn(responseEntity);

        Cookie cookie = loginService.performLogin(userLoginForm);
        assertNotNull(cookie);
    }

    @Test
    public void performLogin_ShouldReturnNullWhenCredentialsAreInvalid(){
        when(restTemplate.postForEntity(nullable(String.class), any(), any())).thenThrow(HttpClientErrorException.class);

        Cookie cookie = loginService.performLogin(userLoginForm);
        assertNull(cookie);
    }

    @Test
    public void performLogout_ShouldResetCookieValueAndCloseSession() {
        loginService.performLogout(response, session);
        verify(session).invalidate();
        verify(response).addCookie(any());
    }
}

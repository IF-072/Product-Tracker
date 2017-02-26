package com.softserve.if072.mvcapp.service;

import com.softserve.if072.mvcapp.dto.UserLoginForm;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.Cookie;

/**
 * Contains methods that manage user login process
 *
 * @author Igor Parada
 */
@Service
public class LoginService {

    private static final Logger LOGGER = LogManager.getLogger(LoginService.class);

    @Value("${service.url.login}")
    private String loginUrl;

    @Value("${application.authenticationCookieName}")
    private String cookieName;

    @Value("${application.authenticationCookieLifetimeInSeconds}")
    private int cookieLifeTime;

    @Autowired
    private RestTemplate restTemplate;

    /**
     * Retrieves authentication token from RESTful service. If authentication was successful, returns cookie with token string.
     *
     * @param loginForm represents user's credentials
     * @return cookie with token string if authentication successful, null if authentication fails
     */
    public Cookie performLogin(UserLoginForm loginForm){
        MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
        params.set("login", loginForm.getEmail());
        params.set("password", loginForm.getPassword());
        try {
            ResponseEntity<String> response = restTemplate.postForEntity(loginUrl, params, String.class);
            if (HttpStatus.OK.equals(response.getStatusCode()) && response.hasBody() && !response.getBody().isEmpty()) {
                Cookie cookie = new Cookie(cookieName, response.getBody());
                return cookie;
            }
        } catch (HttpClientErrorException e) {
            LOGGER.info("User {} entered invalid credentials", loginForm.getEmail());
        }

        return null;
    }
}

package com.softserve.if072.restservice.controller;

import com.softserve.if072.common.model.User;
import com.softserve.if072.restservice.service.TokenService;
import com.softserve.if072.restservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class LoginController {

    private TokenService tokenService;
    private UserService userService;

    @Autowired
    public LoginController(TokenService tokenService, UserService userService) {
        this.tokenService = tokenService;
        this.userService = userService;
    }

    /**
     * Performs server-side authentication process. If user credentials are correct, generates token string and returns
     * it as a response body
     *
     * @param login user login
     * @param password user password
     * @return token string
     */
    @RequestMapping(value = "/", method = RequestMethod.POST)
    @ResponseBody
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<?> getAuthenticationToken(@RequestParam String login, @RequestParam String password) {
        try {
            User user = userService.getByUsername(login);
            if (user == null) {
                throw new BadCredentialsException("Invalid login");
            }
            if (!user.getPassword().equals(userService.encodePassword(password))) {
                throw new BadCredentialsException("Wrong password");
            }
            return new ResponseEntity<>(tokenService.generateTokenFor(login), HttpStatus.OK);
        } catch (BadCredentialsException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.UNAUTHORIZED);
        }
    }
}

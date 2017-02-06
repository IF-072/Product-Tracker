package com.softserve.if072.restservice.controller;

import com.softserve.if072.common.model.User;
import com.softserve.if072.restservice.dao.mybatisdao.UserDAO;
import com.softserve.if072.restservice.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UserDAO userDAO;

    @RequestMapping(value = "/", method = RequestMethod.POST)
    @ResponseBody
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<?> getAuthenticationToken(@RequestParam String login, @RequestParam String password, HttpServletResponse response) {
        try {
            User user = userDAO.getByUsername(login);
            if (user == null) {
                throw new BadCredentialsException("Invalid login");
            }
            if (user.getPassword().equals(password) == false) {
                throw new BadCredentialsException("Wrong password");
            }
            return new ResponseEntity<>(tokenService.generateTokenFor(login), HttpStatus.OK);
        } catch (BadCredentialsException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.UNAUTHORIZED);
        }
    }
}

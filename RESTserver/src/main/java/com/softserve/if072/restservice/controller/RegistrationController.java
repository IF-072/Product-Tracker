package com.softserve.if072.restservice.controller;

import com.softserve.if072.common.model.User;
import com.softserve.if072.restservice.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/register")
public class RegistrationController {

    private static final Logger LOGGER = LogManager.getLogger(RegistrationController.class);

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public ResponseEntity<String> performRegistration(@RequestBody User user) {
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        User alreadyRegisteredUser = userService.getByUsername(user.getEmail());
        if (alreadyRegisteredUser != null) {
            return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
        }

        userService.insert(user);
        LOGGER.info("User successfully registered: " + user);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}

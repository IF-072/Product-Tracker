package com.softserve.if072.restservice.controller;

import com.softserve.if072.common.model.User;
import com.softserve.if072.restservice.exception.DataNotFoundException;
import com.softserve.if072.restservice.security.authentication.AuthenticatedUserProxy;
import com.softserve.if072.restservice.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * This class allows to obtain information about User using REST Service.
 *
 * @author Oleh Pochernin
 */
@RestController
@RequestMapping("/api")
public class UserController {
    private static final Logger LOG = LogManager.getLogger(UserController.class);

    @Autowired
    UserService userService;

    /**
     * Allows to obtain all users.
     */
    @RequestMapping(value = "/user/", method = RequestMethod.GET)
    public ResponseEntity<List<User>> getAllUsers() throws DataNotFoundException {
        LOG.debug("Fetching list of all users...");
        List<User> users = userService.getAll();

        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    /**
     * Allows to obtain user by id.
     *
     * @param id user's id
     */
    @PreAuthorize("#id == authentication.user.id")
    @RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
    public ResponseEntity<User> getUserById(@PathVariable("id") int id) throws DataNotFoundException {
        LOG.debug(String.format("Fetching User with %d...", id));
        User user = userService.getById(id);

        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    /**
     * Allows to update user details
     *
     * @param userId user's id
     * @param user new user details
     */
    @PreAuthorize("#userId == authentication.user.id && #user != null && #user.id == #userId")
    @RequestMapping(value = "/user/{userId}/update", method = RequestMethod.POST)
    public ResponseEntity<?> updateUser(@PathVariable("userId") int userId, @RequestBody User user) throws DataNotFoundException {
        LOG.debug("Updating user with id = {}", userId);
        userService.update(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    /**
     * Retrieves current principal from Spring's SecurityHolder and returns it as a response
     *
     * @return current logged in user entity
     */
    @RequestMapping(value = "/security/user/current")
    @ResponseBody
    public User testController() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth.isAuthenticated()) {
            AuthenticatedUserProxy userProxy = (AuthenticatedUserProxy) auth;
            if (userProxy != null)
                return userProxy.getUser();
        }
        return null;
    }
}

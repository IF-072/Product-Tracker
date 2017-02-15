package com.softserve.if072.restservice.controller;

import com.softserve.if072.common.model.User;
import com.softserve.if072.restservice.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAll();

        if(users.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    /**
     * Allows to obtain user by id.
     *
     * @param id user's id
     */
    @PreAuthorize("#id == authentication.user.id")
    @RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
    public ResponseEntity<User> getUserById(@PathVariable("id") int id) {
        LOG.debug("Fetching User with id " + id);
        User user = userService.getById(id);

        if (user == null) {
            LOG.warn(String.format("User with id %d not found"), id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<String> sendAccessDeniedStatus(){
        return new ResponseEntity(HttpStatus.FORBIDDEN);
    }
}

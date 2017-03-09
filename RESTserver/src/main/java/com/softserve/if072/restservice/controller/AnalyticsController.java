package com.softserve.if072.restservice.controller;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * The AnalyticsController class handles requests for analytics resources
 *
 */
@RestController
@RequestMapping("api/users/{userId}/analytics")
public class AnalyticsController {

    /**
     * Handles requests for retrieving all analytics records for current user
     *
     * @param userId - current user unique identifier
     */
    @PreAuthorize("hasRole('ROLE_PREMIUM') && #userId == authentication.user.id")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public String getByUserId(@PathVariable int userId) {
        return null;
    }
}

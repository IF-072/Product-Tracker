package com.softserve.if072.restservice.controller;

import com.softserve.if072.common.model.ProductStatistics;
import com.softserve.if072.common.model.dto.AnalyticsProductDTO;
import com.softserve.if072.restservice.exception.NotEnoughDataException;
import com.softserve.if072.restservice.service.AnalyticsService;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * The AnalyticsController class handles requests for analytics resources
 *
 * @author Igor Kryviuk
 */
@RestController
@RequestMapping("api/users/{userId}/analytics")
public class AnalyticsController {
    private final AnalyticsService analyticsService;

    public AnalyticsController(AnalyticsService analyticsService) {
        this.analyticsService = analyticsService;
    }

    /**
     * Handles requests for getting list of all user's products
     *
     * @param userId - current user unique identifier
     * @return - list of products or empty list
     */
    @PreAuthorize("hasRole('ROLE_PREMIUM') && #userId == authentication.user.id")
    @GetMapping("/products")
    @ResponseStatus(HttpStatus.OK)
    public List<AnalyticsProductDTO> getByUserId(@PathVariable int userId) {

        return analyticsService.getProducts(userId);
    }

    /**
     * Handles requests for retrieving analytics about concrete product
     *
     * @param productId - product unique identifier
     * @return - ProductStatistics object
     */
    @PreAuthorize("hasRole('ROLE_PREMIUM') && #userId == authentication.user.id")
    @GetMapping("/{productId}")
    @ResponseStatus(HttpStatus.OK)
    public ProductStatistics getByUserId(@PathVariable int userId, @PathVariable int productId) throws NotEnoughDataException {

        return analyticsService.getProductStatistics(productId);
    }
}

package com.softserve.if072.mvcapp.service;

import com.softserve.if072.common.model.ProductStatistics;
import com.softserve.if072.common.model.dto.AnalyticsProductDTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * The AnalyticsService class is used to hold business logic and to retrieve appropriate resources from a
 * REST server
 *
 * @author Igor Kryviuk
 */
@Service
public class AnalyticsService {
    private static final Logger LOGGER = LogManager.getLogger();
    private final RestTemplate restTemplate;
    private final UserService userService;
    @Value("${application.restAnalyticsURL}")
    private String restAnalyticsURL;
    @Value("${application.restAnalyticsGetProductsURL}")
    private String restAnalyticsGetProductsURL;
    @Value("${analytics.requestReceive}")
    private String analyticsRequestReceive;
    @Value("${analytics.successfullyOperation}")
    private String analyticsSuccessfullyOperation;

    public AnalyticsService(RestTemplate restTemplate, UserService userService) {
        this.restTemplate = restTemplate;
        this.userService = userService;
    }

    /**
     * Make request to a REST server for retrieving analytics about concrete product for current user
     *
     * @param productId - product unique identifier
     * @return ProductStatistics object
     */
    public ProductStatistics getProductStatistics(int productId) {
        int userId = userService.getCurrentUser().getId();
        LOGGER.info(analyticsRequestReceive, "analytics for the product with id", productId, userId);
        ProductStatistics productStatistics = restTemplate.getForObject(restAnalyticsURL, ProductStatistics.class,
                userId, productId);
        LOGGER.info(analyticsSuccessfullyOperation, "analytics for the product with id", productId, userId);

        return productStatistics;
    }

    /**
     * Make request to a REST server for retrieving products for current user
     *
     * @return list of products or empty list
     */
    public List<AnalyticsProductDTO> getProducts() {
        int userId = userService.getCurrentUser().getId();
        LOGGER.info(analyticsRequestReceive, "all", "products", userId);
        List<AnalyticsProductDTO> analyticsProductDTOs = restTemplate.getForObject(restAnalyticsGetProductsURL, List.class, userId);
        LOGGER.info(analyticsSuccessfullyOperation, "all", "products", userId);

        return analyticsProductDTOs;
    }

    /**
     * Clean ProductStatistics session object
     */
    public void cleanProductStatisticsSessionObject( ){
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpSession session = servletRequestAttributes.getRequest().getSession();
        session.setAttribute("productStatistics", null);
    }
}

package com.softserve.if072.mvcapp.service;

import com.softserve.if072.common.model.Cart;
import com.softserve.if072.common.model.dto.CartDTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * The CartService class is used to hold business logic and to retrieve appropriate resources from a
 * REST server
 *
 * @author Igor Kryviuk
 */
@Service
public class CartService {
    private static final Logger LOGGER = LogManager.getLogger();
    private final RestTemplate restTemplate;
    private final UserService userService;
    private final AnalyticsService analyticsService;
    @Value("${application.restCartURL}")
    private String restCartURL;
    @Value("${application.restCartPurchaseURL}")
    private String restCartPurchaseURL;
    @Value("${application.restCartDeleteURL}")
    private String restCartDeleteURL;
    @Value("${cart.requestReceive}")
    private String cartRequestReceive;
    @Value("${cart.successfullyOperation}")
    private String cartSuccessfullyOperation;

    public CartService(RestTemplate restTemplate, UserService userService, AnalyticsService analyticsService) {
        this.restTemplate = restTemplate;
        this.userService = userService;
        this.analyticsService = analyticsService;
    }

    /**
     * Make request to a REST server for retrieving all cart records for current user
     *
     * @return list of cart records or empty list
     */
    public List<Cart> getByUserId() {
        int userId = userService.getCurrentUser().getId();

        LOGGER.info(cartRequestReceive, "retrieving", "all cart records", userId);
        @SuppressWarnings("unchecked")
        List<Cart> carts = restTemplate.getForObject(restCartURL, List.class, userId);
        LOGGER.info(cartSuccessfullyOperation, "retrieving", "all cart records", userId);
        return carts;
    }

    /**
     * Make request to a REST server for implementing behaviour of product purchase
     *
     * @param cartDTO - an object with required information for the product purchase
     */
    public void productPurchase(CartDTO cartDTO) {
        int productId = cartDTO.getProductId();
        int userId = cartDTO.getUserId();

        LOGGER.info(cartRequestReceive, "purchasing the product  with id", productId, userId);
        restTemplate.put(restCartPurchaseURL, cartDTO, userId, productId);
        analyticsService.cleanProductStatisticsSessionObject();
        LOGGER.info(cartSuccessfullyOperation, "purchasing the product  with id", productId, userId);
    }

    /**
     * С
     * Make request to a REST server for deleting a product from the cart of current user
     *
     * @param cartDTO - an object with required information for the product delete
     */
    public void deleteProductFromCart(CartDTO cartDTO) {
        int productId = cartDTO.getProductId();
        int userId = cartDTO.getUserId();

        LOGGER.info(cartRequestReceive, "deleting the product with id", productId, userId);
        restTemplate.delete(restCartDeleteURL, userId, productId);
        LOGGER.info(cartSuccessfullyOperation, "deleting the product with id", productId, userId);
    }

    /**
     * Make request to a REST server for deleting all products from the cart of current user
     */
    public void deleteAllProductsFromCart() {
        int userId = userService.getCurrentUser().getId();

        LOGGER.info(cartRequestReceive, "deleting ", "all products", userId);
        restTemplate.delete(restCartURL, userId);
        LOGGER.info(cartSuccessfullyOperation, "deleting ", "all products", userId);
    }
}

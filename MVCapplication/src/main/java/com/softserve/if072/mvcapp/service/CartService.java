package com.softserve.if072.mvcapp.service;

import com.softserve.if072.common.model.Cart;
import com.softserve.if072.common.model.dto.CartDTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
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
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private UserService userService;
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

    /**
     * Make request to a REST server for retrieving all cart records for current user
     *
     * @return list of cart records or empty list
     */
    public List<Cart> getByUserId() {
        int userId = userService.getCurrentUser().getId();
        LOGGER.info(cartRequestReceive, "retrieving", "all cart records", userId);
        List<Cart> carts = restTemplate.getForObject(String.format(restCartURL, userId), List.class);
        LOGGER.info(cartSuccessfullyOperation, "retrieving", "all cart records", userId);
        return carts;
    }

    /**
     * Make request to a REST server for implementing behaviour of product purchase
     *
     * @param cartDTO - an object with required information for the product purchase
     */
    public void productPurchase(CartDTO cartDTO) {
        LOGGER.info(cartRequestReceive, "purchasing the product  with id", cartDTO.getProductId(), cartDTO.getUserId());
        restTemplate.put(String.format(restCartPurchaseURL, cartDTO.getUserId()), cartDTO);
        LOGGER.info(cartSuccessfullyOperation, "purchasing the product  with id", cartDTO.getProductId(), cartDTO.getUserId());
    }

    /**
     * Make request to a REST server for deleting a product from the cart of current user
     *
     * @param cartDTO - an object with required information for the product delete
     */
    @GetMapping("/delete")
    public void deleteProductFromCart(CartDTO cartDTO) {
        LOGGER.info(cartRequestReceive, "deleting the product with id", cartDTO.getProductId(), cartDTO.getUserId());
        restTemplate.delete(String.format(restCartDeleteURL, cartDTO.getUserId(), cartDTO.getProductId()));
        LOGGER.info(cartSuccessfullyOperation, "deleting the product with id", cartDTO.getProductId(), cartDTO.getUserId());
    }
}

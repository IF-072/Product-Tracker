package com.softserve.if072.restservice.service;

import com.softserve.if072.common.model.Cart;
import com.softserve.if072.restservice.dao.mybatisdao.CartDAO;
import com.softserve.if072.restservice.exception.DataNotFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * The CartService class is used to hold business logic for working with the cart DAO
 *
 * @author Igor Kryviuk
 */
@Service
@PropertySource("classpath:message.properties")
public class CartService {
    private static final Logger LOGGER = LogManager.getLogger(CartService.class);
    @Autowired
    private CartDAO cartDAO;
    @Value("${cart.notFound}")
    private String cartNotFound;
    @Value("${cart.found}")
    private String cartFound;
    @Value("${cart.SuccessfullyOperation}")
    private String successfullyOperation;
    @Value("%{cart.foundProductId}")
    private String cartFoundProductId;

    public List<Cart> getByUserId(int userId) {
        List<Cart> carts = cartDAO.getByUserId(userId);
        LOGGER.info(String.format(cartFound, userId, carts.size()));
        return carts;
    }

    public Cart getByProductId(int productId) {
        Cart cart=cartDAO.getByProductId(productId);
        LOGGER.info(String.format(cartFoundProductId, productId));
        return cart;
    }

    public void insert(Cart cart) {
        cartDAO.insert(cart);
        LOGGER.info(String.format(successfullyOperation, cart.getProduct().getName(), "inserted into"));
    }

    public void update(Cart cart) throws DataNotFoundException {
        if (cartDAO.update(cart) == 0) {
            throw new DataNotFoundException(String.format(cartNotFound, "invalid UPDATE operation", cart.getProduct().getName()));
        }
        LOGGER.info(String.format(successfullyOperation, cart.getProduct().getName(), "updated in"));
    }

    public void delete(Cart cart) throws DataNotFoundException {
        if (cartDAO.delete(cart) == 0) {
            throw new DataNotFoundException(String.format(cartNotFound, "invalid DELETE operation", cart.getProduct().getName()));
        }
        LOGGER.info(String.format(successfullyOperation, cart.getProduct().getName(), "deleted from"));
    }

    public void delete(int productId) throws DataNotFoundException {
        if (cartDAO.deleteByProductId(productId) == 0) {
            throw new DataNotFoundException(String.format(cartNotFound, "invalid DELETE operation", productId));
        }
        LOGGER.info(String.format(successfullyOperation, productId, "deleted from"));
    }
}



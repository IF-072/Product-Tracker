package com.softserve.if072.restservice.service;

import com.softserve.if072.common.model.Cart;
import com.softserve.if072.restservice.dao.mybatisdao.CartDAO;
import com.softserve.if072.restservice.exception.DataNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * The CartService class is used to hold business logic for working with the cart DAO
 *
 * @author Igor Kryviuk
 */
@Service
public class CartService {
    @Autowired
    private CartDAO cartDAO;

    public List<Cart> getAll() {
        return cartDAO.getAll();
    }

    public List<Cart> getAllByUserId(int userID) throws  DataNotFoundException{
        List<Cart> carts = cartDAO.getAllByUserId(userID);
        if (!CollectionUtils.isEmpty(carts)) {
            return carts;
        } else {
            throw new DataNotFoundException("carts not found");
        }
    }

    public void insert(Cart cart) {
        cartDAO.insert(cart);
    }

    public void update(Cart cart) {
        cartDAO.update(cart);
    }

    public void delete(Cart cart) {
        cartDAO.delete(cart);
    }
}



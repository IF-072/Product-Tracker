package com.softserve.if072.restservice.service.security;

import com.softserve.if072.common.model.Cart;
import com.softserve.if072.restservice.dao.mybatisdao.CartDAO;
import org.springframework.stereotype.Service;

/**
 * Provides security methods for CartControlLer
 *
 * @author Igor Parada
 */
@Service
public class CartSecurityService extends BaseSecurityService {
    private CartDAO cartDAO;

    public CartSecurityService(CartDAO cartDAO) {
        this.cartDAO = cartDAO;
    }

    public boolean hasPermissionToAccess(int productId) {
        Cart cart = cartDAO.getByProductId(productId);
        return cart != null && cart.getUser() != null && cart.getUser().getId() == getCurrentUser().getId();
    }
}

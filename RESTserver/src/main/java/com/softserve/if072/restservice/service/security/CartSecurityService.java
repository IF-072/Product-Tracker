package com.softserve.if072.restservice.service.security;

import com.softserve.if072.common.model.Cart;
import com.softserve.if072.restservice.dao.mybatisdao.CartDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Provides security methods for CartControlLer
 *
 * @author Igor Parada
 */
@Service
public class CartSecurityService extends BaseSecurityService {
    @Autowired
    private CartDAO cartDAO;

    public boolean hasPermissionToAccess(int productId) {
        Cart cart = cartDAO.getByProductId(productId);
        if (cart != null && cart.getUser() != null && cart.getUser().getId() == getCurrentUser().getId())
            return true;
        else
            return false;
    }
}

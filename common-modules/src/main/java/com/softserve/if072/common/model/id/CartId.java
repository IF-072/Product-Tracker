package com.softserve.if072.common.model.id;

import com.softserve.if072.common.model.Product;
import com.softserve.if072.common.model.Store;
import com.softserve.if072.common.model.User;

import java.io.Serializable;

/**
 * Created by Nazar Vynnyk
 */
public class CartId implements Serializable {

    private User user;
    private Store store;
    private Product product;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CartId cartId = (CartId) o;

        return user.equals(cartId.user) && store.equals(cartId.store) && product.equals(cartId.product);
    }

    @Override
    public int hashCode() {
        int result = user.hashCode();
        result = 31 * result + store.hashCode();
        result = 31 * result + product.hashCode();
        return result;
    }
}

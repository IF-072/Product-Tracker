package com.softserve.if072.common.model.id;

import com.softserve.if072.common.model.Product;
import com.softserve.if072.common.model.User;

import java.io.Serializable;

/**
 * Created by Nazar Vynnyk
 */
public class ShoppingListId implements Serializable {

    private User user;
    private Product product;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ShoppingListId that = (ShoppingListId) o;

        if (!user.equals(that.user)) return false;
        return product.equals(that.product);
    }

    @Override
    public int hashCode() {
        int result = user.hashCode();
        result = 31 * result + product.hashCode();
        return result;
    }
}

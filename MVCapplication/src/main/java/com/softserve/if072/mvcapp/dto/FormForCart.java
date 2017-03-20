package com.softserve.if072.mvcapp.dto;

import com.softserve.if072.common.model.Cart;
import com.softserve.if072.common.model.Product;
import com.softserve.if072.common.model.Store;
import com.softserve.if072.common.model.User;

import java.util.ArrayList;
import java.util.List;

/**
 * The FormForCart class is used to hold information
 * from form in goShoppingProducts.jsp.
 *
 * @author Roman Dyndyn
 */
public class FormForCart {
    private List<Cart> carts;
    private List<Integer> checkbox;

    public FormForCart() {
        carts = new ArrayList<Cart>();
        checkbox = new ArrayList<Integer>();
    }

    public FormForCart(final int size) {
        carts = new ArrayList<Cart>();
        for (int i = 0; i < size; i++) {
            final Cart cart = new Cart();
            cart.setProduct(new Product());
            cart.setStore(new Store());
            carts.add(cart);
        }
        checkbox = new ArrayList<Integer>();

        for (int i = 0; i < size; i++) {
            checkbox.add(0);
        }
    }

    public List<Integer> getCheckbox() {
        return checkbox;
    }

    public void setCheckbox(final List<Integer> checkbox) {
        this.checkbox = checkbox;
    }

    public List<Cart> getCarts() {
        return carts;
    }

    public void setCarts(final List<Cart> carts) {
        this.carts = carts;
    }

    public void setUser(final User user) {
        for (final Cart cart : carts) {
            cart.setUser(user);
        }
    }

    public int getUserId() {
        if (carts.get(0) != null && carts.get(0).getUser() != null) {
            return carts.get(0).getUser().getId();
        }
        return -1;
    }

    public String getStoreName() {
        if (carts.get(0) != null && carts.get(0).getStore() != null) {
            return carts.get(0).getStore().getName();
        }
        return "store";
    }

    public void removeUncheked() {
        final List<Cart> list = new ArrayList<Cart>();
        for (Integer i : checkbox) {
            if (i != null) {
                list.add(carts.get(i));
            }
        }
        setCarts(list);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder(60);
        sb.append("FormForCart{");
        for (Cart cart : carts) {
            sb.append("Cart{productId=").append(cart.getProduct().getId())
                    .append(", storeId=").append(cart.getStore().getId())
                    .append(", amount=").append(cart.getAmount()).append("}");
        }
        return sb.append(", checkbox=" + checkbox +
                '}').toString();
    }
}

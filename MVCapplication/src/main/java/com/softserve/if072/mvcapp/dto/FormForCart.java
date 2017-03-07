package com.softserve.if072.mvcapp.dto;

import com.softserve.if072.common.model.Cart;
import com.softserve.if072.common.model.Product;
import com.softserve.if072.common.model.Store;
import com.softserve.if072.common.model.User;

import java.util.ArrayList;
import java.util.List;

/**
 * The FormForCart class is used to hold information from form in goShoppingProducts.jsp
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

    public FormForCart(int size) {
        carts = new ArrayList<Cart>();
        for (int i = 0; i < size; i++) {
            Cart cart = new Cart();
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

    public void setCheckbox(List<Integer> checkbox) {
        this.checkbox = checkbox;
    }

    public List<Cart> getCarts() {
        return carts;
    }

    public void setCarts(List<Cart> carts) {
        this.carts = carts;
    }

    public void setUser(User user) {
        for (Cart cart : carts) {
            cart.setUser(user);
        }
    }

    public int getUserId() {
        if (carts.get(0) != null && carts.get(0).getUser() != null) {
            carts.get(0).getUser().getId();
        }
        return -1;
    }

    public void removeUncheked() {
        List<Cart> list = new ArrayList<Cart>();
        for (Integer i : checkbox) {
            if (i != null)
                list.add(carts.get(i));
        }
        setCarts(list);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("FormForCart{");
        for (Cart cart : carts) {
            sb.append("Cart{productId=" + cart.getProduct().getId());
            sb.append(", storeId=" + cart.getStore().getId());
            sb.append(", amount=" + cart.getAmount() + "}");
        }
        return sb.append(", checkbox=" + checkbox +
                '}').toString();
    }
}

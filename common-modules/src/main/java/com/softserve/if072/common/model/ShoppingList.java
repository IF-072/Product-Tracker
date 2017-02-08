package com.softserve.if072.common.model;


/**
 * Created by dyndyn on 18.01.2017.
 */
public class ShoppingList {
    private User user;
    private Product product;
    private int amount;

    public ShoppingList(User user, Product product, int amount) {
        this.user = user;
        this.product = product;
        this.amount = amount;
    }

    public ShoppingList(){}

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "ShoppingList{" +
                "user=" + user +
                ", product=" + product +
                ", amount=" + amount +
                '}';
    }
}

package com.softserve.if072.common.model;


/**
 * Created by dyndyn on 18.01.2017.
 */
public class ShoppingList {
    private User user;
    private Product product;
    private int amount;
    private int id;

    public ShoppingList(User user, Product product, int amount, int id) {
        this.user = user;
        this.product = product;
        this.amount = amount;
        this.id = id;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "ShoppingList{" +
                "user=" + user +
                ", product=" + product +
                ", amount=" + amount +
                ", id=" + id +
                '}';
    }
}

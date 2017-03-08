package com.softserve.if072.common.model;


/**
 * The ShoppingList class stores information about products and their amount
 * that user may buy
 *
 * @author Roman Dyndyn
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

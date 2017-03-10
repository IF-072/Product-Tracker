package com.softserve.if072.common.model;

/**
 * The Cart class stores information about products and their amount
 * that user is going to buy in current store
 *
 * @author Igor Kryviuk
 */
public class Cart {
    private User user;
    private Store store;
    private Product product;
    private int amount;

    public Cart() {
    }

    public Cart(User user, Store store, Product product, int amount) {
        this.user = user;
        this.store = store;
        this.product = product;
        this.amount = amount;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
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
        return "Cart{\nUser: " + user +
                ";\nStore:  " + store +
                ";\nProduct: " + product +
                  ", amount: " + amount +
                "\n}";
    }
}

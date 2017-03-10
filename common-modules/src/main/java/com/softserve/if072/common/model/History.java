package com.softserve.if072.common.model;

import java.sql.Timestamp;

/**
 * The History class stores information about
 * how many units of specific product and when were used by current user
 *
 * @author Igor Kryviuk
 */
public class History {
    private int id;
    private User user;
    private Product product;
    private int amount;
    private Timestamp usedDate;

    public History() {
    }

    public History(int id, User user, Product product, int amount, Timestamp usedDate) {
        this.id = id;
        this.user = user;
        this.product = product;
        this.amount = amount;
        this.usedDate = usedDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    public Timestamp getUsedDate() {
        return usedDate;
    }

    public void setUsedDate(Timestamp usedDate) {
        this.usedDate = usedDate;
    }

    @Override
    public String toString() {
        return "History{\nid:" + id +
                "\nUser: " + user +
                ";\nProduct: " + product +
                ", amount: " + amount +
                ", usedDate: " + usedDate +
                "\n}";
    }
}

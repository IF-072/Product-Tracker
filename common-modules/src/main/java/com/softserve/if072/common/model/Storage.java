package com.softserve.if072.common.model;

import java.sql.Timestamp;

/**
 * The Storage class stores information about product,
 * its estimated time of ending and  amount that user contain in his storage.
 *
 * @author Roman Dyndyn
 */
public class Storage {
    private User user;
    private Product product;
    private int amount;
    private Timestamp endDate;

    public Storage(final User user, final Product product, final int amount, final Timestamp endDate) {
        this.user = user;
        this.product = product;
        this.amount = amount;
        this.endDate = endDate;
    }

    public Storage() {
    }

    public User getUser() {
        return user;
    }

    public void setUser(final User user) {
        this.user = user;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(final Product product) {
        this.product = product;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(final int amount) {
        this.amount = amount;
    }

    public Timestamp getEndDate() {
        return endDate;
    }

    public void setEndDate(final Timestamp endDate) {
        this.endDate = endDate;
    }

    @Override
    public String toString() {
        return "Storage{" +
                "user=" + user +
                ", product=" + product +
                ", amount=" + amount +
                ", endDate=" + endDate +
                '}';
    }
}

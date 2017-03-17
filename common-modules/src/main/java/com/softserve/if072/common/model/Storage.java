package com.softserve.if072.common.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.io.Serializable;
import java.sql.Date;

/**
 * The Storage class stores information about product, its estimated time of ending
 * and  amount that user contain in his storage
 *
 * @author Roman Dyndyn
 */


@Entity
@Table(name = "storage")
public class Storage implements Serializable {

    @Id
    @JoinColumns({
            @JoinColumn(
                    name = "user_id",
                    referencedColumnName = "user_id"),
            @JoinColumn(
                    name = "product_id",
                    referencedColumnName = "product_id")
    })

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(name = "amount")
    private int amount;

    @Column(name = "end_date")
    private Date endDate;

    public Storage(User user, Product product, int amount, Date endDate) {
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

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
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

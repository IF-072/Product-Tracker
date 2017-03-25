package com.softserve.if072.common.model;

import com.softserve.if072.common.model.id.StorageId;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * The Storage class stores information about product,
 * its estimated time of ending and  amount that user contain in his storage.
 *
 * @author Roman Dyndyn
 */

@Entity
@Table(name = "storage")
@IdClass(StorageId.class)
public class Storage implements Serializable {

    @Id
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(name = "amount")
    private int amount;

    @Column(name = "end_date")
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Storage storage = (Storage) o;

        return user.equals(storage.user) && product.equals(storage.product);
    }

    @Override
    public int hashCode() {
        int result = user.hashCode();
        result = 31 * result + product.hashCode();
        return result;
    }
}

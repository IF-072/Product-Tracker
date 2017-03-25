package com.softserve.if072.common.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * The History class stores information about
 * how many units of specific product and when were purchased or used by current user
 *
 * @author Igor Kryviuk
 */

@Entity
@Table(name = "history")
public class History implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(name = "amount")
    private int amount;

    @Column(name = "used_date")
    private Timestamp usedDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "action")
    private Action action;

    public History() {
    }

    public History(int id, User user, Product product, int amount, Timestamp usedDate, Action action) {
        this.id = id;
        this.user = user;
        this.product = product;
        this.amount = amount;
        this.usedDate = usedDate;
        this.action = action;
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

    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        this.action = action;
    }

    @Override
    public String toString() {
        return "History{\nid:" + id +
                "\nUser: " + user +
                ";\nProduct: " + product +
                ", amount: " + amount +
                ", usedDate: " + usedDate +
                "; action: " + action +
                "\n}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        History history = (History) o;

        return id == history.id && user.equals(history.user) && product.equals(history.product);
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + user.hashCode();
        result = 31 * result + product.hashCode();
        return result;
    }
}

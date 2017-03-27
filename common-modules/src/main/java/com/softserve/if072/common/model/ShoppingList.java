package com.softserve.if072.common.model;


import com.softserve.if072.common.model.id.ShoppingListId;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * The ShoppingList class stores information about products and their amount
 * that user may buy.
 *
 * @author Roman Dyndyn
 */

@Entity
@Table(name = "shopping_list")
@IdClass(ShoppingListId.class)
public class ShoppingList implements Serializable {

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

    public ShoppingList(final User user, final Product product, final int amount) {
        this.user = user;
        this.product = product;
        this.amount = amount;
    }

    public ShoppingList() {
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

    @Override
    public String toString() {
        return "ShoppingList{" +
                "user=" + user +
                ", product=" + product +
                ", amount=" + amount +
                '}';
    }

}

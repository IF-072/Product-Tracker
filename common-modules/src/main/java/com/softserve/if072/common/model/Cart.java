package com.softserve.if072.common.model;

import com.softserve.if072.common.model.id.CartId;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * The Cart class stores information about products and their amount
 * that user is going to buy in current store
 *
 * @author Igor Kryviuk
 */
@Entity
@Table(name = "cart")
@IdClass(CartId.class)
public class Cart implements Serializable {

    @Id
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    @Id
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id")
    private Store store;

    @Id
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(name = "amount")
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Cart cart = (Cart) o;

        return user.equals(cart.user) && store.equals(cart.store) && product.equals(cart.product);
    }

    @Override
    public int hashCode() {
        int result = user.hashCode();
        result = 31 * result + store.hashCode();
        result = 31 * result + product.hashCode();
        return result;
    }
}

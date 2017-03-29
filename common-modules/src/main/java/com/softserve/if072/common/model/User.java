package com.softserve.if072.common.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.List;

/**
 * This class stores information about user.
 *
 * @author Oleh Pochernin
 * @author Igor Parada
 */

@Entity
@Table(name = "user")
public class User implements Serializable {
    static final long serialVersionUID = 98786688446743962L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Transient
    private String name;
    @Transient
    private String email;
    @Transient
    private String password;
    @Transient
    private boolean isEnabled;
    @Transient
    private List<Store> stores;
    @Transient
    private List<Product> products;
    @Transient
    private List<Cart> carts;
    @Transient
    private List<Category> categories;
    @Transient
    private List<ShoppingList> shoppingLists;
    @Transient
    private List<Storage> storages;
    @Transient
    private Role role;
    @Transient
    private long premiumExpiresTime;

    public User() {
        /*NOP*/
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isEnabled() {
        return isEnabled;
    }

    public void setEnabled(boolean enabled) {
        isEnabled = enabled;
    }

    public List<Store> getStores() {
        return stores;
    }

    public void setStores(List<Store> stores) {
        this.stores = stores;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public List<Cart> getCarts() {
        return carts;
    }

    public void setCarts(List<Cart> carts) {
        this.carts = carts;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public List<ShoppingList> getShoppingLists() {
        return shoppingLists;
    }

    public void setShoppingLists(List<ShoppingList> shoppingLists) {
        this.shoppingLists = shoppingLists;
    }

    public List<Storage> getStorages() {
        return storages;
    }

    public void setStorages(List<Storage> storages) {
        this.storages = storages;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public long getPremiumExpiresTime() {
        return premiumExpiresTime;
    }

    public void setPremiumExpiresTime(long premiumExpiresTime) {
        this.premiumExpiresTime = premiumExpiresTime;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", isEnabled=" + isEnabled +
                ", stores=" + stores +
                ", products=" + products +
                ", carts=" + carts +
                ", categories=" + categories +
                ", shoppingLists=" + shoppingLists +
                ", storages=" + storages +
                ", role=" + role +
                ", premiumExpiresTime=" + premiumExpiresTime +
                '}';
    }
}

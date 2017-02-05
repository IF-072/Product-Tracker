package com.softserve.if072.common.model;


import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Size;
import java.util.List;

/**
 * This class stores information about user.
 *
 * @author Oleh Pochernin
 */
public class User {

    private int id;
    private String name;

    @NotEmpty(message = "{error.email.notnull}")
    @Size(min = 5, max = 64, message = "{error.email.size}")
    private String email;

    @NotEmpty(message = "{error.password.notnull}")
    @Size(min = 4, max = 64, message = "{error.password.size}")
    private String password;

    private boolean isEnabled;
    private List<Store> stores;
    private List<Product> products;
    private List<Cart> carts;
    private List<Category> categories;
    private List<ShoppingList> shoppingLists;
    private List<Storage> storages;
    private Role role;

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

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", isEnabled=" + isEnabled +
                ", stores=" + stores +
                ", products=" + products +
                ", carts=" + carts +
                ", categories=" + categories +
                ", shoppingLists=" + shoppingLists +
                ", storages=" + storages +
                ", role=" + role +
                '}';
    }
}

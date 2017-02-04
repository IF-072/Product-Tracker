package com.softserve.if072.common.model;

import java.util.List;

/**
 * This Store class contains information about current store and
 * about products that user can buy in current store
 *
 * @author Nazar Vynnyk
 */
public class Store {

    private int id;
    private String name;
    private String address;
    private User user;
    private boolean isEnabled;
    private List<Product> products;
    private String latitute;
    private String longtitude;

    public Store(int id, String name, String address, User user, boolean isEnabled, List<Product> products, String
            latitute, String longtitude) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.user = user;
        this.isEnabled = isEnabled;
        this.products = products;
        this.latitute = latitute;
        this.longtitude = longtitude;
    }

    public Store() {}

    public Store(int id, String name, String address, User user, boolean isEnabled, List<Product> products) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.user = user;
        this.isEnabled = isEnabled;
        this.products = products;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public User getUser() {return this.user; }

    public void setUser(User user) {this.user = user;}

    public boolean isEnabled() {
        return isEnabled;
    }

    public void setEnabled(boolean enabled) {
        isEnabled = enabled;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public String getLatitute() {
        return latitute;
    }

    public void setLatitute(String latitute) {
        this.latitute = latitute;
    }

    public String getLongtitude() {
        return longtitude;
    }

    public void setLongtitude(String longtitude) {
        this.longtitude = longtitude;
    }

    @Override
    public String toString() {
        return "Store{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", user=" + user +
                ", isEnabled=" + isEnabled +
                ", products=" + products +
                ", latitute='" + latitute + '\'' +
                ", longtitude='" + longtitude + '\'' +
                '}';
    }
}
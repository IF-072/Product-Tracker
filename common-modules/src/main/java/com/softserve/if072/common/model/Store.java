package com.softserve.if072.common.model;

import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Size;
import java.util.List;

/**
 * This Store class contains information about current store and
 * about products that user can buy in current store
 *
 * @author Nazar Vynnyk
 */

public class Store {

    private int id;

    @NotBlank(message = "{error.name.notnull}")
    @Size(min = 3, max = 64, message = "{error.name.size}")
    private String name;

    @Size(min = 5, max = 255, message = "{error.address.size}")
    private String address;
    private User user;
    private boolean isEnabled;
    private List<Product> products;
    private String latitude;
    private String longitude;

    public Store() {
    }

    public Store(int id, String name, String address, User user, boolean isEnabled, List<Product> products, String
            latitude, String longitude) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.user = user;
        this.isEnabled = isEnabled;
        this.products = products;
        this.latitude = latitude;
        this.longitude = longitude;
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

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

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

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Store store = (Store) o;

        if (id != store.id) return false;
        if (!name.equals(store.name)) return false;
        return address != null ? address.equals(store.address) : store.address == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + name.hashCode();
        result = 31 * result + (address != null ? address.hashCode() : 0);
        return result;
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
                ", latitude='" + latitude + '\'' +
                ", longitude='" + longitude + '\'' +
                '}';
    }
}
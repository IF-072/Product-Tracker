package com.softserve.if072.common.model;


public class Store {

    private int id;
    private String name;
    private String address;
    private User User;
    private boolean isActive;

    public Store() {}

    public Store(int id, String name, String address,
                 com.softserve.if072.common.model.User user,
                 boolean isActive) {
        this.id = id;
        this.name = name;
        this.address = address;
        User = user;
        this.isActive = isActive;
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

    public com.softserve.if072.common.model.User getUser() {
        return User;
    }

    public void setUser(com.softserve.if072.common.model.User user) {
        User = user;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    @Override
    public String toString() {
        return "Store{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", User=" + User +
                ", isActive=" + isActive +
                '}';
    }
}
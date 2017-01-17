package com.softserve.if072.common.model;

public class Product {
    private int id;
    private String name;
    private String description;
    private Image image;
    private User user;
    private Category category;
    private Unit unit;
    private boolean isActive;

    public Product() {}

    public Product(int id, String name, String description, Image image, User user, Category category, Unit unit, boolean isActive) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.image = image;
        this.user = user;
        this.category = category;
        this.unit = unit;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Unit getUnit() {
        return unit;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", image=" + image +
                ", user=" + user +
                ", category=" + category +
                ", unit=" + unit +
                ", isActive=" + isActive +
                '}';
    }
}

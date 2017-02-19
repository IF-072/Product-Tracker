package com.softserve.if072.common.model;

import org.hibernate.validator.constraints.NotEmpty;

import java.util.List;

/**
 * The class contains information about current product and
 * about the stores where this product is available for purchase
 *
 * @author Vitaliy Malisevych
 */

public class Product {

    private int id;

    @NotEmpty(message = "{error.productName.notnull}")
    private String name;
    private String description;
    private Image image;
    private User user;
    private Category category;
    private Unit unit;
    private boolean isEnabled;
    private List<Store> stores;

    public Product() {
    }

    public Product(int id, String name, String description, Image image, User user, Category category, Unit unit, boolean isEnabled, List<Store> stores) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.image = image;
        this.user = user;
        this.category = category;
        this.unit = unit;
        this.isEnabled = isEnabled;
        this.stores = stores;
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

//    public boolean equals(Object object) {
//        if (this == object) return true;
//        if (object == null || getClass() != object.getClass()) return false;
//        if (!super.equals(object)) return false;
//
//        Product product = (Product) object;
//
//        if (!name.equals(product.name)) return false;
//        if (description != null ? !description.equals(product.description) : product.description != null) return false;
//        if (category != null ? !category.equals(product.category) : product.category != null) return false;
//        if (unit != null ? !unit.equals(product.unit) : product.unit != null) return false;
//
//        return true;
//    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Product product = (Product) o;

        return id == product.id;
    }

    public int hashCode() {
        int result=1;// = super.hashCode();
        result = 31 * result + name.hashCode();
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (category != null ? category.hashCode() : 0);
        result = 31 * result + (unit != null ? unit.hashCode() : 0);
        return result;
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
                ", isEnabled=" + isEnabled +
                ", stores=" + stores +
                '}';
    }
}
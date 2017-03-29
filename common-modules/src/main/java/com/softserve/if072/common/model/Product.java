package com.softserve.if072.common.model;

import com.softserve.if072.common.model.validation.product.ValidCategory;
import com.softserve.if072.common.model.validation.product.ValidUnit;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.List;

/**
 * The class contains information about current product and
 * about the stores where this product is available for purchase
 *
 * @author Vitaliy Malisevych
 */
@Entity
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @NotBlank(message = "{error.productName.notblank}")
    @Length(min = 3, max = 64, message = "{error.productName.length}")
    @Column(name = "name")
    private String name;

    @Length(max = 255, message = "{error.productDescription.length}")
    @Column(name = "description")
    private String description;

    @Transient
    private Image image;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    @ValidCategory(message = "{error.productCategory.notempty}")
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @ValidUnit(message = "{error.productUnit.notempty}")
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "unit_id")
    private Unit unit;

    @Transient
    private boolean isEnabled;

    @Transient
    private List<Store> stores;

    public Product() {
    }

    public Product(int id, String name, String description, Image image, User user, Category category, Unit unit,
                   boolean isEnabled, List<Store> stores) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Product product = (Product) o;

        return id == product.id;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + name.hashCode();
        result = 31 * result + (description != null ? description.hashCode() : 0);
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
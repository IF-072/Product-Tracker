package com.softserve.if072.common.model;

/**
 * The Cart class stores information about products and their amount
 * that user is going to buy in current store
 *
 * @author Igor Kryviuk
 */
public class Cart {
    private User user;
    private Store store;
    private Product product;
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
        return "Cart{\nUser: " +
                user.getName() +
                ", email: " + user.getEmail() +
                ";\nStore:  " + store.getName() +
                ";\nProduct: " + product.getName() +
                ", category: " + product.getCategory() +
                ", amount: " + amount +
                product.getUnit().getName() +
                "\n}";
    }

    public static class Builder {
        private User user;
        private Store store;
        private Product product;
        private int amount;

        public Builder user(User user) {
            this.user = user;
            return this;
        }

        public Builder defaultUser() {
            user = new User();
            user.setName("user");
            user.setEmail("user%d@gmail.com");
            return this;
        }

        public Builder defaultUser(int count) {
            user = new User();
            user.setName("user" + count);
            user.setEmail(String.format("user%d@gmail.com", count));
            return this;
        }

        public Builder store(Store store) {
            this.store = store;
            return this;
        }

        public Builder defaultStore() {
            store = new Store();
            store.setName("store");
            return this;
        }

        public Builder defaultStore(int count) {
            store = new Store();
            store.setName("store" + count);
            return this;
        }

        public Builder product(Product product) {
            this.product = product;
            return this;
        }

        public Builder defaultProduct() {
            product = new Product();
            product.setName("product");
            return this;
        }

        public Builder defaultProduct(int count) {
            product = new Product();
            product.setName("product" + count);
            return this;
        }

        public Builder amount(int amount) {
            this.amount = amount;
            return this;
        }

        public Cart build() {
            return new Cart(this);
        }
    }

    private Cart(Builder builder) {
        user = builder.user;
        store = builder.store;
        product = builder.product;
        amount = builder.amount;
    }
}

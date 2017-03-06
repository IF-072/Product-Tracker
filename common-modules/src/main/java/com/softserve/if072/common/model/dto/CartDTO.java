package com.softserve.if072.common.model.dto;

/**
 * The CartDTO class is used to simplify data transfer operations
 * between MVCapplication and RESTserver
 *
 * @author Igor Kryviuk
 */
public class CartDTO {
    private int userId;
    private int storeId;
    private int productId;
    private int amount;
    private int initialAmount;

    public CartDTO() {
    }

    public CartDTO(int userId, int storeId, int productId, int amount, int initialAmount) {
        this.userId = userId;
        this.storeId = storeId;
        this.productId = productId;
        this.amount = amount;
        this.initialAmount = initialAmount;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getStoreId() {
        return storeId;
    }

    public void setStoreId(int storeId) {
        this.storeId = storeId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getInitialAmount() {
        return initialAmount;
    }

    public void setInitialAmount(int initialAmount) {
        this.initialAmount = initialAmount;
    }

    @Override
    public String toString() {
        return "CartDTO{ UserId: " + userId +
                "; StoreId:  " + storeId +
                "; ProductId: " + productId +
                ", amount: " + amount +
                "}";
    }

    public static class Builder {
        private int userId;
        private int storeId;
        private int productId;
        private int amount;
        private int initialAmount;

        public Builder userId(int userId) {
            this.userId = userId;
            return this;
        }

        public Builder storeId(int storeId) {
            this.storeId = storeId;
            return this;
        }

        public Builder productId(int productId) {
            this.productId = productId;
            return this;
        }

        public Builder amount(int amount) {
            this.amount = amount;
            return this;
        }

        public Builder initialAmount(int initialAmount) {
            this.initialAmount = amount;
            return this;
        }

        public CartDTO build() {
            return new CartDTO(this);
        }
    }

    private CartDTO(Builder builder) {
        userId = builder.userId;
        storeId = builder.storeId;
        productId = builder.productId;
        amount = builder.amount;
        initialAmount = builder.initialAmount;
    }
}

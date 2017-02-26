package com.softserve.if072.common.model.dto;

/**
 * Created by dyndyn on 24.02.2017.
 */
public class StorageDTO {
    int userId;
    int productId;
    int amount;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
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

    @Override
    public String toString() {
        return "StorageDTO{" +
                "userId=" + userId +
                ", productId=" + productId +
                ", amount=" + amount +
                '}';
    }
}

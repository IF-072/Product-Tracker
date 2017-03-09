package com.softserve.if072.common.model.dto;

import javax.validation.constraints.Min;

/**
 * The StorageDTO class is used to simplify data transfer operations
 * between MVCapplication and RESTserver
 *
 * @author Roman Dyndyn
 */
public class StorageDTO {
    private int userId;
    @Min(value = 1, message = "{error.storage.product}")
    private int productId;
    @Min(value = 0, message = "{error.storage.amount}")
    private int amount;

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

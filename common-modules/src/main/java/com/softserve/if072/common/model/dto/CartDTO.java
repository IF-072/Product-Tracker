package com.softserve.if072.common.model.dto;

/**
 * The CartDTO class is used to simplify data transfer operations
 * between MVC application and REST server
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
}

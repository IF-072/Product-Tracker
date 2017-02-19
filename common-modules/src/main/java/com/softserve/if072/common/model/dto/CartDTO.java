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

    public CartDTO() {
    }

    public CartDTO(int userId, int storeId, int productId, int amount) {
        this.userId = userId;
        this.storeId = storeId;
        this.productId = productId;
        this.amount = amount;
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

    @Override
    public String toString() {
        return "Cart{ UserId: " + userId +
                "; StoreId:  " + storeId +
                "; ProductId: " + productId +
                ", amount: " + amount +
                "}";
    }
}

package com.softserve.if072.common.model.dto;

import com.softserve.if072.common.model.Action;

import java.sql.Timestamp;

/**
 * The HistoryDTO class is used to simplify data transfer operations
 * between MVCapplication and RESTserver
 *
 * @author Igor Kryviuk
 */
public class HistoryDTO {
    private int id;
    private int userId;
    private int productId;
    private int amount;
    private Timestamp usedDate;
    private Action action;

    public HistoryDTO() {
    }

    public HistoryDTO(int id, int userId, int productId, int amount, Timestamp usedDate, Action action) {
        this.id = id;
        this.userId = userId;
        this.productId = productId;
        this.amount = amount;
        this.usedDate = usedDate;
        this.action=action;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    public Timestamp getUsedDate() {
        return usedDate;
    }

    public void setUsedDate(Timestamp usedDate) {
        this.usedDate = usedDate;
    }

    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        this.action = action;
    }

    @Override
    public String toString() {
        return "HistoryDTO{ UserId: " + userId +
                "; ProductId: " + productId +
                "; amount:  " + amount +
                "; usedDate " + usedDate +
                "; action: " + action +
                "}";
    }
}

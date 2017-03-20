package com.softserve.if072.common.model.dto;

import java.util.Date;

/**
 * The HistorySearchDTO class is used to simplify data transfer operations
 * between MVCapplication and RESTserver
 *
 * @author Igor Parada
 */
public class HistorySearchDTO {
    private String name;
    private String description;
    private int categoryId;
    private Date fromDate;
    private Date toDate;

    public HistorySearchDTO() {
    }

    public HistorySearchDTO(String name, String description, int categoryId, Date fromDate, Date toDate) {
        this.name = name;
        this.description = description;
        this.categoryId = categoryId;
        this.fromDate = fromDate;
        this.toDate = toDate;
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

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public Date getToDate() {
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }

    @Override
    public String toString() {
        return "HistorySearchDTO{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", categoryId=" + categoryId +
                ", fromDate=" + fromDate +
                ", toDate=" + toDate +
                '}';
    }
}

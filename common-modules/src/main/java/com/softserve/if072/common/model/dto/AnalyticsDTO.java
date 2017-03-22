package com.softserve.if072.common.model.dto;

import java.util.Date;

public class AnalyticsDTO {
    private int productId;
    private Date fromDate;
    private Date toDate;

    public AnalyticsDTO() {}

    public AnalyticsDTO(int productId, Date fromDate, Date toDate) {
        this.productId = productId;
        this.fromDate = fromDate;
        this.toDate = toDate;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
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
}

package com.softserve.if072.common.model;

import java.sql.Timestamp;
import java.util.Arrays;

/**
 * The ProductStatistics class stores information about the main statistics properties of products using
 *
 * @author Igor Kryviuk
 */
public class ProductStatistics {
    private int productId;
    private String productName;
    private String productUnit;
    private int amountInStorage;
    private Timestamp lastUsingDate;
    private Timestamp lastPurchasingDate;
    private Timestamp endDate;
    private double mean;
    private double min;
    private double max;
    private double count;
    private double range;
    private int totalPurchased;
    private int totalUsed;
    private double standardDeviation;
    private double lowerThreeSigmaLimit;
    private double upperThreeSigmaLimit;
    private double[] productUsingSpeeds;
    private Timestamp[] purchasingProductDates;
    private int[] purchasingProductAmounts;
    private Timestamp[] usingProductDates;
    private int[] usingProductAmounts;
    private boolean hasOutliers;

    public ProductStatistics() {
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductUnit() {
        return productUnit;
    }

    public void setProductUnit(String productUnit) {
        this.productUnit = productUnit;
    }

    public int getAmountInStorage() {
        return amountInStorage;
    }

    public void setAmountInStorage(int amountInStorage) {
        this.amountInStorage = amountInStorage;
    }

    public Timestamp getLastUsingDate() {
        return lastUsingDate;
    }

    public void setLastUsingDate(Timestamp lastUsingDate) {
        this.lastUsingDate = lastUsingDate;
    }

    public Timestamp getLastPurchasingDate() {
        return lastPurchasingDate;
    }

    public void setLastPurchasingDate(Timestamp lastPurchasingDate) {
        this.lastPurchasingDate = lastPurchasingDate;
    }

    public Timestamp getEndDate() {
        return endDate;
    }

    public void setEndDate(Timestamp endDate) {
        this.endDate = endDate;
    }

    public double getMean() {
        return mean;
    }

    public void setMean(double mean) {
        this.mean = mean;
    }

    public double getMin() {
        return min;
    }

    public void setMin(double min) {
        this.min = min;
    }

    public double getMax() {
        return max;
    }

    public void setMax(double max) {
        this.max = max;
    }

    public double getCount() {
        return count;
    }

    public void setCount(double count) {
        this.count = count;
    }

    public double getRange() {
        return range;
    }

    public void setRange(double range) {
        this.range = range;
    }

    public int getTotalPurchased() {
        return totalPurchased;
    }

    public void setTotalPurchased(int totalPurchased) {
        this.totalPurchased = totalPurchased;
    }

    public int getTotalUsed() {
        return totalUsed;
    }

    public void setTotalUsed(int totalUsed) {
        this.totalUsed = totalUsed;
    }

    public double getStandardDeviation() {
        return standardDeviation;
    }

    public void setStandardDeviation(double standardDeviation) {
        this.standardDeviation = standardDeviation;
    }

    public double getLowerThreeSigmaLimit() {
        return lowerThreeSigmaLimit;
    }

    public void setLowerThreeSigmaLimit(double lowerThreeSigmaLimit) {
        this.lowerThreeSigmaLimit = lowerThreeSigmaLimit;
    }

    public double getUpperThreeSigmaLimit() {
        return upperThreeSigmaLimit;
    }

    public void setUpperThreeSigmaLimit(double upperThreeSigmaLimit) {
        this.upperThreeSigmaLimit = upperThreeSigmaLimit;
    }

    public double[] getProductUsingSpeeds() {
        return productUsingSpeeds;
    }

    public void setProductUsingSpeeds(double[] productUsingSpeeds) {
        this.productUsingSpeeds = productUsingSpeeds;
    }

    public Timestamp[] getPurchasingProductDates() {
        return purchasingProductDates;
    }

    public void setPurchasingProductDates(Timestamp[] purchasingProductDates) {
        this.purchasingProductDates = purchasingProductDates;
    }

    public int[] getPurchasingProductAmounts() {
        return purchasingProductAmounts;
    }

    public void setPurchasingProductAmounts(int[] purchasingProductAmounts) {
        this.purchasingProductAmounts = purchasingProductAmounts;
    }

    public Timestamp[] getUsingProductDates() {
        return usingProductDates;
    }

    public void setUsingProductDates(Timestamp[] usingProductDates) {
        this.usingProductDates = usingProductDates;
    }

    public int[] getUsingProductAmounts() {
        return usingProductAmounts;
    }

    public void setUsingProductAmounts(int[] usingProductAmounts) {
        this.usingProductAmounts = usingProductAmounts;
    }

    public boolean getHasOutliers() {
        return hasOutliers;
    }

    public void setHasOutliers(boolean hasOutliers) {
        this.hasOutliers = hasOutliers;
    }

    @Override
    public String toString() {
        return "ProductStatistics{" +
                "productId=" + productId +
                ", productName=" + productName +
                ", productUnit=" + productUnit +
                ", amountInStorage=" + amountInStorage +
                ", lastUsingDate=" + lastUsingDate +
                ", lastPurchasingDate=" + lastPurchasingDate +
                ", endDate=" + endDate +
                ", mean=" + mean +
                ", min=" + min +
                ", max=" + max +
                ", count=" + count +
                ", range=" + range +
                ", totalPurchased=" + totalPurchased +
                ", totalUsed=" + totalUsed +
                ", standardDeviation=" + standardDeviation +
                ", lowerThreeSigmaLimit=" + lowerThreeSigmaLimit +
                ", upperThreeSigmaLimit=" + upperThreeSigmaLimit +
                ", productUsingSpeeds=" + Arrays.toString(productUsingSpeeds) +
                ", purchasingProductDates=" + Arrays.toString(purchasingProductDates) +
                ", purchasingProductAmounts=" + Arrays.toString(purchasingProductAmounts) +
                ", usingProductDates=" + Arrays.toString(usingProductDates) +
                ", usingProductAmounts=" + Arrays.toString(usingProductAmounts) +
                ", hasOutliers=" + hasOutliers +
                '}';
    }
}

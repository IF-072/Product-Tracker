package com.softserve.if072.common.model;

/**
 * The ProductAmountItem class stores information about current amount of product
 *
 * @author Igor Kryviuk
 */
public class ProductAmountItem {
    private Product product;
    private int amount;

    public ProductAmountItem() {
    }

    public ProductAmountItem(Product product, int amount) {
        this.product = product;
        this.amount = amount;
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
        return product.getName()+" - "+amount+" "+product.getUnit();
    }
}

package com.softserve.if072.common.model;

import java.util.ArrayList;
import java.util.List;

/**
 * The Cart class stores information about products and their amount
 * that user is going to buy in current store
 *
 * @author Igor Kryviuk
 */
public class Cart {
    private User user;
    private Store store;
    List<ProductAmountItem> itemList;

    public Cart() {
        itemList=new ArrayList<ProductAmountItem>();
    }

    public Cart(User user, Store store, List<ProductAmountItem> itemList) {
        this.user = user;
        this.store = store;
        this.itemList = itemList;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    public List<ProductAmountItem> getItemList() {
        return itemList;
    }

    public void setItemList(List<ProductAmountItem> itemList) {
        this.itemList = itemList;
    }

    /**
     * Add product with amount to current cart if it doesn't exist or set new amount
     * to existing product.
     *
     * @param productAmountItem item that should be added to current cart
     *                          or changed in current cart.
     */
    public void addProductAmountItem(ProductAmountItem productAmountItem) {
        int productId = productAmountItem.getProduct().getId();
        int amount = productAmountItem.getAmount();

        for (ProductAmountItem item : itemList) {
            if (item.getProduct().getId() == productId) {
                item.setAmount(amount);
                return;
            }
        }
        itemList.add(productAmountItem);
    }

    /**
     * Remove product form current cart.
     *
     * @param productAmountItem item that should be removed from current cart.
     *                          Requires that productAmountItem occurs exactly once
     *                          in the cart item.
     */
    public void removeProductAmountItem(ProductAmountItem productAmountItem) {
        int productId = productAmountItem.getProduct().getId();

        for (ProductAmountItem item : itemList) {
            if (item.getProduct().getId() == productId) {
                itemList.remove(item);
                return;
            }
        }

        /*This code should never be reached*/
        assert false : "There isn't " + productAmountItem.getProduct().getName() + " in your cart!";
    }

    @Override
    public String toString() {
        return user.getName() +
                "! You need to by at the " +
                store.getName() +
                " such things: " +
                itemList.toString();
    }
}

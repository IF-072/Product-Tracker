package com.softserve.if072.mvcapp.dto;

import java.util.List;

/**
 * Created by Nazar Vynnyk
 */
public class ProductsWrapper {


    private List<Integer> productsID;

    public List<Integer> getProducts() {
        return productsID;
    }

    public void setProducts(List<Integer> productsID) {
        this.productsID = productsID;
    }
}

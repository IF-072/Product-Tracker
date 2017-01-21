package com.softserve.if072.common.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by dyndyn on 17.01.2017.
 */
public class Storage {
    private User user;
    private Map<Product, Integer> products;
    private List<Integer> ids;

    public Storage(List<StorageSimple> list){
        if(list != null && list.size() != 0) {
            user = list.get(0).getUser();
            products = new HashMap<Product, Integer>();
            ids = new ArrayList<Integer>();
            for (StorageSimple simple : list){
                products.put(simple.getProduct(), simple.getAmount());
                ids.add(simple.getId());
            }
        }
    }

    public Storage() {
        products = new HashMap<Product, Integer>();
        ids = new ArrayList<Integer>();
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Map<Product, Integer> getProducts() {
        return products;
    }

    public void setProducts(Map<Product, Integer> products) {
        this.products = products;
    }

    public Integer getAmount(Product product){
        return products.get(product);
    }

    public List<Integer> getIds() {
        return ids;
    }

    public void setIds(List<Integer> ids) {
        this.ids = ids;
    }

    public Integer getId(int index){
        return ids.get(index);
    }

    public void add(Product product, Integer amount) {
        products.put(product, amount);
        ids.add(0);
    }

    @Override
    public String toString() {
        return "Storage{" +
                "user=" + user +
                ", products=" + products +
                ", ids=" + ids +
                '}';
    }
}

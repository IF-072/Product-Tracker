package com.softserve.if072.restservice.service;

import com.softserve.if072.common.model.Product;

import java.util.List;

public interface ProductService {

    List<Product> getAll();

    Product getById(int id);

    void insert(Product product);

    void update(Product product);

    void delete(int id);
}

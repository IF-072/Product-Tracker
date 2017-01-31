package com.softserve.if072.restservice.service.impl;

import com.softserve.if072.common.model.Product;
import com.softserve.if072.restservice.dao.mybatisdao.ProductDAO;
import com.softserve.if072.restservice.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductDAO productDAO;

    @Override
    public List<Product> getAll() {return productDAO.getAll();}

    @Override
    public Product getById(int id) {return productDAO.getByID(id);}

    @Override
    public void insert(Product product) {productDAO.insert(product);}

    @Override
    public void update(Product product) {productDAO.update(product);}

    @Override
    public void delete(int id) {productDAO.deleteById(id);}
}

package com.softserve.if072.restservice.service.impl;

import com.softserve.if072.common.model.Category;
import com.softserve.if072.restservice.dao.mybatisdao.CategoryDAO;
import com.softserve.if072.restservice.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryDAO categoryDAO;

    @Override
    public List<Category> getAll() {
        return categoryDAO.getAll();
    }

    @Override
    public Category getById(int id) {
        return categoryDAO.getByID(id);
    }

    @Override
    public void insert(Category category) {
        categoryDAO.insert(category);
    }

    @Override
    public void update(Category category) {
        categoryDAO.update(category);
    }

    @Override
    public void delete(int id) {
        categoryDAO.delete(id);
    }
}

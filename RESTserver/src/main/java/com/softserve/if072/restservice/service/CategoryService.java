package com.softserve.if072.restservice.service;

import com.softserve.if072.common.model.Category;
import com.softserve.if072.restservice.dao.mybatisdao.CategoryDAO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    private CategoryDAO categoryDAO;

    public List<Category> getAll() {
        return categoryDAO.getAll();
    }

    public Category getById(int id) {
        return categoryDAO.getByID(id);
    }

    public void insert(Category category) {
        categoryDAO.insert(category);
    }

    public void update(Category category) {
        categoryDAO.update(category);
    }

    public void delete(int id) {
        categoryDAO.deleteById(id);
    }
}

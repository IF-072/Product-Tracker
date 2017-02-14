package com.softserve.if072.restservice.service;

import com.softserve.if072.common.model.Category;
import com.softserve.if072.restservice.dao.mybatisdao.CategoryDAO;
import com.softserve.if072.restservice.exception.DataNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import javax.xml.crypto.Data;
import java.util.List;

/**
 * The class contains methods for getting and manipulating data from the database
 *
 * @author Pavlo Bendus
 */

@Service
public class CategoryService {

    private CategoryDAO categoryDAO;

    @Autowired
    public CategoryService(CategoryDAO categoryDAO) {
        this.categoryDAO = categoryDAO;
    }

    public List<Category> getByUserID(int userID) throws DataNotFoundException {
        List<Category> categories = categoryDAO.getByUserID(userID);

        if (!categories.isEmpty()) {
            return categories;
        } else {
            throw new DataNotFoundException("Categories not found");
        }
    }

    public Category getById(int id) throws DataNotFoundException {
        Category category = categoryDAO.getByID(id);

        if (category != null) {
            return category;
        } else {
            throw new DataNotFoundException(String.format("Category with id %d not found", id));
        }
    }

    public void insert(Category category) {
        Category category1 = categoryDAO.getByID(category.getId());

        if (category1 == null) {
            categoryDAO.insert(category);
        }
    }

    public void update(Category category) throws DataNotFoundException {
        if (category.getName() != null && category.getUser() != null) {
            categoryDAO.update(category);
        } else {
            throw new DataNotFoundException("Incorrect fields for category");
        }
    }

    public void deleteById(int id) {
        categoryDAO.deleteById(id);
    }
}

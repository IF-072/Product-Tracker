package com.softserve.if072.restservice.service;

import com.softserve.if072.common.model.Category;
import com.softserve.if072.restservice.dao.mybatisdao.CategoryDAO;
import com.softserve.if072.restservice.exception.DataNotFoundException;
import org.apache.commons.collections.CollectionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * The class contains methods for getting and manipulating data from the database
 *
 * @author Pavlo Bendus
 */

@Service
public class CategoryService {

    private static final Logger LOGGER = LogManager.getLogger(CategoryService.class);

    private CategoryDAO categoryDAO;

    @Value("categories.notFound")
    private String categoriesNotFound;

    @Value("category.notFound")
    private String categoryNotFound;

    @Value("categoryByName.notFound")
    private String categoryByNameNotFound;

    @Autowired
    public CategoryService(CategoryDAO categoryDAO) {
        this.categoryDAO = categoryDAO;
    }

    public List<Category> getByUserID(int userID) throws DataNotFoundException {
        List<Category> categories = categoryDAO.getByUserID(userID);
        if (!CollectionUtils.isEmpty(categories)) {
            LOGGER.info(categories);
            return categories;
        } else {
            throw new DataNotFoundException(categoriesNotFound);
        }
    }

    public Category getByID(int id) throws DataNotFoundException {
        Category category = categoryDAO.getByID(id);

        if (category != null) {
            return category;
        } else {
            throw new DataNotFoundException(String.format(categoryNotFound, id));
        }
    }

    public Category getByNameAndUserID(String name, int userID) throws DataNotFoundException {

        Category category = categoryDAO.getByNameAndUserID(name, userID);

        if (category != null) {
            return category;
        } else {
            throw new DataNotFoundException(String.format(categoryByNameNotFound, name, userID));
        }
    }

    public void insert(Category category) {
        Category category1 = categoryDAO.getByID(category.getId());

        if (category1 == null) {
            categoryDAO.insert(category);
        }
    }

    public void update(Category category) throws IllegalArgumentException {
        if (category.getName() != null && category.getUser() != null) {
            categoryDAO.update(category);
        } else {
            throw new IllegalArgumentException();
        }
    }

    public void restore(Category category) throws IllegalArgumentException {
        if (category.getId() != 0 && category.getUser() != null) {
            categoryDAO.restore(category);
        } else {
            throw new IllegalArgumentException();
        }
    }

    public void deleteById(int id) throws IllegalArgumentException {
        if (id != 0) {
            categoryDAO.deleteById(id);
        } else {
            throw new IllegalArgumentException();
        }
    }
}

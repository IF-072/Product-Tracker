package com.softserve.if072.restservice.service;

import com.softserve.if072.common.model.Category;

import java.util.List;

public interface CategoryService {

    List<Category> getAll();

    Category getById(int id);

    void insert(Category category);

    void update(Category category);

    void delete(int id);
}

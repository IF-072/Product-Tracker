package com.softserve.if072.restservice.controller;

import com.softserve.if072.common.model.Category;
import com.softserve.if072.restservice.service.impl.CategoryServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

public class CategoryController {

    @Autowired
    private CategoryServiceImpl categoryService;

    @RequestMapping(value = "/category/all")
    @ResponseBody
    public List<Category> getAll() {
        return categoryService.getAll();
    }
}

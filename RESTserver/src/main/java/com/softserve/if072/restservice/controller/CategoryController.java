package com.softserve.if072.restservice.controller;

import com.softserve.if072.common.model.Category;
import com.softserve.if072.restservice.service.impl.CategoryServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(value = "/category")
public class CategoryController {

    @Autowired
    private CategoryServiceImpl categoryService;

    @RequestMapping(value = "/all")
    @ResponseBody
    public List<Category> getAll() {
        return categoryService.getAll();
    }

    @RequestMapping(value = "/get/{id}")
    @ResponseBody
    public Category getById(@PathVariable("id") int id) {
        return categoryService.getById(id);
    }

//    @RequestMapping(value = "/add", method = RequestMethod.POST)
//    @ResponseBody
//    public void insert(Category category) {
//        System.out.println("LOL");
//        Category category1 = new Category("Побутові товари", null);
//        categoryService.insert(category1);
//    }
//
//    @RequestMapping(value = "/update")
//    @ResponseBody
//    public void update(@RequestBody Category category) {
//        categoryService.update(category);
//    }

    @RequestMapping(value = "/delete/{id}")
    @ResponseBody
    public void delete(@PathVariable("id") int id) {
        categoryService.delete(id);
    }
}

package com.softserve.if072.restservice.controller;

import com.softserve.if072.common.model.Category;
import com.softserve.if072.restservice.service.impl.CategoryServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(value = "/category")
public class CategoryController {

    @Autowired
    private CategoryServiceImpl categoryService;

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    @ResponseBody
    @ResponseStatus(value = HttpStatus.OK)
    public List<Category> getAll() {
        return categoryService.getAll();
    }

    @RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
    @ResponseBody
    @ResponseStatus(value = HttpStatus.OK)
    public Category getById(@PathVariable("id") int id) {
        return categoryService.getById(id);
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    @ResponseStatus(value = HttpStatus.OK)
    public void insert(@RequestBody Category category) {
        categoryService.insert(category);
    }

    @RequestMapping(value = "/update")
    @ResponseBody
    @ResponseStatus(value = HttpStatus.OK)
    public void update(@RequestBody Category category) {
        categoryService.update(category);
    }

    @RequestMapping(value = "/delete/{id}")
    @ResponseBody
    @ResponseStatus(value = HttpStatus.OK)
    public void delete(@PathVariable("id") int id) {
        categoryService.delete(id);
    }
}

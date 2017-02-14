package com.softserve.if072.restservice.controller;

import com.softserve.if072.common.model.Category;
import com.softserve.if072.restservice.exception.DataNotFoundException;
import com.softserve.if072.restservice.service.CategoryService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
@RequestMapping(value = "/category")
@PropertySource("classpath:message.properties")
public class CategoryController {

    private static final Logger LOGGER = LogManager.getLogger(CategoryController.class);
    private CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping(value = "/{userID}")
    @ResponseBody
    @ResponseStatus(value = HttpStatus.OK)
    public List<Category> getAllCategoriesByUserID(@PathVariable("userID") int userID, HttpServletResponse response) {
        try {
            List<Category> categories = categoryService.getByUserID(userID);
            LOGGER.info("All categories were found");
            return categories;
        } catch (DataNotFoundException e) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            LOGGER.error("Categories were not found", e);
            return null;
        }
    }

    @PostMapping(value = "/")
    @ResponseStatus(value = HttpStatus.CREATED)
    public void insert(@RequestBody Category category)  {
            categoryService.insert(category);
            LOGGER.info("New category %d was created", category.getId());
    }

    @PutMapping(value = "/")
    @ResponseStatus(value = HttpStatus.OK)
    public void update(@RequestBody Category category, HttpServletResponse response) {
        int id = category.getId();

        try {
            categoryService.update(category);
            LOGGER.info(String.format("Category with id %d was updated", id));
        } catch (DataNotFoundException e) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            LOGGER.error(String.format("Cannot update category with id %d", id), e);
        }
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public void delete(@PathVariable("id") int id) {
        categoryService.deleteById(id);
        LOGGER.info(String.format("Category with id %d was deleted", id));
    }
}

package com.softserve.if072.restservice.controller;

import com.softserve.if072.common.model.Category;
import com.softserve.if072.restservice.exception.DataNotFoundException;
import com.softserve.if072.restservice.service.CategoryService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
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

/**
 * Controller that handles requests from MVCapplication to RESTserver
 *
 * @author Pavlo Bendus
 */

@Controller
@RequestMapping(value = "api/category")
public class CategoryController {

    private static final Logger LOGGER = LogManager.getLogger(CategoryController.class);
    private CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PreAuthorize("#userID == authentication.user.id")
    @GetMapping(value = "/{userID}")
    @ResponseBody
    @ResponseStatus(value = HttpStatus.OK)
    public List<Category> getAllCategoriesByUserID(@PathVariable("userID") int userID, HttpServletResponse response) throws DataNotFoundException {

        try {
            List<Category> categories = categoryService.getByUserID(userID);
            LOGGER.info(String.format("All categories of user with id %d were found", userID));
            return categories;
        } catch (DataNotFoundException e) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            LOGGER.error(String.format("Categories of user with id %d were not found", userID));
            return null;
        }
    }

    @PreAuthorize("@categorySecurityService.hasPermissionToAccess(#categoryID)")
    @GetMapping(value = "/id/{categoryID}")
    @ResponseBody
    @ResponseStatus(value = HttpStatus.OK)
    public Category getCategoryByID(@PathVariable("categoryID") int categoryID, HttpServletResponse response) throws DataNotFoundException {

        Category category = categoryService.getById(categoryID);
        LOGGER.info(String.format("Category with id %d was found", categoryID));
        return category;
    }

    @PreAuthorize("#userID == authentication.user.id")
    @GetMapping(value = "/{userID}/name/{name}")
    @ResponseBody
    @ResponseStatus(value = HttpStatus.OK)
    public Category getCategoryByNameAndUserID(@PathVariable("name") String name, @PathVariable("userID") int userID) {
        LOGGER.info("Category name - " + name);
        Category category = categoryService.getByNameAndUserID(name, userID);
        LOGGER.info(String.format("Category with name %s of user with id %d was found", name, userID));
        return category;
    }

    @PreAuthorize("#category != null && #category.user != null && #category.user.id == authentication.user.id")
    @PostMapping(value = "/")
    @ResponseStatus(value = HttpStatus.CREATED)
    public void insert(@RequestBody Category category)  {
            categoryService.insert(category);
            LOGGER.info("New category with id %d was created", category.getId());
    }

    @PreAuthorize("#category != null && #category.user != null && #category.user.id == authentication.user.id")
    @PutMapping(value = "/")
    @ResponseStatus(value = HttpStatus.OK)
    public void update(@RequestBody Category category, HttpServletResponse response) {

        categoryService.update(category);
        LOGGER.info(String.format("Category with id %d was updated", category.getId()));
    }

    @PreAuthorize("@categorySecurityService.hasPermissionToAccess(#id)")
    @DeleteMapping(value = "/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public void delete(@PathVariable("id") int id) {
        categoryService.deleteById(id);
        LOGGER.info(String.format("Category with id %d was deleted", id));
    }
}

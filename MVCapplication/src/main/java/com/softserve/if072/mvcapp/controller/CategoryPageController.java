package com.softserve.if072.mvcapp.controller;

import com.softserve.if072.common.model.Category;
import com.softserve.if072.common.model.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * The controller contains methods that handle http-requests from the category's page
 *
 * @author Pavlo Bendus
 */

@Controller
@RequestMapping("/categories")
@PropertySource("classpath:application.properties")
public class CategoryPageController extends BaseController{

    @Value("${application.restCategoryURL}")
    private String restCategoryURL;

    private static final Logger LOGGER = LogManager.getLogger(CategoryPageController.class);

    /**
     * Method for mapping on default categories url
     * shows the list of available categories and allows to add a new one
     *
     * @param model with data for view
     * @return
     */
    @GetMapping
    public String getPage(ModelMap model) {

        RestTemplate restTemplate = getRestTemplate();
        List<Category> categories = restTemplate.getForObject(restCategoryURL + getCurrentUser().getId(), List.class);
        LOGGER.info(categories);
        model.addAttribute("categories", categories);

        return "categories";
    }

    /**
     * Method for mapping on page for editing
     *
     * @param id for getting the category from rest-service
     * @param model for forming model that will be edited
     * @return
     */

    @GetMapping(value="/edit")
    public String editCategory(@RequestParam int id, ModelMap model) {

        RestTemplate restTemplate = getRestTemplate();
        Category category = restTemplate.getForObject(restCategoryURL + "id/" + id, Category.class);
        LOGGER.info("Category were opened for updating: " + category);
        model.addAttribute("category", category);

        return "editCategory";
    }

    /**
     * Method gets edited category and sends a request to the rest-service to update database
     *
     * @param category edited category
     * @return redirect to the categories list page
     */

    @PostMapping(value = "/edit")
    public String editCategory(@ModelAttribute Category category) {

        RestTemplate restTemplate = getRestTemplate();
        User user = getCurrentUser();
        category.setUser(user);
        restTemplate.put(restCategoryURL, category, Category.class);
        LOGGER.info("Category " + category.getName() + " was updated");
        return "redirect:/categories/";
    }

    /**
     * Method for deleting category from the list
     *
     * @param id id of category that will be deleted
     * @return redirect to the categories list
     */

    @PostMapping(value = "/delete")
    public void deleteCategory(@RequestParam int id) {
        RestTemplate restTemplate = getRestTemplate();
        restTemplate.delete(restCategoryURL + id);
    }
}

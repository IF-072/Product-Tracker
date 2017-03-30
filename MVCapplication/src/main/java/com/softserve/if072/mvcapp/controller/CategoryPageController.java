package com.softserve.if072.mvcapp.controller;

import com.softserve.if072.common.model.Category;
import com.softserve.if072.mvcapp.service.CategoryPageService;
import com.softserve.if072.mvcapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

/**
 * The controller contains methods that handle http-requests from the category's page
 *
 * @author Pavlo Bendus
 */

@Controller
@RequestMapping("/category")
public class CategoryPageController {

    @Value("${application.restCategoryURL}")
    private String restCategoryURL;

    private UserService userService;
    private CategoryPageService categoryPageService;

    @Autowired
    public CategoryPageController(UserService userService, CategoryPageService categoryPageService) {
        this.userService = userService;
        this.categoryPageService = categoryPageService;
    }

    /**
     * Method for mapping on default categories url
     * shows the list of available categories and allows to add a new one
     *
     * @param model with data for view
     * @return .jsp page
     */
    @GetMapping
    public String getPage(ModelMap model) {

        model.addAttribute("categories", categoryPageService.getAllCategories(userService.getCurrentUser().getId()));

        return "category";
    }

    /**
     * Method for mapping on page for adding the category
     *
     * @param model allows to create a new default category object
     * @return .jsp page
     */

    @GetMapping("/add")
    public String addCategory(ModelMap model) {

        model.addAttribute("category", new Category());

        return "addCategory";
    }

    @PostMapping("/add")
    public String addCategory(@Validated @ModelAttribute Category category, BindingResult result, ModelMap model) {

        if (result.hasErrors()) {
            model.addAttribute("errors", result.getAllErrors());
            return "addCategory";
        }

        //checks if the category already exists or is deleted
        int categoryResult = categoryPageService.checkIfExistsElseIsDeleted(category, userService.getCurrentUser());
        if (categoryResult != -1) {
            if (categoryResult == 1) {
                model.addAttribute("error", true);

                return "addCategory";
            } else {
                model.addAttribute("category",
                        categoryPageService.getByNameAndUserID(category.getName(), userService.getCurrentUser().getId()));

                return "deletedCategory";
            }
        }

        categoryPageService.addCategory(category, userService.getCurrentUser());

        return "redirect:/category";
    }

    /**
     * Method for mapping on page for editing
     *
     * @param id for getting the category from rest-service
     * @param model for forming model that will be edited
     * @return .jsp page
     */

    @GetMapping(value="/edit")
    public String editCategory(@RequestParam(value = "id", required = false) int id, ModelMap model) {

        model.addAttribute("category", categoryPageService.getCategory(id));

        return "editCategory";
    }

    /**
     * Method gets edited category and sends a request to the rest-service to update database
     *
     * @param category edited category
     * @return redirect to the categories list page
     */

    @PostMapping(value = "/edit")
    public String editCategory(@Validated @ModelAttribute Category category, BindingResult result, ModelMap model) {

        if (result.hasErrors()) {
            model.addAttribute("errors", result.getAllErrors());
            return "editCategory";
        }

        categoryPageService.editCategory(category, userService.getCurrentUser());

        return "redirect:/category/";
    }

    /**
     * Method for deleting category from the list
     *
     * @param id id of category that will be deleted
     * @return redirect to the categories list
     */

    @GetMapping(value = "/delete")
    public String deleteCategory(@RequestParam int id) {
        categoryPageService.deleteCategory(id);

        return "redirect:/category";
    }

    @PostMapping(value = "/restore")
    public String restoreCategory(@ModelAttribute Category category) {

        categoryPageService.restoreCategory(category);

        return "redirect:/category";
    }
}

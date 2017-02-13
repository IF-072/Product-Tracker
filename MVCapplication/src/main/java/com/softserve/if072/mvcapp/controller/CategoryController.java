package com.softserve.if072.mvcapp.controller;

import com.softserve.if072.common.model.Category;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;

/**
 * The controller contains methods that handle http-requests from the category's page
 *
 * @author Pavlo Bendus
 */

@Controller
@RequestMapping("/categories")
@PropertySource("classpath:application.properties")
public class CategoryController {

    @Value("${application.restCategoryURL}")
    private String restCategoryURL;

    private static final Logger LOGGER = LogManager.getLogger(CategoryController.class);

    /**
     * Method for mapping on default categories url
     * shows the list of available categories and allows to add a new one
     *
     * @param model which we will put into the view
     * @return
     */
    @GetMapping
    public String getPage(ModelMap model) {

        int userID = 2;

        RestTemplate restTemplate = new RestTemplate();
        List<Category> categories = restTemplate.getForObject(restCategoryURL + userID, List.class);
        LOGGER.info(categories);
        model.addAttribute("categories", categories);

        return "categories";
    }
}

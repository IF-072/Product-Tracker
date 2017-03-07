package com.softserve.if072.mvcapp.service;

import com.softserve.if072.common.model.Category;
import com.softserve.if072.common.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * The class contains business logic for methods that handles clients' http requests from CategoryPageController
 *
 * @author Pavlo Bendus
 */

@Service
public class CategoryPageService {

    private RestTemplate restTemplate;

    @Value("${application.restCategoryURL}")
    private String restCategoryURL;

    @Autowired
    public CategoryPageService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<Category> getAllCategories(int userID) {
        return restTemplate.getForObject(restCategoryURL + userID, List.class);
    }

    public Category getCategory(int id) {
        return restTemplate.getForObject(restCategoryURL + "id/" + id, Category.class);
    }

    public void addCategory(Category category, User user) {
        category.setUser(user);
        category.setEnabled(true);

        restTemplate.postForObject(restCategoryURL, category, Category.class);
    }

    public void editCategory(Category category, User user) {
        category.setUser(user);
        category.setEnabled(true);

        restTemplate.put(restCategoryURL, category, Category.class);
    }

    public void restoreCategory(Category category) {
        System.out.println("TRYING TO RESTORE CATEGORY: " + category);
        restTemplate.put(restCategoryURL + "/restore", category, Category.class);
    }

    public void deleteCategory(int id) {
        restTemplate.delete(restCategoryURL + id);
    }

    public boolean alreadyExists(Category category, User user) {

        Category existingCategory = getByNameAndUserID(category.getName(), user.getId());

        return existingCategory != null && existingCategory.isEnabled();
    }

    public boolean isDeleted(Category category, User user) {

        Category deletedCategory = getByNameAndUserID(category.getName(), user.getId());

        return deletedCategory != null && !deletedCategory.isEnabled();
    }

    public Category getByNameAndUserID(String name, int userID) {

        return restTemplate.getForObject(restCategoryURL + userID + "/name/" + name, Category.class);
    }
}

package com.softserve.if072.restservice.service;

import com.softserve.if072.common.model.Category;
import com.softserve.if072.common.model.User;
import com.softserve.if072.restservice.dao.mybatisdao.CategoryDAO;
import com.softserve.if072.restservice.exception.DataNotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;

/**
 * The class was created for testing methods of CategoryService class
 *
 * @author Pavlo Bendus
 */

@RunWith(MockitoJUnitRunner.class)
public class CategoryServiceTest {
    @Mock
    private CategoryDAO categoryDAO;

    @InjectMocks
    private CategoryService categoryService;

    private Category category;
    private int userID;
    private int id;
    private String name;

    @Before
    public void setup() {
        User user = new User();
        user.setId(3);

        userID = 3;
        id = 1;
        name = "Tech";

        category = new Category(name, user, true);

    }

    @Test
    public void getByUserID_shouldReturnListOfCategories() {
        List<Category> categoryList = Arrays.asList(category, category, category);
        when(categoryDAO.getByUserID(userID)).thenReturn(categoryList);

        assertTrue(categoryList.equals(categoryService.getByUserID(userID)));
        verify(categoryDAO).getByUserID(userID);
    }

    @Test(expected = DataNotFoundException.class)
    public void getByUserID_shouldThrowDataNotFoundException() throws DataNotFoundException {
        when(categoryDAO.getByUserID(userID)).thenReturn(null);
        categoryService.getByUserID(userID);
        verify(categoryDAO).getByUserID(userID);
    }

    @Test
    public void getByID_shouldReturnCategory() {
        when(categoryDAO.getByID(id)).thenReturn(category);

        assertTrue(category.equals(categoryService.getByID(id)));
        verify(categoryDAO).getByID(id);
    }

    @Test(expected = Exception.class)
    public void getByID_shouldAnException() throws Exception {
        when(categoryDAO.getByID(id)).thenReturn(null);
        categoryService.getByID(id);
        verify(categoryDAO).getByID(id);
    }

    @Test
    public void getByNameAndUserID_shouldReturnCategory() {
        when(categoryDAO.getByNameAndUserID(name, userID)).thenReturn(category);

        assertTrue(category.equals(categoryService.getByNameAndUserID(name, userID)));
        verify(categoryDAO).getByNameAndUserID(name, userID);
    }

    @Test
    public void insert_shouldInsertCategory() {
        categoryService.insert(category);
        verify(categoryDAO).insert(category);
    }

    @Test
    public void update_shouldUpdateCategory() {
        categoryService.update(category);
        verify(categoryDAO).update(category);
    }

    @Test(expected = IllegalArgumentException.class)
    public void update_shouldThrowIllegalArgumentException() throws IllegalArgumentException {
        category.setName(null);
        categoryService.update(category);
        verify(categoryDAO).update(category);
    }

    @Test
    public void restore_shouldRestoreCategory() {
        category.setId(2);
        categoryService.restore(category);
        verify(categoryDAO).restore(category);
    }

    @Test(expected = IllegalArgumentException.class)
    public void restore_shouldThrowIllegalArgumentException() throws IllegalArgumentException {
        categoryService.restore(category);
        verify(categoryDAO).restore(category);
    }

    @Test
    public void delete_shouldDeleteCategory() {
        categoryService.deleteById(id);
        verify(categoryDAO).deleteById(id);
    }

    @Test(expected = IllegalArgumentException.class)
    public void delete_shouldThrowIllegalArgumentException() throws IllegalArgumentException {
        categoryService.deleteById(0);
        verify(categoryDAO).deleteById(0);
    }
}

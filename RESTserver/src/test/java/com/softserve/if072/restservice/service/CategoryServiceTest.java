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

import javax.xml.crypto.Data;

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
    private User user;
    private int userID;

    @Before
    public void setup() {
        user = new User();
        user.setId(3);

        category = new Category("Tech", user, true);

        userID = 3;
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
}

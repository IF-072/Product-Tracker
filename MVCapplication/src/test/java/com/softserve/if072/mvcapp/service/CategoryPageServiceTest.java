package com.softserve.if072.mvcapp.service;

import com.softserve.if072.common.model.Category;
import com.softserve.if072.common.model.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.web.client.RestTemplate;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class CategoryPageServiceTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private CategoryPageService categoryPageService;

    private Category category;

    @Before
    public void setup() {
        User user = new User();
        user.setId(3);
        category = new Category("Tech", user, true);
    }

    @Test
    public void getAllCategories_shouldReturnListOfCategories() {
        List<Category> categoryList = Arrays.asList(category, category, category);
        when(restTemplate.getForObject(anyString(), eq(List.class))).thenReturn(categoryList);

        assertTrue(categoryList.equals(categoryPageService.getAllCategories(category.getUser().getId())));
        verify(restTemplate).getForObject(anyString(), eq(List.class));
    }

    @Test
    public void getCategory_shouldReturnCategory() {
        when(restTemplate.getForObject(anyString(), eq(Category.class))).thenReturn(category);

        assertTrue(category.equals(categoryPageService.getCategory(category.getUser().getId())));
        verify(restTemplate).getForObject(anyString(), eq(Category.class));
    }

    @Test
    public void addCategory_shouldAddCategory() {
        categoryPageService.addCategory(category, category.getUser());

    }
}

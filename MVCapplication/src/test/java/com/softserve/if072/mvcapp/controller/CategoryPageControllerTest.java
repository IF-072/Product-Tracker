package com.softserve.if072.mvcapp.controller;

import com.softserve.if072.common.model.Category;
import com.softserve.if072.common.model.User;
import com.softserve.if072.mvcapp.service.CategoryPageService;
import com.softserve.if072.mvcapp.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;

@RunWith(MockitoJUnitRunner.class)
public class CategoryPageControllerTest {

    @Mock
    private CategoryPageService categoryPageService;

    @Mock
    private UserService userService;

    @InjectMocks
    private CategoryPageController categoryPageController;

    private MockMvc mockMvc;

    private Category category;
    private User user;

    @Before
    public void setup() throws Exception {
        user = new User();
        user.setId(3);

        category = new Category("Tech", user, true);

        mockMvc = standaloneSetup(categoryPageController)
                .setViewResolvers(new InternalResourceViewResolver("/WEB-INF/views/category/", ".jsp"))
                .build();
    }

    @Test
    public void getPage_shouldReturnCategoryViewName() throws Exception {

        List<Category> categoryList = Arrays.asList(category, category, category);
        when(categoryPageService.getAllCategories(anyInt())).thenReturn(categoryList);
        when(userService.getCurrentUser()).thenReturn(user);

        mockMvc.perform(get("/category/"))
                .andExpect(status().isOk())
                .andExpect(view().name("category"))
                .andExpect(forwardedUrl("/WEB-INF/views/category/category.jsp"))
                .andExpect(model().attributeExists("categories"))
                .andExpect(model().attribute("categories", hasSize(3)));

        verify(categoryPageService).getAllCategories(anyInt());
        verifyNoMoreInteractions(categoryPageService);
    }

    @Test
    public void getPage_shouldReturnCategoryViewName_CategoriesListIsEmpty() throws Exception {

        List<Category> categoryList = Arrays.asList();
        when(categoryPageService.getAllCategories(anyInt())).thenReturn(categoryList);
        when(userService.getCurrentUser()).thenReturn(user);

        mockMvc.perform(get("/category/"))
                .andExpect(status().isOk())
                .andExpect(view().name("category"))
                .andExpect(forwardedUrl("/WEB-INF/views/category/category.jsp"))
                .andExpect(model().attributeExists("categories"))
                .andExpect(model().attribute("categories", hasSize(0)));

        verify(categoryPageService).getAllCategories(anyInt());
        verifyNoMoreInteractions(categoryPageService);
    }

    @Test
    public void addCategory_shouldReturnAddCategoryViewName() throws Exception {
        mockMvc.perform(get("/category/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("addCategory"))
                .andExpect(forwardedUrl("/WEB-INF/views/category/addCategory.jsp"))
                .andExpect(model().attributeExists("category"));
    }
}
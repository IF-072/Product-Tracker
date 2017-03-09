package com.softserve.if072.mvcapp.controller;

import com.softserve.if072.common.model.Category;
import com.softserve.if072.common.model.User;
import com.softserve.if072.mvcapp.service.CategoryPageService;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.servlet.view.InternalResourceView;

import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;


@RunWith(MockitoJUnitRunner.class)
public class CategoryPageControllerTest {

    @Mock
    private CategoryPageService categoryPageService;

    @InjectMocks
    private CategoryPageController categoryPageController;

    private MockMvc mockMvc;

    private Category category;

    @Before
    public void setup() {
        User user = new User();
        user.setId(3);

        category = new Category("Tech", user, true);

        mockMvc = standaloneSetup(categoryPageController)
                .setSingleView(new InternalResourceView("/WEB-INF/views/category.jsp"))
                .build();
    }


}

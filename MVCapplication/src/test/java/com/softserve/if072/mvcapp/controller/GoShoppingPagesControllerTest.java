package com.softserve.if072.mvcapp.controller;

import com.softserve.if072.common.model.ShoppingList;
import com.softserve.if072.common.model.Store;
import com.softserve.if072.common.model.User;
import com.softserve.if072.mvcapp.service.GoShoppingPageService;
import com.softserve.if072.mvcapp.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

/**
 * The GoShoppingPagesControllerTest class is used to test GoShoppingPagesController class methods
 *
 * @author Roman Dyndyn
 */
@RunWith(MockitoJUnitRunner.class)
public class GoShoppingPagesControllerTest {
    @Mock
    private GoShoppingPageService goShoppingPageService;
    @Mock
    private UserService userService;
    @Mock
    private User user;
    @Mock
    private Store store;
    @InjectMocks
    private GoShoppingPagesController goShoppingPagesController;
    private MockMvc mockMvc;

    @Before
    public void setup() throws ClassNotFoundException, NoSuchMethodException {
        mockMvc = standaloneSetup(goShoppingPagesController)
                .setViewResolvers(viewResolver())
                .build();
    }

    @Test
    public void testGetPageWithStores_ShouldReturnViewName() throws Exception {
        List<Store> storages = Arrays.asList(store, store);
        when(goShoppingPageService.getStores(anyInt())).thenReturn(storages);
        when(userService.getCurrentUser()).thenReturn(user);
        mockMvc.perform(get("/goShoppingStores"))
                .andExpect(status().isOk())
                .andExpect(view().name("goShoppingStores"))
                .andExpect(model().attributeExists("stores"))
                .andExpect(model().attribute("stores", hasSize(2)));
        verify(goShoppingPageService, times(1)).getStores(anyInt());
    }

    @Test
    public void testGetProductList_ShouldReturnViewName() throws Exception {
        Map<String, List<ShoppingList>> map = new HashMap<>();
        map.put("selected", new ArrayList<>());
        map.put("remained", new ArrayList<>());
        when(userService.getCurrentUser()).thenReturn(user);
        when(goShoppingPageService.getProducts(anyInt(), eq(1))).thenReturn(map);
        mockMvc.perform(post("/goShoppingProducts")
                .param("stores", "1"))
                .andExpect(status().isOk())
                .andExpect(view().name("goShoppingProducts"))
                .andExpect(model().attributeExists("selected"))
                .andExpect(model().attributeExists("remained"))
                .andExpect(model().attributeExists("cartForm"));
        verify(goShoppingPageService, times(1)).getProducts(anyInt(), eq(1));
    }

    @Test
    public void testGetProductList() throws Exception {
        when(userService.getCurrentUser()).thenReturn(user);
        when(goShoppingPageService.getProducts(anyInt(), eq(1))).thenReturn(null);
        mockMvc.perform(post("/goShoppingProducts")
                .param("stores", "1"))
                .andExpect(status().isOk())
                .andExpect(view().name("goShoppingProducts"))
                .andExpect(model().attributeDoesNotExist("selected"))
                .andExpect(model().attributeDoesNotExist("remained"))
                .andExpect(model().attributeDoesNotExist("cartForm"));
        verify(goShoppingPageService, times(1)).getProducts(anyInt(), eq(1));
    }

    @Test
    public void updateAmount_ShouldReturnNonEmptyString() throws Exception {
        when(userService.getCurrentUser()).thenReturn(user);
        mockMvc.perform(post("/addToCart"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/cart/"));
        verify(goShoppingPageService, times(1)).addToCart(any());
    }

    private ViewResolver viewResolver() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();

        viewResolver.setViewClass(JstlView.class);
        viewResolver.setPrefix("/WEB-INF/jsp/");
        viewResolver.setSuffix(".jsp");

        return viewResolver;
    }
}

package com.softserve.if072.mvcapp.controller;

import com.softserve.if072.common.model.ShoppingList;
import com.softserve.if072.common.model.Store;
import com.softserve.if072.common.model.User;
import com.softserve.if072.mvcapp.service.GoShoppingPageService;
import com.softserve.if072.mvcapp.service.MessageService;
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

import javax.servlet.http.Cookie;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

/**
 * The GoShoppingPagesControllerTest class is used to
 * test GoShoppingPagesController class methods.
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
    private MessageService messageService;
    @InjectMocks
    private GoShoppingPagesController goShoppingPagesController;
    private MockMvc mockMvc;
    private User user;
    private Store store;

    @Before
    public void setup() throws ClassNotFoundException, NoSuchMethodException {
        user = new User();
        user.setId(1);
        store = new Store();
        store.setUser(user);
        mockMvc = standaloneSetup(goShoppingPagesController)
                .setViewResolvers(viewResolver())
                .build();
    }

    @Test
    public void getPageWithStores_ShouldReturnViewName() throws Exception {
        final String attribute = "stores";
        final List<Store> storages = Arrays.asList(store, store);
        when(goShoppingPageService.getStores(anyInt())).thenReturn(storages);
        when(userService.getCurrentUser()).thenReturn(user);
        mockMvc.perform(get("/goShoppingStores"))
                .andExpect(status().isOk())
                .andExpect(forwardedUrl("/WEB-INF/views/goShoppingStores.jsp"))
                .andExpect(view().name("goShoppingStores"))
                .andExpect(model().attributeExists(attribute))
                .andExpect(model().attribute(attribute, hasSize(2)));
        verify(goShoppingPageService).getStores(user.getId());
    }

    @Test
    public void getProductList_ShouldReturnViewName() throws Exception {
        final int storeId = 1;
        final Map<String, List<ShoppingList>> map = new HashMap<>();
        map.put("selected", new ArrayList<>());
        map.put("remained", new ArrayList<>());
        when(userService.getCurrentUser()).thenReturn(user);
        when(goShoppingPageService.getProducts(user.getId(), storeId)).thenReturn(map);
        mockMvc.perform(post("/goShoppingProducts")
                .param("stores", Integer.toString(storeId)))
                .andExpect(status().isOk())
                .andExpect(forwardedUrl("/WEB-INF/views/goShoppingProducts.jsp"))
                .andExpect(view().name("goShoppingProducts"))
                .andExpect(model().attributeExists("selected"))
                .andExpect(model().attributeExists("remained"))
                .andExpect(model().attributeExists("cartForm"));
        verify(goShoppingPageService).getProducts(user.getId(), storeId);
    }

    @Test
    public void getProductList() throws Exception {
        final int storeId = 1;
        when(userService.getCurrentUser()).thenReturn(user);
        when(goShoppingPageService.getProducts(anyInt(), eq(storeId))).thenReturn(null);
        mockMvc.perform(post("/goShoppingProducts")
                .param("stores", Integer.toString(storeId)))
                .andExpect(status().isOk())
                .andExpect(view().name("goShoppingProducts"))
                .andExpect(model().attributeDoesNotExist("selected"))
                .andExpect(model().attributeDoesNotExist("remained"))
                .andExpect(model().attributeDoesNotExist("cartForm"));
        verify(goShoppingPageService).getProducts(anyInt(), eq(storeId));
    }

    @Test
    public void updateAmount_ShouldReturnNonEmptyString() throws Exception {
        when(userService.getCurrentUser()).thenReturn(user);
        mockMvc.perform(post("/addToCart")
                .param("carts[0].user.id", "1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/cart/"));
        verify(goShoppingPageService).addToCart(any(), any());
    }

    @Test
    public void testReview() throws Exception {
        final String locale = "uk";
        final String cookie = "myLocaleCookie";
        when(userService.getCurrentUser()).thenReturn(user);
        mockMvc.perform(post("/shopping/finished")
                .header("referer", "http://localhost:8080/cart")
                .cookie(new Cookie(cookie, locale)))
                .andExpect(status().isOk());
        verify(goShoppingPageService).reviewCart(eq(locale), anyInt());
    }

    private ViewResolver viewResolver() {
        final InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();

        viewResolver.setViewClass(JstlView.class);
        viewResolver.setPrefix("/WEB-INF/views/");
        viewResolver.setSuffix(".jsp");

        return viewResolver;
    }
}

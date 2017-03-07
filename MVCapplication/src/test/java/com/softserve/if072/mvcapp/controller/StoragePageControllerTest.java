package com.softserve.if072.mvcapp.controller;

import com.softserve.if072.common.model.Storage;
import com.softserve.if072.common.model.User;
import com.softserve.if072.mvcapp.service.ShoppingListService;
import com.softserve.if072.mvcapp.service.StoragePageService;
import com.softserve.if072.mvcapp.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.servlet.view.InternalResourceView;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

/**
 * The StoragePageControllerTest class is used to test StoragePageController class methods
 *
 * @author Roman Dyndyn
 */
@RunWith(MockitoJUnitRunner.class)
public class StoragePageControllerTest {
    @Mock
    private StoragePageService storagePageService;
    @Mock
    private ShoppingListService shoppingListService;
    @Mock
    private UserService userService;
    @Mock
    private Storage storage;
    @Mock
    private User user;
    @InjectMocks
    private StoragePageController storagePageController;
    private MockMvc mockMvc;

    @Before
    public void setup() throws ClassNotFoundException, NoSuchMethodException {
        mockMvc = standaloneSetup(storagePageController)
                .setSingleView(new InternalResourceView("/WEB-INF/views/storage.jsp"))
                .build();
    }

    @Test
    public void testGetPage_ShouldReturnStorageViewName() throws Exception {
        List<Storage> storages = Arrays.asList(storage, storage, storage);
        when(storagePageService.getStorages(anyInt())).thenReturn(storages);
        when(userService.getCurrentUser()).thenReturn(user);
        when(user.getId()).thenReturn(1);
        mockMvc.perform(get("/storage"))
                .andExpect(status().isOk())
                .andExpect(forwardedUrl("/WEB-INF/views/storage.jsp"))
                .andExpect(view().name("storage"))
                .andExpect(model().attributeExists("list"))
                .andExpect(model().attribute("list", hasSize(3)))
                .andExpect(model().attributeExists("storage"));
        verify(storagePageService, times(1)).getStorages(anyInt());
    }

    @Test
    public void testUpdateAmount_ShouldReturnEmptyString() throws Exception {
        when(userService.getCurrentUser()).thenReturn(user);
        when(user.getId()).thenReturn(1);
        mockMvc.perform(post("/storage/update")
                .param("amount", "1")
                .param("productId", "1"))
                .andExpect(status().isOk());
        verify(storagePageService, times(1)).updateAmount(any(), any());
    }

    @Test
    public void testAddToShoppingList() throws Exception {
        when(userService.getCurrentUser()).thenReturn(user);
        mockMvc.perform(post("/storage/addToSL")
                .param("productId", "1"))
                .andExpect(status().isOk());
        verify(shoppingListService, times(1)).addProductToShoppingList(user, 1);
    }
}

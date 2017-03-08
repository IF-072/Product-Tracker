package com.softserve.if072.mvcapp.controller;

import com.softserve.if072.common.model.Store;
import com.softserve.if072.common.model.User;
import com.softserve.if072.mvcapp.service.StorePageService;
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

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

/**
 * Created by Nazar Vynnyk
 */
@RunWith(MockitoJUnitRunner.class)
public class StorePageControllerTest {

    @Mock
    private StorePageService storePageService;
    @Mock
    private UserService userService;
    @Mock
    private Store store;
    @Mock
    private User user;
    @InjectMocks
    StorePageController storePageController;
    MockMvc mockMvc;

    @Before
    public void setUp() {
        mockMvc = standaloneSetup(storePageController)
                .setViewResolvers(new InternalResourceViewResolver("/WEB-INF/views/store/", ".jsp"))
                .build();
    }

    @Test
    public void test_GetAllStoresByUserId_ShouldReturnViewName() throws Exception {
        List<Store> stores = Arrays.asList(store, store);
        when(storePageService.getAllStoresByUserId(anyInt())).thenReturn(stores);
        when(userService.getCurrentUser()).thenReturn(user);
        mockMvc.perform(get("/stores/"))
                .andExpect(status().isOk())
                .andExpect(view().name("allStores"))
                .andExpect(model().attributeExists("stores"))
                .andExpect(model().attribute("stores", hasSize(2)));
        verify(storePageService, times(1)).getAllStoresByUserId(anyInt());
        verifyNoMoreInteractions(storePageService);
    }


    @Test
    public void test_AddStore_ShouldReturnViewName() throws Exception {
        when(storePageService.addNewStore()).thenReturn(store);
        when(userService.getCurrentUser()).thenReturn(user);
        mockMvc.perform(get("/addStore"))
                .andExpect(status().isOk())
                .andExpect(view().name("addStore"))
                .andExpect(model().attributeExists("store"));
        verify(storePageService, times(1)).addNewStore();
        verifyNoMoreInteractions(storePageService);
    }

    /**
     *  @PostMapping(value = "/addStore")
    public String addStore(@Validated @ModelAttribute("store") Store store, BindingResult result,
    Model model) {
    if (result.hasErrors()) {
    model.addAttribute("errorMessages", result.getFieldErrors());
    return "addStore";
    }

    if (storePageService.alreadyExist(store, userService.getCurrentUser())) {
    model.addAttribute("validMessage", existMessage);
    return "addStore";
    }
    if (storePageService.isDeleted(store, userService.getCurrentUser())) {
    model.addAttribute("store", storePageService.getStoreByNameAndUserId(store, userService.getCurrentUser()));
    return "dialogWindow";
    }
    storePageService.addStore(userService.getCurrentUser(), store);
    LOGGER.info(String.format("Store of user %d was added", userService.getCurrentUser().getId()));

    return "redirect:/stores/";
    }
     */

    @Test
    public void test_AddStore_Post_ShouldReturnViewName() throws Exception {

    }


}

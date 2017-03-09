package com.softserve.if072.mvcapp.controller;

import com.softserve.if072.common.model.Product;
import com.softserve.if072.common.model.Store;
import com.softserve.if072.common.model.User;
import com.softserve.if072.mvcapp.dto.ProductsWrapper;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
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
    private Store store;
    private User user;
    private Product product;
    @InjectMocks
    private StorePageController storePageController;

    private MockMvc mockMvc;
    @Mock
    ProductsWrapper wrapedProducts;

    @Before
    public void setUp() {
        mockMvc = standaloneSetup(storePageController)
                .setViewResolvers(new InternalResourceViewResolver("/WEB-INF/views/store/", ".jsp"))
                .build();
        store = new Store();
        user = new User();
        product = new Product();
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
    public void test_GetAllStoresByUserId_StoreListEmpty_ShouldReturnViewName() throws Exception {
        List<Store> stores = new ArrayList<>();
        when(storePageService.getAllStoresByUserId(anyInt())).thenReturn(stores);
        when(userService.getCurrentUser()).thenReturn(user);
        mockMvc.perform(get("/stores/"))
                .andExpect(status().isOk())
                .andExpect(view().name("allStores"))
                .andExpect(model().attributeExists("stores"))
                .andExpect(model().attribute("stores", hasSize(0)));
        verify(storePageService, times(1)).getAllStoresByUserId(anyInt());
        verifyNoMoreInteractions(storePageService);
    }

    @Test
    public void test_GetAllStoresByUserId_StoreListNull_ShouldReturnView() throws Exception {
        when(storePageService.getAllStoresByUserId(anyInt())).thenReturn(null);
        when(userService.getCurrentUser()).thenReturn(user);
        mockMvc.perform(get("/stores/"))
                .andExpect(status().isOk())
                .andExpect(view().name("allStores"));
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

    @Test
    public void test_AddStore_Post_ShouldReturnViewName() throws Exception {

        when(userService.getCurrentUser()).thenReturn(user);
        String name = createStringWithLength(9);
        String address = createStringWithLength(34);
        store = createStore(0, name, address, true);
        mockMvc.perform(post("/addStore")
                .param("name", name)
                .param("address", address))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/stores/"));
        verify(storePageService, times(1)).addStore(userService.getCurrentUser(), store);
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_AddStore_Post_ParamsNull_ShouldThrowIllegalArgumentException() throws Exception {
        mockMvc.perform(post("/addStore")
                .param("name", null)
                .param("address", null))
                .andExpect(status().is4xxClientError());
        verify(storePageService, times(0)).addStore(userService.getCurrentUser(), store);
    }

    @Test
    public void test_AddStore_Post_ShouldReturnValidationMessages() throws Exception {
        mockMvc.perform(post("/addStore")
                .param("name", "")
                .param("address", ""))
                .andExpect(status().isOk())
                .andExpect(model()
                        .attributeHasFieldErrors("store", "name", "address"))
                .andExpect(view().name("addStore"));
        verify(storePageService, times(0)).addStore(userService.getCurrentUser(), store);
    }

    @Test
    public void test_AddStore_Post_ShouldReturnValidationAddressMessage() throws Exception {
        String name = createStringWithLength(9);
        String address = createStringWithLength(285);
        mockMvc.perform(post("/addStore")
                .param("name", name)
                .param("address", address))
                .andExpect(status().isOk())
                .andExpect(model()
                        .attributeHasFieldErrors("store", "address"))
                .andExpect(view().name("addStore"));
        verify(storePageService, times(0)).addStore(userService.getCurrentUser(), store);
    }

    @Test
    public void test_AddStore_Post_ShouldReturnNameMessage() throws Exception {
        String name = createStringWithLength(2);
        String address = createStringWithLength(34);
        mockMvc.perform(post("/addStore")
                .param("name", name)
                .param("address", address))
                .andExpect(status().isOk())
                .andExpect(model()
                        .attributeHasFieldErrors("store", "name"))
                .andExpect(view().name("addStore"));
        verify(storePageService, times(0)).addStore(userService.getCurrentUser(), store);
        verifyNoMoreInteractions(storePageService);
    }

    @Test
    public void test_AddStore_Post_ShouldReturnTwoValidationMessages() throws Exception {
        String name = createStringWithLength(2);
        String address = createStringWithLength(4);
        mockMvc.perform(post("/addStore")
                .param("name", name)
                .param("address", address))
                .andExpect(status().isOk())
                .andExpect(model()
                        .attributeHasFieldErrors("store", "name", "address"))
                .andExpect(view().name("addStore"));
        verify(storePageService, times(0)).addStore(userService.getCurrentUser(), store);
        verifyNoMoreInteractions(storePageService);
    }

    @Test
    public void test_AddStore_Post_ShouldRedirectAlreadyExist() throws Exception {
        when(userService.getCurrentUser()).thenReturn(user);
        String name = createStringWithLength(9);
        String address = createStringWithLength(34);
        when(storePageService.alreadyExist(any(), any())).thenReturn(true);
        mockMvc.perform(post("/addStore")
                .param("name", name)
                .param("address", address)
                .content("validMessage"))
                .andExpect(model()
                        .attributeExists("store"))
                .andExpect(status().isOk())
                .andExpect(view().name("addStore"));
        verify(storePageService, times(0)).addStore(userService.getCurrentUser(), store);
    }

    @Test
    public void test_AddStore_Post_ShouldRedirectStoreIsDeleted() throws Exception {
        when(userService.getCurrentUser()).thenReturn(user);
        String name = createStringWithLength(9);
        String address = createStringWithLength(34);
        store = createStore(0, name, address, false);
        when(storePageService.isDeleted(any(), any())).thenReturn(true);
        mockMvc.perform(post("/addStore")
                .param("name", name)
                .param("address", address))
                .andExpect(status().isOk())
                .andExpect(view().name("dialogWindow"));
        verify(storePageService, times(0)).addStore(userService.getCurrentUser(), store);
        verify(storePageService, times(1)).getStoreByNameAndUserId(store, userService.getCurrentUser());
    }

    @Test
    public void test_GetAllProductsByStoreId_ShouldReturnViewName() throws Exception {
        List<Product> products = Arrays.asList(product, product);
        int storeId = 2;
        when(storePageService.getStoreById(storeId)).thenReturn(store);
        when(userService.getCurrentUser()).thenReturn(user);
        when(storePageService.getAllProductsFromStore(storeId, user.getId())).thenReturn(products);
        mockMvc.perform(get("/stores/storeProducts")
                .param("storeId", Integer.toString(storeId)))
                .andExpect(status().isOk())
                .andExpect(view().name("productsInStore"))
                .andExpect(model().attributeExists("store"))
                .andExpect(model().attributeExists("products"))
                .andExpect(model().attribute("products", hasSize(2)));
        verify(storePageService, times(1)).getAllProductsFromStore(storeId, user.getId());
        verify(storePageService, times(1)).getStoreById(storeId);

        verifyNoMoreInteractions(storePageService);
    }

    @Test
    public void test_AddProductsToStore_ShouldReturnViewName() throws Exception {
        List<Product> products = Arrays.asList(product, product);
        int storeId = 2;
        when(storePageService.getStoreById(storeId)).thenReturn(store);
        when(userService.getCurrentUser()).thenReturn(user);
        when(storePageService.getNotMappedProducts(storeId, user.getId())).thenReturn(products);
        mockMvc.perform(get("/addProductsToStore")
                .param("storeId", Integer.toString(storeId)))
                .andExpect(status().isOk())
                .andExpect(view().name("addProductsToStore"))
                .andExpect(model().attributeExists("myStore"))
                .andExpect(model().attributeExists("products"))
                .andExpect(model().attribute("products", hasSize(2)))
                .andExpect(model().attributeExists("wrapedProducts"));
        verify(storePageService, times(1)).getNotMappedProducts(storeId, user.getId());
        verify(storePageService, times(1)).getStoreById(storeId);

        verifyNoMoreInteractions(storePageService);
    }

//    @PostMapping("/addProductsToStore")
//    public String addProductsToStore(@RequestParam("storeId") int storeId, @ModelAttribute("wrapedProducts")
//            ProductsWrapper wrapedProducts) {
//        int userId = userService.getCurrentUser().getId();
//        if (wrapedProducts.getProducts().isEmpty()) {
//            LOGGER.info(String.format("No products have been chosen in store %d ", storeId));
//            return "redirect:/stores/storeProducts?storeId=" + storeId;
//        }
//        storePageService.addProductsToStore(userId, storeId, wrapedProducts);
//        LOGGER.info(String.format("Products of user %d added in store %d ", userId, storeId));
//
//        return "redirect:/stores/storeProducts?storeId=" + storeId;
//    }

    @Test
    public void test_AddProductsToStore_Post_ShouldRedirectToStoreProducts() throws Exception {
        when(userService.getCurrentUser()).thenReturn(user);
        when(wrapedProducts.getProducts()).thenReturn(Arrays.asList(2, 4, 6));
        int storeId = 2;
        mockMvc.perform(post("/addProductsToStore")
                .param("storeId", Integer.toString(storeId)))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/stores/storeProducts?storeId=" + storeId));
        storePageService.addProductsToStore(anyInt(), anyInt(), any());
        verify(storePageService, times(1)).addProductsToStore(anyInt(), anyInt(), any());
    }


    public static Store createStore(int id, String name, String address, boolean isEnabled) {
        Store store = new Store();
        store.setId(id);
        store.setName(name);
        store.setAddress(address);
        store.setEnabled(isEnabled);
        return store;
    }

    public static String createStringWithLength(int length) {
        StringBuilder builder = new StringBuilder();

        for (int index = 0; index < length; index++) {
            builder.append("a");
        }

        return builder.toString();
    }
}


package com.softserve.if072.restservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.softserve.if072.common.model.Product;
import com.softserve.if072.common.model.ShoppingList;
import com.softserve.if072.common.model.Store;
import com.softserve.if072.common.model.User;
import com.softserve.if072.restservice.exception.DataNotFoundException;
import com.softserve.if072.restservice.service.GoShoppingService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.Matchers.hasKey;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

/**
 * The GoShoppingControllerTest class is used to test
 * GoShoppingController class methods
 *
 * @author Roman Dyndyn
 */
@RunWith(MockitoJUnitRunner.class)
public class GoShoppingControllerTest {
    private static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType()
            , MediaType.APPLICATION_JSON.getSubtype()
            , Charset.forName("utf8"));
    @Mock
    private GoShoppingService goShoppingService;
    @InjectMocks
    private GoShoppingController goShoppingController;
    private MockMvc mockMvc;
    private User user;
    private Product product1;
    private Product product2;
    private Product product3;

    @Before
    public void setup() {
        mockMvc = standaloneSetup(goShoppingController)
                .build();
        user = new User();
        user.setName("Roman");
        user.setId(2);
        product1 = new Product();
        product1.setId(1);
        product1.setName("Product1");
        product2 = new Product();
        product2.setId(2);
        product2.setName("Product2");
        product3 = new Product();
        product3.setId(3);
        product3.setName("Product3");
    }

    @Test
    public void getStores_ShouldReturnAllUsersStores() throws Exception {
        final Store store1 = new Store();
        store1.setId(1);
        store1.setUser(user);
        store1.setProducts(Arrays.asList(product1, product2));
        final Store store2 = new Store();
        store2.setId(2);
        store2.setUser(user);
        store2.setProducts(Arrays.asList(product1, product3));
        when(goShoppingService.getStoreByUserId(user.getId())).thenReturn(Arrays.asList(store1, store2));
        mockMvc.perform(get("/api/goShopping/stores/{userId}", user.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0]['user']['name']", is(user.getName())))
                .andExpect(jsonPath("$[0]['user']['id']", is(user.getId())))
                .andExpect(jsonPath("$[0]['products']", hasSize(2)))
                .andExpect(jsonPath("$[0]['products'][0]['name']", is(store1.getProducts().get(0).getName())))
                .andExpect(jsonPath("$[0]['products'][0]['id']", is(store1.getProducts().get(0).getId())))
                .andExpect(jsonPath("$[0]['products'][1]['name']", is(store1.getProducts().get(1).getName())))
                .andExpect(jsonPath("$[0]['products'][1]['id']", is(store1.getProducts().get(1).getId())))
                .andExpect(jsonPath("$[1]['user']['name']", is(user.getName())))
                .andExpect(jsonPath("$[1]['user']['id']", is(user.getId())))
                .andExpect(jsonPath("$[1]['products']", hasSize(2)))
                .andExpect(jsonPath("$[1]['products'][0]['name']", is(store2.getProducts().get(0).getName())))
                .andExpect(jsonPath("$[1]['products'][0]['id']", is(store2.getProducts().get(0).getId())))
                .andExpect(jsonPath("$[1]['products'][1]['name']", is(store2.getProducts().get(1).getName())))
                .andExpect(jsonPath("$[1]['products'][1]['id']", is(store2.getProducts().get(1).getId())));
        verify(goShoppingService).getStoreByUserId(user.getId());
        verifyZeroInteractions(goShoppingService);
    }

    @Test
    public void getStores_ShouldReturnEmptyList() throws Exception {
        when(goShoppingService.getStoreByUserId(user.getId())).thenThrow(new DataNotFoundException());
        mockMvc.perform(get("/api/goShopping/stores/{userId}", user.getId()))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$", hasSize(0)));
        verify(goShoppingService).getStoreByUserId(user.getId());
        verifyZeroInteractions(goShoppingService);
    }

    @Test
    public void getProducts_ShouldReturnAllStoresProducts() throws Exception {
        final int storeId = 2;
        final ShoppingList shoppingList1 = new ShoppingList(user, product1, 1);
        final ShoppingList shoppingList2 = new ShoppingList(user, product2, 2);
        final ShoppingList shoppingList3 = new ShoppingList(user, product3, 3);
        final Map<String, List<ShoppingList>> map = new HashMap<>();
        map.put("selected", Arrays.asList(shoppingList1, shoppingList2));
        map.put("remained", Arrays.asList(shoppingList3));
        when(goShoppingService.getProducts(user.getId(), storeId)).thenReturn(map);
        mockMvc.perform(get("/api/goShopping/{storeId}/products/{userId}", storeId, user.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", hasKey("selected")))
                .andExpect(jsonPath("$", hasKey("remained")))
                .andExpect(jsonPath("$['selected']", hasSize(2)))
                .andExpect(jsonPath("$['selected'][0]['user']['name']", is(user.getName())))
                .andExpect(jsonPath("$['selected'][0]['user']['id']", is(user.getId())))
                .andExpect(jsonPath("$['selected'][0]['product']['name']",
                        is(shoppingList1.getProduct().getName())))
                .andExpect(jsonPath("$['selected'][0]['product']['id']",
                        is(shoppingList1.getProduct().getId())))
                .andExpect(jsonPath("$['selected'][0]['amount']", is(shoppingList1.getAmount())))
                .andExpect(jsonPath("$['selected'][1]['user']['name']", is(user.getName())))
                .andExpect(jsonPath("$['selected'][1]['user']['id']", is(user.getId())))
                .andExpect(jsonPath("$['selected'][1]['product']['name']",
                        is(shoppingList2.getProduct().getName())))
                .andExpect(jsonPath("$['selected'][1]['product']['id']",
                        is(shoppingList2.getProduct().getId())))
                .andExpect(jsonPath("$['selected'][1]['amount']", is(shoppingList2.getAmount())))
                .andExpect(jsonPath("$['remained']", hasSize(1)))
                .andExpect(jsonPath("$['remained'][0]['user']['name']", is(user.getName())))
                .andExpect(jsonPath("$['remained'][0]['user']['id']", is(user.getId())))
                .andExpect(jsonPath("$['remained'][0]['product']['name']",
                        is(shoppingList3.getProduct().getName())))
                .andExpect(jsonPath("$['remained'][0]['product']['id']",
                        is(shoppingList3.getProduct().getId())))
                .andExpect(jsonPath("$['remained'][0]['amount']", is(shoppingList3.getAmount())));
        verify(goShoppingService).getProducts(user.getId(), storeId);
        verifyZeroInteractions(goShoppingService);
    }

    @Test
    public void getProducts_ShouldReturnEmptyMap() throws Exception {
        final int storeId = 1;
        when(goShoppingService.getProducts(user.getId(), storeId)).thenThrow(new DataNotFoundException());
        final MvcResult result = mockMvc.perform(
                get("/api/goShopping/{storeId}/products/{userId}", storeId, user.getId()))
                .andExpect(status().isNotFound())
                .andReturn();
        assertEquals("{}", result.getResponse().getContentAsString());
        verify(goShoppingService).getProducts(user.getId(), storeId);
        verifyZeroInteractions(goShoppingService);
    }

    @Test
    public void insert_ShouldReturnStatusCreated() throws Exception {
        final ObjectMapper mapper = new ObjectMapper();
        mockMvc.perform(
                post("/api/goShopping/cart")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(new ArrayList<>())))
                .andExpect(status().isOk());
        verify(goShoppingService).insertCart(any());
        verifyZeroInteractions(goShoppingService);
    }
}

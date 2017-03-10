package com.softserve.if072.restservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.softserve.if072.common.model.Cart;
import com.softserve.if072.common.model.dto.CartDTO;
import com.softserve.if072.restservice.service.CartService;
import com.softserve.if072.restservice.test.utils.CartBuilder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

/**
 * The CartControllerTest class is used to test CartController class methods
 *
 * @author Igor Kryviuk
 */
@RunWith(MockitoJUnitRunner.class)
public class CartControllerTest {
    private static final int FIRST_CART_ITEM_ID = 1;
    private static final int SECOND_CART_ITEM_ID = 2;
    private static final int FIRST_CART_ITEM_AMOUNT = 5;
    private static final int SECOND_CART_ITEM_AMOUNT = 3;
    private static final int CURRENT_USER_ID = 4;
    private static final int PRODUCT_ID = 32;
    public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType()
            , MediaType.APPLICATION_JSON.getSubtype()
            , Charset.forName("utf8"));
    @Mock
    private CartService cartService;
    private CartController cartController;
    private MockMvc mockMvc;
    private ObjectMapper mapper;
    private ObjectWriter objectWriter;

    @Before
    public void setup() throws ClassNotFoundException, NoSuchMethodException {
        cartController = new CartController(cartService);
        mockMvc = standaloneSetup(cartController)
                .build();
        mapper = new ObjectMapper();
        objectWriter = mapper.writer().withDefaultPrettyPrinter();
    }

    @Test
    public void getByUserId_UserIdGiven_ShouldReturnNotEmptyUsersCart() throws Exception {
        Cart cart1 = CartBuilder.getDefaultCart(FIRST_CART_ITEM_ID, CURRENT_USER_ID, FIRST_CART_ITEM_AMOUNT);
        Cart cart2 = CartBuilder.getDefaultCart(SECOND_CART_ITEM_ID, CURRENT_USER_ID, SECOND_CART_ITEM_AMOUNT);
        List<Cart> carts = Arrays.asList(cart1, cart2);
        when(cartService.getByUserId(anyInt())).thenReturn(carts);
        mockMvc.perform(get("/api/users/{userId}/carts", CURRENT_USER_ID))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0]['user']['name']", is(String.format("user%d", CURRENT_USER_ID))))
                .andExpect(jsonPath("$[0]['store']['name']", is(String.format("store%d", FIRST_CART_ITEM_ID))))
                .andExpect(jsonPath("$[0]['product']['name']", is(String.format("product%d", FIRST_CART_ITEM_ID))))
                .andExpect(jsonPath("$[0]['amount']", is(FIRST_CART_ITEM_AMOUNT)))
                .andExpect(jsonPath("$[1]['user']['name']", is(String.format("user%d", CURRENT_USER_ID))))
                .andExpect(jsonPath("$[1]['store']['name']", is(String.format("store%d", SECOND_CART_ITEM_ID))))
                .andExpect(jsonPath("$[1]['product']['name']", is(String.format("product%d", SECOND_CART_ITEM_ID))))
                .andExpect(jsonPath("$[1]['amount']", is(SECOND_CART_ITEM_AMOUNT)));
        verify(cartService).getByUserId(anyInt());
        verifyZeroInteractions(cartService);
    }

    @Test
    public void getByUserId_UserIdGiven_ShouldReturnEmptyUsersCart() throws Exception {
        when(cartService.getByUserId(anyInt())).thenReturn(Collections.emptyList());
        mockMvc.perform(get("/api/users/{userId}/carts", CURRENT_USER_ID))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", hasSize(0)));
        verify(cartService).getByUserId(anyInt());
        verifyZeroInteractions(cartService);
    }

    @Test
    public void productPurchase_CartDTOGiven_ShouldExecuteCartServiceProductPurchaseExactlyOnce() throws Exception {
        String requestCartDTO = objectWriter.writeValueAsString(new CartDTO());
        mockMvc.perform(put("/api/users/{userId}/carts/purchase/{productId}", CURRENT_USER_ID, PRODUCT_ID)
                .contentType(APPLICATION_JSON_UTF8)
                .content(requestCartDTO))
                .andExpect(status().isOk());
        verify(cartService).productPurchase(any());
        verifyZeroInteractions(cartService);
    }

    @Test
    public void deleteByProductId_ProductIdGiven_ShouldExecuteCartServiceDeleteExactlyOnce() throws Exception {
        mockMvc.perform(delete("/api/users/{userId}/carts/{productId}", CURRENT_USER_ID, PRODUCT_ID))
                .andExpect(status().isOk());
        verify(cartService).delete(PRODUCT_ID);
        verifyZeroInteractions(cartService);
    }

    @Test
    public void deleteAll_UserIdGiven_ShouldExecuteCartServiceDeleteAllExactlyOnce() throws Exception {
        mockMvc.perform(delete("/api/users/{userId}/carts", CURRENT_USER_ID))
                .andExpect(status().isOk());
        verify(cartService).deleteAll(CURRENT_USER_ID);
        verifyZeroInteractions(cartService);
    }

    @Test
    public void getByProductId_ProductIdGiven_ShouldExecuteCartServiceGetByProductIdExactlyOnce() throws Exception {
        mockMvc.perform(get("/api/users/{userId}/carts/{productId}", CURRENT_USER_ID, PRODUCT_ID))
                .andExpect(status().isOk());
        verify(cartService).getByProductId(PRODUCT_ID);
        verifyZeroInteractions(cartService);
    }

    @Test
    public void insert_CartGiven_ShouldExecuteCartServiceInsertExactlyOnce() throws Exception {
        String requestCartDTO = objectWriter.writeValueAsString(new Cart());
        mockMvc.perform(post("/api/users/{userId}/carts", CURRENT_USER_ID)
                .contentType(APPLICATION_JSON_UTF8)
                .content(requestCartDTO))
                .andExpect(status().isCreated());
        verify(cartService).insert(any());
        verifyZeroInteractions(cartService);
    }

    @Test
    public void update_CartGiven_ShouldExecuteCartServiceUpdateExactlyOnce() throws Exception {
        String requestCartDTO = objectWriter.writeValueAsString(new Cart());
        mockMvc.perform(put("/api/users/{userId}/carts", CURRENT_USER_ID)
                .contentType(APPLICATION_JSON_UTF8)
                .content(requestCartDTO))
                .andExpect(status().isOk());
        verify(cartService).update(any());
        verifyZeroInteractions(cartService);
    }
}
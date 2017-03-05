package com.softserve.if072.restservice.controller;

import com.softserve.if072.common.model.Cart;
import com.softserve.if072.restservice.service.CartService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
    public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType()
            , MediaType.APPLICATION_JSON.getSubtype()
            , Charset.forName("utf8"));
    @Mock
    private CartService cartService;
    private CartController cartController;
    private MockMvc mockMvc;

    @Before
    public void setup() throws ClassNotFoundException, NoSuchMethodException {
        cartController = new CartController(cartService);
        mockMvc = standaloneSetup(cartController)
                .build();
    }

    @Test
    public void getByUserId_ShouldReturnAllUsersCart() throws Exception {
        Cart cart1 = new Cart.Builder()
                .defaultUser(1)
                .defaultStore(1)
                .defaultProduct(1)
                .amount(5)
                .build();
        Cart cart2 = new Cart.Builder()
                .defaultUser(2)
                .defaultStore(2)
                .defaultProduct(2)
                .amount(3)
                .build();
        List<Cart> carts = Arrays.asList(cart1, cart2);

        when(cartService.getByUserId(anyInt())).thenReturn(carts);
        mockMvc.perform(get("/api/users/{userId}/carts", 4))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0]['user']['name']", is("user1")))
                .andExpect(jsonPath("$[0]['store']['name']", is("store1")))
                .andExpect(jsonPath("$[0]['product']['name']", is("product1")))
                .andExpect(jsonPath("$[0]['amount']", is(5)))
                .andExpect(jsonPath("$[1]['user']['name']", is("user2")))
                .andExpect(jsonPath("$[1]['store']['name']", is("store2")))
                .andExpect(jsonPath("$[1]['product']['name']", is("product2")))
                .andExpect(jsonPath("$[1]['amount']", is(3)));
        verify(cartService, times(1)).getByUserId(anyInt());
        verifyZeroInteractions(cartService);
    }
}
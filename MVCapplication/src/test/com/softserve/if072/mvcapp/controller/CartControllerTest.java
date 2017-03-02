package com.softserve.if072.mvcapp.controller;

import com.softserve.if072.common.model.Cart;
import com.softserve.if072.common.model.User;
import com.softserve.if072.mvcapp.service.CartService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.servlet.view.InternalResourceView;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

/**
 * The CartControllerTest class is used to test CartController class methods
 *
 * @author Igor Kryviuk
 */
@RunWith(MockitoJUnitRunner.class)
public class CartControllerTest {
    @Mock
    private CartService cartService;
    @Mock
    private Cart cart;
    @Mock
    private User user;
    @InjectMocks
    private CartController cartController = spy(CartController.class);
    private MockMvc mockMvc;

    @Before
    public void setup() throws ClassNotFoundException, NoSuchMethodException {
        mockMvc = standaloneSetup(cartController)
                .setSingleView(new InternalResourceView("/WEB-INF/views/cart/cart.jsp"))
                .build();
    }

    @Test
    public void getCarts_ShouldReturnCartViewName() throws Exception {
        List<Cart> carts = Arrays.asList(cart, cart);
        when(cartService.getByUserId()).thenReturn(carts);
        mockMvc.perform(get("/cart"))
                .andExpect(status().isOk())
                .andExpect(view().name("cart"))
                .andExpect(model().attributeExists("carts"))
                .andExpect(model().attribute("carts", hasSize(2)))
                .andExpect(model().attributeExists("cartDTO"));
        verify(cartService, times(1)).getByUserId();
    }

    @Test
    public void getCarts_ShouldReturnEmptyCartViewName() throws Exception {
        List<Cart> carts = Arrays.asList();
        when(cartService.getByUserId()).thenReturn(carts);
        mockMvc.perform(get("/cart"))
                .andExpect(status().isOk())
                .andExpect(view().name("emptyCart"))
                .andExpect(model().attributeDoesNotExist("carts"));
        verify(cartService, times(1)).getByUserId();
    }
}

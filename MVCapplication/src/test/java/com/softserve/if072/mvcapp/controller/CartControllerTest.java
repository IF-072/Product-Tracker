package com.softserve.if072.mvcapp.controller;

import com.softserve.if072.common.model.Cart;
import com.softserve.if072.mvcapp.service.CartService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
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
 * The CartControllerTest class is used to test CartController class methods
 *
 * @author Igor Kryviuk
 */
@RunWith(MockitoJUnitRunner.class)
public class CartControllerTest {
    @Mock
    private CartService cartService;
    private CartController cartController;
    private MockMvc mockMvc;

    @Before
    public void setup() throws ClassNotFoundException, NoSuchMethodException {
        cartController = new CartController(cartService);
        mockMvc = standaloneSetup(cartController)
                .setViewResolvers(new InternalResourceViewResolver("/WEB-INF/views/cart/", ".jsp"))
                .build();
    }

    @Test
    public void getCarts_ShouldReturnCartViewName() throws Exception {
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
        when(cartService.getByUserId()).thenReturn(carts);
        mockMvc.perform(get("/cart"))
                .andExpect(status().isOk())
                .andExpect(view().name("cart"))
                .andExpect(forwardedUrl("/WEB-INF/views/cart/cart.jsp"))
                .andExpect(model().attributeExists("carts"))
                .andExpect(model().attribute("carts", hasSize(2)))
                .andExpect(model().attribute("carts", hasItem(
                        allOf(
                                hasProperty("user", hasProperty("name", is("user1"))),
                                hasProperty("store", hasProperty("name", is("store1"))),
                                hasProperty("product", hasProperty("name", is("product1"))),
                                hasProperty("amount", is(5))
                        ))
                ))
                .andExpect(model().attribute("carts", hasItem(
                        allOf(
                                hasProperty("user", hasProperty("name", is("user2"))),
                                hasProperty("store", hasProperty("name", is("store2"))),
                                hasProperty("product", hasProperty("name", is("product2"))),
                                hasProperty("amount", is(3))
                        ))
                ))
                .andExpect(model().attributeExists("cartDTO"));
        verify(cartService, times(1)).getByUserId();
        verifyZeroInteractions(cartService);
    }

    @Test
    public void getCarts_ShouldReturnEmptyCartViewName() throws Exception {
        List<Cart> carts = Arrays.asList();
        when(cartService.getByUserId()).thenReturn(carts);
        mockMvc.perform(get("/cart"))
                .andExpect(status().isOk())
                .andExpect(view().name("emptyCart"))
                .andExpect(forwardedUrl("/WEB-INF/views/cart/emptyCart.jsp"))
                .andExpect(model().attributeDoesNotExist("carts"));
        verify(cartService, times(1)).getByUserId();
        verifyZeroInteractions(cartService);
    }

    @Test
    public void productPurchase_ShouldRedirectToCartHandler() throws Exception {
        mockMvc.perform(post("/cart/purchase"))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/cart"));
    }

    @Test
    public void deleteProductFromCart_ShouldRedirectToCartHandler() throws Exception {
        mockMvc.perform(get("/cart/delete"))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/cart"));
    }
}



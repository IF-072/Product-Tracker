package com.softserve.if072.mvcapp.controller;

import com.softserve.if072.common.model.Cart;
import com.softserve.if072.mvcapp.service.CartService;
import com.softserve.if072.mvcapp.test.utils.CartBuilder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
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
    private static final int FIRST_CART_ITEM_ID = 1;
    private static final int SECOND_CART_ITEM_ID = 2;
    private static final int FIRST_CART_ITEM_AMOUNT = 5;
    private static final int SECOND_CART_ITEM_AMOUNT = 3;
    private static final int CURRENT_USER_ID = 4;
    private static final int PRODUCT_ID = 32;
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
    public void getCarts_ShouldReturnCartViewNameAndModelShouldHaveAppropriateAttributes() throws Exception {
        Cart cart1 = CartBuilder.getDefaultCart(FIRST_CART_ITEM_ID, CURRENT_USER_ID, FIRST_CART_ITEM_AMOUNT);
        Cart cart2 = CartBuilder.getDefaultCart(SECOND_CART_ITEM_ID, CURRENT_USER_ID, SECOND_CART_ITEM_AMOUNT);
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
                                hasProperty("user", hasProperty("name"
                                        , is(String.format("user%d", CURRENT_USER_ID)))),
                                hasProperty("store", hasProperty("name"
                                        , is(String.format("store%d", FIRST_CART_ITEM_ID)))),
                                hasProperty("product", hasProperty("name"
                                        , is(String.format("product%d", FIRST_CART_ITEM_ID)))),
                                hasProperty("amount", is(FIRST_CART_ITEM_AMOUNT))
                        ))
                ))
                .andExpect(model().attribute("carts", hasItem(
                        allOf(
                                hasProperty("user", hasProperty("name"
                                        , is(String.format("user%d", CURRENT_USER_ID)))),
                                hasProperty("store", hasProperty("name"
                                        , is(String.format("store%d", SECOND_CART_ITEM_ID)))),
                                hasProperty("product", hasProperty("name"
                                        , is(String.format("product%d", SECOND_CART_ITEM_ID)))),
                                hasProperty("amount", is(SECOND_CART_ITEM_AMOUNT))
                        ))
                ))
                .andExpect(model().attributeExists("cartDTO"));
        verify(cartService).getByUserId();
        verifyZeroInteractions(cartService);
    }

    @Test
    public void getCarts_ShouldReturnEmptyCartViewName() throws Exception {
        when(cartService.getByUserId()).thenReturn(Collections.emptyList());
        mockMvc.perform(get("/cart"))
                .andExpect(status().isOk())
                .andExpect(view().name("emptyCart"))
                .andExpect(forwardedUrl("/WEB-INF/views/cart/emptyCart.jsp"))
                .andExpect(model().attributeDoesNotExist("carts"));
        verify(cartService).getByUserId();
        verifyZeroInteractions(cartService);
    }

    @Test
    public void productPurchase_ShouldRedirectToCartHandler() throws Exception {
        mockMvc.perform(post("/cart/purchase/{productId}", PRODUCT_ID))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/cart"));
    }

    @Test
    public void deleteProductFromCart_ShouldRedirectToCartHandler() throws Exception {
        mockMvc.perform(get("/cart/delete/{productId}", PRODUCT_ID))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/cart"));
    }

    @Test
    public void deleteAllProductsFromCart_ShouldForwardToEmptyCartPage() throws Exception {
        mockMvc.perform(get("/cart/delete"))
                .andExpect(status().isOk())
                .andExpect(view().name("emptyCart"))
                .andExpect(forwardedUrl("/WEB-INF/views/cart/emptyCart.jsp"));
    }
}



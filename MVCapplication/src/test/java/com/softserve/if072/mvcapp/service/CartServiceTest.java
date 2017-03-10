package com.softserve.if072.mvcapp.service;

import com.softserve.if072.common.model.Cart;
import com.softserve.if072.common.model.User;
import com.softserve.if072.common.model.dto.CartDTO;
import com.softserve.if072.mvcapp.test.utils.CartBuilder;
import org.apache.commons.collections.CollectionUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

/**
 * The CartServiceTest class is used to test CartService class methods
 *
 * @author Igor Kryviuk
 */
@RunWith(MockitoJUnitRunner.class)
public class CartServiceTest {
    private static final int FIRST_CART_ITEM_ID = 1;
    private static final int SECOND_CART_ITEM_ID = 2;
    private static final int FIRST_CART_ITEM_AMOUNT = 5;
    private static final int SECOND_CART_ITEM_AMOUNT = 3;
    private static final int CURRENT_USER_ID = 4;
    private static final String REST_CART_ULR = null;
    private static final int PRODUCT_ID = 32;
    private CartService cartService;
    @Mock
    private RestTemplate restTemplate;
    @Mock
    UserService userService;
    @Mock
    User user;

    @Before
    public void setup() {
        cartService = new CartService(restTemplate, userService);
        when(userService.getCurrentUser()).thenReturn(user);
        when(user.getId()).thenReturn(CURRENT_USER_ID);
    }

    @Test
    public void getByUserId_ShouldReturnNoEmptyList() throws Exception {
        Cart cart1 = CartBuilder.getDefaultCart(FIRST_CART_ITEM_ID, CURRENT_USER_ID, FIRST_CART_ITEM_AMOUNT);
        Cart cart2 = CartBuilder.getDefaultCart(SECOND_CART_ITEM_ID, CURRENT_USER_ID, SECOND_CART_ITEM_AMOUNT);
        List<Cart> carts = Arrays.asList(cart1, cart2);
        when(restTemplate.getForObject(REST_CART_ULR, List.class, CURRENT_USER_ID)).thenReturn(carts);
        List<Cart> actualCarts = cartService.getByUserId();
        assertEquals(2, actualCarts.size());
        assertEquals(String.format("user%d", CURRENT_USER_ID), actualCarts.get(0).getUser().getName());
        assertEquals(String.format("product%d", SECOND_CART_ITEM_ID), actualCarts.get(1).getProduct().getName());
        verify(restTemplate).getForObject(REST_CART_ULR, List.class, CURRENT_USER_ID);
        verifyZeroInteractions(restTemplate);
    }

    @Test
    public void getByUserId_ShouldReturnEmptyList() throws Exception {
        when(restTemplate.getForObject(REST_CART_ULR, List.class, CURRENT_USER_ID)).thenReturn(Collections.emptyList());
        List<Cart> actualCarts = cartService.getByUserId();
        assertTrue(CollectionUtils.isEmpty(actualCarts));
        verify(restTemplate).getForObject(REST_CART_ULR, List.class, CURRENT_USER_ID);
        verifyZeroInteractions(restTemplate);
    }

    @Test
    public void productPurchase_CartDTOGiven_ShouldExecuteRestTemplatePutExactlyOnce() throws Exception {
        CartDTO cartDTO = new CartDTO(CURRENT_USER_ID, 2, PRODUCT_ID, 4, 4);
        cartService.productPurchase(cartDTO);
        verify(restTemplate).put(REST_CART_ULR, cartDTO, CURRENT_USER_ID, PRODUCT_ID);
        verifyZeroInteractions(restTemplate);
    }

    @Test
    public void deleteProductFromCart_CartDTOGiven_ShouldExecuteRestTemplateDeleteExactlyOnce() throws Exception {
        CartDTO cartDTO = new CartDTO(CURRENT_USER_ID, 2, PRODUCT_ID, 4, 4);
        cartService.deleteProductFromCart(cartDTO);
        verify(restTemplate).delete(REST_CART_ULR, CURRENT_USER_ID, PRODUCT_ID);
        verifyZeroInteractions(restTemplate);
    }

    @Test
    public void deleteAllProductsFromCart_ShouldExecuteRestTemplateDeleteAllExactlyOnce() throws Exception {
        cartService.deleteAllProductsFromCart();
        verify(restTemplate).delete(REST_CART_ULR, CURRENT_USER_ID);
        verifyZeroInteractions(restTemplate);
    }
}
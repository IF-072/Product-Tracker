package com.softserve.if072.mvcapp.service;

import com.softserve.if072.common.model.Cart;
import com.softserve.if072.common.model.ShoppingList;
import com.softserve.if072.common.model.Store;
import com.softserve.if072.mvcapp.dto.FormForCart;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * The GoShoppingPageServiceTest class is used to test
 * GoShoppingPageService class methods.
 *
 * @author Roman Dyndyn
 */
@RunWith(MockitoJUnitRunner.class)
public class GoShoppingPageServiceTest {

    @Mock
    private RestTemplate restTemplate;
    @Mock
    private MessageService messageService;
    @Mock
    private CartService cartService;
    @InjectMocks
    private GoShoppingPageService goShoppingPageService;
    private List<Store> stores;
    private Map<String, List<ShoppingList>> map;
    private int userId;
    private final String locale = "uk";

    @Before
    public void setup() {
        stores = new ArrayList<>();
        map = new HashMap<>();
        userId = 2;
    }

    @Test
    public void testGetStores() {
        when(restTemplate.getForObject(anyString(), eq(List.class))).thenReturn(stores);

        assertEquals(stores, goShoppingPageService.getStores(userId));
        verify(restTemplate).getForObject(anyString(), eq(List.class));
    }

    @Test
    public void testGetProducts() {
        final int storeId = 1;
        when(restTemplate.getForObject(anyString(), eq(Map.class))).thenReturn(map);

        assertEquals(map, goShoppingPageService.getProducts(userId, storeId));
        verify(restTemplate).getForObject(anyString(), eq(Map.class));
    }

    @Test
    public void addToCart() {
        final FormForCart formForCartMock = mock(FormForCart.class);
        when(formForCartMock.getUserId()).thenReturn(userId);
        goShoppingPageService.addToCart(formForCartMock, locale);
        verify(formForCartMock).removeUncheked();
        verify(restTemplate).postForObject(anyString(), any(), eq(Cart.class));
        verify(messageService).broadcast(eq("goShopping.start"), eq(locale), eq(userId), any());
    }

    @Test
    public void reviewCart() {
        goShoppingPageService.reviewCart(locale, userId);

        verify(cartService).getByUserId();
        verify(messageService).broadcast("goShopping.finish", locale, userId);
    }
}

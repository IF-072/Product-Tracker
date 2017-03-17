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
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * The GoShoppingPageServiceTest class is used to test GoShoppingPageService class methods
 *
 * @author Roman Dyndyn
 */
@RunWith(MockitoJUnitRunner.class)
public class GoShoppingPageServiceTest {

    @Mock
    private RestTemplate restTemplate;
    @InjectMocks
    private GoShoppingPageService goShoppingPageService;
    private List<Store> stores;
    private FormForCart formForCart;
    private Map<String, List<ShoppingList>> map;
    private int userId;

    @Before
    public void setup() {
        stores = new ArrayList<>();
        formForCart = new FormForCart();
        map = new HashMap<>();
        userId = 2;
    }

    @Test
    public void testGetStores() {
        when(restTemplate.getForObject(anyString(), eq(List.class))).thenReturn(stores);

        assertTrue(stores.equals(goShoppingPageService.getStores(userId)));
        verify(restTemplate).getForObject(anyString(), eq(List.class));
    }

    @Test
    public void testGetProducts() {
        int storeId = 1;
        when(restTemplate.getForObject(anyString(), eq(Map.class))).thenReturn(map);

        assertTrue(map.equals(goShoppingPageService.getProducts(userId, storeId)));
        verify(restTemplate).getForObject(anyString(), eq(Map.class));
    }

    @Test
    public void addToCart() {
        FormForCart formForCartMock = Mockito.spy(formForCart);
        goShoppingPageService.addToCart(formForCartMock);
        verify(formForCartMock).removeUncheked();
        verify(restTemplate).postForObject(anyString(), any(), eq(Cart.class));
    }
}

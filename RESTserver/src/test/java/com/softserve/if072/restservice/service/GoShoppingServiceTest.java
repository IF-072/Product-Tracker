package com.softserve.if072.restservice.service;

import com.softserve.if072.common.model.Cart;
import com.softserve.if072.common.model.Product;
import com.softserve.if072.common.model.ShoppingList;
import com.softserve.if072.common.model.Store;
import com.softserve.if072.restservice.dao.mybatisdao.CartDAO;
import com.softserve.if072.restservice.dao.mybatisdao.ShoppingListDAO;
import com.softserve.if072.restservice.dao.mybatisdao.StoreDAO;
import com.softserve.if072.restservice.exception.DataNotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * The GoShoppingServiceTest class is used to test GoShoppingService class methods
 *
 * @author Roman Dyndyn
 */
@RunWith(MockitoJUnitRunner.class)
public class GoShoppingServiceTest {

    @Mock
    private ShoppingListDAO shoppingListDAO;
    @Mock
    private StoreDAO storeDAO;
    @Mock
    private CartDAO cartDAO;
    @InjectMocks
    private GoShoppingService goShoppingService;
    private List<Product> products;
    private Product[] arr;
    private Store store;
    private int userId;

    @Before
    public void setup(){
        arr = new Product[]{new Product(), new Product(), new Product(), new Product()};
        int i = 1;
        for (Product product : arr) {
            product.setId(i++);
            product.setName(Integer.toString(i));
        }
        products = new ArrayList<>(Arrays.asList(arr[0], arr[1], arr[2]));
        userId = 2;
        store = new Store();
    }

    @Test
    public void testGetStoreByUserId(){
        Store store1 = new Store();
        store1.setProducts(new ArrayList<>(Arrays.asList(arr[0], arr[1])));
        Store store2 = new Store();
        store2.setProducts(new ArrayList<>(Arrays.asList(arr[2], arr[3])));

        when(cartDAO.getByUserId(userId)).thenReturn(null);
        when(shoppingListDAO.getProductsByUserId(userId)).thenReturn(products);
        when(storeDAO.getAllByUser(userId)).thenReturn(new ArrayList<>(Arrays.asList(store1, store2)));

        List<Store> result = goShoppingService.getStoreByUserId(userId);
        assertTrue(new ArrayList<Product>(Arrays.asList(arr[0], arr[1])).equals(result.get(0).getProducts()));
        assertTrue(new ArrayList<Product>(Arrays.asList(arr[2])).equals(result.get(1).getProducts()));

        verify(cartDAO).getByUserId(userId);
        verify(shoppingListDAO).getProductsByUserId(userId);
        verify(storeDAO).getAllByUser(userId);
    }

    @Test
    public void testGetStoreByUserId_ShouldReturnNull(){
        when(cartDAO.getByUserId(userId)).thenReturn(null);
        when(shoppingListDAO.getProductsByUserId(userId)).thenReturn(null);

        assertNull(goShoppingService.getStoreByUserId(userId));

        verify(cartDAO).getByUserId(userId);
        verify(shoppingListDAO).getProductsByUserId(userId);
        verify(storeDAO, never()).getAllByUser(userId);
    }

    @Test(expected = DataNotFoundException.class)
    public void testGetStoreByUserId_ShouldThrowException(){
        int userId = 2;
        when(cartDAO.getByUserId(userId)).thenReturn(null);
        when(shoppingListDAO.getProductsByUserId(userId)).thenReturn(products);
        when(storeDAO.getAllByUser(userId)).thenReturn(null);

        goShoppingService.getStoreByUserId(userId);

        verify(cartDAO).getByUserId(userId);
        verify(shoppingListDAO).getProductsByUserId(userId);
        verify(storeDAO).getAllByUser(userId);
    }

    @Test
    public void testGetProducts(){
        int userId = 2;
        int storeId = 3;
        ShoppingList[] args = {
                new ShoppingList(null, arr[0], 1),
                new ShoppingList(null, arr[1], 1),
                new ShoppingList(null, arr[2], 1),
                new ShoppingList(null, arr[3], 1)
        };
        List<ShoppingList> selected = new ArrayList<>(Arrays.asList(args[0], args[1], args[2]));
        List<ShoppingList> shoppingLists = new ArrayList<>(selected);
        shoppingLists.add(args[3]);

        when(shoppingListDAO.getByUserID(userId)).thenReturn(shoppingLists);
        when(storeDAO.getProductsByStoreId(storeId, userId)).thenReturn(products);
        when(storeDAO.getByID(storeId)).thenReturn(store);

        Map<String ,List<ShoppingList>> result = goShoppingService.getProducts(userId, storeId);
        assertTrue(selected.equals(result.get("selected")));
        assertTrue(new ArrayList<>(Arrays.asList(args[3]))
                .equals(result.get("remained")));

        verify(shoppingListDAO).getByUserID(userId);
        verify(storeDAO).getByID(storeId);
        verify(storeDAO).getProductsByStoreId(storeId, userId);
    }

    @Test(expected = DataNotFoundException.class)
    public void testGetProducts_ShouldThrowException(){
        int storeId = 3;

        when(storeDAO.getProductsByStoreId(storeId, userId)).thenReturn(null);
        goShoppingService.getProducts(userId, storeId);

        verify(shoppingListDAO, never()).getByUserID(userId);
        verify(storeDAO, never()).getByID(storeId);
        verify(storeDAO).getProductsByStoreId(storeId, userId);
    }

    @Test
    public void testInsertCart(){
        goShoppingService.insertCart(Arrays.asList(mock(Cart.class), mock(Cart.class), mock(Cart.class)));
        verify(cartDAO, times(3)).insert(any());
    }
}

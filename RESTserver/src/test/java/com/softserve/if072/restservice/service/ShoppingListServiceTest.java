package com.softserve.if072.restservice.service;

import com.softserve.if072.common.model.Product;
import com.softserve.if072.common.model.ShoppingList;
import com.softserve.if072.common.model.User;
import com.softserve.if072.restservice.dao.mybatisdao.ShoppingListDAO;
import com.softserve.if072.restservice.exception.DataNotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * The ShoppingListServiceTest class is used to test ShoppingListService class methods
 *
 * @author Roman Dyndyn
 */
@RunWith(MockitoJUnitRunner.class)
public class ShoppingListServiceTest {

    @Mock
    private ShoppingListDAO shoppingListDAO;
    @InjectMocks
    private ShoppingListService shoppingListService;
    private Product product;
    private User user;
    private ShoppingList shoppingList;
    private int userId;

    @Before
    public void setup() throws ClassNotFoundException, NoSuchMethodException {
        user = new User();
        user.setId(2);
        product = new Product();
        product.setUser(user);
        product.setId(1);
        shoppingList = new ShoppingList(user, product, 2);
        userId = 2;
    }

    @Test
    public void testGetByUserId_ShouldReturnListOfShoppingList() {
        List<ShoppingList> shoppingLists = Arrays.asList(shoppingList, shoppingList);
        when(shoppingListDAO.getByUserID(userId)).thenReturn(shoppingLists);

        assertTrue(shoppingLists.equals(shoppingListService.getByUserId(userId)));
        verify(shoppingListDAO, times(1)).getByUserID(userId);
    }

    @Test(expected = DataNotFoundException.class)
    public void testGetByUserId_ShouldThrowException() throws Exception {
        when(shoppingListDAO.getByUserID(userId)).thenReturn(null);
        shoppingListService.getByUserId(userId);
        verify(shoppingListDAO, times(1)).getByUserID(userId);
    }

    @Test
    public void testGetByProductId_ShouldReturnShoppingList() {
        int productId = 2;
        when(shoppingListDAO.getByProductId(productId)).thenReturn(shoppingList);

        assertTrue(shoppingList.equals(shoppingListDAO.getByProductId(productId)));
        verify(shoppingListDAO, times(1)).getByProductId(productId);
    }

    @Test
    public void testGetByUserAndProductId_ShouldReturnShoppingList() {
        int productId = 2;
        when(shoppingListDAO.getByUserAndProductId(userId, productId)).thenReturn(shoppingList);

        assertTrue(shoppingList.equals(shoppingListDAO.getByUserAndProductId(userId, productId)));
        verify(shoppingListDAO, times(1)).getByUserAndProductId(userId, productId);
    }

    @Test
    public void testGetProductsByUserId_ShouldReturnListOfProducts() {
        List<Product> products = Arrays.asList(product, product);
        when(shoppingListDAO.getProductsByUserId(userId)).thenReturn(products);

        assertTrue(products.equals(shoppingListService.getProductsByUserId(userId)));
        verify(shoppingListDAO, times(1)).getProductsByUserId(userId);
    }

    @Test(expected = DataNotFoundException.class)
    public void testGetProductsByUserId() {
        when(shoppingListDAO.getProductsByUserId(userId)).thenReturn(null);
        shoppingListService.getProductsByUserId(userId);
        verify(shoppingListDAO, times(1)).getProductsByUserId(userId);
    }

    @Test
    public void testInsert() {
        shoppingListService.insert(shoppingList);
        verify(shoppingListDAO, times(1)).insert(shoppingList);
    }

    @Test
    public void testInsert_ShouldNotBeExecuted() {
        shoppingListService.insert(null);
        verify(shoppingListDAO, times(0)).insert(any());
    }

    @Test
    public void testUpdate() {
        shoppingListService.update(shoppingList);

        verify(shoppingListDAO, times(1)).update(shoppingList);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testUpdate_ShouldThrowException() {
        shoppingList.setAmount(-1);
        shoppingListService.update(shoppingList);

        verify(shoppingListDAO, times(0)).update(shoppingList);
    }

    @Test
    public void testDelete() {
        shoppingListService.delete(shoppingList);
        verify(shoppingListDAO, times(1)).delete(shoppingList);
    }

    @Test
    public void testDelete_ShouldNotBeExecuted() {
        shoppingListService.delete(null);
        verify(shoppingListDAO, times(0)).delete(any());
    }

    @Test
    public void testDeleteByProductId() {
        int productId = 2;
        shoppingListService.delete(productId);
        verify(shoppingListDAO, times(1)).deleteByProductId(productId);
    }

    @Test
    public void testDeleteByProductId_ShouldNotBeExecuted() {
        int productId = -1;
        shoppingListService.delete(productId);
        verify(shoppingListDAO, times(0)).deleteByProductId(anyInt());
    }
}
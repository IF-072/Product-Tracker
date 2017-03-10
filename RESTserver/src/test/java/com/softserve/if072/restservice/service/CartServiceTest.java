package com.softserve.if072.restservice.service;

import com.softserve.if072.common.model.Cart;
import com.softserve.if072.common.model.Product;
import com.softserve.if072.common.model.ShoppingList;
import com.softserve.if072.common.model.Storage;
import com.softserve.if072.common.model.dto.CartDTO;
import com.softserve.if072.restservice.dao.mybatisdao.CartDAO;
import com.softserve.if072.restservice.exception.DataNotFoundException;
import com.softserve.if072.restservice.test.utils.CartBuilder;
import org.apache.commons.collections.CollectionUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doNothing;
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
    private static final int FIRST_CART_ITEM_INITIALAMOUNT = 5;
    private static final int SECOND_CART_ITEM_AMOUNT = 3;
    private static final int CURRENT_USER_ID = 4;
    private static final int PRODUCT_ID = 32;
    @Mock
    private CartDAO cartDAO;
    @Mock
    private StorageService storageService;
    @Mock
    private ShoppingListService shoppingListService;
    @Mock
    private Storage storage;
    @Mock
    private ShoppingList shoppingList;
    private CartService cartService;
    private CartDTO cartDTO;

    @Before
    public void setup() {
        cartService = new CartService(cartDAO, storageService, shoppingListService);
        when(cartDAO.deleteByProductId(anyInt())).thenReturn(1);
        cartDTO = new CartDTO(CURRENT_USER_ID, 22, PRODUCT_ID, FIRST_CART_ITEM_AMOUNT, FIRST_CART_ITEM_INITIALAMOUNT);
    }

    @Test
    public void getByUserId_UserIdGiven_ShouldReturnNotEmptyUsersCart() {
        Cart cart1 = CartBuilder.getDefaultCart(FIRST_CART_ITEM_ID, CURRENT_USER_ID, FIRST_CART_ITEM_AMOUNT);
        Cart cart2 = CartBuilder.getDefaultCart(SECOND_CART_ITEM_ID, CURRENT_USER_ID, SECOND_CART_ITEM_AMOUNT);
        List<Cart> carts = Arrays.asList(cart1, cart2);
        when(cartDAO.getByUserId(CURRENT_USER_ID)).thenReturn(carts);
        List<Cart> actualCarts = cartService.getByUserId(CURRENT_USER_ID);
        assertEquals(2, actualCarts.size());
        assertEquals(String.format("user%d", CURRENT_USER_ID), actualCarts.get(0).getUser().getName());
        assertEquals(String.format("product%d", SECOND_CART_ITEM_ID), actualCarts.get(1).getProduct().getName());
        verify(cartDAO).getByUserId(CURRENT_USER_ID);
        verifyZeroInteractions(cartDAO);
    }

    @Test
    public void getByUserId_UserIdGiven_ShouldReturnEmptyUsersCart() {
        when(cartDAO.getByUserId(CURRENT_USER_ID)).thenReturn(Collections.emptyList());
        List<Cart> actualCarts = cartService.getByUserId(CURRENT_USER_ID);
        assertTrue(CollectionUtils.isEmpty(actualCarts));
        verify(cartDAO).getByUserId(CURRENT_USER_ID);
        verifyZeroInteractions(cartDAO);
    }

    @Test
    public void productPurchase_CartDTOGiven_ProductNotExistInStoreAndInShoppingList() throws Exception {
        when(storageService.getByProductId(anyInt())).thenReturn(null);
        doNothing().when(storageService).insert(anyInt(), anyInt(), anyInt());
        when(shoppingListService.getByProductId(anyInt())).thenReturn(null);
        cartService.productPurchase(cartDTO);
        verify(cartDAO).deleteByProductId(anyInt());
        verify(storageService).getByProductId(anyInt());
        verify(storageService).insert(anyInt(), anyInt(), anyInt());
        verify(shoppingListService).getByProductId(anyInt());
        verifyZeroInteractions(cartDAO);
        verifyZeroInteractions(storageService);
        verifyZeroInteractions(shoppingListService);
    }

    @Test
    public void productPurchase_CartDTOGiven_ProductExistInStoreAndInShoppingList_InitialAmountNoMorePurchaseAmount() throws
            Exception {
        when(storageService.getByProductId(anyInt())).thenReturn(storage);
        when(shoppingListService.getByProductId(anyInt())).thenReturn(shoppingList);
        cartService.productPurchase(cartDTO);
        assertFalse(cartDTO.getInitialAmount() > cartDTO.getAmount());
        verify(cartDAO).deleteByProductId(anyInt());
        verify(storageService).getByProductId(anyInt());
        verify(storage).setAmount(anyInt());
        verify(storageService).update(storage);
        verify(shoppingListService).getByProductId(anyInt());
        verify(shoppingListService).delete(shoppingList);
        verifyZeroInteractions(cartDAO);
        verifyZeroInteractions(shoppingList);
        verifyZeroInteractions(shoppingListService);
    }

    @Test
    public void productPurchase_CartDTOGiven_ProductExistInStoreAndInShoppingList_InitialAmountMorePurchaseAmount() throws
            Exception {
        when(storageService.getByProductId(anyInt())).thenReturn(storage);
        when(shoppingListService.getByProductId(anyInt())).thenReturn(shoppingList);
        cartDTO.setAmount(2);
        cartService.productPurchase(cartDTO);
        assertTrue(cartDTO.getInitialAmount() > cartDTO.getAmount());
        verify(cartDAO).deleteByProductId(anyInt());
        verify(storageService).getByProductId(anyInt());
        verify(storage).setAmount(anyInt());
        verify(storageService).update(storage);
        verify(shoppingListService).getByProductId(anyInt());
        verify(shoppingList).setAmount(anyInt());
        verify(shoppingListService).update(shoppingList);
        verifyZeroInteractions(cartDAO);
        verifyZeroInteractions(shoppingList);
        verifyZeroInteractions(shoppingListService);
    }

    @Test(expected = DataNotFoundException.class)
    public void productPurchase_CartDTOGiven_ShoppingListServiceGetByProductIdThrowException() throws Exception {
        when(storageService.getByProductId(anyInt())).thenReturn(null);
        doNothing().when(storageService).insert(anyInt(), anyInt(), anyInt());
        when(shoppingListService.getByProductId(anyInt())).thenThrow(new DataNotFoundException());
        cartService.productPurchase(cartDTO);
        verify(cartDAO).deleteByProductId(anyInt());
        verify(storageService).getByProductId(anyInt());
        verify(storageService).insert(anyInt(), anyInt(), anyInt());
        verify(shoppingListService).getByProductId(anyInt());
        verifyZeroInteractions(cartDAO);
        verifyZeroInteractions(storageService);
        verifyZeroInteractions(shoppingListService);
    }

    @Test
    public void delete_ProductIdGiven_ShouldExecuteCartDAODeleteExactlyOnce() throws Exception {
        when(cartDAO.deleteByProductId(PRODUCT_ID)).thenReturn(1);
        cartService.delete(PRODUCT_ID);
        verify(cartDAO).deleteByProductId(PRODUCT_ID);
        verifyZeroInteractions(cartDAO);
    }

    @Test
    public void deleteAll_UserIdGiven_ShouldExecuteCartDAODeleteAllExactlyOnce() throws Exception {
        when(cartDAO.deleteAll(CURRENT_USER_ID)).thenReturn(1);
        cartService.deleteAll(CURRENT_USER_ID);
        verify(cartDAO).deleteAll(CURRENT_USER_ID);
        verifyZeroInteractions(cartDAO);
    }

    @Test
    public void getByProductId_ProductIdGiven_ShouldExecuteCartDAOGetByProductIdExactlyOnce() throws Exception {
        Cart cart = CartBuilder.getDefaultCart(FIRST_CART_ITEM_ID, CURRENT_USER_ID, FIRST_CART_ITEM_AMOUNT);
        when(cartDAO.getByProductId(PRODUCT_ID)).thenReturn(cart);
        Cart actualCart = cartService.getByProductId(PRODUCT_ID);
        assertEquals(String.format("user%d", CURRENT_USER_ID), actualCart.getUser().getName());
        assertEquals(String.format("store%d", FIRST_CART_ITEM_ID), actualCart.getStore().getName());
        assertEquals(String.format("product%d", FIRST_CART_ITEM_ID), actualCart.getProduct().getName());
        assertEquals(FIRST_CART_ITEM_AMOUNT, actualCart.getAmount());
        verify(cartDAO).getByProductId(PRODUCT_ID);
        verifyZeroInteractions(cartDAO);
    }

    @Test
    public void insert_CartGiven_ShouldExecutCartDAOInsertExactlyOnce() throws Exception {
        Product product =new Product();
        product.setId(PRODUCT_ID);
        Cart cart = new Cart();
        cart.setProduct(product);
        cartService.insert(cart);
        verify(cartDAO).insert(cart);
        verifyZeroInteractions(cartDAO);
    }

    @Test
    public void update_CartGiven_ShouldExecutCartDAOUpdateExactlyOnce() throws Exception {
        Product product =new Product();
        product.setId(PRODUCT_ID);
        Cart cart = new Cart();
        cart.setProduct(product);
        when(cartDAO.update(cart)).thenReturn(1);
        cartService.update(cart);
        verify(cartDAO).update(cart);
        verifyZeroInteractions(cartDAO);
    }
}

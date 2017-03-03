package com.softserve.if072.restservice.service;

import com.softserve.if072.common.model.ShoppingList;
import com.softserve.if072.common.model.Storage;
import com.softserve.if072.common.model.dto.CartDTO;
import com.softserve.if072.restservice.dao.mybatisdao.CartDAO;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
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
    @Mock
    private CartDAO cartDAO;
    @Mock
    private StorageService storageService;
    @Mock
    private ShoppingListService shoppingListService;
    @Mock
    Storage storage;
    @Mock
    ShoppingList shoppingList;
    private CartService cartService;
    private CartDTO cartDTO;

    @Before
    public void setup() {
        cartService = new CartService(cartDAO, storageService, shoppingListService);
        when(cartDAO.deleteByProductId(anyInt())).thenReturn(1);
        cartDTO = new CartDTO.Builder()
                .userId(4)
                .storeId(22)
                .productId(22)
                .amount(3)
                .initialAmount(3)
                .build();
    }

    @Test
    public void productPurchase_ProductNotExistInStoreAndInShoppingList() throws Exception {
        when(storageService.getByProductId(anyInt())).thenReturn(null);
        doNothing().when(storageService).insert(anyInt(), anyInt(), anyInt());
        when(shoppingListService.getByProductId(anyInt())).thenReturn(null);
        cartService.productPurchase(cartDTO);
        verify(cartDAO, times(1)).deleteByProductId(anyInt());
        verify(storageService, times(1)).getByProductId(anyInt());
        verify(storageService, times(1)).insert(anyInt(), anyInt(), anyInt());
        verify(shoppingListService, times(1)).getByProductId(anyInt());
        verifyZeroInteractions(cartDAO);
        verifyZeroInteractions(storageService);
        verifyZeroInteractions(shoppingListService);
    }

    @Test
    public void productPurchase_ProductExistInStoreAndInShoppingList_InitialAmountNoMorePurchaseAmount() throws
            Exception {
        when(storageService.getByProductId(anyInt())).thenReturn(storage);
        when(shoppingListService.getByProductId(anyInt())).thenReturn(shoppingList);
        cartService.productPurchase(cartDTO);
        assertFalse(cartDTO.getInitialAmount() > cartDTO.getAmount());
        verify(cartDAO, times(1)).deleteByProductId(anyInt());
        verify(storageService, times(1)).getByProductId(anyInt());
        verify(storage, times(1)).setAmount(anyInt());
        verify(storage, times(1)).getAmount();
        verify(storageService, times(1)).update(storage);
        verify(shoppingListService, times(1)).getByProductId(anyInt());
        verify(shoppingListService, times(1)).delete(shoppingList);
        verifyZeroInteractions(cartDAO);
        verifyZeroInteractions(storage);
        verifyZeroInteractions(shoppingList);
        verifyZeroInteractions(shoppingListService);
    }

    @Test
    public void productPurchase_ProductExistInStoreAndInShoppingList_InitialAmountMorePurchaseAmount() throws
            Exception {
        when(storageService.getByProductId(anyInt())).thenReturn(storage);
        when(shoppingListService.getByProductId(anyInt())).thenReturn(shoppingList);
        cartDTO.setAmount(2);
        cartService.productPurchase(cartDTO);
        assertTrue(cartDTO.getInitialAmount() > cartDTO.getAmount());
        verify(cartDAO, times(1)).deleteByProductId(anyInt());
        verify(storageService, times(1)).getByProductId(anyInt());
        verify(storage, times(1)).setAmount(anyInt());
        verify(storage, times(1)).getAmount();
        verify(storageService, times(1)).update(storage);
        verify(shoppingListService, times(1)).getByProductId(anyInt());
        verify(shoppingList, times(1)).setAmount(anyInt());
        verify(shoppingListService, times(1)).update(shoppingList);
        verifyZeroInteractions(cartDAO);
        verifyZeroInteractions(storage);
        verifyZeroInteractions(shoppingList);
        verifyZeroInteractions(shoppingListService);
    }
}

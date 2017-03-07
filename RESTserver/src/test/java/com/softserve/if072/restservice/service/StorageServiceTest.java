package com.softserve.if072.restservice.service;

import com.softserve.if072.common.model.Product;
import com.softserve.if072.common.model.Storage;
import com.softserve.if072.common.model.User;
import com.softserve.if072.common.model.dto.StorageDTO;
import com.softserve.if072.restservice.dao.mybatisdao.StorageDAO;
import com.softserve.if072.restservice.exception.DataNotFoundException;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.sql.Date;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * The StorageServiceTest class is used to test StorageService class methods
 *
 * @author Roman Dyndyn
 */
@RunWith(MockitoJUnitRunner.class)
public class StorageServiceTest {
    @Mock
    private StorageDAO storageDAO;
    @Mock
    private ShoppingListService shoppingListService;
    @Mock
    private HistoryService historyService;
    @Mock
    private Storage storage;
    @Mock
    private StorageDTO storageDTO;
    @Mock
    private Storage storageDB;
    @Mock
    private Product product;
    @Mock
    private User user;
    @Mock
    private Date date;
    @InjectMocks
    private StorageService storageService;

    @Test
    public void testGetByUserId_ShouldReturnListOfStorage() {
        int userId = 2;
        List<Storage> storages = Arrays.asList(storage, storage);
        when(storageDAO.getByUserID(userId)).thenReturn(storages);

        assertTrue(storages.equals(storageService.getByUserId(userId)));
        verify(storageDAO, times(1)).getByUserID(userId);
    }

    @Test(expected = DataNotFoundException.class)
    public void testGetByUserId_ShouldThrowException() throws Exception {
        int userId = 2;
        when(storageDAO.getByUserID(userId)).thenReturn(null);
        storageService.getByUserId(userId);
        verify(storageDAO, times(1)).getByUserID(userId);
    }

    @Test
    public void testGetByProductId_ShouldReturnStorage() {
        int productId = 2;
        when(storageDAO.getByProductID(productId)).thenReturn(storage);

        assertTrue(storage.equals(storageService.getByProductId(productId)));
        verify(storageDAO, times(1)).getByProductID(productId);
    }

    @Test
    public void testInsert() {
        storageService.insert(storage);
        verify(storageDAO, times(1)).insert(storage);
    }

    @Test
    public void testInsertInParts() {
        int productId = 2;
        int userId = 2;
        int amount = 2;

        storageService.insert(userId, productId, amount);
        verify(storageDAO, times(1)).insertInParts(userId, productId, amount);
    }

    @Test
    public void testUpdate_ShouldNotInsertInShoppingList() {
        int amount = 5;
        int amountDB = 4;
        when(storage.getAmount()).thenReturn(amount);
        when(storage.getProduct()).thenReturn(product);
        when(storage.getEndDate()).thenReturn(date);
        when(storageDB.getAmount()).thenReturn(amountDB);
        when(storageDAO.getByProductID(anyInt())).thenReturn(storageDB);

        storageService.update(storage);

        verify(storageDAO, times(1)).getByProductID(anyInt());
        verify(storageDAO, times(1)).update(storage);
        verify(historyService, times(0)).insert(any());
        verify(shoppingListService, times(0)).insert(any());
    }

    @Test
    public void testUpdate_ShouldInsertInShoppingList() {
        int amount = 1;
        int amountDB = 3;
        when(storage.getAmount()).thenReturn(amount);
        when(storage.getProduct()).thenReturn(product);
        when(storage.getUser()).thenReturn(user);
        when(storageDB.getAmount()).thenReturn(amountDB);
        when(storageDAO.getByProductID(anyInt())).thenReturn(storageDB);

        storageService.update(storage);

        verify(storageDAO, times(1)).getByProductID(anyInt());
        verify(storageDAO, times(1)).updateAmount(storage);
        verify(historyService, times(1)).insert(any());
        verify(shoppingListService, times(1)).insert(any());
    }

    @Test
    public void testUpdate_ShouldNotBeExecuted() {
        when(storage.getAmount()).thenReturn(-1);

        storageService.update(storage);
        verify(storageDAO, times(0)).getByProductID(anyInt());
        verify(storageDAO, times(0)).updateAmount(storage);
        verify(storageDAO, times(0)).update(storage);
        verify(historyService, times(0)).insert(any());
        verify(shoppingListService, times(0)).insert(any());
    }

    @Test
    public void testUpdateWithDto_ShouldNotInsertInShoppingList() {
        int amount = 5;
        int amountDB = 4;
        when(storageDTO.getAmount()).thenReturn(amount);
        when(storage.getAmount()).thenReturn(amountDB);
        when(storageDAO.getByProductID(anyInt())).thenReturn(storage);
        storageService.update(storageDTO);

        verify(storageDAO, times(1)).getByProductID(anyInt());
        verify(storageDAO, times(1)).updateAmount(storage);
        verify(historyService, times(0)).insert(any());
        verify(shoppingListService, times(0)).insert(any());
    }

    @Test
    public void testUpdateWithDTO_ShouldInsertInShoppingList() {
        int amount = 0;
        int amountDB = 1;

        when(storageDTO.getAmount()).thenReturn(amount);
        when(storage.getAmount()).thenReturn(amountDB);
        when(storage.getProduct()).thenReturn(product);
        when(storage.getUser()).thenReturn(user);
        when(storageDAO.getByProductID(anyInt())).thenReturn(storage);

        storageService.update(storageDTO);

        verify(storageDAO, times(1)).getByProductID(anyInt());
        verify(storageDAO, times(1)).updateAmount(storage);
        verify(historyService, times(1)).insert(any());
        verify(shoppingListService, times(1)).insert(any());
    }

    @Test
    public void testUpdateWithDTO_ShouldNotBeExecuted() {
        when(storageDTO.getAmount()).thenReturn(-1);

        storageService.update(storageDTO);
        verify(storageDAO, times(0)).getByProductID(anyInt());
        verify(storageDAO, times(0)).updateAmount(any());
        verify(historyService, times(0)).insert(any());
        verify(shoppingListService, times(0)).insert(any());
    }

    @Test
    public void testDelete() {
        storageService.delete(storage);
        verify(storageDAO, times(1)).delete(storage);
    }

    @Test
    public void testDelete_ShouldNotBeExecuted() {
        storageService.delete(null);
        verify(storageDAO, times(0)).delete(any());
    }
}

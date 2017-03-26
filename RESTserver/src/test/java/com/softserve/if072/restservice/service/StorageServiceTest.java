package com.softserve.if072.restservice.service;

import com.softserve.if072.common.model.Product;
import com.softserve.if072.common.model.Storage;
import com.softserve.if072.common.model.User;
import com.softserve.if072.common.model.dto.StorageDTO;
import com.softserve.if072.restservice.dao.mybatisdao.StorageDAO;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;
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
    @InjectMocks
    private StorageService storageService;

    private Storage storage;
    private StorageDTO storageDTO;
    private Storage storageDB;
    private User user;

    @Before
    public void setup() throws ClassNotFoundException, NoSuchMethodException {
        user = new User();
        user.setId(2);
        Product product = new Product();
        product.setUser(user);
        product.setId(1);
        Timestamp date = new Timestamp(System.currentTimeMillis());
        storage = new Storage(user, product, 5, date);
        storageDB = new Storage(user, product, 4, date);
        storageDTO = new StorageDTO();
        storageDTO.setAmount(5);
    }

    @Test
    public void testGetByUserId_ShouldReturnListOfStorage() {
        final List<Storage> storages = Arrays.asList(storage, storage);
        when(storageDAO.getByUserID(user.getId())).thenReturn(storages);

        assertEquals(storages, storageService.getByUserId(user.getId()));
        verify(storageDAO).getByUserID(user.getId());
    }

    @Test
    public void testGetByProductId_ShouldReturnStorage() {
        final int productId = 2;
        when(storageDAO.getByProductID(productId)).thenReturn(storage);

        assertEquals(storage, storageService.getByProductId(productId));
        verify(storageDAO).getByProductID(productId);
    }

    @Test
    public void testInsert() {
        storageService.insert(storage);
        verify(storageDAO).insert(storage);
    }

    @Test
    public void testInsert_ShouldNotInsert() {
        storageService.insert(null);
        verify(storageDAO, never()).insert(any());
    }

    @Test
    public void testInsertInParts() {
        final int productId = 2;
        final int userId = 2;
        final int amount = 2;

        storageService.insert(userId, productId, amount);
        verify(storageDAO).insertInParts(userId, productId, amount);
    }

    @Test
    public void testUpdate_ShouldInsertInShoppingList() {
        storage.setAmount(1);
        storage.setEndDate(null);
        when(storageDAO.getByProductID(anyInt())).thenReturn(storageDB);

        storageService.update(storage);

        verify(storageDAO).getByProductID(storage.getProduct().getId());
        verify(storageDAO).updateAmount(storage);
        verify(historyService).insert(any());
        verify(shoppingListService).insert(any());
    }

    @Test
    public void testUpdate_ShouldUpdateWithoutDate() {
        storage.setAmount(7);
        when(storageDAO.getByProductID(anyInt())).thenReturn(storageDB);

        storageService.update(storage);

        verify(storageDAO).getByProductID(storage.getProduct().getId());
        verify(storageDAO).update(storage);
        verify(historyService).insert(any());
        verify(shoppingListService, never()).insert(any());
    }

    @Test
    public void testUpdate_ShouldNotBeExecuted() {
        storage.setAmount(-2);

        storageService.update(storage);
        verifyZeroInteractions(storageDAO);
        verify(historyService, never()).insert(any());
        verify(shoppingListService, never()).insert(any());
    }

    @Test
    public void testUpdateWithDTO_ShouldInsertInShoppingList() {
        storageDTO.setAmount(1);
        when(storageDAO.getByProductID(anyInt())).thenReturn(storageDB);

        storageService.update(storageDTO);

        verify(storageDAO).getByProductID(storageDTO.getProductId());
        verify(storageDAO).updateAmount(storageDB);
        verify(historyService).insert(any());
        verify(shoppingListService).insert(any());
    }

    @Test
    public void testUpdateWithDTO_ShouldNotInsertInShoppingList() {
        storageDTO.setAmount(7);
        when(storageDAO.getByProductID(anyInt())).thenReturn(storageDB);

        storageService.update(storageDTO);

        verify(storageDAO).getByProductID(storageDTO.getProductId());
        verify(storageDAO).updateAmount(storageDB);
        verify(historyService).insert(any());
        verify(shoppingListService, never()).insert(any());
    }

    @Test
    public void testUpdateWithDTO_ShouldNotBeExecuted() {
        storageDTO.setAmount(-1);

        storageService.update(storageDTO);
        verifyZeroInteractions(storageDAO);
        verify(historyService, never()).insert(any());
        verify(shoppingListService, never()).insert(any());
    }

    @Test
    public void testDelete() {
        storageService.delete(storage);
        verify(storageDAO).delete(storage);
    }

    @Test
    public void testDelete_ShouldNotBeExecuted() {
        storageService.delete(null);
        verify(storageDAO, never()).delete(any());
    }
}

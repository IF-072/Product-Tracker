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

import static org.junit.Assert.assertTrue;
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
    @Mock
    private MessageService messageService;
    @Mock
    private UserService userService;
    @InjectMocks
    private StorageService storageService;

    private Storage storage;
    private StorageDTO storageDTO;
    private Storage storageDB;
    private Product product;
    private User user;
    private Timestamp date;

    @Before
    public void setup() throws ClassNotFoundException, NoSuchMethodException {
        user = new User();
        user.setId(2);
        product = new Product();
        product.setUser(user);
        product.setId(1);
        date = new Timestamp(System.currentTimeMillis());
        storage = new Storage(user, product, 5, date);
        storageDB = new Storage(user, product, 4, date);
        storageDTO = new StorageDTO();
        storageDTO.setAmount(5);
    }

    @Test
    public void testGetByUserId_ShouldReturnListOfStorage() {
        List<Storage> storages = Arrays.asList(storage, storage);
        when(storageDAO.getByUserID(user.getId())).thenReturn(storages);

        assertTrue(storages.equals(storageService.getByUserId(user.getId())));
        verify(storageDAO).getByUserID(user.getId());
    }

    @Test
    public void testGetByProductId_ShouldReturnStorage() {
        int productId = 2;
        when(storageDAO.getByProductID(productId)).thenReturn(storage);

        assertTrue(storage.equals(storageService.getByProductId(productId)));
        verify(storageDAO).getByProductID(productId);
    }

    @Test
    public void testInsert() {
        storageService.insert(storage);
        verify(storageDAO).insert(storage);
    }

    @Test
    public void testInsertInParts() {
        int productId = 2;
        int userId = 2;
        int amount = 2;

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

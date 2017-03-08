package com.softserve.if072.restservice.service;

import com.softserve.if072.common.model.Product;
import com.softserve.if072.common.model.Storage;
import com.softserve.if072.common.model.User;
import com.softserve.if072.common.model.dto.StorageDTO;
import com.softserve.if072.restservice.dao.mybatisdao.StorageDAO;
import com.softserve.if072.restservice.exception.DataNotFoundException;
import org.junit.Before;
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
    @InjectMocks
    private StorageService storageService;

    private Storage storage;
    private StorageDTO storageDTO;
    private Storage storageDB;
    private Product product;
    private User user;
    private Date date;

    @Before
    public void setup() throws ClassNotFoundException, NoSuchMethodException {
        user = new User();
        user.setId(2);
        product = new Product();
        product.setUser(user);
        product.setId(1);
        date = new Date(System.currentTimeMillis());
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
        verify(storageDAO, times(1)).getByUserID(user.getId());
    }

    @Test(expected = DataNotFoundException.class)
    public void testGetByUserId_ShouldThrowException() throws Exception {
        when(storageDAO.getByUserID(user.getId())).thenReturn(null);
        storageService.getByUserId(user.getId());
        verify(storageDAO, times(1)).getByUserID(user.getId());
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
        when(storageDAO.getByProductID(anyInt())).thenReturn(storageDB);

        storageService.update(storage);

        verify(storageDAO, times(1)).getByProductID(storage.getProduct().getId());
        verify(storageDAO, times(1)).update(storage);
        verify(historyService, times(0)).insert(any());
        verify(shoppingListService, times(0)).insert(any());
    }

    @Test
    public void testUpdate_ShouldInsertInShoppingList() {
        storage.setAmount(1);
        storage.setEndDate(null);
        when(storageDAO.getByProductID(anyInt())).thenReturn(storageDB);

        storageService.update(storage);

        verify(storageDAO, times(1)).getByProductID(storage.getProduct().getId());
        verify(storageDAO, times(1)).updateAmount(storage);
        verify(historyService, times(1)).insert(any());
        verify(shoppingListService, times(1)).insert(any());
    }

    @Test
    public void testUpdate_ShouldNotBeExecuted() {
        storage.setAmount(-1);

        storageService.update(storage);
        verify(storageDAO, times(0)).getByProductID(anyInt());
        verify(storageDAO, times(0)).updateAmount(storage);
        verify(storageDAO, times(0)).update(storage);
        verify(historyService, times(0)).insert(any());
        verify(shoppingListService, times(0)).insert(any());
    }

    @Test
    public void testUpdateWithDto_ShouldNotInsertInShoppingList() {
        when(storageDAO.getByProductID(anyInt())).thenReturn(storageDB);
        storageService.update(storageDTO);

        verify(storageDAO, times(1)).getByProductID(storageDTO.getProductId());
        verify(storageDAO, times(1)).updateAmount(storageDB);
        verify(historyService, times(0)).insert(any());
        verify(shoppingListService, times(0)).insert(any());
    }

    @Test
    public void testUpdateWithDTO_ShouldInsertInShoppingList() {
        storageDTO.setAmount(1);
        when(storageDAO.getByProductID(anyInt())).thenReturn(storageDB);

        storageService.update(storageDTO);

        verify(storageDAO, times(1)).getByProductID(storageDTO.getProductId());
        verify(storageDAO, times(1)).updateAmount(storageDB);
        verify(historyService, times(1)).insert(any());
        verify(shoppingListService, times(1)).insert(any());
    }

    @Test
    public void testUpdateWithDTO_ShouldNotBeExecuted() {
        storageDTO.setAmount(-1);

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

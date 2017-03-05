package com.softserve.if072.restservice.service;

import com.softserve.if072.common.model.Storage;
import com.softserve.if072.restservice.dao.mybatisdao.StorageDAO;
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
    @InjectMocks
    private StorageService storageService;

    @Before
    public void setup() {

    }

    @Test
    public void testGetByUserId_ShouldReturnListOfStorage() {
        int userId = 2;
        List<Storage> storages = Arrays.asList(storage, storage);
        when(storageDAO.getByUserID(userId)).thenReturn(storages);

        assertTrue(storages.equals(storageService.getByUserId(userId)));
        verify(storageDAO, times(1)).getByUserID(userId);
    }

    @Test(expected = DataNotFoundException.class)
    public void testGetByUserId_ShouldThrowExceprion() throws Exception {
        int userId = 2;
        when(storageDAO.getByUserID(userId)).thenReturn(null);
        storageService.getByUserId(userId);
        verify(storageDAO, times(1)).getByUserID(userId);
    }

    @Test
    public void testGetByProductId_ShouldReturnListOfStorage() {
        int productId = 2;
        when(storageDAO.getByProductID(productId)).thenReturn(storage);

        assertTrue(storage.equals(storageService.getByProductId(productId)));
        verify(storageDAO, times(1)).getByProductID(productId);
    }

}

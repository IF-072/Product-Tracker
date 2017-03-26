package com.softserve.if072.mvcapp.service;

import com.softserve.if072.common.model.Product;
import com.softserve.if072.common.model.Storage;
import com.softserve.if072.common.model.User;
import com.softserve.if072.common.model.dto.StorageDTO;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * The StoragePageServiceTest class is used to test
 * StoragePageServiceTest class methods.
 *
 * @author Roman Dyndyn
 */
@RunWith(MockitoJUnitRunner.class)
public class StoragePageServiceTest {

    @Mock
    private RestTemplate restTemplate;
    @Mock
    private ShoppingListService shoppingListService;
    @InjectMocks
    private StoragePageService storagePageService;
    private Storage storage;

    @Before
    public void setup() {
        storage = new Storage(new User(), new Product(), 2, null);
    }

    @Test
    public void getStorages(){
        final int userId = 2;
        final List<Storage> storages = Arrays.asList(storage, storage);
        when(restTemplate.getForObject(anyString(), eq(List.class))).thenReturn(storages);

        assertEquals(storages, storagePageService.getStorages(userId));
        verify(restTemplate).getForObject(anyString(), eq(List.class));
    }

    @Test
    public void updateAmount(){
        final StorageDTO storageDTO = new StorageDTO();
        storagePageService.updateAmount(storageDTO);
        verify(restTemplate).put(anyString(), eq(storageDTO));
    }

    @Test
    public void addProductToShoppingList_ShouldInsert(){
        final User user = new User();
        final int productId = 2;
        storagePageService.addProductToShoppingList(productId);
        verify(shoppingListService).addProductToShoppingList(productId);
    }

    @Test
    public void addProductToShoppingList_ShouldNotInsert(){
        final User user = new User();
        final int productId = 0;
        storagePageService.addProductToShoppingList(productId);
        verify(shoppingListService, never()).addProductToShoppingList(productId);
    }
}

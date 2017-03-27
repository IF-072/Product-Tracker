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

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
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
    @Mock
    private AnalyticsService analyticsService;
    @Mock
    private MessageService messageService;
    @InjectMocks
    private StoragePageService storagePageService;
    private Storage storage;
    private final String insert = "storage.insertInList";
    private final String update = "storage.update";
    private final String locale = "en";
    private StorageDTO storageDTO;
    private Timestamp timestamp;

    @Before
    public void setup() {
        storage = new Storage(new User(), new Product(), 2, null);
        storageDTO = new StorageDTO();
        timestamp = new Timestamp(System.currentTimeMillis());
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
        when(restTemplate.postForObject(anyString(), eq(storageDTO), eq(Timestamp.class))).thenReturn(timestamp);

        assertEquals(new SimpleDateFormat("yyyy/MM/dd").format(timestamp),
                storagePageService.updateAmount(storageDTO, locale));

        verify(restTemplate).postForObject(anyString(), eq(storageDTO), eq(Timestamp.class));
        verify(messageService).broadcast(eq(update), eq(locale), anyInt(), any(), any());
        verify(messageService).broadcast(eq(insert), eq(locale), anyInt(), any());
    }

    @Test
    public void updateAmount_ShouldNotBroadcastInsert(){
        storageDTO.setAmount(2);
        when(restTemplate.postForObject(anyString(), eq(storageDTO), eq(Timestamp.class))).thenReturn(timestamp);

        assertEquals(new SimpleDateFormat("yyyy/MM/dd").format(timestamp),
                storagePageService.updateAmount(storageDTO, locale));

        verify(restTemplate).postForObject(anyString(), eq(storageDTO), eq(Timestamp.class));
        verify(messageService).broadcast(eq(update), eq(locale), anyInt(), any(), any());
        verify(messageService, never()).broadcast(eq(insert), eq(locale), anyInt(), any());
    }

    @Test
    public void addProductToShoppingList_ShouldInsert(){
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

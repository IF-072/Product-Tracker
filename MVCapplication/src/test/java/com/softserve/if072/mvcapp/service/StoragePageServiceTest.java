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
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.core.ParameterizedTypeReference;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.mock;
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

    @Before
    public void setup() {
        storage = new Storage(new User(), new Product(), 2, null);
        storageDTO = new StorageDTO();
    }

    @Test
    public void getStorages() {
        final int userId = 2;
        final List<Storage> storages = Arrays.asList(storage, storage);
        final ResponseEntity responseEntity = mock(ResponseEntity.class);
        when(responseEntity.getBody()).thenReturn(storages);
        when(restTemplate.exchange(anyString(), eq(HttpMethod.GET), isNull(),
                eq(new ParameterizedTypeReference<List<Storage>>() {
                }))).thenReturn(responseEntity);

        assertEquals(storages, storagePageService.getStorages(userId));
        verify(restTemplate).exchange(anyString(), eq(HttpMethod.GET), isNull(),
                eq(new ParameterizedTypeReference<List<Storage>>() {
                }));
    }

    @Test
    public void updateAmount() {
        final Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        storageDTO.setAmount(1);
        storageDTO.setPreviousAmount(4);
        when(restTemplate.postForObject(anyString(), eq(storageDTO), eq(Timestamp.class))).thenReturn(timestamp);

        assertEquals(new SimpleDateFormat("yyyy/MM/dd").format(timestamp),
                storagePageService.updateAmount(storageDTO, locale));

        verify(restTemplate).postForObject(anyString(), eq(storageDTO), eq(Timestamp.class));
        verify(messageService).broadcast(eq(update), eq(locale), anyInt(), any(), any());
        verify(messageService).broadcast(eq(insert), eq(locale), anyInt(), any());
    }

    @Test
    public void updateAmount_ShouldNotBroadcastInsert() {
        storageDTO.setAmount(2);
        when(restTemplate.postForObject(anyString(), eq(storageDTO), eq(Timestamp.class))).thenReturn(null);

        assertEquals("----------",
                storagePageService.updateAmount(storageDTO, locale));

        verify(restTemplate).postForObject(anyString(), eq(storageDTO), eq(Timestamp.class));
        verify(messageService).broadcast(eq(update), eq(locale), anyInt(), any(), any());
        verify(messageService, never()).broadcast(eq(insert), eq(locale), anyInt(), any());
    }

    @Test
    public void addProductToShoppingList_ShouldInsert() {
        final int productId = 2;
        storagePageService.addProductToShoppingList(productId);
        verify(shoppingListService).addProductToShoppingList(productId);
    }

    @Test
    public void addProductToShoppingList_ShouldNotInsert() {
        final int productId = 0;
        storagePageService.addProductToShoppingList(productId);
        verify(shoppingListService, never()).addProductToShoppingList(productId);
    }

    @Test
    public void testReviewStorage() {
        final int userId = 2;
        final ResponseEntity responseEntity = mock(ResponseEntity.class);
        storage.setEndDate(new Timestamp(System.currentTimeMillis()));
        when(responseEntity.getBody()).thenReturn(Arrays.asList(storage));
        when(restTemplate.exchange(anyString(), eq(HttpMethod.GET), isNull(),
                eq(new ParameterizedTypeReference<List<Storage>>() {
                }))).thenReturn(responseEntity);

        storagePageService.reviewStorage(locale, userId);

        verify(messageService).broadcast("storage.productEnding", locale, storage.getUser().getId(),
                storage.getProduct().getName());
    }
}

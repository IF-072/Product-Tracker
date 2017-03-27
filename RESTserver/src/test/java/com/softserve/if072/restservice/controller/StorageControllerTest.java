package com.softserve.if072.restservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.softserve.if072.common.model.Product;
import com.softserve.if072.common.model.Storage;
import com.softserve.if072.common.model.User;
import com.softserve.if072.common.model.dto.StorageDTO;
import com.softserve.if072.restservice.service.StorageService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.charset.Charset;
import java.sql.Timestamp;
import java.util.Arrays;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

/**
 * The StorageControllerTest class is used to test StorageController class methods
 *
 * @author Roman Dyndyn
 */
@RunWith(MockitoJUnitRunner.class)
public class StorageControllerTest {
    private static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType()
            , MediaType.APPLICATION_JSON.getSubtype()
            , Charset.forName("utf8"));
    @Mock
    private StorageService storageService;
    @InjectMocks
    private StorageController storageController;
    private MockMvc mockMvc;
    private User user;
    private Storage storage1;
    private Storage storage2;

    @Before
    public void setup() {
        mockMvc = standaloneSetup(storageController)
                .build();
        user = new User();
        user.setName("Roman");
        user.setId(2);
        final Product product = new Product();
        product.setId(1);
        product.setName("Product1");
        storage1 = new Storage(user, product, 5, new Timestamp(System.currentTimeMillis()));

        final Product product2 = new Product();
        product2.setId(2);
        product2.setName("Product2");
        storage2 = new Storage(user, product2, 3, new Timestamp(System.currentTimeMillis()));
    }

    @Test
    public void getByUserId_ShouldReturnAllUsersStorage() throws Exception {
        when(storageService.getByUserId(user.getId())).thenReturn(Arrays.asList(storage1, storage2));
        mockMvc.perform(get("/api/storage/{user_id}", user.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0]['user']['name']", is(user.getName())))
                .andExpect(jsonPath("$[0]['user']['id']", is(user.getId())))
                .andExpect(jsonPath("$[0]['product']['name']", is(storage1.getProduct().getName())))
                .andExpect(jsonPath("$[0]['product']['id']", is(storage1.getProduct().getId())))
                .andExpect(jsonPath("$[0]['amount']", is(storage1.getAmount())))
                .andExpect(jsonPath("$[1]['user']['name']", is(user.getName())))
                .andExpect(jsonPath("$[1]['user']['id']", is(user.getId())))
                .andExpect(jsonPath("$[1]['product']['name']", is(storage2.getProduct().getName())))
                .andExpect(jsonPath("$[1]['product']['id']", is(storage2.getProduct().getId())))
                .andExpect(jsonPath("$[1]['amount']", is(storage2.getAmount())));
        verify(storageService).getByUserId(user.getId());
        verifyZeroInteractions(storageService);
    }

    @Test
    public void getByProductId_ShouldReturnStorage() throws Exception {
        when(storageService.getByProductId(storage1.getProduct().getId())).thenReturn(storage1);
        mockMvc.perform(get("/api/storage/{userId}/{productId}", user.getId(), storage1.getProduct().getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$['user']['name']", is(user.getName())))
                .andExpect(jsonPath("$['user']['id']", is(user.getId())))
                .andExpect(jsonPath("$['product']['name']", is(storage1.getProduct().getName())))
                .andExpect(jsonPath("$['product']['id']", is(storage1.getProduct().getId())))
                .andExpect(jsonPath("$['amount']", is(storage1.getAmount())));
        verify(storageService).getByProductId(storage1.getProduct().getId());
        verifyZeroInteractions(storageService);
    }

    @Test
    public void insert_ShouldReturnStatusCreated() throws Exception {
        final ObjectMapper mapper = new ObjectMapper();
        mockMvc.perform(
                post("/api/storage/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(storage1)))
                .andExpect(status().isCreated());
        verify(storageService).insert(any());
        verifyZeroInteractions(storageService);
    }

    @Test
    public void update_ShouldReturnStatusOk() throws Exception {
        final ObjectMapper mapper = new ObjectMapper();
        mockMvc.perform(
                put("/api/storage/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(storage1)))
                .andExpect(status().isOk());
        verify(storageService).update(any(Storage.class));
        verifyZeroInteractions(storageService);
    }

    @Test
    public void updateByDTO_ShouldReturnStatusOk() throws Exception {
        final ObjectMapper mapper = new ObjectMapper();
        when(storageService.getByProductId(anyInt())).thenReturn(storage1);
        mockMvc.perform(
                post("/api/storage/dto")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(new StorageDTO())))
                .andExpect(status().isOk());
        verify(storageService).update(any(StorageDTO.class));
        verify(storageService).getByProductId(anyInt());
        verifyZeroInteractions(storageService);
    }

    @Test
    public void delete_ShouldReturnStatusOk() throws Exception {
        final ObjectMapper mapper = new ObjectMapper();
        mockMvc.perform(
                delete("/api/storage/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(storage1)))
                .andExpect(status().isOk());
        verify(storageService).delete(any(Storage.class));
        verifyZeroInteractions(storageService);
    }
}

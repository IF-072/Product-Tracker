package com.softserve.if072.restservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.softserve.if072.common.model.Action;
import com.softserve.if072.common.model.History;
import com.softserve.if072.common.model.dto.HistoryDTO;
import com.softserve.if072.restservice.service.HistoryService;
import com.softserve.if072.restservice.test.utils.HistoryBuilder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.charset.Charset;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

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
 * The HistoryControllerTest class is used to test CartController class methods
 *
 * @author Igor Kryviuk
 */
@RunWith(MockitoJUnitRunner.class)
public class HistoryControllerTest {
    private static final int FIRST_HISTORY_ITEM_ID = 1;
    private static final int SECOND_HISTORY_ITEM_ID = 2;
    private static final int FIRST_HISTORY_ITEM_AMOUNT = 5;
    private static final int SECOND_HISTORY_ITEM_AMOUNT = 3;
    private static final Timestamp FIRST_HISTORY_ITEM_USEDDATE = new Timestamp(System.currentTimeMillis());
    private static final Timestamp SECOND_HISTORY_ITEM_USEDDATE = new Timestamp(System.currentTimeMillis());
    private static final int CURRENT_USER_ID = 4;
    private static final int PRODUCT_ID = 20;
    private static final int HISTORY_ID = 32;
    private static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType()
            , MediaType.APPLICATION_JSON.getSubtype()
            , Charset.forName("utf8"));
    @Mock
    private HistoryService historyService;
    private HistoryController historyController;
    private MockMvc mockMvc;
    private ObjectMapper mapper;
    private ObjectWriter objectWriter;

    @Before
    public void setup() throws ClassNotFoundException, NoSuchMethodException {
        historyController = new HistoryController(historyService);
        mockMvc = standaloneSetup(historyController)
                .build();
        mapper = new ObjectMapper();
        objectWriter = mapper.writer().withDefaultPrettyPrinter();
    }

    @Test
    public void getByUserId_UserIdGiven_ShouldReturnNotEmptyUsersHistory() throws Exception {
        History history1 = HistoryBuilder.getDefaultHistory(FIRST_HISTORY_ITEM_ID, CURRENT_USER_ID
                , FIRST_HISTORY_ITEM_AMOUNT, FIRST_HISTORY_ITEM_USEDDATE, Action.PURCHASED);
        History history2 = HistoryBuilder.getDefaultHistory(SECOND_HISTORY_ITEM_ID, CURRENT_USER_ID
                , SECOND_HISTORY_ITEM_AMOUNT, SECOND_HISTORY_ITEM_USEDDATE, Action.USED);
        List<History> histories = Arrays.asList(history1, history2);

        when(historyService.getByUserId(anyInt())).thenReturn(histories);

        mockMvc.perform(get("/api/users/{userId}/histories", CURRENT_USER_ID))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0]['user']['name']", is(String.format("user%d", CURRENT_USER_ID))))
                .andExpect(jsonPath("$[0]['product']['name']", is(String.format("product%d", FIRST_HISTORY_ITEM_ID))))
                .andExpect(jsonPath("$[0]['amount']", is(FIRST_HISTORY_ITEM_AMOUNT)))
                .andExpect(jsonPath("$[0]['usedDate']", is(FIRST_HISTORY_ITEM_USEDDATE.getTime())))
                .andExpect(jsonPath("$[0]['action']", is("PURCHASED")))
                .andExpect(jsonPath("$[1]['user']['name']", is(String.format("user%d", CURRENT_USER_ID))))
                .andExpect(jsonPath("$[1]['product']['name']", is(String.format("product%d", SECOND_HISTORY_ITEM_ID))))
                .andExpect(jsonPath("$[1]['amount']", is(SECOND_HISTORY_ITEM_AMOUNT)))
                .andExpect(jsonPath("$[1]['usedDate']", is(SECOND_HISTORY_ITEM_USEDDATE.getTime())))
                .andExpect(jsonPath("$[1]['action']", is("USED")));

        verify(historyService).getByUserId(anyInt());
        verifyZeroInteractions(historyService);
    }

    @Test
    public void getByUserId_UserIdGiven_ShouldReturnEmptyUsersHistory() throws Exception {
        when(historyService.getByUserId(anyInt())).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/users/{userId}/histories", CURRENT_USER_ID))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", hasSize(0)));

        verify(historyService).getByUserId(anyInt());
        verifyZeroInteractions(historyService);
    }

    @Test
    public void delete_HistoryIdGiven_ShouldExecuteHistoryServiceDeleteExactlyOnce() throws Exception {
        mockMvc.perform(delete("/api/users/{userId}/histories/{historyId}", CURRENT_USER_ID, HISTORY_ID))
                .andExpect(status().isOk());

        verify(historyService).delete(HISTORY_ID);
        verifyZeroInteractions(historyService);
    }

    @Test
    public void deleteAll_UserIdGiven_ShouldExecuteHistoryServiceDeleteAllExactlyOnce() throws Exception {
        mockMvc.perform(delete("/api/users/{userId}/histories/", CURRENT_USER_ID))
                .andExpect(status().isOk());

        verify(historyService).deleteAll(CURRENT_USER_ID);
        verifyZeroInteractions(historyService);
    }

    @Test
    public void getByProductId_UserIdAndHistoryIdGiven_ShouldReturnNotEmptyUsersHistory() throws Exception {
        History history1 = HistoryBuilder.getDefaultHistory(PRODUCT_ID, CURRENT_USER_ID
                , FIRST_HISTORY_ITEM_AMOUNT, FIRST_HISTORY_ITEM_USEDDATE, Action.USED);
        History history2 = HistoryBuilder.getDefaultHistory(PRODUCT_ID, CURRENT_USER_ID
                , SECOND_HISTORY_ITEM_AMOUNT, SECOND_HISTORY_ITEM_USEDDATE, Action.PURCHASED);
        List<History> histories = Arrays.asList(history1, history2);

        when(historyService.getByProductId(CURRENT_USER_ID, PRODUCT_ID)).thenReturn(histories);

        mockMvc.perform(get("/api/users/{userId}/histories/products/{productId}", CURRENT_USER_ID, PRODUCT_ID))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0]['user']['name']", is(String.format("user%d", CURRENT_USER_ID))))
                .andExpect(jsonPath("$[0]['product']['name']", is(String.format("product%d", PRODUCT_ID))))
                .andExpect(jsonPath("$[0]['amount']", is(FIRST_HISTORY_ITEM_AMOUNT)))
                .andExpect(jsonPath("$[0]['usedDate']", is(FIRST_HISTORY_ITEM_USEDDATE.getTime())))
                .andExpect(jsonPath("$[0]['action']", is("USED")))
                .andExpect(jsonPath("$[1]['user']['name']", is(String.format("user%d", CURRENT_USER_ID))))
                .andExpect(jsonPath("$[1]['product']['name']", is(String.format("product%d", PRODUCT_ID))))
                .andExpect(jsonPath("$[1]['amount']", is(SECOND_HISTORY_ITEM_AMOUNT)))
                .andExpect(jsonPath("$[1]['usedDate']", is(SECOND_HISTORY_ITEM_USEDDATE.getTime())))
                .andExpect(jsonPath("$[1]['action']", is("PURCHASED")));

        verify(historyService).getByProductId(CURRENT_USER_ID, PRODUCT_ID);
        verifyZeroInteractions(historyService);
    }

    @Test
    public void getByProductId_UserIdAndHistoryIdGiven_ShouldReturnEmptyUsersHistory() throws Exception {
        when(historyService.getByProductId(CURRENT_USER_ID, PRODUCT_ID)).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/users/{userId}/histories/products/{productId}", CURRENT_USER_ID, PRODUCT_ID))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", hasSize(0)));

        verify(historyService).getByProductId(CURRENT_USER_ID, PRODUCT_ID);
        verifyZeroInteractions(historyService);
    }

    @Test
    public void insert_HistoryDTOGiven_ShouldExecuteHistoryServiceInsertExactlyOnce() throws Exception {
        String requestCartDTO = objectWriter.writeValueAsString(new HistoryDTO());

        mockMvc.perform(post("/api/users/{userId}/histories", CURRENT_USER_ID)
                .contentType(APPLICATION_JSON_UTF8)
                .content(requestCartDTO))
                .andExpect(status().isCreated());

        verify(historyService).insert(any());
        verifyZeroInteractions(historyService);
    }

    @Test
    public void update_HistoryDTOGiven_ShouldExecuteHistoryServiceUpdateExactlyOnce() throws Exception {
        String requestCartDTO = objectWriter.writeValueAsString(new HistoryDTO());

        mockMvc.perform(put("/api/users/{userId}/histories", CURRENT_USER_ID)
                .contentType(APPLICATION_JSON_UTF8)
                .content(requestCartDTO))
                .andExpect(status().isOk());

        verify(historyService).update(any());
        verifyZeroInteractions(historyService);
    }

}
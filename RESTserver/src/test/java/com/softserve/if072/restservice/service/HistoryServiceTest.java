package com.softserve.if072.restservice.service;

import com.softserve.if072.common.model.Action;
import com.softserve.if072.common.model.History;
import com.softserve.if072.common.model.dto.HistoryDTO;
import com.softserve.if072.restservice.dao.mybatisdao.HistoryDAO;
import com.softserve.if072.restservice.test.utils.HistoryBuilder;
import org.apache.commons.collections.CollectionUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

/**
 * The HistoryServiceTest class is used to test HistoryService class methods
 *
 * @author Igor Kryviuk
 */
@RunWith(MockitoJUnitRunner.class)
public class HistoryServiceTest {
    private static final int FIRST_HISTORY_ITEM_ID = 1;
    private static final int SECOND_HISTORY_ITEM_ID = 2;
    private static final int FIRST_HISTORY_ITEM_AMOUNT = 5;
    private static final int SECOND_HISTORY_ITEM_AMOUNT = 3;
    private static final Timestamp FIRST_HISTORY_ITEM_USEDDATE = new Timestamp(System.currentTimeMillis());
    private static final Timestamp SECOND_HISTORY_ITEM_USEDDATE = new Timestamp(System.currentTimeMillis());
    private static final int CURRENT_USER_ID = 4;
    private static final int PRODUCT_ID = 20;
    private static final int HISTORY_ID = 32;
    private HistoryService historyService;
    @Mock
    private HistoryDAO historyDAO;
    @Mock
    private ForecastService forecastService;

    @Before
    public void setup() {
        historyService = new HistoryService(historyDAO, forecastService);
    }

    @Test
    public void getByUserId_UserIdGiven_ShouldReturnNoEmptyList() throws Exception {
        History history1 = HistoryBuilder.getDefaultHistory(FIRST_HISTORY_ITEM_ID, CURRENT_USER_ID);
        History history2 = HistoryBuilder.getDefaultHistory(SECOND_HISTORY_ITEM_ID, CURRENT_USER_ID);
        List<History> histories = Arrays.asList(history1, history2);

        when(historyDAO.getByUserId(CURRENT_USER_ID)).thenReturn(histories);

        List<History> actualHistories = historyService.getByUserId(CURRENT_USER_ID);

        assertEquals(2, actualHistories.size());
        assertEquals(String.format("user%d", CURRENT_USER_ID), actualHistories.get(0).getUser().getName());
        assertEquals(String.format("product%d", SECOND_HISTORY_ITEM_ID), actualHistories.get(1).getProduct().getName());
        verify(historyDAO).getByUserId(CURRENT_USER_ID);
        verifyZeroInteractions(historyDAO);
    }

    @Test
    public void getByUserId_UserIdGiven_ShouldReturnEmptyList() throws Exception {
        when(historyDAO.getByUserId(CURRENT_USER_ID)).thenReturn(Collections.emptyList());

        List<History> actualHistories = historyService.getByUserId(CURRENT_USER_ID);

        assertTrue(CollectionUtils.isEmpty(actualHistories));
        verify(historyDAO).getByUserId(CURRENT_USER_ID);
        verifyZeroInteractions(historyDAO);
    }

    @Test
    public void delete_HistoryIdGiven_ShouldExecuteHistoryDAODeleteExactlyOnce() throws Exception {
        when(historyDAO.delete(HISTORY_ID)).thenReturn(1);

        historyService.delete(HISTORY_ID);

        verify(historyDAO).delete(HISTORY_ID);
    }

    @Test
    public void deleteAll_UserIdGiven_ShouldExecuteHistoryDAODeleteAllExactlyOnce() throws Exception {
        when(historyDAO.deleteAll(CURRENT_USER_ID)).thenReturn(1);

        historyService.deleteAll(CURRENT_USER_ID);

        verify(historyDAO).deleteAll(CURRENT_USER_ID);
        verifyZeroInteractions(historyDAO);
    }

    @Test
    public void getByProductId_UserIdAndHistoryIdGiven_ShouldReturnNotEmptyUsersHistory() throws Exception {
        History history1 = HistoryBuilder.getDefaultHistory(PRODUCT_ID, CURRENT_USER_ID
                , FIRST_HISTORY_ITEM_AMOUNT, FIRST_HISTORY_ITEM_USEDDATE, Action.USED);
        History history2 = HistoryBuilder.getDefaultHistory(PRODUCT_ID, CURRENT_USER_ID
                , SECOND_HISTORY_ITEM_AMOUNT, SECOND_HISTORY_ITEM_USEDDATE, Action.PURCHASED);
        List<History> histories = Arrays.asList(history1, history2);

        when(historyDAO.getByProductId(CURRENT_USER_ID, PRODUCT_ID)).thenReturn(histories);

        List<History> actualHistories = historyService.getByProductId(CURRENT_USER_ID, PRODUCT_ID);

        assertEquals(2, actualHistories.size());
        assertEquals(String.format("user%d", CURRENT_USER_ID), actualHistories.get(0).getUser().getName());
        assertEquals(String.format("product%d", PRODUCT_ID), actualHistories.get(0).getProduct().getName());
        assertEquals(FIRST_HISTORY_ITEM_AMOUNT, actualHistories.get(0).getAmount());
        assertEquals(FIRST_HISTORY_ITEM_USEDDATE, actualHistories.get(0).getUsedDate());
        assertEquals(Action.USED, actualHistories.get(0).getAction());
        assertEquals(String.format("user%d", CURRENT_USER_ID), actualHistories.get(1).getUser().getName());
        assertEquals(String.format("product%d", PRODUCT_ID), actualHistories.get(1).getProduct().getName());
        assertEquals(SECOND_HISTORY_ITEM_AMOUNT, actualHistories.get(1).getAmount());
        assertEquals(SECOND_HISTORY_ITEM_USEDDATE, actualHistories.get(1).getUsedDate());
        assertEquals(Action.PURCHASED, actualHistories.get(1).getAction());
        verify(historyDAO).getByProductId(CURRENT_USER_ID, PRODUCT_ID);
        verifyZeroInteractions(historyDAO);
    }

    @Test
    public void getByProductId_ProductIdGiven_ShouldReturnEmptyUsersHistory() throws Exception {
        when(historyDAO.getByProductId(CURRENT_USER_ID, PRODUCT_ID)).thenReturn(Collections.emptyList());

        List<History> actualHistories = historyService.getByProductId(CURRENT_USER_ID, PRODUCT_ID);

        assertTrue(CollectionUtils.isEmpty(actualHistories));
        verify(historyDAO).getByProductId(CURRENT_USER_ID, PRODUCT_ID);
        verifyZeroInteractions(historyDAO);
    }

    @Test
    public void insert_HistoryDTO_ShouldExecuteHistoryDAOInsertExactlyOnce() throws Exception {
        HistoryDTO historyDTO = new HistoryDTO(HISTORY_ID, CURRENT_USER_ID, PRODUCT_ID, FIRST_HISTORY_ITEM_AMOUNT
                , FIRST_HISTORY_ITEM_USEDDATE, Action.USED);

        historyService.insert(historyDTO);

        verify(historyDAO).insert(historyDTO);
        verifyZeroInteractions(historyDAO);
    }

    @Test
    public void update_HistoryDTO_ShouldExecuteHistoryDAOUpdateExactlyOnce() throws Exception {
        HistoryDTO historyDTO = new HistoryDTO(HISTORY_ID, CURRENT_USER_ID, PRODUCT_ID, FIRST_HISTORY_ITEM_AMOUNT
                , FIRST_HISTORY_ITEM_USEDDATE, Action.PURCHASED);

       when(historyDAO.update(historyDTO)).thenReturn(1);

        historyService.update(historyDTO);

        verify(historyDAO).update(historyDTO);
        verifyZeroInteractions(historyDAO);
    }
}
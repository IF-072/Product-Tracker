package com.softserve.if072.mvcapp.service;

import com.softserve.if072.common.model.History;
import com.softserve.if072.common.model.User;
import com.softserve.if072.mvcapp.test.utils.HistoryBuilder;
import org.apache.commons.collections.CollectionUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.web.client.RestTemplate;

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
    private static final int CURRENT_USER_ID = 4;
    private static final String REST_HISTORY_ULR = null;
    private static final int HISTORY_ID = 32;
    private HistoryService historyService;
    @Mock
    private RestTemplate restTemplate;
    @Mock
    private UserService userService;
    @Mock
    private AnalyticsService analyticsService;
    @Mock
    private User user;

    @Before
    public void setup() {
        historyService = new HistoryService(restTemplate, userService, analyticsService);
        when(userService.getCurrentUser()).thenReturn(user);
        when(user.getId()).thenReturn(CURRENT_USER_ID);
    }

    @Test
    public void getByUserId_ShouldReturnNoEmptyList() throws Exception {
       History history1 = HistoryBuilder.getDefaultHistory(FIRST_HISTORY_ITEM_ID, CURRENT_USER_ID);
       History history2 = HistoryBuilder.getDefaultHistory(SECOND_HISTORY_ITEM_ID, CURRENT_USER_ID);
       List<History> histories = Arrays.asList(history1, history2);

        when(restTemplate.getForObject(REST_HISTORY_ULR, List.class, CURRENT_USER_ID)).thenReturn(histories);

        List<History> actualHistories = historyService.getByUserId();

        assertEquals(2, actualHistories.size());
        assertEquals(String.format("user%d", CURRENT_USER_ID), actualHistories.get(0).getUser().getName());
        assertEquals(String.format("product%d", SECOND_HISTORY_ITEM_ID), actualHistories.get(1).getProduct().getName());
        verify(restTemplate).getForObject(REST_HISTORY_ULR, List.class, CURRENT_USER_ID);
        verifyZeroInteractions(restTemplate);
    }

    @Test
    public void getByUserId_ShouldReturnEmptyList() throws Exception {
        when(restTemplate.getForObject(REST_HISTORY_ULR, List.class, CURRENT_USER_ID)).thenReturn(Collections.emptyList());

        List<History> actualHistories = historyService.getByUserId();

        assertTrue(CollectionUtils.isEmpty(actualHistories));
        verify(restTemplate).getForObject(null, List.class, CURRENT_USER_ID);
        verifyZeroInteractions(restTemplate);
    }

    @Test
    public void deleteRecordFromHistory_HistoryIdGiven_ShouldExecuteRestTemplateDeleteExactlyOnce() throws Exception {
        historyService.deleteRecordFromHistory(HISTORY_ID);

        verify(restTemplate).delete(REST_HISTORY_ULR, CURRENT_USER_ID, HISTORY_ID);
        verifyZeroInteractions(restTemplate);
    }

    @Test
    public void deleteAllRecordsFromHistory_ShouldExecuteRestTemplateDeleteAllExactlyOnce() throws Exception {
        historyService.deleteAllRecordsFromHistory();

        verify(restTemplate).delete(REST_HISTORY_ULR, CURRENT_USER_ID);
        verifyZeroInteractions(restTemplate);
    }


}
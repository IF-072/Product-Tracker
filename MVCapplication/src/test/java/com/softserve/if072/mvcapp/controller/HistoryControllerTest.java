package com.softserve.if072.mvcapp.controller;

import com.softserve.if072.common.model.Action;
import com.softserve.if072.common.model.Category;
import com.softserve.if072.common.model.History;
import com.softserve.if072.common.model.User;
import com.softserve.if072.mvcapp.service.HistoryService;
import com.softserve.if072.mvcapp.service.PdfCreatorService;
import com.softserve.if072.mvcapp.service.ProductPageService;
import com.softserve.if072.mvcapp.service.UserService;
import com.softserve.if072.mvcapp.test.utils.HistoryBuilder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
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
    private static final int HISTORY_ID = 32;
    @Mock
    private HistoryService historyService;
    @Mock
    private ProductPageService productPageService;
    @Mock
    private UserService userService;
    @Mock
    private PdfCreatorService pdfCreatorService;
    private HistoryController historyController;
    private MockMvc mockMvc;

    @Before
    public void setup() throws ClassNotFoundException, NoSuchMethodException {
        historyController = new HistoryController(historyService, productPageService, userService, pdfCreatorService);
        mockMvc = standaloneSetup(historyController)
                .setViewResolvers(new InternalResourceViewResolver("/WEB-INF/views/history/", ".jsp"))
                .build();

        User user = new User();
        user.setId(1);
        when(userService.getCurrentUser()).thenReturn(user);
        when(productPageService.getAllCategories(anyInt())).thenReturn(new ArrayList<Category>());
    }

    @Test
    public void getHistories_ShouldReturnHistoryViewName_ModelShouldHaveAppropriateAttributes() throws Exception {
        History history1 = HistoryBuilder.getDefaultHistory(FIRST_HISTORY_ITEM_ID, CURRENT_USER_ID
                , FIRST_HISTORY_ITEM_AMOUNT, FIRST_HISTORY_ITEM_USEDDATE, Action.PURCHASED);
        History history2 = HistoryBuilder.getDefaultHistory(SECOND_HISTORY_ITEM_ID, CURRENT_USER_ID
                , SECOND_HISTORY_ITEM_AMOUNT, SECOND_HISTORY_ITEM_USEDDATE, Action.USED);
        List<History> histories = Arrays.asList(history1, history2);

        when(historyService.getByUserId()).thenReturn(histories);

        mockMvc.perform(get("/history"))
                .andExpect(status().isOk())
                .andExpect(view().name("history"))
                .andExpect(forwardedUrl("/WEB-INF/views/history/history.jsp"))
                .andExpect(model().attributeExists("histories"))
                .andExpect(model().attributeExists("categories"))
                .andExpect(model().attributeExists("historySearchDTO"))
                .andExpect(model().attribute("histories", hasSize(2)))
                .andExpect(model().attribute("histories", hasItem(
                        allOf(
                                hasProperty("id", is(FIRST_HISTORY_ITEM_ID)),
                                hasProperty("user", hasProperty("name"
                                        , is(String.format("user%d", CURRENT_USER_ID)))),
                                hasProperty("product", hasProperty("name"
                                        , is(String.format("product%d", FIRST_HISTORY_ITEM_ID)))),
                                hasProperty("amount", is(FIRST_HISTORY_ITEM_AMOUNT)),
                                hasProperty("usedDate", is(FIRST_HISTORY_ITEM_USEDDATE)),
                                hasProperty("action", is(Action.PURCHASED))
                        ))
                ))
                .andExpect(model().attribute("histories", hasItem(
                        allOf(
                                hasProperty("id", is(SECOND_HISTORY_ITEM_ID)),
                                hasProperty("user", hasProperty("name"
                                        , is(String.format("user%d", CURRENT_USER_ID)))),
                                hasProperty("product", hasProperty("name"
                                        , is(String.format("product%d", SECOND_HISTORY_ITEM_ID)))),
                                hasProperty("amount", is(SECOND_HISTORY_ITEM_AMOUNT)),
                                hasProperty("usedDate", is(SECOND_HISTORY_ITEM_USEDDATE)),
                                hasProperty("action", is(Action.USED))
                        ))
                ));

        verify(historyService).getByUserId();
        verifyZeroInteractions(historyService);
    }

    @Test
    public void searchHistories_ShouldReturnHistoryViewName_ModelShouldHaveAppropriateAttributes() throws Exception {
        History historyA = HistoryBuilder.getDefaultHistory(FIRST_HISTORY_ITEM_ID, CURRENT_USER_ID
                , FIRST_HISTORY_ITEM_AMOUNT, FIRST_HISTORY_ITEM_USEDDATE, Action.PURCHASED);
        List<History> histories = Arrays.asList(historyA);

        when(historyService.getByUserIdAndSearchParams(any())).thenReturn(histories);

        mockMvc.perform(post("/history"))
                .andExpect(status().isOk())
                .andExpect(view().name("history"))
                .andExpect(forwardedUrl("/WEB-INF/views/history/history.jsp"))
                .andExpect(model().attributeExists("histories"))
                .andExpect(model().attributeExists("categories"))
                .andExpect(model().attributeExists("historySearchDTO"))
                .andExpect(model().attribute("histories", hasSize(1)))
                .andExpect(model().attribute("histories", hasItem(
                        allOf(
                                hasProperty("id", is(FIRST_HISTORY_ITEM_ID)),
                                hasProperty("user", hasProperty("name"
                                        , is(String.format("user%d", CURRENT_USER_ID)))),
                                hasProperty("product", hasProperty("name"
                                        , is(String.format("product%d", FIRST_HISTORY_ITEM_ID)))),
                                hasProperty("amount", is(FIRST_HISTORY_ITEM_AMOUNT)),
                                hasProperty("usedDate", is(FIRST_HISTORY_ITEM_USEDDATE)),
                                hasProperty("action", is(Action.PURCHASED))
                        ))
                ));

        verify(historyService).getByUserIdAndSearchParams(any());
    }

    @Test
    public void getHistories_ShouldReturnEmptyHistoryViewName() throws Exception {
        when(historyService.getByUserId()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/history"))
                .andExpect(status().isOk())
                .andExpect(view().name("emptyHistory"))
                .andExpect(forwardedUrl("/WEB-INF/views/history/emptyHistory.jsp"))
                .andExpect(model().attributeDoesNotExist("histories"));

        verify(historyService).getByUserId();
        verifyZeroInteractions(historyService);
    }

    @Test
    public void deleteRecordFromHistory_ShouldRedirectToHistoryHandler() throws Exception {
        mockMvc.perform(get("/history/delete/{historyId}", HISTORY_ID))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/history"));
    }

    @Test
    public void deleteAllRecordsFromHistory_ShouldForwardToEmptyHistoryPage() throws Exception {
        mockMvc.perform(get("/history/delete"))
                .andExpect(status().isOk())
                .andExpect(view().name("emptyHistory"))
                .andExpect(forwardedUrl("/WEB-INF/views/history/emptyHistory.jsp"));
    }
}
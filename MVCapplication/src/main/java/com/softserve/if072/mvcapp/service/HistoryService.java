package com.softserve.if072.mvcapp.service;

import com.softserve.if072.common.model.History;
import com.softserve.if072.common.model.RestResponsePage;
import com.softserve.if072.common.model.dto.HistorySearchDTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The HistoryService class is used to hold business logic and to retrieve appropriate resources from a
 * REST server
 *
 * @author Igor Kryviuk
 */
@Service
public class HistoryService {
    private static final Logger LOGGER = LogManager.getLogger();
    private final RestTemplate restTemplate;
    private final UserService userService;
    private final AnalyticsService analyticsService;
    @Value("${application.restHistoryURL}")
    private String restHistoryURL;
    @Value("${application.restHistoryDeleteURL}")
    private String restHistoryDeleteURL;
    @Value("${history.requestReceive}")
    private String historyRequestReceive;
    @Value("${history.successfullyOperation}")
    private String historySuccessfullyOperation;
    @Value("${application.restHistoryPageURL}")
    private String historyPageURL;
    @Value("${application.restHistorySearchPageURL}")
    private String historySearchPageURL;

    public HistoryService(RestTemplate restTemplate, UserService userService, AnalyticsService analyticsService) {
        this.restTemplate = restTemplate;
        this.userService = userService;
        this.analyticsService=analyticsService;
    }

    /**
     * Make request to a REST server for retrieving all history records for current user
     *
     * @return list of history records or empty list
     */
    public List<History> getByUserId() {
        int userId = userService.getCurrentUser().getId();

        LOGGER.info(historyRequestReceive, "retrieving", "all history records", userId);
        @SuppressWarnings("unchecked")
        List<History> histories = restTemplate.getForObject(restHistoryURL, List.class, userId);
        LOGGER.info(historySuccessfullyOperation, "retrieving", "all history records", userId);
        return histories;
    }

    /**
     * Make request to a REST server for deleting a record from the history of current user
     *
     * @param historyId - history unique identifier
     */
    public void deleteRecordFromHistory(int historyId) {
        int userId = userService.getCurrentUser().getId();

        LOGGER.info(historyRequestReceive, "deleting the record with id", historyId, userId);
        restTemplate.delete(restHistoryDeleteURL, userId, historyId);
        analyticsService.cleanProductStatisticsSessionObject();
        LOGGER.info(historySuccessfullyOperation, "deleting the record with id", historyId, userId);
    }

    /**
     * Make request to a REST server for deleting all records from the history of current user
     */
    public void deleteAllRecordsFromHistory() {
        int userId = userService.getCurrentUser().getId();

        LOGGER.info(historyRequestReceive, "deleting ", "all records", userId);
        restTemplate.delete(restHistoryURL, userId);
        LOGGER.info(historySuccessfullyOperation, "deleting ", "all records", userId);
    }

    /**
     * Sends request to the REST server for retrieving all history records that match given search attributes
     *
     * @param pageNumber - number of pages
     * @param pageSize   - number of records on page
     * @return page of history records or empty page
     */
    public Page<History> getHistorySearchPage(HistorySearchDTO searchData, int pageNumber, int pageSize) {
        int userId = userService.getCurrentUser().getId();

        Map<String, String> param = new HashMap<>();
        param.put("userId", Integer.toString(userId));
        param.put("pageNumber", Integer.toString(pageNumber));
        param.put("pageSize", Integer.toString(pageSize));

        ParameterizedTypeReference<RestResponsePage<History>> responseType = new
                ParameterizedTypeReference<RestResponsePage<History>>() {
                };

        ResponseEntity<RestResponsePage<History>> historyResult = restTemplate.exchange(historySearchPageURL, HttpMethod.POST,
                new HttpEntity<>(searchData), responseType, param);

        LOGGER.info(historySuccessfullyOperation, "searching", "records", userId);
        return historyResult.getBody();
    }

}


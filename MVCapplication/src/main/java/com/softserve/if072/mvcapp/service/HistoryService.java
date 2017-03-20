package com.softserve.if072.mvcapp.service;

import com.softserve.if072.common.model.History;
import com.softserve.if072.common.model.dto.HistorySearchDTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

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
    @Value("${application.restHistoryURL}")
    private String restHistoryURL;
    @Value("${application.restHistorySearchURL}")
    private String restHistorySearchURL;
    @Value("${application.restHistoryDeleteURL}")
    private String restHistoryDeleteURL;
    @Value("${history.requestReceive}")
    private String historyRequestReceive;
    @Value("${history.successfullyOperation}")
    private String historySuccessfullyOperation;

    public HistoryService(RestTemplate restTemplate, UserService userService) {
        this.restTemplate = restTemplate;
        this.userService = userService;
    }

    /**
     * Make request to a REST server for retrieving all history records for current user
     *
     * @return list of history records or empty list
     */
    public List<History> getByUserId() {
        int userId = userService.getCurrentUser().getId();

        LOGGER.info(historyRequestReceive, "retrieving", "all history records", userId);
        List<History> histories = restTemplate.getForObject(restHistoryURL, List.class, userId);
        LOGGER.info(historySuccessfullyOperation, "retrieving", "all history records", userId);
        return histories;
    }

    /**
     * Sends request to the REST server for retrieving all history records that match given search attributes
     *
     * @return list of history records or empty list
     */
    public List<History> getByUserIdAndSearchParams(HistorySearchDTO searchDTO) {
        int userId = userService.getCurrentUser().getId();

        LOGGER.info(historyRequestReceive, "searching", "history records", userId);
        List<History> histories = restTemplate.postForObject(restHistorySearchURL, searchDTO, List.class, userId);
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
}


package com.softserve.if072.mvcapp.service;

import com.softserve.if072.common.model.History;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
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
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private UserService userService;
    @Value("${application.restHistoryURL}")
    private String restHistoryURL;
    @Value("${application.restHistoryDeleteURL}")
    private String restHistoryDeleteURL;
    @Value("${history.requestReceive}")
    private String historyRequestReceive;
    @Value("${history.successfullyOperation}")
    private String historySuccessfullyOperation;

    /**
     * Make request to a REST server for retrieving all history records for current user
     *
     * @return list of history records or empty list
     */
    public List<History> getByUserId() {
        int userId = userService.getCurrentUser().getId();
        LOGGER.info(historyRequestReceive, "retrieving", "all history records", userId);
        List<History> histories = restTemplate.getForObject(String.format(restHistoryURL, userId), List.class);
        LOGGER.info(historySuccessfullyOperation, "retrieving", "all history records", userId);
        return histories;
    }

    /**
     * Make request to a REST server for deleting a record from the history of current user
     *
     * @param historyId - history unique identifier
     */
    public void deleteHistory(int historyId) {
        int userId = userService.getCurrentUser().getId();
        LOGGER.info(historyRequestReceive, "deleting the record with id", historyId, userId);
        restTemplate.delete(String.format(restHistoryDeleteURL, userId, historyId));
        LOGGER.info(historySuccessfullyOperation, "deleting the record with id", historyId, userId);
    }
}

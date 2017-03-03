package com.softserve.if072.restservice.service;

import com.softserve.if072.common.model.History;
import com.softserve.if072.restservice.dao.mybatisdao.HistoryDAO;
import com.softserve.if072.restservice.exception.DataNotFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * The HistoryService class is used to hold business logic for working with the history DAO
 *
 * @author Igor Kryviuk
 */
@Service
public class HistoryService {
    private static final Logger LOGGER = LogManager.getLogger();
    private final HistoryDAO historyDAO;
    @Value("${history.containsRecords}")
    private String historyContainsRecords;
    @Value("${history.notFound}")
    private String historyNotFound;
    @Value("${history.SuccessfullyOperation}")
    private String historySuccessfullyOperation;

    public HistoryService(HistoryDAO historyDAO) {
        this.historyDAO = historyDAO;
    }

    /**
     * Make request to a History DAO for retrieving all history records for current user
     *
     * @param userID - current user unique identifier
     * @return list of cart records or empty list
     */
    public List<History> getByUserId(int userID) {
        List<History> histories = historyDAO.getByUserId(userID);
        LOGGER.info(historyContainsRecords, "user", userID, histories.size());
        return histories;
    }

    /**
     * Make request to a History DTO for deleting a record from the history of current user
     *
     * @param historyId - history unique identifier
     */
    public void delete(int historyId) {
        if (historyDAO.delete(historyId) == 0) {
            throw new DataNotFoundException(String.format(historyNotFound, "DELETE", historyId));
        }
        LOGGER.info(historySuccessfullyOperation, historyId, "deleted from");
    }

    public List<History> getByProductId(int userID, int productID) {
        List<History> histories = historyDAO.getByProductId(userID, productID);
        LOGGER.info(historyContainsRecords, "product", productID, histories.size());
        return histories;
    }

    public void insert(History history) {
        historyDAO.insert(history);
        LOGGER.info(historySuccessfullyOperation, history.getId(), "inserted into");
    }

    public void update(History history) {
        if (historyDAO.update(history) == 0) {
            throw new DataNotFoundException(String.format(historyNotFound, "UPDATE", history.getId()));
        }
        LOGGER.info(historySuccessfullyOperation, history.getId(), "updated in");
    }
}

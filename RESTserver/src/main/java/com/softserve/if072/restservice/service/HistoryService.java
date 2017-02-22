package com.softserve.if072.restservice.service;

import com.softserve.if072.common.model.History;
import com.softserve.if072.restservice.dao.mybatisdao.HistoryDAO;
import com.softserve.if072.restservice.exception.DataNotFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * The HistoryService class is used to hold business logic for working with the history DAO
 *
 * @author Igor Kryviuk
 */
@Service
@PropertySource("classpath:message.properties")
public class HistoryService {
    private static final Logger LOGGER = LogManager.getLogger(HistoryService.class);
    @Autowired
    private HistoryDAO historyDAO;
    @Value("${history.notFound}")
    private String historyNotFound;
    @Value("${history.found}")
    private String historyFound;
    @Value("${history.SuccessfullyOperation}")
    private String successfullyOperation;

    public List<History> getByUserId(int userID) {
        List<History> histories = historyDAO.getByUserId(userID);
        LOGGER.info(String.format(historyFound, "user", userID, histories.size()));
        return histories;
    }

    public List<History> getByProductId(int userID, int productID) {
        List<History> histories = historyDAO.getByProductId(userID, productID);
        LOGGER.info(String.format(historyFound, "product", productID, histories.size()));
        return histories;
    }

    public void insert(History history) {
        historyDAO.insert(history);
        LOGGER.info(String.format(successfullyOperation, history.getProduct().getName(), "inserted into"));
    }

    public void update(History history) throws DataNotFoundException {
        if (historyDAO.update(history) == 0) {
            throw new DataNotFoundException(String.format(historyNotFound, "invalid UPDATE operation", history.getProduct().getName()));
        }
        LOGGER.info(String.format(successfullyOperation, history.getProduct().getName(), "updated in"));
    }

    public void delete(int historyId) throws DataNotFoundException {
        if (historyDAO.delete(historyId) == 0) {
            throw new  DataNotFoundException(String.format(historyNotFound, "invalid DELETE operation", historyId));
        }
        LOGGER.info(String.format(successfullyOperation, historyId, "deleted from"));
    }
}

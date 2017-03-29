package com.softserve.if072.restservice.service;

import com.softserve.if072.common.model.History;
import com.softserve.if072.common.model.dto.HistoryDTO;
import com.softserve.if072.common.model.dto.HistorySearchDTO;
import com.softserve.if072.restservice.dao.mybatisdao.HistoryDAO;
import com.softserve.if072.restservice.exception.DataNotFoundException;
import com.softserve.if072.restservice.repository.HistoryRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * The HistoryService class is used to hold business logic for working with the history DAOInterfaces
 *
 * @author Igor Kryviuk
 */
@Service
public class HistoryService {
    private static final Logger LOGGER = LogManager.getLogger();
    private final HistoryDAO historyDAO;
    private final HistoryRepository historyRepository;
    private final ForecastService forecastService;
    @Value("${history.containsRecords}")
    private String historyContainsRecords;
    @Value("${history.notFound}")
    private String historyNotFound;
    @Value("${history.SuccessfullyOperation}")
    private String historySuccessfullyOperation;
    @Value("${history.deleteAllSuccessfullyOperation}")
    private String deleteAllSuccessfullyOperation;
    @Value("${history.containsPages}")
    private String historyContainsPages;

    @Autowired
    public HistoryService(HistoryDAO historyDAO, HistoryRepository historyRepository, ForecastService forecastService) {
        this.historyDAO = historyDAO;
        this.historyRepository = historyRepository;
        this.forecastService = forecastService;
    }

    /**
     * Make request to a History repository for retrieving all history records by given search attributes
     *
     * @param userID     - current user unique identifier
     * @param searchData DTO that contains search criterias
     * @return list of history records or empty list
     */
    public Page<History> getByUserIdAndSearchParams(int userID, HistorySearchDTO searchData, Pageable pageable) {
        Page<History> histories = historyRepository.findByMultipleParams(userID,
                searchData.getName() != null && searchData.getName().length() > 0 ? "%" + searchData.getName()+ "%" : null,
                searchData.getDescription() != null && searchData.getDescription().length() > 0 ? "%" + searchData.getDescription()+ "%" : null,
                searchData.getCategoryId(), searchData.getFromDate(), searchData.getToDate(), pageable);
        LOGGER.info(historyContainsRecords, "user", userID, histories.getTotalElements());
        return histories;
    }

    /**
     * Make request to a History DTO for deleting a record from the history of current user
     *
     * @param historyId - history unique identifier
     */
    public void delete(int historyId) {
        int productId = historyDAO.getProductIdByHistoryId(historyId);
        if (historyDAO.delete(historyId) == 0) {
            throw new DataNotFoundException(String.format(historyNotFound, "DELETE", historyId));
        }
        LOGGER.info(historySuccessfullyOperation, historyId, "deleted from");
        forecastService.setEndDate(productId);
    }

    /**
     * Make request to a History DTO for deleting all records from the history of current user
     *
     * @param userId - current user unique identifier
     */
    public void deleteAll(int userId) {
        int count = historyDAO.deleteAll(userId);
        LOGGER.info(deleteAllSuccessfullyOperation, count, userId);
    }

    /**
     * Make request to a History DAO for retrieving all history records
     * with specific product id for current user
     *
     * @param userID    - current user unique identifier
     * @param productID - product unique identifier
     * @return list of history records or empty list
     */
    public List<History> getByProductId(int userID, int productID) {
        List<History> histories = historyDAO.getByProductId(userID, productID);
        LOGGER.info(historyContainsRecords, "product", productID, histories.size());
        return histories;
    }

    public void insert(HistoryDTO historyDTO) {
        historyDAO.insert(historyDTO);
        LOGGER.info(historySuccessfullyOperation, historyDTO.getProductId(), historyDTO.getAction(), "inserted into");
    }

    public void update(HistoryDTO historyDTO) {
        if (historyDAO.update(historyDTO) == 0) {
            throw new DataNotFoundException(String.format(historyNotFound, "UPDATE", historyDTO.getId()));
        }
        LOGGER.info(historySuccessfullyOperation, historyDTO.getProductId(), historyDTO.getAction(), "updated in");
    }
}
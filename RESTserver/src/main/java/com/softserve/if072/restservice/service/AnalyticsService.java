package com.softserve.if072.restservice.service;

import com.softserve.if072.common.model.Action;
import com.softserve.if072.common.model.ProductStatistics;
import com.softserve.if072.common.model.dto.AnalyticsProductDTO;
import com.softserve.if072.common.model.dto.HistoryDTO;
import com.softserve.if072.restservice.dao.mybatisdao.HistoryDAO;
import com.softserve.if072.restservice.dao.mybatisdao.ProductDAO;
import com.softserve.if072.restservice.exception.NotEnoughDataException;
import org.apache.commons.collections.CollectionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * The AnalyticsService class is used to hold business logic for working with the ProductStatistics object
 *
 * @author Igor Kryviuk
 */
@Service
public class AnalyticsService {
    private static final Logger LOGGER = LogManager.getLogger();
    private final ForecastService forecastService;
    private final HistoryDAO historyDAO;
    private final ProductDAO productDAO;
    @Value("${analytics.SuccessfullyOperation}")
    private String successfullyOperation;
    @Value("${analytics.userHasProducts}")
    private String userHasProducts;

    public AnalyticsService(ForecastService forecastService, HistoryDAO historyDAO, ProductDAO productDAO) {
        this.forecastService = forecastService;
        this.historyDAO = historyDAO;
        this.productDAO = productDAO;
    }

    /**
     * Make request to a Product DAO for retrieving all products for current user
     *
     * @param userId - current user unique identifier
     * @return list of products or empty list
     */
    public List<AnalyticsProductDTO> getProducts(int userId) {
        List<AnalyticsProductDTO> analyticsProductDTOs = productDAO.getAllAnalyticsProductDTOs(userId);
        LOGGER.info(userHasProducts, userId, analyticsProductDTOs.size());

        return analyticsProductDTOs;
    }

    /**
     * Get ProductStatistics object form the forecastService and set additional data to it
     *
     * @param productId -  product unique identifier
     * @return ProductStatistics object
     * @throws NotEnoughDataException
     */
    public ProductStatistics getProductStatistics(int productId) throws NotEnoughDataException {
        ProductStatistics productStatistics = forecastService.getProductStatistics(productId);
        List<HistoryDTO> historyDTOs = historyDAO.getDTOByProductIdAndAction(productId, Action.PURCHASED);
        if (CollectionUtils.isNotEmpty(historyDTOs)) {
            ForecastService.setDataToArrays(historyDTOs, productStatistics, Action.PURCHASED);
            int purchasingDatesSize = productStatistics.getPurchasingProductDates().length;
            productStatistics.setLastPurchasingDate(productStatistics.getPurchasingProductDates()[purchasingDatesSize - 1]);
        }
        LOGGER.info(successfullyOperation, productId);

        return productStatistics;
    }
}

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

import java.sql.Timestamp;
import java.time.LocalDate;
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
        prepareDataForCharts(productStatistics);
        LOGGER.info(successfullyOperation, productId);

        return productStatistics;
    }

    private static void prepareDataForCharts(ProductStatistics productStatistics) {
        StringBuilder stringBuilder = new StringBuilder();

        for (double productUsingSpeed : productStatistics.getProductUsingSpeeds()) {
            stringBuilder.append(String.format("%.1f ", productUsingSpeed));
        }
        String stringData = stringBuilder.toString().trim();
        stringData = stringData.replace(',', '.');
        productStatistics.setProductUsingSpeedsForChart(stringData);
        productStatistics.setProductUsingSpeeds(null);

        convertAmountsAndDatesDataFromArrayToString(productStatistics, Action.USED);
        convertAmountsAndDatesDataFromArrayToString(productStatistics, Action.PURCHASED);
    }

    private static void convertAmountsAndDatesDataFromArrayToString(ProductStatistics productStatistics, Action action) {
        int[] amounts;
        Timestamp[] dates;
        StringBuilder stringBuilderAmounts = new StringBuilder();
        StringBuilder stringBuilderDates = new StringBuilder();
        if (action == Action.USED) {
            amounts = productStatistics.getUsingProductAmounts();
            dates = productStatistics.getUsingProductDates();
        } else {
            amounts = productStatistics.getPurchasingProductAmounts();
            dates = productStatistics.getPurchasingProductDates();
        }

        if (amounts != null) {
            int i;
            for (i = 1; i < dates.length; i++) {
                int totalAmountPerDay = amounts[i - 1];
                if (isTheSameDay(dates[i], dates[i - 1])) {
                    totalAmountPerDay += amounts[i];
                    i++;
                    while (i < dates.length) {
                        if (!isTheSameDay(dates[i], dates[i - 1])) {
                            break;
                        }
                        totalAmountPerDay += amounts[i];
                        i++;
                    }
                }

                stringBuilderAmounts.append(totalAmountPerDay);
                stringBuilderAmounts.append(" ");
                stringBuilderDates.append(dates[i - 1].toLocalDateTime().toLocalDate());
                stringBuilderDates.append(" ");
            }
            if (i == dates.length) {
                stringBuilderAmounts.append(amounts[i - 1]);
                stringBuilderAmounts.append(" ");
                stringBuilderDates.append(dates[i - 1].toLocalDateTime().toLocalDate());
                stringBuilderDates.append(" ");
            }
        }

        String stringAmounts = stringBuilderAmounts.toString().trim();
        String stringDates = stringBuilderDates.toString().trim();

        if (action == Action.USED) {
            productStatistics.setUsingProductAmountsForChart(stringAmounts);
            productStatistics.setUsingProductDatesForChart(stringDates);
            productStatistics.setUsingProductAmounts(null);
            productStatistics.setUsingProductDates(null);
        } else {
            productStatistics.setPurchasingProductAmountsForChart(stringAmounts);
            productStatistics.setPurchasingProductDatesForChart(stringDates);
            productStatistics.setPurchasingProductAmounts(null);
            productStatistics.setPurchasingProductDates(null);
        }
    }

    private static boolean isTheSameDay(Timestamp dateOne, Timestamp dateTwo) {
        LocalDate localDateOne = dateOne.toLocalDateTime().toLocalDate();
        LocalDate localDateTwo = dateTwo.toLocalDateTime().toLocalDate();

        return localDateOne.equals(localDateTwo);
    }
}


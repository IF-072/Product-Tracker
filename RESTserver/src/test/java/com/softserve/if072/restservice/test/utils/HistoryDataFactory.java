package com.softserve.if072.restservice.test.utils;

import com.softserve.if072.common.model.Action;
import com.softserve.if072.common.model.dto.HistoryDTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.round;

/**
 * The HistoryDateFactory class is used to generate different distributions for testing ForecastService class methods
 *
 * @author Igor Kryviuk
 */
public class HistoryDataFactory {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final long MILLISECONDS_PER_DAY = 24 * 60 * 60 * 1000L;
    private static final int CURRENT_USER_ID = 4;
    private static final int PRODUCT_ID = 20;

    public static List<HistoryDTO> getRegularDistribution(int size, Timestamp startDate, double dateStep, int usedAmount) {
        List<HistoryDTO> historyDTOs = new ArrayList<>();
        HistoryDTO historyDTO;
        Timestamp usedDate = startDate;
        for (int i = 0; i < size; i++) {
            usedDate = new Timestamp(usedDate.getTime() + round(dateStep * MILLISECONDS_PER_DAY));
            historyDTO = new HistoryDTO(i, CURRENT_USER_ID, PRODUCT_ID, usedAmount, usedDate, Action.USED);
            historyDTOs.add(historyDTO);
        }

        return historyDTOs;
    }

    public static List<HistoryDTO> getOutlierExistsAtTheBeginning() {
        List<HistoryDTO> historyDTOs = getIrregularDistributionWithoutOutliers();
        historyDTOs.get(1).setAmount(8);

        return historyDTOs;
    }

    public static List<HistoryDTO> getOutliersExistAtTheBeginning() {
        List<HistoryDTO> historyDTOs = getIrregularDistributionWithoutOutliers();
        historyDTOs.get(1).setAmount(8);
        historyDTOs.get(2).setAmount(25);

        return historyDTOs;
    }

    public static List<HistoryDTO> getOutlierExistsAtTheEnd() {
        List<HistoryDTO> historyDTOs = getIrregularDistributionWithoutOutliers();
        historyDTOs.get(30).setAmount(10);

        return historyDTOs;
    }

    public static List<HistoryDTO> getOutliersExistAtTheEnd() {
        List<HistoryDTO> historyDTOs = getIrregularDistributionWithoutOutliers();
        historyDTOs.get(30).setAmount(18);
        historyDTOs.get(29).setAmount(9);

        return historyDTOs;
    }

    public static List<HistoryDTO> getOutliersExistAtTheBeginningAndAtTheEnd() {
        List<HistoryDTO> historyDTOs = getIrregularDistributionWithoutOutliers();
        historyDTOs.get(30).setAmount(18);
        historyDTOs.get(1).setAmount(9);

        return historyDTOs;
    }

    public static List<HistoryDTO> getOutliersExistBetweenTheBeginningAndTheEnd() {
        List<HistoryDTO> historyDTOs = getIrregularDistributionWithoutOutliers();
        historyDTOs.get(6).setAmount(8);
        historyDTOs.get(20).setAmount(12);

        return historyDTOs;
    }

    public static List<HistoryDTO> getOutliersExistBetweenTheBeginningAndTheEndOneAfterTheOther() {
        List<HistoryDTO> historyDTOs = getIrregularDistributionWithoutOutliers();
        historyDTOs.get(20).setAmount(14);
        historyDTOs.get(21).setAmount(8);

        return historyDTOs;
    }

    public static List<HistoryDTO> getComplicatedOutliersDistribution(int distributionSize, Timestamp startDate, double dateStep, int usedProductAmount) {
        List<HistoryDTO> historyDTOs = getRegularDistribution(distributionSize, startDate, dateStep, usedProductAmount);
        historyDTOs.get(1).setAmount(10);
        historyDTOs.get(2).setAmount(10);
        historyDTOs.get(11).setAmount(10);
        historyDTOs.get(12).setAmount(10);
        historyDTOs.get(13).setAmount(10);
        historyDTOs.get(79).setAmount(10);
        historyDTOs.get(80).setAmount(10);
        historyDTOs.get(89).setAmount(10);
        historyDTOs.get(118).setAmount(10);
        historyDTOs.get(119).setAmount(10);
        historyDTOs.get(120).setAmount(10);

        return historyDTOs;
    }

    public static List<HistoryDTO> getIrregularDistributionWithoutOutliers() {
        List<HistoryDTO> historyDTOs = new ArrayList<>();
        historyDTOs.add(new HistoryDTO(0, CURRENT_USER_ID, PRODUCT_ID, 1,
                Timestamp.valueOf("2017-01-03 12:00:00"), Action.USED));
        historyDTOs.add(new HistoryDTO(1, CURRENT_USER_ID, PRODUCT_ID, 1,
                Timestamp.valueOf("2017-01-04 12:00:00"), Action.USED));
        historyDTOs.add(new HistoryDTO(2, CURRENT_USER_ID, PRODUCT_ID, 1,
                Timestamp.valueOf("2017-01-07 10:00:00"), Action.USED));
        historyDTOs.add(new HistoryDTO(3, CURRENT_USER_ID, PRODUCT_ID, 1,
                Timestamp.valueOf("2017-01-07 21:00:00"), Action.USED));
        historyDTOs.add(new HistoryDTO(4, CURRENT_USER_ID, PRODUCT_ID, 2,
                Timestamp.valueOf("2017-01-11 12:00:00"), Action.USED));
        historyDTOs.add(new HistoryDTO(5, CURRENT_USER_ID, PRODUCT_ID, 1,
                Timestamp.valueOf("2017-01-12 12:00:00"), Action.USED));
        historyDTOs.add(new HistoryDTO(6, CURRENT_USER_ID, PRODUCT_ID, 1,
                Timestamp.valueOf("2017-01-13 12:00:00"), Action.USED));
        historyDTOs.add(new HistoryDTO(7, CURRENT_USER_ID, PRODUCT_ID, 4,
                Timestamp.valueOf("2017-01-15 12:00:00"), Action.USED));
        historyDTOs.add(new HistoryDTO(8, CURRENT_USER_ID, PRODUCT_ID, 2,
                Timestamp.valueOf("2017-01-16 12:00:00"), Action.USED));
        historyDTOs.add(new HistoryDTO(9, CURRENT_USER_ID, PRODUCT_ID, 4,
                Timestamp.valueOf("2017-01-18 12:00:00"), Action.USED));
        historyDTOs.add(new HistoryDTO(10, CURRENT_USER_ID, PRODUCT_ID, 5,
                Timestamp.valueOf("2017-01-21 12:00:00"), Action.USED));
        historyDTOs.add(new HistoryDTO(11, CURRENT_USER_ID, PRODUCT_ID, 2,
                Timestamp.valueOf("2017-01-22 12:00:00"), Action.USED));
        historyDTOs.add(new HistoryDTO(12, CURRENT_USER_ID, PRODUCT_ID, 6,
                Timestamp.valueOf("2017-01-24 14:48:05"), Action.USED));
        historyDTOs.add(new HistoryDTO(13, CURRENT_USER_ID, PRODUCT_ID, 6,
                Timestamp.valueOf("2017-01-26 12:00:00"), Action.USED));
        historyDTOs.add(new HistoryDTO(14, CURRENT_USER_ID, PRODUCT_ID, 4,
                Timestamp.valueOf("2017-01-28 12:00:00"), Action.USED));
        historyDTOs.add(new HistoryDTO(15, CURRENT_USER_ID, PRODUCT_ID, 1,
                Timestamp.valueOf("2017-01-29 12:00:00"), Action.USED));
        historyDTOs.add(new HistoryDTO(16, CURRENT_USER_ID, PRODUCT_ID, 6,
                Timestamp.valueOf("2017-02-02 12:00:00"), Action.USED));
        historyDTOs.add(new HistoryDTO(17, CURRENT_USER_ID, PRODUCT_ID, 2,
                Timestamp.valueOf("2017-02-03 12:00:00"), Action.USED));
        historyDTOs.add(new HistoryDTO(18, CURRENT_USER_ID, PRODUCT_ID, 2,
                Timestamp.valueOf("2017-02-05 12:00:00"), Action.USED));
        historyDTOs.add(new HistoryDTO(19, CURRENT_USER_ID, PRODUCT_ID, 1,
                Timestamp.valueOf("2017-02-05 21:00:00"), Action.USED));
        historyDTOs.add(new HistoryDTO(20, CURRENT_USER_ID, PRODUCT_ID, 1,
                Timestamp.valueOf("2017-02-07 12:00:00"), Action.USED));
        historyDTOs.add(new HistoryDTO(21, CURRENT_USER_ID, PRODUCT_ID, 2,
                Timestamp.valueOf("2017-02-08 12:00:00"), Action.USED));
        historyDTOs.add(new HistoryDTO(22, CURRENT_USER_ID, PRODUCT_ID, 5,
                Timestamp.valueOf("2017-02-11 12:00:00"), Action.USED));
        historyDTOs.add(new HistoryDTO(23, CURRENT_USER_ID, PRODUCT_ID, 6,
                Timestamp.valueOf("2017-02-13 12:00:00"), Action.USED));
        historyDTOs.add(new HistoryDTO(24, CURRENT_USER_ID, PRODUCT_ID, 2,
                Timestamp.valueOf("2017-02-14 16:15:27"), Action.USED));
        historyDTOs.add(new HistoryDTO(25, CURRENT_USER_ID, PRODUCT_ID, 3,
                Timestamp.valueOf("2017-02-15 12:00:00"), Action.USED));
        historyDTOs.add(new HistoryDTO(26, CURRENT_USER_ID, PRODUCT_ID, 4,
                Timestamp.valueOf("2017-02-17 12:00:00"), Action.USED));
        historyDTOs.add(new HistoryDTO(27, CURRENT_USER_ID, PRODUCT_ID, 2,
                Timestamp.valueOf("2017-02-18 12:00:00"), Action.USED));
        historyDTOs.add(new HistoryDTO(28, CURRENT_USER_ID, PRODUCT_ID, 5,
                Timestamp.valueOf("2017-02-22 12:00:00"), Action.USED));
        historyDTOs.add(new HistoryDTO(29, CURRENT_USER_ID, PRODUCT_ID, 2,
                Timestamp.valueOf("2017-02-23 12:00:00"), Action.USED));
        historyDTOs.add(new HistoryDTO(30, CURRENT_USER_ID, PRODUCT_ID, 1,
                Timestamp.valueOf("2017-02-25 10:30:00"), Action.USED));

        return historyDTOs;
    }

    public static void logGeneratedDataSet(List<HistoryDTO> historyDTOs) {
        LOGGER.trace("Generated data set");
        for (int i = 0; i < historyDTOs.size(); i++) {
            HistoryDTO historyDTO = historyDTOs.get(i);
            LOGGER.trace("[ №{}; usedDate = {}; amount = {} ]", i, historyDTO.getUsedDate(), historyDTO.getAmount());
        }
    }

    public static Timestamp[] getOneOrLessDatesRecordsPerDayAboutUsing() {
        Timestamp[] dates = new Timestamp[10];
        dates[0] = Timestamp.valueOf("2017-01-03 12:00:00");
        dates[1] = Timestamp.valueOf("2017-01-04 12:00:00");
        dates[2] = Timestamp.valueOf("2017-01-07 12:00:00");
        dates[3] = Timestamp.valueOf("2017-01-08 12:00:00");
        dates[4] = Timestamp.valueOf("2017-01-10 12:00:00");
        dates[5] = Timestamp.valueOf("2017-01-11 12:00:00");
        dates[6] = Timestamp.valueOf("2017-01-13 12:00:00");
        dates[7] = Timestamp.valueOf("2017-01-15 12:00:00");
        dates[8] = Timestamp.valueOf("2017-01-18 12:00:00");
        dates[9] = Timestamp.valueOf("2017-01-19 12:00:00");

        return dates;
    }

    public static Timestamp[] getMoreThanOneRecordsPerDayAboutUsing() {
        Timestamp[] dates = new Timestamp[10];
        dates[0] = Timestamp.valueOf("2017-01-03 12:00:00");
        dates[1] = Timestamp.valueOf("2017-01-04 12:00:00");
        dates[2] = Timestamp.valueOf("2017-01-04 22:00:00");
        dates[3] = Timestamp.valueOf("2017-01-08 12:00:00");
        dates[4] = Timestamp.valueOf("2017-01-10 12:00:00");
        dates[5] = Timestamp.valueOf("2017-01-11 12:00:00");
        dates[6] = Timestamp.valueOf("2017-01-11 14:00:30");
        dates[7] = Timestamp.valueOf("2017-01-11 17:00:00");
        dates[8] = Timestamp.valueOf("2017-01-18 12:00:00");
        dates[9] = Timestamp.valueOf("2017-01-19 12:00:00");

        return dates;
    }

    public static List<HistoryDTO> getHistoryDTOListWithMoreThanOneRecordsPerDayAboutPurchasing() {
        int userId = 4;
        int productId = 20;
        List<HistoryDTO> historyDTOs = new ArrayList<>();
        historyDTOs.add(new HistoryDTO(0, userId, productId, 3, Timestamp.valueOf("2017-01-03 12:00:00"), Action.PURCHASED));
        historyDTOs.add(new HistoryDTO(1, userId, productId, 4, Timestamp.valueOf("2017-01-03 12:00:00"), Action.PURCHASED));
        historyDTOs.add(new HistoryDTO(2, userId, productId, 7, Timestamp.valueOf("2017-01-03 12:00:00"), Action.PURCHASED));
        historyDTOs.add(new HistoryDTO(3, userId, productId, 2, Timestamp.valueOf("2017-01-04 12:00:00"), Action.PURCHASED));
        historyDTOs.add(new HistoryDTO(4, userId, productId, 1, Timestamp.valueOf("2017-01-05 12:00:00"), Action.PURCHASED));
        historyDTOs.add(new HistoryDTO(5, userId, productId, 2, Timestamp.valueOf("2017-01-05 12:00:00"), Action.PURCHASED));
        historyDTOs.add(new HistoryDTO(6, userId, productId, 4, Timestamp.valueOf("2017-01-06 12:00:00"), Action.PURCHASED));
        historyDTOs.add(new HistoryDTO(7, userId, productId, 6, Timestamp.valueOf("2017-01-09 12:00:00"), Action.PURCHASED));
        historyDTOs.add(new HistoryDTO(8, userId, productId, 6, Timestamp.valueOf("2017-01-10 12:00:00"), Action.PURCHASED));
        historyDTOs.add(new HistoryDTO(9, userId, productId, 3, Timestamp.valueOf("2017-01-11 12:00:00"), Action.PURCHASED));
        historyDTOs.add(new HistoryDTO(10, userId, productId, 6, Timestamp.valueOf("2017-01-13 12:00:00"), Action.PURCHASED));
        historyDTOs.add(new HistoryDTO(11, userId, productId, 5, Timestamp.valueOf("2017-01-14 12:00:00"), Action.PURCHASED));
        historyDTOs.add(new HistoryDTO(12, userId, productId, 4, Timestamp.valueOf("2017-01-17 12:00:00"), Action.PURCHASED));
        historyDTOs.add(new HistoryDTO(13, userId, productId, 3, Timestamp.valueOf("2017-01-17 12:00:00"), Action.PURCHASED));
        historyDTOs.add(new HistoryDTO(14, userId, productId, 4, Timestamp.valueOf("2017-01-17 12:00:00"), Action.PURCHASED));

        return historyDTOs;
    }
}


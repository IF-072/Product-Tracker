package com.softserve.if072.restservice.service;

import com.softserve.if072.common.model.Action;
import com.softserve.if072.common.model.ProductStatistics;
import com.softserve.if072.common.model.dto.HistoryDTO;
import com.softserve.if072.restservice.dao.mybatisdao.HistoryDAO;
import com.softserve.if072.restservice.dao.mybatisdao.ProductDAO;
import com.softserve.if072.restservice.exception.NotEnoughDataException;
import com.softserve.if072.restservice.test.utils.HistoryDataFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Collections;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.text.IsEmptyString.isEmptyOrNullString;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.when;

/**
 * The AnalyticsServiceTest class is used to test AnalyticsService class methods
 *
 * @author Igor Kryviuk
 */
@RunWith(MockitoJUnitRunner.class)
public class AnalyticsServiceTest {
    private static final int PRODUCT_ID = 20;
    private ProductStatistics productStatistics;
    @Mock
    private ForecastService forecastService;
    @Mock
    private NotEnoughDataException notEnoughDataException;
    @Mock
    private HistoryDAO historyDAO;
    @Mock
    private ProductDAO productDAO;
    @InjectMocks
    private AnalyticsService analyticsService;

    @Before
    public void setup() {
        productStatistics = new ProductStatistics();
        productStatistics.setProductUsingSpeeds(new double[]{1.0, 0.39774976751894375, 3.998519067012218, 0.44727442149402075,
                0.6, 0.5, 0.3333333333333333, 5.0, 0.6666666666666666});
    }

    /*
    * Testing strategy for getProductStatistics method:
    *  Partition the inputs as follows:
    *       NotEnoughDataException will be thrown;
    *       there are no records about purchasing the product;
    *       there are one or less records about using the product per day;
    *       there are more than one records about using the product per day;
    *       there are more than one records about purchasing the product per day
    *       product using speeds should be correct convert form array to string;
    *       product using amounts should be correct convert form array to string;
    *       product using dates should be correct convert form array to string;
    *       product purchasing amounts should be correct convert form array to string;
    *       product purchasing dates should be correct convert form array to string.
    *
    */

    /*
    * covers:   NotEnoughDataException will be thrown.
    */
    @Test(expected = NotEnoughDataException.class)
    public void getProductStatistics_ShouldThrowException() throws Exception {
        when(forecastService.getProductStatistics(PRODUCT_ID)).thenThrow(notEnoughDataException);
        analyticsService.getProductStatistics(PRODUCT_ID);
    }

    /*
    * covers:   there are no records about purchasing the product;
    *           product using speeds should be correct convert form array to string;
    *           there are one or less records about using the product per day;
    *           product using amounts should be correct convert form array to string;
    *           product using dates should be correct convert form array to string.
    */
    @Test
    public void getProductStatistics_NoRecordsAboutPurchasingGiven_ShouldReturnExpectedResults() throws Exception {
        String expectedProductUsingSpeedsForChart = "1.0 0.4 4.0 0.4 0.6 0.5 0.3 5.0 0.7";
        String expectedUsingProductAmountsForChart = "4 4 3 5 4 8 4 7 5 3";
        String expectedUsingProductDatesForChart = "2017-01-03 2017-01-04 2017-01-07 2017-01-08 2017-01-10" +
                " 2017-01-11 2017-01-13 2017-01-15 2017-01-18 2017-01-19";

        productStatistics.setUsingProductAmounts(new int[]{4, 4, 3, 5, 4, 8, 4, 7, 5, 3});
        productStatistics.setUsingProductDates(HistoryDataFactory.getOneOrLessDatesRecordsPerDayAboutUsing());

        when(forecastService.getProductStatistics(PRODUCT_ID)).thenReturn(productStatistics);
        when(historyDAO.getDTOByProductIdAndAction(PRODUCT_ID, Action.PURCHASED)).thenReturn(Collections.emptyList());

        analyticsService.getProductStatistics(PRODUCT_ID);

        assertThat(productStatistics.getProductUsingSpeedsForChart(), equalTo(expectedProductUsingSpeedsForChart));
        assertThat(productStatistics.getUsingProductAmountsForChart(), equalTo(expectedUsingProductAmountsForChart));
        assertThat(productStatistics.getUsingProductDatesForChart(), equalTo(expectedUsingProductDatesForChart));
        assertThat(productStatistics.getPurchasingProductAmountsForChart(), isEmptyOrNullString());
        assertThat(productStatistics.getPurchasingProductDatesForChart(), isEmptyOrNullString());

        assertNull(productStatistics.getProductUsingSpeeds());
        assertNull(productStatistics.getUsingProductAmounts());
        assertNull(productStatistics.getUsingProductDates());
        assertNull(productStatistics.getPurchasingProductAmounts());
        assertNull(productStatistics.getPurchasingProductDates());
    }

    /*
    * covers:   there are more than one records about using the product per day;
    *           there are more than one records about purchasing the product per day
    *           product using amounts should be correct convert form array to string;
    *           product using dates should be correct convert form array to string;
    *           product using purchasing amounts should be correct convert form array to string;
    *           product using purchasing dates should be correct convert form array to string.
    */
    @Test
    public void getProductStatistics_MoreThanOneRecordsAboutUsingAndPurchasingPerDayGiven_ShouldReturnExpectedResults() throws Exception {
        String expectedUsingProductAmountsForChart = "4 7 5 5 18 5 3";
        String expectedUsingProductDatesForChart = "2017-01-03 2017-01-04 2017-01-08 2017-01-10" +
                " 2017-01-11 2017-01-18 2017-01-19";
        String expectedPurchasingProductAmountsForChart = "14 2 3 4 6 6 3 6 5 11";
        String expectedPurchasingProductDatesForChart = "2017-01-03 2017-01-04 2017-01-05 2017-01-06" +
                " 2017-01-09 2017-01-10 2017-01-11 2017-01-13 2017-01-14 2017-01-17";

        productStatistics.setUsingProductAmounts(new int[]{4, 3, 4, 5, 5, 6, 5, 7, 5, 3});
        productStatistics.setUsingProductDates(HistoryDataFactory.getMoreThanOneRecordsPerDayAboutUsing());
        List<HistoryDTO> historyDTOs = HistoryDataFactory.getHistoryDTOListWithMoreThanOneRecordsPerDayAboutPurchasing();

        when(forecastService.getProductStatistics(PRODUCT_ID)).thenReturn(productStatistics);
        when(historyDAO.getDTOByProductIdAndAction(PRODUCT_ID, Action.PURCHASED)).thenReturn(historyDTOs);

        analyticsService.getProductStatistics(PRODUCT_ID);

        assertThat(productStatistics.getUsingProductAmountsForChart(), equalTo(expectedUsingProductAmountsForChart));
        assertThat(productStatistics.getUsingProductDatesForChart(), equalTo(expectedUsingProductDatesForChart));
        assertThat(productStatistics.getPurchasingProductAmountsForChart(), equalTo(expectedPurchasingProductAmountsForChart));
        assertThat(productStatistics.getPurchasingProductDatesForChart(), equalTo(expectedPurchasingProductDatesForChart));

        assertNull(productStatistics.getUsingProductAmounts());
        assertNull(productStatistics.getUsingProductDates());
        assertNull(productStatistics.getPurchasingProductAmounts());
        assertNull(productStatistics.getPurchasingProductDates());
    }
}
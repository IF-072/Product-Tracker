package com.softserve.if072.restservice.service;

import com.softserve.if072.common.model.Action;
import com.softserve.if072.common.model.Product;
import com.softserve.if072.common.model.ProductStatistics;
import com.softserve.if072.common.model.Storage;
import com.softserve.if072.common.model.dto.AnalyticsProductDTO;
import com.softserve.if072.common.model.dto.HistoryDTO;
import com.softserve.if072.restservice.dao.mybatisdao.HistoryDAO;
import com.softserve.if072.restservice.dao.mybatisdao.ProductDAO;
import com.softserve.if072.restservice.dao.mybatisdao.StorageDAO;
import com.softserve.if072.restservice.exception.NotEnoughDataException;
import com.softserve.if072.restservice.test.utils.HistoryDataFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static java.lang.Math.round;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.number.IsCloseTo.closeTo;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.when;

/**
 * The ForecastServiceTest class is used to test ForecastService class methods
 *
 * @author Igor Kryviuk
 */
@RunWith(MockitoJUnitRunner.class)
public class ForecastServiceTest {
    private static final Timestamp START_DATE = Timestamp.valueOf("2017-01-01 12:00:00");
    private static final long MILLISECONDS_PER_DAY = 24 * 60 * 60 * 1000;
    private static final int PRODUCT_ID = 20;
    private static final int STORAGE_PRODUCT_AMOUNT = 10;
    private static final int DISTRIBUTION_SIZE = 31;
    private static final String PRODUCT_NAME = "Product" + PRODUCT_ID;
    private static final String PRODUCT_UNIT = "кг";
    @Mock
    private StorageDAO storageDAO;
    @Mock
    private ProductService productService;
    @Mock
    private HistoryDAO historyDAO;
    @Mock
    private ProductDAO productDAO;
    @Mock
    private Storage storage;
    @Mock
    private Product product;
    @Mock
    private AnalyticsProductDTO analyticsProductDTO;
    @InjectMocks
    private ForecastService forecastService;

    @Before
    public void setup() {
        when(storageDAO.getByProductID(PRODUCT_ID)).thenReturn(storage);
        when(storage.getAmount()).thenReturn(STORAGE_PRODUCT_AMOUNT);
        when(productService.getProductById(PRODUCT_ID)).thenReturn(product);
        when(productDAO.getAnalyticsProductDTO(PRODUCT_ID)).thenReturn(analyticsProductDTO);
        when(analyticsProductDTO.getName()).thenReturn(PRODUCT_NAME);
        when(analyticsProductDTO.getUnit()).thenReturn(PRODUCT_UNIT);
    }

    /*
    * Testing strategy for getProductStatistics method:
    *  Partition the inputs as follows:
    *      List<HistoryDTO>.size() is empty;
    *      List<HistoryDTO>.size() < 2;
    *      List<HistoryDTO>.size() = 2;
    *      List<HistoryDTO>.size() > 2;
    *      there in no product in in the user's storage
    *      there is 0 units of a product in the user's storage
    *      given more than one record about product used per day;
    *      given regular data distribution;
    *      given irregular data distribution;
    *      outliers don't exist;
    *      outlier exists at the beginning of the data set;
    *      outliers exist at the beginning of the data set more than one time one after the other;
    *      outlier exists at the end of the date set;
    *      outliers exist at the end of the date set more than one time one after the other;
    *      outliers exist at the beginning and at the end of the data set;
    *      outliers exist between the beginning and the end of the data set more than one time;
    *      outliers exist between the beginning and the end of the data set more than one time one after the other;
    *      complicated outliers distribution.
    *
    * Testing strategy for getProductStatistics method:
    *   Partition the inputs as follows:
    *        getProductStatistics method doesn't throw NotEnoughDataException;
    *        getProductStatistics method throws NotEnoughDataException.
    *
    */

    /*
    * covers:   List<HistoryDTO>.size() is empty.
    */
    @Test(expected = NotEnoughDataException.class)
    public void getProductStatistics_EmptyListGiven_ShouldThrowException() throws Exception {
        when(historyDAO.getDTOByProductIdAndAction(PRODUCT_ID, Action.USED)).thenReturn(Collections.emptyList());

        forecastService.getProductStatistics(PRODUCT_ID);
    }

    /*
    * covers:   List<HistoryDTO>.size() < 2.
    */
    @Test(expected = NotEnoughDataException.class)
    public void getProductStatistics_ListSizeLessTwoGiven_ShouldThrowException() throws Exception {
        List<HistoryDTO> historyDTOs = Arrays.asList(new HistoryDTO());

        when(historyDAO.getDTOByProductIdAndAction(PRODUCT_ID, Action.USED)).thenReturn(historyDTOs);

        forecastService.getProductStatistics(PRODUCT_ID);
    }

    /*
    * covers:  List<HistoryDTO>.size() = 2.
    */
    @Test
    public void getProductStatistics_ListSizeEqualTwoGiven_ShouldReturnExpectedResults() throws Exception {
        double dateStep = 5;
        int distributionSize = 2;
        int usedProductAmount = 7;
        double expectedMean = usedProductAmount / dateStep;
        double expectedMin = expectedMean;
        double expectedMax = expectedMean;
        double expectedCount = distributionSize - 1;
        double expectedRange = 0;
        double expectedStandardDeviation = 0;
        double expectedLowerThreeSigmaLimit = expectedMean;
        double expectedUpperThreeSigmaBound = expectedMean;
        Timestamp expectedEndDate = new Timestamp(System.currentTimeMillis() +
                round(STORAGE_PRODUCT_AMOUNT / expectedMean * MILLISECONDS_PER_DAY));
        List<HistoryDTO> historyDTOs = HistoryDataFactory.getRegularDistribution(distributionSize,
                START_DATE, dateStep, usedProductAmount);
        HistoryDataFactory.logGeneratedDataSet(historyDTOs);

        when(historyDAO.getDTOByProductIdAndAction(PRODUCT_ID, Action.USED)).thenReturn(historyDTOs);

        ProductStatistics productStatistics = forecastService.getProductStatistics(PRODUCT_ID);
        Timestamp actualEndDate = productStatistics.getEndDate();

        assertThat(productStatistics.getMean(), equalTo(expectedMean));
        assertThat(productStatistics.getMin(), equalTo(expectedMin));
        assertThat(productStatistics.getMax(), equalTo(expectedMax));
        assertThat(productStatistics.getCount(), equalTo(expectedCount));
        assertThat(productStatistics.getRange(), equalTo(expectedRange));
        assertThat(productStatistics.getStandardDeviation(), equalTo(expectedStandardDeviation));
        assertThat(productStatistics.getLowerThreeSigmaLimit(), equalTo(expectedLowerThreeSigmaLimit));
        assertThat(productStatistics.getUpperThreeSigmaLimit(), equalTo(expectedUpperThreeSigmaBound));
        assertThat(isTheSameDay(expectedEndDate, actualEndDate), equalTo(true));
    }

    /*
    * covers:  List<HistoryDTO>.size() = 2;
    *          there is no product in in the user's storage.
    */
    @Test
    public void getProductStatistics_ListSizeEqualTwoAndNoProductInStorageGiven_ShouldReturnExpectedResults() throws Exception {
        double dateStep = 2;
        int distributionSize = 2;
        int usedProductAmount = 7;
        double expectedMean = usedProductAmount / dateStep;
        double expectedMin = expectedMean;
        double expectedMax = expectedMean;
        double expectedCount = distributionSize - 1;
        double expectedRange = 0;
        double expectedStandardDeviation = 0;
        double expectedLowerThreeSigmaLimit = expectedMean;
        double expectedUpperThreeSigmaBound = expectedMean;

        List<HistoryDTO> historyDTOs = HistoryDataFactory.getRegularDistribution(distributionSize,
                START_DATE, dateStep, usedProductAmount);
        HistoryDataFactory.logGeneratedDataSet(historyDTOs);

        when(historyDAO.getDTOByProductIdAndAction(PRODUCT_ID, Action.USED)).thenReturn(historyDTOs);
        when(storageDAO.getByProductID(PRODUCT_ID)).thenReturn(null);

        ProductStatistics productStatistics = forecastService.getProductStatistics(PRODUCT_ID);
        Timestamp actualEndDate = productStatistics.getEndDate();

        assertThat(productStatistics.getMean(), equalTo(expectedMean));
        assertThat(productStatistics.getMin(), equalTo(expectedMin));
        assertThat(productStatistics.getMax(), equalTo(expectedMax));
        assertThat(productStatistics.getCount(), equalTo(expectedCount));
        assertThat(productStatistics.getRange(), equalTo(expectedRange));
        assertThat(productStatistics.getStandardDeviation(), equalTo(expectedStandardDeviation));
        assertThat(productStatistics.getLowerThreeSigmaLimit(), equalTo(expectedLowerThreeSigmaLimit));
        assertThat(productStatistics.getUpperThreeSigmaLimit(), equalTo(expectedUpperThreeSigmaBound));
        assertNull(actualEndDate);
    }

    /*
    * covers:   List<HistoryDTO>.size() > 2;
    *           given regular data distribution.
    */
    @Test
    public void getProductStatistics_RegularDistributionGiven_ShouldReturnExpectedResults() throws Exception {
        double dateStep = 2;
        int usedProductAmount = 6;
        double expectedMean = usedProductAmount / dateStep;
        double expectedMin = expectedMean;
        double expectedMax = expectedMean;
        double expectedCount = DISTRIBUTION_SIZE - 1;
        double expectedRange = 0;
        double expectedStandardDeviation = 0;
        double expectedLowerThreeSigmaLimit = expectedMean;
        double expectedUpperThreeSigmaBound = expectedMean;
        Timestamp expectedEndDate = new Timestamp(System.currentTimeMillis() +
                round(STORAGE_PRODUCT_AMOUNT / expectedMean * MILLISECONDS_PER_DAY));
        List<HistoryDTO> historyDTOs = HistoryDataFactory.getRegularDistribution(DISTRIBUTION_SIZE,
                START_DATE, dateStep, usedProductAmount);
        HistoryDataFactory.logGeneratedDataSet(historyDTOs);

        when(historyDAO.getDTOByProductIdAndAction(PRODUCT_ID, Action.USED)).thenReturn(historyDTOs);

        ProductStatistics productStatistics = forecastService.getProductStatistics(PRODUCT_ID);
        Timestamp actualEndDate = productStatistics.getEndDate();

        assertThat(productStatistics.getMean(), equalTo(expectedMean));
        assertThat(productStatistics.getMin(), equalTo(expectedMin));
        assertThat(productStatistics.getMax(), equalTo(expectedMax));
        assertThat(productStatistics.getCount(), equalTo(expectedCount));
        assertThat(productStatistics.getRange(), equalTo(expectedRange));
        assertThat(productStatistics.getStandardDeviation(), equalTo(expectedStandardDeviation));
        assertThat(productStatistics.getLowerThreeSigmaLimit(), equalTo(expectedLowerThreeSigmaLimit));
        assertThat(productStatistics.getUpperThreeSigmaLimit(), equalTo(expectedUpperThreeSigmaBound));
        assertThat(isTheSameDay(expectedEndDate, actualEndDate), equalTo(true));
    }

    /*
    * covers:   List<HistoryDTO>.size() > 2;
    *           given regular data distribution;
    *           given more than one record about product used per day.
    */
    @Test
    public void getProductStatistics_MoreThanOneRecordPerDayAndRegularDistributionGiven_ShouldReturnExpectedResults()
            throws Exception {
        double dateStep = 0.3;
        int usedProductAmount = 5;
        double expectedMean = usedProductAmount / dateStep;
        double expectedMin = expectedMean;
        double expectedMax = expectedMean;
        double expectedCount = DISTRIBUTION_SIZE - 1;
        double expectedRange = 0;
        double expectedStandardDeviation = 0;
        double expectedLowerThreeSigmaLimit = expectedMean;
        double expectedUpperThreeSigmaBound = expectedMean;
        Timestamp expectedEndDate = new Timestamp(System.currentTimeMillis() +
                round(STORAGE_PRODUCT_AMOUNT / expectedMean * MILLISECONDS_PER_DAY));
        List<HistoryDTO> historyDTOs = HistoryDataFactory.getRegularDistribution(DISTRIBUTION_SIZE,
                START_DATE, dateStep, usedProductAmount);
        HistoryDataFactory.logGeneratedDataSet(historyDTOs);

        when(historyDAO.getDTOByProductIdAndAction(PRODUCT_ID, Action.USED)).thenReturn(historyDTOs);

        ProductStatistics productStatistics = forecastService.getProductStatistics(PRODUCT_ID);
        Timestamp actualEndDate = productStatistics.getEndDate();

        assertThat(productStatistics.getMean(), equalTo(expectedMean));
        assertThat(productStatistics.getMin(), equalTo(expectedMin));
        assertThat(productStatistics.getMax(), equalTo(expectedMax));
        assertThat(productStatistics.getCount(), equalTo(expectedCount));
        assertThat(productStatistics.getRange(), equalTo(expectedRange));
        assertThat(productStatistics.getStandardDeviation(), equalTo(expectedStandardDeviation));
        assertThat(productStatistics.getLowerThreeSigmaLimit(), equalTo(expectedLowerThreeSigmaLimit));
        assertThat(productStatistics.getUpperThreeSigmaLimit(), equalTo(expectedUpperThreeSigmaBound));
        assertThat(isTheSameDay(expectedEndDate, actualEndDate), equalTo(true));
    }

    /*
    * covers:   List<HistoryDTO>.size() > 2;
    *           given irregular data distribution;
    *           outliers don't exist.
    */
    @Test
    public void getProductStatistics_IrregularDistributionGiven_ShouldReturnExpectedResults()
            throws Exception {
        double expectedMean = 1.744;
        double expectedMin = 0.343;
        double expectedMax = 3.647;
        double expectedCount = DISTRIBUTION_SIZE - 1;
        double expectedRange = expectedMax - expectedMin;
        double expectedStandardDeviation = 0.818;
        double expectedLowerThreeSigmaLimit = -0.710;
        double expectedUpperThreeSigmaBound = 4.198;
        Timestamp expectedEndDate = new Timestamp(System.currentTimeMillis() +
                round(STORAGE_PRODUCT_AMOUNT / expectedMean * MILLISECONDS_PER_DAY));
        List<HistoryDTO> historyDTOs = HistoryDataFactory.getIrregularDistributionWithoutOutliers();
        HistoryDataFactory.logGeneratedDataSet(historyDTOs);

        when(historyDAO.getDTOByProductIdAndAction(PRODUCT_ID, Action.USED)).thenReturn(historyDTOs);

        ProductStatistics productStatistics = forecastService.getProductStatistics(PRODUCT_ID);
        Timestamp actualEndDate = productStatistics.getEndDate();

        assertThat(productStatistics.getMean(), closeTo(expectedMean, 0.001));
        assertThat(productStatistics.getMin(), closeTo(expectedMin, 0.001));
        assertThat(productStatistics.getMax(), closeTo(expectedMax, 0.001));
        assertThat(productStatistics.getCount(), equalTo(expectedCount));
        assertThat(productStatistics.getRange(), closeTo(expectedRange, 0.001));
        assertThat(productStatistics.getStandardDeviation(), closeTo(expectedStandardDeviation, 0.001));
        assertThat(productStatistics.getLowerThreeSigmaLimit(), closeTo(expectedLowerThreeSigmaLimit, 0.001));
        assertThat(productStatistics.getUpperThreeSigmaLimit(), closeTo(expectedUpperThreeSigmaBound, 0.001));
        assertThat(isTheSameDay(expectedEndDate, actualEndDate), equalTo(true));
    }

    /*
    * covers:   List<HistoryDTO>.size() > 2;
    *           given irregular data distribution;
    *           outlier exists at the beginning of the data set.
    */
    @Test
    public void getProductStatistics_OutlierExistsAtTheBeginningGiven_ShouldReturnExpectedResults()
            throws Exception {
        double expectedMean = 1.77;
        double expectedMin = 0.343;
        double expectedMax = 3.647;
        double expectedCount = DISTRIBUTION_SIZE - 2;
        double expectedRange = expectedMax - expectedMin;
        double expectedStandardDeviation = 0.820;
        double expectedLowerThreeSigmaLimit = -0.691;
        double expectedUpperThreeSigmaBound = 4.230;
        Timestamp expectedEndDate = new Timestamp(System.currentTimeMillis() +
                round(STORAGE_PRODUCT_AMOUNT / expectedMean * MILLISECONDS_PER_DAY));
        List<HistoryDTO> historyDTOs = HistoryDataFactory.getOutlierExistsAtTheBeginning();
        HistoryDataFactory.logGeneratedDataSet(historyDTOs);

        when(historyDAO.getDTOByProductIdAndAction(PRODUCT_ID, Action.USED)).thenReturn(historyDTOs);

        ProductStatistics productStatistics = forecastService.getProductStatistics(PRODUCT_ID);
        Timestamp actualEndDate = productStatistics.getEndDate();

        assertThat(productStatistics.getMean(), closeTo(expectedMean, 0.001));
        assertThat(productStatistics.getMin(), closeTo(expectedMin, 0.001));
        assertThat(productStatistics.getMax(), closeTo(expectedMax, 0.001));
        assertThat(productStatistics.getCount(), equalTo(expectedCount));
        assertThat(productStatistics.getRange(), closeTo(expectedRange, 0.001));
        assertThat(productStatistics.getStandardDeviation(), closeTo(expectedStandardDeviation, 0.001));
        assertThat(productStatistics.getLowerThreeSigmaLimit(), closeTo(expectedLowerThreeSigmaLimit, 0.001));
        assertThat(productStatistics.getUpperThreeSigmaLimit(), closeTo(expectedUpperThreeSigmaBound, 0.001));
        assertThat(isTheSameDay(expectedEndDate, actualEndDate), equalTo(true));
    }

    /*
    * covers:   List<HistoryDTO>.size() > 2;
    *           given irregular data distribution;
    *           outliers exist at the beginning of the date set more than one time one after the other.
    */
    @Test
    public void getProductStatistics_OutliersExistAtTheBeginningGiven_ShouldReturnExpectedResults()
            throws Exception {
        double expectedMean = 1.821;
        double expectedMin = 0.516;
        double expectedMax = 3.647;
        double expectedCount = DISTRIBUTION_SIZE - 3;
        double expectedRange = expectedMax - expectedMin;
        double expectedStandardDeviation = 0.787;
        double expectedLowerThreeSigmaLimit = -0.541;
        double expectedUpperThreeSigmaBound = 4.182;
        Timestamp expectedEndDate = new Timestamp(System.currentTimeMillis() +
                round(STORAGE_PRODUCT_AMOUNT / expectedMean * MILLISECONDS_PER_DAY));
        List<HistoryDTO> historyDTOs = HistoryDataFactory.getOutliersExistAtTheBeginning();
        HistoryDataFactory.logGeneratedDataSet(historyDTOs);

        when(historyDAO.getDTOByProductIdAndAction(PRODUCT_ID, Action.USED)).thenReturn(historyDTOs);

        ProductStatistics productStatistics = forecastService.getProductStatistics(PRODUCT_ID);
        Timestamp actualEndDate = productStatistics.getEndDate();

        assertThat(productStatistics.getMean(), closeTo(expectedMean, 0.001));
        assertThat(productStatistics.getMin(), closeTo(expectedMin, 0.001));
        assertThat(productStatistics.getMax(), closeTo(expectedMax, 0.001));
        assertThat(productStatistics.getCount(), equalTo(expectedCount));
        assertThat(productStatistics.getRange(), closeTo(expectedRange, 0.001));
        assertThat(productStatistics.getStandardDeviation(), closeTo(expectedStandardDeviation, 0.001));
        assertThat(productStatistics.getLowerThreeSigmaLimit(), closeTo(expectedLowerThreeSigmaLimit, 0.001));
        assertThat(productStatistics.getUpperThreeSigmaLimit(), closeTo(expectedUpperThreeSigmaBound, 0.001));
        assertThat(isTheSameDay(expectedEndDate, actualEndDate), equalTo(true));
    }

    /*
    * covers:   List<HistoryDTO>.size() > 2;
    *           given irregular data distribution;
    *           outlier exists at the end of the date set.
    */
    @Test
    public void getProductStatistics_OutlierExistsAtTheEndGiven_ShouldReturnExpectedResults()
            throws Exception {
        double expectedMean = 1.786;
        double expectedMin = 0.343;
        double expectedMax = 3.647;
        double expectedCount = DISTRIBUTION_SIZE - 2;
        double expectedRange = expectedMax - expectedMin;
        double expectedStandardDeviation = 0.799;
        double expectedLowerThreeSigmaLimit = -0.609;
        double expectedUpperThreeSigmaBound = 4.182;
        Timestamp expectedEndDate = new Timestamp(System.currentTimeMillis() +
                round(STORAGE_PRODUCT_AMOUNT / expectedMean * MILLISECONDS_PER_DAY));
        List<HistoryDTO> historyDTOs = HistoryDataFactory.getOutlierExistsAtTheEnd();
        HistoryDataFactory.logGeneratedDataSet(historyDTOs);

        when(historyDAO.getDTOByProductIdAndAction(PRODUCT_ID, Action.USED)).thenReturn(historyDTOs);

        ProductStatistics productStatistics = forecastService.getProductStatistics(PRODUCT_ID);
        Timestamp actualEndDate = productStatistics.getEndDate();

        assertThat(productStatistics.getMean(), closeTo(expectedMean, 0.001));
        assertThat(productStatistics.getMin(), closeTo(expectedMin, 0.001));
        assertThat(productStatistics.getMax(), closeTo(expectedMax, 0.001));
        assertThat(productStatistics.getCount(), equalTo(expectedCount));
        assertThat(productStatistics.getRange(), closeTo(expectedRange, 0.001));
        assertThat(productStatistics.getStandardDeviation(), closeTo(expectedStandardDeviation, 0.001));
        assertThat(productStatistics.getLowerThreeSigmaLimit(), closeTo(expectedLowerThreeSigmaLimit, 0.001));
        assertThat(productStatistics.getUpperThreeSigmaLimit(), closeTo(expectedUpperThreeSigmaBound, 0.001));
        assertThat(isTheSameDay(expectedEndDate, actualEndDate), equalTo(true));
    }

    /*
    * covers:   List<HistoryDTO>.size() > 2;
    *           given irregular data distribution;
    *           outliers exist at the end of the date set more than one time one after the other.
    */
    @Test
    public void getProductStatistics_OutliersExistAtTheEndGiven_ShouldReturnExpectedResults()
            throws Exception {
        double expectedMean = 1.779;
        double expectedMin = 0.343;
        double expectedMax = 3.647;
        double expectedCount = DISTRIBUTION_SIZE - 3;
        double expectedRange = expectedMax - expectedMin;
        double expectedStandardDeviation = 0.812;
        double expectedLowerThreeSigmaLimit = -0.658;
        double expectedUpperThreeSigmaBound = 4.215;
        Timestamp expectedEndDate = new Timestamp(System.currentTimeMillis() +
                round(STORAGE_PRODUCT_AMOUNT / expectedMean * MILLISECONDS_PER_DAY));
        List<HistoryDTO> historyDTOs = HistoryDataFactory.getOutliersExistAtTheEnd();
        HistoryDataFactory.logGeneratedDataSet(historyDTOs);

        when(historyDAO.getDTOByProductIdAndAction(PRODUCT_ID, Action.USED)).thenReturn(historyDTOs);

        ProductStatistics productStatistics = forecastService.getProductStatistics(PRODUCT_ID);
        Timestamp actualEndDate = productStatistics.getEndDate();

        assertThat(productStatistics.getMean(), closeTo(expectedMean, 0.001));
        assertThat(productStatistics.getMin(), closeTo(expectedMin, 0.001));
        assertThat(productStatistics.getMax(), closeTo(expectedMax, 0.001));
        assertThat(productStatistics.getCount(), equalTo(expectedCount));
        assertThat(productStatistics.getRange(), closeTo(expectedRange, 0.001));
        assertThat(productStatistics.getStandardDeviation(), closeTo(expectedStandardDeviation, 0.001));
        assertThat(productStatistics.getLowerThreeSigmaLimit(), closeTo(expectedLowerThreeSigmaLimit, 0.001));
        assertThat(productStatistics.getUpperThreeSigmaLimit(), closeTo(expectedUpperThreeSigmaBound, 0.001));
        assertThat(isTheSameDay(expectedEndDate, actualEndDate), equalTo(true));
    }

    /*
    * covers:   List<HistoryDTO>.size() > 2;
    *           given irregular data distribution;
    *           outliers exist at the beginning and at the end of the data set.
    */
    @Test
    public void getProductStatistics_OutliersExistAtTheBeginningAndAtTheEndGiven_ShouldReturnExpectedResults()
            throws Exception {
        double expectedMean = 1.815;
        double expectedMin = 0.343;
        double expectedMax = 3.647;
        double expectedCount = DISTRIBUTION_SIZE - 3;
        double expectedRange = expectedMax - expectedMin;
        double expectedStandardDeviation = 0.799;
        double expectedLowerThreeSigmaLimit = -0.581;
        double expectedUpperThreeSigmaBound = 4.210;
        Timestamp expectedEndDate = new Timestamp(System.currentTimeMillis() +
                round(STORAGE_PRODUCT_AMOUNT / expectedMean * MILLISECONDS_PER_DAY));
        List<HistoryDTO> historyDTOs = HistoryDataFactory.getOutliersExistAtTheBeginningAndAtTheEnd();
        HistoryDataFactory.logGeneratedDataSet(historyDTOs);

        when(historyDAO.getDTOByProductIdAndAction(PRODUCT_ID, Action.USED)).thenReturn(historyDTOs);

        ProductStatistics productStatistics = forecastService.getProductStatistics(PRODUCT_ID);
        Timestamp actualEndDate = productStatistics.getEndDate();

        assertThat(productStatistics.getMean(), closeTo(expectedMean, 0.001));
        assertThat(productStatistics.getMin(), closeTo(expectedMin, 0.001));
        assertThat(productStatistics.getMax(), closeTo(expectedMax, 0.001));
        assertThat(productStatistics.getCount(), equalTo(expectedCount));
        assertThat(productStatistics.getRange(), closeTo(expectedRange, 0.001));
        assertThat(productStatistics.getStandardDeviation(), closeTo(expectedStandardDeviation, 0.001));
        assertThat(productStatistics.getLowerThreeSigmaLimit(), closeTo(expectedLowerThreeSigmaLimit, 0.001));
        assertThat(productStatistics.getUpperThreeSigmaLimit(), closeTo(expectedUpperThreeSigmaBound, 0.001));
        assertThat(isTheSameDay(expectedEndDate, actualEndDate), equalTo(true));
    }

    /*
    * covers:   List<HistoryDTO>.size() > 2;
    *           given irregular data distribution;
    *           outliers exist between the beginning and the end of the data set more than one time.
    */
    @Test
    public void getProductStatistics_OutliersExistBetweenTheBeginningAndTheEndGiven_ShouldReturnExpectedResults()
            throws Exception {
        double expectedMean = 1.818;
        double expectedMin = 0.343;
        double expectedMax = 3.647;
        double expectedCount = DISTRIBUTION_SIZE - 1;
        double expectedRange = expectedMax - expectedMin;
        double expectedStandardDeviation = 0.784;
        double expectedLowerThreeSigmaLimit = -0.534;
        double expectedUpperThreeSigmaBound = 4.171;
        Timestamp expectedEndDate = new Timestamp(System.currentTimeMillis() +
                round(STORAGE_PRODUCT_AMOUNT / expectedMean * MILLISECONDS_PER_DAY));
        List<HistoryDTO> historyDTOs = HistoryDataFactory.getOutliersExistBetweenTheBeginningAndTheEnd();
        HistoryDataFactory.logGeneratedDataSet(historyDTOs);

        when(historyDAO.getDTOByProductIdAndAction(PRODUCT_ID, Action.USED)).thenReturn(historyDTOs);

        ProductStatistics productStatistics = forecastService.getProductStatistics(PRODUCT_ID);
        Timestamp actualEndDate = productStatistics.getEndDate();

        assertThat(productStatistics.getMean(), closeTo(expectedMean, 0.001));
        assertThat(productStatistics.getMin(), closeTo(expectedMin, 0.001));
        assertThat(productStatistics.getMax(), closeTo(expectedMax, 0.001));
        assertThat(productStatistics.getCount(), equalTo(expectedCount));
        assertThat(productStatistics.getRange(), closeTo(expectedRange, 0.001));
        assertThat(productStatistics.getStandardDeviation(), closeTo(expectedStandardDeviation, 0.001));
        assertThat(productStatistics.getLowerThreeSigmaLimit(), closeTo(expectedLowerThreeSigmaLimit, 0.001));
        assertThat(productStatistics.getUpperThreeSigmaLimit(), closeTo(expectedUpperThreeSigmaBound, 0.001));
        assertThat(isTheSameDay(expectedEndDate, actualEndDate), equalTo(true));
    }

    /*
    * covers:   List<HistoryDTO>.size() > 2;
    *           given irregular data distribution;
    *           outliers exist between the beginning and the end of the data set more than one time one after the other.
    */
    @Test
    public void getProductStatistics_OutliersExistBetweenTheBeginningAndTheEndOneAfterTheOtherGiven_ShouldReturnExpectedResults()
            throws Exception {
        double expectedMean = 1.801;
        double expectedMin = 0.343;
        double expectedMax = 3.647;
        double expectedCount = DISTRIBUTION_SIZE - 1;
        double expectedRange = expectedMax - expectedMin;
        double expectedStandardDeviation = 0.795;
        double expectedLowerThreeSigmaLimit = -0.584;
        double expectedUpperThreeSigmaBound = 4.187;
        Timestamp expectedEndDate = new Timestamp(System.currentTimeMillis() +
                round(STORAGE_PRODUCT_AMOUNT / expectedMean * MILLISECONDS_PER_DAY));
        List<HistoryDTO> historyDTOs = HistoryDataFactory.getOutliersExistBetweenTheBeginningAndTheEndOneAfterTheOther();
        HistoryDataFactory.logGeneratedDataSet(historyDTOs);

        when(historyDAO.getDTOByProductIdAndAction(PRODUCT_ID, Action.USED)).thenReturn(historyDTOs);

        ProductStatistics productStatistics = forecastService.getProductStatistics(PRODUCT_ID);
        Timestamp actualEndDate = productStatistics.getEndDate();

        assertThat(productStatistics.getMean(), closeTo(expectedMean, 0.001));
        assertThat(productStatistics.getMin(), closeTo(expectedMin, 0.001));
        assertThat(productStatistics.getMax(), closeTo(expectedMax, 0.001));
        assertThat(productStatistics.getCount(), equalTo(expectedCount));
        assertThat(productStatistics.getRange(), closeTo(expectedRange, 0.001));
        assertThat(productStatistics.getStandardDeviation(), closeTo(expectedStandardDeviation, 0.001));
        assertThat(productStatistics.getLowerThreeSigmaLimit(), closeTo(expectedLowerThreeSigmaLimit, 0.001));
        assertThat(productStatistics.getUpperThreeSigmaLimit(), closeTo(expectedUpperThreeSigmaBound, 0.001));
        assertThat(isTheSameDay(expectedEndDate, actualEndDate), equalTo(true));
    }

    /*
    * covers:   List<HistoryDTO>.size() > 2;
    *           complicated outliers distribution.
    */
    @Test
    public void getProductStatistics_ComplicatedOutliersDistributionGiven_ShouldReturnExpectedResults() throws Exception {
        double dateStep = 2;
        int distributionSize = 121;
        int usedProductAmount = 6;
        double expectedMean = usedProductAmount / dateStep;
        double expectedMin = expectedMean;
        double expectedMax = expectedMean;
        double expectedCount = distributionSize - 6;
        double expectedRange = 0;
        double expectedStandardDeviation = 0;
        double expectedLowerThreeSigmaLimit = expectedMean;
        double expectedUpperThreeSigmaBound = expectedMean;
        Timestamp expectedEndDate = new Timestamp(System.currentTimeMillis() +
                round(STORAGE_PRODUCT_AMOUNT / expectedMean * MILLISECONDS_PER_DAY));
        List<HistoryDTO> historyDTOs = HistoryDataFactory.getComplicatedOutliersDistribution(distributionSize,
                START_DATE, dateStep, usedProductAmount);
        HistoryDataFactory.logGeneratedDataSet(historyDTOs);

        when(historyDAO.getDTOByProductIdAndAction(PRODUCT_ID, Action.USED)).thenReturn(historyDTOs);

        ProductStatistics productStatistics = forecastService.getProductStatistics(PRODUCT_ID);
        Timestamp actualEndDate = productStatistics.getEndDate();

        assertThat(productStatistics.getMean(), equalTo(expectedMean));
        assertThat(productStatistics.getMin(), equalTo(expectedMin));
        assertThat(productStatistics.getMax(), equalTo(expectedMax));
        assertThat(productStatistics.getCount(), equalTo(expectedCount));
        assertThat(productStatistics.getRange(), equalTo(expectedRange));
        assertThat(productStatistics.getStandardDeviation(), equalTo(expectedStandardDeviation));
        assertThat(productStatistics.getLowerThreeSigmaLimit(), equalTo(expectedLowerThreeSigmaLimit));
        assertThat(productStatistics.getUpperThreeSigmaLimit(), equalTo(expectedUpperThreeSigmaBound));
        assertThat(isTheSameDay(expectedEndDate, actualEndDate), equalTo(true));
    }

    /*
* covers:   List<HistoryDTO>.size() > 2;
*           complicated outliers distribution;
*           there is 0 units of a product in the user's storage.
*/
    @Test
    public void getProductStatistics_ComplicatedOutliersDistributionAndProductAmountZeroGiven_ShouldReturnExpectedResults() throws Exception {
        double dateStep = 2;
        int distributionSize = 121;
        int usedProductAmount = 6;
        double expectedMean = usedProductAmount / dateStep;
        double expectedMin = expectedMean;
        double expectedMax = expectedMean;
        double expectedCount = distributionSize - 6;
        double expectedRange = 0;
        double expectedStandardDeviation = 0;
        double expectedLowerThreeSigmaLimit = expectedMean;
        double expectedUpperThreeSigmaBound = expectedMean;
        List<HistoryDTO> historyDTOs = HistoryDataFactory.getComplicatedOutliersDistribution(distributionSize,
                START_DATE, dateStep, usedProductAmount);
        HistoryDataFactory.logGeneratedDataSet(historyDTOs);

        when(historyDAO.getDTOByProductIdAndAction(PRODUCT_ID, Action.USED)).thenReturn(historyDTOs);
        when(storage.getAmount()).thenReturn(0);

        ProductStatistics productStatistics = forecastService.getProductStatistics(PRODUCT_ID);
        Timestamp actualEndDate = productStatistics.getEndDate();

        assertThat(productStatistics.getMean(), equalTo(expectedMean));
        assertThat(productStatistics.getMin(), equalTo(expectedMin));
        assertThat(productStatistics.getMax(), equalTo(expectedMax));
        assertThat(productStatistics.getCount(), equalTo(expectedCount));
        assertThat(productStatistics.getRange(), equalTo(expectedRange));
        assertThat(productStatistics.getStandardDeviation(), equalTo(expectedStandardDeviation));
        assertThat(productStatistics.getLowerThreeSigmaLimit(), equalTo(expectedLowerThreeSigmaLimit));
        assertThat(productStatistics.getUpperThreeSigmaLimit(), equalTo(expectedUpperThreeSigmaBound));
        assertNull(actualEndDate);
    }

    /*
    * covers:   getProductStatistics method doesn't throw NotEnoughDataException.
    */
    @Test
    public void setEndDate_ShouldSetNotNullEndDate() throws NotEnoughDataException {
        List<HistoryDTO> historyDTOs = HistoryDataFactory.getIrregularDistributionWithoutOutliers();
        HistoryDataFactory.logGeneratedDataSet(historyDTOs);

        when(historyDAO.getDTOByProductIdAndAction(PRODUCT_ID, Action.USED)).thenReturn(historyDTOs);

        forecastService.setEndDate(PRODUCT_ID);
    }

    /*
    * covers:    getProductStatistics method throws NotEnoughDataException.
    */
    @Test
    public void setEndDate_ShouldSetNullEndDate() throws NotEnoughDataException {
        when(historyDAO.getDTOByProductIdAndAction(PRODUCT_ID, Action.USED)).thenReturn(Collections.emptyList());

        forecastService.setEndDate(PRODUCT_ID);
    }

    private static boolean isTheSameDay(Timestamp expectedEndDate, Timestamp actualEndDate) {
        LocalDate localDateExpectedEndDate = expectedEndDate.toLocalDateTime().toLocalDate();
        LocalDate localDateActualEndDate = actualEndDate.toLocalDateTime().toLocalDate();

        return localDateExpectedEndDate.equals(localDateActualEndDate);
    }
}

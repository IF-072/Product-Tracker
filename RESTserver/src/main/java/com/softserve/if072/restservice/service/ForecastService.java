package com.softserve.if072.restservice.service;

import com.softserve.if072.common.model.Action;
import com.softserve.if072.common.model.ProductStatistics;
import com.softserve.if072.common.model.Storage;
import com.softserve.if072.common.model.dto.AnalyticsProductDTO;
import com.softserve.if072.common.model.dto.HistoryDTO;
import com.softserve.if072.restservice.dao.mybatisdao.HistoryDAO;
import com.softserve.if072.restservice.dao.mybatisdao.ProductDAO;
import com.softserve.if072.restservice.dao.mybatisdao.StorageDAO;
import com.softserve.if072.restservice.exception.NotEnoughDataException;
import com.softserve.if072.restservice.security.authentication.AuthenticatedUserProxy;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;

import static java.lang.Math.round;

/**
 * The ForecastService class is used to calculate main descriptive statistics measures for using speed of product and
 * forecast the end date of the product
 *
 * @author Igor Kryviuk
 */
@Service
public class ForecastService {
    private static final Logger LOGGER = LogManager.getLogger();
    private final HistoryDAO historyDAO;
    private final ProductDAO productDAO;
    private final StorageDAO storageDAO;
    private final ProductService productService;
    private static final long MILLISECONDS_PER_DAY = 24 * 60 * 60 * 1000L;

    public ForecastService(HistoryDAO historyDAO, ProductDAO productDAO,
                           StorageDAO storageDAO, ProductService productService) {
        this.historyDAO = historyDAO;
        this.productDAO = productDAO;
        this.storageDAO = storageDAO;
        this.productService = productService;
    }
    /*
    * General forecasting strategy:
    * We can forecast the date of the product ending having average speed of the product using per day (or other time span)
    * and the product amount in the storage. We can also following the next steps for calculating the average speed of the
    * product using per day :
    * 1. Retrieve the data (date and amount) about the product using from the history table.
    * Since the size of the data won't be changed and we'll be working with primitives (int, double),
    * store the data in the arrays like "Timestamp[] usedDates" for the dates and
    * "int[] usedAmounts" for the amounts is the best choice.
    * 2. Calculate the time spans in milliseconds between two nearby dates in such way:
    * timeSpans[i-1]=usedDates[i].getTime()-usedDates[i-1].getTime(); (i=0, 1,..., usedDates.length). In this case,
    * a timeSpans array will be one less than a usedDates array and we should correctly map the time spans to the
    * appropriate used amounts (timeSpans[0] will be mapped to usedAmounts[1] and so on).
    * 3. Since the time spans between two nearby dates can be differ through the dates set we should transform them in to
    * the one time span like day : daysDiff[i]=timeSpans[i]/MILLISECONDS_PER_DAY.
    * 4. Calculate the speeds of the product using for each time span in the daysDiff array.
    * 5. Calculate the main descriptive statistics values for the productUsedSpeeds array : mean, min, max, count, range,
    * standard deviation, lower and upper three sigma limits.
    * 6. If standard deviation is not equals to 0, then the product used speeds set could contain outliers in the data and
    * we should detect them and deal with them. According to the theory of the normal data distribution, 99.73% of the values
    * lie within three standard deviations of the mean or within the lower and upper three sigma limits. Therefore, we can
    * convince that the data values that are beyond three standard deviations from the mean (less than the lower three sigma
    * limit or more than the upper three sigma limit) are outliers with the probability of 0.9973.
    * 7. Detect the data that are less than the lower three sigma limit or more than the upper three sigma. If there are the
    * outliers in the data set we can deal with them in two ways:
    *  - if the outliers occur at the beginning of the data set or at the end of the data set we can simply remove them (trim
    *  the data set);
    * - if the outliers occur between two certain values we can simply change them to the average of this two certain values.
    * 8. Calculate the date of the product ending using the mean from step 4 as the average speed.
    */

    /**
     * Construct the ProductStatistics object, that stores statistical information about product using
     *
     * @param productId - product unique identifier
     * @return - ProductStatistics object
     * @throws NotEnoughDataException
     */
    public ProductStatistics getProductStatistics(int productId) throws NotEnoughDataException {
        LOGGER.trace("getProductStatistics method starts to execute");
//todo uncomment;
//      isAuthorize(productId);
        List<HistoryDTO> historyDTOs = historyDAO.getDTOByProductIdAndAction(productId, Action.USED);
        if (CollectionUtils.isEmpty(historyDTOs) || historyDTOs.size() < 2) {
            LOGGER.info("List of product using size is {}. NotEnoughDataException will be thrown.", historyDTOs.size());

            String productName = productService.getProductById(productId).getName();

            throw new NotEnoughDataException(String.format("You don't have enough data records about \"%s\"" +
                    " for successful forecasting its ending date.", productName), productName);
        }

        ProductStatistics productStatistics = new ProductStatistics();
        setDataToArrays(historyDTOs, productStatistics, Action.USED);
        productStatistics.setProductUsingSpeeds(getProductUsedSpeeds(productStatistics));

        while (true) {
            productStatistics.setHasOutliers(false);
            getDescriptiveStatistics(productStatistics);

            if (productStatistics.getStandardDeviation() != 0) {
                LOGGER.debug("Standard deviation is {}. Data set could contain outliers. " +
                        "dialWithOutliers method will be called.", productStatistics.getStandardDeviation());
                dealWithOutliers(productStatistics);
            }

            if (!productStatistics.getHasOutliers()) {
                LOGGER.trace("Data set has no outliers.");
                break;
            }
            LOGGER.trace("Data set had outliers. Main statistics values will be recalculated.");
        }

        productStatistics.setProductId(productId);
        AnalyticsProductDTO analyticsProductDTO = productDAO.getAnalyticsProductDTO(productId);
        productStatistics.setProductName(analyticsProductDTO.getName());
        productStatistics.setProductUnit(analyticsProductDTO.getUnit());
        productStatistics.setLastUsingDate(historyDTOs.get(historyDTOs.size() - 1).getUsedDate());

        setEndDate(productStatistics);

        LOGGER.debug(productStatistics);
        LOGGER.trace("getProductStatistics method has successfully finished.");

        return productStatistics;
    }

    /**
     * Calculate the speed of the product using per day
     *
     * @return array with the speed of the product using per day
     */
    private double[] getProductUsedSpeeds(ProductStatistics productStatistics) {
        LOGGER.trace("getProductUsingSpeeds method starts to execute.");
        Timestamp[] usingDates = productStatistics.getUsingProductDates();
        int[] usingAmounts = productStatistics.getUsingProductAmounts();
        int usedDateSize = usingDates.length;
        double[] productUsedSpeeds = new double[usedDateSize - 1];

        for (int i = 1; i < usedDateSize; i++) {
            Timestamp currentDate = usingDates[i];
            Timestamp previousDate = usingDates[i - 1];
            double daysDiff = (double) (currentDate.getTime() - previousDate.getTime()) / MILLISECONDS_PER_DAY;
            productUsedSpeeds[i - 1] = usingAmounts[i] / daysDiff;
        }
        return productUsedSpeeds;
    }

    /**
     * Calculate main descriptive statistics measures for using speed of product
     *
     * @param productStatistics - object that stores statistical information about product using
     */
    private void getDescriptiveStatistics(ProductStatistics productStatistics) {
        LOGGER.trace("getDescriptiveStatistics method starts to execute.");
        DescriptiveStatistics descriptiveStatistics = new DescriptiveStatistics(productStatistics.getProductUsingSpeeds());
        productStatistics.setMean(descriptiveStatistics.getMean());
        productStatistics.setMin(descriptiveStatistics.getMin());
        productStatistics.setMax(descriptiveStatistics.getMax());
        productStatistics.setCount(descriptiveStatistics.getN());
        productStatistics.setRange(productStatistics.getMax() - productStatistics.getMin());
        productStatistics.setStandardDeviation(descriptiveStatistics.getStandardDeviation());
        productStatistics.setLowerThreeSigmaLimit(productStatistics.getMean() - 3 * productStatistics.getStandardDeviation());
        productStatistics.setUpperThreeSigmaLimit(productStatistics.getMean() + 3 * productStatistics.getStandardDeviation());
    }

    /**
     * Detect outliers and deal with them
     *
     * @param productStatistics - object that stores statistical information about product using
     */
    private void dealWithOutliers(ProductStatistics productStatistics) {
        LOGGER.trace("dealWithOutliers method starts to execute.");
        boolean hasOutliers = false;
        double[] productUsedSpeeds = productStatistics.getProductUsingSpeeds();
        double lowerThreeSigmaLimit = productStatistics.getLowerThreeSigmaLimit();
        double upperThreeSigmaLimit = productStatistics.getUpperThreeSigmaLimit();
        boolean isOutlierAtTheBeginningOrEnd = false;
        int leftTrimBound = 0;
        int rightTrimBound = productUsedSpeeds.length;

        /*
        * Find outliers at the beginning of the data set
        */
        for (int i = 0; i < productUsedSpeeds.length; i++) {
            if (productUsedSpeeds[i] < lowerThreeSigmaLimit || productUsedSpeeds[i] > upperThreeSigmaLimit) {
                if (i == 0) {
                    isOutlierAtTheBeginningOrEnd = true;
                    leftTrimBound++;
                    continue;
                }
                leftTrimBound++;
                continue;
            }
            LOGGER.trace("Find {} outlier(s) at the beginning of the data set.", i);
            break;
        }

        /*
        * Find outliers at the end of the data set
        */
        for (int i = productUsedSpeeds.length - 1; i >= 0; i--) {
            if (productUsedSpeeds[i] < lowerThreeSigmaLimit || productUsedSpeeds[i] > upperThreeSigmaLimit) {
                if (i == productUsedSpeeds.length - 1) {
                    isOutlierAtTheBeginningOrEnd = true;
                    rightTrimBound--;
                    continue;
                }
                rightTrimBound--;
                continue;
            }
            LOGGER.trace("Find {} outlier(s) at the end of the data set.", productUsedSpeeds.length - 1 - i);
            break;
        }

        /*
        * Trim outliers from the beginning and the end of the data set
        */
        if (isOutlierAtTheBeginningOrEnd) {
            hasOutliers = true;
            productUsedSpeeds = Arrays.copyOfRange(productUsedSpeeds, leftTrimBound, rightTrimBound);
        }

        /*
        * Find and deal with other outliers
        */
        int otherOutliersCount = 0;
        for (int i = 1; i < productUsedSpeeds.length - 1; i++) {
            if (productUsedSpeeds[i] < lowerThreeSigmaLimit || productUsedSpeeds[i] > upperThreeSigmaLimit) {
                hasOutliers = true;
                otherOutliersCount++;
                int lastOutlierIndex = i;
                while (true) {
                    if (!(productUsedSpeeds[lastOutlierIndex + 1] < lowerThreeSigmaLimit || productUsedSpeeds[lastOutlierIndex + 1] > upperThreeSigmaLimit)) {
                        break;
                    }
                    otherOutliersCount++;
                    lastOutlierIndex++;
                }
                for (int k = i; k <= lastOutlierIndex; k++) {
                    productUsedSpeeds[k] = (productUsedSpeeds[i - 1] + productUsedSpeeds[lastOutlierIndex + 1]) / 2;
                }
                LOGGER.trace("Find outlier(s) from the index {} to the index {}.", i, lastOutlierIndex);
                i = lastOutlierIndex + 1;
            }
        }
        LOGGER.trace("Find {} outlier(s) between the beginning and the end of the data set.", otherOutliersCount);

        productStatistics.setProductUsingSpeeds(productUsedSpeeds);
        productStatistics.setHasOutliers(hasOutliers);
    }

    /**
     * Calculate forecasting end date and set it to the ProductStatistics object
     *
     * @param productStatistics - object that stores statistical information about product using
     */
    private void setEndDate(ProductStatistics productStatistics) {
        LOGGER.trace("setEndDate method starts to execute.");
        double mean = productStatistics.getMean();
        Storage storage = storageDAO.getByProductID(productStatistics.getProductId());

        if (storage == null) {
            LOGGER.info("There is no \"{}\" with id {} in the user's storage.", productStatistics.getProductName(),
                    productStatistics.getProductId());
            productStatistics.setEndDate(null);

            return;
        }
        if (storage.getAmount() == 0) {
            LOGGER.info("There is 0 unit of \"{}\" with id {} in the user's storage.", productStatistics.getProductName(),
                    productStatistics.getProductId());
            productStatistics.setEndDate(null);

            return;
        }
        productStatistics.setAmountInStorage(storage.getAmount());
        int amountInStorage = productStatistics.getAmountInStorage();
        Timestamp endDate = new Timestamp(System.currentTimeMillis() + round(amountInStorage / mean * MILLISECONDS_PER_DAY));
        productStatistics.setEndDate(endDate);
        storageDAO.updateEndDate(productStatistics.getProductId(), endDate);
        LOGGER.info("The end date of the product with id {} has been successfully set in the storage.",
                productStatistics.getProductId());
    }

    /**
     * Calculate forecasting end date of the product in set it to the storage
     *
     * @param productId - product unique identifier
     */
    public void setEndDate(int productId) {
        LOGGER.trace("getEndDate method starts to execute.");
        try {
            getProductStatistics(productId);
        } catch (NotEnoughDataException e) {
            LOGGER.error(e.getMessage());
            storageDAO.updateEndDate(productId, null);
        }
    }

    /**
     * Set appropriate data
     *
     * @param historyDTOs       - list of historyDTO objects
     * @param productStatistics - object that stores statistical information about product using
     * @param action            - Action enum object with appropriate action
     */
    static void setDataToArrays(List<HistoryDTO> historyDTOs, ProductStatistics productStatistics, Action action) {
        int dataSetSize = historyDTOs.size();
        Timestamp[] dates = new Timestamp[dataSetSize];
        int[] amounts = new int[dataSetSize];
        int total = 0;
        for (int i = 0; i < dataSetSize; i++) {
            dates[i] = historyDTOs.get(i).getUsedDate();
            amounts[i] = historyDTOs.get(i).getAmount();
            total += amounts[i];
            if (action == Action.USED) {
                productStatistics.setUsingProductDates(dates);
                productStatistics.setUsingProductAmounts(amounts);
                productStatistics.setTotalUsed(total);
            } else {
                productStatistics.setPurchasingProductDates(dates);
                productStatistics.setPurchasingProductAmounts(amounts);
                productStatistics.setTotalPurchased(total);
            }
        }
    }

    private void isAuthorize(int productId) {
        int productOwnerId = productService.getUserIdByProductId(productId);
        AuthenticatedUserProxy userProxy = (AuthenticatedUserProxy) SecurityContextHolder.getContext().getAuthentication();
        int currentUserId = userProxy.getUser().getId();
        if (productOwnerId != currentUserId) {
            throw new AccessDeniedException(String.format("You don't have permission to access to the product with id %d.", productId));
        }
    }
}

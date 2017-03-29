package com.softserve.if072.restservice.service;

import com.softserve.if072.common.model.Action;
import com.softserve.if072.common.model.ShoppingList;
import com.softserve.if072.common.model.Storage;
import com.softserve.if072.common.model.dto.HistoryDTO;
import com.softserve.if072.common.model.dto.StorageDTO;
import com.softserve.if072.restservice.dao.mybatisdao.StorageDAO;
import org.apache.commons.collections.CollectionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

/**
 * The StorageService class is used to hold business
 * logic for working with the storage DAO.
 *
 * @author Roman Dyndyn
 */
@Service
public class StorageService {
    private static final Logger LOGGER = LogManager.getLogger(StorageService.class);
    private final StorageDAO storageDAO;
    private final ShoppingListService shoppingListService;
    private final HistoryService historyService;
    private final ForecastService forecastService;
    private static final int LIMIT = 1;


    @Autowired
    public StorageService(final StorageDAO storageDAO, final ShoppingListService shoppingListService,
                          final HistoryService historyService, final ForecastService forecastService) {
        this.storageDAO = storageDAO;
        this.shoppingListService = shoppingListService;
        this.historyService = historyService;
        this.forecastService = forecastService;
    }

    /**
     * Make request to a Storage DAO for retrieving
     * all storage records for current user.
     *
     * @param userId - current user unique identifier
     * @return list of storage records
     */
    public List<Storage> getByUserId(final int userId) {
        final List<Storage> list = storageDAO.getByUserID(userId);
        if (CollectionUtils.isEmpty(list)) {
            LOGGER.warn("Storage of user with id {} not found", userId);
        }
        return list;
    }

    /**
     * Make request to a Storage DAO for retrieving
     * storage record for product.
     *
     * @param productId - product unique identifier
     * @return storage record
     */
    public Storage getByProductId(final int productId) {
        return storageDAO.getByProductID(productId);
    }

    /**
     * Make request to a Storage DAO for inserting
     * storage record.
     *
     * @param storage - storage that must be inserted
     */
    public void insert(final Storage storage) {
        if (storage != null) {
            storageDAO.insert(storage);
        }
    }

    /**
     * Make request to a Storage DAO for inserting
     * storage record.
     *
     * @param productId - product unique identifier
     * @param userId    - current user unique identifier
     * @param amount    - amount of product
     */
    public void insert(final int userId, final int productId, final int amount) {
        storageDAO.insertInParts(userId, productId, amount);
    }

    /**
     * Make request to a Storage DAO for updating storage record.
     * Insert in history record about product's usage.
     * If amount is less than 2, insert record in shopping list
     *
     * @param storage - storage that must be updated
     */
    public void update(final Storage storage) {
        if (storage.getAmount() < 0) {
            LOGGER.error("Illegal argument: amount < 0");
            return;
        }

        final Storage storageDB = storageDAO.getByProductID(storage.getProduct().getId());
        final int diff = storageDB.getAmount() - storage.getAmount();
        if (diff > 0) {
            addToHistory(storage, diff, Action.USED);
        } else if (diff < 0) {
            addToHistory(storage, -diff, Action.PURCHASED);
        }
        if (storage.getEndDate() == null) {
            storageDAO.updateAmount(storage);
        } else {
            storageDAO.update(storage);
        }
        if (storage.getAmount() <= LIMIT) {
            shoppingListService.insert(new ShoppingList(storage.getUser(), storage.getProduct(), 1));
        }
    }

    /**
     * Make request to a Storage DAO for updating storage record.
     * Insert in history record about product's usage.
     * If amount is less than 2, insert record in shopping list
     *
     * @param storageDTO - storage that must be updated
     */
    public void update(final StorageDTO storageDTO) {
        if (storageDTO.getAmount() < 0) {
            LOGGER.error("Illegal argument: amount < 0");
            return;
        }

        final Storage storage = storageDAO.getByProductID(storageDTO.getProductId());
        if (storage == null) {
            LOGGER.warn(String.format("Storage with product id %d doesn't exist", storageDTO.getProductId()));
            return;
        }

        final int diff = storage.getAmount() - storageDTO.getAmount();
        if (diff > 0) {
            addToHistory(storage, diff, Action.USED);
        } else if (diff < 0) {
            addToHistory(storage, -diff, Action.PURCHASED);
        }
        storage.setAmount(storageDTO.getAmount());
        storageDAO.updateAmount(storage);
        forecastService.setEndDate(storage.getProduct().getId());

        if (storage.getAmount() <= LIMIT) {
            shoppingListService.insert(new ShoppingList(storage.getUser(), storage.getProduct(), 1));
        }

    }

    /**
     * Make request to a Storage DAO for deleting
     * storage record.
     *
     * @param storage - storage that must be deleted
     */
    public void delete(final Storage storage) {
        if (storage != null) {
            storageDAO.delete(storage);
        } else {
            LOGGER.error("Illegal argument: storage == null");
        }
    }

    private void addToHistory(final Storage storage, final int diff, final Action action) {
        final HistoryDTO historyDTO = new HistoryDTO();
        historyDTO.setUserId(storage.getUser().getId());
        historyDTO.setProductId(storage.getProduct().getId());
        historyDTO.setAmount(diff);
        historyDTO.setUsedDate(new Timestamp(System.currentTimeMillis()));
        historyDTO.setAction(action);
        historyService.insert(historyDTO);
    }
}

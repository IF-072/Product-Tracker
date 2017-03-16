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
 * The StorageService class is used to hold business logic for working with the storage DAO
 *
 * @author Roman Dyndyn
 */
@Service
public class StorageService {
    private static final Logger LOGGER = LogManager.getLogger(StorageService.class);
    private StorageDAO storageDAO;
    private ShoppingListService shoppingListService;
    private HistoryService historyService;
    private MessageService messageService;
    private UserService userService;

    @Autowired
    public StorageService(StorageDAO storageDAO, ShoppingListService shoppingListService,
                          HistoryService historyService, MessageService messageService,
                          UserService userService) {
        this.storageDAO = storageDAO;
        this.shoppingListService = shoppingListService;
        this.historyService = historyService;
        this.messageService = messageService;
        this.userService = userService;
    }

    public List<Storage> getByUserId(int user_id) {
        List<Storage> list = storageDAO.getByUserID(user_id);
        if (CollectionUtils.isEmpty(list)) {
            LOGGER.warn("Storage of user with id {} not found", user_id);
        }
        return list;
    }

    public Storage getByProductId(int product_id) {
        return storageDAO.getByProductID(product_id);
    }

    public void insert(Storage storage) {
        if (storage != null)
            storageDAO.insert(storage);
    }

    public void insert(int userId, int productId, int amount) {
        storageDAO.insertInParts(userId, productId, amount);
    }

    public void update(Storage storage) {
        if (storage.getAmount() < 0) {
            LOGGER.error("Illegal argument: amount < 0");
            return;
        }

        Storage storageDB = storageDAO.getByProductID(storage.getProduct().getId());
        int diff;
        if ((diff = storageDB.getAmount() - storage.getAmount()) > 0) {
            addToHistory(storage, diff, Action.USED);
        } else {
            addToHistory(storage, -diff, Action.PURCHASED);
        }
        if (storage.getEndDate() != null) {
            storageDAO.update(storage);
        } else {
            storageDAO.updateAmount(storage);
        }
        if (storage.getAmount() <= 1) {
            shoppingListService.insert(new ShoppingList(storage.getUser(), storage.getProduct(), 1));
        }
    }

    public void update(StorageDTO storageDTO) {
        if (storageDTO.getAmount() < 0) {
            LOGGER.error("Illegal argument: amount < 0");
            return;
        }

        Storage storage = storageDAO.getByProductID(storageDTO.getProductId());
        if (storage == null) {
            LOGGER.warn(String.format("Storage with product id %d doesn't exist", storageDTO.getProductId()));
            return;
        }

        int diff;
        if ((diff = storage.getAmount() - storageDTO.getAmount()) > 0) {
            addToHistory(storage, diff, Action.USED);
        } else {
            addToHistory(storage, -diff, Action.PURCHASED);
        }
        storage.setAmount(storageDTO.getAmount());
        storageDAO.updateAmount(storage);

        if (storage.getAmount() <= 1) {
            shoppingListService.insert(new ShoppingList(storage.getUser(), storage.getProduct(), 1));
        }

        messageService.broadcastSpittle("storage updated", userService.getById(storageDTO.getUserId()));
    }

    public void delete(Storage storage) {
        if (storage != null) {
            storageDAO.delete(storage);
        } else {
            LOGGER.error("Illegal argument: storage == null");
            return;
        }
    }

    private void addToHistory(Storage storage, int diff, Action action) {
        HistoryDTO historyDTO = new HistoryDTO();
        historyDTO.setUserId(storage.getUser().getId());
        historyDTO.setProductId(storage.getProduct().getId());
        historyDTO.setAmount(diff);
        historyDTO.setUsedDate(new Timestamp(System.currentTimeMillis()));
        historyDTO.setAction(action);
        historyService.insert(historyDTO);
    }
}

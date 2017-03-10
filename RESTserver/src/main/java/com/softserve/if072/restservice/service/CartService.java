package com.softserve.if072.restservice.service;

import com.softserve.if072.common.model.Cart;
import com.softserve.if072.common.model.ShoppingList;
import com.softserve.if072.common.model.Storage;
import com.softserve.if072.common.model.dto.CartDTO;
import com.softserve.if072.restservice.dao.mybatisdao.CartDAO;
import com.softserve.if072.restservice.exception.DataNotFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.util.List;

/**
 * The CartService class is used to hold business logic for working with the cart DAO
 *
 * @author Igor Kryviuk
 */
@Service
public class CartService {
    private static final Logger LOGGER = LogManager.getLogger();
    private final CartDAO cartDAO;
    private final StorageService storageService;
    private final ShoppingListService shoppingListService;
    @Value("${cart.containsProduct}")
    private String cartContainsProduct;
    @Value("${cart.deleteProduct}")
    private String cartDeleteProduct;
    @Value("${cart.insertProduct}")
    private String cartInsertProduct;
    @Value("${cart.updateProductAmount}")
    private String cartUpdateProductAmount;
    @Value("${cart.productExist}")
    private String cartProductExist;
    @Value("${cart.notFound}")
    private String cartNotFound;
    @Value("${cart.SuccessfullyOperation}")
    private String cartSuccessfullyOperation;
    @Value("${cart.deleteAllSuccessfullyOperation}")
    private String deleteAllSuccessfullyOperation;
    @Value("${cart.foundProductId}")
    private String cartFoundProductId;
    @Value("${cart.transaction}")
    private String cartTransaction;
    @Value("${cart.productPurchaseErrorOccur}")
    private String cartProductPurchaseErrorOccur;

    public CartService(CartDAO cartDAO, StorageService storageService, ShoppingListService shoppingListService) {
        this.cartDAO = cartDAO;
        this.storageService = storageService;
        this.shoppingListService = shoppingListService;
    }

    /**
     * Make request to a Cart DAO for retrieving all cart records for current user
     *
     * @param userId - current user unique identifier
     * @return list of cart records or empty list
     */
    public List<Cart> getByUserId(int userId) {
        List<Cart> carts = cartDAO.getByUserId(userId);
        LOGGER.info(cartContainsProduct, userId, carts.size());
        return carts;
    }

    /**
     * This method implements behavior of product purchase that consists of three steps.
     * Step one: the product is being removed from the user's cart.
     * Step two: if there is exactly the same product in the user's storage, then its
     * amount will be updated otherwise the new product will be inserted into the user's storage.
     * Step three: if the user buy product amount that is less than they have planed and
     * if exactly the same product exists on the user's shopping list, then the product amount on the user's
     * shopping list will be updated. Otherwise, if the user buys product amount that is more or
     * equal than they have planed, and  if exactly the same product exists on the user's shopping list
     * then the product will be removed from the user's shopping list
     *
     * @param cartDTO - an object with required information for the product purchase
     */
    @Transactional(rollbackFor = Exception.class)
    public void productPurchase(CartDTO cartDTO) {
        try {
            if (TransactionSynchronizationManager.isActualTransactionActive()) {
                LOGGER.info(cartTransaction, cartDTO.getProductId(), "has");
            } else {
                LOGGER.warn(cartTransaction, cartDTO.getProductId(), "has not");
            }
            cartDAO.deleteByProductId(cartDTO.getProductId());
            LOGGER.info(cartDeleteProduct, cartDTO.getProductId(), "cart", cartDTO.getUserId());

            Storage storage = storageService.getByProductId(cartDTO.getProductId());
            if (storage == null) {
                storageService.insert(cartDTO.getUserId(), cartDTO.getProductId(), cartDTO.getAmount());
                LOGGER.info(cartInsertProduct, cartDTO.getAmount(), cartDTO.getProductId(), cartDTO.getUserId());
            } else {
                storage.setAmount(storage.getAmount() + cartDTO.getAmount());
                storageService.update(storage);
                LOGGER.info(cartUpdateProductAmount, cartDTO.getProductId(), "storage", cartDTO.getUserId());
            }

            ShoppingList shoppingList = shoppingListService.getByProductId(cartDTO.getProductId());
            if (shoppingList != null) {
                LOGGER.info(cartProductExist, cartDTO.getProductId(), cartDTO.getUserId());
                if (cartDTO.getInitialAmount() > cartDTO.getAmount()) {
                    shoppingList.setAmount(cartDTO.getInitialAmount() - cartDTO.getAmount());
                    shoppingListService.update(shoppingList);
                    LOGGER.info(cartUpdateProductAmount, cartDTO.getProductId(), "shopping list", cartDTO.getUserId());
                } else {
                    shoppingListService.delete(shoppingList);
                    LOGGER.info(cartDeleteProduct, cartDTO.getProductId(), "shopping list", cartDTO.getUserId());
                }
            }
        } catch (Exception e) {
            LOGGER.error(cartProductPurchaseErrorOccur, e, cartDTO.getProductId());
            throw e;
        }
    }

    /**
     * Make request to a Cart DTO for deleting a product from the cart of current user
     *
     * @param productId - product unique identifier
     */
    public void delete(int productId) {

        if (cartDAO.deleteByProductId(productId) == 0) {
            throw new DataNotFoundException(String.format(cartNotFound, " DELETE", productId));
        }
        LOGGER.info(cartSuccessfullyOperation, productId, "deleted from");
    }

    /**
     * Make request to a Cart DTO for deleting all product from the cart of current user
     * @param userId - current user unique identifier
     */
    public void deleteAll(int userId) {
        int count=cartDAO.deleteAll(userId);
        LOGGER.info(deleteAllSuccessfullyOperation, count, userId);
    }

    public Cart getByProductId(int productId) {
        Cart cart = cartDAO.getByProductId(productId);
        LOGGER.info(cartFoundProductId, productId);
        return cart;
    }

    public void insert(Cart cart) {
        cartDAO.insert(cart);
        LOGGER.info(cartSuccessfullyOperation, cart.getProduct().getId(), "inserted into");
    }

    public void update(Cart cart) {
        if (cartDAO.update(cart) == 0) {
            throw new DataNotFoundException(String.format(cartNotFound, "UPDATE", cart.getProduct().getId()));
        }
        LOGGER.info(cartSuccessfullyOperation, cart.getProduct().getId(), "updated in");
    }


}



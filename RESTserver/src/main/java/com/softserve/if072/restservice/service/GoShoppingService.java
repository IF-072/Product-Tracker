package com.softserve.if072.restservice.service;

import com.softserve.if072.common.model.Product;
import com.softserve.if072.common.model.ShoppingList;
import com.softserve.if072.common.model.Store;
import com.softserve.if072.common.model.Cart;
import com.softserve.if072.restservice.dao.mybatisdao.CartDAO;
import com.softserve.if072.restservice.dao.mybatisdao.ShoppingListDAO;
import com.softserve.if072.restservice.dao.mybatisdao.StoreDAO;
import com.softserve.if072.restservice.exception.DataNotFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.apache.commons.collections.CollectionUtils;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * The GoShoppingService class is used to hold business logic for working with the function "Go Shopping"
 *
 * @author Roman Dyndyn
 */
@Service
public class GoShoppingService {
    private static final Logger LOGGER = LogManager.getLogger(GoShoppingService.class);

    private ShoppingListDAO shoppingListDAO;
    private StoreDAO storeDAO;
    private CartDAO cartDAO;

    @Autowired
    public GoShoppingService(ShoppingListDAO shoppingListDAO, StoreDAO storeDAO, CartDAO cartDAO) {
        this.shoppingListDAO = shoppingListDAO;
        this.storeDAO = storeDAO;
        this.cartDAO = cartDAO;
    }

    /**
     * Select stores from the shopping_list table that belong to specific user
     *
     * @param userId unique user's identifier
     * @return list of store item that belong to specific user
     */
    public List<Store> getStoreByUserId(int userId) throws DataNotFoundException {
        if (CollectionUtils.isNotEmpty(cartDAO.getByUserId(userId))) {
            return null;
        }

        List<Product> shoppingList = shoppingListDAO.getProductsByUserId(userId);
        if (CollectionUtils.isEmpty(shoppingList)) {
            LOGGER.info(String.format("Shopping list of user with id %d is empty", userId));
            return null;
        }
        List<Store> storeList = storeDAO.getAllByUser(userId);

        if (CollectionUtils.isEmpty(storeList)) {
            throw new DataNotFoundException(String.format("Store list of user with id %d is empty", userId));
        }

        return retainProductFromShoppingList(storeList, shoppingList);
    }

    /**
     * Select products from the store that user chose and contained in the shopping list ("selected"), and products
     * that are remained in shoppinglist.
     *
     * @param userId  unique user's identifier
     * @param storeId unique store's identifier
     * @return two lists of products item that belong to specific user
     */
    public Map<String, List<ShoppingList>> getProducts(int userId, int storeId) throws DataNotFoundException {
        List<Product> productsFromSelectedStore = storeDAO.getProductsByStoreId(storeId, userId);
        if (CollectionUtils.isEmpty(productsFromSelectedStore)) {
            throw new DataNotFoundException(String.format("Products of user with id %d, store with id %d is empty",
                    userId, storeId));
        }

        Map<String, List<ShoppingList>> productsMap = new HashMap<>();
        List<ShoppingList> shoppingLists = shoppingListDAO.getByUserID(userId);
        List<Store> store = new ArrayList<>();
        store.add(storeDAO.getByID(storeId));



        List<ShoppingList> productsToBuy = new ArrayList<>();
        productsToBuy = shoppingLists.stream().filter(element -> productsFromSelectedStore.contains(element.getProduct()))
                .map(element -> {
                    element.getProduct().setStores(store);
                    return element;
                }).collect(Collectors.toList());
        shoppingLists.removeAll(productsToBuy);

        productsMap.put("selected", productsToBuy);
        productsMap.put("remained", shoppingLists);
        return productsMap;
    }

    public void insertCart(List<Cart> carts) {
        for (Cart cart : carts) {
            cartDAO.insert(cart);
        }
    }

    /**
     * method retain in each store products that are contained in the shopping list. If store doesn't contain
     * products from shopping list, it will be removed.
     *
     * @param shoppingList list of products from shoppinglist
     * @param storeList    list of stores
     * @return List of Store with products that contains in ShoppingList
     */
    private List<Store> retainProductFromShoppingList(List<Store> storeList, List<Product> shoppingList) {
        return storeList.stream().filter(store -> CollectionUtils.isNotEmpty(store.getProducts()))
                .map(store -> {
                    Set<Product> set = new HashSet<>(shoppingList);
                    set.retainAll(store.getProducts());
                    store.setProducts(new ArrayList<>(set));
                    return store;
                }).filter(store -> CollectionUtils.isNotEmpty(store.getProducts()))
                .collect(Collectors.toCollection(ArrayList::new));
    }
}

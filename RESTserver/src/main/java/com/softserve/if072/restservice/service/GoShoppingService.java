package com.softserve.if072.restservice.service;

import com.softserve.if072.common.model.FormForCart;
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
import org.springframework.util.CollectionUtils;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


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
        if (!CollectionUtils.isEmpty(cartDAO.getByUserId(userId))) {
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
    public Map<String, List<ShoppingList>> getProducts(Integer userId, int storeId) throws DataNotFoundException {
        Map<String, List<ShoppingList>> productsMap = new HashMap<String, List<ShoppingList>>();

        List<ShoppingList> shoppinList = shoppingListDAO.getByUserID(userId);
        List<Product> productsFromSelectedStore = storeDAO.getProductsByStoreId(storeId, userId);
        List<Store> store = new ArrayList<Store>();
        store.add(storeDAO.getByID(storeId));

        if (CollectionUtils.isEmpty(productsFromSelectedStore)) {
            throw new DataNotFoundException(String.format("Products of user with id %d, store with id %d is empty",
                    userId, storeId));
        }

        List<ShoppingList> productsToBuy = new ArrayList<ShoppingList>();

        for (Iterator<ShoppingList> iterator = shoppinList.iterator(); iterator.hasNext(); ) {
            ShoppingList element = iterator.next();
            if (productsFromSelectedStore.contains(element.getProduct())) {
                element.getProduct().setStores(store);
                productsToBuy.add(element);
                iterator.remove();
            }
        }

        productsMap.put("selected", productsToBuy);
        productsMap.put("remained", shoppinList);
        return productsMap;
    }

    public void insertCart(FormForCart carts) {
        carts.removeUncheked();
        for (Cart cart : carts.getCarts()) {
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
        Iterator<Store> iterator = storeList.iterator();
        while (iterator.hasNext()) {
            Store store = iterator.next();
            if (!CollectionUtils.isEmpty(store.getProducts())) {
                Set<Product> set = new HashSet<Product>(shoppingList);
                set.retainAll(store.getProducts());
                if (CollectionUtils.isEmpty(set)) {
                    iterator.remove();
                } else {
                    store.setProducts(new ArrayList<Product>(set));
                }
            } else {
                iterator.remove();
            }

        }
        return storeList;
    }
}

package com.softserve.if072.restservice.service;

import com.softserve.if072.common.model.Cart;
import com.softserve.if072.common.model.FormForCart;
import com.softserve.if072.common.model.Product;
import com.softserve.if072.common.model.Store;
import com.softserve.if072.restservice.dao.mybatisdao.CartDAO;
import com.softserve.if072.restservice.dao.mybatisdao.ShoppingListDAO;
import com.softserve.if072.restservice.dao.mybatisdao.StoreDAO;
import com.softserve.if072.restservice.exception.DataNotFoundException;
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
        List<Product> shoppingList = shoppingListDAO.getProductsByUserId(userId);
        if (CollectionUtils.isEmpty(shoppingList)) {
            throw new DataNotFoundException(String.format("Shopping list of user with id %d is empty", userId));
        }
        List<Store> storeList = storeDAO.getAllByUser(userId);
        if (CollectionUtils.isEmpty(storeList)) {
            throw new DataNotFoundException(String.format("Store list of user with id %d is empty", userId));
        }

        return retainProductFormShoppingList(storeList, shoppingList);
    }

    /**
     * Select products from the stores that user chose and contained in the shopping list ("selected"), and products
     * that are remained in shoppinglist.
     *
     * @param userId unique user's identifier
     * @param storesIds unique store's identifier
     * @return two lists of products item that belong to specific user
     */
    public Map<String, List<Product>> getProducts(Integer userId, int[] storesIds) throws DataNotFoundException {
        Map<String, List<Product>> productsMap = new HashMap<String, List<Product>>();
        List<Product> productsFromSelectedStore = new ArrayList<Product>();

        Set<Product> set = new HashSet<Product>(shoppingListDAO.getProductsByUserId(userId));
        for (int i : storesIds) {
            Store store = storeDAO.getByID(i);
            for (Product product : storeDAO.getProductsOnlyByStoreId(i)) {
                if (set.contains(product)) {
                    int index;
                    if ((index = productsFromSelectedStore.indexOf(product)) != -1) {
                        productsFromSelectedStore.get(index).getStores().add(store);
                    } else {
                        product.setStores(new ArrayList<Store>());
                        product.getStores().add(store);
                        productsFromSelectedStore.add(product);
                    }
                }
            }
        }


        if (CollectionUtils.isEmpty(productsFromSelectedStore)) {
            throw new DataNotFoundException(String.format("Shopping list of user with id %d is empty", userId));
        }

        set.removeAll(productsFromSelectedStore);

        productsMap.put("selected", productsFromSelectedStore);
        productsMap.put("remained", new ArrayList<Product>(set));
        return productsMap;
    }

    public void insertCart(FormForCart carts) {
        carts.removeUncheked();
        for (Cart cart : carts.getCarts()) {
            try {
                cartDAO.insert(cart);
            } catch (Exception e) {

            }
        }
    }

    /**
     * method retain in each store only products that are contained in the shopping list. If store doesn't contain
     * products from shopping list, it will be removed.
     *
     * @param shoppingList list of products from shoppinglist
     * @param storeList list of stores
     * @return storage item that belong to specific product
     */
    private List<Store> retainProductFormShoppingList(List<Store> storeList, List<Product> shoppingList){
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

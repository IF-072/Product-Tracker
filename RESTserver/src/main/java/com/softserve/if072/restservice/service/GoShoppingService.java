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

import java.sql.SQLException;
import java.util.*;

/**
 * Created by dyndyn on 15.02.2017.
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

    public List<Store> getStoreByUserId(int userId) throws DataNotFoundException {
        List<Product> shoppingList = shoppingListDAO.getProductsByUserId(userId);
        if (CollectionUtils.isEmpty(shoppingList)) {
            throw new DataNotFoundException(String.format("Shopping list of user with id %d is empty", userId));
        }
        List<Store> list = storeDAO.getAllByUser(userId);
        if (CollectionUtils.isEmpty(list)) {
            throw new DataNotFoundException(String.format("Store list of user with id %d is empty", userId));
        }

        Iterator<Store> iterator = list.iterator();
        while (iterator.hasNext()) {
            Store store = iterator.next();
            if (!CollectionUtils.isEmpty(store.getProducts())) {
                Set<Product> set = new HashSet<Product>(shoppingList);
                set.retainAll(store.getProducts());
                if (CollectionUtils.isEmpty(set)) {
                    iterator.remove();
                } else {
                    store.setProducts(new LinkedList<Product>(set));
                }
            } else {
                iterator.remove();
            }

        }
        return list;
    }

    public Map<String, List<Product>> getProducts(Integer userId, int[] storesIds) throws DataNotFoundException {
        Map<String, List<Product>> map = new HashMap<String, List<Product>>();
        List<Product> selected = new ArrayList<Product>();

        Set<Product> set = new HashSet<Product>(shoppingListDAO.getProductsByUserId(userId));
        for (int i : storesIds) {
            Store store = storeDAO.getByID(i);
            for (Product product : storeDAO.getProductsOnlyByStoreId(i)) {
                if (set.contains(product)) {
                    int index;
                    if ((index = selected.indexOf(product)) != -1) {
                        selected.get(index).getStores().add(store);
                    } else {
                        product.setStores(new ArrayList<Store>());
                        product.getStores().add(store);
                        selected.add(product);
                    }
                }
            }
        }


        if (CollectionUtils.isEmpty(selected)) {
            throw new DataNotFoundException(String.format("Shopping list of user with id %d is empty", userId));
        }

        set.removeAll(selected);

        map.put("selected", selected);
        map.put("remained", new ArrayList<Product>(set));
        return map;
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
}

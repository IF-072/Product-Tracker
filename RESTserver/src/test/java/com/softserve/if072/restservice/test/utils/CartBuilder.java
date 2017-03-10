package com.softserve.if072.restservice.test.utils;

import com.softserve.if072.common.model.Cart;
import com.softserve.if072.common.model.Product;
import com.softserve.if072.common.model.Store;
import com.softserve.if072.common.model.User;

/**
 * The CartBuilder class is used to simplify building Cart objects for testing
 *
 * @author Igor Kryviuk
 */
public class CartBuilder {
    public static Cart getDefaultCart(int id, int userId, int amount) {
        User user = new User();
        user.setName("user" + userId);
        Store store = new Store();
        store.setName("store" + id);
        Product product = new Product();
        product.setName("product" + id);
        return new Cart(user, store, product, amount);
    }
}



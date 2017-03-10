package com.softserve.if072.restservice.test.utils;

import com.softserve.if072.common.model.History;
import com.softserve.if072.common.model.Product;
import com.softserve.if072.common.model.User;

import java.sql.Timestamp;

/**
 * The HistoryBuilder class is used to simplify building Cart objects for testing
 *
 * @author Igor Kryviuk
 */
public class HistoryBuilder {
    public static History getDefaultHistory(int id, int userId) {
        int amount= 5;
        return getDefaultHistory(id, userId, amount);
    }

    public static History getDefaultHistory(int id, int userId, int amount) {
        return getDefaultHistory(id, userId, amount, new Timestamp(System.currentTimeMillis()));
    }

    public static History getDefaultHistory(int id, int userId, int amount, Timestamp usedDate) {
        User user = new User();
        user.setName("user" + userId);
        Product product = new Product();
        product.setName("product" + id);
        return new History(id, user, product, amount, usedDate);
    }
}

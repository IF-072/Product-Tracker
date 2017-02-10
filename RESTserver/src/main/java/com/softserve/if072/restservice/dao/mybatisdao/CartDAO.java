package com.softserve.if072.restservice.dao.mybatisdao;

import com.softserve.if072.common.model.Cart;
import com.softserve.if072.common.model.Product;
import com.softserve.if072.common.model.Store;
import com.softserve.if072.common.model.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * The CartDAO interface is used to configure
 * mapped SQL statements for Cart class
 *
 * @author Igor Kryviuk
 */
@Repository
public interface CartDAO {
    /**
     * Select all records from the cart table
     *
     * @return list of all cart items that are stored in the database
     */
    @Select("SELECT user_id, store_id, product_id, amount FROM cart")
    @Results(value = {
            @Result(property = "user", column = "user_id", javaType = User.class,
                    one = @One(select = "com.softserve.if072.restservice.dao.mybatisdao.UserDAO.getByID")),
            @Result(property = "store", column = "store_id", javaType = Store.class,
                    one = @One(select = "com.softserve.if072.restservice.dao.mybatisdao.StoreDAO.getByID")),
            @Result(property = "product", column = "product_id", javaType = Product.class,
                    one = @One(select = "com.softserve.if072.restservice.dao.mybatisdao.ProductDAO.getByID")),
            @Result(property = "amount", column = "amount")
    })
    List<Cart> getAll();

    /**
     * Select all records from the cart table that belong to specific user
     *
     * @param userId unique user's identifier
     * @return list of all cart items that belong to specific user
     */
    @Select("SELECT user_id, store_id, product_id, amount FROM cart" +
            "WHERE user_id = #{userId}")
    @Results(value = {
            @Result(property = "user", column = "user_id", javaType = User.class,
                    one = @One(select = "com.softserve.if072.restservice.dao.mybatisdao.UserDAO.getByID")),
            @Result(property = "store", column = "store_id", javaType = Store.class,
                    one = @One(select = "com.softserve.if072.restservice.dao.mybatisdao.StoreDAO.getByID")),
            @Result(property = "product", column = "product_id", javaType = Product.class,
                    one = @One(select = "com.softserve.if072.restservice.dao.mybatisdao.ProductDAO.getByID")),
            @Result(property = "amount", column = "amount")
    })
    List<Cart> getAllByUserId(int userId);

    /**
     * Insert new record into the cart table
     *
     * @param cart item to be inserted to the cart table
     */
    @Insert("INSERT INTO cart(user_id, store_id, product_id, amount) VALUES (#{user.id}, #{store.id}, #{product.id}, #{cart.amount})")
    void insert(Cart cart);

    /**
     * Update amount for current cart.
     * Since other columns in the cart table are keys we should update only amount column.
     *
     * @param cart item to be updated in the cart table
     */
    @Update("UPDATE cart SET amount=#{amount} WHERE product_id=#{cart.product.id}")
    void update(Cart cart);

    /**
     * Delete current cart from the cart table
     *
     * @param cart item to be deleted from the cart table
     */
    @Delete("DELETE FROM cart WHERE product_id=#{cart.product.id}")
    void delete(Cart cart);
}

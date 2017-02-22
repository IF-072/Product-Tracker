package com.softserve.if072.restservice.dao.mybatisdao;

import com.softserve.if072.common.model.Cart;
import com.softserve.if072.common.model.Product;
import com.softserve.if072.common.model.Store;
import com.softserve.if072.common.model.User;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
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
     * Select all records from the cart table that belong to specific user
     *
     * @param userId unique user's identifier
     * @return list of all cart items that belong to specific user
     */
    @Select("SELECT user_id, store_id, product_id, amount FROM cart " +
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
    List<Cart> getByUserId(int userId);

    /**
     * Select record from the cart table that belong to specific product
     *
     * @param productId unique product identifier
     * @return cart item that belong to specific product identifier
     */
    @Select("SELECT user_id, store_id, product_id, amount FROM cart " +
            "WHERE product_id = #{productId}")
    @Results(value = {
            @Result(property = "user", column = "user_id", javaType = User.class,
                    one = @One(select = "com.softserve.if072.restservice.dao.mybatisdao.UserDAO.getByID")),
            @Result(property = "store", column = "store_id", javaType = Store.class,
                    one = @One(select = "com.softserve.if072.restservice.dao.mybatisdao.StoreDAO.getByID")),
            @Result(property = "product", column = "product_id", javaType = Product.class,
                    one = @One(select = "com.softserve.if072.restservice.dao.mybatisdao.ProductDAO.getByID")),
            @Result(property = "amount", column = "amount")
    })
    Cart getByProductId(int productId);

    /**
     * Insert new record into the cart table
     *
     * @param cart item to be inserted to the cart table
     */
    @Insert("INSERT INTO cart(user_id, store_id, product_id, amount) VALUES (#{user.id}, #{store.id}, #{product.id}, #{amount})")
    void insert(Cart cart);

    /**
     * Update amount for current cart.
     * Since other columns in the cart table are keys we should update only amount column.
     *
     * @param cart item to be updated in the cart table
     */
    @Update("UPDATE cart SET amount=#{amount} WHERE product_id=#{product.id}")
    int update(Cart cart);

    /**
     * Delete cart item with specific product from the cart table
     *
     * @param productId unique product identifier
     */
    @Delete("DELETE FROM cart WHERE product_id=#{productId}")
    int deleteByProductId(int productId);
}

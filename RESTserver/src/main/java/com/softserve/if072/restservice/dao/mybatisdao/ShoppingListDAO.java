package com.softserve.if072.restservice.dao.mybatisdao;

import com.softserve.if072.common.model.Product;
import com.softserve.if072.common.model.ShoppingList;
import com.softserve.if072.common.model.User;
import com.softserve.if072.restservice.dao.DAO;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * The ShoppingListDAO interface is used to configure
 * mapped SQL statements for ShoppingList class.
 *
 * @author Roman Dyndyn
 */
@Repository
public interface ShoppingListDAO extends DAO<ShoppingList> {

    /**
     * Select record from the shopping_list table
     * that belong to specific product.
     *
     * @param productId unique product's identifier
     * @return ShoppingList item that belong to specific product
     */
    @Select("SELECT amount, user_id, product_id FROM shopping_list WHERE product_id = #{productId}")
    @Results(value = {
            @Result(property = "amount", column = "amount"),
            @Result(property = "user", column = "user_id", javaType = User.class,
                    one = @One(select = "com.softserve.if072.restservice.dao.mybatisdao.UserDAO.getByID")),
            @Result(property = "product", column = "product_id", javaType = Product.class,
                    one = @One(select = "com.softserve.if072.restservice.dao.mybatisdao.ProductDAO.getByID"))
    })
    ShoppingList getByProductId(int productId);

    /**
     * Select all records from the shopping_list
     * table that belong to specific user.
     *
     * @param userId unique user's identifier
     * @return list of all shoppingList items that belong to specific user
     */
    @Select("SELECT amount, shopping_list.user_id, product_id FROM shopping_list LEFT JOIN product"
            + " ON product_id=id WHERE shopping_list.user_id = #{userId} AND is_enabled=1")
    @Results(value = {
            @Result(property = "amount", column = "amount"),
            @Result(property = "user", column = "user_id", javaType = User.class,
                    one = @One(select = "com.softserve.if072.restservice.dao.mybatisdao.UserDAO.getByID")),
            @Result(property = "product", column = "product_id", javaType = Product.class,
                    one = @One(select = "com.softserve.if072.restservice.dao.mybatisdao.ProductDAO.getByID"))
    })
    List<ShoppingList> getByUserID(int userId);

    /**
     * Select record from the shopping_list table
     * that belong to specific user and product.
     *
     * @param userId    unique user's identifier
     * @param productId unique product's identifier
     * @return ShoppingList item that belong to specific product
     */
    @Select("SELECT * FROM shopping_list WHERE user_id = #{userId} and product_id = #{productId}")
    @Results(value = {
            @Result(property = "amount", column = "amount"),
            @Result(property = "user", column = "user_id", javaType = User.class,
                    one = @One(select = "com.softserve.if072.restservice.dao.mybatisdao.UserDAO.getByID")),
            @Result(property = "product", column = "product_id", javaType = Product.class,
                    one = @One(select = "com.softserve.if072.restservice.dao.mybatisdao.ProductDAO.getByID"))
    })
    ShoppingList getByUserAndProductId(@Param("userId") Integer userId, @Param("productId") Integer productId);

    /**
     * Select all products from the product table that
     * belong to specific user's rows of shopping_list table.
     *
     * @param userId unique user's identifier
     * @return list of all product items that belong to specific user
     */
    @Select("SELECT id, name, shopping_list.user_id, is_enabled FROM product RIGHT JOIN "
            + "shopping_list ON product.id = shopping_list.product_id WHERE shopping_list.user_id = #{userId} "
            + "AND is_enabled = 1")
    @Results(value = {
            @Result(property = "id", column = "id"),
            @Result(property = "isEnabled", column = "is_enabled"),
            @Result(property = "stores", column = "id", javaType = List.class,
                    many = @Many(select = "com.softserve.if072.restservice.dao.mybatisdao.ProductDAO."
                            + "getStoresByProductId"))

    })
    List<Product> getProductsByUserId(int userId);

    /**
     * Select record from the shopping_list table
     * that belong to specific user and product.
     *
     * @param shoppingList the object being inspected
     * @return ShoppingList item that belong to specific product
     */
    @Select("SELECT * FROM shopping_list WHERE user_id=#{user.id} AND product_id=#{product.id}")
    @Results(value = {
            @Result(property = "amount", column = "amount"),
            @Result(property = "user", column = "user_id", javaType = User.class,
                    one = @One(select = "com.softserve.if072.restservice.dao.mybatisdao.UserDAO.getByID")),
            @Result(property = "product", column = "product_id", javaType = Product.class,
                    one = @One(select = "com.softserve.if072.restservice.dao.mybatisdao.ProductDAO.getByID"))
    })
    ShoppingList getByClass(ShoppingList shoppingList);

    /**
     * Insert new record into the shopping_list table.
     *
     * @param shoppingList item to be inserted to the shopping_list table
     */
    @Override
    @Insert("INSERT INTO shopping_list (user_id, product_id, amount) VALUES (#{user.id}, #{product.id}, #{amount})")
    @Options(useGeneratedKeys = true)
    void insert(ShoppingList shoppingList);

    /**
     * Update amount for current shoppingList.
     *
     * @param shoppingList item to be updated in the shopping_list table
     */
    @Override
    @Update("UPDATE shopping_list SET amount=#{amount} WHERE user_id=#{user.id} AND product_id=#{product.id}")
    void update(ShoppingList shoppingList);

    /**
     * Delete current shoppingList from the shopping_list table.
     *
     * @param shoppingList item to be deleted from the shopping_list table
     */
    @Delete("DELETE FROM shopping_list WHERE user_id=#{user.id} AND product_id=#{product.id}")
    void delete(ShoppingList shoppingList);

    /**
     * Delete shoppingList with current product from the shopping_list table.
     *
     * @param productId unique product's identifier
     */
    @Delete("DELETE FROM shopping_list WHERE product_id=#{productId}")
    void deleteByProductId(@Param("productId") Integer productId);
}


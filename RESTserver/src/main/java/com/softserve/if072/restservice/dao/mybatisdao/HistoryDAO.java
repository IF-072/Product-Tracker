package com.softserve.if072.restservice.dao.mybatisdao;

import com.softserve.if072.common.model.History;
import com.softserve.if072.common.model.Product;
import com.softserve.if072.common.model.User;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * The HistoryDAO interface is used to configure
 * mapped SQL statements for History class
 *
 * @author Igor Kryviuk
 */
@Repository
public interface HistoryDAO {
    /**
     * Select all records from the history table that belong to specific user
     *
     * @param userId unique user's identifier
     * @return list of all history items that belong to specific user
     */
    @Select("SELECT id, user_id, product_id, amount, used_date FROM history " +
            "WHERE user_id = #{userId}")
    @Results(value = {
            @Result(property = "id", column = "id"),
            @Result(property = "user", column = "user_id", javaType = User.class,
                    one = @One(select = "com.softserve.if072.restservice.dao.mybatisdao.UserDAO.getByID")),
            @Result(property = "product", column = "product_id", javaType = Product.class,
                    one = @One(select = "com.softserve.if072.restservice.dao.mybatisdao.ProductDAO.getByID")),
            @Result(property = "amount", column = "amount"),
            @Result(property = "usedDate", column = "used_date")
    })
    List<History> getByUserId(int userId);

    /**
     * Select all records from the history table that belong to specific user
     * and specific product
     *
     * @param productId unique product identifier
     * @return list of all history items that belong to specific user and specific product
     */
    @Select("SELECT id, user_id, product_id, amount, used_date FROM history " +
            "WHERE user_id = #{userId} AND product_id = #{productId}")
    @Results(value = {
            @Result(property = "id", column = "id"),
            @Result(property = "user", column = "user_id", javaType = User.class,
                    one = @One(select = "com.softserve.if072.restservice.dao.mybatisdao.UserDAO.getByID")),
            @Result(property = "product", column = "product_id", javaType = Product.class,
                    one = @One(select = "com.softserve.if072.restservice.dao.mybatisdao.ProductDAO.getByID")),
            @Result(property = "amount", column = "amount"),
            @Result(property = "usedDate", column = "used_date")
    })
    List<History> getByProductId(@Param("userId") int userId, @Param("productId") int productId);

    /**
     * Select a record from the history table with specific id identifier
     * *
     *
     * @param historyId unique history identifier
     * @return history with specific id identifier
     */
    @Select("SELECT id, user_id, product_id, amount, used_date FROM history " +
            "WHERE id = #{historyId}")
    @Results(value = {
            @Result(property = "id", column = "id"),
            @Result(property = "user", column = "user_id", javaType = User.class,
                    one = @One(select = "com.softserve.if072.restservice.dao.mybatisdao.UserDAO.getByID")),
            @Result(property = "product", column = "product_id", javaType = Product.class,
                    one = @One(select = "com.softserve.if072.restservice.dao.mybatisdao.ProductDAO.getByID")),
            @Result(property = "amount", column = "amount"),
            @Result(property = "usedDate", column = "used_date")
    })
    History getByHistoryId(int historyId);

    /**
     * Insert new record into the history table
     *
     * @param history item to be inserted to the history table
     */
    @Insert("INSERT INTO history(user_id, product_id, amount, used_date) values(#{user.id}, #{product.id}, #{amount}, #{usedDate})")
    void insert(History history);

    /**
     * Update amount for current history.
     *
     * @param history item to be updated in the history table
     */
    @Update("UPDATE history SET amount=#{amount} WHERE user_id=#{user.id} AND product_id=#{product.id} AND used_date=#{usedDate}")
    int update(History history);

    /**
     * Delete current history from the history table
     *
     * @param historyId unique history identifier that is going to be deleted from the history table
     */
    @Delete("DELETE FROM history WHERE id=#{historyId}")
    int delete(int historyId);
}

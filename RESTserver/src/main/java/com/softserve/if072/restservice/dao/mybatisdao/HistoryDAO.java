package com.softserve.if072.restservice.dao.mybatisdao;

import com.softserve.if072.common.model.Action;
import com.softserve.if072.common.model.History;
import com.softserve.if072.common.model.Product;
import com.softserve.if072.common.model.User;
import com.softserve.if072.common.model.dto.HistoryDTO;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.Date;
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
     * @param userId - unique user's identifier
     * @return list of all history items that belong to specific user
     */
    @Select("SELECT id, user_id, product_id, amount, used_date, action FROM history " +
            "WHERE user_id = #{userId}")
    @Results(value = {
            @Result(property = "id", column = "id"),
            @Result(property = "user", column = "user_id", javaType = User.class,
                    one = @One(select = "com.softserve.if072.restservice.dao.mybatisdao.UserDAO.getByID")),
            @Result(property = "product", column = "product_id", javaType = Product.class,
                    one = @One(select = "com.softserve.if072.restservice.dao.mybatisdao.ProductDAO.getByID")),
            @Result(property = "amount", column = "amount"),
            @Result(property = "usedDate", column = "used_date"),
            @Result(property = "action", column = "action")
    })
    List<History> getByUserId(int userId);

    /**
     * Select all records from the history table that belong to specific user
     * and specific product
     *
     * @param userId    - unique user's identifier
     * @param productId - unique product identifier
     * @return list of all history items that belong to specific user and specific product
     */
    @Select("SELECT id, user_id, product_id, amount, used_date, action FROM history " +
            "WHERE user_id = #{userId} AND product_id = #{productId}")
    @Results(value = {
            @Result(property = "id", column = "id"),
            @Result(property = "user", column = "user_id", javaType = User.class,
                    one = @One(select = "com.softserve.if072.restservice.dao.mybatisdao.UserDAO.getByID")),
            @Result(property = "product", column = "product_id", javaType = Product.class,
                    one = @One(select = "com.softserve.if072.restservice.dao.mybatisdao.ProductDAO.getByID")),
            @Result(property = "amount", column = "amount"),
            @Result(property = "usedDate", column = "used_date"),
            @Result(property = "action", column = "action")
    })
    List<History> getByProductId(@Param("userId") int userId, @Param("productId") int productId);

    /**
     * Select all records from the history table that belong to specific product
     *
     * @param productId - unique product identifier
     * @return list of all history items that belong to specific user and specific product
     */
    @Select("SELECT id, user_id, product_id, amount, used_date, action FROM history " +
            "WHERE product_id = #{productId}")
    @Results(value = {
            @Result(property = "id", column = "id"),
            @Result(property = "userId", column = "user_id"),
            @Result(property = "productId", column = "product_id"),
            @Result(property = "amount", column = "amount"),
            @Result(property = "usedDate", column = "used_date"),
            @Result(property = "action", column = "action")
    })
    List<HistoryDTO> getDTOByProductId(int productId);

    /**
     * Select all records from the history table that belong to specific product
     * and specific action
     *
     * @param productId - unique product identifier
     * @return list of all history items that belong to specific user and specific product
     */
    @Select("SELECT id, user_id, product_id, amount, used_date, action FROM history " +
            "WHERE product_id = #{productId} AND action = #{action}")
    @Results(value = {
            @Result(property = "id", column = "id"),
            @Result(property = "userId", column = "user_id"),
            @Result(property = "productId", column = "product_id"),
            @Result(property = "amount", column = "amount"),
            @Result(property = "usedDate", column = "used_date"),
            @Result(property = "action", column = "action")
    })
    List<HistoryDTO> getDTOByProductIdAndAction(@Param("productId") int productId, @Param("action") Action action);

    /**
     * Select a record from the history table with specific id identifier
     * *
     *
     * @param historyId - unique history record identifier
     * @return history with specific id identifier
     */
    @Select("SELECT id, user_id, product_id, amount, used_date, action FROM history " +
            "WHERE id = #{historyId}")
    @Results(value = {
            @Result(property = "id", column = "id"),
            @Result(property = "user", column = "user_id", javaType = User.class,
                    one = @One(select = "com.softserve.if072.restservice.dao.mybatisdao.UserDAO.getByID")),
            @Result(property = "product", column = "product_id", javaType = Product.class,
                    one = @One(select = "com.softserve.if072.restservice.dao.mybatisdao.ProductDAO.getByID")),
            @Result(property = "amount", column = "amount"),
            @Result(property = "usedDate", column = "used_date"),
            @Result(property = "action", column = "action")
    })
    History getByHistoryId(int historyId);

    /**
     * Insert new record into the history table
     *
     * @param historyDTO - item to be inserted to the history table
     * @return number of inserted records
     */
    @Insert("INSERT INTO history(user_id, product_id, amount, used_date, action)" +
            " values(#{userId}, #{productId}, #{amount}, #{usedDate}, #{action})")
    int insert(HistoryDTO historyDTO);

    /**
     * Update amount for current history.
     *
     * @param historyDTO - item to be updated in the history table
     * @return number of updated records
     */
    @Update("UPDATE history SET amount=#{amount} WHERE id=#{id}")
    int update(HistoryDTO historyDTO);

    /**
     * Delete current history record from the history table
     *
     * @param historyId unique history record identifier that is going to be deleted from the history table
     * @return number of deleted records
     */
    @Delete("DELETE FROM history WHERE id=#{historyId}")
    int delete(int historyId);

    /**
     * Delete all records from the history of current user
     *
     * @param userId - unique user's identifier
     * @return number of deleted records
     */
    @Delete("DELETE FROM history WHERE user_id=#{userId}")
    int deleteAll(int userId);

    /**
     * Select all records from the history table that belong to specific user and matches given search params
     *
     * @param userId      - unique user's identifier
     * @param name        product name
     * @param description product's description keywords
     * @param categoryId  product's category id
     * @param dateFrom    starting date
     * @param dateTo      ending date
     * @return list of all history items that belong to specific user
     */
    @Select("SELECT h.id, h.user_id, h.product_id, h.amount, h.used_date, h.action " +
            "FROM history h " +
            "  INNER JOIN product p ON h.product_id = p.id " +
            "WHERE h.user_id = #{userId} " +
            "AND (#{name} IS NULL OR p.name LIKE CONCAT('%',#{name},'%')) " +
            "AND (#{description} IS NULL OR p.description LIKE CONCAT('%',#{description},'%')) " +
            "AND (#{categoryId} IS NULL OR #{categoryId} = 0 OR p.category_id = #{categoryId}) " +
            "AND (#{dateFrom} IS NULL OR h.used_date >= #{dateFrom}) " +
            "AND (#{dateTo} IS NULL OR h.used_date <= #{dateTo}) "
    )
    @Results(value = {
            @Result(property = "id", column = "id"),
            @Result(property = "user", column = "user_id", javaType = User.class,
                    one = @One(select = "com.softserve.if072.restservice.dao.mybatisdao.UserDAO.getByID")),
            @Result(property = "product", column = "product_id", javaType = Product.class,
                    one = @One(select = "com.softserve.if072.restservice.dao.mybatisdao.ProductDAO.getByID")),
            @Result(property = "amount", column = "amount"),
            @Result(property = "usedDate", column = "used_date"),
            @Result(property = "action", column = "action")
    })
    List<History> searchAllByUserIdAndParams(@Param("userId") int userId, @Param("name") String name,
                                             @Param("description") String description, @Param("categoryId") int categoryId,
                                             @Param("dateFrom") Date dateFrom, @Param("dateTo") Date dateTo);

    /**
     * Select a product id from the history table
     *
     * @param historyId - unique history record identifier
     * @return product id that belong to the given history record
     */
    @Select("SELECT product_id FROM history WHERE id = #{historyId}")
    int getProductIdByHistoryId(int historyId);
}
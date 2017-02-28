package com.softserve.if072.restservice.dao.mybatisdao;

import com.softserve.if072.common.model.Product;
import com.softserve.if072.common.model.Storage;
import com.softserve.if072.common.model.User;
import com.softserve.if072.restservice.dao.DAO;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * The StorageDAO interface is used to configure
 * mapped SQL statements for Storage class
 *
 * @author Roman Dyndyn
 */
@Repository
public interface StorageDAO extends DAO<Storage> {

    /**
     * Select all records from the storage table that belong to specific user
     *
     * @param userId unique user's identifier
     * @return list of all storage items that belong to specific user
     */
    @Select("SELECT end_date, amount, storage.user_id, product_id FROM storage LEFT JOIN product " +
            "ON product_id=id WHERE storage.user_id = #{userId} AND is_enabled=1")
    @Results(value = {
            @Result(property = "endDate", column = "end_date"),
            @Result(property = "amount", column = "amount"),
            @Result(property = "user", column = "user_id", javaType=User.class,
                    one=@One(select="com.softserve.if072.restservice.dao.mybatisdao.UserDAO.getByID")),
            @Result(property = "product", column = "product_id", javaType=Product.class,
                    one=@One(select="com.softserve.if072.restservice.dao.mybatisdao.ProductDAO.getByID"))
    })
    List<Storage> getByUserID(int userId);

    /**
     * Select record from the storage table that belong to specific product
     *
     * @param productId unique product's identifier
     * @return storage item that belong to specific product
     */
    @Select("SELECT end_date, amount, user_id, product_id FROM storage WHERE product_id = #{productId}")
    @Results(value = {
            @Result(property = "endDate", column = "end_date"),
            @Result(property = "amount", column = "amount"),
            @Result(property = "user", column = "user_id", javaType=User.class,
                    one=@One(select="com.softserve.if072.restservice.dao.mybatisdao.UserDAO.getByID")),
            @Result(property = "product", column = "product_id", javaType=Product.class,
                    one=@One(select="com.softserve.if072.restservice.dao.mybatisdao.ProductDAO.getByID"))
    })
    Storage getByProductID(int productId);

    /**
     * Insert new record into the storage table
     *
     * @param storage item to be inserted to the storage table
     */
    @Override
    @Insert("INSERT INTO storage (user_id, product_id, amount, end_date) VALUES (#{user.id}, #{product.id}, #{amount}, #{endDate})")
    @Options(useGeneratedKeys = true)
    void insert(Storage storage);

    /**
     * Insert new record into the storage table
     *
     * @param userId unique user's identifier
     * @param productId unique product's identifier
     * @param amount amount of product in storage
     */
    @Insert("INSERT INTO storage (user_id, product_id, amount) VALUES (#{userId}, #{productId}, #{amount})")
    @Options(useGeneratedKeys = true)
    void insertInParts(@Param("userId") int userId, @Param("productId") int productId, @Param("amount") int amount);

    /**
     * Update amount and endDate for current storage.
     *
     * @param storage item to be updated in the storage table
     */
    @Override
    @Update("UPDATE storage SET end_date=#{endDate}, amount=#{amount} WHERE user_id=#{user.id} AND product_id=#{product.id}")
    void update(Storage storage);

    /**
     * Update amount for current storage.
     *
     * @param storage item to be updated in the storage table
     */
    @Update("UPDATE storage SET amount=#{amount} WHERE user_id=#{user.id} AND product_id=#{product.id}")
    void updateAmount(Storage storage);

    /**
     * Delete current storage from the storage table
     *
     * @param storage item to be deleted from the storage table
     */
    @Delete("DELETE FROM storage WHERE user_id=#{user.id} AND product_id=#{product.id}")
    void delete(Storage storage) ;
}

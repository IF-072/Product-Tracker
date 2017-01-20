package com.softserve.if072.restservice.dao.mybatisdao;

import com.softserve.if072.common.model.Product;
import com.softserve.if072.common.model.Storage;
import com.softserve.if072.common.model.User;
import com.softserve.if072.restservice.dao.DAO;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * Created by dyndyn on 17.01.2017.
 */
public interface StorageDAO extends DAO<Storage> {
    @Override
    @Select("SELECT * FROM storage")
    @Results(value = {
            @Result(property = "id", column = "id"),
            @Result(property = "amount", column = "amount"),
            @Result(property = "user", column = "user_id", javaType=User.class, one=@One(select="com.softserve.if072.restservice.dao.UserDAO.getByID")),
            @Result(property = "product", column = "product_id", javaType=Product.class, one=@One(select="com.softserve.if072.restservice.dao.ProductDAO.getByID"))
    })
    public List<Storage> getAll();

    @Override
    @Select("SELECT * FROM storage WHERE id = #{id}")
    @Results(value = {
            @Result(property = "id", column = "id"),
            @Result(property = "amount", column = "amount"),
            @Result(property = "user", column = "user_id", javaType=User.class, one=@One(select="com.softserve.if072.restservice.dao.UserDAO.getByID")),
            @Result(property = "product", column = "product_id", javaType=Product.class, one=@One(select="com.softserve.if072.restservice.dao.ProductDAO.getByID"))
    })
    public Storage getByID(@Param("id") int id);

    @Override
    @Insert("INSERT INTO storage (user_id, product_id, amount) VALUES (#{user.id}, #{product.id}, #{amount})")
    @Options(useGeneratedKeys = true)
    public void insert(Storage storage);

    @Override
    @Update("UPDATE storage SET user_id=#{user.id}, product_id=#{product.id}, amount=#{amount} WHERE id=#{id}")
    public void update(Storage storage);

    @Override
    @Delete("DELETE FROM storage WHERE id = #{id}")
    public void delete(int id) ;
}

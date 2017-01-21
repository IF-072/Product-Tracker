package com.softserve.if072.restservice.dao.mybatisdao;

import com.softserve.if072.common.model.Product;
import com.softserve.if072.common.model.StorageSimple;
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

import java.util.List;

/**
 * Created by dyndyn on 17.01.2017.
 */
public interface StorageDAO extends DAO<StorageSimple> {

    @Select("SELECT * FROM storage WHERE user_id = #{user_id}")
    @Results(value = {
            @Result(property = "id", column = "id"),
            @Result(property = "amount", column = "amount"),
            @Result(property = "user", column = "user_id", javaType=User.class,
                    one=@One(select="com.softserve.if072.restservice.dao.mybatisdao.UserDAO.getByID")),
            @Result(property = "product", column = "product_id", javaType=Product.class,
                    one=@One(select="com.softserve.if072.restservice.dao.mybatisdao.ProductDAO.getByID"))
    })
    public List<StorageSimple> getByUserID(int user_id);

    @Override
    @Select("SELECT * FROM storage WHERE id = #{id}")
    @Results(value = {
            @Result(property = "id", column = "id"),
            @Result(property = "amount", column = "amount"),
            @Result(property = "user", column = "user_id", javaType=User.class,
                    one=@One(select="com.softserve.if072.restservice.dao.mybatisdao.UserDAO.getByID")),
            @Result(property = "product", column = "product_id", javaType=Product.class,
                    one=@One(select="com.softserve.if072.restservice.dao.mybatisdao.ProductDAO.getByID"))
    })
    public StorageSimple getByID(@Param("id") int id);

    @Override
    @Insert("INSERT INTO storage (user_id, product_id, amount) VALUES (#{user.id}, #{product.id}, #{amount})")
    @Options(useGeneratedKeys = true)
    public void insert(StorageSimple storage);

    @Override
    @Update("UPDATE storage SET user_id=#{user.id}, product_id=#{product.id}, amount=#{amount} WHERE id=#{id}")
    public void update(StorageSimple storage);

    @Override
    @Delete("DELETE FROM storage WHERE id = #{id}")
    public void delete(int id) ;
}

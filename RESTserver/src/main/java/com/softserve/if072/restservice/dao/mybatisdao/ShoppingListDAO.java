package com.softserve.if072.restservice.dao.mybatisdao;

import com.softserve.if072.common.model.Product;
import com.softserve.if072.common.model.ShoppingListSimple;
import com.softserve.if072.common.model.User;
import com.softserve.if072.restservice.dao.DAO;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * Created by dyndyn on 17.01.2017.
 */
public interface ShoppingListDAO extends DAO<ShoppingListSimple> {

    @Select("SELECT * FROM shopping_list WHERE user_id = #{user_id}")
    @Results(value = {
            @Result(property = "id", column = "id"),
            @Result(property = "amount", column = "amount"),
            @Result(property = "user", column = "user_id", javaType=User.class, one=@One(select="com.softserve.if072.restservice.dao.mybatisdao.UserDAO.getByID")),
            @Result(property = "product", column = "product_id", javaType=Product.class, one=@One(select="com.softserve.if072.restservice.dao.mybatisdao.ProductDAO.getByID"))
    })
    public List<ShoppingListSimple> getByUserID(int user_id);

    @Override
    @Select("SELECT * FROM shopping_list WHERE id = #{id}")
    @Results(value = {
            @Result(property = "id", column = "id"),
            @Result(property = "amount", column = "amount"),
            @Result(property = "user", column = "user_id", javaType=User.class, one=@One(select="com.softserve.if072.restservice.dao.mybatisdao.UserDAO.getByID")),
            @Result(property = "product", column = "product_id", javaType=Product.class, one=@One(select="com.softserve.if072.restservice.dao.mybatisdao.ProductDAO.getByID"))
    })
    public ShoppingListSimple getByID(@Param("id") int id);

    @Override
    @Insert("INSERT INTO shopping_list (user_id, product_id, amount) VALUES (#{user.id}, #{product.id}, #{amount})")
    @Options(useGeneratedKeys = true)
    public void insert(ShoppingListSimple shoppingList);

    @Override
    @Update("UPDATE shopping_list SET user_id=#{user.id}, product_id=#{product.id}, amount=#{amount} WHERE id=#{id}")
    public void update(ShoppingListSimple shoppingList);

    @Override
    @Delete("DELETE FROM shopping_list WHERE id = #{id}")
    public void delete(int id) ;
}

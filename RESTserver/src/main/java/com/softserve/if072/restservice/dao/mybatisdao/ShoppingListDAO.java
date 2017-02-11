package com.softserve.if072.restservice.dao.mybatisdao;

import com.softserve.if072.common.model.*;
import com.softserve.if072.restservice.dao.DAO;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.Delete;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by dyndyn on 17.01.2017.
 */
@Repository
public interface ShoppingListDAO extends DAO<ShoppingList> {

    @Select("SELECT * FROM shopping_list WHERE user_id = #{user_id}")
    @Results(value = {
            @Result(property = "amount", column = "amount"),
            @Result(property = "user", column = "user_id", javaType=User.class,
                    one=@One(select="com.softserve.if072.restservice.dao.mybatisdao.UserDAO.getByID")),
            @Result(property = "product", column = "product_id", javaType=Product.class,
                    one=@One(select="com.softserve.if072.restservice.dao.mybatisdao.ProductDAO.getByID"))
    })
    public List<ShoppingList> getByUserID(int user_id);

    @Select("SELECT id, name, description, shopping_list.user_id, category_id, unit_id, is_enabled FROM product RIGHT JOIN "
            + "shopping_list ON product.id = shopping_list.product_id WHERE shopping_list.user_id = #{userId}")
    @Results(value = {
            @Result(property = "user", column = "user_id", javaType = User.class,
                    one = @One(select = "com.softserve.if072.restservice.dao.mybatisdao.UserDAO.getByID")),
            @Result(property = "category", column = "category_id", javaType = Category.class,
                    one = @One(select = "com.softserve.if072.restservice.dao.mybatisdao.CategoryDAO.getByID")),
            @Result(property = "unit", column = "unit_id", javaType = Unit.class,
                    one = @One(select = "com.softserve.if072.restservice.dao.mybatisdao.UnitDAO.getByID")),
            @Result(property = "isEnabled", column = "is_enabled")
    })
    List<Product> getProductsByUserId(int userId);

    @Select("SELECT * FROM shopping_list WHERE user_id=#{user.id} AND product_id=#{product.id}")
    @Results(value = {
            @Result(property = "amount", column = "amount"),
            @Result(property = "user", column = "user_id", javaType=User.class,
                    one=@One(select="com.softserve.if072.restservice.dao.mybatisdao.UserDAO.getByID")),
            @Result(property = "product", column = "product_id", javaType=Product.class,
                    one=@One(select="com.softserve.if072.restservice.dao.mybatisdao.ProductDAO.getByID"))
    })
    public ShoppingList getByClass(ShoppingList shoppingList);

    @Override
    @Insert("INSERT INTO shopping_list (user_id, product_id, amount) VALUES (#{user.id}, #{product.id}, #{amount})")
    @Options(useGeneratedKeys = true)
    public void insert(ShoppingList shoppingList);

    @Override
    @Update("UPDATE shopping_list SET amount=#{amount} WHERE user_id=#{user.id} AND product_id=#{product.id}")
    public void update(ShoppingList shoppingList);

    @Delete("DELETE FROM shopping_list WHERE user_id=#{user.id} AND product_id=#{product.id}")
    public void delete(ShoppingList shoppingList) ;
}


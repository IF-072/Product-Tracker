package com.softserve.if072.restservice.dao.mybatisdao;

import com.softserve.if072.common.model.Product;
import com.softserve.if072.common.model.ShoppingList;
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

    @Override
    @Insert("INSERT INTO shopping_list (user_id, product_id, amount) VALUES (#{user.id}, #{product.id}, #{amount})")
    @Options(useGeneratedKeys = true)
    public void insert(ShoppingList shoppingList);

    @Override
    @Update("UPDATE shopping_list SET amount=#{amount} WHERE user_id=#{user.id}, product_id=#{product.id}")
    public void update(ShoppingList shoppingList);

    @Delete("DELETE FROM shopping_list WHERE user_id=#{user.id}, product_id=#{product.id}")
    public void delete(ShoppingList shoppingList) ;
}


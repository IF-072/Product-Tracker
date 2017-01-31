package com.softserve.if072.restservice.dao.mybatisdao;

import com.softserve.if072.common.model.Category;
import com.softserve.if072.common.model.Image;
import com.softserve.if072.common.model.Unit;
import com.softserve.if072.common.model.User;
import com.softserve.if072.restservice.dao.DAO;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface UserDAO extends DAO<User> {
    String SELECT_ALL = "SELECT id, name, email, password, is_enabled FROM user";
    String SELECT_BY_ID = "SELECT id, name, email, password, is_enabled FROM user WHERE id = #{id}";
    String INSERT = "INSERT INTO user (name, email, password, is_enabled) VALUES (#{name}, #{email}, #{password}, #{isEnabled})";
    String UPDATE = "UPDATE user SET name = #{name}, email = #{email}, password = #{password}, isEnabled = #{isEnabled} WHERE id=#{id}";
    String DELETE = "UPDATE user SET is_enabled = 0 WHERE id = #{id}";

    @Override
    @Select(SELECT_ALL)
    @Results(value = {
            @Result(property = "id", column = "id"),
            @Result(property = "name", column = "name"),
            @Result(property = "email", column = "email"),
            @Result(property = "password", column = "password"),
            @Result(property = "isEnabled", column = "is_enabled"),
            @Result(property = "stores", column = "id", javaType = List.class,
                    many = @Many(select = "com.softserve.if072.restservice.dao.mybatisdao.StorageDAO.getByUserID")),
            @Result(property = "products", column = "id", javaType = List.class,
                    many = @Many(select = "com.softserve.if072.restservice.dao.mybatisdao.ProductDAO.getByUserID")),
            @Result(property = "carts", column = "id", javaType = List.class,
                    many = @Many(select = "com.softserve.if072.restservice.dao.mybatisdao.CartDAO.getByUserID")),
            @Result(property = "categories", column = "id", javaType = List.class,
                    many = @Many(select = "com.softserve.if072.restservice.dao.mybatisdao.CategoryDAO.getByUserID")),
            @Result(property = "shoppingLists", column = "id", javaType = List.class,
                    many = @Many(select = "com.softserve.if072.restservice.dao.mybatisdao.ShoppingListDAO.getByUserID")),
            @Result(property = "storages", column = "id", javaType = List.class,
                    many = @Many(select = "com.softserve.if072.restservice.dao.mybatisdao.StorageDAO.getByUserID")),
    })
    List<User> getAll();

    @Override
    @Select(SELECT_BY_ID)
    @Results(value = {
            @Result(property = "id", column = "id"),
            @Result(property = "name", column = "name"),
            @Result(property = "email", column = "email"),
            @Result(property = "password", column = "password"),
            @Result(property = "isEnabled", column = "is_enabled"),
    })
    User getByID(int id);

    @Override
    @Insert(INSERT)
    @Options(useGeneratedKeys = true)
    void insert(User user);

    @Override
    @Update(UPDATE)
    void update(User user);

    @Override
    @Update(DELETE)
    void delete(int id);
}

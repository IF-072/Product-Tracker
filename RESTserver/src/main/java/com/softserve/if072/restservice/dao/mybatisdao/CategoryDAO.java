package com.softserve.if072.restservice.dao.mybatisdao;

import com.softserve.if072.common.model.Category;
import com.softserve.if072.common.model.User;
import com.softserve.if072.restservice.dao.DAO;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * The interface contains SQL-queries for manipulating categories data
 */

public interface CategoryDAO extends DAO<Category> {

    @Select("SELECT id, name, user_id, is_enabled FROM category WHERE user_id = #{userID} AND is_enabled = 1")
    @Results(value = {
            @Result(property = "id", column = "id"),
            @Result(property = "name", column = "name"),
            @Result(property = "user", column = "user_id", javaType = User.class, one = @One(select = "com.softserve.if072.restservice.dao.mybatisdao.UserDAO.getByID")),
            @Result(property = "isEnabled", column = "is_enabled")
    })
    List<Category> getByUserID(int userID);

    @Override
    @Select("SELECT id, name, user_id FROM category WHERE id = #{id}")
    @Results(value = {
            @Result(property = "id", column = "id"),
            @Result(property = "name", column = "name"),
            @Result(property = "user", column = "user_id", javaType = User.class, one = @One(select = "com.softserve.if072.restservice.dao.mybatisdao.UserDAO.getByID")),
            @Result(property = "isEnabled", column = "is_enabled")
    })
    Category getByID(int id);

    @Override
    @Insert("INSERT INTO category(name, user_id) VALUES(#{name}, #{user.id})")
    void insert(Category category);


    @Override
    @Update("UPDATE category SET name = #{name}, user_id = #{user.id} WHERE id = #{id}")
    void update(Category category);

    @Override
    @Delete("UPDATE category SET is_enabled = 0 WHERE id = #{id}")
    void deleteById(int id);

}

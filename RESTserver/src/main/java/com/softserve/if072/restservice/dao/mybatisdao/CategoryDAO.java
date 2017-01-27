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

    @Override
    @Select("SELECT * FROM category")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "name", column = "name"),
            @Result(property = "user", column = "user_id", javaType = User.class, one = @One(select = "com.softserve.if072.restservice.dao.mybatisdao.UserDAO.getAll"))
    })
    List<Category> getAll();

    @Override
    @Select("SELECT * FROM category WHERE id = #{id}")
    @Results(value = {
            @Result(property = "id", column = "id"),
            @Result(property = "name", column = "name"),
            @Result(property = "user", column = "user_id", javaType = User.class, one = @One(select = "com.softserve.if072.restservice.dao.mybatisdao.UserDAO.getByID"))
    })
     Category getByID(@Param("id") int id);

    @Override
    @Insert("INSERT INTO category(name, user_id) VALUES(#{name}, #{user.id})")
    @Options(useGeneratedKeys = true)
    void insert(Category category);


    @Override
    @Update("UPDATE category SET name = #{name}, user_id = #{user.id} WHERE id = #{id}")
    void update(Category category);

    @Override
    @Delete("DELETE FROM category WHERE id = #{id}")
    void delete(@Param("id") int id);
}

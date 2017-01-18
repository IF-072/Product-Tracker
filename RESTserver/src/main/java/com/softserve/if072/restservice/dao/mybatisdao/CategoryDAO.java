package com.softserve.if072.restservice.dao;

import com.softserve.if072.common.model.Category;
import com.softserve.if072.restservice.dao.core.DAO;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface CategoryDAO extends DAO<Category> {
    @Override
    @Results({
            @Result(property = "user", column = "user_id")
    })
    @Select("SELECT id, name, user_id FROM category")
    List<Category> getAll();

    @Override
    @Results({
            @Result(property = "user", column = "user_id")
    })
    @Select("SELECT id, name, user_id FROM category WHERE id = ${id}")
    Category getByID(int id);

    @Override
    @Insert("INSERT INTO category(name, user_id) VALUES(#{name}, #{user.id})")
    void insert(Category category);

    @Override
    @Update("UPDATE category SET name = #{name}, user_id = #{user.id} WHERE id = #{id}")
    void update(Category category);

    @Override
    @Delete("DELETE FROM category WHERE id = #{id}")
    void delete(int id);
}

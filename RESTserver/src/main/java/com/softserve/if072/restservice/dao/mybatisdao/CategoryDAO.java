package com.softserve.if072.restservice.dao.mybatisdao;

import com.softserve.if072.common.model.Category;
import com.softserve.if072.common.model.User;
import com.softserve.if072.restservice.dao.DAO;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * The interface contains SQL-queries for manipulating categories data
 *
 * @author Pavlo Bendus
 */

public interface CategoryDAO extends DAO<Category> {

    /**
     * Selects all categories for specified user's id
     *
     * @param userID id of current user
     * @return list of categories
     */

    @Select("SELECT id, name, user_id, is_enabled FROM category WHERE user_id = #{userID} AND is_enabled = 1")
    @Results(value = {
            @Result(property = "id", column = "id"),
            @Result(property = "name", column = "name"),
            @Result(property = "user", column = "user_id", javaType = User.class, one = @One(select = "com.softserve.if072.restservice.dao.mybatisdao.UserDAO.getByID")),
            @Result(property = "isEnabled", column = "is_enabled")
    })
    List<Category> getByUserID(@Param("userID") int userID);

    /**
     * Selects specified category by its id
     *
     * @param id category id
     * @return category's object
     */

    @Override
    @Select("SELECT id, name, user_id, is_enabled FROM category WHERE id = #{id} AND is_enabled = 1")
    @Results(value = {
            @Result(property = "id", column = "id"),
            @Result(property = "name", column = "name"),
            @Result(property = "user", column = "user_id", javaType = User.class, one = @One(select = "com.softserve.if072.restservice.dao.mybatisdao.UserDAO.getByID")),
            @Result(property = "isEnabled", column = "is_enabled")
    })
    Category getByID(@Param("id") int id);

    /**
     * Checks whether category already was added to the database
     *
     * @param name category's name
     * @param userID id of current user
     * @return category's object
     */

    @Select("SELECT id, name, user_id, is_enabled FROM category WHERE user_id = #{userID} AND name = #{name}")
    @Results(value = {
            @Result(property = "id", column = "id"),
            @Result(property = "name", column = "name"),
            @Result(property = "user", column = "user_id", javaType = User.class, one = @One(select = "com.softserve.if072.restservice.dao.mybatisdao.UserDAO.getByID")),
            @Result(property = "isEnabled", column = "is_enabled")
    })
    Category getByNameAndUserID(@Param("name") String name, @Param("userID") int userID);

    /**
     * Inserts new category to the database
     *
     * @param category category's object
     */

    @Override
    @Insert("INSERT INTO category(name, user_id) VALUES(#{name}, #{user.id})")
    void insert(Category category);

    /**
     * Updates specified category in the database
     *
     * @param category updated category's object
     */

    @Override
    @Update("UPDATE category SET name = #{name}, user_id = #{user.id} WHERE id = #{id}")
    void update(Category category);

    /**
     * Restores deleted category from the database
     *
     * @param category that should be restored
     */

    @Update("UPDATE category SET is_enabled = 1 WHERE id = #{id}")
    void restore(Category category);

    /**
     * Provides soft-deleting of specified category
     *
     * @param id category's id
     */

    @Override
    @Delete("UPDATE category SET is_enabled = 0 WHERE id = #{id}")
    void deleteById(@Param("id") int id);

}

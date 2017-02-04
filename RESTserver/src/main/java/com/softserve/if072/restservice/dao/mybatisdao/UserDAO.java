package com.softserve.if072.restservice.dao.mybatisdao;

import com.softserve.if072.common.model.Role;
import com.softserve.if072.common.model.User;
import com.softserve.if072.restservice.dao.DAO;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * This class allows to get user model from a database.
 *
 * @author Oleh Pochernin
 */
public interface UserDAO extends DAO<User> {
    String SELECT_ALL = "SELECT id, name, email, password, role_id, is_enabled FROM user";
    String SELECT_BY_ID = "SELECT id, name, email, password, role_id, is_enabled FROM user WHERE id = #{id}";
    String SELECT_BY_USERNAME = "SELECT id, name, email, password, role_id, is_enabled FROM user WHERE email = #{username}";
    String INSERT = "INSERT INTO user (name, email, password, role_id, is_enabled) VALUES (#{name}, #{email}, #{password}, #{role.id}, #{isEnabled})";
    String UPDATE = "UPDATE user SET name = #{name}, email = #{email}, password = #{password}, role_id = #{role.id}, isEnabled = #{isEnabled} WHERE id=#{id}";
    String DELETE = "UPDATE user SET is_enabled = 0 WHERE id = #{id}";

    /**
     * Pulls all users from the database and returns them as list.
     *
     * @return list of all users
     */
    @Override
    @Select(SELECT_ALL)
    @Results(value = {
            @Result(property = "id", column = "id"),
            @Result(property = "name", column = "name"),
            @Result(property = "email", column = "email"),
            @Result(property = "password", column = "password"),
            @Result(property = "role", column = "role_id", javaType = Role.class, one = @One(select = "com.softserve.if072.restservice.dao.mybatisdao.RoleDAO.getByID")),
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

    /**
     * Pulls all users from the database with specified id and returns them as list.
     *
     * @param id unique user's id
     * @return user with a specified id
     */
    @Override
    @Select(SELECT_BY_ID)
    @Results(value = {
            @Result(property = "id", column = "id"),
            @Result(property = "name", column = "name"),
            @Result(property = "email", column = "email"),
            @Result(property = "password", column = "password"),
            @Result(property = "role", column = "role_id", javaType = Role.class, one = @One(select = "com.softserve.if072.restservice.dao.mybatisdao.RoleDAO.getByID")),
            @Result(property = "isEnabled", column = "is_enabled"),
    })
    User getByID(int id);

    /**
     * Puts user to the database.
     *
     * @param user model you want to put to the database
     */
    @Override
    @Insert(INSERT)
    @Options(useGeneratedKeys = true)
    void insert(User user);

    /**
     * Update information about user in the database.
     *
     * @param user model you want to put to the database
     */
    @Override
    @Update(UPDATE)
    void update(User user);

    /**
     * Deletes user from the database.
     *
     * @param id id of user you want to delete
     */
    @Override
    @Update(DELETE)
    void deleteById(int id);

    /**
     * Returns one user from the database with specified username.
     *
     * @param username unique user's
     * @return user with a specified username
     */
    @Select(SELECT_BY_USERNAME)
    @Results(value = {
            @Result(property = "id", column = "id"),
            @Result(property = "name", column = "name"),
            @Result(property = "email", column = "email"),
            @Result(property = "password", column = "password"),
            @Result(property = "role", column = "role_id", javaType = Role.class, one = @One(select = "com.softserve.if072.restservice.dao.mybatisdao.RoleDAO.getByID")),
            @Result(property = "isEnabled", column = "is_enabled"),
    })
    User getByUsername(@Param("username") String username);
}

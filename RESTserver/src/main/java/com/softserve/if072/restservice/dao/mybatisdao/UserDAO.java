package com.softserve.if072.restservice.dao.mybatisdao;

import com.softserve.if072.common.model.Role;
import com.softserve.if072.common.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.softserve.if072.restservice.dao.Queries.INSERT_USER;
import static com.softserve.if072.restservice.dao.Queries.SELECT_ALL_USERS;
import static com.softserve.if072.restservice.dao.Queries.SELECT_USER_BY_ID;
import static com.softserve.if072.restservice.dao.Queries.UPDATE_USER;
import static com.softserve.if072.restservice.dao.Queries.DELETE_USER;
import static com.softserve.if072.restservice.dao.Queries.SELECT_USER_BY_USERNAME;

/**
 * This class allows to get user model from a database.
 *
 * @author Oleh Pochernin
 */
@Repository
public interface UserDAO {
    /**
     * Pulls all users from the database and returns them as list.
     *
     * @return list of all users
     */
    @Select(SELECT_ALL_USERS)
    @Results(value = {
            @Result(property = "id", column = "id"),
            @Result(property = "name", column = "name"),
            @Result(property = "email", column = "email"),
            @Result(property = "password", column = "password"),
            @Result(property = "role", column = "role", javaType = Role.class, typeHandler=org.apache.ibatis.type.EnumTypeHandler.class),
            @Result(property = "isEnabled", column = "is_enabled"),
            @Result(property = "premiumExpiresTime", column = "premium_expires_time"),
            @Result(property = "stores", column = "id", javaType = List.class,
                    many = @Many(select = "com.softserve.if072.restservice.dao.mybatisdao.StorageDAO.getAllStoresByUser")),
            @Result(property = "products", column = "id", javaType = List.class,
                    many = @Many(select = "com.softserve.if072.restservice.dao.mybatisdao.ProductDAO.getAllByUserId")),
            @Result(property = "carts", column = "id", javaType = List.class,
                    many = @Many(select = "com.softserve.if072.restservice.dao.mybatisdao.CartDAO.getByUserId")),
            @Result(property = "categories", column = "id", javaType = List.class,
                    many = @Many(select = "com.softserve.if072.restservice.dao.mybatisdao.CategoryDAO.getByUserID")),
            @Result(property = "shoppingLists", column = "id", javaType = List.class,
                    many = @Many(select = "com.softserve.if072.restservice.dao.mybatisdao.ShoppingListDAO.getByUserID")),
            @Result(property = "storages", column = "id", javaType = List.class,
                    many = @Many(select = "com.softserve.if072.restservice.dao.mybatisdao.StorageDAO.getByUserID"))
    })
    List<User> getAll();

    /**
     * Pulls all users from the database with specified id and returns them as list.
     *
     * @param id unique user's id
     * @return user with a specified id
     */
    @Select(SELECT_USER_BY_ID)
    @Results(value = {
            @Result(property = "id", column = "id"),
            @Result(property = "name", column = "name"),
            @Result(property = "email", column = "email"),
            @Result(property = "password", column = "password"),
            @Result(property = "role", column = "role", javaType = Role.class, typeHandler=org.apache.ibatis.type.EnumTypeHandler.class),
            @Result(property = "isEnabled", column = "is_enabled"),
            @Result(property = "premiumExpiresTime", column = "premium_expires_time")
    })
    User getByID(int id);

    /**
     * Puts user to the database.
     *
     * @param user model you want to put to the database
     */
    @Insert(INSERT_USER)
    void insert(User user);

    /**
     * Updates information about user in the database.
     *
     * @param user model you want to put to the database
     */
    @Update(UPDATE_USER)
    void update(User user);

    /**
     * Deletes user from the database.
     *
     * @param id id of user you want to delete
     */
    @Update(DELETE_USER)
    void deleteById(int id);

    /**
     * Returns one user from the database with specified username.
     *
     * @param username unique user's
     * @return user with a specified username
     */
    @Select(SELECT_USER_BY_USERNAME)
    @Results(value = {
            @Result(property = "id", column = "id"),
            @Result(property = "name", column = "name"),
            @Result(property = "email", column = "email"),
            @Result(property = "password", column = "password"),
            @Result(property = "role", column = "role", javaType = Role.class, typeHandler=org.apache.ibatis.type.EnumTypeHandler.class),
            @Result(property = "isEnabled", column = "is_enabled"),
            @Result(property = "premiumExpiresTime", column = "premium_expires_time")
    })
    User getByUsername(@Param("username") String username);
}

package com.softserve.if072.restservice.dao;

import com.softserve.if072.common.model.User;
import com.softserve.if072.restservice.dao.core.DAO;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface UserDAO extends DAO<User> {

    @Override
    @Select("SELECT id, name, email, password, token, is_enabled FROM user")
    List<User> getAll();

    @Override
    @Select("SELECT id, name, email, password, token, is_enabled FROM user WHERE id = #{id}")
    User getByID(int id);

    @Override
    @Insert("INSERT INTO user (name, email, password, token, is_enabled)" +
            "VALUES (#{name}, #{email}, #{password}, #{token}, #{isEnabled})")
    @Options(useGeneratedKeys = true)
    void insert(User user);

    @Override
    @Update("UPDATE user" +
            "SET name = #{name}, email = #{email}, password = #{password}, token = #{token}, isEnabled = #{isEnabled})" +
            "WHERE id=#{id}")
    void update(User user);

    @Override
    @Delete("DELETE FROM user WHERE id = #{id}")
    void delete(int id);
}

package com.softserve.if072.restservice.dao.mybatisdao;

import com.softserve.if072.common.model.*;
import com.softserve.if072.restservice.dao.DAO;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Contains MyBatis methods for working with Role table
 */

@Repository
public interface RoleDAO extends DAO<Role> {

    @Override
    @Select("SELECT id, name FROM role")
    @Results(value = {
            @Result(property = "id", column = "id"),
            @Result(property = "authority", column = "name"),
    })
    List<Role> getAll();

    @Override
    @Select("SELECT id, name FROM role WHERE id = #{id}")
    @Results(value = {
            @Result(property = "id", column = "id"),
            @Result(property = "authority", column = "name"),
    })
    Role getByID(int id);

    @Override
    @Insert("INSERT into role(name) VALUES(#{name})")
    void insert(Role role);

    @Override
    @Update("UPDATE role SET name = #{name} WHERE id = #{id}")
    void update(Role role);

    @Override
    @Delete("DELETE FROM role WHERE id = #{id}")
    void deleteById(int id);
}

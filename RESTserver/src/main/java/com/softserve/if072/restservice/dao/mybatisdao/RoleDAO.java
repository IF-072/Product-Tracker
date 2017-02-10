package com.softserve.if072.restservice.dao.mybatisdao;

import com.softserve.if072.common.model.Role;
import com.softserve.if072.restservice.dao.DAO;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Contains MyBatis methods for working with Role table
 */

@Repository
public interface RoleDAO extends DAO<Role> {

    @Override
    @Select("SELECT id, name, description FROM role")
    @Results(value = {
            @Result(property = "id", column = "id"),
            @Result(property = "authority", column = "name"),
            @Result(property = "description", column = "description"),
    })
    List<Role> getAll();

    @Override
    @Select("SELECT id, name, description FROM role WHERE id = #{id}")
    @Results(value = {
            @Result(property = "id", column = "id"),
            @Result(property = "authority", column = "name"),
            @Result(property = "description", column = "description"),
    })
    Role getByID(int id);

    @Override
    @Insert("INSERT into role(name, description) VALUES(#{name}, #{description})")
    void insert(Role role);

    @Override
    @Update("UPDATE role SET name = #{name}, description=#{description} WHERE id = #{id}")
    void update(Role role);

    @Override
    @Delete("DELETE FROM role WHERE id = #{id}")
    void deleteById(int id);
}

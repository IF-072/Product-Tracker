package com.softserve.if072.restservice.dao.mybatisdao;

import com.softserve.if072.common.model.Store;
import com.softserve.if072.common.model.User;
import com.softserve.if072.restservice.dao.DAO;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface StoreDAO extends DAO<Store> {
    @Override
    @Results(value = {
            @Result(property = "user", column = "user_id",
                    javaType = User.class, one = @One(select = "com.softserve.if072.restservice.dao.mybatisdao.UserDAO.getByID")),
            @Result(property = "isActive", column = "is_active")
    })
    @Select("SELECT id, name, address, user_id, is_active FROM store")
    List<Store> getAll();

    @Override
    @Results(value = {
            @Result(property = "user", column = "user_id",
                    javaType = User.class, one = @One(select = "com.softserve.if072.restservice.dao.mybatisdao.UserDAO.getByID")),
            @Result(property = "isActive", column = "is_active")
    })
    @Select("SELECT id, name, address, user_id, is_active FROM store WHERE id = #{id}")
    Store getByID(int id);

    @Override
    @Insert("INSERT into store(name, address, user_id, is_active) VALUES(#{name}, #{address}, #{user.id}, #{isActive}))")
    void insert(Store store);

    @Override
    @Update("UPDATE store SET name = #{name}, address = #{address}, is_active = #{isActive} WHERE id = #{id}")
    void update(Store store);

    @Override
    @Delete("DELETE FROM store WHERE id = #{id}")
    void delete(int id);
}

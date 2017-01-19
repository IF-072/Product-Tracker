package com.softserve.if072.restservice.dao.mybatisdao;

import com.softserve.if072.common.model.Store;
import com.softserve.if072.restservice.dao.DAO;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface StoreDAO extends DAO<Store> {
    @Override
    @Results({
            @Result(property = "user", column = "user_id"),
            @Result(property = "isActive", column = "is_active")
    })
    @Select("SELECT id, name, address, user_id, is_active FROM store")
    List<Store> getAll();

    @Override
    @Results({
            @Result(property = "user", column = "user_id"),
            @Result(property = "isActive", column = "is_active")
    })
    @Select("SELECT id, name, address, user_id, is_active FROM store WHERE id = #{id}")
    Store getByID(int id);

    @Override
    @Insert("INSERT into store(name, address, user_id, is_active) VALUES(#{name}, #{address}, #{user}, #{isActive}))")
    void insert(Store store);

    @Override
    @Update("UPDATE store SET name = #{name}, address = #{address}, is_active = #{isActive} WHERE id = #{id}")
    void update(Store store);

    @Override
    @Delete("DELETE FROM store WHERE id = #{id}")
    void delete(int id);
}

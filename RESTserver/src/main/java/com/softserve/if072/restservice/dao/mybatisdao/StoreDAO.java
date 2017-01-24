package com.softserve.if072.restservice.dao.mybatisdao;

import com.softserve.if072.common.model.Store;
import com.softserve.if072.common.model.User;
import com.softserve.if072.restservice.dao.DAO;
import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.Delete;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Contain MyBatis methods wor working with Store DB
 */

@Repository
public interface StoreDAO extends DAO<Store> {

    @Override
    @Results(value = {
            @Result(property = "user", column = "user_id",
                    javaType = User.class,
                    one = @One(select = "com.softserve.if072.restservice.dao.mybatisdao.UserDAO.getByID")),
            @Result(property = "isActive", column = "is_active")
    })
    @Select("SELECT id, name, address, user_id, is_active FROM store")
    List<Store> getAll();

    @Override
    @Results(value = {
            @Result(property = "user", column = "user_id",
                    javaType = User.class,
                    one = @One(select = "com.softserve.if072.restservice.dao.mybatisdao.UserDAO.getByID")),
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

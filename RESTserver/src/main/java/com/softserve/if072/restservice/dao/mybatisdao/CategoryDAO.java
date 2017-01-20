package com.softserve.if072.restservice.dao.mybatisdao;

import com.softserve.if072.common.model.Category;
import com.softserve.if072.common.model.User;
import com.softserve.if072.restservice.dao.DAO;
import org.apache.ibatis.annotations.*;

public interface CategoryDAO extends DAO<Category> {

//    @Override
//    @Results({
//            @Result(property = "user", column = "user_id")
//    })
//    @Select("SELECT id, name, user_id FROM category")
//    List<Category> getAll();

    @Override
    @Select("SELECT * FROM category WHERE id = #{id}")
    @Results(value = {
            @Result(property = "id", column = "id"),
            @Result(property = "name", column = "name"),
            @Result(property = "user", column = "user_id", javaType = User.class, one = @One(select = "com.softserve.if072.restservice.dao.mybatisdao.UserDAO.getByID"))
    })
     Category getByID(@Param("id") int id);

//    @Override
//    @Insert("INSERT INTO category(name, user_id) VALUES(#{name}, #{user.id})")
//    void insert(Category category);
//
//    @Override
//    @Update("UPDATE category SET name = #{name}, user_id = #{user.id} WHERE id = #{id}")
//    void update(Category category);
//
//    @Override
//    @Delete("DELETE FROM category WHERE id = #{id}")
//    void delete(int id);
}

package com.softserve.if072.restservice.dao;

import com.softserve.if072.common.model.Product;
import com.softserve.if072.restservice.dao.core.DAO;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface ProductDAO extends DAO<Product> {
    @Override
    @Select("SELECT * FROM product")
    List<Product> getAll();

    @Override
    @Select("SELECT * FROM product WHERE id = #{id}")
    Product getByID(@Param("id") int id);

    @Override
    @Insert("INSERT INTO product (name, description, image_id, user_id, category_id, unit_id) " +
            "VALUES (#{name}, #{description}, #{image.id}), #{user.id}, #{category.id}, #{unit.id}")
    void insert(Product product);

    @Override
    @Update("UPDATE product SET name = #{name}, description = #{description}, image_id = #{image.id}," +
            "user_id = #{user.id}, category_id = #{category.id}, unit_id = #{unit.id} WHERE id = #{id}")
    void update(Product product);

    @Override
    @Update("UPDATE product SET is_active = #{0} WHERE id = #{id}")
    void delete(int id);
}

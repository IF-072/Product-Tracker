package com.softserve.if072.restservice.dao.mybatisdao;

import com.softserve.if072.common.model.*;
import com.softserve.if072.restservice.dao.DAO;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * The interface contains MyBatis annotations and SQL-queries for working with product's data
 *
 * @author Vitaliy Malisevych
 */

@Repository
public interface ProductDAO extends DAO<Product> {
    @Override
    @Select("SELECT * FROM product")
    @Results(value = {
            @Result(property = "id", column = "id"),
            @Result(property = "name", column = "name"),
            @Result(property = "description", column = "description"),
            @Result(property = "image", column = "image_id", javaType = Image.class, one = @One(select = "com.softserve.if072.restservice.dao.mybatisdao.ImageDAO.getByID")),
            @Result(property = "user", column = "user_id", javaType = User.class, one = @One(select = "com.softserve.if072.restservice.dao.mybatisdao.UserDAO.getByID")),
            @Result(property = "category", column = "category_id", javaType = Category.class, one = @One(select = "com.softserve.if072.restservice.dao.mybatisdao.CategoryDAO.getByID")),
            @Result(property = "unit", column = "unit_id", javaType = Unit.class, one = @One(select = "com.softserve.if072.restservice.dao.mybatisdao.UnitDAO.getByID")),
            @Result(property = "isEnabled", column = "is_enabled"),
            @Result(property = "stores", column = "id", javaType = List.class, many = @Many(select = "com.softserve.if072.restservice.dao.mybatisdao.StoreDAO.getStoresByProductID"))
    })
    List<Product> getAll();

    @Override
    @Select("SELECT * FROM product WHERE id = #{id}")
    @Results(value = {
            @Result(property = "id", column = "id"),
            @Result(property = "name", column = "name"),
            @Result(property = "description", column = "description"),
            @Result(property = "image", column = "image_id", javaType = Image.class, one = @One(select = "com.softserve.if072.restservice.dao.mybatisdao.ImageDAO.getByID")),
            @Result(property = "user", column = "user_id", javaType = User.class, one = @One(select = "com.softserve.if072.restservice.dao.mybatisdao.UserDAO.getByID")),
            @Result(property = "category", column = "category_id", javaType = Category.class, one = @One(select = "com.softserve.if072.restservice.dao.mybatisdao.CategoryDAO.getByID")),
            @Result(property = "unit", column = "unit_id", javaType = Unit.class, one = @One(select = "com.softserve.if072.restservice.dao.mybatisdao.UnitDAO.getByID")),
            @Result(property = "isEnabled", column = "is_enabled"),
            @Result(property = "stores", column = "id", javaType = List.class, many = @Many(select = "com.softserve.if072.restservice.dao.mybatisdao.StoreDAO.getStoresByProductID"))
    })
    Product getByID(@Param("id") int id);

    @Override
    @Insert("INSERT INTO product (name, description, image_id, user_id, category_id, unit_id, is_enabled) " +
            "VALUES (#{name}, #{description}, #{image.id}, #{user.id}, #{category.id}, #{unit.id}, #{isEnabled})")
    @Options(useGeneratedKeys = true)
    void insert(Product product);

    @Override
    @Update("UPDATE product SET name = #{name}, description = #{description}, image_id = #{image.id}," +
            "user_id = #{user.id}, category_id = #{category.id}, unit_id = #{unit.id} WHERE id = #{id}")
    void update(Product product);

    @Override
    @Update("UPDATE product SET is_enabled = 0 WHERE id = #{id}")
    void deleteById(int id);

    @Select("SELECT product.id, name, description, image_id, user_id, category_id, unit_id, is_enabled FROM stores_products " +
            "JOIN product ON product_id = product.id JOIN store ON store_id = store.id WHERE store.id = #{storeId}")
    @Results(value = {
            @Result(property = "image", column = "image_id", javaType = Image.class, one = @One(select = "com.softserve.if072.restservice.dao.mybatisdao.ImageDAO.getByID")),
            @Result(property = "user", column = "user_id", javaType = User.class, one = @One(select = "com.softserve.if072.restservice.dao.mybatisdao.UserDAO.getByID")),
            @Result(property = "category", column = "category_id", javaType = Category.class, one = @One(select = "com.softserve.if072.restservice.dao.mybatisdao.CategoryDAO.getByID")),
            @Result(property = "unit", column = "unit_id", javaType = Unit.class, one = @One(select = "com.softserve.if072.restservice.dao.mybatisdao.UnitDAO.getByID")),
            @Result(property = "isEnabled", column = "is_enabled")
    })
    public List<Product> getProductsByStoreId(int storeId);
}

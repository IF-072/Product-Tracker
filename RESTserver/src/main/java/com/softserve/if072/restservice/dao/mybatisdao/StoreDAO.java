package com.softserve.if072.restservice.dao.mybatisdao;

import com.softserve.if072.common.model.Category;
import com.softserve.if072.common.model.Image;
import com.softserve.if072.common.model.Product;
import com.softserve.if072.common.model.Store;
import com.softserve.if072.common.model.Unit;
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
 * Contains MyBatis methods for working with Store DB
 */

@Repository
public interface StoreDAO extends DAO<Store> {

    @Override
    @Select("SELECT id, name, address, user_id, is_active FROM store")
    @Results(value = {
            @Result(property = "user", column = "user_id",
                    javaType = User.class,
                    one = @One(select = "com.softserve.if072.restservice.dao.mybatisdao.UserDAO.getByID")),
            @Result(property = "isActive", column = "is_active")
    })
    List<Store> getAll();

    @Override
    @Select("SELECT id, name, address, user_id, is_active FROM store WHERE id = #{id}")
    @Results(value = {
            @Result(property = "user", column = "user_id",
                    javaType = User.class,
                    one = @One(select = "com.softserve.if072.restservice.dao.mybatisdao.UserDAO.getByID")),
            @Result(property = "isActive", column = "is_active")
    })
    Store getByID(int id);

    @Override
    @Insert("INSERT into store(name, address, user_id, is_active) VALUES(#{name}, #{address}, #{user.id}, #{isActive}))")
    void insert(Store store);

    @Override
    @Update("UPDATE store SET name = #{name}, address = #{address}, is_active = #{isActive} WHERE id = #{id}")
    void update(Store store);

    @Override
    @Delete("DELETE FROM store WHERE id = #{id}")
    void deleteById(int id);

    @Select("SELECT id, name, description, image_id, user_id, category_id, unit_id, is_active FROM product  JOIN " +
            "stores_products ON product.id = stores_products.product_id WHERE store_id = #{storeid}")
    @Results(value = {
            @Result(property = "id", column = "id"),
            @Result(property = "name", column = "name"),
            @Result(property = "description", column = "description"),
            @Result(property = "image", column = "image_id", javaType = Image.class, one = @One(select = "com.softserve.if072.restservice.dao.mybatisdao.ImageDAO.getByID")),
            @Result(property = "user", column = "user_id", javaType = User.class, one = @One(select = "com.softserve.if072.restservice.dao.mybatisdao.UserDAO.getByID")),
            @Result(property = "category", column = "category_id", javaType = Category.class, one = @One(select = "com.softserve.if072.restservice.dao.mybatisdao.CategoryDAO.getByID")),
            @Result(property = "unit", column = "unit_id", javaType = Unit.class, one = @One(select = "com.softserve.if072.restservice.dao.mybatisdao.UnitDAO.getByID")),
            @Result(property = "isActive", column = "is_active")
    })
    List<Product> getProductsByStoreId(int storeId);

}

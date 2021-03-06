package com.softserve.if072.restservice.dao.mybatisdao;

import com.softserve.if072.common.model.Category;
import com.softserve.if072.common.model.Image;
import com.softserve.if072.common.model.Product;
import com.softserve.if072.common.model.Store;
import com.softserve.if072.common.model.Unit;
import com.softserve.if072.common.model.User;
import com.softserve.if072.restservice.dao.DAO;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Contains MyBatis methods for working with Store DB
 *
 * @author Nazar Vynnyk
 */

@Repository
public interface StoreDAO extends DAO<Store> {

    @Override
    @Select("SELECT id, name, address, latitude, longitude, user_id, is_enabled FROM store")
    @Results(value = {
            @Result(property = "id", column = "id"),
            @Result(property = "name", column = "name"),
            @Result(property = "address", column = "address"),
            @Result(property = "latitude", column = "latitude"),
            @Result(property = "longitude", column = "longitude"),
            @Result(property = "user", column = "user_id", javaType = User.class,
                    one = @One(select = "com.softserve.if072.restservice.dao.mybatisdao.UserDAO.getByID")),
            @Result(property = "isEnabled", column = "is_enabled"),
    })
    List<Store> getAll();

    @Select("SELECT id, name, address, latitude, longitude, user_id, is_enabled FROM store WHERE user_id = #{userId} " +
            "AND is_enabled = 1")
    @Results(value = {
            @Result(property = "id", column = "id"),
            @Result(property = "name", column = "name"),
            @Result(property = "address", column = "address"),
            @Result(property = "latitude", column = "latitude"),
            @Result(property = "longitude", column = "longitude"),
            @Result(property = "user", column = "user_id", javaType = User.class,
                    one = @One(select = "com.softserve.if072.restservice.dao.mybatisdao.UserDAO.getByID")),
            @Result(property = "isEnabled", column = "is_enabled"),
            @Result(property = "products", column = "id", javaType = List.class,
                    many = @Many(select = "com.softserve.if072.restservice.dao.mybatisdao.StoreDAO" +
                            ".getProductsByStoreId"))})
    List<Store> getAllStoresByUser(int userId);

    @Select("SELECT id, name, address, latitude, longitude, is_enabled FROM store WHERE user_id = #{userId}")
    @Results(value = {
            @Result(property = "id", column = "id"),
            @Result(property = "name", column = "name"),
            @Result(property = "address", column = "address"),
            @Result(property = "latitude", column = "latitude"),
            @Result(property = "longitude", column = "longitude"),
            @Result(property = "user", column = "user_id", javaType = User.class,
                    one = @One(select = "com.softserve.if072.restservice.dao.mybatisdao.UserDAO.getByID")),
            @Result(property = "isEnabled", column = "is_enabled"),
            @Result(property = "products", column = "id", javaType = List.class,
                    many = @Many(select = "com.softserve.if072.restservice.dao.mybatisdao.StoreDAO" +
                            ".getProductsOnlyByStoreId"))})
    List<Store> getAllByUser(int userId);

    @Override
    @Select("SELECT id, name, address, user_id, latitude, longitude, is_enabled FROM store WHERE id = #{id}")
    @Results(value = {
            @Result(property = "id", column = "id"),
            @Result(property = "name", column = "name"),
            @Result(property = "address", column = "address"),
            @Result(property = "latitude", column = "latitude"),
            @Result(property = "longitude", column = "longitude"),
            @Result(property = "user", column = "user_id", javaType = User.class,
                    one = @One(select = "com.softserve.if072.restservice.dao.mybatisdao.UserDAO.getByID")),
            @Result(property = "isEnabled", column = "is_enabled"),
            @Result(property = "products", column = "id", javaType = List.class,
                    many = @Many(select = "com.softserve.if072.restservice.dao.mybatisdao.StoreDAO" +
                            ".getProductsOnlyByStoreId"))
    })
    Store getByID(int id);

    @Select(value = "SELECT id, name, address, latitude, longitude, user_id, is_enabled FROM store WHERE user_id = " +
            "#{userId} AND name = #{StoreName} AND address = #{address}")
    @Results(value = {
            @Result(property = "id", column = "id"),
            @Result(property = "name", column = "name"),
            @Result(property = "address", column = "address"),
            @Result(property = "latitude", column = "latitude"),
            @Result(property = "longitude", column = "longitude"),
            @Result(property = "user", column = "user_id", javaType = User.class,
                    one = @One(select = "com.softserve.if072.restservice.dao.mybatisdao.UserDAO.getByID")),
            @Result(property = "isEnabled", column = "is_enabled"),
            @Result(property = "products", column = "id", javaType = List.class,
                    many = @Many(select = "com.softserve.if072.restservice.dao.mybatisdao.StoreDAO" +
                            ".getProductsByStoreId"))})
    Store getByName(@Param("StoreName") String StoreName, @Param("address") String address, @Param("userId") Integer
            userId);

    @Override
    @Insert("INSERT into store (name, address, user_id, is_enabled, latitude, longitude) VALUES(#{name}, " +
            "#{address}, #{user.id}, #{isEnabled}, #{latitude}, #{longitude})")
    @Options(useGeneratedKeys = true)
    void insert(Store store);

    @Override
    @Update("UPDATE store SET name = #{name}, address = #{address}, is_enabled = 1, latitude = " +
            "#{latitude}, longitude = #{longitude}  WHERE id = #{id}")
    @Results(value = {
            @Result(property = "id", column = "id"),
            @Result(property = "name", column = "name"),
            @Result(property = "address", column = "address"),
            @Result(property = "latitude", column = "latitude"),
            @Result(property = "longitude", column = "longitude"),
            @Result(property = "user", column = "user_id", javaType = User.class,
                    one = @One(select = "com.softserve.if072.restservice.dao.mybatisdao.UserDAO.getByID")),
            @Result(property = "isEnabled", column = "is_enabled")})
    void update(Store store);

    @Override
    @Update("UPDATE store SET is_enabled = 0 WHERE id = #{id}")
    void deleteById(int id);

    @Update("UPDATE store SET is_enabled = 1 WHERE id = #{storeId}")
    void retrieveStore(int storeId);

    @Select("SELECT id, name, description, image_id, category_id, unit_id, is_enabled FROM product  JOIN " +
            "stores_products ON product.id = stores_products.product_id WHERE store_id = #{storeId} and user_id = " +
            "#{userId} ")
    @Results(value = {
            @Result(property = "id", column = "id"),
            @Result(property = "name", column = "name"),
            @Result(property = "description", column = "description"),
            @Result(property = "image", column = "image_id", javaType = Image.class,
                    one = @One(select = "com.softserve.if072.restservice.dao.mybatisdao.ImageDAO.getByID")),
            @Result(property = "user", column = "user_id", javaType = User.class,
                    one = @One(select = "com.softserve.if072.restservice.dao.mybatisdao.UserDAO.getByID")),
            @Result(property = "category", column = "category_id", javaType = Category.class,
                    one = @One(select = "com.softserve.if072.restservice.dao.mybatisdao.CategoryDAO.getByID")),
            @Result(property = "unit", column = "unit_id", javaType = Unit.class,
                    one = @One(select = "com.softserve.if072.restservice.dao.mybatisdao.UnitDAO.getByID")),
            @Result(property = "isEnabled", column = "is_enabled")
    })
    List<Product> getProductsByStoreId(@Param("storeId") Integer storeId, @Param("userId") Integer userId);

    @Select("SELECT id, name, description, is_enabled FROM product  JOIN " +
            "stores_products ON product.id = stores_products.product_id WHERE store_id = #{storeId} " +
            "AND is_enabled = 1")
    @Results(value = {
            @Result(property = "id", column = "id"),
            @Result(property = "isEnabled", column = "is_enabled")
    })
    List<Product> getProductsOnlyByStoreId(@Param("storeId") Integer storeId);

    @Select("SELECT id, name, description, image_id, user_id, category_id, unit_id, is_enabled FROM product  JOIN " +
            "stores_products ON product.id = stores_products.product_id WHERE store_id = #{storeId} and product_id " +
            "=#{productId}")
    @Results(value = {
            @Result(property = "id", column = "id"),
            @Result(property = "name", column = "name"),
            @Result(property = "description", column = "description"),
            @Result(property = "image", column = "image_id", javaType = Image.class,
                    one = @One(select = "com.softserve.if072.restservice.dao.mybatisdao.ImageDAO.getByID")),
            @Result(property = "user", column = "user_id", javaType = User.class,
                    one = @One(select = "com.softserve.if072.restservice.dao.mybatisdao.UserDAO.getByID")),
            @Result(property = "category", column = "category_id", javaType = Category.class,
                    one = @One(select = "com.softserve.if072.restservice.dao.mybatisdao.CategoryDAO.getByID")),
            @Result(property = "unit", column = "unit_id", javaType = Unit.class,
                    one = @One(select = "com.softserve.if072.restservice.dao.mybatisdao.UnitDAO.getByID")),
            @Result(property = "isEnabled", column = "is_enabled")
    })
    Product getProductFromStoreById(@Param("storeId") Integer storeId, @Param("productId") Integer productId);

    @Delete("DELETE FROM stores_products WHERE store_id = #{storeId} and product_id = #{productId}")
    void deleteProductFromStoreById(@Param("storeId") Integer storeId, @Param("productId") Integer productId);

    @Insert("INSERT into stores_products(store_id, product_id) VALUES(#{storeId}, #{productId})")
    void addProductToStore(@Param("productId") Integer productId, @Param("storeId") Integer storeId);

}

package com.softserve.if072.restservice.dao.mybatisdao;

import com.softserve.if072.common.model.Image;
import com.softserve.if072.restservice.dao.DAO;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface ImageDAO extends DAO<Image> {

    @Override
    @Select("SELECT * FROM image")
    @Results(value = {
            @Result(property = "id", column = "id"),
            @Result(property = "fileName", column = "file_name"),
            @Result(property = "contentType", column = "content_type"),
            @Result(property = "imageData", column = "image_data")
    })
    List<Image> getAll();

    @Override
    @Select("SELECT * FROM image WHERE id = #{id}")
    @Results(value = {
            @Result(property = "id", column = "id"),
            @Result(property = "fileName", column = "file_name"),
            @Result(property = "contentType", column = "content_type"),
            @Result(property = "imageData", column = "image_data")
    })
    Image getByID(@Param("id") int id);


    @Override
    @Insert("INSERT INTO image (file_name, content_type, image_data) VALUES (#{fileName}, #{contentType}, #{imageData})")
    @Options(useGeneratedKeys = true)
    void insert(Image t);

    @Override
    @Update("UPDATE image SET file_name=#{fileName}, content_type=#{contentType}, image_data=#{imageData} WHERE id=#{id}")
    void update(Image t);

    @Override
    @Delete("DELETE FROM image WHERE id = #{id}")
    void delete(@Param("id") int id);
}

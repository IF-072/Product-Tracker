package com.softserve.if072.restservice.dao.mybatisdao;

import com.softserve.if072.common.model.Product;
import com.softserve.if072.common.model.Storage;
import com.softserve.if072.common.model.User;
import com.softserve.if072.restservice.dao.DAO;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by dyndyn on 17.01.2017.
 */
@Repository
public interface StorageDAO extends DAO<Storage> {

    @Select("SELECT * FROM storage WHERE user_id = #{user_id}")
    @Results(value = {
            @Result(property = "endDate", column = "end_date"),
            @Result(property = "amount", column = "amount"),
            @Result(property = "user", column = "user_id", javaType=User.class,
                    one=@One(select="com.softserve.if072.restservice.dao.mybatisdao.UserDAO.getByID")),
            @Result(property = "product", column = "product_id", javaType=Product.class,
                    one=@One(select="com.softserve.if072.restservice.dao.mybatisdao.ProductDAO.getByID"))
    })
    public List<Storage> getByUserID(int user_id);

    @Override
    @Insert("INSERT INTO storage (user_id, product_id, amount, end_date) VALUES (#{user.id}, #{product.id}, #{amount}, #{endDate})")
    @Options(useGeneratedKeys = true)
    public void insert(Storage storage);

    @Override
    @Update("UPDATE storage SET end_date=#{endDate}, amount=#{amount} WHERE user_id=#{user.id} AND product_id=#{product.id}")
    public void update(Storage storage);

    @Update("UPDATE storage SET amount=#{amount} WHERE user_id=#{user.id} AND product_id=#{product.id}")
    public void updateAmount(Storage storage);

    @Delete("DELETE FROM storage WHERE user_id=#{user.id} AND product_id=#{product.id}")
    public void delete(Storage storage) ;
}

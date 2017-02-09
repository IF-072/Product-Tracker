package com.softserve.if072.restservice.mapper;

import com.softserve.if072.common.model.Cart;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * This class allows to get a cart model from a database.
 *
 * @author Oleh Pochernin
 */
public interface CartMapper {
    String SELECT_BY_USER_ID = "SELECT";

    @Select(SELECT_BY_USER_ID)
    @Results(value = {

    })
    List<Cart> getByUserID();
}

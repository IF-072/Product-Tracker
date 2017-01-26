package com.softserve.if072.restservice.dao.mybatisdao;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.springframework.stereotype.Repository;

/**
 * Contains MyBatis methods for changing products that sell at current store
 * Dates are in Stores_Products DB
 */

@Repository
public interface StoresProductsDAO {

    @Delete("DELETE FROM stores_products WHERE store_id = #{id} and product_id = #{id}")
    void deleteProductFromStoreById (int storeId, int productId);

    @Insert("INSERT into stores_products(store_id, product_id) VALUES(#{id}, #{id})")
    void addProductToStore(int storeId, int productId);
}

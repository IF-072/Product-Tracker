package com.softserve.if072.restservice.repository;

import com.softserve.if072.common.model.History;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;

/**
 * Created by Nazar Vynnyk
 */

@Repository
public interface HistoryRepository extends JpaRepository<History, Integer> {

    Page<History> findAll(Pageable pageable);

    Page<History> findByUserId(int userId, Pageable pageable);

    @Query("select h from History h where h.user.id = :userId" +
            " and (:name is NULL or h.product.name like :name)" +
            " and (:description is NULL or h.product.description like :description)" +
            " and (:categoryId is NULL or :categoryId = 0 or h.product.category.id = :categoryId)" +
            " and (:fromDate is NULL or h.usedDate >= :fromDate)" +
            " and (:toDate is NULL or h.usedDate <= :toDate)" +
            "")
    Page<History> findByMultipleParams(@Param("userId") int userId, @Param("name") String name, @Param("description") String description, @Param("categoryId") int categoryId,  @Param("fromDate") Date fromDate, @Param("toDate") Date toDate, Pageable p);


}

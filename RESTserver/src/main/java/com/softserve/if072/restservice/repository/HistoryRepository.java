package com.softserve.if072.restservice.repository;

import com.softserve.if072.common.model.History;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Nazar Vynnyk
 */

@Repository
public interface HistoryRepository extends JpaRepository<History, Integer> {

}

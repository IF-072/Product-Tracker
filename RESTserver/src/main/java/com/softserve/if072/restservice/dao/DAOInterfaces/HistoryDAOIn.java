package com.softserve.if072.restservice.dao.DAOInterfaces;

import com.softserve.if072.common.model.History;
import com.softserve.if072.common.model.dto.HistoryDTO;

import java.util.List;

/**
 * Created by Home on 17.03.2017.
 */
public interface HistoryDAOIn {

        List<History> getByUserId(int userId);

        List<History> getByProductId(int userId, int productId);

        History getByHistoryId(int historyId);

        void insert(HistoryDTO historyDTO);

        void update(HistoryDTO historyDTO);

        void delete(int historyId);

        void deleteAll(int userId);



}

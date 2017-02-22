package com.softserve.if072.restservice.service.security;

import com.softserve.if072.common.model.History;
import com.softserve.if072.restservice.dao.mybatisdao.HistoryDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * The HistorySecurityServise class provides security methods for CartControlLer
 *
 * @author Igor Kryviuk
 */
@Service
public class HistorySecurityService extends BaseSecurityService {
    @Autowired
    private HistoryDAO historyDAO;

    public boolean hasPermissionToAccess(int historyId) {
        History history = historyDAO.getByHistoryId(historyId);
        if (history != null && history.getUser() != null && history.getUser().getId() == getCurrentUser().getId()) {
            return true;
        }
        return false;
    }
}
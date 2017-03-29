package com.softserve.if072.restservice.service.security;

import com.softserve.if072.common.model.History;
import com.softserve.if072.restservice.dao.mybatisdao.HistoryDAO;
import com.softserve.if072.restservice.service.ProductService;
import org.springframework.stereotype.Service;

/**
 * The HistorySecurityServise class provides security methods for CartControlLer
 *
 * @author Igor Kryviuk
 */
@Service
public class HistorySecurityService extends BaseSecurityService {
    private HistoryDAO historyDAO;
    private final ProductService productService;

    public HistorySecurityService(HistoryDAO historyDAO, ProductService productService) {
        this.historyDAO = historyDAO;
        this.productService = productService;
    }

    public boolean hasPermissionToDelete(int historyId) {
        History history = historyDAO.getByHistoryId(historyId);
        return history != null && history.getUser() != null && history.getUser().getId() == getCurrentUser().getId();
    }

    public boolean hasPermissionToAccessByProductId(int productId) {
        return productService.getUserIdByProductId(productId) == getCurrentUser().getId();
    }
}
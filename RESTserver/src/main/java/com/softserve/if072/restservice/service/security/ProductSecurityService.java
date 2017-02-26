package com.softserve.if072.restservice.service.security;

import com.softserve.if072.common.model.Product;
import com.softserve.if072.restservice.dao.mybatisdao.ProductDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Provides security methods for StoreService
 *
 * @author Vitaliy Malisevych
 */

@Service
public class ProductSecurityService extends BaseSecurityService {

    @Autowired
    ProductDAO productDAO;

    public boolean hasPermissionToAccess(int productId){
        Product product = productDAO.getByID(productId);
        return product != null && product.getUser() != null && product.getUser().getId() == getCurrentUser().getId();
    }
}

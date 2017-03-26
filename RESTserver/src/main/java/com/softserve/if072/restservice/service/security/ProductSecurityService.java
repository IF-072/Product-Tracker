package com.softserve.if072.restservice.service.security;

import com.softserve.if072.common.model.Product;
import com.softserve.if072.restservice.dao.mybatisdao.ProductDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Provides security methods for ProductController
 *
 * @author Vitaliy Malisevych
 */

@Service
public class ProductSecurityService extends BaseSecurityService {

    private ProductDAO productDAO;

    @Autowired
    public ProductSecurityService(ProductDAO productDAO) {
        this.productDAO = productDAO;
    }

    public boolean hasPermissionToAccess(int productId){
        Product product = productDAO.getByID(productId);
        return product != null && product.getUser() != null && product.getUser().getId() == getCurrentUser().getId();
    }

    public boolean hasPermissionToAccess(String productName) {

        Product product = productDAO.getProductByNameAndUserId(productName, getCurrentUser().getId());

        return product == null || product.getUser() != null && product.getUser().getId() == getCurrentUser().getId();

    }
}

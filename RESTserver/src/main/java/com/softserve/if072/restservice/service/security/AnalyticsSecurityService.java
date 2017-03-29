package com.softserve.if072.restservice.service.security;

import com.softserve.if072.restservice.service.ProductService;
import org.springframework.stereotype.Service;

/**
 * The ForecatsSecurityService class provides security methods for AnalyticsControlLer
 *
 * @author Igor Kryviuk
 */
@Service
public class AnalyticsSecurityService extends BaseSecurityService {
    private final ProductService productService;

    public AnalyticsSecurityService(ProductService productService) {
        this.productService = productService;
    }

    public boolean hasPermissionToAccess(int productId) {
        int productOwnerId = productService.getUserIdByProductId(productId);
        return productService.getUserIdByProductId(productId) == getCurrentUser().getId();
    }
}

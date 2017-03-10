package com.softserve.if072.restservice.service.security;

import com.softserve.if072.common.model.Category;
import com.softserve.if072.restservice.dao.mybatisdao.CategoryDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Provides security methods for CategoryService
 *
 * @author Igor Parada, Pavlo Bendus
 */
@Service
public class CategorySecurityService extends BaseSecurityService {

    private CategoryDAO categoryDAO;

    @Autowired
    public CategorySecurityService(CategoryDAO categoryDAO) {
        this.categoryDAO = categoryDAO;
    }

    public boolean hasPermissionToAccess(int categoryID){
        Category category = categoryDAO.getByID(categoryID);
        return category != null && category.getUser() != null && category.getUser().getId() == getCurrentUser().getId();
    }
}

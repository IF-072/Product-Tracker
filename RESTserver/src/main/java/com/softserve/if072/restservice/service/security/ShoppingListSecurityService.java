package com.softserve.if072.restservice.service.security;

import com.softserve.if072.common.model.ShoppingList;
import com.softserve.if072.restservice.dao.mybatisdao.ShoppingListDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Provides security methods for ShoppingListService
 *
 * @author Nazar Vyynnyk
 */
@Service
public class ShoppingListSecurityService extends BaseSecurityService {

    @Autowired
    private ShoppingListDAO shoppingListDAO;

    public boolean hasPermissionToAccess(int storeID) {
        ShoppingList shoppingList = shoppingListDAO.getByID(storeID);
        if (shoppingList != null && shoppingList.getUser() != null && shoppingList.getUser().getId() ==
                getCurrentUser().getId()) {
            return true;
        }
        return false;
    }
}

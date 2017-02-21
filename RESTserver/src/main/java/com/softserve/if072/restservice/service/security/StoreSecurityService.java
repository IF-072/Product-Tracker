package com.softserve.if072.restservice.service.security;

import com.softserve.if072.common.model.Store;
import com.softserve.if072.restservice.dao.mybatisdao.StoreDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Provides security methods for StoreService
 *
 * @author Igor Parada
 */
@Service
public class StoreSecurityService extends BaseSecurityService {

    @Autowired
    private StoreDAO storeDAO;

    public boolean hasPermissionToAccess(int storeID){
        Store store = storeDAO.getByID(storeID);
        if(store != null && store.getUser() != null && store.getUser().getId() == getCurrentUser().getId()) {
            return true;
        }
        return false;
    }
}

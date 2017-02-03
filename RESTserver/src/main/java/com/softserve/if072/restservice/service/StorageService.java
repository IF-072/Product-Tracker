package com.softserve.if072.restservice.service;


import com.softserve.if072.common.model.Storage;
import com.softserve.if072.restservice.Exception.DataSourceException;

import java.util.List;

/**
 * Created by dyndyn on 21.01.2017.
 */
public interface StorageService {

    List<Storage> getByUserId(int user_id) throws DataSourceException;

    void insert(Storage storage);

    void update(Storage storage) throws DataSourceException ;

    void delete(Storage storage) throws DataSourceException ;
}

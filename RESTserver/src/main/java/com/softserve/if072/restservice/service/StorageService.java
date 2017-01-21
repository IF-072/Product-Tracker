package com.softserve.if072.restservice.service;


import com.softserve.if072.common.model.Storage;

import java.util.List;

/**
 * Created by dyndyn on 21.01.2017.
 */
public interface StorageService {

    Storage getByUserId(int user_id);

    void insert(Storage storage);

    void update(Storage storage);

    void delete(int id);
}

package com.softserve.if072.restservice.service;

import com.softserve.if072.common.model.Store;
import java.util.List;

public interface StoreService {

    List<Store> getAll();

    Store getByID(int id);

    void insert(Store store);

    void update(Store store);

    void delete(int id);
}


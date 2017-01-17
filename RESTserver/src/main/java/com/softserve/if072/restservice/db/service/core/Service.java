package com.softserve.if072.restservice.db.service.core;

import java.util.List;

public interface Service<T> {

    List<T> getAll();

    T getByID(int id);

    void insert(T t);

    void delete(T t);

    void update(T t);

    int createTable();

}

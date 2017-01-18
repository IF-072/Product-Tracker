package com.softserve.if072.restservice.dao.core;

import java.util.List;

public interface DAO<T> {

    List<T> getAll();

    T getByID(int id);

    void insert(T t);

    void update(T t);

    void delete(int id);

}

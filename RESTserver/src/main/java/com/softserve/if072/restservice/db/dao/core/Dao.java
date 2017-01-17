package com.softserve.if072.restservice.db.dao.core;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public interface Dao<T> {

    List<T> getAll() throws SQLException;

    T getByID(int id) throws SQLException;

    void insert(T t) throws SQLException;

    void delete(T t) throws SQLException;

    void update(T t) throws SQLException;

    int createTable();

    boolean isTableCreated();

    List<T> parseResultSet(ResultSet resultSet) throws SQLException;

}

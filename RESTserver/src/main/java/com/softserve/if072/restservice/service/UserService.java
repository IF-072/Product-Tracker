package com.softserve.if072.restservice.service;

import com.softserve.if072.common.model.User;

import java.util.List;

public interface UserService {
    List<User> getAll();

    User getById(int id);

    void insert(User user);

    void update(User user);

    void delete(int id);
}

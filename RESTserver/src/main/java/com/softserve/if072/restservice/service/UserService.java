package com.softserve.if072.restservice.service;

import com.softserve.if072.common.model.User;
import com.softserve.if072.restservice.dao.mybatisdao.UserDAO;

import com.softserve.if072.restservice.exception.DataNotFoundException;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * This class contains method for CRUD operations with users.
 *
 * @author Oleh Pochernin
 */
@Service
@PropertySource(value = {"classpath:message.properties"})
public class UserService {

    @Value("${users.notfound}")
    private String usersNotFound;

    @Value("${user.notfound}")
    private String userNotFound;

    @Autowired
    private UserDAO userDAO;

    public List<User> getAll() throws DataNotFoundException {
        List<User> users = userDAO.getAll();

        if (CollectionUtils.isNotEmpty(users)) {
            return users;
        } else {
            throw new DataNotFoundException(usersNotFound);
        }
    }

    public User getById(int id) throws DataNotFoundException {
        User user = userDAO.getByID(id);

        if (id < 1) {
            throw new IllegalArgumentException("Id should be 1 or more.");
        }

        if (user == null) {
            throw new DataNotFoundException(String.format(userNotFound, id));
        } else {
            return user;
        }
    }

    public void insert(User user) {
        userDAO.insert(user);
    }

    public void update(User user) {
        userDAO.update(user);
    }

    public void delete(int id) {
        userDAO.deleteById(id);
    }
}

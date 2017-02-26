package com.softserve.if072.restservice.service;

import com.softserve.if072.common.model.User;
import com.softserve.if072.restservice.dao.mybatisdao.UserDAO;
import com.softserve.if072.restservice.exception.DataNotFoundException;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.xml.bind.annotation.adapters.HexBinaryAdapter;
import java.security.MessageDigest;
import java.util.List;

import static org.apache.commons.codec.binary.StringUtils.getBytesUtf8;

/**
 * This class contains method for CRUD operations with users.
 *
 * @author Oleh Pochernin
 */
@Service
public class UserService {

    @Value("${users.notfound}")
    private String usersNotFound;

    @Value("${user.notfound}")
    private String userNotFound;

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private MessageDigest messageDigest;

    @Autowired
    private HexBinaryAdapter hexBinaryAdapter;

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

    public User getByUsername(String username) {
        return userDAO.getByUsername(username);
    }

    public void insert(User user) {
        user.setPassword(encodePassword(user.getPassword()));
        userDAO.insert(user);
    }

    public void update(User user) {
        userDAO.update(user);
    }

    public void delete(int id) {
        userDAO.deleteById(id);
    }

    public String encodePassword(String password) {
        return hexBinaryAdapter.marshal(messageDigest.digest(getBytesUtf8(password)));
    }
}

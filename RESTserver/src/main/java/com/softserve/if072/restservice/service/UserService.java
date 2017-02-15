package com.softserve.if072.restservice.service;

import com.softserve.if072.common.model.User;
import com.softserve.if072.restservice.dao.mybatisdao.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.bind.annotation.adapters.HexBinaryAdapter;
import java.security.MessageDigest;
import java.util.List;

import static org.apache.commons.codec.binary.StringUtils.getBytesUtf8;

@Service
public class UserService {

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private MessageDigest messageDigest;

    @Autowired
    private HexBinaryAdapter hexBinaryAdapter;

    public List<User> getAll() {
        return userDAO.getAll();
    }

    public User getById(int id) {
        return userDAO.getByID(id);
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
        return hexBinaryAdapter.marshal( messageDigest.digest(getBytesUtf8(password)));
    }
}

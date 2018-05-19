package com.softserve.if072.restservice.integration;

/**
 * Created by dyndyn on 18.05.2018.
 */

import static org.junit.Assert.assertEquals;

import com.softserve.if072.common.model.User;
import com.softserve.if072.restservice.configuration.DataSourceConfigIT;
import com.softserve.if072.restservice.dao.mybatisdao.UserDAO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * The UserIT class is used to test
 * UserDAO class methods
 *
 * @author Roman Dyndyn
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = DataSourceConfigIT.class)
@SqlGroup({
        @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD,
                scripts = "classpath:beforeScripts.sql"),
        @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD,
                scripts = "classpath:afterScripts.sql") })
public class UserIT {
    private static final Logger LOGGER = LogManager.getLogger(UserIT.class);

    @Autowired
    private UserDAO userDAO;

    @Test
    public void shouldGetUserByID(){
        User user = userDAO.getByID(2);
        LOGGER.info(user);
        assertEquals("Roman Dyndyn", user.getName());
    }

    @Test
    public void shouldGetUserByUsername(){
        String username = "romanDyndyn@gmail.com";
        int userId = 2;
        User user = userDAO.getByUsername(username);
        LOGGER.info(user);
        assertEquals(userId, user.getId());
    }

    @Test
    public void shouldUpdate(){
        int userId = 2;
        User user = userDAO.getByID(userId);
        LOGGER.info(user);
        String email = "testIT@gmail.com";
        String name = "test";
        user.setEmail(email);
        user.setName(name);
        userDAO.update(user);

        user = userDAO.getByID(userId);
        assertEquals(email, user.getEmail());
        assertEquals(name, user.getName());
    }

    @Test(expected = org.springframework.dao.DuplicateKeyException.class)
    public void ShouldThrowException(){
        int userId = 2;
        User user = userDAO.getByID(userId);
        String email = "test@gmail.com";
        String name = "test";
        user.setEmail(email);
        user.setName(name);
        userDAO.update(user);

        user = userDAO.getByID(userId);
        assertEquals(email, user.getEmail());
        assertEquals(name, user.getName());
    }

}

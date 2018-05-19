package com.softserve.if072.restservice.integration;

/**
 * Created by dyndyn on 18.05.2018.
 */

import static org.junit.Assert.assertEquals;

import com.softserve.if072.restservice.configuration.DataSourceConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = DataSourceConfig.class)
@SqlGroup({
        @Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD,
                scripts = "classpath:beforeScripts.sql"),
        @Sql(executionPhase = ExecutionPhase.AFTER_TEST_METHOD,
                scripts = "classpath:afterScripts.sql") })
public class StorageIT {

    @Autowired
    private PersonDAO personDAO;

    @Test
    public void should_update_password(){

        Person person = new Person();
        person.setPersonId(1);
        person.setPassword("wilma");

        personDAO.updatePassword(person);

        //validate update occurred
        Person updatedPerson = personDAO.getPerson(1);
        assertEquals("wilma", updatedPerson.getPassword());


    }

}

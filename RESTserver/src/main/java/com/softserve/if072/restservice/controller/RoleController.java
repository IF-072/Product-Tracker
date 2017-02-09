package com.softserve.if072.restservice.controller;

import com.softserve.if072.common.model.Role;
import com.softserve.if072.restservice.dao.mybatisdao.RoleDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/roles")
public class RoleController {

    @Autowired
    private RoleDAO roleDAO;

    @RequestMapping(value = "/{id}")
    public ResponseEntity<Role> getSingleRole(@PathVariable("id") int id) {
        Role role = roleDAO.getByID(id);
        if(role != null) {
            return new ResponseEntity<Role>(role, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @RequestMapping(value = "")
    public ResponseEntity<List<Role>> getRolesList() {
        List<Role> roles = roleDAO.getAll();
        if(roles != null && roles.size() > 0) {
            return new ResponseEntity<List<Role>>(roles, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}

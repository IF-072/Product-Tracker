package com.softserve.if072.restservice.controller;

/**
 * Created by dyndyn on 21.01.2017.
 */

import com.softserve.if072.common.model.Storage;
import com.softserve.if072.restservice.service.impl.StorageServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(value = "/storage")
public class StorageController {

    @Autowired
    private StorageServiceImpl storageService;

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(value = HttpStatus.OK)
    public void delete(@PathVariable int id){
        storageService.delete(id);
    }

    @RequestMapping(value = "/getByUser/{user_id}", method = RequestMethod.GET)
    @ResponseBody
    @ResponseStatus(value = HttpStatus.OK)
    public List<Storage> getByUserId(@PathVariable int user_id){
        return storageService.getByUserId(user_id);
    }

    @RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
    @ResponseBody
    @ResponseStatus(value = HttpStatus.OK)
    public Storage getById(@PathVariable int id){
        return storageService.getById(id);
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST, headers="Accept=application/json")
    @ResponseStatus(value = HttpStatus.OK)
    public void insert(@RequestBody Storage storage){
        storageService.insert(storage);
    }

    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    @ResponseStatus(value = HttpStatus.OK)
    public void update(@RequestBody Storage storage){
        storageService.update(storage);
    }
}

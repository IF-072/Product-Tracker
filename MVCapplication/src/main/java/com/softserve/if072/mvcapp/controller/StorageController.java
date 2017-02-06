package com.softserve.if072.mvcapp.controller;

import com.softserve.if072.common.model.Storage;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import java.util.Arrays;

/**
 * Created by dyndyn on 05.02.2017.
 */
@Controller
@RequestMapping("/storage")
public class StorageController {

    @GetMapping
    public ModelAndView getPage(@RequestParam("user_id") int userId) {
        ModelAndView model = new ModelAndView("storage");
        final String uri = "http://localhost:8080/rest/storage/" + userId;
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Storage[]> storages = restTemplate.getForEntity(uri, Storage[].class);

        model.addObject("list", Arrays.asList(storages.getBody()));
        return model;

    }

    @PutMapping
    public void updateAmount(@RequestBody Storage storage){
        final String uri = "http://localhost:8080/rest/storage/";
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.put(uri, storage);
    }
}

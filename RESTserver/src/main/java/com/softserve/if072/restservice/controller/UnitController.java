package com.softserve.if072.restservice.controller;

import com.softserve.if072.common.model.Unit;
import com.softserve.if072.restservice.exception.DataNotFoundException;
import com.softserve.if072.restservice.service.UnitService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * The class contains methods to add, read and delete units from database using REST Service
 *
 * @author Vitaliy Malisevych
 * @author Igor Parada
 */

@RestController
@RequestMapping(value = "/unit")
public class UnitController {

    public static final Logger LOGGER =  LogManager.getLogger(ProductController.class);
    private UnitService unitService;

    @Autowired
    UnitController(UnitService unitService) {
        this.unitService = unitService;
    }

    @Value("${unit.notFound}")
    private String unitNotFound;

    @GetMapping(value = "/")
    @ResponseBody
    @ResponseStatus(value = HttpStatus.OK)
    public List<Unit> getAllUnits(HttpServletResponse response) {
        try {
            List<Unit> units = unitService.getAllUnits();
            LOGGER.info("All units were found");
            return units;
        } catch (DataNotFoundException e) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            LOGGER.error(e.getMessage(), e);
            return null;
        }
    }

    @GetMapping(value = "/{id}")
    @ResponseBody
    @ResponseStatus(value = HttpStatus.OK)
    public Unit getUnitById(@PathVariable int id, HttpServletResponse response) {
        try {
            Unit unit = unitService.getUnitById(id);
            LOGGER.info(String.format("Unit with id %d was retrieved", id));
            return unit;
        } catch (DataNotFoundException e) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            LOGGER.error(String.format(unitNotFound, id), e);
            return null;
        }
    }
}

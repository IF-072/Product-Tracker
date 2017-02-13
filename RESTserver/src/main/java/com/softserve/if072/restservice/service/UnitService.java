package com.softserve.if072.restservice.service;

import com.softserve.if072.common.model.Unit;
import com.softserve.if072.restservice.dao.mybatisdao.UnitDAO;
import com.softserve.if072.restservice.exception.DataNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * The class contains methods to add, read and delete units from database
 *
 * @author Vitaliy Malisevych
 * @author Igor Parada
 */

@Service
@PropertySource(value = {"classpath:message.properties"})
public class UnitService {

    private UnitDAO unitDAO;

    @Value("${unit.notFound}")
    private String unitNotFound;

    @Autowired
    public UnitService (UnitDAO unitDAO) {
        this.unitDAO = unitDAO;
    }

    public List<Unit> getAllUnits() throws DataNotFoundException {
        List<Unit> units = unitDAO.getAll();
        if (!units.isEmpty()){
            return units;
        } else {
            throw new DataNotFoundException("Units not found");
        }
    }

    public Unit getUnitById(int id) throws DataNotFoundException {
        Unit unit = unitDAO.getByID(id);
        if (unit != null){
            return unit;
        } else {
            throw new DataNotFoundException(String.format(unitNotFound, id));
        }
    }
}

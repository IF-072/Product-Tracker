package com.softserve.academy.services;


import com.softserve.academy.dao.UnitDAO;
import com.softserve.academy.dto.Unit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UnitServiceImpl implements UnitService {

    @Autowired
    @Qualifier("UnitDaoMybatis")
    private UnitDAO unitDAOImplementation;

    public Unit getUnitById(int id) {
        Unit unit = unitDAOImplementation.getUnitById(id);
        if(unit == null)
           unit = unitDAOImplementation.getUnitById(1);

        return unit;
    }

    public List<Unit> getAllUnits() {
       return unitDAOImplementation.getAllUnits();
    }

    public void addUnit(Unit unit){
        if((unit != null) && (!unit.getName().equals("")))
            unitDAOImplementation.addUnit(unit);

    }

    public void updateUnit(Unit unit){
        unitDAOImplementation.updateUnit(unit);
    }

    public void removeUnit(int id){
        unitDAOImplementation.removeUnitById(id);
    }

}

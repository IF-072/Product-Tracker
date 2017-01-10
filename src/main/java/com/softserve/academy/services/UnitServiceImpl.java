package com.softserve.academy.services;


import com.softserve.academy.dao.UnitDAO;
import com.softserve.academy.dto.Unit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UnitServiceImpl implements UnitService {

    @Autowired
    private UnitDAO unitDAOMybatis;

    public Unit getUnitById(int id) {
        Unit unit = unitDAOMybatis.getUnitById(id);
        if(unit == null)
           unit = unitDAOMybatis.getUnitById(1);

        return unit;
    }

    public List<Unit> getAllUnits() {
       return unitDAOMybatis.getAllUnits();
    }

    public void addUnit(Unit unit){
        if(unit != null)
            unitDAOMybatis.addUnit(unit);
    }

    public void updateUnit(Unit unit){
        unitDAOMybatis.updateUnit(unit);
    }

    public void removeUnit(int id){
        unitDAOMybatis.removeUnitById(id);
    }

}

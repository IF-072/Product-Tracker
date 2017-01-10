package com.softserve.academy.services;


import com.softserve.academy.dto.Unit;
import com.softserve.academy.mappers.UnitMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UnitServiceImpl implements UnitService {

    @Autowired
    private UnitMapper unitMapper;

    public Unit getUnitById(int id) {
        Unit unit = unitMapper.getUnitById(id);
        if(unit == null)
           unit = unitMapper.getUnitById(1);

        return unit;
    }

    public List<Unit> getAllUnits() {
       return unitMapper.getAllUnits();
    }

    public void addUnit(Unit unit){
        if(unit != null)
            unitMapper.addUnit(unit);
    }

    public void removeUnit(int id){
        unitMapper.removeUnitById(id);
    }

}

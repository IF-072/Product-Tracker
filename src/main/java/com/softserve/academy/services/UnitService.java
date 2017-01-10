package com.softserve.academy.services;

import com.softserve.academy.dto.Unit;

import java.util.List;

public interface UnitService {

    Unit getUnitById(int id);

    List<Unit> getAllUnits();

    void addUnit(Unit unit);

    void removeUnit(int id);
}

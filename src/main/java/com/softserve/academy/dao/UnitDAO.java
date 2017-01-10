package com.softserve.academy.dao;


import com.softserve.academy.dto.Unit;

import java.util.List;

public interface UnitDAO {

    Unit getUnitById(int id);

    List<Unit> getAllUnits();

    void addUnit(Unit unit);

    void updateUnit(Unit unit);

    void removeUnitById(int id);
}

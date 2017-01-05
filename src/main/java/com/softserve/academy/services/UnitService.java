package com.softserve.academy.services;

import com.softserve.academy.dto.Unit;
import org.springframework.stereotype.Service;

import java.util.List;

public interface UnitService {

    public Unit getUnitById(int id);

    public List<Unit> getAllUnits();
}

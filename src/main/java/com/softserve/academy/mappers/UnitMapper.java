package com.softserve.academy.mappers;

import com.softserve.academy.dto.Unit;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface UnitMapper {

    @Select("SELECT * FROM unit WHERE id = #{id}")
    public Unit getUnitById(@Param("id") int id);

    @Select("SELECT * FROM unit")
    public List<Unit> getAllUnits();
}

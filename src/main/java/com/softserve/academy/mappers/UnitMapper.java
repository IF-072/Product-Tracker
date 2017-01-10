package com.softserve.academy.mappers;

import com.softserve.academy.dto.Unit;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface UnitMapper {

    @Select("SELECT * FROM unit WHERE id = #{id}")
    Unit getUnitById(@Param("id") int id);

    @Select("SELECT * FROM unit ORDER BY id")
    List<Unit> getAllUnits();

    @Insert("INSERT INTO unit (name) VALUES (#{name})")
    @Options(useGeneratedKeys = true)
    void addUnit(Unit unit);

    @Delete("DELETE FROM unit WHERE id = #{id}")
    void removeUnitById(@Param("id") int id);
}

package com.softserve.academy.dao;

import com.softserve.academy.dto.Unit;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Component(value = "UnitDaoMybatis")
public interface UnitDAOMybatis extends UnitDAO{

    @Select("SELECT * FROM unit WHERE id = #{id}")
    Unit getUnitById(@Param("id") int id);

    @Select("SELECT * FROM unit ORDER BY id")
    List<Unit> getAllUnits();

    @Insert("INSERT INTO unit (name) VALUES (#{name})")
    @Options(useGeneratedKeys = true)
    void addUnit(Unit unit);

    @Update("UPDATE unit SET name=#{name} WHERE id=#{id}")
    void updateUnit(Unit unit);

    @Delete("DELETE FROM unit WHERE id = #{id}")
    void removeUnitById(@Param("id") int id);
}

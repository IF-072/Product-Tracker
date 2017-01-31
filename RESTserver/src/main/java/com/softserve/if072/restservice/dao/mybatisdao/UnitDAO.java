package com.softserve.if072.restservice.dao.mybatisdao;

import com.softserve.if072.common.model.Unit;
import com.softserve.if072.restservice.dao.DAO;
import org.apache.ibatis.annotations.*;

import java.util.List;


public interface UnitDAO extends DAO<Unit> {
    @Override
    @Select("SELECT * FROM unit")
    List<Unit> getAll();

    @Override
    @Select("SELECT * FROM unit WHERE id = #{id}")
    Unit getByID(@Param("id") int id);

    @Override
    @Insert("INSERT INTO unit (name) VALUES (#{name})")
    @Options(useGeneratedKeys = true)
    void insert(Unit unit);

    @Override
    @Update("UPDATE unit SET name=#{name} WHERE id=#{id}")
    void update(Unit unit);

    @Override
    @Delete("DELETE FROM unit WHERE id = #{id}")
    void deleteById(@Param("id") int id);
}

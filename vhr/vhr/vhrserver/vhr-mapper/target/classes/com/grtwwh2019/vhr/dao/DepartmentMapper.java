package com.grtwwh2019.vhr.dao;

import com.grtwwh2019.vhr.model.Department;

import java.util.List;

public interface DepartmentMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Department record);

    int insertSelective(Department record);

    Department selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Department record);

    int updateByPrimaryKey(Department record);

    List<Department> getAllDepartmentsByParentId(Integer pid);

    Integer getLastInsertId();

    Integer getSubDivisionByDid(Integer did);

    Integer getDepEmployeesByDid(Integer did);

    Integer getSubCountbyPid(Integer pid);
}
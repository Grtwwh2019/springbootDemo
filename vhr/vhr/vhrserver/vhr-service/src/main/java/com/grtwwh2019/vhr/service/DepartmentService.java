package com.grtwwh2019.vhr.service;

import com.grtwwh2019.vhr.dao.DepartmentMapper;
import com.grtwwh2019.vhr.model.Department;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DepartmentService {

    @Autowired
    DepartmentMapper departmentMapper;

    public List<Department> getAllDepartmentsByParentId() {
        // 从-1开始查，即从最高层开始
        return departmentMapper.getAllDepartmentsByParentId(-1);
    }

    @Transactional
    public Integer addDep(Department department) {
        department.setEnabled(true);
        department.setParent(false);
        // 1.插入新部门，并获取新插入的id
        Integer InsertResult = departmentMapper.insert(department);
        Integer did = departmentMapper.getLastInsertId();
        // 2.查询parent的depPath，然后在depPath上加上 《.自己的id》
        // 然后更新自己的depPath
        Department parentDep = departmentMapper.selectByPrimaryKey(department.getParentId());
        String parentDepPath = parentDep.getDepPath();
        StringBuffer sb = new StringBuffer(parentDepPath);
        sb.append("." + did);
        department.setDepPath(new String(sb));
        department.setId(did);
        departmentMapper.updateByPrimaryKey(department);
        // 3.判断parent的isParent是否为true，如果为false，则更新为true
        if (!parentDep.getParent()) {
            parentDep.setParent(true);
            departmentMapper.updateByPrimaryKey(parentDep);
        }
        return InsertResult;
    }

    public Integer deleteDep(Integer did) {
        // 1.查询该部门是否存在子部门(判断isParent是否为false)
        Integer subCount = departmentMapper.getSubDivisionByDid(did);
        if (subCount == 0) {
            return -2;
        }
        // 2.查询该部门下的员工人数
        Integer employees = departmentMapper.getDepEmployeesByDid(did);
        if (employees > 0) {
            return -1;
        }
        // 3.删除该部门
        Department dep = departmentMapper.selectByPrimaryKey(did);
        Integer pid = dep.getParentId();
        Integer result  = departmentMapper.deleteByPrimaryKey(did);
        // 4.判断该部门的父部门是否还有子部门
        Department parentDep = departmentMapper.selectByPrimaryKey(pid);
        Integer parentSubCount = departmentMapper.getSubCountbyPid(pid);
        if (parentSubCount == 0) {
            parentDep.setParent(false);
        }
        departmentMapper.updateByPrimaryKey(parentDep);
        return result;
    }
}

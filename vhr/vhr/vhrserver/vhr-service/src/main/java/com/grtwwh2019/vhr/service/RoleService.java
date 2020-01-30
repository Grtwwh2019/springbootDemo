package com.grtwwh2019.vhr.service;

import com.grtwwh2019.vhr.dao.MenuRoleMapper;
import com.grtwwh2019.vhr.dao.RoleMapper;
import com.grtwwh2019.vhr.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {

    @Autowired
    RoleMapper roleMapper;


    public List<Role> getAllRoles() {
        return roleMapper.getAllRoles();
    }


    public Integer addRole(Role role) {
        if (!role.getName().startsWith("ROLE_")) {
            role.setName("ROLE_" + role.getName());
        }
        return roleMapper.insertSelective(role);
    }

    public Integer deleteRole(Integer rid) {
        return roleMapper.deleteByPrimaryKey(rid);
    }
}

package com.grtwwh2019.vhr.service;

import com.grtwwh2019.vhr.dao.MenuMapper;
import com.grtwwh2019.vhr.dao.MenuRoleMapper;
import com.grtwwh2019.vhr.model.Hr;
import com.grtwwh2019.vhr.model.Menu;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MenuService {

    @Autowired
    MenuMapper menuMapper;

    @Autowired
    MenuRoleMapper menuRoleMapper;


    public List<Menu> getMenusByHrId() {
        /**
         * 当前登录的对象就保存在：Principal中
         * SecurityContextHolder.getContext().getAuthentication().getPrincipal()
         */
        return menuMapper.getMenusByHrId(((Hr) SecurityContextHolder.getContext().
                getAuthentication().getPrincipal()).getId());
    }

    /**
     * 根据角色获取对应的菜单
     */
    public List<Menu> getAllMenusWithRole() {
        return menuMapper.getAllMenusWithRole();
    }

    public List<Menu> getAllMenusWithChildren() {
        return menuMapper.getAllMenusWithChildren();
    }

    public List<Integer> getMenusByRid(Integer rid) {
        return menuMapper.getMenusByRid(rid);
    }

    @Transactional
    public boolean updateMenuRole(Integer rid, Integer[] mids) {
        menuRoleMapper.deleteRecord(rid);
        if (mids == null || mids.length == 0) {
            return true;
        }
        Integer result = menuRoleMapper.insertRecord(rid, mids);
        return result == mids.length;
    }
}

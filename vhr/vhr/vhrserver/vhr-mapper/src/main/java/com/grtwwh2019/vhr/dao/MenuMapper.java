package com.grtwwh2019.vhr.dao;

import com.grtwwh2019.vhr.model.Menu;

import java.util.List;

public interface MenuMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Menu record);

    int insertSelective(Menu record);

    Menu selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Menu record);

    int updateByPrimaryKey(Menu record);

    List<Menu> getMenusByHrId(Integer hrId);

    List<Menu> getAllMenusWithRole();

    List<Menu> getAllMenusWithChildren();

    List<Integer> getMenusByRid(Integer rid);
}
package com.grtwwh2019.vhr.dao;

import com.grtwwh2019.vhr.model.Hr;
import com.grtwwh2019.vhr.model.Role;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface HrMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Hr record);

    int insertSelective(Hr record);

    Hr selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Hr record);

    int updateByPrimaryKey(Hr record);

    Hr loadUserByUsername(String username);

    List<Role> getRolesById(Integer id);

    List<Hr> getAllHrs(@Param("hrid") Integer hrid, @Param("keywords") String keywords);

    void deleteHrRole(Integer hrid);

    Integer addHrRole(@Param("hrid") Integer hrid, @Param("rids") Integer[] rids);
}
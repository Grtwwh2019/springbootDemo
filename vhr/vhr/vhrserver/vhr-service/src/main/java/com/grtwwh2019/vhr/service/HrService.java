package com.grtwwh2019.vhr.service;

import com.grtwwh2019.vhr.dao.HrMapper;
import com.grtwwh2019.vhr.model.Hr;
import com.grtwwh2019.vhr.utils.HrUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class HrService implements UserDetailsService {

    @Autowired
    HrMapper hrMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Hr hr = hrMapper.loadUserByUsername(username);
        // 判断传入的username是否为空，若为空，hr可能为null
        if (username.trim().equals("") || username == null) {
            throw new UsernameNotFoundException("用户名不存在！");
        }
        hr.setRoles(hrMapper.getRolesById(hr.getId()));
        return hr;
    }

    public List<Hr> getAllHrs(String keywords) {
        Hr currentHr = HrUtils.getCurrentHr();
        Integer hrid = currentHr.getId();
        return hrMapper.getAllHrs(hrid, keywords);
    }

    public Integer updateHr(Hr hr) {
        return hrMapper.updateByPrimaryKey(hr);
    }

    @Transactional
    public Integer updateHrRole(Integer hrid, Integer[] rids) {
        hrMapper.deleteHrRole(hrid);
        if (rids == null ||rids.length == 0) {
            return 0;
        }
        return hrMapper.addHrRole(hrid, rids);
    }

    public Integer deleteHrRole(Integer hrid) {
        return hrMapper.deleteByPrimaryKey(hrid);
    }
}

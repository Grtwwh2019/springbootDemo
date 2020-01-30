package com.grtwwh2019.vhr.controller.system;

import com.grtwwh2019.vhr.model.Hr;
import com.grtwwh2019.vhr.model.RespBean;
import com.grtwwh2019.vhr.model.Role;
import com.grtwwh2019.vhr.service.HrService;
import com.grtwwh2019.vhr.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/system/hr")
public class HrController {

    @Autowired
    HrService hrService;

    @Autowired
    RoleService roleService;

    @GetMapping("/")
    public List<Hr> getAllHrs(String keywords) {
        return hrService.getAllHrs(keywords);
    }

    @PutMapping("/")
    public RespBean updateHr(@RequestBody Hr hr) {
        if (hrService.updateHr(hr) == 1) {
            return RespBean.success("修改成功");
        }
        return RespBean.error("修改失败");
    }

    @GetMapping("/roles")
    public List<Role> getAllRoles() {
        return roleService.getAllRoles();
    }

    @PutMapping("/role")
    public RespBean updateHrRole(Integer hrid, Integer[] rids) {
        Integer result = hrService.updateHrRole(hrid, rids);
        if (result == 0 || result == rids.length) {
            return RespBean.success("修改成功");
        }
        return RespBean.error("修改失败");
    }

    @DeleteMapping("/{hrid}")
    public RespBean deleteHrRole(@PathVariable("hrid") Integer hrid) {
        if (hrService.deleteHrRole(hrid) == 1) {
            return RespBean.success("删除成功");
        }
        return RespBean.error("删除失败");
    }
}

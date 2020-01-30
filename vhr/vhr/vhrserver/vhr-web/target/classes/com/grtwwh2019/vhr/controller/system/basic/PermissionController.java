package com.grtwwh2019.vhr.controller.system.basic;

import com.grtwwh2019.vhr.model.Menu;
import com.grtwwh2019.vhr.model.RespBean;
import com.grtwwh2019.vhr.model.Role;
import com.grtwwh2019.vhr.service.MenuService;
import com.grtwwh2019.vhr.service.PermissionService;
import com.grtwwh2019.vhr.service.RoleService;
import com.sun.org.apache.regexp.internal.RE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/system/basic/permiss")
public class PermissionController {

    @Autowired
    RoleService roleService;

    @Autowired
    MenuService menuService;

    @Autowired
    PermissionService permissionService;

    @GetMapping("/")
    public List<Role> getAllRoles() {
        return roleService.getAllRoles();
    }

    @GetMapping("/menus")
    public List<Menu> getAllMenusWithChildren() {
        return menuService.getAllMenusWithChildren();
    }

    @GetMapping("/mids/{rid}")
    public List<Integer> getMenusByRid(@PathVariable("rid") Integer rid) {
        return menuService.getMenusByRid(rid);
    }

    @PutMapping("/")
    public RespBean updateMenuRole(Integer rid, Integer[] mids) {
        if (menuService.updateMenuRole(rid, mids)) {
            return RespBean.success("修改成功");
        }
        return RespBean.error("修改失败");
    }

    @PostMapping("/role")
    public RespBean addRole (@RequestBody Role role) {
        if (roleService.addRole(role) == 1) {
            return RespBean.success("添加成功");
        }
        return RespBean.error("添加失败");
    }

    @DeleteMapping("/role/{rid}")
    public RespBean deleteRole(@PathVariable("rid") Integer rid) {
        if (roleService.deleteRole(rid) == 1) {
            return RespBean.success("删除成功");
        }
        return RespBean.error("删除失败");
    }
}

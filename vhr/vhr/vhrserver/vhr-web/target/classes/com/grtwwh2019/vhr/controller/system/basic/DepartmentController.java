package com.grtwwh2019.vhr.controller.system.basic;

import com.grtwwh2019.vhr.model.Department;
import com.grtwwh2019.vhr.model.RespBean;
import com.grtwwh2019.vhr.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/system/basic/department")
public class DepartmentController {

    @Autowired
    DepartmentService departmentService;

    @GetMapping("/")
    public List<Department> getAllDepartments() {
        return departmentService.getAllDepartmentsByParentId();
    }

    @PostMapping("/")
    public RespBean addDep(@RequestBody Department department) {
        if (departmentService.addDep(department) == 1) {
            return RespBean.success("添加成功", department);
        }
        return RespBean.error("添加失败");
    }

    @DeleteMapping("/{did}")
    public RespBean deleteDep(@PathVariable("did") Integer did) {
        Integer result = departmentService.deleteDep(did);
        if (result == 1){
            return RespBean.success("删除成功");
        } else if (result == -1) {
            return RespBean.error("删除失败, 该部门还存在员工！");
        } else if (result == -2) {
            return RespBean.error("删除失败，该部门还存在子部门！");
        } else {
            return RespBean.error("删除失败，未知错误！");
        }
    }
}

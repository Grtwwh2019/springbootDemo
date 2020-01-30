package com.grtwwh2019.vhr.controller.system.basic;

import com.grtwwh2019.vhr.model.JobLevel;
import com.grtwwh2019.vhr.model.RespBean;
import com.grtwwh2019.vhr.service.JobLevelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/system/basic/joblevel")
public class JobLevelController {

    @Autowired
    JobLevelService jobLevelService;

    @GetMapping("/")
    public List<JobLevel> getAllJobLevel() {
        return jobLevelService.getAllJobLevel();
    }

    @PostMapping("/")
    public RespBean addJobLevel(@RequestBody JobLevel jobLevel) {
        if (jobLevelService.addJobLevel(jobLevel) == 1) {
            return RespBean.success("添加成功");
        }
        return RespBean.error("添加失败");
    }

    @PutMapping("/")
    public RespBean updateJobLevel(@RequestBody JobLevel jobLevel) {
        if (jobLevelService.updateJobLevel(jobLevel) == 1) {
            return RespBean.success("修改成功");
        }
        return RespBean.error("修改失败");
    }

    @DeleteMapping("/{id}")
    public RespBean deleteJobLevel(@PathVariable("id") Integer id) {
        if (jobLevelService.deleteJobLevel(id) == 1) {
            return RespBean.success("删除成功");
        }
        return RespBean.error("删除失败");
    }

    @DeleteMapping("/")
    public RespBean deleteJobLevelByIds(Integer[] ids) {
        if (jobLevelService.deleteJobLevelByIds(ids) == ids.length) {
            return RespBean.success("删除成功");
        }
        return RespBean.error("删除失败");
    }
}

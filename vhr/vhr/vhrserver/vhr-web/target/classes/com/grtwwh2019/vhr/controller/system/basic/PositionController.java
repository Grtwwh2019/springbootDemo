package com.grtwwh2019.vhr.controller.system.basic;

import com.grtwwh2019.vhr.model.Position;
import com.grtwwh2019.vhr.model.RespBean;
import com.grtwwh2019.vhr.service.PositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/system/basic/pos")
public class PositionController {

    @Autowired
    PositionService positionService;

    @GetMapping("/")
    public List<Position> getAllPositions() {
        return positionService.getAllPositions();
    }

    @PostMapping("/")
    public RespBean addPosition(@RequestBody Position position) {
        Integer integer = positionService.addPosition(position);
        if (integer == 1) {
            return RespBean.success("添加成功");
        }
        return RespBean.error("添加失败");
    }

    @PutMapping("/")
    public RespBean updatePosition(@RequestBody Position position) {
        if (positionService.updatePosition(position) == 1) {
            return RespBean.success("修改成功");
        }
        return RespBean.error("修改失败");
    }

    @DeleteMapping("/{id}")
    public RespBean deletePosition(@PathVariable("id") Integer id) {
        if (positionService.deletePosition(id) == 1) {
            return RespBean.success("删除成功");
        }
        return RespBean.error("删除失败");
    }

    @DeleteMapping("/")
    public RespBean deletePositionByIds(Integer[] ids) {
        if (positionService.deletePositionByIds(ids) == ids.length) {
            return RespBean.success("删除成功");
        }
        return RespBean.error("删除失败");
    }

}

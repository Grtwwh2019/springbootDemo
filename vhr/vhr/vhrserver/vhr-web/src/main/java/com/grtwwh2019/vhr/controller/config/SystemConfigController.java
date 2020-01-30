package com.grtwwh2019.vhr.controller.config;

import com.grtwwh2019.vhr.model.Menu;
import com.grtwwh2019.vhr.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/system/config/")
public class SystemConfigController {

    @Autowired
    MenuService menuService;

    @GetMapping("/menu")
    public List<Menu> getMenusByHrId() {
        /**
         * 前端的数据不可信，即不能使用前端传递的参数，有可能是篡改的
         * 一定要从后端获取，后端再校验，即从内存中获取数据
         * 因为别人有可能使用的不是你的页面而是类似postman的工具
         */
        return menuService.getMenusByHrId();
    }
}

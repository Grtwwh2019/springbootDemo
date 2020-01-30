package com.grtwwh2019.vhr.config;

import com.grtwwh2019.vhr.dao.MenuMapper;
import com.grtwwh2019.vhr.model.Menu;
import com.grtwwh2019.vhr.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import java.util.Collection;
import java.util.List;

/**
 * 这个类的作用是：根据用户传来的请求地址，分析出请求需要的角色
 */
@Component
public class CustomSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {

    @Autowired
    MenuMapper menuMapper;

    AntPathMatcher antPathMatcher = new AntPathMatcher();
    /**
     * Collection：当前请求需要的角色
     */
    @Override
    public Collection<ConfigAttribute> getAttributes(Object o) throws IllegalArgumentException {
        String requestUrl = ((FilterInvocation) o).getRequestUrl(); // 获取当前请求的地址
        /**
         *  即，1.根据用户请求的地址分析所需要的角色
         */
        List<Menu> menus = menuMapper.getAllMenusWithRole();
        for (Menu menu : menus) {
            // 假如用户请求的地址 匹配了接口的地址，就添加这个角色
            if (antPathMatcher.match(menu.getUrl(), requestUrl)) {
                List<Role> roles = menu.getRoles();
                String[] strings = new String[roles.size()];
                for (int i = 0; i < roles.size(); i++) {
                    strings[i] = roles.get(i).getName();
                }
                return SecurityConfig.createList(strings);
            }
        }
        return SecurityConfig.createList("ROLE_LOGIN");
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }
}

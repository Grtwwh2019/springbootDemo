package com.grtwwh2019.vhr.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.grtwwh2019.vhr.model.Hr;
import com.grtwwh2019.vhr.model.RespBean;
import com.grtwwh2019.vhr.service.HrService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.*;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    HrService hrService;

    @Autowired
    CustomSecurityMetadataSource customSecurityMetadataSource;

    @Autowired
    CustomUrlDecisionManager customUrlDecisionManager;

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * HttpSecurity 配置拦截规则
     * 登录成功和失败以后的响应
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests() // 开启配置
//                .antMatchers("/hello") // ant风格的路径，判断路径是否符合该规则
//                .hasRole("admin") // 表示访问 /hello 是否具备admin这个角色
//                .anyRequest().authenticated() // 表示接口在登录之后才能访问
                .withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>() {

                    @Override
                    public <O extends FilterSecurityInterceptor> O postProcess(O o) {
                        o.setAccessDecisionManager(customUrlDecisionManager);
                        o.setSecurityMetadataSource(customSecurityMetadataSource);
                        return o;
                    }
                })
                .and()
                .formLogin() // 表单登录
                .loginPage("/login") // 登录页面，未登录的接口会自动跳转到该页面
                .loginProcessingUrl("/doLogin") // 登录处理接口
                .usernameParameter("username") // 定义登录时，用户名的key，默认为username
                .passwordParameter("password") // 定义登录时，密码的key，默认为password
                // 登录成功的处理器
                .successHandler(new AuthenticationSuccessHandler() {
                    @Override
                    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                                                        Authentication authentication) throws IOException, ServletException {
                        // authentication: 登录成功保存的用户信息
                        httpServletResponse.setContentType("applcation/json;charset=utf-8");
                        PrintWriter out = httpServletResponse.getWriter();
                        Hr hr = (Hr) authentication.getPrincipal();// 登录成功返回的hr对象
                        hr.setPassword(null); // 取消返回的密码
                        RespBean success = RespBean.success("登录成功", hr);
                        out.write(new ObjectMapper().writeValueAsString(success)); // 将hr对象写成String类型再写出
                        out.flush();
                        out.close();
                    }
                })
                // 登录失败的处理器
                .failureHandler(new AuthenticationFailureHandler() {
                    @Override
                    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                                                        AuthenticationException e) throws IOException, ServletException {
                        httpServletResponse.setContentType("applcation/json;charset=utf-8");
                        PrintWriter out = httpServletResponse.getWriter();
                        RespBean respBean = RespBean.error("登录失败！");
                        if (e instanceof LockedException) {
                            respBean.setMessage("账户被锁定，请联系管理员");
                        }
                        else if (e instanceof CredentialsExpiredException) {
                            respBean.setMessage("密码过期，请联系管理员");
                        }
                        else if (e instanceof AccountExpiredException) {
                            respBean.setMessage("账户过期，请联系管理员");
                        }
                        else if (e instanceof DisabledException) {
                            respBean.setMessage("账户被禁用，请联系管理员");
                        }
                        else if (e instanceof BadCredentialsException) {
                            respBean.setMessage("用户名或密码错误，请重新输入！");
                        }
                        out.write(new ObjectMapper().writeValueAsString(respBean)); // 将hr对象写成String类型再写出
                        out.flush();
                        out.close();
                    }
                })
                .permitAll() // 和表单登录相关的接口都允许通过
                .and()
                .logout() // 配置注销登录,默认接口
//                .logoutUrl("/logout") // 配置注销登录的url路径（接口）
                .logoutSuccessHandler(new LogoutSuccessHandler() {
                    @Override
                    public void onLogoutSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                                                Authentication authentication) throws IOException, ServletException {
                        httpServletResponse.setContentType("application/json;charset=utf-8");
                        PrintWriter out = httpServletResponse.getWriter();
                        out.write(new ObjectMapper().writeValueAsString(RespBean.success("注销成功!")));
                        out.flush();
                        out.close();
                    }
                })
                .permitAll()
                .and()
                .csrf()
                .disable()
                .exceptionHandling().authenticationEntryPoint(new AuthenticationEntryPoint() {
            @Override
            // 没有登录认证时，在这里进行处理，不要重定向
            public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
                httpServletResponse.setContentType("applcation/json;charset=utf-8");
                // 解决Session失效，401：登录验证失效
                httpServletResponse.setStatus(401);
                PrintWriter out = httpServletResponse.getWriter();
                RespBean respBean = RespBean.error("访问失败！");
                if (e instanceof InsufficientAuthenticationException) {
                    respBean.setMessage("权限不足，请联系管理员");
                }
                out.write(new ObjectMapper().writeValueAsString(respBean)); // 将hr对象写成String类型再写出
                out.flush();
                out.close();
            }
        });
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(hrService);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        /**
         * 解决：Could not get any response...
         * 如果返回login页，则不用经过Spring Security
         */
        web.ignoring().antMatchers("/login");
    }
}

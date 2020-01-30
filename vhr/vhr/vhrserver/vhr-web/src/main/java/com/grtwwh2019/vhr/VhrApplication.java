package com.grtwwh2019.vhr;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = {"com.grtwwh2019.vhr.dao"})
public class VhrApplication {
    public static void main(String[] args) {
        SpringApplication.run(VhrApplication.class, args);
    }

}

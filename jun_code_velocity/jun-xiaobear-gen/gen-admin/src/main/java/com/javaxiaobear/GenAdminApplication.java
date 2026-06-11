package com.javaxiaobear;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author javaxiaobear
 * @date 2021年11月11日 14:49
 * @Description 小熊学Java
 */
@SpringBootApplication
@MapperScan("com.javaxiaobear.mapper")
@EnableScheduling
public class GenAdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(GenAdminApplication.class, args);
        System.out.println("代码生成平台启动成功！");
    }
}

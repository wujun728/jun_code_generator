package cn.yt4j.layuirbac;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author gyv12345@163.com
 */
@MapperScan("cn.yt4j.layuirbac.dao")
@SpringBootApplication
public class LayuiRbacApplication {

    public static void main(String[] args) {
        SpringApplication.run(LayuiRbacApplication.class, args);
    }

}

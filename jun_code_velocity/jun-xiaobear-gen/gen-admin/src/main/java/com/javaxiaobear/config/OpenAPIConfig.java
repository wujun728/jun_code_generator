package com.javaxiaobear.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * OpenAPI配置
 *
 * @author javaxiaobear
 * @date 2024-06-27
 */
@Configuration
public class OpenAPIConfig {

    /**
     * 创建API文档配置
     */
    @Bean
    public OpenAPI createOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("XiaoBear代码生成器API文档")
                        .description("基于Spring Boot + Vue的智能化代码生成器RESTful API")
                        .version("2.0")
                        .contact(new Contact()
                                .name("javaxiaobear")
                                .url("https://github.com/javaxiaobear")
                                .email("javaxiaobear@example.com"))
                        .license(new License()
                                .name("AGPL-3.0")
                                .url("https://www.gnu.org/licenses/agpl-3.0.html")));
    }
}

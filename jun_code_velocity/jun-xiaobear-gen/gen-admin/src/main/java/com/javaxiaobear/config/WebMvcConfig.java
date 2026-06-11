package com.javaxiaobear.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Web MVC配置
 * 
 * @author javaxiaobear
 * @date 2024-06-27
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    /**
     * 静态资源映射
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 本地文件上传路径
        registry.addResourceHandler("/profile/**")
                .addResourceLocations("file:./uploadPath/");
        
        // Swagger UI资源映射
        registry.addResourceHandler("/swagger-ui/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/springfox-swagger-ui/");
        
        // 静态资源映射
        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/static/");
    }
}

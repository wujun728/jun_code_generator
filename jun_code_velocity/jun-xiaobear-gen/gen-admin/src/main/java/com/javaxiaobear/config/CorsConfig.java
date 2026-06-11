package com.javaxiaobear.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * 跨域配置
 * 
 * @author javaxiaobear
 * @date 2024-06-27
 */
@Configuration
public class CorsConfig {

    /**
     * 跨域配置
     */
    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration config = new CorsConfiguration();
        
        // 允许指定域名进行跨域调用
        config.addAllowedOrigin("http://localhost:8083");
        config.addAllowedOrigin("http://localhost:8084");
        config.addAllowedOrigin("http://127.0.0.1:8083");
        config.addAllowedOrigin("http://127.0.0.1:8084");
        
        // 允许所有请求头
        config.addAllowedHeader("*");
        
        // 允许所有HTTP方法
        config.addAllowedMethod("*");
        
        // 允许发送Cookie
        config.setAllowCredentials(true);
        
        // 预检请求的缓存时间（秒）
        config.setMaxAge(3600L);
        
        // 添加映射路径
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        
        return new CorsFilter(source);
    }

    /**
     * CORS配置源
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        
        // 允许的源
        configuration.addAllowedOrigin("*");
        
        // 允许的方法
        configuration.addAllowedMethod("GET");
        configuration.addAllowedMethod("POST");
        configuration.addAllowedMethod("PUT");
        configuration.addAllowedMethod("DELETE");
        configuration.addAllowedMethod("OPTIONS");
        
        // 允许的头部
        configuration.addAllowedHeader("*");
        
        // 允许发送凭证
        configuration.setAllowCredentials(true);
        
        // 预检请求缓存时间
        configuration.setMaxAge(3600L);
        
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        
        return source;
    }
}

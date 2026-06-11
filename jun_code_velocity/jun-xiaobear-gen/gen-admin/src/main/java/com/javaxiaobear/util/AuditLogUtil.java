package com.javaxiaobear.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 审计日志工具类
 * 用于记录关键业务操作的审计日志
 * 
 * @author javaxiaobear
 * @version 2.0
 * @since 2021-11-11
 */
public class AuditLogUtil {
    
    private static final Logger auditLogger = LoggerFactory.getLogger("AUDIT");
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    
    /**
     * 记录操作日志
     * 
     * @param operation 操作类型
     * @param resource 操作资源
     * @param details 操作详情
     * @param request HTTP请求对象
     */
    public static void logOperation(String operation, String resource, String details, HttpServletRequest request) {
        try {
            // 设置MDC上下文
            MDC.put("operation", operation);
            MDC.put("resource", resource);
            MDC.put("timestamp", LocalDateTime.now().format(formatter));
            MDC.put("clientIp", getClientIpAddress(request));
            MDC.put("userAgent", request.getHeader("User-Agent"));
            MDC.put("requestUri", request.getRequestURI());
            MDC.put("method", request.getMethod());
            
            // 记录审计日志
            auditLogger.info("操作审计 - 操作: {}, 资源: {}, 详情: {}, 客户端IP: {}, 请求URI: {}", 
                    operation, resource, details, getClientIpAddress(request), request.getRequestURI());
        } finally {
            // 清理MDC
            MDC.clear();
        }
    }
    
    /**
     * 记录数据库操作日志
     */
    public static void logDatabaseOperation(String operation, String tableName, Object data, HttpServletRequest request) {
        String details = String.format("表: %s, 数据: %s", tableName, data != null ? data.toString() : "null");
        logOperation("DATABASE_" + operation.toUpperCase(), tableName, details, request);
    }
    
    /**
     * 记录代码生成操作日志
     */
    public static void logCodeGeneration(String tableName, String genType, String result, HttpServletRequest request) {
        String details = String.format("表: %s, 生成类型: %s, 结果: %s", tableName, genType, result);
        logOperation("CODE_GENERATION", tableName, details, request);
    }
    
    /**
     * 记录数据源操作日志
     */
    public static void logDataSourceOperation(String operation, String dataSourceName, String result, HttpServletRequest request) {
        String details = String.format("数据源: %s, 结果: %s", dataSourceName, result);
        logOperation("DATASOURCE_" + operation.toUpperCase(), dataSourceName, details, request);
    }
    
    /**
     * 记录文件操作日志
     */
    public static void logFileOperation(String operation, String fileName, String result, HttpServletRequest request) {
        String details = String.format("文件: %s, 结果: %s", fileName, result);
        logOperation("FILE_" + operation.toUpperCase(), fileName, details, request);
    }
    
    /**
     * 记录登录日志
     */
    public static void logLogin(String username, boolean success, String reason, HttpServletRequest request) {
        String operation = success ? "LOGIN_SUCCESS" : "LOGIN_FAILED";
        String details = String.format("用户: %s, 原因: %s", username, reason);
        logOperation(operation, "USER_AUTH", details, request);
    }
    
    /**
     * 记录安全事件日志
     */
    public static void logSecurityEvent(String eventType, String details, HttpServletRequest request) {
        logOperation("SECURITY_EVENT", eventType, details, request);
    }
    
    /**
     * 记录性能日志
     */
    public static void logPerformance(String operation, long executionTime, String details, HttpServletRequest request) {
        if (executionTime > 1000) { // 只记录超过1秒的操作
            String perfDetails = String.format("执行时间: %dms, 详情: %s", executionTime, details);
            logOperation("PERFORMANCE_SLOW", operation, perfDetails, request);
        }
    }
    
    /**
     * 获取客户端真实IP地址
     */
    private static String getClientIpAddress(HttpServletRequest request) {
        if (request == null) {
            return "unknown";
        }
        
        String xForwardedFor = request.getHeader("X-Forwarded-For");
        if (StringUtils.isNotEmpty(xForwardedFor) && !"unknown".equalsIgnoreCase(xForwardedFor)) {
            return xForwardedFor.split(",")[0].trim();
        }
        
        String xRealIp = request.getHeader("X-Real-IP");
        if (StringUtils.isNotEmpty(xRealIp) && !"unknown".equalsIgnoreCase(xRealIp)) {
            return xRealIp;
        }
        
        String proxyClientIp = request.getHeader("Proxy-Client-IP");
        if (StringUtils.isNotEmpty(proxyClientIp) && !"unknown".equalsIgnoreCase(proxyClientIp)) {
            return proxyClientIp;
        }
        
        String wlProxyClientIp = request.getHeader("WL-Proxy-Client-IP");
        if (StringUtils.isNotEmpty(wlProxyClientIp) && !"unknown".equalsIgnoreCase(wlProxyClientIp)) {
            return wlProxyClientIp;
        }
        
        return request.getRemoteAddr();
    }
    
    /**
     * 记录方法执行时间
     */
    public static class PerformanceTimer {
        private final long startTime;
        private final String operation;
        private final HttpServletRequest request;
        
        public PerformanceTimer(String operation, HttpServletRequest request) {
            this.startTime = System.currentTimeMillis();
            this.operation = operation;
            this.request = request;
        }
        
        public void end(String details) {
            long executionTime = System.currentTimeMillis() - startTime;
            logPerformance(operation, executionTime, details, request);
        }
    }
}

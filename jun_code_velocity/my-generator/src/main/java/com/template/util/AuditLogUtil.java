package com.template.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;

/**
 * 审计日志工具
 *
 * @author Wujun
 */
public class AuditLogUtil {

    private static final Logger log = LoggerFactory.getLogger(AuditLogUtil.class);

    public static void logDatabaseOperation(String action, String target, String detail, HttpServletRequest request) {
        String ip = request != null ? request.getRemoteAddr() : "unknown";
        log.info("[AUDIT] action={}, target={}, detail={}, ip={}", action, target, detail, ip);
    }

    public static void logCodeGeneration(String tableName, String type, String result, HttpServletRequest request) {
        String ip = request != null ? request.getRemoteAddr() : "unknown";
        log.info("[AUDIT-CODE] table={}, type={}, result={}, ip={}", tableName, type, result, ip);
    }

    public static class PerformanceTimer {
        private final String name;
        private final long startTime;

        public PerformanceTimer(String name, HttpServletRequest request) {
            this.name = name;
            this.startTime = System.currentTimeMillis();
        }

        public void end(String message) {
            long elapsed = System.currentTimeMillis() - startTime;
            log.info("[PERF] {}: {} (耗时 {}ms)", name, message, elapsed);
        }
    }
}
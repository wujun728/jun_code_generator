package com.javaxiaobear.handle;

import com.javaxiaobear.common.AjaxResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 全局异常处理器
 * 统一处理系统异常，提供友好的错误信息和完整的日志记录
 *
 * @author javaxiaobear
 * @version 2.0
 * @date 2022/8/19 12:54
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * 处理参数验证异常
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public AjaxResult handleValidationException(MethodArgumentNotValidException e, HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
        String errorMessage = fieldErrors.stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.joining(", "));

        log.warn("参数验证失败 - 请求地址: {}, 错误信息: {}", requestURI, errorMessage);
        return AjaxResult.error("参数验证失败: " + errorMessage);
    }

    /**
     * 处理参数绑定异常
     */
    @ExceptionHandler(BindException.class)
    public AjaxResult handleBindException(BindException e, HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
        String errorMessage = fieldErrors.stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.joining(", "));

        log.warn("参数绑定失败 - 请求地址: {}, 错误信息: {}", requestURI, errorMessage);
        return AjaxResult.error("参数错误: " + errorMessage);
    }

    /**
     * 处理缺少请求参数异常
     */
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public AjaxResult handleMissingParameterException(MissingServletRequestParameterException e, HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        log.warn("缺少请求参数 - 请求地址: {}, 参数名: {}", requestURI, e.getParameterName());
        return AjaxResult.error("缺少必需的请求参数: " + e.getParameterName());
    }

    /**
     * 处理参数类型不匹配异常
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public AjaxResult handleTypeMismatchException(MethodArgumentTypeMismatchException e, HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        log.warn("参数类型不匹配 - 请求地址: {}, 参数名: {}, 期望类型: {}",
                requestURI, e.getName(), e.getRequiredType().getSimpleName());
        return AjaxResult.error("参数类型错误: " + e.getName() + " 应该是 " + e.getRequiredType().getSimpleName());
    }

    /**
     * 处理SQL异常
     */
    @ExceptionHandler(SQLException.class)
    public AjaxResult handleSQLException(SQLException e, HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        String errorCode = e.getSQLState();

        log.error("SQL执行异常 - 请求地址: {}, SQL状态码: {}, 错误信息: {}", requestURI, errorCode, e.getMessage(), e);

        // 根据SQL错误码返回友好的错误信息
        String message = "数据库操作失败";
        if ("23000".equals(errorCode)) {
            message = "数据完整性约束违反，可能存在重复数据";
        } else if ("42000".equals(errorCode)) {
            message = "SQL语法错误";
        } else if ("08001".equals(errorCode) || "08006".equals(errorCode)) {
            message = "数据库连接失败";
        }

        return AjaxResult.error(message);
    }

    /**
     * 处理数据访问异常
     */
    @ExceptionHandler(DataAccessException.class)
    public AjaxResult handleDataAccessException(DataAccessException e, HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        log.error("数据访问异常 - 请求地址: {}, 错误信息: {}", requestURI, e.getMessage(), e);
        return AjaxResult.error("数据访问失败，请稍后重试");
    }

    /**
     * 处理空指针异常
     */
    @ExceptionHandler(NullPointerException.class)
    public AjaxResult handleNullPointerException(NullPointerException e, HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        log.error("空指针异常 - 请求地址: {}", requestURI, e);
        return AjaxResult.error("系统内部错误，请联系管理员");
    }

    /**
     * 处理非法参数异常
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public AjaxResult handleIllegalArgumentException(IllegalArgumentException e, HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        log.warn("非法参数异常 - 请求地址: {}, 错误信息: {}", requestURI, e.getMessage());
        return AjaxResult.error("参数错误: " + e.getMessage());
    }

    /**
     * 拦截未知的运行时异常
     */
    @ExceptionHandler(RuntimeException.class)
    public AjaxResult handleRuntimeException(RuntimeException e, HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        String userAgent = request.getHeader("User-Agent");
        String remoteAddr = getClientIpAddress(request);

        log.error("运行时异常 - 请求地址: {}, 客户端IP: {}, User-Agent: {}, 错误信息: {}",
                requestURI, remoteAddr, userAgent, e.getMessage(), e);
        return AjaxResult.error("系统运行异常，请稍后重试");
    }

    /**
     * 系统异常
     */
    @ExceptionHandler(Exception.class)
    public AjaxResult handleException(Exception e, HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        String userAgent = request.getHeader("User-Agent");
        String remoteAddr = getClientIpAddress(request);

        log.error("系统异常 - 请求地址: {}, 客户端IP: {}, User-Agent: {}, 错误信息: {}",
                requestURI, remoteAddr, userAgent, e.getMessage(), e);
        return AjaxResult.error("系统异常，请联系管理员");
    }

    /**
     * 获取客户端真实IP地址
     */
    private String getClientIpAddress(HttpServletRequest request) {
        String xForwardedFor = request.getHeader("X-Forwarded-For");
        if (xForwardedFor != null && !xForwardedFor.isEmpty() && !"unknown".equalsIgnoreCase(xForwardedFor)) {
            return xForwardedFor.split(",")[0].trim();
        }

        String xRealIp = request.getHeader("X-Real-IP");
        if (xRealIp != null && !xRealIp.isEmpty() && !"unknown".equalsIgnoreCase(xRealIp)) {
            return xRealIp;
        }

        return request.getRemoteAddr();
    }
}

package cn.yt4j.layuirbac.handler;

import cn.yt4j.layuirbac.vo.LayuiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;

/**
 * 全局异常处理
 * @author gyv12345@163.com
 */
@Slf4j
@ControllerAdvice
@ResponseBody
public class RbacExceptionHandler {

    @ExceptionHandler(value = AuthenticationException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public LayuiResponse authenticationException(HttpServletRequest request, Exception e){
        log.error("请求:{}的时候出现错误：{}",request.getRequestURI(),e.getMessage());
        //其他异常
        log.error("权限错误：{}",e.getMessage());
        return LayuiResponse.failed("权限错误:"+e.getMessage());
    }

    /**
     * 拦截所有异常
     * @param request
     * @param e
     * @return
     */
    @ExceptionHandler(value = Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public LayuiResponse exceptionHandler(HttpServletRequest request, Exception e) {
        e.printStackTrace();
        log.error("请求:{}的时候出现错误：{}",request.getRequestURI(),e.getMessage());
        return LayuiResponse.failed("服务器无响应,请重新尝试");
    }


}

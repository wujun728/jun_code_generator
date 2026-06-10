package io.github.wujun728.admin.config;

import io.github.wujun728.admin.model.ReturnT;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class GlobalDefaultExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ReturnT<String> defaultExceptionHandler(HttpServletRequest req, Exception e) {
        e.printStackTrace();
        return new ReturnT<>(ReturnT.FAIL_CODE, "代码生成失败:" + e.getMessage());
    }
}
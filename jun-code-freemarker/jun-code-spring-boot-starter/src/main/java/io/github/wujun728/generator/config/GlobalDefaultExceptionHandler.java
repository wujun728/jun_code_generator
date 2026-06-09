package io.github.wujun728.generator.config;

import io.github.wujun728.common.base.Result;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class GlobalDefaultExceptionHandler {
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Result defaultExceptionHandler(HttpServletRequest req, Exception e) {
        e.printStackTrace();
        return new Result(500, "代码生成失败:" + e.getMessage());
    }
}
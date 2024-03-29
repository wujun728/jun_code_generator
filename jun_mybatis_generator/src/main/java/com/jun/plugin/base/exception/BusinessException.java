package com.jun.plugin.base.exception;

import com.jun.plugin.base.exception.code.BaseResponseCode;

/**
 * @ClassName: BusinessException
 * TODO:类文件简单描述
 * @author Wujun
 * @Version: 0.0.1
 */
public class BusinessException extends RuntimeException{
    /**
     *  异常 code
     */
    private final int code;

    /**
     *  异常提示
     */
    public final String defaultMessage;

    public BusinessException(int code, String defaultMessage) {
        super(defaultMessage);
        this.code = code;
        this.defaultMessage = defaultMessage;
    }

    public BusinessException(BaseResponseCode baseResponseCode){
        this(baseResponseCode.getCode(), baseResponseCode.getMsg());
    }
    public int getCode() {
        return code;
    }

    public String getDefaultMessage() {
        return defaultMessage;
    }
}

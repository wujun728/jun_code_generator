package cn.afterturn.gen.common.exception;

import cn.afterturn.gen.core.exception.GunsException;

/**
 * @author fengshuonan
 * @Description 业务异常的封装
 * @date 2016年11月12日 下午5:05:10
 */
public class BussinessException extends GunsException {

    public BussinessException(BizExceptionEnum bizExceptionEnum) {
        super(bizExceptionEnum.getCode(), bizExceptionEnum.getMessage(), bizExceptionEnum.getUrlPath());
    }
}

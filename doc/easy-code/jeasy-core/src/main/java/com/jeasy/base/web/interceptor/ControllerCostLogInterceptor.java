package com.jeasy.base.web.interceptor;

import com.jeasy.base.interceptor.AbstractResponseLogInterceptor;
import com.jeasy.base.web.dto.ModelResult;
import com.jeasy.common.Func;
import com.jeasy.doc.util.MonitorUtils;
import com.jeasy.exception.KitException;
import com.jeasy.exception.MessageException;
import com.jeasy.exception.ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.web.method.HandlerMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Controller层日志拦截器
 *
 * @author taomk
 * @version 1.0
 * @since 15-5-22 下午7:57
 */
@Slf4j
public class ControllerCostLogInterceptor extends AbstractResponseLogInterceptor {

    @Override
    protected boolean isThreadLocalEnable() {
        return true;
    }

    @Override
    protected ModelResult buildModelResult(final HttpServletRequest request, final HandlerMethod handler, final Exception ex) {
        ModelResult result = (ModelResult) request.getAttribute("result");
        if (Func.isNotEmpty(ex)) {

            MonitorUtils.incCountForException(handler.getBeanType().getName(), handler.getMethod().getName());

            if (Func.isEmpty(result)) {
                result = new ModelResult(ModelResult.CODE_500);
            } else {
                result.setCode(ModelResult.CODE_500);
            }

            if (ex instanceof ServiceException) {
                result.setCode(((ServiceException) ex).getCode());
                result.setMessage(ex.getMessage());
            } else if (ex instanceof MessageException) {
                result.setCode(((MessageException) ex).getCode());
                result.setMessage(ex.getMessage());
            } else if (ex instanceof KitException) {
                result.setCode(ModelResult.CODE_500);
                result.setMessage(ex.getMessage());
            } else if (ex instanceof UnauthorizedException) {
                result.setCode(ModelResult.CODE_406);
                result.setMessage(ModelResult.UNAUTHORIZED);
            } else {
                result.setMessage(Func.isEmpty(ex.getMessage()) ? ModelResult.FAIL : ex.getMessage());
            }
        }
        return result;
    }

    @Override
    public boolean preHandleProcess(final HttpServletRequest request, final HttpServletResponse response, final Object handler) {
        return true;
    }

    @Override
    protected String getLogName() {
        return "Controller execute report ";
    }
}

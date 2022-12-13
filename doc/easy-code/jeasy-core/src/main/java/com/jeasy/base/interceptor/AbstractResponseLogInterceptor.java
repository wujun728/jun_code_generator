package com.jeasy.base.interceptor;

import com.jeasy.base.web.dto.ModelResult;
import com.jeasy.common.Func;
import com.jeasy.common.date.DateKit;
import com.jeasy.common.json.JsonKit;
import com.jeasy.common.str.StrKit;
import com.jeasy.common.thread.ThreadLocalKit;
import com.jeasy.common.web.RequestKit;
import com.jeasy.common.web.ResponseKit;
import com.jeasy.common.web.UrlKit;
import com.jeasy.doc.util.MonitorUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 日志响应抽象拦截器
 *
 * @author taomk
 * @version 1.0
 * @since 15-5-22 下午7:57
 */
@Slf4j
public abstract class AbstractResponseLogInterceptor extends HandlerInterceptorAdapter {

    public static final String APPLICATION_OCTET_STREAM = "application/octet-stream";

    @Override
    public boolean preHandle(final HttpServletRequest request, final HttpServletResponse response, final Object handler) throws Exception {
        if (isThreadLocalEnable()) {
            ThreadLocalKit.putTime(System.currentTimeMillis());
        }
        return preHandleProcess(request, response, handler);
    }

    @Override
    public void afterCompletion(final HttpServletRequest request, final HttpServletResponse response, final Object handler, final Exception ex) throws Exception {
        if (!(handler instanceof HandlerMethod)) {
            return;
        }

        long endTime = System.currentTimeMillis();
        long beginTime = ThreadLocalKit.getTime();
        long consumeTime = endTime - beginTime;

        try {
            buildResponseContentLog(request, response, (HandlerMethod) handler, ex, consumeTime);
        } finally {
            if (isThreadLocalEnable()) {
                ThreadLocalKit.removeDevice();
                ThreadLocalKit.removeTime();
                ThreadLocalKit.removeUser();
                MonitorUtils.incTimeForController(((HandlerMethod) handler).getBeanType().getName(), ((HandlerMethod) handler).getMethod().getName(), consumeTime);
            }
        }
    }

    protected void buildResponseContentLog(HttpServletRequest request, HttpServletResponse response, HandlerMethod handler, Exception ex, long consumeTime) {
        StringBuilder logMsg = new StringBuilder("\n" + getLogName() + "-------- " + DateKit.currentDatetime() + " -------------------------------------");
        logMsg.append("\nURI         : ").append(request.getRequestURI()).append(", Method : ").append(request.getMethod());
        logMsg.append("\nController  : ").append(handler.getBeanType().getName()).append(", Method : ").append(handler.getMethod().getName());
        logMsg.append("\nDevice Info : ").append(Func.isEmpty(ThreadLocalKit.getDevice()) ? StrKit.S_EMPTY : JsonKit.toJson(ThreadLocalKit.getDevice()));
        logMsg.append("\nUser Info   : ").append(Func.isEmpty(ThreadLocalKit.getCurrentUser()) ? StrKit.S_EMPTY : JsonKit.toJson(ThreadLocalKit.getCurrentUser()));

        if (RequestKit.isGET(request)) {
            logMsg.append("\nQueryString : ").append(UrlKit.decodeURLComponent(StrKit.isBlank(request.getQueryString()) ? StrKit.S_EMPTY : request.getQueryString()));
        } else if (RequestKit.isPOST(request)) {
            logMsg.append("\nParameter   : ").append(JsonKit.toJson(request.getParameterMap()));
        }

        ModelResult result = buildModelResult(request, handler, ex);

        logMsg.append("\nResponse    : ").append(Func.isEmpty(result) ? StrKit.S_EMPTY : JsonKit.toJson(result));
        logMsg.append("\nCost Time   : ").append(consumeTime).append(" ms");
        logMsg.append("\n--------------------------------------------------------------------------------------------");
        log.info(logMsg.toString());

        if (Func.isNotEmpty(ex)) {
            log.error(handler.getBeanType().getName() + " Occur Exception : ", ex);
        }
        Boolean isJson = (Func.isEmpty(response.getContentType()) || !APPLICATION_OCTET_STREAM.equalsIgnoreCase(response.getContentType())) && Func.isNotEmpty(result);
        if (isJson) {
            //在IE浏览器下，返回数据类型为JSON时，提示下载错误，遂添加头信息text/html
            if (RequestKit.isIE(request)) {
                String contentType = "Content-Type:text/html";
                ResponseKit.renderJson(response, result, contentType);
            } else {
                ResponseKit.renderJson(response, result);
            }
        }
    }

    /**
     * 是否启用ThreadLocal类型的变量
     *
     * @return
     */
    protected abstract boolean isThreadLocalEnable();

    /**
     * 创建ModelResult对象
     *
     * @param request
     * @param handler
     * @param ex
     * @return
     */
    protected abstract ModelResult buildModelResult(final HttpServletRequest request, final HandlerMethod handler, final Exception ex);

    /**
     * 前置处理
     *
     * @param request
     * @param response
     * @param handler
     * @return
     */
    protected abstract boolean preHandleProcess(final HttpServletRequest request, final HttpServletResponse response, final Object handler);

    /**
     * 日志名称 用于区分不同模块的运行日志（Api、controller等）
     *
     * @return
     */
    protected abstract String getLogName();
}

package com.jeasy.base.web.csrf;

import com.jeasy.base.web.dto.ModelResult;
import com.jeasy.common.Func;
import com.jeasy.exception.MessageException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Csrf拦截器，用来生成或去除CsrfToken
 *
 * @author L.cm
 */
@Slf4j
public class CsrfInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private CsrfTokenRepository csrfTokenRepository;

    @Override
    public boolean preHandle(final HttpServletRequest request, final HttpServletResponse response, final Object handler) throws Exception {
        // 非控制器请求直接跳出
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) handler;

        CsrfToken csrfToken = handlerMethod.getMethodAnnotation(CsrfToken.class);
        // 判断是否含有@CsrfToken注解
        if (null == csrfToken) {
            return true;
        }

        // create、remove同时为true时异常
        if (csrfToken.create() && csrfToken.remove()) {
            log.error("CsrfToken attr create and remove can Not at the same time to true!");
            return renderError(request, response, "CsrfToken attr create and remove can Not at the same time to true!");
        }
        // 创建
        if (csrfToken.create()) {
            CsrfTokenBean token = csrfTokenRepository.generateToken(request);
            csrfTokenRepository.saveToken(token, request, response);
            request.setAttribute(token.getParameterName(), token);
            return true;
        }
        // 校验，并且清除
        CsrfTokenBean tokenBean = csrfTokenRepository.loadToken(request);
        if (tokenBean == null) {
            return renderError(request, response, "CsrfToken is null!");
        }

        // String actualToken = request.getHeader(tokenBean.getHeaderName());
        // if (actualToken == null) {
        //      actualToken = request.getParameter(tokenBean.getParameterName());
        // }
        //  if (!tokenBean.getToken().equals(actualToken)) {
        //      return renderError(request, response, "CsrfToken not eq!");
        //  }

        // CsrfToken不从request header和parameter中取, 校对放到缓存中取
        CsrfTokenBean cacheTokenBean = csrfTokenRepository.loadTokenFromCache(tokenBean);
        if (Func.isEmpty(cacheTokenBean)) {
            return renderError(request, response, "CsrfToken is null!");
        }

        return tokenBean.getToken().equals(cacheTokenBean.getToken()) || renderError(request, response, "CsrfToken not eq!");
    }

    private boolean renderError(final HttpServletRequest request, final HttpServletResponse response, final String message) throws IOException {
        // 非ajax CsrfToken校验异常，先清理token
        csrfTokenRepository.saveToken(null, request, response);
        throw new MessageException(ModelResult.CODE_500, message);
    }

    /**
     * 用于清理@CsrfToken保证只能请求成功一次
     */
    @Override
    public void postHandle(final HttpServletRequest request, final HttpServletResponse response, final Object handler, final ModelAndView modelAndView) throws Exception {
        // 非控制器请求直接跳出
        if (!(handler instanceof HandlerMethod)) {
            return;
        }
        HandlerMethod handlerMethod = (HandlerMethod) handler;

        CsrfToken csrfToken = handlerMethod.getMethodAnnotation(CsrfToken.class);
        if (csrfToken == null || !csrfToken.remove()) {
            return;
        }
        csrfTokenRepository.saveToken(null, request, response);
    }
}

package com.jeasy.base.web.filter;

import com.jeasy.common.Func;
import org.slf4j.MDC;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @author taomk
 * @version 1.0
 * @since 2015/05/13 17:34
 */
public class MdcLogFilter implements Filter {

    @Override
    public void doFilter(final ServletRequest rq, final ServletResponse rp, final FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) rq;
        HttpServletResponse response = (HttpServletResponse) rp;

        try {
            // 保存信息
            putMDC(request, response);
            chain.doFilter(request, response);
        } finally {
            // 记得 clear 相关信息，否则会导致内存溢出
            clearMDC(request);
        }
    }

    @Override
    public void init(final FilterConfig config) throws ServletException {
    }

    @Override
    public void destroy() {
    }

    private void putMDC(final HttpServletRequest request, final HttpServletResponse response) {
        MDC.put("remoteAddr", request.getRemoteAddr());
        MDC.put("remoteHost", request.getRemoteHost());
        HttpSession session = request.getSession(false);
        if (Func.isNotEmpty(session)) {
            MDC.put("sessionId", session.getId());
        }
    }

    private void clearMDC(final HttpServletRequest request) {
        MDC.clear();
    }
}

package com.jeasy.base.web.xss;

import com.jeasy.common.ConvertKit;
import com.jeasy.common.Func;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * 安全的Filter(过滤SQL和XSS)
 *
 * @author taomk
 * @version 1.0
 * @since 15-8-4 下午4:45
 */
public class HttpServletRequestSafeFilter implements Filter {

    public static final String FILTER_XSS = "filterXSS";
    public static final String FILTER_SQL = "filterSQL";
    FilterConfig filterConfig = null;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.filterConfig = filterConfig;
    }

    @Override
    public void destroy() {
        this.filterConfig = null;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        boolean filterXSS = false;
        boolean filterSQL = false;

        if (Func.isNotEmpty(filterConfig.getInitParameter(FILTER_XSS))) {
            filterXSS = ConvertKit.toBool(filterConfig.getInitParameter(FILTER_XSS));
        }

        if (Func.isNotEmpty(filterConfig.getInitParameter(FILTER_SQL))) {
            filterSQL = ConvertKit.toBool(filterConfig.getInitParameter(FILTER_SQL));
        }

        chain.doFilter(new HttpServletRequestSafeWrapper((HttpServletRequest) request, filterXSS, filterSQL), response);
    }
}

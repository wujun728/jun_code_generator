package com.jeasy.base.web.xss;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.jeasy.common.Func;
import com.jeasy.common.str.StrKit;
import com.jeasy.common.web.WafKit;
import org.springframework.web.util.HtmlUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Map;
import java.util.Set;

/**
 * 安全的HttpServlet(过滤SQL和XSS)
 *
 * @author taomk
 * @version 1.0
 * @since 15-8-4 下午4:45
 */
public class HttpServletRequestSafeWrapper extends HttpServletRequestWrapper {

    private boolean filterXSS = true;

    private boolean filterSQL = true;

    private HttpServletRequest orgRequest = null;

    public HttpServletRequestSafeWrapper(HttpServletRequest request) {
        super(request);
        this.orgRequest = request;
    }

    public HttpServletRequestSafeWrapper(HttpServletRequest request, boolean filterXSS, boolean filterSQL) {
        super(request);
        orgRequest = request;
        this.filterXSS = filterXSS;
        this.filterSQL = filterSQL;
    }

    @Override
    public Enumeration<String> getParameterNames() {
        Set<String> parameterNameSafeList = Sets.newHashSet();
        Enumeration parameterNames = super.getParameterNames();
        while (parameterNames.hasMoreElements()) {
            parameterNameSafeList.add(filterText(String.valueOf(parameterNames.nextElement()), true));
        }
        return Collections.enumeration(parameterNameSafeList);
    }

    @Override
    public Map<String, String[]> getParameterMap() {
        Map<String, String[]> parameterSafeMap = Maps.newHashMap();
        Set parameterNameSet = super.getParameterMap().keySet();
        for (Object key : parameterNameSet) {
            parameterSafeMap.put(String.valueOf(key), getParameterValues(String.valueOf(key)));
        }
        return parameterSafeMap;
    }

    @Override
    public String[] getParameterValues(String name) {
        String[] values = super.getParameterValues(filterText(name, true));
        if (Func.isEmpty(values)) {
            return null;
        }
        int count = values.length;
        String[] safeValues = new String[count];
        for (int i = 0; i < count; i++) {
            // 判断是否为JSON格式数据
            if (isJsonFormat(values[i])) {
                safeValues[i] = filterText(values[i], false);
            } else {
                safeValues[i] = filterText(values[i], true);
            }

        }
        return safeValues;
    }

    /**
     * 覆盖getParameter方法，将参数名和参数值都做xss & sql过滤。<br/>
     * 如果需要获得原始的值，则通过super.getParameterValues(name)来获取<br/>
     * getParameterNames,getParameterValues和getParameterMap也可能需要覆盖
     */
    @Override
    public String getParameter(String name) {
        String value = super.getParameter(filterText(name, true));
        String safeValue = null;
        if (Func.isNotEmpty(value)) {
            // 判断是否为JSON格式数据
            if (isJsonFormat(value)) {
                safeValue = filterText(value, false);
            } else {
                safeValue = filterText(value, true);
            }
        }
        return safeValue;
    }

    /**
     * 覆盖getHeader方法，将参数名和参数值都做xss & sql过滤。<br/>
     * 如果需要获得原始的值，则通过super.getHeaders(name)来获取<br/>
     * getHeaderNames 也可能需要覆盖
     */
    @Override
    public String getHeader(String name) {
        String value = super.getHeader(filterText(name, true));
        String safeValue = null;
        if (Func.isNotEmpty(value)) {
            // 判断是否为JSON格式数据
            if (isJsonFormat(value)) {
                safeValue = filterText(value, false);
            } else {
                safeValue = filterText(value, true);
            }
        }
        return safeValue;
    }

    @Override
    public Enumeration<String> getHeaders(String name) {
        Set<String> headerValSafeList = Sets.newHashSet();
        Enumeration headerVals = super.getHeaders(name);
        while (headerVals.hasMoreElements()) {
            headerValSafeList.add(filterText(String.valueOf(headerVals.nextElement()), true));
        }
        return Collections.enumeration(headerValSafeList);
    }

    @Override
    public Enumeration<String> getHeaderNames() {
        Set<String> headerNamesSafeList = Sets.newHashSet();
        Enumeration headerNames = super.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            headerNamesSafeList.add(filterText(String.valueOf(headerNames.nextElement()), true));
        }
        return Collections.enumeration(headerNamesSafeList);
    }

    /**
     * 判断是否为JSON格式
     *
     * @param json
     * @return
     */
    private boolean isJsonFormat(String json) {
        return Func.isNotEmpty(json) && json.startsWith(StrKit.S_DELIM_START) && json.endsWith(StrKit.S_DELIM_END);
    }

    /**
     * 过滤XSS和SQL
     *
     * @param text
     * @param isHtmlEscape
     * @return
     */
    private String filterText(final String text, final boolean isHtmlEscape) {
        String filterText = StrKit.trim(text);
        if (filterXSS) {
            filterText = WafKit.cleanXSS(filterText);
        }

        if (filterSQL) {
            filterText = WafKit.stripSqlInjection(filterText);
        }
        if (isHtmlEscape) {
            filterText = HtmlUtils.htmlEscape(filterText);
        }
        return filterText;
    }


    /**
     * 获取最原始的request
     *
     * @return
     */
    public HttpServletRequest getOrgRequest() {
        return orgRequest;
    }

    /**
     * 获取最原始的request的静态方法
     *
     * @return
     */
    public static HttpServletRequest getOrgRequest(HttpServletRequest req) {
        if (req instanceof HttpServletRequestSafeWrapper) {
            return ((HttpServletRequestSafeWrapper) req).getOrgRequest();
        }
        return req;
    }
}

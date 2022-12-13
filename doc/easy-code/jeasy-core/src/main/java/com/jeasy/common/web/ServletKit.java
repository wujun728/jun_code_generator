package com.jeasy.common.web;

import com.jeasy.common.Func;
import com.jeasy.common.charset.CharsetKit;
import com.jeasy.common.str.StrKit;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.Enumeration;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.TreeMap;

/**
 * The Class ServletUtils.
 *
 * @author taomk
 * @version 1.0
 * @since 2015/05/13 17:34
 */
public class ServletKit {

    // -- Content Type 定义 --//
    /**
     * The Constant TEXT_TYPE.
     */
    public static final String TEXT_TYPE = "text/plain";

    /**
     * The Constant JSON_TYPE.
     */
    public static final String JSON_TYPE = "application/json";

    /**
     * The Constant XML_TYPE.
     */
    public static final String XML_TYPE = "text/xml";

    /**
     * The Constant HTML_TYPE.
     */
    public static final String HTML_TYPE = "text/html";

    /**
     * The Constant JS_TYPE.
     */
    public static final String JS_TYPE = "text/javascript";

    /**
     * The Constant STREAM_TYPE.
     */
    public static final String STREAM_TYPE = "application/octet-stream";

    /**
     * The Constant EXCEL_TYPE.
     */
    public static final String EXCEL_TYPE = "application/vnd.ms-excel";

    // -- Header 定义 --//
    /**
     * The Constant AUTHENTICATION_HEADER.
     */
    public static final String AUTHENTICATION_HEADER = "Authorization";

    // -- 常用数值定义 --//
    /**
     * The Constant ONE_YEAR_SECONDS.
     */
    public static final long ONE_YEAR_SECONDS = 60 * 60 * 24 * 365;
    public static final int THOUSAND = 1000;

    /**
     * 设置客户端缓存过期时间 Header.
     *
     * @param response       the response
     * @param expiresSeconds the expires seconds
     */
    public static void setExpiresHeader(HttpServletResponse response, long expiresSeconds) {
        // Http 1.0 header
        response.setDateHeader("Expires", System.currentTimeMillis() + expiresSeconds * THOUSAND);
        // Http 1.1 header
        response.setHeader("Cache-Control", "private, max-age=" + expiresSeconds);
    }

    /**
     * 设置客户端无缓存Header.
     *
     * @param response the new no cache header
     */
    public static void setNoCacheHeader(HttpServletResponse response) {
        // Http 1.0 header
        response.setDateHeader("Expires", 0);
        response.addHeader("Pragma", "no-cache");
        // Http 1.1 header
        response.setHeader("Cache-Control", "no-cache");
    }

    /**
     * 设置LastModified Header.
     *
     * @param response         the response
     * @param lastModifiedDate the last modified date
     */
    public static void setLastModifiedHeader(HttpServletResponse response, long lastModifiedDate) {
        response.setDateHeader("Last-Modified", lastModifiedDate);
    }

    /**
     * 设置Etag Header.
     *
     * @param response the response
     * @param etag     the etag
     */
    public static void setEtag(HttpServletResponse response, String etag) {
        response.setHeader("ETag", etag);
    }

    /**
     * 根据浏览器If-Modified-Since Header, 计算文件是否已被修改.
     * <p>
     * 如果无修改, checkIfModify返回false ,设置304 not modify status.
     *
     * @param request      the request
     * @param response     the response
     * @param lastModified 内容的最后修改时间.
     * @return true, if successful
     */
    public static boolean checkIfModifiedSince(HttpServletRequest request, HttpServletResponse response,
                                               long lastModified) {
        long ifModifiedSince = request.getDateHeader("If-Modified-Since");
        if ((ifModifiedSince != -1) && (lastModified < ifModifiedSince + THOUSAND)) {
            response.setStatus(HttpServletResponse.SC_NOT_MODIFIED);
            return false;
        }
        return true;
    }

    /**
     * 根据浏览器 If-None-Match Header, 计算Etag是否已无效.
     * <p>
     * 如果Etag有效, checkIfNoneMatch返回false, 设置304 not modify status.
     *
     * @param request  the request
     * @param response the response
     * @param etag     内容的ETag.
     * @return true, if successful
     */
    public static boolean checkIfNoneMatchEtag(HttpServletRequest request, HttpServletResponse response, String etag) {
        String headerValue = request.getHeader("If-None-Match");
        if (Func.isNotEmpty(headerValue)) {
            boolean conditionSatisfied = false;
            if (!StrKit.S_ASTERISK.equals(headerValue)) {
                StringTokenizer commaTokenizer = new StringTokenizer(headerValue, ",");

                while (!conditionSatisfied && commaTokenizer.hasMoreTokens()) {
                    String currentToken = commaTokenizer.nextToken();
                    if (currentToken.trim().equals(etag)) {
                        conditionSatisfied = true;
                    }
                }
            } else {
                conditionSatisfied = true;
            }

            if (conditionSatisfied) {
                response.setStatus(HttpServletResponse.SC_NOT_MODIFIED);
                response.setHeader("ETag", etag);
                return false;
            }
        }
        return true;
    }

    /**
     * 设置让浏览器弹出下载对话框的Header.
     *
     * @param response the response
     * @param fileName 下载后的文件名.
     */
    public static void setFileDownloadHeader(HttpServletResponse response, String fileName) {
        try {
            // 中文文件名支持
            String encodedfileName = new String(fileName.getBytes(CharsetKit.DEFAULT_CHARSET), "ISO8859-1");
            response.setHeader("Content-Disposition", "attachment; filename=\"" + encodedfileName + "\"");
        } catch (UnsupportedEncodingException e) {
        }
    }

    /**
     * 取得带相同前缀的Request Parameters.
     * <p>
     * 返回的结果Parameter名已去除前缀.
     *
     * @param request the request
     * @param prefix  the _prefix
     * @return the parameters starting with
     */
    public static Map getParametersStartingWith(HttpServletRequest request, String prefix) {
        Enumeration paramNames = request.getParameterNames();
        Map params = new TreeMap();
        if (Func.isEmpty(prefix)) {
            prefix = StrKit.S_EMPTY;
        }
        while (Func.isNotEmpty(paramNames) && paramNames.hasMoreElements()) {
            String paramName = (String) paramNames.nextElement();
            if (Func.isEmpty(prefix) || paramName.startsWith(prefix)) {
                String unprefixed = paramName.substring(prefix.length());
                String[] values = request.getParameterValues(paramName);
                // NOSONAR
                if (Func.isEmpty(values)) {
                    // Do nothing, no values found at all.
                } else if (values.length > 1) {
                    params.put(unprefixed, values);
                } else {
                    params.put(unprefixed, values[0]);
                }
            }
        }
        return params;
    }

}

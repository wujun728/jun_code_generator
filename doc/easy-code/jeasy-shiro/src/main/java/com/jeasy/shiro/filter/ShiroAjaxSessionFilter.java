package com.jeasy.shiro.filter;

import com.jeasy.common.web.RequestKit;
import org.apache.shiro.web.filter.authc.UserFilter;
import org.apache.shiro.web.util.WebUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * ajax shiro session超时统一处理
 *
 * @author taomk
 * @version 1.0
 * @since 2014/9/22 14:33
 */
public class ShiroAjaxSessionFilter extends UserFilter {

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest req = WebUtils.toHttp(request);
        if (RequestKit.isAjaxRequest(req)) {
            HttpServletResponse res = WebUtils.toHttp(response);
            // 采用res.sendError(401);在Easyui中会处理掉error，$.ajaxSetup中监听不到
            res.setHeader("oauthstatus", "401");
            return false;
        }
        return super.onAccessDenied(request, response);
    }
}

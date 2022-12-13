package com.jeasy.shiro.filter;

import com.jeasy.base.web.dto.CurrentUser;
import com.jeasy.common.Func;
import com.jeasy.common.web.RequestKit;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.DefaultSessionKey;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.Serializable;
import java.util.Deque;
import java.util.LinkedList;

/**
 * 用户登录并发控制
 *
 * @author taomk
 * @version 1.0
 * @since 2014/9/22 14:33
 */
@Slf4j
@Data
@EqualsAndHashCode(callSuper = true)
public class KickoutSessionControlFilter extends AccessControlFilter implements InitializingBean {

    /**
     * 踢出后到的地址
     */
    private String kickoutUrl;

    /**
     * 踢出之前登录的/之后登录的用户 默认踢出之前登录的用户
     */
    private boolean kickoutAfter = false;

    /**
     * 同一个帐号最大会话数 默认1
     */
    private int maxSession = 1;

    private String kickoutAttribute = "kickout";

    private SessionManager sessionManager;

    private CacheManager cacheManager;

    private String kickoutCacheName = "shiro-kickout-session";

    private Cache<String, Deque<Serializable>> cache;

    @Override
    protected boolean isAccessAllowed(final ServletRequest request, final ServletResponse response, final Object mappedValue) throws Exception {
        return false;
    }

    @Override
    protected boolean onAccessDenied(final ServletRequest request, final ServletResponse response) throws Exception {
        Subject subject = getSubject(request, response);
        if (!subject.isAuthenticated() && !subject.isRemembered()) {
            // 如果没有登录，直接进行之后的流程
            return true;
        }

        Session session = subject.getSession();
        CurrentUser currentUser = (CurrentUser) subject.getPrincipal();
        String username = currentUser.getLoginName();
        Serializable sessionId = session.getId();

        Deque<Serializable> deque = cache.get(username);
        if (Func.isEmpty(deque)) {
            deque = new LinkedList<>();
        }

        // 如果队列里没有此sessionId，且用户没有被踢出；放入队列
        if (!deque.contains(sessionId) && Func.isEmpty(session.getAttribute(kickoutAttribute))) {
            deque.push(sessionId);
            cache.put(username, deque);
        }

        // 如果队列里的sessionId数超出最大会话数，开始踢人
        while (deque.size() > maxSession) {
            Serializable kickoutSessionId;
            if (kickoutAfter) {
                // 如果踢出后者
                kickoutSessionId = deque.removeFirst();
            } else {
                // 否则踢出前者
                kickoutSessionId = deque.removeLast();
            }
            try {
                Session kickoutSession = sessionManager.getSession(new DefaultSessionKey(kickoutSessionId));
                if (Func.isNotEmpty(kickoutSession)) {
                    // 设置会话的kickout属性表示踢出了
                    kickoutSession.setAttribute(kickoutAttribute, true);
                }
            } catch (Exception e) {
                log.error("获取 [" + kickoutSessionId + "] Session对象异常: ", e);
            } finally {
                cache.put(username, deque);
            }
        }

        // 如果被踢出了，直接退出，重定向到踢出后的地址
        if (Func.isNotEmpty(session.getAttribute(kickoutAttribute))) {
            // 会话被踢出了
            try {
                subject.logout();
            } catch (Exception e) {
                log.error(session.getId() + "Session登出异常: ", e);
            }

            HttpServletRequest httpServletRequest = WebUtils.toHttp(request);
            HttpServletResponse httpServletResponse = WebUtils.toHttp(response);
            saveRequest(httpServletRequest);

            // 1. 前后端分离：不使用重定向，而判断是否为Ajax请求，退出后设置Response Header "oauthstatus=401"
            if (RequestKit.isAjaxRequest(httpServletRequest)) {
                // 采用res.sendError(401);在Easyui中会处理掉error，$.ajaxSetup中监听不到
                httpServletResponse.setHeader("oauthstatus", "401");
            }

            // 2. 非前后端分离：使用重定向
            // WebUtils.issueRedirect(httpServletRequest, httpServletResponse, kickoutUrl);
            return false;
        }
        return true;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Assert.notNull(cacheManager, "you must set cacheManager!");
        this.cache = cacheManager.getCache(kickoutCacheName);
    }
}

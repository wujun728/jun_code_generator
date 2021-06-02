package org.coderfun.boot.core.shiro;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;


/**
 * 
 * 在shiro应用中，session统一交由shiro管理即可
 * 
 * @author klguang[klguang@foxmail.com]
 * @date 2018年11月12日
 */
public class ShiroSessionUtil {
	
	public static Session getSession() {
		return SecurityUtils.getSubject().getSession();
	}

	public static SessionUser getSessionUser(){
		return (SessionUser) SecurityUtils.getSubject().getPrincipal();
	}
	
	public static void setAttribute(String key, Object value) {
		getSession().setAttribute(key, value);
	}

	public static Object getAttribute(String key) {
		return getSession().getAttribute(key);
	}
	
}

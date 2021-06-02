package org.coderfun.boot.core.shiro;

import java.util.List;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.shiro.web.filter.authz.AuthorizationFilter;
import org.coderfun.boot.core.BootConst;
import org.coderfun.boot.core.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;


/**
 * 
 * 页面权限
 * 
 * @author klguang[klguang@foxmail.com]
 * @date 2018年11月11日
 */
public class PageAccessFilter extends AuthorizationFilter {
	
	private static final Logger logger = LoggerFactory.getLogger(PageAccessFilter.class);
	
	@Autowired
	UserService userService;
	
	@Override
	protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
		// TODO Auto-generated method stub
		//获取请求URI
		String requestURI = getPathWithinApplication(request);
		logger.info("shiro页面拦截URI:{}",requestURI);
		
		List<String> menuUrls = (List<String>) ShiroSessionUtil.getAttribute(BootConst.SESSION_USER_MENUS);
		if(null == menuUrls){
			SessionUser user = ShiroSessionUtil.getSessionUser();
			if(null == user){
				return false;
			}
			logger.info("从数据库查询用户id={}所有MenuUrls，并存入session-{}",user.getId(),ShiroSessionUtil.getSession().getId());
			menuUrls = userService.queryMenuUrls(user.getId());
			ShiroSessionUtil.setAttribute(BootConst.SESSION_USER_MENUS, menuUrls);
		}

		
		if(menuUrls.contains(requestURI)){
			return true;
		}		
		return false;
	}
	
	
	
}

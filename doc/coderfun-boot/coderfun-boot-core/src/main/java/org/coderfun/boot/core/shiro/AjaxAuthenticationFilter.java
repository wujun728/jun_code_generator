package org.coderfun.boot.core.shiro;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.coderfun.common.exception.ErrorCodeEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import klg.common.model.JsonData;
import klg.common.utils.JacksonUtil;

public class AjaxAuthenticationFilter extends FormAuthenticationFilter{

	private static final Logger logger = LoggerFactory.getLogger(AjaxAuthenticationFilter.class);
	
	/**
	 * 对ajax为认证的请求设置sessionStatus超时，非ajax方式则正常处理
	 */
	@Override
	protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		//获取请求URI
		String requestURI = getPathWithinApplication(request);
		logger.info("shiro未登录拦截URI：{}",requestURI);
		logger.info("session-{}" , ShiroSessionUtil.getSession().getId());
		
		HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        if (isAjax(request)) {

        	logger.info("shiro未登录拦截：ajax请求处理");
        	//超时请求头
            httpServletResponse.setHeader("sessionStatus", "timeout");
            httpServletResponse.setStatus(HttpServletResponse.SC_FORBIDDEN);
            
        	httpServletResponse.setCharacterEncoding("UTF-8");
            httpServletResponse.setContentType("application/json");
            JsonData jsonData = JsonData.error(ErrorCodeEnum.UNAUTHENTICATED.getCode(), 
            		ErrorCodeEnum.UNAUTHENTICATED.getMessageFormat());
            httpServletResponse.getWriter().write(JacksonUtil.toJSONString(jsonData));
            
            return false;
        } else {
        	logger.info("shiro未登录拦截：非ajax请求正常处理");
        	//非ajax请求正常处理
            return super.onAccessDenied(request, httpServletResponse);
        }

	}

    private boolean isAjax(ServletRequest request){
        String header = ((HttpServletRequest) request).getHeader("X-Requested-With");
        if("XMLHttpRequest".equalsIgnoreCase(header)){
            return true;
        }
        return false;
    }

    @Override
    public void afterCompletion(ServletRequest request, ServletResponse response, Exception exception) throws Exception {
    	// TODO Auto-generated method stub
    	if(exception !=null){
        	logger.error("shiro filter afterCompletion",exception);    		
    	}
    }
    
}

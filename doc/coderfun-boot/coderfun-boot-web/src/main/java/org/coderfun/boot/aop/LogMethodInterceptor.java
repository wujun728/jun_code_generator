package org.coderfun.boot.aop;

import java.lang.reflect.Method;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.coderfun.boot.core.BootDict;
import org.coderfun.boot.core.shiro.SessionUser;
import org.coderfun.boot.core.shiro.ShiroSessionUtil;
import org.coderfun.common.log.IPUtils;
import org.coderfun.common.log.Logger;
import org.coderfun.common.log.entity.SysLog;
import org.coderfun.common.log.service.SysLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import eu.bitwalker.useragentutils.UserAgent;
import eu.bitwalker.useragentutils.Version;
import klg.common.utils.JacksonUtil;

public class LogMethodInterceptor implements MethodInterceptor{
	@Autowired
	private SysLogService sysLogService;
	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {
		// TODO Auto-generated method stub
		long beginTime = System.currentTimeMillis();
		
		Object result = null;
		SysLog sysLog = buildSysLog(invocation);
		try {
			//执行方法
			 result = invocation.proceed();
			 sysLog.setSuccessed(BootDict.YES);
		} catch (Exception e) {
			// TODO: handle exception
			sysLog.setMessage(e.getMessage());
			sysLog.setSuccessed(BootDict.NO);
			throw e;
		}finally {
			//执行时长(毫秒)
			long time = System.currentTimeMillis() - beginTime;
			sysLog.setExecuteTime(time);
			
			//保存系统日志
			sysLogService.save(sysLog);
		}
		
		return result;
	}
	private SysLog buildSysLog(MethodInvocation invocation) {
		Method method = invocation.getMethod();

		SysLog sysLog = new SysLog();
		Logger logger = method.getAnnotation(Logger.class);
		if(logger != null){
			//注解上的描述
			sysLog.setName(logger.name());
			sysLog.setModuleCode(logger.moduleCode());
		}

		//请求的方法名
		String className = method.getDeclaringClass().getName();
		String methodName = method.getName();
		sysLog.setMethod(className + "." + methodName + "()");

		//请求的参数,只保存第一个参数
		Object[] args = invocation.getArguments();
		try{
			String params = JacksonUtil.printJSON(args[0]);
			sysLog.setParams(params);
		}catch (Exception e){
			e.printStackTrace();
		}

		//获取request
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		//设置IP地址
		sysLog.setOpip(IPUtils.getIpAddr(request));
		//客户操作系统，浏览器
		UserAgent userAgent = UserAgent.parseUserAgentString(request.getHeader("User-Agent"));
		String os=userAgent.getOperatingSystem().getName();	//获取客户端操作系统
		Version version = userAgent.getBrowser().getVersion(request.getHeader("User-Agent"));
		String browser=userAgent.getBrowser().getName() + "/" + version.getVersion();	//获取客户端浏览器
		sysLog.setOs(os);
		sysLog.setBrowser(browser);
		
		//用户名
		SessionUser sessionUser = ShiroSessionUtil.getSessionUser();
		sysLog.setOpusername(sessionUser.getLoginName());

		sysLog.setOptime(new Date());

		return sysLog;
	}
}

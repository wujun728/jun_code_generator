package org.coderfun.boot.web.controller;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.coderfun.boot.core.BootConst;
import org.coderfun.boot.core.BootConst.KickoutMode;
import org.coderfun.boot.core.BootDict;
import org.coderfun.boot.core.BootSettings;
import org.coderfun.boot.core.entity.User;
import org.coderfun.boot.core.exception.BootErrorCode;
import org.coderfun.boot.core.service.UserService;
import org.coderfun.boot.core.shiro.SessionUser;
import org.coderfun.boot.core.shiro.ShiroSessionUtil;
import org.coderfun.boot.core.shiro.SimplePasswordMatcher;
import org.coderfun.common.exception.AppException;
import org.coderfun.common.log.IPUtils;
import org.coderfun.common.log.LogModuleCode;
import org.coderfun.common.log.entity.LoginLog;
import org.coderfun.common.log.entity.SysLog;
import org.coderfun.common.log.service.LoginLogService;
import org.coderfun.common.log.service.SysLogService;
import org.crazycake.shiro.RedisSessionDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import eu.bitwalker.useragentutils.UserAgent;
import eu.bitwalker.useragentutils.Version;
import klg.common.model.JsonData;

@Controller("adminUserAccessController")
@RequestMapping("/admin/action/boot/userAccess")
public class UserAccessController {
	
	private static Logger logger = LoggerFactory.getLogger(UserAccessController.class);
	
	@Autowired
	UserService userService;
	
    /**
     * 登录
     *
     * @return
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
	public JsonData login(
			@RequestParam String username, 
			@RequestParam String password,
			@RequestParam String kaptcha,
			HttpServletRequest request){
    	//验证码
    	String captcha = (String) SecurityUtils.getSubject().getSession().getAttribute(com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY);
    	
    	if(!kaptcha.equals(captcha)){
    		saveLog(username, BootDict.NO, "验证码错误", request);
    		throw new AppException(BootErrorCode.CAPTCHA_WORNG);
    	}
    	
        //用户名密码验证
        UsernamePasswordToken token = new UsernamePasswordToken(username, password, false);
        Subject subject = SecurityUtils.getSubject();
        
        try {
        	subject.login(token);
		} catch (IncorrectCredentialsException e ){
			logger.error("登录失败{}", e.getMessage());//用户名或密码错误
			saveLog(username, BootDict.NO, "密码错误", request);
			throw new AppException(BootErrorCode.LOGIN_FAILED);
		}catch (UnknownAccountException e) {
            logger.error("登录失败{}", e.getMessage());//帐号不存在
			saveLog(username, BootDict.NO, "帐号不存在", request);
            throw new AppException(BootErrorCode.LOGIN_FAILED);
        }
        

        if(BootSettings.enableLoginKickout()){
            // 踢出之前登录的用户
            SessionUser sessionUser = ShiroSessionUtil.getSessionUser();
            userService.kickout(sessionUser.getId(), KickoutMode.BEFORE);        	
        }
        if(BootSettings.enableLoginLog()){
    		//保存日志
    		saveLog(username, BootDict.YES, null, request);
        }
		return JsonData.success();
	}
	
    
    @Autowired
    LoginLogService loginLogService;
    private void saveLog(String loginName , String successed ,String message, HttpServletRequest request){
		LoginLog log = new LoginLog();
		log.setSuccessed(successed);
		log.setOpusername(loginName);
		log.setMessage(message);
				
		
		UserAgent userAgent = UserAgent.parseUserAgentString(request.getHeader("User-Agent"));
		String os=userAgent.getOperatingSystem().getName();	//获取客户端操作系统

		Version version = userAgent.getBrowser().getVersion(request.getHeader("User-Agent"));
		String browser=userAgent.getBrowser().getName() + "/" + version.getVersion();	//获取客户端浏览器
		
		log.setOs(os);
		log.setBrowser(browser);
		log.setOptime(new Date());
		log.setOpip(IPUtils.getIpAddr(request));

		loginLogService.save(log);
    }
    
    /**
     * 登出
     * @return
     */
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout() {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return "redirect:" + BootSettings.getAdminPath() + "/login";
	}
	
    
    @RequestMapping(value = "/changeProfile", method = RequestMethod.POST)
    @ResponseBody
    public JsonData changeProfile(@ModelAttribute User profile){
    	SessionUser sessionUser = ShiroSessionUtil.getSessionUser();
    	User user = userService.getById(sessionUser.getId());
    	user.setGender(profile.getGender());
    	user.setBirthday(profile.getBirthday());
    	user.setEmail(profile.getEmail());
    	user.setPhone(profile.getPhone());
    	userService.update(user);
    	
    	
    	return JsonData.success(user);
    }
    
    @RequestMapping(value = "/getProfile", method = RequestMethod.GET)
    @ResponseBody
    public JsonData getProfile(){
    	SessionUser sessionUser = ShiroSessionUtil.getSessionUser();
        User user = userService.getById(sessionUser.getId());
    	return JsonData.success(user);
    }
    
    
    @RequestMapping(value = "/changePassword", method = RequestMethod.POST)
    @ResponseBody
	public JsonData changePassword(
			@RequestParam String oldPlainPassword,
			@RequestParam String newPlainPassword){
    	
    	SessionUser sessionUser = ShiroSessionUtil.getSessionUser();
    	userService.changePassword(sessionUser.getId(), oldPlainPassword, newPlainPassword);
    	
		return JsonData.success();
	}
    
    
//	@Autowired
//	SimplePasswordMatcher passwordMatcher;
//    
//	@ResponseBody
//	@RequestMapping(value = "/testPassword", method = RequestMethod.GET)
//	public JsonData testPassword(
//			@RequestParam String password,
//			@RequestParam String salt,
//			HttpSession session){
//		SessionUser sessionUser = ShiroSessionUtil.getSessionUser();
//		if(sessionUser.getId().equals(Const.SUPER_ADMIN_ID)){
//			return JsonData.success(passwordMatcher.hashPassword(password, salt).toHex());			
//		}else{
//			return JsonData.success();
//		}
//	}
	
//    @Autowired
//    RedisSessionDAO redisSessionDAO;
//    
//    @RequestMapping(value = "/testSessions", method = RequestMethod.GET)
//    public void testSessions(){
//
//        for(Session session:redisSessionDAO.getActiveSessions()){
//        	System.out.println("\r\n--------------------------------\r\n" + session.getId());
//        	for(Object key:session.getAttributeKeys()){
//        		System.out.println(key.toString()+ " : " +session.getAttribute(key.toString()));
//        	}
//        	System.out.println("\r\n--------------------------------");
//        }
//    }
	
}

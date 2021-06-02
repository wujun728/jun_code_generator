package org.coderfun.boot.core.shiro;

import java.util.Set;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.coderfun.boot.core.BootDict;
import org.coderfun.boot.core.entity.User;
import org.coderfun.boot.core.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 
 * @author klguang[klguang@foxmail.com]
 * @date 2018年11月11日
 */
public class UserAuthRealm extends AuthorizingRealm {
	
	@Autowired
	UserService userService;


	/**
	 * 认证,登录时调用.
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		// TODO Auto-generated method stub
		UsernamePasswordToken authcToken = (UsernamePasswordToken) token;
		User user = userService.getByLoginName(authcToken.getUsername());
		if(null != user){
			if(user.getState().equals(BootDict.User.State.FORBIDDEN)){
				throw new LockedAccountException();
			}
			
			//设置用户信息到session
		    SessionUser sessionUser = initSessionUser(user);
	        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
	        		sessionUser, //用户信息，必须有authCacheKey
	                user.getPassword(), //散列后的密码
	                ByteSource.Util.bytes(user.getSalt()), // salt
	                getName()  //realm name
	        );
	       
        	

			return authenticationInfo;
		}
		return null;
	}
	
	/**
	 * 授权, 进行鉴权但缓存中无用户的授权信息时调用.
	 */
	
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		
		//System.out.println(org.apache.shiro.subject.support.DefaultSubjectContext.PRINCIPALS_SESSION_KEY);
		//principals 是保存在session中的
		SessionUser sessionUser= ShiroSessionUtil.getSessionUser();
		
        //用户权限列表
        Set<String> permsSet = userService.queryPermissions(sessionUser.getId());
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		info.setStringPermissions(permsSet);
		return info;
	}


	private SessionUser initSessionUser(User user){
		SessionUser sessionUser = new SessionUser();
		sessionUser.setId(user.getId());
		sessionUser.setLoginName(user.getLoginName());
		sessionUser.setName(user.getName());
		sessionUser.setAvatar(user.getAvatar());
		
		return sessionUser;
	}
	

    @Override
    public void clearCachedAuthenticationInfo(PrincipalCollection principals) {
        super.clearCachedAuthenticationInfo(principals);
    }


	@Override
    public void clearCachedAuthorizationInfo(PrincipalCollection principals) {
        super.clearCachedAuthorizationInfo(principals);
    }
    
    @Override
    public void clearCache(PrincipalCollection principals) {
        super.clearCache(principals);
    }
}

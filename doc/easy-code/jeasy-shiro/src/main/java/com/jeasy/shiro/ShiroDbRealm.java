package com.jeasy.shiro;

import com.jeasy.base.web.dto.CurrentUser;
import com.jeasy.base.web.dto.ModelResult;
import com.jeasy.common.ConvertKit;
import com.jeasy.common.Func;
import com.jeasy.common.str.StrKit;
import com.jeasy.dictionary.DictionaryKit;
import com.jeasy.exception.MessageException;
import com.jeasy.resource.dto.ResourceDTO;
import com.jeasy.resource.service.ResourceService;
import com.jeasy.roleresource.dto.RoleResourceDTO;
import com.jeasy.roleresource.service.RoleResourceService;
import com.jeasy.user.dto.UserDTO;
import com.jeasy.user.service.UserService;
import com.jeasy.userrole.dto.UserRoleDTO;
import com.jeasy.userrole.service.UserRoleService;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

import java.io.Serializable;
import java.util.Deque;
import java.util.List;
import java.util.Set;

/**
 * shiro权限认证
 *
 * @author taomk
 * @version 1.0
 * @since 2014/9/22 14:33
 */
@Slf4j
@Data
@EqualsAndHashCode(callSuper = true)
public class ShiroDbRealm extends AuthorizingRealm implements InitializingBean {

    @Lazy
    @Autowired
    private UserService userService;

    @Lazy
    @Autowired
    private UserRoleService userRoleService;

    @Lazy
    @Autowired
    private RoleResourceService roleResourceService;

    @Lazy
    @Autowired
    private ResourceService resourceService;

    private CacheManager cacheManager;

    private String kickoutCacheName = "shiro-kickout-session";

    private Cache<String, Deque<Serializable>> cache;

    public ShiroDbRealm(final CacheManager cacheManager, final CredentialsMatcher matcher) {
        super(cacheManager, matcher);
    }

    /**
     * Shiro登录认证(原理：用户提交 用户名和密码  --- shiro 封装令牌 ---- realm 通过用户名将密码查询返回 ---- shiro 自动去比较查询出密码和用户输入密码是否一致---- 进行登陆控制 )
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(final AuthenticationToken authcToken) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
        UserDTO userDTO = new UserDTO();
        // 此处token.getUsername()对应的user.name而非user.loginName
        userDTO.setLoginName(token.getUsername());
        UserDTO user = userService.findOne(userDTO);
        // 账号不存在
        if (user == null) {
            throw new UnknownAccountException();
        }

        // 账号未启用
        if (user.getStatusVal() == DictionaryKit.YHZT_TY_VAL().intValue()) {
            throw new DisabledAccountException();
        }

        CurrentUser currentUser = new CurrentUser();
        currentUser.setId(user.getId());
        currentUser.setName(user.getName());
        currentUser.setLoginName(token.getUsername());
        currentUser.setCode(user.getCode());
        currentUser.setIsTest(user.getIsTest());

        UserRoleDTO paramDTO = new UserRoleDTO();
        paramDTO.setUserId(currentUser.getId());

        List<UserRoleDTO> userRoleDTOs = userRoleService.find(paramDTO);
        if (Func.isEmpty(userRoleDTOs)) {
            throw new MessageException(ModelResult.CODE_200, "账号未分配角色");
        }

        currentUser.setRoleId(userRoleDTOs.get(0).getRoleId());
        currentUser.setRoleName(userRoleDTOs.get(0).getRoleName());
        currentUser.setRoleCode(userRoleDTOs.get(0).getRoleCode());

        // 认证缓存信息
        return new SimpleAuthenticationInfo(currentUser, user.getPwd().toCharArray(), ShiroByteSource.of(Func.isEmpty(user.getSalt()) ? StrKit.S_EMPTY : user.getSalt()), getName());
    }

    /**
     * Shiro权限认证
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(final PrincipalCollection principals) {
        CurrentUser currentUser = (CurrentUser) principals.getPrimaryPrincipal();

        UserRoleDTO paramDTO = new UserRoleDTO();
        paramDTO.setUserId(currentUser.getId());

        List<UserRoleDTO> userRoleDTOs = userRoleService.find(paramDTO);

        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        if (Func.isEmpty(userRoleDTOs)) {
            return info;
        }

        List<Long> roleIdList = com.google.common.collect.Lists.newArrayList();
        Set<String> roleCodeSet = com.google.common.collect.Sets.newHashSet();
        for (UserRoleDTO userRoleDTO : userRoleDTOs) {
            roleIdList.add(userRoleDTO.getRoleId());
            roleCodeSet.add(userRoleDTO.getRoleCode());
        }

        Set<String> permissionCodeSet = com.google.common.collect.Sets.newHashSet();
        List<RoleResourceDTO> roleResourceDTOList = roleResourceService.findByRoleIds(roleIdList);
        for (RoleResourceDTO roleResourceDTO : roleResourceDTOList) {
            ResourceDTO resourceDTO = resourceService.findById(roleResourceDTO.getResourceId());
            if (Func.isNotEmpty(resourceDTO) && !ConvertKit.toBool(resourceDTO.getIsMenu())) {
                permissionCodeSet.add(roleResourceDTO.getResourceCode());
            }
        }

        info.setRoles(roleCodeSet);
        info.addStringPermissions(permissionCodeSet);

        return info;
    }

    @Override
    public void onLogout(final PrincipalCollection principals) {
        super.clearCachedAuthorizationInfo(principals);
        CurrentUser user = (CurrentUser) principals.getPrimaryPrincipal();
        removeUserCache(user);
    }

    /**
     * 清除用户缓存
     *
     * @param user
     */
    public void removeUserCache(final CurrentUser user) {
        SimplePrincipalCollection principals = new SimplePrincipalCollection();
        principals.add(user.getLoginName(), super.getName());
        if (Func.isNotEmpty(this.cache)) {
            Serializable sessionId = SecurityUtils.getSubject().getSession().getId();
            Deque<Serializable> deque = cache.get(user.getLoginName());
            // 判断队列中是否存在sessionId, 存在则删除，更新缓存
            if (Func.isNotEmpty(deque) && deque.contains(sessionId) && deque.remove(sessionId)) {
                this.cache.put(user.getLoginName(), deque);
            }
        }
        super.clearCachedAuthenticationInfo(principals);
        super.clearCachedAuthorizationInfo(principals);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        if (Func.isNotEmpty(this.cacheManager)) {
            this.cache = this.cacheManager.getCache(kickoutCacheName);
        }
    }
}

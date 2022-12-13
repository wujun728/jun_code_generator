package com.jeasy.base.web.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Set;

/**
 * 自定义Authentication对象，使得Subject除了携带用户的登录名外还可以携带更多信息
 *
 * @author taomk
 * @version 1.0
 * @since 2017/08/21 18:29
 */
@Data
public class CurrentUser implements Serializable {

    private static final long serialVersionUID = -1373760761780840081L;

    private Long id;

    private String loginName;

    private String name;

    private String code;

    private Long roleId;

    private String roleName;

    private String roleCode;

    private String mobile;

    private Integer isTest = 0;
    private Integer dataSecurityVal = 0;

    private Set<Long> roleIdSet;
    private Set<String> roleCodeSet;

    private Set<Long> permissionIdSet;
    private Set<String> permissionCodeSet;

    /**
     * 本函数输出将作为默认的<shiro:principal/>输出.
     */
    @Override
    public String toString() {
        return loginName;
    }
}

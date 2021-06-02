package cn.yt4j.layuirbac.model;

import lombok.*;

import java.io.Serializable;

/**
 * 角色权限(RoleMenu)实体类
 *
 * @author gyv12345@163.com
 * @since 2020-03-03 21:47:03
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
public class RoleMenu implements Serializable {
    private static final long serialVersionUID = 614369201595685400L;
    private Integer roleId;
    private Integer menuId;
}
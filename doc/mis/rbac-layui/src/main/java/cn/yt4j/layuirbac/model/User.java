package cn.yt4j.layuirbac.model;

import cn.yt4j.layuirbac.annotation.CreateTime;
import cn.yt4j.layuirbac.annotation.UpdateTime;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

/**
 * @author gyv12345@163.com
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
public class User implements Serializable {

    private static final long serialVersionUID = 3212522351157666317L;

    private Integer id;

    private String username;

    private String password;

    private String realName;

    private String phone;

    private Integer roleId;

    private String roleName;

    private String roleCode;

    @CreateTime
    private Date createTime;

    @UpdateTime
    private Date updateTime;

}

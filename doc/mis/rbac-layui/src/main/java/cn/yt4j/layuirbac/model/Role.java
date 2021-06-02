package cn.yt4j.layuirbac.model;

import cn.yt4j.layuirbac.annotation.CreateTime;
import cn.yt4j.layuirbac.annotation.UpdateTime;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

/**
 * @author gyv12345@163.com
 * @date 2020/3/3 21:00
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
public class Role implements Serializable {

    private static final long serialVersionUID = -7985856889946852806L;

    private Integer id;

    private String roleName;

    private String roleCode;

    private String description;

    @CreateTime
    private Date createTime;

    @UpdateTime
    private Date updateTime;
}

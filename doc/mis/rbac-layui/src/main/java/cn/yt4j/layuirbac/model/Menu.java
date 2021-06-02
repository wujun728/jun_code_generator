package cn.yt4j.layuirbac.model;

import cn.yt4j.layuirbac.annotation.CreateTime;
import cn.yt4j.layuirbac.annotation.UpdateTime;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

/**
 * @author gyv12345@163.com
 * @date 2020/3/3 21:02
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
public class Menu implements Serializable {

    private static final long serialVersionUID = -204440892419680514L;

    private Integer id;

    private String menuName;

    private String menuCode;

    private Integer parentId;

    private String level;

    @CreateTime
    private Date createTime;

    @UpdateTime
    private Date updateTime;
}

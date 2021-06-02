package cn.yt4j.layuirbac.model;

import cn.yt4j.layuirbac.annotation.CreateTime;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

/**
 * (Log)实体类
 *
 * @author gyv12345@163.com
 * @since 2020-03-03 21:47:31
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
public class Log implements Serializable {

    private static final long serialVersionUID = 210630106233040024L;

    private Integer id;

    private String content;

    @CreateTime
    private Date createTime;
}
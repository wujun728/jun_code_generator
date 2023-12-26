package com.jun.plugin.book.app.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 用户表
 * </p>
 *
 * @author wujun
 * @since 2023-12-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

        /**
     * ID主键
     */
         @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

        /**
     * 用户姓名
     */
         private String username;

        /**
     * 用户账号
     */
         private String account;

        /**
     * 用户密码
     */
         private String password;

        /**
     * 创建时间
     */
         private Date createTime;

        /**
     * 更新时间
     */
         private Date updateTime;

        /**
     * 是否弃用
     */
         private Integer deleted;


}

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
 * 图书库
 * </p>
 *
 * @author wujun
 * @since 2023-12-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Book implements Serializable {

    private static final long serialVersionUID = 1L;

        /**
     * id
     */
         @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

        /**
     * 图书名称
     */
         private String title;

        /**
     * 图书描述内容
     */
         private String info;

        /**
     * 图书封面
     */
         private String cover;

        /**
     * 图书分类 JSON
     */
         private String kinds;

        /**
     * 图书作者 JSON
     */
         private String originAuthor;

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
         private Integer loseFlag;


}

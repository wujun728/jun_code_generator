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
 * 我的书单
 * </p>
 *
 * @author wujun
 * @since 2023-12-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class BookList implements Serializable {

    private static final long serialVersionUID = 1L;

        /**
     * id
     */
         @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

        /**
     * 用户编号
     */
         private Integer userId;

        /**
     * 图书编号
     */
         private Integer bookId;

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

package com.jeasy.fileattach.biz.model;

import com.jeasy.validate.AnnotationValidable;
import lombok.Data;

import java.io.Serializable;

/**
 * 文件附件
 *
 * @author taomk
 * @version 1.0
 * @since 2019/06/20 17:27
 */
@Data
public class FileAttachModel implements AnnotationValidable, Serializable {

    private static final long serialVersionUID = 5409185459234711691L;

    /**
     * 主键
     */
    private Long id;

    /**
     * 表名称
     */
    private String tableName;

    /**
     * 记录ID
     */
    private Long recordId;

    /**
     * 文件原名称
     */
    private String name;

    /**
     * 文件URL
     */
    private String url;

    /**
     * 文件图标URL
     */
    private String iconUrl;

    /**
     * 文件预览URL
     */
    private String previewUrl;

    /**
     * 创建时间
     */
    private Long createAt;

    /**
     * 创建人ID
     */
    private Long createBy;

    /**
     * 创建人名称
     */
    private String createName;

    /**
     * 更新时间
     */
    private Long updateAt;

    /**
     * 更新人ID
     */
    private Long updateBy;

    /**
     * 更新人名称
     */
    private String updateName;

    /**
     * 是否删除
     */
    private Integer isDel;

    /**
     * 是否测试
     */
    private Integer isTest;

}

package com.jeasy.fileattach.dto;

import com.jeasy.doc.annotation.InitField;
import com.jeasy.validate.AnnotationValidable;
import lombok.Data;

import java.io.Serializable;

/**
 * 文件附件 列表 ResDTO
 *
 * @author taomk
 * @version 1.0
 * @since 2019/06/20 17:27
 */
@Data
public class FileAttachListResDTO implements AnnotationValidable, Serializable {

    private static final long serialVersionUID = 5409185459234711691L;

    /**
     * 主键
     */
    @InitField(value = "", desc = "主键")
    private Long id;

    /**
     * 表名称
     */
    @InitField(value = "", desc = "表名称")
    private String tableName;

    /**
     * 记录ID
     */
    @InitField(value = "", desc = "记录ID")
    private Long recordId;

    /**
     * 文件原名称
     */
    @InitField(value = "", desc = "文件原名称")
    private String name;

    /**
     * 文件URL
     */
    @InitField(value = "", desc = "文件URL")
    private String url;

    /**
     * 文件图标URL
     */
    @InitField(value = "", desc = "文件图标URL")
    private String iconUrl;

    /**
     * 文件预览URL
     */
    @InitField(value = "", desc = "文件预览URL")
    private String previewUrl;

    /**
     * 创建时间
     */
    @InitField(value = "", desc = "创建时间")
    private Long createAt;

    /**
     * 创建人ID
     */
    @InitField(value = "", desc = "创建人ID")
    private Long createBy;

    /**
     * 创建人名称
     */
    @InitField(value = "", desc = "创建人名称")
    private String createName;

    /**
     * 更新时间
     */
    @InitField(value = "", desc = "更新时间")
    private Long updateAt;

    /**
     * 更新人ID
     */
    @InitField(value = "", desc = "更新人ID")
    private Long updateBy;

    /**
     * 更新人名称
     */
    @InitField(value = "", desc = "更新人名称")
    private String updateName;

    /**
     * 是否删除
     */
    @InitField(value = "", desc = "是否删除")
    private Integer isDel;

    /**
     * 是否测试
     */
    @InitField(value = "", desc = "是否测试")
    private Integer isTest;

}

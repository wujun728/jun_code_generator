package cn.afterturn.gen.modular.code.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.io.Serializable;
import java.util.Date;

/**
 * 模板管理
 *
 * @author JueYue
 * @Date 2017-09-11 11:22
 */
@TableName("t_code_template")
public class TemplateModel extends CodeBaseModel<TemplateModel> {

    private static final long serialVersionUID = 1L;


    /**
     * Id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 模板名称
     */
    @TableField(value = "TEMPLATE_NAME")
    private String templateName;

    /**
     * 模板地址
     */
    @TableField(value = "TEMPLATE_path")
    private String templatePath;

    /**
     * UserId
     */
    @TableField(value = "USER_ID")
    private Integer userId;

    /**
     * TemplateDesc
     */
    @TableField(value = "TEMPLATE_DESC")
    private String templateDesc;

    /**
     * 文件名称
     */
    @TableField(value = "FILE_NAME")
    private String fileName;
    /**
     * 本地路径
     */
    @TableField(value = "local_path")
    private String localPath;
    /**
     *  组ID
     */
    @TableField(value = "GROUP_ID")
    private String groupId;
    /**
     *  原ID
     */
    @TableField(value = "ORIGINAL_ID")
    private Integer originalId;
    /**
     *  版本 1 正常版本 2 历史版本
     */
    @TableField(value = "VERSION")
    private Integer version;
    /**
     * 模板类型
     */
    @TableField(value = "TEMPLATE_TYPE")
    private String templateType;

    @TableField(exist = false)
    private TemplateFileModel fileModel;

    /**
     * 获取: Id
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置: Id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取: 模板名称
     */
    public String getTemplateName() {
        return templateName;
    }

    /**
     * 设置: 模板名称
     */
    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    /**
     * 获取: 模板地址
     */
    public String getTemplatePath() {
        return templatePath;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    /**
     * 设置: 模板地址
     */
    public void setTemplatePath(String templatePath) {
        this.templatePath = templatePath;
    }

    /**
     * 获取: UserId
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     * 设置: UserId
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    /**
     * 获取: TemplateDesc
     */
    public String getTemplateDesc() {
        return templateDesc;
    }

    /**
     * 设置: TemplateDesc
     */
    public void setTemplateDesc(String templateDesc) {
        this.templateDesc = templateDesc;
    }

    /**
     * 获取: 文件名称
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * 设置: 文件名称
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    /**
     * 获取: 模板类型
     */
    public String getTemplateType() {
        return templateType;
    }

    /**
     * 设置: 模板类型
     */
    public void setTemplateType(String templateType) {
        this.templateType = templateType;
    }

    public TemplateFileModel getFileModel() {
        return fileModel;
    }

    public String getLocalPath() {
        return localPath;
    }

    public void setLocalPath(String localPath) {
        this.localPath = localPath;
    }

    public void setFileModel(TemplateFileModel fileModel) {
        this.fileModel = fileModel;
    }

    public Integer getOriginalId() {
        return originalId;
    }

    public void setOriginalId(Integer originalId) {
        this.originalId = originalId;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}

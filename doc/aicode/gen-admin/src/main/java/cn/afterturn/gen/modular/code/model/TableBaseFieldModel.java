package cn.afterturn.gen.modular.code.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;


import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.io.Serializable;
import java.util.Date;

/**
 * 基础字段
 *
 * @author 
 * @Date 
 */
@TableName("t_code_table_base_field")
public class TableBaseFieldModel extends CodeBaseModel<TableBaseFieldModel> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     * 
     */
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;
    /**
     * 用户
     * 
     */
    @TableField(value="user_id")
    private Integer userId;
    /**
     * 别名
     * 
     */
    @TableField(value="alias")
    private String alias;
    /**
     * 字段ID
     * 
     */
    @TableField(value="field_id")
    private Integer fieldId;
    /**
     * 字段名,和字段表保持一致,冗余做校验用
     *
     */
    @TableField(value="field_name_check")
    private String fieldNameCheck;

    @TableField(exist = false)
    private TableFieldModel fieldModel;
    @TableField(exist = false)
    private TableFieldVerifyModel verifyModel;
    /**
     * 获取: 主键
     * 
     */
    public Integer getId() {
    return id;
    }
    /**
     * 设置: 主键
     * 
     */
    public void setId(Integer id) {
    this.id = id;
    }
    /**
     * 获取: 用户
     * 
     */
    public Integer getUserId() {
    return userId;
    }
    /**
     * 设置: 用户
     * 
     */
    public void setUserId(Integer userId) {
    this.userId = userId;
    }
    /**
     * 获取: 别名
     * 
     */
    public String getAlias() {
    return alias;
    }
    /**
     * 设置: 别名
     * 
     */
    public void setAlias(String alias) {
    this.alias = alias;
    }
    /**
     * 获取: 字段ID
     * 
     */
    public Integer getFieldId() {
    return fieldId;
    }
    /**
     * 设置: 字段ID
     * 
     */
    public void setFieldId(Integer fieldId) {
    this.fieldId = fieldId;
    }

    public String getFieldNameCheck() {
        return fieldNameCheck;
    }

    public void setFieldNameCheck(String fieldNameCheck) {
        this.fieldNameCheck = fieldNameCheck;
    }

    public TableFieldModel getFieldModel() {
        return fieldModel;
    }

    public void setFieldModel(TableFieldModel fieldModel) {
        this.fieldModel = fieldModel;
    }

    public TableFieldVerifyModel getVerifyModel() {
        return verifyModel;
    }

    public void setVerifyModel(TableFieldVerifyModel verifyModel) {
        this.verifyModel = verifyModel;
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

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
 * @author JueYue
 * @Date 2017-09-12 13:42
 */
@TableName("t_code_template_group")
public class TemplateGroupModel extends CodeBaseModel<TemplateGroupModel> {

    private static final long serialVersionUID = 1L;


    /**
     * Id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 用户ID
     */
    @TableField(value = "user_id")
    private Integer userId;
    /**
     * 分享状态
     */
    @TableField(value = "share_status")
    private Integer shareStatus;

    /**
     * 组名称
     */
    @TableField(value = "name")
    private String name;

    /**
     * 描述
     */
    @TableField(value = "desc")
    private String desc;

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
     * 获取: 用户ID
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     * 设置: 用户ID
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    /**
     * 获取: 组名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置: 组名称
     */
    public void setName(String name) {
        this.name = name;
    }

    public Integer getShareStatus() {
        return shareStatus;
    }

    public void setShareStatus(Integer shareStatus) {
        this.shareStatus = shareStatus;
    }

    /**
     * 获取: 描述
     */
    public String getDesc() {
        return desc;
    }

    /**
     * 设置: 描述
     */
    public void setDesc(String desc) {
        this.desc = desc;
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

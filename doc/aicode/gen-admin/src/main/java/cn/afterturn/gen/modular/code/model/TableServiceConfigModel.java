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
 * @author JueYue
 * @Date 2017-09-20 09:21
 */
@TableName("t_code_table_service_config")
public class TableServiceConfigModel extends Model<TableServiceConfigModel> {

    private static final long serialVersionUID = 1L;

    public TableServiceConfigModel() {

    }

    public TableServiceConfigModel(String type, Integer isEnable, Integer isPermission,
                                   Integer isTransactional, String transactionalType) {
        this.type = type;
        this.isEnable = isEnable;
        this.isPermission = isPermission;
        this.isTransactional = isTransactional;
        this.transactionalType = transactionalType;
    }

    /**
     * Id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 对应表
     */
    @TableField(value = "table_id")
    private Integer tableId;

    /**
     * 功能
     */
    @TableField(value = "type")
    private String type;

    /**
     * 是否启用该功能
     */
    @TableField(value = "is_enable")
    private Integer isEnable;

    /**
     * 是否需要授权
     */
    @TableField(value = "is_permission")
    private Integer isPermission;

    /**
     * 是否开启事务
     */
    @TableField(value = "is_transactional")
    private Integer isTransactional;

    /**
     * 事务类型
     */
    @TableField(value = "transactional_type")
    private String transactionalType;


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
     * 获取: 对应表
     */
    public Integer getTableId() {
        return tableId;
    }

    /**
     * 设置: 对应表
     */
    public void setTableId(Integer tableId) {
        this.tableId = tableId;
    }

    /**
     * 获取: 功能
     */
    public String getType() {
        return type;
    }

    /**
     * 设置: 功能
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * 获取: 是否启用改功能
     */
    public Integer getIsEnable() {
        return isEnable;
    }

    /**
     * 设置: 是否启用改功能
     */
    public void setIsEnable(Integer isEnable) {
        this.isEnable = isEnable;
    }

    /**
     * 获取: 是否需要授权
     */
    public Integer getIsPermission() {
        return isPermission;
    }

    /**
     * 设置: 是否需要授权
     */
    public void setIsPermission(Integer isPermission) {
        this.isPermission = isPermission;
    }

    /**
     * 获取: 是否开启事务
     */
    public Integer getIsTransactional() {
        return isTransactional;
    }

    /**
     * 设置: 是否开启事务
     */
    public void setIsTransactional(Integer isTransactional) {
        this.isTransactional = isTransactional;
    }

    /**
     * 获取: 事务类型
     */
    public String getTransactionalType() {
        return transactionalType;
    }

    /**
     * 设置: 事务类型
     */
    public void setTransactionalType(String transactionalType) {
        this.transactionalType = transactionalType;
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

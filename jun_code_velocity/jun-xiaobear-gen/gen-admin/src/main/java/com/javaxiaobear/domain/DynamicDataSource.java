package com.javaxiaobear.domain;

import com.javaxiaobear.common.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 动态数据源配置对象 dynamic_data_source
 * 
 * @author javaxiaobear
 * @date 2025_01_08
 */
public class DynamicDataSource extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键ID */
    private Long id;

    /** 数据源名称（唯一标识） */
    private String name;

    /** 数据库连接地址 */
    private String url;

    /** 数据库端口号 */
    private Long port;

    /** 数据库名称 */
    private String dbName;

    /** 数据库用户名 */
    private String username;

    /** 数据库密码 */
    private String password;

    /** 数据库驱动类名 */
    private String driverClassName;

    /** 数据库类型（如：MYSQL、POSTGRESQL、ORACLE） */
    private String type;

    /** 数据源状态（1：启用，0：禁用） */
    private Integer status;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setName(String name) 
    {
        this.name = name;
    }

    public String getName() 
    {
        return name;
    }
    public void setUrl(String url) 
    {
        this.url = url;
    }

    public String getUrl() 
    {
        return url;
    }
    public void setPort(Long port) 
    {
        this.port = port;
    }

    public Long getPort() 
    {
        return port;
    }
    public void setDbName(String dbName) 
    {
        this.dbName = dbName;
    }

    public String getDbName() 
    {
        return dbName;
    }
    public void setUsername(String username) 
    {
        this.username = username;
    }

    public String getUsername() 
    {
        return username;
    }
    public void setPassword(String password) 
    {
        this.password = password;
    }

    public String getPassword() 
    {
        return password;
    }
    public void setDriverClassName(String driverClassName) 
    {
        this.driverClassName = driverClassName;
    }

    public String getDriverClassName() 
    {
        return driverClassName;
    }
    public void setType(String type) 
    {
        this.type = type;
    }

    public String getType() 
    {
        return type;
    }
    public void setStatus(Integer status) 
    {
        this.status = status;
    }

    public Integer getStatus() 
    {
        return status;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("name", getName())
            .append("url", getUrl())
            .append("port", getPort())
            .append("dbName", getDbName())
            .append("username", getUsername())
            .append("password", getPassword())
            .append("driverClassName", getDriverClassName())
            .append("type", getType())
            .append("status", getStatus())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}

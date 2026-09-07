package com.template.domain;

/**
 * 动态数据源配置 gen_datasource
 *
 * @author Wujun
 */
public class DynamicDataSource extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    private Long id;
    private String name;
    private String url;
    private String port;
    private String dbName;
    private String username;
    private String password;
    private String type;
    private Integer status;
    private String driverClassName;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getUrl() { return url; }
    public void setUrl(String url) { this.url = url; }
    public String getPort() { return port; }
    public void setPort(String port) { this.port = port; }
    public String getDbName() { return dbName; }
    public void setDbName(String dbName) { this.dbName = dbName; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public Integer getStatus() { return status; }
    public void setStatus(Integer status) { this.status = status; }
    public String getDriverClassName() { return driverClassName; }
    public void setDriverClassName(String driverClassName) { this.driverClassName = driverClassName; }
}
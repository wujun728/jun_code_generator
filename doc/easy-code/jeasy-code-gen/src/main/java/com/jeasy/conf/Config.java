package com.jeasy.conf;

import com.google.common.collect.Sets;
import com.jeasy.common.date.DateKit;
import com.jeasy.common.str.StrKit;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

import java.text.ParseException;
import java.util.Set;

/**
 * @author taomk
 * @version 1.0
 * @since 2014/10/18 13:52
 */
@Data
@Slf4j
public class Config {

    private static final String CONF_PATH = "jeasy-code-gen.properties";

    private String driverClass;

    private String jdbcUrl;

    private String userName;

    private String userPwd;

    private String dbName;

    private String tables;

    private String basePackage;

    private String author;

    private String version;

    private String webModuleName;

    private String webAppName;

    private String apiModuleName;

    private String apiAppName;

    private String serverModuleName;

    private String clientModuleName;

    private String bizModuleName;

    private String serviceModuleName;

    private String daoModuleName;

    private String shiroModuleName;

    private String adminFrontName;

    private String parentArtifactId;

    private String parentGroupId;

    private String coreArtifactId;

    private Set<String> tableSet = Sets.newHashSet();

    public Config() {
        initConf(CONF_PATH);
    }

    private void initConf(String confPath) {
        try {
            Configuration conf = new PropertiesConfiguration(confPath);
            this.driverClass = conf.getString("driver.class") == null ? StrKit.S_EMPTY : conf.getString("driver.class").trim();
            this.userName = conf.getString("userName") == null ? StrKit.S_EMPTY : conf.getString("userName").trim();
            this.userPwd = conf.getString("userPwd") == null ? StrKit.S_EMPTY : conf.getString("userPwd").trim();
            this.dbName = conf.getString("dbName") == null ? StrKit.S_EMPTY : conf.getString("dbName").trim();
            this.jdbcUrl = conf.getString("jdbc.url") == null ? StrKit.S_EMPTY : conf.getString("jdbc.url").trim().replaceAll("\\{dbName}", this.dbName);
            this.tables = conf.getString("tables") == null ? StrKit.S_EMPTY : conf.getString("tables").trim();

            if (!tables.contains(StrKit.S_ASTERISK)) {
                for (String table : tables.split(StrKit.S_SEMICOLON)) {
                    tableSet.add(table.trim());
                }
            }

            this.basePackage = conf.getString("base.package") == null ? StrKit.S_EMPTY : conf.getString("base.package").trim();
            this.author = conf.getString("author") == null ? StrKit.S_EMPTY : conf.getString("author").trim();
            this.version = conf.getString("version") == null ? StrKit.S_EMPTY : conf.getString("version").trim();

            this.webModuleName = conf.getString("webModuleName") == null ? StrKit.S_EMPTY : conf.getString("webModuleName").trim();
            this.webAppName = conf.getString("webAppName") == null ? StrKit.S_EMPTY : conf.getString("webAppName").trim();
            this.apiModuleName = conf.getString("apiModuleName") == null ? StrKit.S_EMPTY : conf.getString("apiModuleName").trim();
            this.apiAppName = conf.getString("apiAppName") == null ? StrKit.S_EMPTY : conf.getString("apiAppName").trim();
            this.serverModuleName = conf.getString("serverModuleName") == null ? StrKit.S_EMPTY : conf.getString("serverModuleName").trim();
            this.clientModuleName = conf.getString("clientModuleName") == null ? StrKit.S_EMPTY : conf.getString("clientModuleName").trim();
            this.bizModuleName = conf.getString("bizModuleName") == null ? StrKit.S_EMPTY : conf.getString("bizModuleName").trim();
            this.serviceModuleName = conf.getString("serviceModuleName") == null ? StrKit.S_EMPTY : conf.getString("serviceModuleName").trim();
            this.daoModuleName = conf.getString("daoModuleName") == null ? StrKit.S_EMPTY : conf.getString("daoModuleName").trim();
            this.shiroModuleName = conf.getString("shiroModuleName") == null ? StrKit.S_EMPTY : conf.getString("shiroModuleName").trim();
            this.adminFrontName = conf.getString("adminFrontName") == null ? StrKit.S_EMPTY : conf.getString("adminFrontName").trim();
            this.parentArtifactId = conf.getString("parentArtifactId") == null ? StrKit.S_EMPTY : conf.getString("parentArtifactId").trim();
            this.parentGroupId = conf.getString("parentGroupId") == null ? StrKit.S_EMPTY : conf.getString("parentGroupId").trim();
            this.coreArtifactId = conf.getString("coreArtifactId") == null ? StrKit.S_EMPTY : conf.getString("coreArtifactId").trim();
        } catch (ConfigurationException e) {
            log.error("error exception : ", e);
        }
    }

    public String getCreateDate() {
        try {
            return DateKit.currDate("yyyy/MM/dd HH:mm");
        } catch (ParseException e) {
            log.error("error exception : ", e);
        }
        return StrKit.S_EMPTY;
    }
}

package net.trueland.scrm.paas.runtime.extender.standard.crm.generator.conf;

import cn.hutool.extra.spring.SpringUtil;
import lombok.extern.slf4j.Slf4j;
import net.trueland.scrm.common.model.exception.BuException;
import net.trueland.scrm.paas.runtime.extender.standard.crm.generator.CodeGenerator;
import org.apache.commons.lang3.StringUtils;

import java.io.InputStream;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;

/**
 * @author bright
 * @date 2024/2/1 16:59
 */
@Slf4j
public class GeneratorProperties {

    {
        try (InputStream inputStream = CodeGenerator.class.getResourceAsStream("/generator.properties")) {
            Properties p = new Properties();
            p.load(inputStream);
            USERNAME = p.getProperty("mysql.username");
            PASSWORD = p.getProperty("mysql.password");
            URL = p.getProperty("mysql.url");
            TABLES = p.getProperty("mysql.tables");
            DBNAME = p.getProperty("mysql.dbname");

            // 包路径
            String pEntity = p.getProperty("path.entity");
            if (StringUtils.isNotBlank(pEntity)) PATH_ENTITY = pEntity;
            String pXml = p.getProperty("path.xml");
            if (StringUtils.isNotBlank(pXml)) PATH_XML = pXml;
            String pMapper = p.getProperty("path.mapper");
            if (StringUtils.isNotBlank(pMapper)) PATH_MAPPER = pMapper;
            String pService = p.getProperty("path.service");
            if (StringUtils.isNotBlank(pService)) PATH_SERVICE = pService;
            String pServiceImpl = p.getProperty("path.serviceImpl");
            if (StringUtils.isNotBlank(pServiceImpl)) PATH_SERVICE_IMPL = pServiceImpl;
            String pController = p.getProperty("path.controller");
            if (StringUtils.isNotBlank(pController)) PATH_CONTROLLER = pController;
            String pPackage = p.getProperty("package.path");
            if (StringUtils.isNotBlank(pPackage)) PACKAGE_PATH = pPackage;
        } catch (Throwable e) {
            log.error("获取【generator.properties】配置失败", e);
            throw new BuException(e);
        }
        ACCOUNT_ID = getAccountId();
    }


    public static Long getAccountId() {
        try {
            // 直接从Spring中获取
            final String accountId = SpringUtil.getProperty("spring.cloud.nacos.discovery.metadata.accountId");
            if (Objects.isNull(accountId)) {
                throw new BuException("请先配置spring.cloud.nacos.discovery.metadata.accountId 属性 !");
            }
            return Long.valueOf(accountId);
        } catch (Exception e) {
            // 独立运行时返回默认值
            log.warn("Spring容器未初始化，返回默认accountId", e);
            return 1L;
        }
    }

    /**
     * 解析yaml文件
     */
    public static void parseYml(Map<String, Object> map, String keyPrefix, Map<String, Object> configMap) {
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            String fullKey = (keyPrefix.isEmpty() ? key : keyPrefix + "." + key);
            if (value instanceof Map) {
                parseYml((Map<String, Object>) value, fullKey, configMap);
            } else {
                configMap.put(fullKey, value);
            }
        }
    }

    public static String URL;
    /**
     * db 账号
     */
    public static String USERNAME;
    /**
     * db 密码
     */
    public static String PASSWORD;
    /*
    数据库
     */
    public static String DBNAME;
    /**
     * 生成的表名(多个表用英文逗号分隔，所有表输入 )
     */
    public static String TABLES;

    /**
     * 租户id
     */
    public static Long ACCOUNT_ID;


    /**
     * 项目路径
     */
    public static final String PARENT_DIR = System.getProperty("user.dir");
    /**
     * 基本路径
     */
    public static final String RUNTIME_SERVER = "/runtime-server/src/main/";
    /**
     * 包路径
     */
    public static final String RUNTIME_SERVER_PATH = RUNTIME_SERVER + "java/net/trueland/scrm/paas/runtime/extender/tenant/";
    /**
     * entity路径
     */
    public static String PATH_ENTITY = "/runtime-model/src/main/java/net/trueland/scrm/paas/runtime/extender/tenant/model/entity";
    /**
     * xml路径
     */
    public static String PATH_XML = RUNTIME_SERVER + "resources/mapper";
    /**
     * mapper路径
     */
    public static String PATH_MAPPER = RUNTIME_SERVER_PATH + "mapper";
    /**
     * service路径
     */
    public static String PATH_SERVICE = RUNTIME_SERVER_PATH + "service";
    /**
     * serviceImpl路径
     */
    public static String PATH_SERVICE_IMPL = RUNTIME_SERVER_PATH + "service/impl/";
    /**
     * controller路径
     */
    public static String PATH_CONTROLLER = RUNTIME_SERVER_PATH + "controller";
    /**
     * 包配置路径
     */
    public static String PACKAGE_PATH = "net.trueland.scrm.paas.runtime.extender.standard";
}

package com.javaxiaobear.generator;

import com.javaxiaobear.domain.GenTable;
import com.javaxiaobear.domain.GenTableColumn;
import com.javaxiaobear.util.StringUtils;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * API规范生成器
 * 确保前后端API接口完全一致
 * 
 * @author javaxiaobear
 * @version 2.0
 * @since 2024-01-01
 */
@Component
public class ApiSpecGenerator {
    
    /**
     * 生成API规范文档
     */
    public Map<String, Object> generateApiSpec(GenTable genTable) {
        Map<String, Object> apiSpec = new HashMap<>();
        
        String moduleName = genTable.getModuleName();
        String businessName = genTable.getBusinessName();
        String className = genTable.getClassName();
        
        // 基础信息
        apiSpec.put("moduleName", moduleName);
        apiSpec.put("businessName", businessName);
        apiSpec.put("className", className);
        apiSpec.put("tableName", genTable.getTableName());
        apiSpec.put("tableComment", genTable.getTableComment());
        
        // 生成API端点
        apiSpec.put("endpoints", generateEndpoints(genTable));
        
        // 生成数据模型
        apiSpec.put("models", generateModels(genTable));
        
        // 生成前端API调用代码
        apiSpec.put("frontendApi", generateFrontendApi(genTable));
        
        // 生成后端Controller代码
        apiSpec.put("backendController", generateBackendController(genTable));
        
        return apiSpec;
    }
    
    /**
     * 生成API端点定义
     */
    private List<Map<String, Object>> generateEndpoints(GenTable genTable) {
        List<Map<String, Object>> endpoints = new ArrayList<>();
        
        String businessName = genTable.getBusinessName();
        String baseUrl = "/" + genTable.getModuleName() + "/" + businessName;
        
        // 查询列表
        endpoints.add(createEndpoint("GET", baseUrl + "/list", "查询" + genTable.getTableComment() + "列表", 
                "PageRequest", "PageResult<" + genTable.getClassName() + "VO>"));
        
        // 查询详情
        endpoints.add(createEndpoint("GET", baseUrl + "/{id}", "查询" + genTable.getTableComment() + "详情", 
                "Long", genTable.getClassName() + "VO"));
        
        // 新增
        endpoints.add(createEndpoint("POST", baseUrl, "新增" + genTable.getTableComment(), 
                genTable.getClassName() + "CreateDTO", "Result<Long>"));
        
        // 修改
        endpoints.add(createEndpoint("PUT", baseUrl, "修改" + genTable.getTableComment(), 
                genTable.getClassName() + "UpdateDTO", "Result<Void>"));
        
        // 删除
        endpoints.add(createEndpoint("DELETE", baseUrl + "/{ids}", "删除" + genTable.getTableComment(), 
                "String", "Result<Void>"));
        
        // 导出
        endpoints.add(createEndpoint("POST", baseUrl + "/export", "导出" + genTable.getTableComment(), 
                genTable.getClassName() + "QueryDTO", "void"));
        
        return endpoints;
    }
    
    /**
     * 创建端点定义
     */
    private Map<String, Object> createEndpoint(String method, String url, String description, 
                                               String requestType, String responseType) {
        Map<String, Object> endpoint = new HashMap<>();
        endpoint.put("method", method);
        endpoint.put("url", url);
        endpoint.put("description", description);
        endpoint.put("requestType", requestType);
        endpoint.put("responseType", responseType);
        return endpoint;
    }
    
    /**
     * 生成数据模型
     */
    private Map<String, Object> generateModels(GenTable genTable) {
        Map<String, Object> models = new HashMap<>();
        
        // 实体类
        models.put("entity", generateEntityModel(genTable));
        
        // VO类
        models.put("vo", generateVOModel(genTable));
        
        // DTO类
        models.put("createDTO", generateCreateDTOModel(genTable));
        models.put("updateDTO", generateUpdateDTOModel(genTable));
        models.put("queryDTO", generateQueryDTOModel(genTable));
        
        return models;
    }
    
    /**
     * 生成实体模型
     */
    private Map<String, Object> generateEntityModel(GenTable genTable) {
        Map<String, Object> model = new HashMap<>();
        model.put("className", genTable.getClassName());
        model.put("tableName", genTable.getTableName());
        model.put("comment", genTable.getTableComment());
        
        List<Map<String, Object>> fields = new ArrayList<>();
        for (GenTableColumn column : genTable.getColumns()) {
            Map<String, Object> field = new HashMap<>();
            field.put("fieldName", column.getJavaField());
            field.put("fieldType", column.getJavaType());
            field.put("columnName", column.getColumnName());
            field.put("columnComment", column.getColumnComment());
            field.put("isPk", column.isPk());
            field.put("isRequired", column.isRequired());
            fields.add(field);
        }
        model.put("fields", fields);
        
        return model;
    }
    
    /**
     * 生成VO模型
     */
    private Map<String, Object> generateVOModel(GenTable genTable) {
        Map<String, Object> model = new HashMap<>();
        model.put("className", genTable.getClassName() + "VO");
        model.put("comment", genTable.getTableComment() + "视图对象");
        
        List<Map<String, Object>> fields = new ArrayList<>();
        for (GenTableColumn column : genTable.getColumns()) {
            if (column.isList()) {
                Map<String, Object> field = new HashMap<>();
                field.put("fieldName", column.getJavaField());
                field.put("fieldType", getVOFieldType(column));
                field.put("comment", column.getColumnComment());
                field.put("dictType", column.getDictType());
                fields.add(field);
            }
        }
        model.put("fields", fields);
        
        return model;
    }
    
    /**
     * 生成创建DTO模型
     */
    private Map<String, Object> generateCreateDTOModel(GenTable genTable) {
        Map<String, Object> model = new HashMap<>();
        model.put("className", genTable.getClassName() + "CreateDTO");
        model.put("comment", genTable.getTableComment() + "创建对象");
        
        List<Map<String, Object>> fields = new ArrayList<>();
        for (GenTableColumn column : genTable.getColumns()) {
            if (column.isInsert() && !column.isPk()) {
                Map<String, Object> field = new HashMap<>();
                field.put("fieldName", column.getJavaField());
                field.put("fieldType", column.getJavaType());
                field.put("comment", column.getColumnComment());
                field.put("isRequired", column.isRequired());
                field.put("validations", generateValidations(column));
                fields.add(field);
            }
        }
        model.put("fields", fields);
        
        return model;
    }
    
    /**
     * 生成更新DTO模型
     */
    private Map<String, Object> generateUpdateDTOModel(GenTable genTable) {
        Map<String, Object> model = new HashMap<>();
        model.put("className", genTable.getClassName() + "UpdateDTO");
        model.put("comment", genTable.getTableComment() + "更新对象");
        
        List<Map<String, Object>> fields = new ArrayList<>();
        for (GenTableColumn column : genTable.getColumns()) {
            if (column.isEdit()) {
                Map<String, Object> field = new HashMap<>();
                field.put("fieldName", column.getJavaField());
                field.put("fieldType", column.getJavaType());
                field.put("comment", column.getColumnComment());
                field.put("isRequired", column.isRequired() && !column.isPk());
                field.put("validations", generateValidations(column));
                fields.add(field);
            }
        }
        model.put("fields", fields);
        
        return model;
    }
    
    /**
     * 生成查询DTO模型
     */
    private Map<String, Object> generateQueryDTOModel(GenTable genTable) {
        Map<String, Object> model = new HashMap<>();
        model.put("className", genTable.getClassName() + "QueryDTO");
        model.put("comment", genTable.getTableComment() + "查询对象");
        
        List<Map<String, Object>> fields = new ArrayList<>();
        for (GenTableColumn column : genTable.getColumns()) {
            if (column.isQuery()) {
                Map<String, Object> field = new HashMap<>();
                field.put("fieldName", column.getJavaField());
                field.put("fieldType", getQueryFieldType(column));
                field.put("comment", column.getColumnComment());
                field.put("queryType", column.getQueryType());
                fields.add(field);
            }
        }
        model.put("fields", fields);
        
        return model;
    }
    
    /**
     * 生成前端API代码
     */
    private String generateFrontendApi(GenTable genTable) {
        StringBuilder api = new StringBuilder();
        String businessName = genTable.getBusinessName();
        String baseUrl = "/" + genTable.getModuleName() + "/" + businessName;
        
        api.append("import request from '@/utils/request'\n\n");
        api.append("// ").append(genTable.getTableComment()).append("API\n");
        api.append("export default {\n");
        
        // 查询列表
        api.append("  // 查询列表\n");
        api.append("  getList(params) {\n");
        api.append("    return request({\n");
        api.append("      url: '").append(baseUrl).append("/list',\n");
        api.append("      method: 'get',\n");
        api.append("      params\n");
        api.append("    })\n");
        api.append("  },\n\n");
        
        // 查询详情
        api.append("  // 查询详情\n");
        api.append("  getDetail(id) {\n");
        api.append("    return request({\n");
        api.append("      url: `").append(baseUrl).append("/${id}`,\n");
        api.append("      method: 'get'\n");
        api.append("    })\n");
        api.append("  },\n\n");
        
        // 新增
        api.append("  // 新增\n");
        api.append("  create(data) {\n");
        api.append("    return request({\n");
        api.append("      url: '").append(baseUrl).append("',\n");
        api.append("      method: 'post',\n");
        api.append("      data\n");
        api.append("    })\n");
        api.append("  },\n\n");
        
        // 修改
        api.append("  // 修改\n");
        api.append("  update(data) {\n");
        api.append("    return request({\n");
        api.append("      url: '").append(baseUrl).append("',\n");
        api.append("      method: 'put',\n");
        api.append("      data\n");
        api.append("    })\n");
        api.append("  },\n\n");
        
        // 删除
        api.append("  // 删除\n");
        api.append("  delete(ids) {\n");
        api.append("    return request({\n");
        api.append("      url: `").append(baseUrl).append("/${ids}`,\n");
        api.append("      method: 'delete'\n");
        api.append("    })\n");
        api.append("  }\n");
        
        api.append("}\n");
        
        return api.toString();
    }
    
    /**
     * 生成后端Controller代码框架
     */
    private String generateBackendController(GenTable genTable) {
        // 这里返回Controller的基本结构，具体实现在模板中
        return "Controller template structure for " + genTable.getClassName();
    }
    
    /**
     * 获取VO字段类型
     */
    private String getVOFieldType(GenTableColumn column) {
        if ("Date".equals(column.getJavaType())) {
            return "String"; // VO中日期转为字符串
        }
        return column.getJavaType();
    }
    
    /**
     * 获取查询字段类型
     */
    private String getQueryFieldType(GenTableColumn column) {
        if ("Date".equals(column.getJavaType())) {
            return "String[]"; // 查询支持日期范围
        }
        return column.getJavaType();
    }
    
    /**
     * 生成字段验证注解
     */
    private List<String> generateValidations(GenTableColumn column) {
        List<String> validations = new ArrayList<>();
        
        if (column.isRequired()) {
            validations.add("@NotNull(message = \"" + column.getColumnComment() + "不能为空\")");
        }
        
        if ("String".equals(column.getJavaType())) {
            // 简化版本，不检查字段长度
            validations.add("@NotBlank(message = \"" + column.getColumnComment() + "不能为空\")");
        }
        
        return validations;
    }
}

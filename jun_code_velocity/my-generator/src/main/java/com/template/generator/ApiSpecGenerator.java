package com.template.generator;

import com.template.domain.GenTable;
import com.template.domain.GenTableColumn;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * API规范生成器
 *
 * @author Wujun
 */
@Component
public class ApiSpecGenerator {

    public Map<String, Object> generateApiSpec(GenTable table) {
        Map<String, Object> spec = new LinkedHashMap<>();
        spec.put("tableName", table.getTableName());
        spec.put("className", table.getClassName());
        spec.put("businessName", table.getBusinessName());
        spec.put("functionName", table.getFunctionName());
        spec.put("moduleName", table.getModuleName());

        List<Map<String, String>> fields = new ArrayList<>();
        if (table.getColumns() != null) {
            for (GenTableColumn col : table.getColumns()) {
                Map<String, String> field = new LinkedHashMap<>();
                field.put("name", col.getJavaField());
                field.put("type", col.getJavaType());
                field.put("comment", col.getColumnComment());
                field.put("required", "1".equals(col.getIsRequired()) ? "true" : "false");
                fields.add(field);
            }
        }
        spec.put("fields", fields);
        return spec;
    }
}
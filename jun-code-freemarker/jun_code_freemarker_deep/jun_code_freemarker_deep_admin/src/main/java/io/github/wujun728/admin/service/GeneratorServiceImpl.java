package io.github.wujun728.admin.service;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import freemarker.template.TemplateException;
import io.github.wujun728.admin.core.util.MapUtil;
import io.github.wujun728.admin.util.FreemarkerUtil;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * GeneratorService implementation
 */
@Service
public class GeneratorServiceImpl implements GeneratorService {

    String templateConfig = null;

    @Override
    public String getTemplateConfig() throws IOException {
        if (templateConfig != null) {
            return templateConfig;
        }
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("template.json");
        templateConfig = new BufferedReader(new InputStreamReader(inputStream))
                .lines().collect(Collectors.joining(System.lineSeparator()));
        inputStream.close();
        return templateConfig;
    }

    @Override
    public Map<String, String> getResultByParams(Map<String, Object> params) throws IOException, TemplateException {
        Map<String, String> result = new HashMap<>(32);
        result.put("tableName", MapUtil.getString(params, "tableName"));
        JSONArray parentTemplates = JSONArray.parseArray(getTemplateConfig());
        for (int i = 0; i < parentTemplates.size(); i++) {
            JSONObject parentTemplateObj = parentTemplates.getJSONObject(i);
            for (int x = 0; x < parentTemplateObj.getJSONArray("templates").size(); x++) {
                JSONObject childTemplate = parentTemplateObj.getJSONArray("templates").getJSONObject(x);
                result.put(childTemplate.getString("name"),
                        FreemarkerUtil.processString(
                                parentTemplateObj.getString("group") + "/" + childTemplate.getString("name") + ".ftl",
                                params));
            }
        }
        return result;
    }

}
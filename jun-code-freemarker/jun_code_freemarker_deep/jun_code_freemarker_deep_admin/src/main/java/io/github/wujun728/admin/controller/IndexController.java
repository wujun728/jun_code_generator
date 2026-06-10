package io.github.wujun728.admin.controller;

import freemarker.template.TemplateException;
import io.github.wujun728.admin.core.CodeGeneratorTool;
import io.github.wujun728.admin.core.model.ClassInfo;
import io.github.wujun728.admin.core.model.ParamInfo;
import io.github.wujun728.admin.core.util.MapUtil;
import io.github.wujun728.admin.core.util.StringUtils;
import io.github.wujun728.admin.core.util.TableParseUtil;
import io.github.wujun728.admin.model.ReturnT;
import io.github.wujun728.admin.service.GeneratorService;
import io.github.wujun728.admin.util.FreemarkerTool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Controller
public class IndexController {
    private static final Logger logger = LoggerFactory.getLogger(IndexController.class);

    @Resource
    private FreemarkerTool freemarkerTool;

    @Resource
    private GeneratorService generatorService;

    @RequestMapping("/")
    public String index() {
        return "redirect:/static/index.html";
    }

    @RequestMapping("/codeGenerate")
    @ResponseBody
    public ReturnT<Map<String, String>> codeGenerate(String tableSql) {
        try {
            if (tableSql == null || tableSql.trim().length() == 0) {
                return new ReturnT<>(ReturnT.FAIL_CODE, "表结构信息不可为空");
            }

            ClassInfo classInfo = CodeGeneratorTool.processTableIntoClassInfo(tableSql);

            Map<String, Object> params = new HashMap<>();
            params.put("classInfo", classInfo);

            Map<String, String> result = new HashMap<>();
            result.put("controller_code", freemarkerTool.processString("code-generator/controller.ftl", params));
            result.put("service_code", freemarkerTool.processString("code-generator/service.ftl", params));
            result.put("service_impl_code", freemarkerTool.processString("code-generator/service_impl.ftl", params));
            result.put("dao_code", freemarkerTool.processString("code-generator/dao.ftl", params));
            result.put("mybatis_code", freemarkerTool.processString("code-generator/mybatis.ftl", params));
            result.put("model_code", freemarkerTool.processString("code-generator/model.ftl", params));

            int lineNum = 0;
            for (Map.Entry<String, String> item : result.entrySet()) {
                if (item.getValue() != null) {
                    lineNum += StringUtils.countMatches(item.getValue(), "\n");
                }
            }
            logger.info("生成代码行数：{}", lineNum);

            return new ReturnT<>(result);
        } catch (IOException | TemplateException e) {
            logger.error(e.getMessage(), e);
            return new ReturnT<>(ReturnT.FAIL_CODE, "表结构解析失败");
        }
    }

    @PostMapping("/code/generate")
    @ResponseBody
    public ReturnT<Map<String, String>> generateCode(@RequestBody ParamInfo paramInfo) {
        try {
            if (paramInfo.getTableSql() == null || paramInfo.getTableSql().trim().length() == 0) {
                return new ReturnT<>(ReturnT.FAIL_CODE, "表结构信息不可为空");
            }

            ClassInfo classInfo = TableParseUtil.processTableIntoClassInfo(paramInfo);

            Map<String, Object> params = new HashMap<>();
            params.put("classInfo", classInfo);
            if (paramInfo.getOptions() != null) {
                params.putAll(paramInfo.getOptions());
            }
            params.put("ClassName", classInfo.getClassName());

            Map<String, String> result = generatorService.getResultByParams(params);
            result.put("tableName", classInfo.getTableName());

            int lineNum = 0;
            for (Map.Entry<String, String> item : result.entrySet()) {
                if (item.getValue() != null) {
                    lineNum += StringUtils.countMatches(item.getValue(), "\n");
                }
            }
            logger.info("生成代码行数：{}", lineNum);

            return new ReturnT<>(result);
        } catch (IOException | TemplateException e) {
            logger.error(e.getMessage(), e);
            return new ReturnT<>(ReturnT.FAIL_CODE, "表结构解析失败:" + e.getMessage());
        }
    }

    @RequestMapping("/template/all")
    @ResponseBody
    public ReturnT<String> getAllTemplates() {
        try {
            String templateConfig = generatorService.getTemplateConfig();
            return new ReturnT<>(templateConfig);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            return new ReturnT<>(ReturnT.FAIL_CODE, "获取模板配置失败");
        }
    }

}
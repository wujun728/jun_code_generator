package io.github.wujun728.generator.controller;

import freemarker.template.TemplateException;
import io.github.wujun728.common.base.Result;
import io.github.wujun728.generator.entity.ClassInfo;
import io.github.wujun728.generator.entity.ParamInfo;
import io.github.wujun728.generator.service.GeneratorService;
import io.github.wujun728.generator.util.FreemarkerUtil;
import io.github.wujun728.generator.util.StringUtils;
import io.github.wujun728.generator.util.TableParseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Controller
public class CodeGeneratorController {

    @Resource
    private GeneratorService generatorService;

//    @Resource
//    private FreemarkerUtil freemarkerUtil;

    @RequestMapping("/")
    public String index() {
        return "generate/sqlPaste";
    }

    @RequestMapping("/generate")
    public String generate() {
        return "generate/sqlPaste";
    }

    @RequestMapping("/codeGenerate/sqlPaste")
    public String sqlPaste() {
        return "generate/sqlPaste";
    }

    @RequestMapping("/codeGenerate")
    @ResponseBody
    public Result codeGenerate(String tableSql) {
        try {
            if (tableSql == null || tableSql.trim().length() == 0) {
                return new Result(500, "表结构信息不可为空");
            }
            ParamInfo paramInfo = new ParamInfo();
            paramInfo.setTableSql(tableSql);
            Map<String, Object> options = new HashMap<>();
            options.put("nameCaseType", "CamelCase");
            options.put("isPackageType", true);
            options.put("isLombok", true);
            options.put("isComment", true);
            paramInfo.setOptions(options);

            ClassInfo classInfo = TableParseUtil.processTableIntoClassInfo(paramInfo);

            Map<String, Object> params = new HashMap<>();
            params.put("classInfo", classInfo);
            params.put("packageController", "io.github.wujun728.biz.controller");
            params.put("packageService", "io.github.wujun728.biz.service");
            params.put("packageServiceImpl", "io.github.wujun728.biz.service.impl");
            params.put("packageDao", "io.github.wujun728.biz.dao");
            params.put("packageMybatisXML", "io.github.wujun728.biz.model");
            params.put("packageModel", "io.github.wujun728.biz.model");

            Map<String, String> result = new HashMap<>();
            result.put("controller_code", FreemarkerUtil.processString("code-generator/controller.ftl", params));
            result.put("service_code", FreemarkerUtil.processString("code-generator/service.ftl", params));
            result.put("service_impl_code", FreemarkerUtil.processString("code-generator/service_impl.ftl", params));
            result.put("dao_code", FreemarkerUtil.processString("code-generator/dao.ftl", params));
            result.put("mybatis_code", FreemarkerUtil.processString("code-generator/mybatis.ftl", params));
            result.put("model_code", FreemarkerUtil.processString("code-generator/model.ftl", params));

            int lineNum = 0;
            for (Map.Entry<String, String> item : result.entrySet()) {
                if (item.getValue() != null) {
                    lineNum += StringUtils.countMatches(item.getValue(), "\n");
                }
            }
            log.info("生成代码行数：{}", lineNum);

            return Result.ok(result);
        } catch (IOException | TemplateException e) {
            log.error(e.getMessage(), e);
            return new Result(500, "表结构解析失败");
        }
    }

    @PostMapping("/code/generate")
    @ResponseBody
    public Result generateCode(@RequestBody ParamInfo paramInfo) {
        try {
            if (paramInfo.getTableSql() == null || paramInfo.getTableSql().trim().length() == 0) {
                return new Result(500, "表结构信息不可为空");
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
            log.info("生成代码行数：{}", lineNum);

            return Result.ok(result);
        } catch (IOException | TemplateException e) {
            log.error(e.getMessage(), e);
            return new Result(500, "表结构解析失败:" + e.getMessage());
        }
    }

    @RequestMapping("/template/all")
    @ResponseBody
    public Result getAllTemplates() {
        try {
            String templateConfig = generatorService.getTemplateConfig();
            return Result.ok(templateConfig);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            return new Result(500, "获取模板配置失败");
        }
    }

}
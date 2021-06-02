/**
 * Copyright 2013-2017 JueYue (qrb.jueyue@gmail.com)
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
package cn.afterturn.gen.modular.code.controller;

import cn.afterturn.gen.config.properties.GunsProperties;
import cn.afterturn.gen.core.CodeGenModel;
import cn.afterturn.gen.core.CodeGenUtil;
import cn.afterturn.gen.core.GenCoreConstant;
import cn.afterturn.gen.core.db.read.IReadTable;
import cn.afterturn.gen.core.db.read.ReadTableFactory;
import cn.afterturn.gen.core.model.*;
import cn.afterturn.gen.core.parse.ParseFactory;
import cn.afterturn.gen.core.shiro.ShiroKit;
import cn.afterturn.gen.core.util.ConnectionUtil;
import cn.afterturn.gen.core.util.DateUtil;
import cn.afterturn.gen.core.util.NameUtil;
import cn.afterturn.gen.modular.code.model.*;
import cn.afterturn.gen.modular.code.service.*;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * @author JueYue 2017年4月22日
 */
@Controller
@RequestMapping("code")
public class GenController {

    private static final Logger LOGGER = LoggerFactory.getLogger(GenController.class);

    private String PREFIX = "/code/gen/";

    @Autowired
    private IGenService           genService;
    @Autowired
    private ITemplateService      templateService;
    @Autowired
    private IDbInfoService        dbInfoService;
    @Autowired
    private ITemplateGroupService templateGroupService;
    @Autowired
    private IGenParamService      genParamService;
    @Autowired
    private GunsProperties        gunsProperties;
    @Autowired
    private ITableInfoService     tableInfoService;

    /**
     * 跳转到首页
     */
    @RequestMapping("")
    public String index(Model modelMap) {
        TemplateGroupModel model = new TemplateGroupModel();
        model.setUserId(ShiroKit.getUser().getId());
        modelMap.addAttribute("groups", templateGroupService.selectList(model));
        GenParamModel params = new GenParamModel();
        params.setUserId(ShiroKit.getUser().getId());
        modelMap.addAttribute("params", genParamService.selectList(params));
        return PREFIX + "index.html";
    }

    /**
     * 跳转到首页
     */
    @RequestMapping("/tableGen/{id}")
    public String tableGen(Model modelMap, @PathVariable Integer id) {
        TemplateGroupModel model = new TemplateGroupModel();
        model.setUserId(ShiroKit.getUser().getId());
        modelMap.addAttribute("groups", templateGroupService.selectList(model));
        GenParamModel params = new GenParamModel();
        params.setUserId(ShiroKit.getUser().getId());
        modelMap.addAttribute("params", genParamService.selectList(params));
        modelMap.addAttribute("table", tableInfoService.selectOne(new TableInfoModel(id)));
        return PREFIX + "tableinfo_gen.html";
    }

    @RequestMapping(value = "queryDatabses")
    @ResponseBody
    public ResponseModel queryDatabses(DbInfoModel entity, RequestModel form) {
        try {
            entity = dbInfoService.selectOne(entity);
            ConnectionUtil.init(entity.getDbDriver(), entity.getDbUrl(), entity.getDbUserName(),
                    entity.getDbPassword());
            IReadTable      readTable = ReadTableFactory.getReadTable(entity.getDbType());
            List<String>    list      = readTable.getAllDB();
            List<BaseModel> dblist    = new ArrayList<BaseModel>();
            BaseModel       info;
            for (String db : list) {
                info = new BaseModel(db);
                dblist.add(info);
            }
            return ResponseModel.ins(dblist);
        } finally {
            ConnectionUtil.close();
        }
    }

    @RequestMapping(value = "queryTables")
    @ResponseBody
    public ResponseModel queryTables(DbInfoModel entity, String dbName, RequestModel form) {
        try {
            entity = dbInfoService.selectOne(entity);
            ConnectionUtil.init(entity.getDbDriver(), entity.getDbUrl(), entity.getDbUserName(),
                    entity.getDbPassword());
            IReadTable readTable = ReadTableFactory.getReadTable(entity.getDbType());
            return ResponseModel.ins(readTable.getAllTable(dbName));
        } finally {
            ConnectionUtil.close();
        }
    }

    @RequestMapping(value = "genCode")
    public void genCode(DbInfoModel entity, String dbName, String tableName, String localPath, String encoded, GenerationEntity ge,
                        HttpServletRequest req, HttpServletResponse res) {
        entity = dbInfoService.selectOne(entity);
        String[]     templates = req.getParameterValues("templates[]");
        CodeGenModel model     = new CodeGenModel();
        model.setDbType(GenCoreConstant.MYSQL);
        model.setTableName(tableName);
        model.setDbName(dbName);
        model.setUrl(entity.getDbUrl());
        model.setPasswd(entity.getDbPassword());
        model.setUsername(entity.getDbUserName());
        if (StringUtils.isEmpty(ge.getEntityName())) {
            ge.setEntityName(NameUtil.getEntityHumpName(tableName));
        }
        model.setGenerationEntity(ge);
        List<TemplateModel> templateList     = templateService.getTemplateByIds(templates);
        List<String>        templateFileList = genService.loadTemplateFile(templateList);
        List<String>        fileList         = new ArrayList<String>();
        for (int i = 0; i < templateList.size(); i++) {
            model.setParseType(templateList.get(i).getTemplateType());
            model.setFile(templateFileList.get(i));
            fileList.addAll(CodeGenUtil.codeGen(model));
        }
        if (StringUtils.isNotEmpty(localPath) && gunsProperties.getGenLocal()) {
            writeThisFileList(localPath, encoded, fileList, templateList, ge);
        } else {
            downThisFileList(res, fileList, templateList, ge);
        }
    }


    @RequestMapping(value = "genTableCode")
    public void genTableCode(Integer tableId, String localPath, String encoded, GenerationEntity ge,
                             HttpServletRequest req, HttpServletResponse res) {
        String[]            templates        = req.getParameterValues("templates[]");
        List<TemplateModel> templateList     = templateService.getTemplateByIds(templates);
        final List<String>  templateFileList = genService.loadTemplateFile(templateList);
        List<String>        fileList         = new ArrayList<String>();
        GenBeanEntity       tableEntity      = tableInfoService.getGenBean(tableId);
        ge.setTableName(tableEntity.getTableName());
        ge.setDate(DateUtil.getTime());
        for (int i = 0; i < templateList.size(); i++) {
            final int index = i;
            fileList.addAll(ParseFactory.getParse(templateList.get(i).getTemplateType()).parse(ge, tableEntity,
                    new ArrayList<String>() {
                        {
                            add(templateFileList.get(index));
                        }
                    }));
        }
        if (StringUtils.isNotEmpty(localPath) && gunsProperties.getGenLocal()) {
            writeThisFileList(localPath, encoded, fileList, templateList, ge);
        } else {
            downThisFileList(res, fileList, templateList, ge);
        }
    }

    /**
     * 本地输出代码
     */
    private void writeThisFileList(String localPath, String encoded, List<String> fileList, List<TemplateModel> templateList, GenerationEntity ge) {
        for (int i = 0; i < fileList.size(); i++) {
            // 文件路径包括 本地项目路径 + 项目相对路径 + 包路径 + 类的自我路径
            String filePath = localPath + File.separator +
                    (StringUtils.isNotEmpty(templateList.get(i).getLocalPath()) ?
                            templateList.get(i).getLocalPath().replaceAll("\\.", "\\/") + File.separator : "") +
                    getPackagePath(templateList.get(i), ge);
            File path = new File(filePath + File.separator);
            if (!path.exists()) {
                path.mkdirs();
            }

            try {
                File file = new File(filePath + File.separator + getFileName(templateList.get(i), ge));
                FileUtils.writeStringToFile(file, fileList.get(i), encoded);
            } catch (IOException e) {
                LOGGER.error(e.getMessage(), e);
            }
        }
    }

    /**
     * 下载代码
     */
    private void downThisFileList(HttpServletResponse res, List<String> fileList, List<TemplateModel> templateList, GenerationEntity ge) {
        ZipOutputStream out = null;
        try {
            res.setContentType("application/octet-stream");
            res.setHeader("Content-Disposition", "attachment;filename=GEN_" + ge.getEntityName() + ".zip");
            out = new ZipOutputStream(res.getOutputStream());
            for (int i = 0; i < fileList.size(); i++) {
                out.putNextEntry(new ZipEntry(getPackagePath(templateList.get(i), ge) + File.separator + getFileName(templateList.get(i), ge)));
                out.write(fileList.get(i).getBytes(), 0, fileList.get(i).getBytes().length);
                out.closeEntry();
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        } finally {
            try {
                out.close();
                res.getOutputStream().flush();
                res.getOutputStream().close();
            } catch (IOException e) {
                LOGGER.error(e.getMessage(), e);
            }
        }
    }

    private String getFileName(TemplateModel templateModel, GenerationEntity ge) {
        if (templateModel.getFileName().endsWith("js")) {
            return String.format(templateModel.getFileName(), ge.getEntityName().toLowerCase());
        } else if (templateModel.getFileName().endsWith("html")) {
            return String.format(templateModel.getFileName(), ge.getEntityName().toLowerCase());
        } else if (templateModel.getFileName().endsWith("vue")) {
            return String.format(templateModel.getFileName(), ge.getEntityName().toLowerCase());
        } else if (templateModel.getFileName().endsWith("xml")) {
            return String.format(templateModel.getFileName(), ge.getEntityName());
        } else {
            return String.format(templateModel.getFileName(), ge.getEntityName());
        }
    }


    private String getPackagePath(TemplateModel templateModel, GenerationEntity ge) {
        if (templateModel.getFileName().endsWith("js")) {
            return ge.getJsPackage().replaceAll("\\.", "\\/")
                    + (StringUtils.isNotEmpty(templateModel.getTemplatePath()) ? "/"
                    + templateModel.getTemplatePath().replaceAll("\\.", "\\/") : "");
        } else if (templateModel.getFileName().endsWith("html")) {
            return ge.getHtmlPackage().replaceAll("\\.", "\\/")
                    + (StringUtils.isNotEmpty(templateModel.getTemplatePath()) ? "/"
                    + templateModel.getTemplatePath().replaceAll("\\.", "\\/") : "");
        } else if (templateModel.getFileName().endsWith("vue")) {
            return ge.getHtmlPackage().replaceAll("\\.", "\\/")
                    + (StringUtils.isNotEmpty(templateModel.getTemplatePath()) ? "/"
                    + templateModel.getTemplatePath().replaceAll("\\.", "\\/") : "");
        } else if (templateModel.getFileName().endsWith("xml")) {
            return ge.getXmlPackage().replaceAll("\\.", "\\/")
                    + (StringUtils.isNotEmpty(templateModel.getTemplatePath()) ? "/"
                    + templateModel.getTemplatePath().replaceAll("\\.", "\\/") : "");
        } else {
            return ge.getCodePackage().replaceAll("\\.", "\\/")
                    + (StringUtils.isNotEmpty(templateModel.getTemplatePath()) ? "/"
                    + templateModel.getTemplatePath().replaceAll("\\.", "\\/") : "");
        }
    }


}

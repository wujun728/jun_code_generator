package cn.afterturn.gen.modular.code.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;

import cn.afterturn.gen.common.annotion.BussinessLog;
import cn.afterturn.gen.common.annotion.Permission;
import cn.afterturn.gen.common.constant.factory.PageFactory;
import cn.afterturn.gen.common.exception.BizExceptionEnum;
import cn.afterturn.gen.common.exception.BussinessException;
import cn.afterturn.gen.core.CodeGenModel;
import cn.afterturn.gen.core.CodeGenUtil;
import cn.afterturn.gen.core.GenCoreConstant;
import cn.afterturn.gen.core.base.controller.BaseController;
import cn.afterturn.gen.core.model.GenBeanEntity;
import cn.afterturn.gen.core.shiro.ShiroKit;
import cn.afterturn.gen.core.util.ToolUtil;
import cn.afterturn.gen.modular.code.model.DbInfoModel;
import cn.afterturn.gen.modular.code.model.TableInfoModel;
import cn.afterturn.gen.modular.code.service.IDbInfoService;
import cn.afterturn.gen.modular.code.service.ITableConvertServer;
import cn.afterturn.gen.modular.code.service.ITableInfoService;
import cn.afterturn.gen.modular.system.warpper.BeanKeyConvert;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

/**
 * 控制器
 *
 * @author JueYue
 * @Date 2017-09-20 09:18
 */
@Controller
@RequestMapping("/tableinfo")
public class TableInfoController extends BaseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(TableInfoController.class);

    private String PREFIX = "/code/tableinfo/";

    @Autowired
    private ITableInfoService tableInfoService;
    @Autowired
    private IDbInfoService dbInfoService;
    @Resource(name = "dbTableConvertServer")
    private ITableConvertServer dbTableConvertServer;
    @Resource(name = "sqlTableConvertServer")
    private ITableConvertServer sqlTableConvertServer;

    /**
     * 跳转到首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "tableinfo.html";
    }

    /**
     * 跳转到添加
     */
    @RequestMapping("/goto_add")
    public String tableInfoAdd() {
        return PREFIX + "tableinfo_add.html";
    }

    /**
     * 跳转到修改
     */
    @RequestMapping("/goto_update/{id}")
    public String tbleInfoUpdate(@PathVariable Integer id, Model model) {
        model.addAttribute("tableinfo", tableInfoService.selectById(id));
        return PREFIX + "tableinfo_edit.html";
    }

    /**
     * 获取列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(TableInfoModel model) {
        Page<TableInfoModel> page = new PageFactory<TableInfoModel>().defaultPage();
        model.setUserId(ShiroKit.getUser().getId());
        page.setRecords(tableInfoService.selectPage(page, model, new EntityWrapper<TableInfoModel>()));
        BeanKeyConvert.systemUserNameConvert(page.getRecords());
        return super.packForBT(page);
    }


    @BussinessLog(value = "新增", key = "tableName")
    @RequestMapping(value = "/add")
    @Permission
    @ResponseBody
    public Object add(@RequestBody TableInfoModel model) {
        tableInfoService.insert(model,ShiroKit.getUser().getId());
        return SUCCESS_TIP;
    }


    @BussinessLog(value = "删除", key = "id")
    @RequestMapping(value = "/delete")
    @Permission
    @ResponseBody
    public Object delete(Integer id) {
        tableInfoService.deleteById(id);
        return SUCCESS_TIP;
    }


    @BussinessLog(value = "修改", key = "id")
    @RequestMapping(value = "/update")
    @Permission
    @ResponseBody
    public Object update(@RequestBody TableInfoModel model) {
        if (ToolUtil.isOneEmpty(model.getId())) {
            throw new BussinessException(BizExceptionEnum.REQUEST_NULL);
        }
        tableInfoService.updateById(model,ShiroKit.getUser().getId());
        return SUCCESS_TIP;
    }

    /**
     * 详情
     */
    @RequestMapping(value = "/detail")
    @ResponseBody
    public Object detail(TableInfoModel model) {
        return tableInfoService.selectOne(model);
    }

    @RequestMapping("/goto_dbimport")
    public String tableInfoDbImport() {
        return PREFIX + "tableinfo_dbimport.html";
    }


    @BussinessLog(value = "DB导入", key = "tableName")
    @RequestMapping(value = "/dbimport")
    @Permission
    @ResponseBody
    public Object dbImport(DbInfoModel entity, String dbName, String tableName) {
        entity = dbInfoService.selectOne(entity);
        CodeGenModel model = new CodeGenModel();
        model.setDbType(GenCoreConstant.MYSQL);
        model.setTableName(tableName);
        model.setDbName(dbName);
        model.setUrl(entity.getDbUrl());
        model.setPasswd(entity.getDbPassword());
        model.setUsername(entity.getDbUserName());
        GenBeanEntity bean = CodeGenUtil.getTableBean(model);
        dbTableConvertServer.importBean(JSON.toJSONString(bean), ShiroKit.getUser().getId());
        return SUCCESS_TIP;
    }

    @RequestMapping("/goto_sqlimport")
    public String tableInfoSQLImport() {
        return PREFIX + "tableinfo_sqlimport.html";
    }

    @BussinessLog(value = "SQL导入")
    @RequestMapping(value = "/sqlimport")
    @Permission
    @ResponseBody
    public Object sqlImport(String dbType, String sql) {
        //sql = HtmlUtils.htmlUnescape(sql);
        sql = handlerFileEncode(sql);
        Map<String, String> map = new HashMap<String, String>();
        map.put("dbType", dbType);
        map.put("sql", sql);
        sqlTableConvertServer.importBean(JSON.toJSONString(map), ShiroKit.getUser().getId());
        return SUCCESS_TIP;
    }

    @BussinessLog(value = "文档生产")
    @RequestMapping(value = "/genDoc")
    public String genDoc(ModelMap map) {
        return PREFIX + "tableinfo_gendoc.html";
    }

    private String handlerFileEncode(String sql) {
        return sql.replaceAll("& #40;","(")
                .replaceAll("& #41;",")")
                .replaceAll("& lt;","<")
                .replaceAll("& gt;",">")
                .replaceAll("& #39;","'");
    }
}

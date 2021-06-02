package cn.afterturn.gen.modular.code.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;

import cn.afterturn.gen.common.annotion.BussinessLog;
import cn.afterturn.gen.common.annotion.Permission;
import cn.afterturn.gen.common.constant.factory.PageFactory;
import cn.afterturn.gen.common.exception.BizExceptionEnum;
import cn.afterturn.gen.common.exception.BussinessException;
import cn.afterturn.gen.core.base.controller.BaseController;
import cn.afterturn.gen.core.model.enmus.DBType;
import cn.afterturn.gen.core.shiro.ShiroKit;
import cn.afterturn.gen.core.util.ToolUtil;
import cn.afterturn.gen.modular.code.model.DbInfoModel;
import cn.afterturn.gen.modular.code.service.IDbInfoService;
import cn.afterturn.gen.modular.system.warpper.BeanKeyConvert;

/**
 * 数据库管理控制器
 *
 * @author JueYue
 * @Date 2017-09-11 11:15
 */
@Controller
@RequestMapping("/dbinfo")
public class DbInfoController extends BaseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(DbInfoController.class);

    private String PREFIX = "/code/dbinfo/";

    @Autowired
    private IDbInfoService dbInfoService;

    /**
     * 跳转到首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "dbinfo.html";
    }

    /**
     * 跳转到添加
     */
    @RequestMapping("/goto_add")
    public String DbInfoAdd() {
        return PREFIX + "dbinfo_add.html";
    }

    /**
     * 跳转到修改
     */
    @RequestMapping("/goto_update/{id}")
    public String DbInfoUpdate(@PathVariable Integer id, Model model) {
        model.addAttribute("dbinfo", dbInfoService.selectById(id));
        return PREFIX + "dbinfo_edit.html";
    }

    /**
     * 获取列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(DbInfoModel model) {
        Page<DbInfoModel> page = new PageFactory<DbInfoModel>().defaultPage();
        model.setUserId(ShiroKit.getUser().getId());
        page.setRecords(dbInfoService.selectPage(page, model, new EntityWrapper<DbInfoModel>()));
        BeanKeyConvert.systemUserNameConvert(page.getRecords());
        return super.packForBT(page);
    }

    /**
     * 获取列表
     */
    @RequestMapping(value = "/queryAll")
    @ResponseBody
    public Object queryAll(DbInfoModel model) {
        Page<DbInfoModel> page = new PageFactory<DbInfoModel>().defaultPage();
        model.setUserId(ShiroKit.getUser().getId());
        page.setRecords(dbInfoService.selectPage(page, model, new EntityWrapper<DbInfoModel>()));
        return super.packForBT(page);
    }


    @BussinessLog(value = "数据库管理新增", key = "alias")
    @RequestMapping(value = "/add")
    @Permission
    @ResponseBody
    public Object add(DbInfoModel model) {
        model.setDbDriver(DBType.getDbTypeByType(model.getDbType()).getDriver());
        dbInfoService.insert(model);
        return SUCCESS_TIP;
    }


    @BussinessLog(value = "数据库管理删除", key = "id")
    @RequestMapping(value = "/delete")
    @Permission
    @ResponseBody
    public Object delete(Integer id) {
        dbInfoService.deleteById(id);
        return SUCCESS_TIP;
    }


    @BussinessLog(value = "数据库管理修改", key = "id")
    @RequestMapping(value = "/update")
    @Permission
    @ResponseBody
    public Object update(DbInfoModel model) {
        if (ToolUtil.isOneEmpty(model.getId())) {
            throw new BussinessException(BizExceptionEnum.REQUEST_NULL);
        }
        model.setDbDriver(DBType.getDbTypeByType(model.getDbType()).getDriver());
        dbInfoService.updateById(model);
        return SUCCESS_TIP;
    }

    /**
     * 详情
     */
    @RequestMapping(value = "/detail")
    @ResponseBody
    public Object detail(DbInfoModel model) {
        return dbInfoService.selectOne(model);
    }
}

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
import cn.afterturn.gen.core.shiro.ShiroKit;
import cn.afterturn.gen.core.util.ToolUtil;
import cn.afterturn.gen.modular.code.model.TemplateGroupModel;
import cn.afterturn.gen.modular.code.service.ITemplateGroupService;
import cn.afterturn.gen.modular.system.warpper.BeanKeyConvert;

/**
 * 组管理控制器
 *
 * @author JueYue
 * @Date 2017-09-12 13:45
 */
@Controller
@RequestMapping("/templategroup")
public class TemplateGroupController extends BaseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(TemplateGroupController.class);

    private String PREFIX = "/code/group/";

    @Autowired
    private ITemplateGroupService templateGroupService;

    /**
     * 跳转到首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "templategroup.html";
    }

    /**
     * 跳转到添加
     */
    @RequestMapping("/goto_add")
    public String TemplateGroupAdd() {
        return PREFIX + "templategroup_add.html";
    }

    /**
     * 跳转到修改
     */
    @RequestMapping("/goto_update/{id}")
    public String TemplateGroupUpdate(@PathVariable Integer id, Model model) {
        model.addAttribute("templategroup", templateGroupService.selectById(id));
        return PREFIX + "templategroup_edit.html";
    }

    /**
     * 获取列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(TemplateGroupModel model) {
        Page<TemplateGroupModel> page = new PageFactory<TemplateGroupModel>().defaultPage();
        model.setUserId(ShiroKit.getUser().getId());
        page.setRecords(templateGroupService.selectPage(page, model, new EntityWrapper<TemplateGroupModel>()));
        BeanKeyConvert.systemUserNameConvert(page.getRecords());
        return super.packForBT(page);
    }


    @BussinessLog(value = "组管理新增", key = "companyid")
    @RequestMapping(value = "/add")
    @Permission
    @ResponseBody
    public Object add(TemplateGroupModel model) {
        templateGroupService.insert(model);
        return SUCCESS_TIP;
    }


    @BussinessLog(value = "组管理删除", key = "id")
    @RequestMapping(value = "/delete")
    @Permission
    @ResponseBody
    public Object delete(Integer id) {
        templateGroupService.deleteById(id);
        return SUCCESS_TIP;
    }


    @BussinessLog(value = "组管理修改", key = "id")
    @RequestMapping(value = "/update")
    @Permission
    @ResponseBody
    public Object update(TemplateGroupModel model) {
        if (ToolUtil.isOneEmpty(model.getId())) {
            throw new BussinessException(BizExceptionEnum.REQUEST_NULL);
        }
        templateGroupService.updateById(model);
        return SUCCESS_TIP;
    }

    @BussinessLog(value = "组管理分享", key = "id")
    @RequestMapping(value = "/share/{id}")
    @Permission
    @ResponseBody
    public Object share(@PathVariable Integer id) {
        templateGroupService.share(id);
        return SUCCESS_TIP;
    }

    /**
     * 详情
     */
    @RequestMapping(value = "/detail")
    @ResponseBody
    public Object detail(TemplateGroupModel model) {
        return templateGroupService.selectOne(model);
    }
}

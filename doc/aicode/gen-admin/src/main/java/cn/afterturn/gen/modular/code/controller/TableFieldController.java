package cn.afterturn.gen.modular.code.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import cn.afterturn.gen.common.annotion.BussinessLog;
import cn.afterturn.gen.common.annotion.Permission;
import cn.afterturn.gen.common.constant.factory.PageFactory;
import cn.afterturn.gen.common.exception.BizExceptionEnum;
import cn.afterturn.gen.common.exception.BussinessException;
import cn.afterturn.gen.core.base.controller.BaseController;
import cn.afterturn.gen.core.util.ToolUtil;
import cn.afterturn.gen.modular.code.model.TableFieldModel;
import cn.afterturn.gen.modular.code.service.ITableFieldService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 控制器
 *
 * @author JueYue
 * @Date 2017-09-20 09:22
 */
@Controller
@RequestMapping("/tablefield")
public class TableFieldController extends BaseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(TableFieldController.class);

    private String PREFIX = "/biz/tablefield/";

    @Autowired
    private ITableFieldService tableFieldService;

    /**
     * 跳转到首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "tablefield.html";
    }

    /**
     * 跳转到添加
     */
    @RequestMapping("/goto_add")
    public String TableFieldAdd() {
        return PREFIX + "tablefield_add.html";
    }

    /**
     * 跳转到修改
     */
    @RequestMapping("/goto_update/{id}")
    public String TableFieldUpdate(@PathVariable Integer id, Model model) {
	model.addAttribute("tablefield", tableFieldService.selectById(id));
        return PREFIX + "tablefield_edit.html";
    }

    /**
     * 获取列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(TableFieldModel model) {
        Page<TableFieldModel> page = new PageFactory<TableFieldModel>().defaultPage();
        page.setRecords(tableFieldService.selectPage(page,model,new EntityWrapper<TableFieldModel>()));
        return super.packForBT(page);
    }


    @BussinessLog(value = "新增", key = "companyid" )
    @RequestMapping(value = "/add")
    @Permission
    @ResponseBody
    public Object add(TableFieldModel model) {
        tableFieldService.insert(model);
        return SUCCESS_TIP;
    }


    @BussinessLog(value = "删除", key = "id" )
    @RequestMapping(value = "/delete")
    @Permission
    @ResponseBody
    public Object delete(Integer id) {
        tableFieldService.deleteById(id);
        return SUCCESS_TIP;
    }


    @BussinessLog(value = "修改", key = "id" )
    @RequestMapping(value = "/update")
    @Permission
    @ResponseBody
    public Object update(TableFieldModel model) {
        if (ToolUtil.isOneEmpty(model.getId())) {
            throw new BussinessException(BizExceptionEnum.REQUEST_NULL);
        }
        tableFieldService.updateById(model);
        return SUCCESS_TIP;
    }

    /**
     * 详情
     */
    @RequestMapping(value = "/detail")
    @ResponseBody
    public Object detail(TableFieldModel model) {
        return tableFieldService.selectOne(model);
    }
}

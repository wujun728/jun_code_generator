<%if (t.isProtocol == 1) {%>
        ${g.copyright!}
<%}%>
package ${g.codePackage}.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.stylefeng.guns.common.annotion.Permission;
import com.stylefeng.guns.common.annotion.log.BussinessLog;
import com.stylefeng.guns.common.constant.factory.PageFactory;
import com.stylefeng.guns.common.constant.state.BizLogType;
import com.stylefeng.guns.common.controller.BaseController;
import com.stylefeng.guns.common.exception.BizExceptionEnum;
import com.stylefeng.guns.common.exception.BussinessException;
import com.stylefeng.guns.common.persistence.model.OperationLog;
import com.stylefeng.guns.core.util.ToolUtil;
import ${g.codePackage}.model.${g.entityName}Model;
import ${g.codePackage}.service.I${g.entityName}Service;

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
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * ${g.name}控制器
 *
 * @author ${g.author}
 * @Date ${g.date}
 */
@Controller
@RequestMapping("/${strutil.toLowerCase(g.entityName)}")
public class ${g.entityName}Controller extends BaseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(${g.entityName}Controller.class);

    private String PREFIX = "/biz/${strutil.toLowerCase(g.entityName)}/";

    @Autowired
    private I${g.entityName}Service ${g.lowerEntityName}Service;

    /**
     * 跳转到首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "${strutil.toLowerCase(g.entityName)}.html";
    }

    /**
     * 跳转到添加
     */
    @RequestMapping("/goto_add")
    public String gotoAdd() {
        return PREFIX + "${strutil.toLowerCase(g.entityName)}_add.html";
    }

    /**
     * 跳转到修改
     */
    @RequestMapping("/goto_update/{id}")
    public String gotoUpdate(@PathVariable Integer id, Model model) {
        model.addAttribute("${strutil.toLowerCase(g.entityName)}", ${g.lowerEntityName}Service.selectById(id));
        return PREFIX + "${strutil.toLowerCase(g.entityName)}_edit.html";
    }

    /**
     * 获取列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(${g.entityName}Model model,HttpServletRequest req, HttpServletResponse res) {
        Page<${g.entityName}Model> page = new PageFactory<${g.entityName}Model>().defaultPage();
        page.setRecords(${g.lowerEntityName}Service.selectPage(page,model,new EntityWrapper<${g.entityName}Model>()));
        return super.packForBT(page);
    }

    <%if (t.isLog == 1) {%>
    @BussinessLog(value = "${g.name}新增", key = "?" )
    <%}%>
    @RequestMapping(value = "/add")
    @Permission
    @ResponseBody
    public Object add(${g.entityName}Model model) {
        ${g.lowerEntityName}Service.insert(model);
        return SUCCESS_TIP;
    }

    <%if (t.isLog == 1) {%>
    @BussinessLog(value = "${g.name}删除", key = "id" )
    <%}%>
    @RequestMapping(value = "/delete")
    @Permission
    @ResponseBody
    public Object delete(Integer id) {
        ${g.lowerEntityName}Service.deleteById(id);
        return SUCCESS_TIP;
    }

    <%if (t.isLog == 1) {%>
    @BussinessLog(value = "${g.name}修改", key = "id" )
    <%}%>
    @RequestMapping(value = "/update")
    @Permission
    @ResponseBody
    public Object update(${g.entityName}Model model) {
        if (ToolUtil.isOneEmpty(model.getId())) {
        throw new BussinessException(BizExceptionEnum.REQUEST_NULL);
        }
        ${g.lowerEntityName}Service.updateById(model);
        return SUCCESS_TIP;
    }

    @RequestMapping(value = "/detail")
    @ResponseBody
    public Object detail(${g.entityName}Model model) {
        return ${g.lowerEntityName}Service.selectOne(model);
    }
}
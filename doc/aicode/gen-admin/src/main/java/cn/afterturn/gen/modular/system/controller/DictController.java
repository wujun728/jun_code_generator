package cn.afterturn.gen.modular.system.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import cn.afterturn.gen.common.annotion.BussinessLog;
import cn.afterturn.gen.common.annotion.Permission;
import cn.afterturn.gen.common.constant.Const;
import cn.afterturn.gen.common.constant.factory.ConstantFactory;
import cn.afterturn.gen.common.exception.BizExceptionEnum;
import cn.afterturn.gen.common.exception.BussinessException;
import cn.afterturn.gen.common.persistence.dao.DictMapper;
import cn.afterturn.gen.common.persistence.model.Dict;
import cn.afterturn.gen.core.base.controller.BaseController;
import cn.afterturn.gen.core.log.LogObjectHolder;
import cn.afterturn.gen.core.util.ToolUtil;
import cn.afterturn.gen.modular.system.dao.DictDao;
import cn.afterturn.gen.modular.system.service.IDictService;
import cn.afterturn.gen.modular.system.warpper.DictWarpper;

/**
 * 字典控制器
 *
 * @author fengshuonan
 * @Date 2017年4月26日 12:55:31
 */
@Controller
@RequestMapping("/dict")
public class DictController extends BaseController {

    private String PREFIX = "/system/dict/";

    @Resource
    DictDao dictDao;

    @Resource
    DictMapper dictMapper;

    @Resource
    IDictService dictService;

    /**
     * 跳转到字典管理首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "dict.html";
    }

    /**
     * 跳转到添加字典
     */
    @RequestMapping("/dict_add")
    public String deptAdd() {
        return PREFIX + "dict_add.html";
    }

    /**
     * 跳转到修改字典
     */
    @RequestMapping("/dict_edit/{dictId}")
    public String deptUpdate(@PathVariable Integer dictId, Model model) {
        Dict dict = dictMapper.selectById(dictId);
        model.addAttribute("dict", dict);
        List<Dict> subDicts = dictMapper.selectList(new EntityWrapper<Dict>().eq("pid", dictId));
        model.addAttribute("subDicts", subDicts);
        LogObjectHolder.me().set(dict);
        return PREFIX + "dict_edit.html";
    }

    /**
     * 新增字典
     *
     * @param dictValues 格式例如   "1:启用;2:禁用;3:冻结"
     */
    @BussinessLog(value = "添加字典记录", key = "dictName,dictValues", dict = cn.afterturn.gen.common.constant.Dict.DictMap)
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(String dictName, String dictValues) {
        if (ToolUtil.isOneEmpty(dictName, dictValues)) {
            throw new BussinessException(BizExceptionEnum.REQUEST_NULL);
        }
        this.dictService.addDict(dictName, dictValues);
        return SUCCESS_TIP;
    }

    /**
     * 获取所有字典列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition) {
        List<Map<String, Object>> list = this.dictDao.list(condition);
        return super.warpObject(new DictWarpper(list));
    }

    /**
     * 字典详情
     */
    @RequestMapping(value = "/detail/{dictId}")
    @ResponseBody
    public Object detail(@PathVariable("dictId") Integer dictId) {
        return dictMapper.selectById(dictId);
    }

    /**
     * 修改字典
     */
    @BussinessLog(value = "修改字典", key = "dictName,dictValues", dict = cn.afterturn.gen.common.constant.Dict.DictMap)
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(Integer dictId, String dictName, String dictValues) {
        if (ToolUtil.isOneEmpty(dictId, dictName, dictValues)) {
            throw new BussinessException(BizExceptionEnum.REQUEST_NULL);
        }
        dictService.editDict(dictId, dictName, dictValues);
        return SUCCESS_TIP;
    }

    /**
     * 删除字典记录
     */
    @BussinessLog(value = "删除字典记录", key = "dictId", dict = cn.afterturn.gen.common.constant.Dict.DeleteDict)
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Integer dictId) {
        //缓存被删除的名称
        LogObjectHolder.me().set(ConstantFactory.me().getDictName(dictId));
        this.dictService.delteDict(dictId);
        return SUCCESS_TIP;
    }

    @RequestMapping(value = "/getKeys/{dictName}")
    @ResponseBody
    public Object getKeys(@PathVariable("dictName") String dictName) {
        return dictDao.selectByCode(dictName);
    }


    @RequestMapping(value = "/getDataByKey/{dictName}/{num}")
    @ResponseBody
    public Object getDataByKey(@PathVariable("dictName") String dictName ,@PathVariable("num") int num) {
        return dictDao.getDataByKey(dictName,num);
    }

}

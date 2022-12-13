package com.jeasy.dictionary.controller;

import com.jeasy.base.dto.PageDTO;
import com.jeasy.base.web.controller.BaseController;
import com.jeasy.base.web.dto.ModelResult;
import com.jeasy.base.web.resolver.FromJson;
import com.jeasy.common.Func;
import com.jeasy.dictionary.dto.*;
import com.jeasy.dictionary.service.DictionaryService;
import com.jeasy.doc.annotation.InitField;
import com.jeasy.doc.annotation.MethodDoc;
import com.jeasy.doc.annotation.StatusEnum;
import com.jeasy.validate.handler.ValidateNotNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 字典 Controller
 *
 * @author taomk
 * @version 1.0
 * @since 2017/11/08 16:33
 */
@Slf4j
@Controller
public class DictionaryController extends BaseController<DictionaryService> {

    @MethodDoc(lists = DictionaryListResDTO.class, desc = {"5.基础数据", "1.公共码表", "1.字典列表查询"}, status = StatusEnum.DONE, author = "taomk", finishTime = "2017/03/28 15:13")
    @RequestMapping(value = "dictionary/list", method = {RequestMethod.GET})
    @ResponseBody
    public void list(final @FromJson DictionaryListReqDTO dictionaryReqDTO) {
        List<DictionaryListResDTO> items = service.list(dictionaryReqDTO);
        responseList(ModelResult.CODE_200, ModelResult.SUCCESS, items);
    }

    @MethodDoc(lists = DictionaryTypeListResDTO.class, desc = {"5.基础数据", "1.公共码表", "2.字典类型查询"}, status = StatusEnum.DONE, author = "taomk", finishTime = "2017/03/28 15:13")
    @RequestMapping(value = "dictionary/listType", method = {RequestMethod.GET})
    @ResponseBody
    public void listType() {
        List<DictionaryTypeListResDTO> items = service.listType();
        responseList(ModelResult.CODE_200, ModelResult.SUCCESS, items);
    }

    @RequiresPermissions(value = {"JCSJ:GGMB:CX"})
    @MethodDoc(pages = DictionaryPageResDTO.class, desc = {"5.基础数据", "1.公共码表", "3.字典分页列表"}, status = StatusEnum.DONE, author = "taomk", finishTime = "2017/03/28 15:13")
    @RequestMapping(value = "dictionary/page", method = {RequestMethod.GET})
    @ResponseBody
    public void page(final @FromJson DictionaryPageReqDTO dictionaryPageReqDTO,
                     final @InitField(name = "pageNo", value = "1", desc = "当前页码") @FromJson Integer pageNo,
                     final @InitField(name = "pageSize", value = "10", desc = "每页大小") @FromJson Integer pageSize) {
        int current = Func.isNullOrZero(pageNo) ? 1 : pageNo;
        int size = Func.isNullOrZero(pageSize) ? 10 : pageSize;

        PageDTO<DictionaryPageResDTO> dictionaryPage = service.pagination(dictionaryPageReqDTO, current, size);
        responsePage(ModelResult.CODE_200, ModelResult.SUCCESS, dictionaryPage.getTotal(), dictionaryPage.getRecords(), size, current);
    }

    @RequiresPermissions(value = {"JCSJ:GGMB:XZ"})
    @MethodDoc(desc = {"5.基础数据", "1.公共码表", "4.字典新增"}, status = StatusEnum.DONE, author = "taomk", finishTime = "2017/03/28 15:13")
    @RequestMapping(value = "dictionary/add", method = {RequestMethod.POST})
    @ResponseBody
    public void add(final @FromJson DictionaryAddReqDTO dictionaryAddReqDTO) {
        Boolean result = service.add(dictionaryAddReqDTO);
        responseMessage(result ? ModelResult.CODE_200 : ModelResult.CODE_500, result ? ModelResult.SUCCESS : ModelResult.FAIL);
    }

    @RequiresPermissions(value = {"JCSJ:GGMB:CK"})
    @MethodDoc(entity = DictionaryShowResDTO.class, desc = {"5.基础数据", "1.公共码表", "5.字典详情"}, status = StatusEnum.DONE, author = "taomk", finishTime = "2017/03/28 15:13")
    @RequestMapping(value = "dictionary/show", method = {RequestMethod.GET})
    @ResponseBody
    public void show(final @InitField(name = "id", value = "10", desc = "用户ID") @FromJson @ValidateNotNull(message = "ID不允许为空") Long id) {
        DictionaryShowResDTO dictionaryShowResDTO = service.show(id);
        responseEntity(Func.isNotEmpty(dictionaryShowResDTO) ? ModelResult.CODE_200 : ModelResult.CODE_500, Func.isNotEmpty(dictionaryShowResDTO) ? ModelResult.SUCCESS : ModelResult.FAIL, dictionaryShowResDTO);
    }

    @RequiresPermissions(value = {"JCSJ:GGMB:BJ"})
    @MethodDoc(desc = {"5.基础数据", "1.公共码表", "6.字典更新"}, status = StatusEnum.DONE, author = "taomk", finishTime = "2017/03/28 15:13")
    @RequestMapping(value = "dictionary/modify", method = {RequestMethod.POST})
    @ResponseBody
    public void modify(final @FromJson DictionaryModifyReqDTO dictionaryModifyReqDTO) {
        Boolean result = service.modify(dictionaryModifyReqDTO);
        responseMessage(result ? ModelResult.CODE_200 : ModelResult.CODE_500, result ? ModelResult.SUCCESS : ModelResult.FAIL);
    }

    @RequiresPermissions(value = {"JCSJ:GGMB:SC"})
    @MethodDoc(desc = {"5.基础数据", "1.公共码表", "7.字典删除"}, status = StatusEnum.DONE, author = "taomk", finishTime = "2017/03/28 15:13")
    @RequestMapping(value = "dictionary/remove", method = {RequestMethod.POST})
    @ResponseBody
    public void remove(final @InitField(name = "id", value = "10", desc = "字典ID") @FromJson @ValidateNotNull(message = "ID不允许为空") Long id) {
        Boolean result = service.remove(id);
        responseMessage(result ? ModelResult.CODE_200 : ModelResult.CODE_500, result ? ModelResult.SUCCESS : ModelResult.FAIL);
    }
}

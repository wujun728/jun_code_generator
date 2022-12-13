package com.jeasy.log.controller;

import com.jeasy.base.dto.PageDTO;
import com.jeasy.base.web.controller.BaseController;
import com.jeasy.base.web.dto.ModelResult;
import com.jeasy.base.web.resolver.FromJson;
import com.jeasy.common.Func;
import com.jeasy.doc.annotation.InitField;
import com.jeasy.doc.annotation.MethodDoc;
import com.jeasy.doc.annotation.StatusEnum;
import com.jeasy.log.dto.*;
import com.jeasy.log.service.LogService;
import com.jeasy.validate.handler.ValidateNotNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 日志 Controller
 *
 * @author taomk
 * @version 1.0
 * @since 2019/06/20 17:27
 */
@Slf4j
@Controller
public class LogController extends BaseController<LogService> {

    @MethodDoc(lists = LogListResDTO.class, desc = {"PC端", "日志-列表查询", "列表查询"}, status = StatusEnum.DONE, author = "taomk", finishTime = "2017/05/20 11:22")
    @RequestMapping(value = "log/list", method = {RequestMethod.GET})
    @ResponseBody
    public void list(final @FromJson LogListReqDTO logListReqDTO) {
        List<LogListResDTO> items = service.list(logListReqDTO);
        responseList(ModelResult.CODE_200, ModelResult.SUCCESS, items);
    }

    @MethodDoc(lists = LogListResDTO.class, desc = {"PC端", "日志-列表查询", "列表查询1.1.0"}, status = StatusEnum.DONE, author = "taomk", finishTime = "2017/05/20 11:22")
    @RequestMapping(value = "log/list", headers = {"version=1.1.0"}, method = {RequestMethod.GET})
    @ResponseBody
    public void listByVersion1(final @FromJson LogListReqDTO logListReqDTO) {
        List<LogListResDTO> items = service.listByVersion1(logListReqDTO);
        responseList(ModelResult.CODE_200, ModelResult.SUCCESS, items);
    }

    @MethodDoc(lists = LogListResDTO.class, desc = {"PC端", "日志-列表查询", "列表查询1.2.0"}, status = StatusEnum.DONE, author = "taomk", finishTime = "2017/05/20 11:22")
    @RequestMapping(value = "log/list", headers = {"version=1.2.0", "platform=APP"}, method = {RequestMethod.GET})
    @ResponseBody
    public void listByVersion2(final @FromJson LogListReqDTO logListReqDTO) {
        List<LogListResDTO> items = service.listByVersion2(logListReqDTO);
        responseList(ModelResult.CODE_200, ModelResult.SUCCESS, items);
    }

    @MethodDoc(lists = LogListResDTO.class, desc = {"PC端", "日志-列表查询", "列表查询1.3.0"}, status = StatusEnum.DONE, author = "taomk", finishTime = "2017/05/20 11:22")
    @RequestMapping(value = "log/list", headers = {"version=1.3.0", "platform=APP", "device=IOS"}, method = {RequestMethod.GET})
    @ResponseBody
    public void listByVersion3(final @FromJson LogListReqDTO logListReqDTO) {
        List<LogListResDTO> items = service.listByVersion3(logListReqDTO);
        responseList(ModelResult.CODE_200, ModelResult.SUCCESS, items);
    }

    @MethodDoc(entity = LogListResDTO.class, desc = {"PC端", "日志-列表查询", "First查询"}, status = StatusEnum.DONE, author = "taomk", finishTime = "2017/05/20 11:22")
    @RequestMapping(value = "log/listOne", method = {RequestMethod.GET})
    @ResponseBody
    public void listOne(final @FromJson LogListReqDTO logListReqDTO) {
        LogListResDTO entity = service.listOne(logListReqDTO);
        responseEntity(ModelResult.CODE_200, ModelResult.SUCCESS, entity);
    }

    @RequiresPermissions(value = {"RZJK:CZRZ:CX"})
    @MethodDoc(pages = LogPageResDTO.class, desc = {"PC端", "日志-分页查询", "分页查询"}, status = StatusEnum.DONE, author = "taomk", finishTime = "2017/05/20 11:22")
    @RequestMapping(value = "log/page", method = {RequestMethod.GET})
    @ResponseBody
    public void page(final @FromJson LogPageReqDTO logPageReqDTO,
                     final @InitField(name = "pageNo", value = "1", desc = "当前页码") @FromJson Integer pageNo,
                     final @InitField(name = "pageSize", value = "10", desc = "每页大小") @FromJson Integer pageSize) {
        int current = Func.isNullOrZero(pageNo) ? 1 : pageNo;
        int size = Func.isNullOrZero(pageSize) ? 10 : pageSize;

        PageDTO<LogPageResDTO> logPage = service.pagination(logPageReqDTO, current, size);
        responsePage(ModelResult.CODE_200, ModelResult.SUCCESS, logPage.getTotal(), logPage.getRecords(), size, current);
    }

    @RequiresPermissions(value = {"RZJK:CZRZ:XZ"})
    @MethodDoc(desc = {"PC端", "日志-新增", "新增"}, status = StatusEnum.DONE, author = "taomk", finishTime = "2017/05/20 11:22")
    @RequestMapping(value = "log/add", method = {RequestMethod.POST})
    @ResponseBody
    public void add(final @FromJson LogAddReqDTO logAddReqDTO) {
        Boolean isSuccess = service.add(logAddReqDTO);
        responseMessage(isSuccess ? ModelResult.CODE_200 : ModelResult.CODE_500, isSuccess ? ModelResult.SUCCESS : ModelResult.FAIL);
    }

    @MethodDoc(desc = {"PC端", "日志-新增", "新增(所有字段)"}, status = StatusEnum.DONE, author = "taomk", finishTime = "2017/05/20 11:22")
    @RequestMapping(value = "log/addAllColumn", method = {RequestMethod.POST})
    @ResponseBody
    public void addAllColumn(final @FromJson LogAddReqDTO logAddReqDTO) {
        Boolean isSuccess = service.addAllColumn(logAddReqDTO);
        responseMessage(isSuccess ? ModelResult.CODE_200 : ModelResult.CODE_500, isSuccess ? ModelResult.SUCCESS : ModelResult.FAIL);
    }

    @MethodDoc(desc = {"PC端", "日志-新增", "批量新增(所有字段)"}, status = StatusEnum.DONE, author = "taomk", finishTime = "2017/05/20 11:22")
    @RequestMapping(value = "log/addBatchAllColumn", method = {RequestMethod.POST})
    @ResponseBody
    public void addBatchAllColumn(final @FromJson List<LogAddReqDTO> logAddReqDTOList) {
        Boolean isSuccess = service.addBatchAllColumn(logAddReqDTOList);
        responseMessage(isSuccess ? ModelResult.CODE_200 : ModelResult.CODE_500, isSuccess ? ModelResult.SUCCESS : ModelResult.FAIL);
    }

    @RequiresPermissions(value = {"RZJK:CZRZ:CK"})
    @MethodDoc(entity = LogShowResDTO.class, desc = {"PC端", "日志-查看", "查看"}, status = StatusEnum.DONE, author = "taomk", finishTime = "2017/05/20 11:22")
    @RequestMapping(value = "log/show", method = {RequestMethod.GET})
    @ResponseBody
    public void show(final @InitField(name = "id", value = "10", desc = "主键ID") @FromJson @ValidateNotNull(message = "ID不允许为空") Long id) {
        LogShowResDTO entity = service.show(id);
        responseEntity(ModelResult.CODE_200, ModelResult.SUCCESS, entity);
    }

    @MethodDoc(entity = LogShowResDTO.class, desc = {"PC端", "日志-查看", "查看(批量)"}, status = StatusEnum.DONE, author = "taomk", finishTime = "2017/05/20 11:22")
    @RequestMapping(value = "log/showByIds", method = {RequestMethod.GET})
    @ResponseBody
    public void showByIds(final @InitField(name = "ids", value = "[\"1001\",\"1002\",\"1003\",\"1004\",\"1005\"]", desc = "主键ID集合") @FromJson List<Long> ids) {
        List<LogShowResDTO> items = service.showByIds(ids);
        responseList(ModelResult.CODE_200, ModelResult.SUCCESS, items);
    }

    @RequiresPermissions(value = {"RZJK:CZRZ:BJ"})
    @MethodDoc(desc = {"PC端", "日志-编辑", "编辑"}, status = StatusEnum.DONE, author = "taomk", finishTime = "2017/05/20 11:22")
    @RequestMapping(value = "log/modify", method = {RequestMethod.POST})
    @ResponseBody
    public void modify(final @FromJson LogModifyReqDTO logModifyReqDTO) {
        Boolean isSuccess = service.modify(logModifyReqDTO);
        responseMessage(isSuccess ? ModelResult.CODE_200 : ModelResult.CODE_500, isSuccess ? ModelResult.SUCCESS : ModelResult.FAIL);
    }

    @MethodDoc(desc = {"PC端", "日志-编辑", "编辑(所有字段)"}, status = StatusEnum.DONE, author = "taomk", finishTime = "2017/05/20 11:22")
    @RequestMapping(value = "log/modifySelective", method = {RequestMethod.POST})
    @ResponseBody
    public void modifyAllColumn(final @FromJson LogModifyReqDTO logModifyReqDTO) {
        Boolean isSuccess = service.modifyAllColumn(logModifyReqDTO);
        responseMessage(isSuccess ? ModelResult.CODE_200 : ModelResult.CODE_500, isSuccess ? ModelResult.SUCCESS : ModelResult.FAIL);
    }

    @RequiresPermissions(value = {"XXXX:XXXX:SC"})
    @MethodDoc(desc = {"PC端", "日志-删除", "删除"}, status = StatusEnum.DONE, author = "taomk", finishTime = "2017/05/20 11:22")
    @RequestMapping(value = "log/remove", method = {RequestMethod.POST})
    @ResponseBody
    public void remove(final @InitField(name = "id", value = "10", desc = "主键ID") @FromJson @ValidateNotNull(message = "ID不允许为空") Long id) {
        Boolean isSuccess = service.remove(id);
        responseMessage(isSuccess ? ModelResult.CODE_200 : ModelResult.CODE_500, isSuccess ? ModelResult.SUCCESS : ModelResult.FAIL);
    }

    @MethodDoc(desc = {"PC端", "日志-删除", "删除(批量)"}, status = StatusEnum.DONE, author = "taomk", finishTime = "2017/05/20 11:22")
    @RequestMapping(value = "log/removeBatch", method = {RequestMethod.POST})
    @ResponseBody
    public void removeBatch(final @InitField(name = "ids", value = "[\"1001\",\"1002\",\"1003\",\"1004\",\"1005\"]", desc = "主键ID集合") @FromJson List<Long> ids) {
        Boolean isSuccess = service.removeBatch(ids);
        responseMessage(isSuccess ? ModelResult.CODE_200 : ModelResult.CODE_500, isSuccess ? ModelResult.SUCCESS : ModelResult.FAIL);
    }

    @MethodDoc(desc = {"PC端", "日志-删除", "删除(参数)"}, status = StatusEnum.DONE, author = "taomk", finishTime = "2017/05/20 11:22")
    @RequestMapping(value = "log/removeByParams", method = {RequestMethod.POST})
    @ResponseBody
    public void removeByParams(final @FromJson LogRemoveReqDTO logRemoveReqDTO) {
        Boolean isSuccess = service.removeByParams(logRemoveReqDTO);
        responseMessage(isSuccess ? ModelResult.CODE_200 : ModelResult.CODE_500, isSuccess ? ModelResult.SUCCESS : ModelResult.FAIL);
    }
}

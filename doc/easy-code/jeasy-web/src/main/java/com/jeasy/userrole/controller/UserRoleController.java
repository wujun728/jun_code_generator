package com.jeasy.userrole.controller;

import com.jeasy.base.dto.PageDTO;
import com.jeasy.base.web.controller.BaseController;
import com.jeasy.base.web.dto.ModelResult;
import com.jeasy.base.web.resolver.FromJson;
import com.jeasy.common.Func;
import com.jeasy.doc.annotation.InitField;
import com.jeasy.doc.annotation.MethodDoc;
import com.jeasy.doc.annotation.StatusEnum;
import com.jeasy.userrole.dto.*;
import com.jeasy.userrole.service.UserRoleService;
import com.jeasy.validate.handler.ValidateNotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 用户角色 Controller
 *
 * @author taomk
 * @version 1.0
 * @since 2017/11/08 16:33
 */
@Slf4j
@Controller
public class UserRoleController extends BaseController<UserRoleService> {

    @MethodDoc(lists = UserRoleListResDTO.class, desc = {"PC端", "用户角色-列表", "列表"}, status = StatusEnum.DONE, author = "taomk", finishTime = "2017/05/20 11:22")
    @RequestMapping(value = "userRole/list", method = {RequestMethod.GET})
    @ResponseBody
    public void list(final @FromJson UserRoleListReqDTO userRoleListReqDTO) {
        List<UserRoleListResDTO> items = service.list(userRoleListReqDTO);
        responseList(ModelResult.CODE_200, ModelResult.SUCCESS, items);
    }

    @MethodDoc(lists = UserRoleListResDTO.class, desc = {"PC端", "用户角色-列表", "列表1.1.0"}, status = StatusEnum.DONE, author = "taomk", finishTime = "2017/05/20 11:22")
    @RequestMapping(value = "userRole/list", headers = {"version=1.1.0"}, method = {RequestMethod.GET})
    @ResponseBody
    public void listByVersion1(final @FromJson UserRoleListReqDTO userRoleListReqDTO) {
        List<UserRoleListResDTO> items = service.listByVersion1(userRoleListReqDTO);
        responseList(ModelResult.CODE_200, ModelResult.SUCCESS, items);
    }

    @MethodDoc(lists = UserRoleListResDTO.class, desc = {"PC端", "用户角色-列表", "列表1.2.0"}, status = StatusEnum.DONE, author = "taomk", finishTime = "2017/05/20 11:22")
    @RequestMapping(value = "userRole/list", headers = {"version=1.2.0", "platform=APP"}, method = {RequestMethod.GET})
    @ResponseBody
    public void listByVersion2(final @FromJson UserRoleListReqDTO userRoleListReqDTO) {
        List<UserRoleListResDTO> items = service.listByVersion2(userRoleListReqDTO);
        responseList(ModelResult.CODE_200, ModelResult.SUCCESS, items);
    }

    @MethodDoc(lists = UserRoleListResDTO.class, desc = {"PC端", "用户角色-列表", "列表1.3.0"}, status = StatusEnum.DONE, author = "taomk", finishTime = "2017/05/20 11:22")
    @RequestMapping(value = "userRole/list", headers = {"version=1.3.0", "platform=APP", "device=IOS"}, method = {RequestMethod.GET})
    @ResponseBody
    public void listByVersion3(final @FromJson UserRoleListReqDTO userRoleListReqDTO) {
        List<UserRoleListResDTO> items = service.listByVersion3(userRoleListReqDTO);
        responseList(ModelResult.CODE_200, ModelResult.SUCCESS, items);
    }

    @MethodDoc(entity = UserRoleListResDTO.class, desc = {"PC端", "用户角色-列表", "First查询"}, status = StatusEnum.DONE, author = "taomk", finishTime = "2017/05/20 11:22")
    @RequestMapping(value = "userRole/listOne", method = {RequestMethod.GET})
    @ResponseBody
    public void listOne(final @FromJson UserRoleListReqDTO userRoleListReqDTO) {
        UserRoleListResDTO entity = service.listOne(userRoleListReqDTO);
        responseEntity(ModelResult.CODE_200, ModelResult.SUCCESS, entity);
    }

    @MethodDoc(pages = UserRolePageResDTO.class, desc = {"PC端", "用户角色-分页", "分页"}, status = StatusEnum.DONE, author = "taomk", finishTime = "2017/05/20 11:22")
    @RequestMapping(value = "userRole/page", method = {RequestMethod.GET})
    @ResponseBody
    public void page(final @FromJson UserRolePageReqDTO userRolePageReqDTO,
                     final @InitField(name = "pageNo", value = "1", desc = "当前页码") @FromJson Integer pageNo,
                     final @InitField(name = "pageSize", value = "10", desc = "每页大小") @FromJson Integer pageSize) {
        int current = Func.isNullOrZero(pageNo) ? 1 : pageNo;
        int size = Func.isNullOrZero(pageSize) ? 10 : pageSize;

        PageDTO<UserRolePageResDTO> userRolePage = service.pagination(userRolePageReqDTO, current, size);
        responsePage(ModelResult.CODE_200, ModelResult.SUCCESS, userRolePage.getTotal(), userRolePage.getRecords(), size, current);
    }

    @MethodDoc(desc = {"PC端", "用户角色-新增", "新增"}, status = StatusEnum.DONE, author = "taomk", finishTime = "2017/05/20 11:22")
    @RequestMapping(value = "userRole/add", method = {RequestMethod.POST})
    @ResponseBody
    public void add(final @FromJson UserRoleAddReqDTO userRoleAddReqDTO) {
        Boolean isSuccess = service.add(userRoleAddReqDTO);
        responseMessage(isSuccess ? ModelResult.CODE_200 : ModelResult.CODE_500, isSuccess ? ModelResult.SUCCESS : ModelResult.FAIL);
    }

    @MethodDoc(desc = {"PC端", "用户角色-新增", "新增(所有字段)"}, status = StatusEnum.DONE, author = "taomk", finishTime = "2017/05/20 11:22")
    @RequestMapping(value = "userRole/addAllColumn", method = {RequestMethod.POST})
    @ResponseBody
    public void addAllColumn(final @FromJson UserRoleAddReqDTO userRoleAddReqDTO) {
        Boolean isSuccess = service.addAllColumn(userRoleAddReqDTO);
        responseMessage(isSuccess ? ModelResult.CODE_200 : ModelResult.CODE_500, isSuccess ? ModelResult.SUCCESS : ModelResult.FAIL);
    }

    @MethodDoc(desc = {"PC端", "用户角色-新增", "批量新增(所有字段)"}, status = StatusEnum.DONE, author = "taomk", finishTime = "2017/05/20 11:22")
    @RequestMapping(value = "userRole/addBatchAllColumn", method = {RequestMethod.POST})
    @ResponseBody
    public void addBatchAllColumn(final @FromJson List<UserRoleAddReqDTO> userRoleAddReqDTOList) {
        Boolean isSuccess = service.addBatchAllColumn(userRoleAddReqDTOList);
        responseMessage(isSuccess ? ModelResult.CODE_200 : ModelResult.CODE_500, isSuccess ? ModelResult.SUCCESS : ModelResult.FAIL);
    }

    @MethodDoc(entity = UserRoleShowResDTO.class, desc = {"PC端", "用户角色-详情", "详情"}, status = StatusEnum.DONE, author = "taomk", finishTime = "2017/05/20 11:22")
    @RequestMapping(value = "userRole/show", method = {RequestMethod.GET})
    @ResponseBody
    public void show(final @InitField(name = "id", value = "10", desc = "主键ID") @FromJson @ValidateNotNull(message = "ID不允许为空") Long id) {
        UserRoleShowResDTO entity = service.show(id);
        responseEntity(ModelResult.CODE_200, ModelResult.SUCCESS, entity);
    }

    @MethodDoc(entity = UserRoleShowResDTO.class, desc = {"PC端", "用户角色-详情", "详情(批量)"}, status = StatusEnum.DONE, author = "taomk", finishTime = "2017/05/20 11:22")
    @RequestMapping(value = "userRole/showByIds", method = {RequestMethod.GET})
    @ResponseBody
    public void showByIds(final @InitField(name = "ids", value = "[\"1001\",\"1002\",\"1003\",\"1004\",\"1005\"]", desc = "主键ID集合") @FromJson List<Long> ids) {
        List<UserRoleShowResDTO> items = service.showByIds(ids);
        responseList(ModelResult.CODE_200, ModelResult.SUCCESS, items);
    }

    @MethodDoc(desc = {"PC端", "用户角色-更新", "更新"}, status = StatusEnum.DONE, author = "taomk", finishTime = "2017/05/20 11:22")
    @RequestMapping(value = "userRole/modify", method = {RequestMethod.POST})
    @ResponseBody
    public void modify(final @FromJson UserRoleModifyReqDTO userRoleModifyReqDTO) {
        Boolean isSuccess = service.modify(userRoleModifyReqDTO);
        responseMessage(isSuccess ? ModelResult.CODE_200 : ModelResult.CODE_500, isSuccess ? ModelResult.SUCCESS : ModelResult.FAIL);
    }

    @MethodDoc(desc = {"PC端", "用户角色-更新", "更新(所有字段)"}, status = StatusEnum.DONE, author = "taomk", finishTime = "2017/05/20 11:22")
    @RequestMapping(value = "userRole/modifySelective", method = {RequestMethod.POST})
    @ResponseBody
    public void modifyAllColumn(final @FromJson UserRoleModifyReqDTO userRoleModifyReqDTO) {
        Boolean isSuccess = service.modifyAllColumn(userRoleModifyReqDTO);
        responseMessage(isSuccess ? ModelResult.CODE_200 : ModelResult.CODE_500, isSuccess ? ModelResult.SUCCESS : ModelResult.FAIL);
    }

    @MethodDoc(desc = {"PC端", "用户角色-删除", "删除"}, status = StatusEnum.DONE, author = "taomk", finishTime = "2017/05/20 11:22")
    @RequestMapping(value = "userRole/remove", method = {RequestMethod.POST})
    @ResponseBody
    public void remove(final @InitField(name = "id", value = "10", desc = "主键ID") @FromJson @ValidateNotNull(message = "ID不允许为空") Long id) {
        Boolean isSuccess = service.remove(id);
        responseMessage(isSuccess ? ModelResult.CODE_200 : ModelResult.CODE_500, isSuccess ? ModelResult.SUCCESS : ModelResult.FAIL);
    }

    @MethodDoc(desc = {"PC端", "用户角色-删除", "删除(批量)"}, status = StatusEnum.DONE, author = "taomk", finishTime = "2017/05/20 11:22")
    @RequestMapping(value = "userRole/removeBatch", method = {RequestMethod.POST})
    @ResponseBody
    public void removeBatch(final @InitField(name = "ids", value = "[\"1001\",\"1002\",\"1003\",\"1004\",\"1005\"]", desc = "主键ID集合") @FromJson List<Long> ids) {
        Boolean isSuccess = service.removeBatch(ids);
        responseMessage(isSuccess ? ModelResult.CODE_200 : ModelResult.CODE_500, isSuccess ? ModelResult.SUCCESS : ModelResult.FAIL);
    }

    @MethodDoc(desc = {"PC端", "用户角色-删除", "删除(参数)"}, status = StatusEnum.DONE, author = "taomk", finishTime = "2017/05/20 11:22")
    @RequestMapping(value = "userRole/removeByParams", method = {RequestMethod.POST})
    @ResponseBody
    public void removeByParams(final @FromJson UserRoleRemoveReqDTO userRoleRemoveReqDTO) {
        Boolean isSuccess = service.removeByParams(userRoleRemoveReqDTO);
        responseMessage(isSuccess ? ModelResult.CODE_200 : ModelResult.CODE_500, isSuccess ? ModelResult.SUCCESS : ModelResult.FAIL);
    }
}

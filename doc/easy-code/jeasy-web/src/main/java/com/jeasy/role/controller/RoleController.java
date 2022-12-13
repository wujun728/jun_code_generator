package com.jeasy.role.controller;

import com.jeasy.base.dto.PageDTO;
import com.jeasy.base.web.controller.BaseController;
import com.jeasy.base.web.dto.ModelResult;
import com.jeasy.base.web.resolver.FromJson;
import com.jeasy.common.Func;
import com.jeasy.doc.annotation.InitField;
import com.jeasy.doc.annotation.MethodDoc;
import com.jeasy.doc.annotation.StatusEnum;
import com.jeasy.role.dto.*;
import com.jeasy.role.service.RoleService;
import com.jeasy.validate.handler.ValidateNotNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 角色 Controller
 *
 * @author taomk
 * @version 1.0
 * @since 2017/11/08 16:33
 */
@Slf4j
@Controller
public class RoleController extends BaseController<RoleService> {

    @MethodDoc(lists = RoleListResDTO.class, desc = {"PC端", "角色-列表", "列表"}, status = StatusEnum.DONE, author = "taomk", finishTime = "2017/05/20 11:22")
    @RequestMapping(value = "role/list", method = {RequestMethod.GET})
    @ResponseBody
    public void list(final @FromJson RoleListReqDTO roleListReqDTO) {
        List<RoleListResDTO> items = service.list(roleListReqDTO);
        responseList(ModelResult.CODE_200, ModelResult.SUCCESS, items);
    }

    @RequiresPermissions(value = {"YHGL:JSGL:QXPZ"})
    @MethodDoc(lists = RoleListPermissionResDTO.class, desc = {"PC端", "角色-列表", "列表"}, status = StatusEnum.DONE, author = "taomk", finishTime = "2017/05/20 11:22")
    @RequestMapping(value = "role/listPermission", method = {RequestMethod.GET})
    @ResponseBody
    public void listPermission(final @FromJson RoleListPermissionReqDTO roleListPermissionReqDTO) {
        List<RoleListPermissionResDTO> items = service.listPermission(roleListPermissionReqDTO);
        responseList(ModelResult.CODE_200, ModelResult.SUCCESS, items);
    }

    @MethodDoc(lists = RoleListResDTO.class, desc = {"PC端", "角色-列表", "列表1.1.0"}, status = StatusEnum.DONE, author = "taomk", finishTime = "2017/05/20 11:22")
    @RequestMapping(value = "role/list", headers = {"version=1.1.0"}, method = {RequestMethod.GET})
    @ResponseBody
    public void listByVersion1(final @FromJson RoleListReqDTO roleListReqDTO) {
        List<RoleListResDTO> items = service.listByVersion1(roleListReqDTO);
        responseList(ModelResult.CODE_200, ModelResult.SUCCESS, items);
    }

    @MethodDoc(lists = RoleListResDTO.class, desc = {"PC端", "角色-列表", "列表1.2.0"}, status = StatusEnum.DONE, author = "taomk", finishTime = "2017/05/20 11:22")
    @RequestMapping(value = "role/list", headers = {"version=1.2.0", "platform=APP"}, method = {RequestMethod.GET})
    @ResponseBody
    public void listByVersion2(final @FromJson RoleListReqDTO roleListReqDTO) {
        List<RoleListResDTO> items = service.listByVersion2(roleListReqDTO);
        responseList(ModelResult.CODE_200, ModelResult.SUCCESS, items);
    }

    @MethodDoc(lists = RoleListResDTO.class, desc = {"PC端", "角色-列表", "列表1.3.0"}, status = StatusEnum.DONE, author = "taomk", finishTime = "2017/05/20 11:22")
    @RequestMapping(value = "role/list", headers = {"version=1.3.0", "platform=APP", "device=IOS"}, method = {RequestMethod.GET})
    @ResponseBody
    public void listByVersion3(final @FromJson RoleListReqDTO roleListReqDTO) {
        List<RoleListResDTO> items = service.listByVersion3(roleListReqDTO);
        responseList(ModelResult.CODE_200, ModelResult.SUCCESS, items);
    }

    @MethodDoc(entity = RoleListResDTO.class, desc = {"PC端", "角色-列表", "First查询"}, status = StatusEnum.DONE, author = "taomk", finishTime = "2017/05/20 11:22")
    @RequestMapping(value = "role/listOne", method = {RequestMethod.GET})
    @ResponseBody
    public void listOne(final @FromJson RoleListReqDTO roleListReqDTO) {
        RoleListResDTO entity = service.listOne(roleListReqDTO);
        responseEntity(ModelResult.CODE_200, ModelResult.SUCCESS, entity);
    }

    @RequiresPermissions(value = {"YHGL:JSGL:CX"})
    @MethodDoc(pages = RolePageResDTO.class, desc = {"PC端", "角色-分页", "分页"}, status = StatusEnum.DONE, author = "taomk", finishTime = "2017/05/20 11:22")
    @RequestMapping(value = "role/page", method = {RequestMethod.GET})
    @ResponseBody
    public void page(final @FromJson RolePageReqDTO rolePageReqDTO,
                     final @InitField(name = "pageNo", value = "1", desc = "当前页码") @FromJson Integer pageNo,
                     final @InitField(name = "pageSize", value = "10", desc = "每页大小") @FromJson Integer pageSize) {
        int current = Func.isNullOrZero(pageNo) ? 1 : pageNo;
        int size = Func.isNullOrZero(pageSize) ? 10 : pageSize;

        PageDTO<RolePageResDTO> rolePage = service.pagination(rolePageReqDTO, current, size);
        responsePage(ModelResult.CODE_200, ModelResult.SUCCESS, rolePage.getTotal(), rolePage.getRecords(), size, current);
    }

    @RequiresPermissions(value = {"YHGL:JSGL:XZ"})
    @MethodDoc(desc = {"PC端", "角色-新增", "新增"}, status = StatusEnum.DONE, author = "taomk", finishTime = "2017/05/20 11:22")
    @RequestMapping(value = "role/add", method = {RequestMethod.POST})
    @ResponseBody
    public void add(final @FromJson RoleAddReqDTO roleAddReqDTO) {
        Boolean isSuccess = service.add(roleAddReqDTO);
        responseMessage(isSuccess ? ModelResult.CODE_200 : ModelResult.CODE_500, isSuccess ? ModelResult.SUCCESS : ModelResult.FAIL);
    }

    @MethodDoc(desc = {"PC端", "角色-新增", "新增(所有字段)"}, status = StatusEnum.DONE, author = "taomk", finishTime = "2017/05/20 11:22")
    @RequestMapping(value = "role/addAllColumn", method = {RequestMethod.POST})
    @ResponseBody
    public void addAllColumn(final @FromJson RoleAddReqDTO roleAddReqDTO) {
        Boolean isSuccess = service.addAllColumn(roleAddReqDTO);
        responseMessage(isSuccess ? ModelResult.CODE_200 : ModelResult.CODE_500, isSuccess ? ModelResult.SUCCESS : ModelResult.FAIL);
    }

    @MethodDoc(desc = {"PC端", "角色-新增", "批量新增(所有字段)"}, status = StatusEnum.DONE, author = "taomk", finishTime = "2017/05/20 11:22")
    @RequestMapping(value = "role/addBatchAllColumn", method = {RequestMethod.POST})
    @ResponseBody
    public void addBatchAllColumn(final @FromJson List<RoleAddReqDTO> roleAddReqDTOList) {
        Boolean isSuccess = service.addBatchAllColumn(roleAddReqDTOList);
        responseMessage(isSuccess ? ModelResult.CODE_200 : ModelResult.CODE_500, isSuccess ? ModelResult.SUCCESS : ModelResult.FAIL);
    }

    @RequiresPermissions(value = {"YHGL:JSGL:CK"})
    @MethodDoc(entity = RoleShowResDTO.class, desc = {"PC端", "角色-详情", "详情"}, status = StatusEnum.DONE, author = "taomk", finishTime = "2017/05/20 11:22")
    @RequestMapping(value = "role/show", method = {RequestMethod.GET})
    @ResponseBody
    public void show(final @InitField(name = "id", value = "10", desc = "主键ID") @FromJson @ValidateNotNull(message = "ID不允许为空") Long id) {
        RoleShowResDTO entity = service.show(id);
        responseEntity(ModelResult.CODE_200, ModelResult.SUCCESS, entity);
    }

    @MethodDoc(entity = RoleShowResDTO.class, desc = {"PC端", "角色-详情", "详情(批量)"}, status = StatusEnum.DONE, author = "taomk", finishTime = "2017/05/20 11:22")
    @RequestMapping(value = "role/showByIds", method = {RequestMethod.GET})
    @ResponseBody
    public void showByIds(final @InitField(name = "ids", value = "[\"1001\",\"1002\",\"1003\",\"1004\",\"1005\"]", desc = "主键ID集合") @FromJson List<Long> ids) {
        List<RoleShowResDTO> items = service.showByIds(ids);
        responseList(ModelResult.CODE_200, ModelResult.SUCCESS, items);
    }

    @RequiresPermissions(value = {"YHGL:JSGL:BJ"})
    @MethodDoc(desc = {"PC端", "角色-更新", "更新"}, status = StatusEnum.DONE, author = "taomk", finishTime = "2017/05/20 11:22")
    @RequestMapping(value = "role/modify", method = {RequestMethod.POST})
    @ResponseBody
    public void modify(final @FromJson RoleModifyReqDTO roleModifyReqDTO) {
        Boolean isSuccess = service.modify(roleModifyReqDTO);
        responseMessage(isSuccess ? ModelResult.CODE_200 : ModelResult.CODE_500, isSuccess ? ModelResult.SUCCESS : ModelResult.FAIL);
    }

    @RequiresPermissions(value = {"YHGL:JSGL:QXPZ"})
    @MethodDoc(desc = {"PC端", "角色-更新", "更新"}, status = StatusEnum.DONE, author = "taomk", finishTime = "2017/05/20 11:22")
    @RequestMapping(value = "role/modifyPermission", method = {RequestMethod.POST})
    @ResponseBody
    public void modifyPermission(final @FromJson RoleModifyPermissionReqDTO roleModifyPermissionReqDTO) {
        Boolean isSuccess = service.modifyPermission(roleModifyPermissionReqDTO);
        responseMessage(isSuccess ? ModelResult.CODE_200 : ModelResult.CODE_500, isSuccess ? ModelResult.SUCCESS : ModelResult.FAIL);
    }

    @MethodDoc(desc = {"PC端", "角色-更新", "更新(所有字段)"}, status = StatusEnum.DONE, author = "taomk", finishTime = "2017/05/20 11:22")
    @RequestMapping(value = "role/modifySelective", method = {RequestMethod.POST})
    @ResponseBody
    public void modifyAllColumn(final @FromJson RoleModifyReqDTO roleModifyReqDTO) {
        Boolean isSuccess = service.modifyAllColumn(roleModifyReqDTO);
        responseMessage(isSuccess ? ModelResult.CODE_200 : ModelResult.CODE_500, isSuccess ? ModelResult.SUCCESS : ModelResult.FAIL);
    }

    @RequiresPermissions(value = {"YHGL:JSGL:SC"})
    @MethodDoc(desc = {"PC端", "角色-删除", "删除"}, status = StatusEnum.DONE, author = "taomk", finishTime = "2017/05/20 11:22")
    @RequestMapping(value = "role/remove", method = {RequestMethod.POST})
    @ResponseBody
    public void remove(final @InitField(name = "id", value = "10", desc = "主键ID") @FromJson @ValidateNotNull(message = "ID不允许为空") Long id) {
        Boolean isSuccess = service.remove(id);
        responseMessage(isSuccess ? ModelResult.CODE_200 : ModelResult.CODE_500, isSuccess ? ModelResult.SUCCESS : ModelResult.FAIL);
    }

    @MethodDoc(desc = {"PC端", "角色-删除", "删除(批量)"}, status = StatusEnum.DONE, author = "taomk", finishTime = "2017/05/20 11:22")
    @RequestMapping(value = "role/removeBatch", method = {RequestMethod.POST})
    @ResponseBody
    public void removeBatch(final @InitField(name = "ids", value = "[\"1001\",\"1002\",\"1003\",\"1004\",\"1005\"]", desc = "主键ID集合") @FromJson List<Long> ids) {
        Boolean isSuccess = service.removeBatch(ids);
        responseMessage(isSuccess ? ModelResult.CODE_200 : ModelResult.CODE_500, isSuccess ? ModelResult.SUCCESS : ModelResult.FAIL);
    }

    @MethodDoc(desc = {"PC端", "角色-删除", "删除(参数)"}, status = StatusEnum.DONE, author = "taomk", finishTime = "2017/05/20 11:22")
    @RequestMapping(value = "role/removeByParams", method = {RequestMethod.POST})
    @ResponseBody
    public void removeByParams(final @FromJson RoleRemoveReqDTO roleRemoveReqDTO) {
        Boolean isSuccess = service.removeByParams(roleRemoveReqDTO);
        responseMessage(isSuccess ? ModelResult.CODE_200 : ModelResult.CODE_500, isSuccess ? ModelResult.SUCCESS : ModelResult.FAIL);
    }
}

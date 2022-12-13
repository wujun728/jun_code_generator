package com.jeasy.user.controller;

import com.jeasy.base.dto.PageDTO;
import com.jeasy.base.web.controller.BaseController;
import com.jeasy.base.web.dto.ModelResult;
import com.jeasy.base.web.resolver.FromJson;
import com.jeasy.common.Func;
import com.jeasy.doc.annotation.InitField;
import com.jeasy.doc.annotation.MethodDoc;
import com.jeasy.doc.annotation.StatusEnum;
import com.jeasy.user.dto.*;
import com.jeasy.user.service.UserService;
import com.jeasy.validate.handler.ValidateNotNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 用户 Controller
 *
 * @author taomk
 * @version 1.0
 * @since 2017/11/08 16:33
 */
@Slf4j
@Controller
public class UserController extends BaseController<UserService> {

    @MethodDoc(lists = UserListResDTO.class, desc = {"PC端", "用户-列表", "列表"}, status = StatusEnum.DONE, author = "taomk", finishTime = "2017/05/20 11:22")
    @RequestMapping(value = "user/list", method = {RequestMethod.GET})
    @ResponseBody
    public void list(final @FromJson UserListReqDTO userListReqDTO) {
        List<UserListResDTO> items = service.list(userListReqDTO);
        responseList(ModelResult.CODE_200, ModelResult.SUCCESS, items);
    }

    @MethodDoc(lists = UserListResDTO.class, desc = {"PC端", "用户-列表", "列表1.1.0"}, status = StatusEnum.DONE, author = "taomk", finishTime = "2017/05/20 11:22")
    @RequestMapping(value = "user/list", headers = {"version=1.1.0"}, method = {RequestMethod.GET})
    @ResponseBody
    public void listByVersion1(final @FromJson UserListReqDTO userListReqDTO) {
        List<UserListResDTO> items = service.listByVersion1(userListReqDTO);
        responseList(ModelResult.CODE_200, ModelResult.SUCCESS, items);
    }

    @MethodDoc(lists = UserListResDTO.class, desc = {"PC端", "用户-列表", "列表1.2.0"}, status = StatusEnum.DONE, author = "taomk", finishTime = "2017/05/20 11:22")
    @RequestMapping(value = "user/list", headers = {"version=1.2.0", "platform=APP"}, method = {RequestMethod.GET})
    @ResponseBody
    public void listByVersion2(final @FromJson UserListReqDTO userListReqDTO) {
        List<UserListResDTO> items = service.listByVersion2(userListReqDTO);
        responseList(ModelResult.CODE_200, ModelResult.SUCCESS, items);
    }

    @MethodDoc(lists = UserListResDTO.class, desc = {"PC端", "用户-列表", "列表1.3.0"}, status = StatusEnum.DONE, author = "taomk", finishTime = "2017/05/20 11:22")
    @RequestMapping(value = "user/list", headers = {"version=1.3.0", "platform=APP", "device=IOS"}, method = {RequestMethod.GET})
    @ResponseBody
    public void listByVersion3(final @FromJson UserListReqDTO userListReqDTO) {
        List<UserListResDTO> items = service.listByVersion3(userListReqDTO);
        responseList(ModelResult.CODE_200, ModelResult.SUCCESS, items);
    }

    @MethodDoc(entity = UserListResDTO.class, desc = {"PC端", "用户-列表", "First查询"}, status = StatusEnum.DONE, author = "taomk", finishTime = "2017/05/20 11:22")
    @RequestMapping(value = "user/listOne", method = {RequestMethod.GET})
    @ResponseBody
    public void listOne(final @FromJson UserListReqDTO userListReqDTO) {
        UserListResDTO entity = service.listOne(userListReqDTO);
        responseEntity(ModelResult.CODE_200, ModelResult.SUCCESS, entity);
    }

    @RequiresPermissions(value = {"YHGL:RYGL:CX"})
    @MethodDoc(pages = UserPageResDTO.class, desc = {"PC端", "用户-分页", "分页"}, status = StatusEnum.DONE, author = "taomk", finishTime = "2017/05/20 11:22")
    @RequestMapping(value = "user/page", method = {RequestMethod.GET})
    @ResponseBody
    public void page(final @FromJson UserPageReqDTO userPageReqDTO,
                     final @InitField(name = "pageNo", value = "1", desc = "当前页码") @FromJson Integer pageNo,
                     final @InitField(name = "pageSize", value = "10", desc = "每页大小") @FromJson Integer pageSize) {
        int current = Func.isNullOrZero(pageNo) ? 1 : pageNo;
        int size = Func.isNullOrZero(pageSize) ? 10 : pageSize;

        PageDTO<UserPageResDTO> userPage = service.pagination(userPageReqDTO, current, size);
        responsePage(ModelResult.CODE_200, ModelResult.SUCCESS, userPage.getTotal(), userPage.getRecords(), size, current);
    }

    @RequiresPermissions(value = {"YHGL:RYGL:JSPZ"})
    @MethodDoc(pages = UserPageRoleResDTO.class, desc = {"PC端", "用户-分页", "分页"}, status = StatusEnum.DONE, author = "taomk", finishTime = "2017/05/20 11:22")
    @RequestMapping(value = "user/pageRole", method = {RequestMethod.GET})
    @ResponseBody
    public void pageRole(final @FromJson UserPageRoleReqDTO userPageRoleReqDTO,
                         final @InitField(name = "pageNo", value = "1", desc = "当前页码") @FromJson Integer pageNo,
                         final @InitField(name = "pageSize", value = "10", desc = "每页大小") @FromJson Integer pageSize) {
        int current = Func.isNullOrZero(pageNo) ? 1 : pageNo;
        int size = Func.isNullOrZero(pageSize) ? 10 : pageSize;

        PageDTO<UserPageRoleResDTO> userPage = service.pagination(userPageRoleReqDTO, current, size);
        responsePage(ModelResult.CODE_200, ModelResult.SUCCESS, userPage.getTotal(), userPage.getRecords(), size, current);
    }

    @RequiresPermissions(value = {"YHGL:RYGL:JSPZ"})
    @MethodDoc(pages = UserListRoleResDTO.class, desc = {"PC端", "用户-分页", "分页"}, status = StatusEnum.DONE, author = "taomk", finishTime = "2017/05/20 11:22")
    @RequestMapping(value = "user/listRole", method = {RequestMethod.GET})
    @ResponseBody
    public void listRole(final @FromJson UserListRoleReqDTO userListRoleReqDTO) {
        List<UserListRoleResDTO> items = service.listRole(userListRoleReqDTO);
        responseList(ModelResult.CODE_200, ModelResult.SUCCESS, items);
    }

    @RequiresPermissions(value = {"YHGL:RYGL:XZ"})
    @MethodDoc(desc = {"PC端", "用户-新增", "新增"}, status = StatusEnum.DONE, author = "taomk", finishTime = "2017/05/20 11:22")
    @RequestMapping(value = "user/add", method = {RequestMethod.POST})
    @ResponseBody
    public void add(final @FromJson UserAddReqDTO userAddReqDTO) {
        Boolean isSuccess = service.add(userAddReqDTO);
        responseMessage(isSuccess ? ModelResult.CODE_200 : ModelResult.CODE_500, isSuccess ? ModelResult.SUCCESS : ModelResult.FAIL);
    }

    @MethodDoc(desc = {"PC端", "用户-新增", "新增(所有字段)"}, status = StatusEnum.DONE, author = "taomk", finishTime = "2017/05/20 11:22")
    @RequestMapping(value = "user/addAllColumn", method = {RequestMethod.POST})
    @ResponseBody
    public void addAllColumn(final @FromJson UserAddReqDTO userAddReqDTO) {
        Boolean isSuccess = service.addAllColumn(userAddReqDTO);
        responseMessage(isSuccess ? ModelResult.CODE_200 : ModelResult.CODE_500, isSuccess ? ModelResult.SUCCESS : ModelResult.FAIL);
    }

    @MethodDoc(desc = {"PC端", "用户-新增", "批量新增(所有字段)"}, status = StatusEnum.DONE, author = "taomk", finishTime = "2017/05/20 11:22")
    @RequestMapping(value = "user/addBatchAllColumn", method = {RequestMethod.POST})
    @ResponseBody
    public void addBatchAllColumn(final @FromJson List<UserAddReqDTO> userAddReqDTOList) {
        Boolean isSuccess = service.addBatchAllColumn(userAddReqDTOList);
        responseMessage(isSuccess ? ModelResult.CODE_200 : ModelResult.CODE_500, isSuccess ? ModelResult.SUCCESS : ModelResult.FAIL);
    }

    @RequiresPermissions(value = {"YHGL:RYGL:CK"})
    @MethodDoc(entity = UserShowResDTO.class, desc = {"PC端", "用户-详情", "详情"}, status = StatusEnum.DONE, author = "taomk", finishTime = "2017/05/20 11:22")
    @RequestMapping(value = "user/show", method = {RequestMethod.GET})
    @ResponseBody
    public void show(final @InitField(name = "id", value = "10", desc = "主键ID") @FromJson @ValidateNotNull(message = "ID不允许为空") Long id) {
        UserShowResDTO entity = service.show(id);
        responseEntity(ModelResult.CODE_200, ModelResult.SUCCESS, entity);
    }

    @RequiresPermissions(value = {"YHGL:RYGL:JGPZ"})
    @MethodDoc(entity = UserListOrganizationResDTO.class, desc = {"PC端", "用户-详情", "详情"}, status = StatusEnum.DONE, author = "taomk", finishTime = "2017/05/20 11:22")
    @RequestMapping(value = "user/listOrganization", method = {RequestMethod.GET})
    @ResponseBody
    public void listOrganization(final @FromJson UserListOrganizationReqDTO userListOrganizationReqDTO) {
        List<UserListOrganizationResDTO> items = service.listOrganization(userListOrganizationReqDTO);
        responseList(ModelResult.CODE_200, ModelResult.SUCCESS, items);
    }

    @MethodDoc(entity = UserShowResDTO.class, desc = {"PC端", "用户-详情", "详情(批量)"}, status = StatusEnum.DONE, author = "taomk", finishTime = "2017/05/20 11:22")
    @RequestMapping(value = "user/showByIds", method = {RequestMethod.GET})
    @ResponseBody
    public void showByIds(final @InitField(name = "ids", value = "[\"1001\",\"1002\",\"1003\",\"1004\",\"1005\"]", desc = "主键ID集合") @FromJson List<Long> ids) {
        List<UserShowResDTO> items = service.showByIds(ids);
        responseList(ModelResult.CODE_200, ModelResult.SUCCESS, items);
    }

    @RequiresPermissions(value = {"YHGL:RYGL:BJ"})
    @MethodDoc(desc = {"PC端", "用户-更新", "更新"}, status = StatusEnum.DONE, author = "taomk", finishTime = "2017/05/20 11:22")
    @RequestMapping(value = "user/modify", method = {RequestMethod.POST})
    @ResponseBody
    public void modify(final @FromJson UserModifyReqDTO userModifyReqDTO) {
        Boolean isSuccess = service.modify(userModifyReqDTO);
        responseMessage(isSuccess ? ModelResult.CODE_200 : ModelResult.CODE_500, isSuccess ? ModelResult.SUCCESS : ModelResult.FAIL);
    }

    @RequiresPermissions(value = {"YHGL:RYGL:JSPZ"})
    @MethodDoc(desc = {"PC端", "用户-更新", "更新"}, status = StatusEnum.DONE, author = "taomk", finishTime = "2017/05/20 11:22")
    @RequestMapping(value = "user/modifyRole", method = {RequestMethod.POST})
    @ResponseBody
    public void modifyRole(final @FromJson UserModifyRoleReqDTO userModifyRoleReqDTO) {
        Boolean isSuccess = service.modifyRole(userModifyRoleReqDTO);
        responseMessage(isSuccess ? ModelResult.CODE_200 : ModelResult.CODE_500, isSuccess ? ModelResult.SUCCESS : ModelResult.FAIL);
    }

    @RequiresPermissions(value = {"YHGL:RYGL:JGPZ"})
    @MethodDoc(desc = {"PC端", "用户-更新", "更新"}, status = StatusEnum.DONE, author = "taomk", finishTime = "2017/05/20 11:22")
    @RequestMapping(value = "user/modifyOrganization", method = {RequestMethod.POST})
    @ResponseBody
    public void modifyOrganization(final @FromJson UserModifyOrganizationReqDTO userModifyOrganizationReqDTO) {
        Boolean isSuccess = service.modifyOrganization(userModifyOrganizationReqDTO);
        responseMessage(isSuccess ? ModelResult.CODE_200 : ModelResult.CODE_500, isSuccess ? ModelResult.SUCCESS : ModelResult.FAIL);
    }

    @MethodDoc(desc = {"PC端", "用户-更新", "更新(所有字段)"}, status = StatusEnum.DONE, author = "taomk", finishTime = "2017/05/20 11:22")
    @RequestMapping(value = "user/modifySelective", method = {RequestMethod.POST})
    @ResponseBody
    public void modifyAllColumn(final @FromJson UserModifyReqDTO userModifyReqDTO) {
        Boolean isSuccess = service.modifyAllColumn(userModifyReqDTO);
        responseMessage(isSuccess ? ModelResult.CODE_200 : ModelResult.CODE_500, isSuccess ? ModelResult.SUCCESS : ModelResult.FAIL);
    }

    @RequiresPermissions(value = {"YHGL:RYGL:SC"})
    @MethodDoc(desc = {"PC端", "用户-删除", "删除"}, status = StatusEnum.DONE, author = "taomk", finishTime = "2017/05/20 11:22")
    @RequestMapping(value = "user/remove", method = {RequestMethod.POST})
    @ResponseBody
    public void remove(final @InitField(name = "id", value = "10", desc = "主键ID") @FromJson @ValidateNotNull(message = "ID不允许为空") Long id) {
        Boolean isSuccess = service.remove(id);
        responseMessage(isSuccess ? ModelResult.CODE_200 : ModelResult.CODE_500, isSuccess ? ModelResult.SUCCESS : ModelResult.FAIL);
    }

    @MethodDoc(desc = {"PC端", "用户-删除", "删除(批量)"}, status = StatusEnum.DONE, author = "taomk", finishTime = "2017/05/20 11:22")
    @RequestMapping(value = "user/removeBatch", method = {RequestMethod.POST})
    @ResponseBody
    public void removeBatch(final @InitField(name = "ids", value = "[\"1001\",\"1002\",\"1003\",\"1004\",\"1005\"]", desc = "主键ID集合") @FromJson List<Long> ids) {
        Boolean isSuccess = service.removeBatch(ids);
        responseMessage(isSuccess ? ModelResult.CODE_200 : ModelResult.CODE_500, isSuccess ? ModelResult.SUCCESS : ModelResult.FAIL);
    }

    @MethodDoc(desc = {"PC端", "用户-删除", "删除(参数)"}, status = StatusEnum.DONE, author = "taomk", finishTime = "2017/05/20 11:22")
    @RequestMapping(value = "user/removeByParams", method = {RequestMethod.POST})
    @ResponseBody
    public void removeByParams(final @FromJson UserRemoveReqDTO userRemoveReqDTO) {
        Boolean isSuccess = service.removeByParams(userRemoveReqDTO);
        responseMessage(isSuccess ? ModelResult.CODE_200 : ModelResult.CODE_500, isSuccess ? ModelResult.SUCCESS : ModelResult.FAIL);
    }
}

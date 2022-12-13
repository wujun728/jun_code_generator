package com.jeasy.resource.controller;

import com.jeasy.base.web.controller.BaseController;
import com.jeasy.base.web.dto.ModelResult;
import com.jeasy.base.web.resolver.FromJson;
import com.jeasy.doc.annotation.InitField;
import com.jeasy.doc.annotation.MethodDoc;
import com.jeasy.doc.annotation.StatusEnum;
import com.jeasy.resource.dto.*;
import com.jeasy.resource.service.ResourceService;
import com.jeasy.validate.handler.ValidateNotNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 菜单 Controller
 *
 * @author taomk
 * @version 1.0
 * @since 2017/11/08 16:33
 */
@Slf4j
@Controller
public class ResourceController extends BaseController<ResourceService> {

    @RequiresPermissions(value = {"YHGL:CDZY:CX"})
    @MethodDoc(lists = ResourceListResDTO.class, desc = {"PC端", "菜单-列表", "列表"}, status = StatusEnum.DONE, author = "taomk", finishTime = "2017/05/20 11:22")
    @RequestMapping(value = "resource/list", method = {RequestMethod.GET})
    @ResponseBody
    public void list(final @FromJson ResourceListReqDTO resourceListReqDTO) {
        List<ResourceListResDTO> items = service.list(resourceListReqDTO);
        responseList(ModelResult.CODE_200, ModelResult.SUCCESS, items);
    }

    @RequiresPermissions(value = {"YHGL:CDZY:XZ"})
    @MethodDoc(desc = {"PC端", "菜单-新增", "新增"}, status = StatusEnum.DONE, author = "taomk", finishTime = "2017/05/20 11:22")
    @RequestMapping(value = "resource/add", method = {RequestMethod.POST})
    @ResponseBody
    public void add(final @FromJson ResourceAddReqDTO resourceAddReqDTO) {
        Boolean isSuccess = service.add(resourceAddReqDTO);
        responseMessage(isSuccess ? ModelResult.CODE_200 : ModelResult.CODE_500, isSuccess ? ModelResult.SUCCESS : ModelResult.FAIL);
    }

    @RequiresPermissions(value = {"YHGL:CDZY:CK"})
    @MethodDoc(entity = ResourceShowResDTO.class, desc = {"PC端", "菜单-详情", "详情"}, status = StatusEnum.DONE, author = "taomk", finishTime = "2017/05/20 11:22")
    @RequestMapping(value = "resource/show", method = {RequestMethod.GET})
    @ResponseBody
    public void show(final @InitField(name = "id", value = "10", desc = "主键ID") @FromJson @ValidateNotNull(message = "ID不允许为空") Long id) {
        ResourceShowResDTO entity = service.show(id);
        responseEntity(ModelResult.CODE_200, ModelResult.SUCCESS, entity);
    }

    @RequiresPermissions(value = {"YHGL:CDZY:BJ"})
    @MethodDoc(desc = {"PC端", "菜单-更新", "更新"}, status = StatusEnum.DONE, author = "taomk", finishTime = "2017/05/20 11:22")
    @RequestMapping(value = "resource/modify", method = {RequestMethod.POST})
    @ResponseBody
    public void modify(final @FromJson ResourceModifyReqDTO resourceModifyReqDTO) {
        Boolean isSuccess = service.modify(resourceModifyReqDTO);
        responseMessage(isSuccess ? ModelResult.CODE_200 : ModelResult.CODE_500, isSuccess ? ModelResult.SUCCESS : ModelResult.FAIL);
    }

    @RequiresPermissions(value = {"YHGL:CDZY:SC"})
    @MethodDoc(desc = {"PC端", "菜单-删除", "删除"}, status = StatusEnum.DONE, author = "taomk", finishTime = "2017/05/20 11:22")
    @RequestMapping(value = "resource/remove", method = {RequestMethod.POST})
    @ResponseBody
    public void remove(final @InitField(name = "id", value = "10", desc = "主键ID") @FromJson @ValidateNotNull(message = "ID不允许为空") Long id) {
        Boolean isSuccess = service.remove(id);
        responseMessage(isSuccess ? ModelResult.CODE_200 : ModelResult.CODE_500, isSuccess ? ModelResult.SUCCESS : ModelResult.FAIL);
    }

    @MethodDoc(lists = UserMenuResourceDTO.class, desc = {"5.登录管理", "1.权限管理", "1.权限资源-获取菜单资源集合"}, status = StatusEnum.DONE, author = "taomk", finishTime = "2017/03/28 15:13")
    @RequestMapping(value = "resource/listUserMenu", method = {RequestMethod.GET})
    @ResponseBody
    public void listUserMenu() {
        List<UserMenuResourceDTO> items = service.listUserMenu();
        responseList(ModelResult.CODE_200, ModelResult.SUCCESS, items);
    }

    @MethodDoc(lists = UserMenuOperationDTO.class, desc = {"5.登录管理", "1.权限管理", "2.权限资源-根据菜单ID,获取菜单页面操作集合"}, status = StatusEnum.DONE, author = "taomk", finishTime = "2017/03/28 15:13")
    @RequestMapping(value = "resource/listUserMenuOperation", method = {RequestMethod.GET})
    @ResponseBody
    public void listUserMenuOperation(final @InitField(name = "menuPath", value = "/XXXX", desc = "菜单路径") @FromJson @ValidateNotNull(message = "菜单路径不允许为空") String menuPath) {
        List<UserMenuOperationDTO> items = service.listUserMenuOperation(menuPath);
        responseList(ModelResult.CODE_200, ModelResult.SUCCESS, items);
    }
}

package com.jeasy.organization.controller;

import com.jeasy.base.dto.PageDTO;
import com.jeasy.base.web.controller.BaseController;
import com.jeasy.base.web.dto.ModelResult;
import com.jeasy.base.web.resolver.FromJson;
import com.jeasy.common.Func;
import com.jeasy.doc.annotation.InitField;
import com.jeasy.doc.annotation.MethodDoc;
import com.jeasy.doc.annotation.StatusEnum;
import com.jeasy.organization.dto.*;
import com.jeasy.organization.service.OrganizationService;
import com.jeasy.validate.handler.ValidateNotNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 机构 Controller
 *
 * @author taomk
 * @version 1.0
 * @since 2017/11/08 16:33
 */
@Slf4j
@Controller
public class OrganizationController extends BaseController<OrganizationService> {

    @RequiresPermissions(value = {"YHGL:ZZJG:CX"})
    @MethodDoc(lists = OrganizationListResDTO.class, desc = {"PC端", "机构-列表", "列表"}, status = StatusEnum.DONE, author = "taomk", finishTime = "2017/05/20 11:22")
    @RequestMapping(value = "organization/list", method = {RequestMethod.GET})
    @ResponseBody
    public void list(final @FromJson OrganizationListReqDTO organizationListReqDTO) {
        List<OrganizationListResDTO> items = service.list(organizationListReqDTO);
        responseList(ModelResult.CODE_200, ModelResult.SUCCESS, items);
    }

    @MethodDoc(pages = OrganizationPageResDTO.class, desc = {"PC端", "机构-分页", "分页"}, status = StatusEnum.DONE, author = "taomk", finishTime = "2017/05/20 11:22")
    @RequestMapping(value = "organization/page", method = {RequestMethod.GET})
    @ResponseBody
    public void page(final @FromJson OrganizationPageReqDTO organizationPageReqDTO,
                     final @InitField(name = "pageNo", value = "1", desc = "当前页码") @FromJson Integer pageNo,
                     final @InitField(name = "pageSize", value = "10", desc = "每页大小") @FromJson Integer pageSize) {
        int current = Func.isNullOrZero(pageNo) ? 1 : pageNo;
        int size = Func.isNullOrZero(pageSize) ? 10 : pageSize;

        PageDTO<OrganizationPageResDTO> organizationPage = service.pagination(organizationPageReqDTO, current, size);
        responsePage(ModelResult.CODE_200, ModelResult.SUCCESS, organizationPage.getTotal(), organizationPage.getRecords(), size, current);
    }

    @RequiresPermissions(value = {"YHGL:ZZJG:XZ"})
    @MethodDoc(desc = {"PC端", "机构-新增", "新增"}, status = StatusEnum.DONE, author = "taomk", finishTime = "2017/05/20 11:22")
    @RequestMapping(value = "organization/add", method = {RequestMethod.POST})
    @ResponseBody
    public void add(final @FromJson OrganizationAddReqDTO organizationAddReqDTO) {
        Boolean isSuccess = service.add(organizationAddReqDTO);
        responseMessage(isSuccess ? ModelResult.CODE_200 : ModelResult.CODE_500, isSuccess ? ModelResult.SUCCESS : ModelResult.FAIL);
    }

    @RequiresPermissions(value = {"YHGL:ZZJG:CK"})
    @MethodDoc(entity = OrganizationShowResDTO.class, desc = {"PC端", "机构-详情", "详情"}, status = StatusEnum.DONE, author = "taomk", finishTime = "2017/05/20 11:22")
    @RequestMapping(value = "organization/show", method = {RequestMethod.GET})
    @ResponseBody
    public void show(final @InitField(name = "id", value = "10", desc = "主键ID") @FromJson @ValidateNotNull(message = "ID不允许为空") Long id) {
        OrganizationShowResDTO entity = service.show(id);
        responseEntity(ModelResult.CODE_200, ModelResult.SUCCESS, entity);
    }

    @RequiresPermissions(value = {"YHGL:ZZJG:BJ"})
    @MethodDoc(desc = {"PC端", "机构-更新", "更新"}, status = StatusEnum.DONE, author = "taomk", finishTime = "2017/05/20 11:22")
    @RequestMapping(value = "organization/modify", method = {RequestMethod.POST})
    @ResponseBody
    public void modify(final @FromJson OrganizationModifyReqDTO organizationModifyReqDTO) {
        Boolean isSuccess = service.modify(organizationModifyReqDTO);
        responseMessage(isSuccess ? ModelResult.CODE_200 : ModelResult.CODE_500, isSuccess ? ModelResult.SUCCESS : ModelResult.FAIL);
    }

    @RequiresPermissions(value = {"YHGL:ZZJG:SC"})
    @MethodDoc(desc = {"PC端", "机构-删除", "删除"}, status = StatusEnum.DONE, author = "taomk", finishTime = "2017/05/20 11:22")
    @RequestMapping(value = "organization/remove", method = {RequestMethod.POST})
    @ResponseBody
    public void remove(final @InitField(name = "id", value = "10", desc = "主键ID") @FromJson @ValidateNotNull(message = "ID不允许为空") Long id) {
        Boolean isSuccess = service.remove(id);
        responseMessage(isSuccess ? ModelResult.CODE_200 : ModelResult.CODE_500, isSuccess ? ModelResult.SUCCESS : ModelResult.FAIL);
    }
}

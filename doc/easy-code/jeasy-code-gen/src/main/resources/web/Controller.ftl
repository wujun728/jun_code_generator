package ${conf.basePackage}.${table.lowerCamelName}.controller;

import ${conf.basePackage}.base.dto.PageDTO;
import ${conf.basePackage}.base.web.controller.BaseController;
import ${conf.basePackage}.base.web.dto.ModelResult;
import ${conf.basePackage}.base.web.resolver.FromJson;
import ${conf.basePackage}.common.Func;
import ${conf.basePackage}.doc.annotation.InitField;
import ${conf.basePackage}.doc.annotation.MethodDoc;
import ${conf.basePackage}.doc.annotation.StatusEnum;
import ${conf.basePackage}.${table.lowerCamelName}.dto.*;
import ${conf.basePackage}.${table.lowerCamelName}.service.${table.className}Service;
import ${conf.basePackage}.validate.handler.ValidateNotNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * ${table.comment} Controller
 *
 * @author ${conf.author}
 * @version ${conf.version}
 * @since ${conf.createDate}
 */
@Slf4j
@Controller
public class ${table.className}Controller extends BaseController<${table.className}Service> {

    @MethodDoc(lists = ${table.className}ListResDTO.class, desc = {"PC端", "${table.comment}-列表查询", "列表查询"}, status = StatusEnum.DONE, author = "taomk", finishTime = "2017/05/20 11:22")
    @RequestMapping(value = "${table.camelName}/list", method = {RequestMethod.GET})
    @ResponseBody
    public void list(final @FromJson ${table.className}ListReqDTO ${table.camelName}ListReqDTO) {
        List<${table.className}ListResDTO> items = service.list(${table.camelName}ListReqDTO);
        responseList(ModelResult.CODE_200, ModelResult.SUCCESS, items);
    }

    @MethodDoc(lists = ${table.className}ListResDTO.class, desc = {"PC端", "${table.comment}-列表查询", "列表查询1.1.0"}, status = StatusEnum.DONE, author = "taomk", finishTime = "2017/05/20 11:22")
    @RequestMapping(value = "${table.camelName}/list", headers = {"version=1.1.0"}, method = {RequestMethod.GET})
    @ResponseBody
    public void listByVersion1(final @FromJson ${table.className}ListReqDTO ${table.camelName}ListReqDTO) {
        List<${table.className}ListResDTO> items = service.listByVersion1(${table.camelName}ListReqDTO);
        responseList(ModelResult.CODE_200, ModelResult.SUCCESS, items);
    }

    @MethodDoc(lists = ${table.className}ListResDTO.class, desc = {"PC端", "${table.comment}-列表查询", "列表查询1.2.0"}, status = StatusEnum.DONE, author = "taomk", finishTime = "2017/05/20 11:22")
    @RequestMapping(value = "${table.camelName}/list", headers = {"version=1.2.0", "platform=APP"}, method = {RequestMethod.GET})
    @ResponseBody
    public void listByVersion2(final @FromJson ${table.className}ListReqDTO ${table.camelName}ListReqDTO) {
        List<${table.className}ListResDTO> items = service.listByVersion2(${table.camelName}ListReqDTO);
        responseList(ModelResult.CODE_200, ModelResult.SUCCESS, items);
    }

    @MethodDoc(lists = ${table.className}ListResDTO.class, desc = {"PC端", "${table.comment}-列表查询", "列表查询1.3.0"}, status = StatusEnum.DONE, author = "taomk", finishTime = "2017/05/20 11:22")
    @RequestMapping(value = "${table.camelName}/list", headers = {"version=1.3.0", "platform=APP", "device=IOS"}, method = {RequestMethod.GET})
    @ResponseBody
    public void listByVersion3(final @FromJson ${table.className}ListReqDTO ${table.camelName}ListReqDTO) {
        List<${table.className}ListResDTO> items = service.listByVersion3(${table.camelName}ListReqDTO);
        responseList(ModelResult.CODE_200, ModelResult.SUCCESS, items);
    }

    @MethodDoc(entity = ${table.className}ListResDTO.class, desc = {"PC端", "${table.comment}-列表查询", "First查询"}, status = StatusEnum.DONE, author = "taomk", finishTime = "2017/05/20 11:22")
    @RequestMapping(value = "${table.camelName}/listOne", method = {RequestMethod.GET})
    @ResponseBody
    public void listOne(final @FromJson ${table.className}ListReqDTO ${table.camelName}ListReqDTO) {
        ${table.className}ListResDTO entity = service.listOne(${table.camelName}ListReqDTO);
        responseEntity(ModelResult.CODE_200, ModelResult.SUCCESS, entity);
    }

    @RequiresPermissions(value = {"XXXX:XXXX:CX"})
    @MethodDoc(pages = ${table.className}PageResDTO.class, desc = {"PC端", "${table.comment}-分页查询", "分页查询"}, status = StatusEnum.DONE, author = "taomk", finishTime = "2017/05/20 11:22")
    @RequestMapping(value = "${table.camelName}/page", method = {RequestMethod.GET})
    @ResponseBody
    public void page(final @FromJson ${table.className}PageReqDTO ${table.camelName}PageReqDTO,
                     final @InitField(name = "pageNo", value = "1", desc = "当前页码") @FromJson Integer pageNo,
                     final @InitField(name = "pageSize", value = "10", desc = "每页大小") @FromJson Integer pageSize) {
        int current = Func.isNullOrZero(pageNo) ? 1 : pageNo;
        int size = Func.isNullOrZero(pageSize) ? 10 : pageSize;

        PageDTO<${table.className}PageResDTO> ${table.camelName}Page = service.pagination(${table.camelName}PageReqDTO, current, size);
        responsePage(ModelResult.CODE_200, ModelResult.SUCCESS, ${table.camelName}Page.getTotal(), ${table.camelName}Page.getRecords(), size, current);
    }

    @RequiresPermissions(value = {"XXXX:XXXX:XZ"})
    @MethodDoc(desc = {"PC端", "${table.comment}-新增", "新增"}, status = StatusEnum.DONE, author = "taomk", finishTime = "2017/05/20 11:22")
    @RequestMapping(value = "${table.camelName}/add", method = {RequestMethod.POST})
    @ResponseBody
    public void add(final @FromJson ${table.className}AddReqDTO ${table.camelName}AddReqDTO) {
        Boolean isSuccess = service.add(${table.camelName}AddReqDTO);
        responseMessage(isSuccess ? ModelResult.CODE_200 : ModelResult.CODE_500, isSuccess ? ModelResult.SUCCESS : ModelResult.FAIL);
    }

    @MethodDoc(desc = {"PC端", "${table.comment}-新增", "新增(所有字段)"}, status = StatusEnum.DONE, author = "taomk", finishTime = "2017/05/20 11:22")
    @RequestMapping(value = "${table.camelName}/addAllColumn", method = {RequestMethod.POST})
    @ResponseBody
    public void addAllColumn(final @FromJson ${table.className}AddReqDTO ${table.camelName}AddReqDTO) {
        Boolean isSuccess = service.addAllColumn(${table.camelName}AddReqDTO);
        responseMessage(isSuccess ? ModelResult.CODE_200 : ModelResult.CODE_500, isSuccess ? ModelResult.SUCCESS : ModelResult.FAIL);
    }

    @MethodDoc(desc = {"PC端", "${table.comment}-新增", "批量新增(所有字段)"}, status = StatusEnum.DONE, author = "taomk", finishTime = "2017/05/20 11:22")
    @RequestMapping(value = "${table.camelName}/addBatchAllColumn", method = {RequestMethod.POST})
    @ResponseBody
    public void addBatchAllColumn(final @FromJson List<${table.className}AddReqDTO> ${table.camelName}AddReqDTOList) {
        Boolean isSuccess = service.addBatchAllColumn(${table.camelName}AddReqDTOList);
        responseMessage(isSuccess ? ModelResult.CODE_200 : ModelResult.CODE_500, isSuccess ? ModelResult.SUCCESS : ModelResult.FAIL);
    }

    @RequiresPermissions(value = {"XXXX:XXXX:CK"})
    @MethodDoc(entity = ${table.className}ShowResDTO.class, desc = {"PC端", "${table.comment}-查看", "查看"}, status = StatusEnum.DONE, author = "taomk", finishTime = "2017/05/20 11:22")
    @RequestMapping(value = "${table.camelName}/show", method = {RequestMethod.GET})
    @ResponseBody
    public void show(final @InitField(name = "id", value = "10", desc = "主键ID") @FromJson @ValidateNotNull(message = "ID不允许为空") Long id) {
        ${table.className}ShowResDTO entity = service.show(id);
        responseEntity(ModelResult.CODE_200, ModelResult.SUCCESS, entity);
    }

    @MethodDoc(entity = ${table.className}ShowResDTO.class, desc = {"PC端", "${table.comment}-查看", "查看(批量)"}, status = StatusEnum.DONE, author = "taomk", finishTime = "2017/05/20 11:22")
    @RequestMapping(value = "${table.camelName}/showByIds", method = {RequestMethod.GET})
    @ResponseBody
    public void showByIds(final @InitField(name = "ids", value = "[\"1001\",\"1002\",\"1003\",\"1004\",\"1005\"]", desc = "主键ID集合") @FromJson List<Long> ids) {
        List<${table.className}ShowResDTO> items = service.showByIds(ids);
        responseList(ModelResult.CODE_200, ModelResult.SUCCESS, items);
    }

    @RequiresPermissions(value = {"XXXX:XXXX:BJ"})
    @MethodDoc(desc = {"PC端", "${table.comment}-编辑", "编辑"}, status = StatusEnum.DONE, author = "taomk", finishTime = "2017/05/20 11:22")
    @RequestMapping(value = "${table.camelName}/modify", method = {RequestMethod.POST})
    @ResponseBody
    public void modify(final @FromJson ${table.className}ModifyReqDTO ${table.camelName}ModifyReqDTO) {
        Boolean isSuccess = service.modify(${table.camelName}ModifyReqDTO);
        responseMessage(isSuccess ? ModelResult.CODE_200 : ModelResult.CODE_500, isSuccess ? ModelResult.SUCCESS : ModelResult.FAIL);
    }

    @MethodDoc(desc = {"PC端", "${table.comment}-编辑", "编辑(所有字段)"}, status = StatusEnum.DONE, author = "taomk", finishTime = "2017/05/20 11:22")
    @RequestMapping(value = "${table.camelName}/modifySelective", method = {RequestMethod.POST})
    @ResponseBody
    public void modifyAllColumn(final @FromJson ${table.className}ModifyReqDTO ${table.camelName}ModifyReqDTO) {
        Boolean isSuccess = service.modifyAllColumn(${table.camelName}ModifyReqDTO);
        responseMessage(isSuccess ? ModelResult.CODE_200 : ModelResult.CODE_500, isSuccess ? ModelResult.SUCCESS : ModelResult.FAIL);
    }

    @RequiresPermissions(value = {"XXXX:XXXX:SC"})
    @MethodDoc(desc = {"PC端", "${table.comment}-删除", "删除"}, status = StatusEnum.DONE, author = "taomk", finishTime = "2017/05/20 11:22")
    @RequestMapping(value = "${table.camelName}/remove", method = {RequestMethod.POST})
    @ResponseBody
    public void remove(final @InitField(name = "id", value = "10", desc = "主键ID") @FromJson @ValidateNotNull(message = "ID不允许为空") Long id) {
        Boolean isSuccess = service.remove(id);
        responseMessage(isSuccess ? ModelResult.CODE_200 : ModelResult.CODE_500, isSuccess ? ModelResult.SUCCESS : ModelResult.FAIL);
    }

    @MethodDoc(desc = {"PC端", "${table.comment}-删除", "删除(批量)"}, status = StatusEnum.DONE, author = "taomk", finishTime = "2017/05/20 11:22")
    @RequestMapping(value = "${table.camelName}/removeBatch", method = {RequestMethod.POST})
    @ResponseBody
    public void removeBatch(final @InitField(name = "ids", value = "[\"1001\",\"1002\",\"1003\",\"1004\",\"1005\"]", desc = "主键ID集合") @FromJson List<Long> ids) {
        Boolean isSuccess = service.removeBatch(ids);
        responseMessage(isSuccess ? ModelResult.CODE_200 : ModelResult.CODE_500, isSuccess ? ModelResult.SUCCESS : ModelResult.FAIL);
    }

    @MethodDoc(desc = {"PC端", "${table.comment}-删除", "删除(参数)"}, status = StatusEnum.DONE, author = "taomk", finishTime = "2017/05/20 11:22")
    @RequestMapping(value = "${table.camelName}/removeByParams", method = {RequestMethod.POST})
    @ResponseBody
    public void removeByParams(final @FromJson ${table.className}RemoveReqDTO ${table.camelName}RemoveReqDTO) {
        Boolean isSuccess = service.removeByParams(${table.camelName}RemoveReqDTO);
        responseMessage(isSuccess ? ModelResult.CODE_200 : ModelResult.CODE_500, isSuccess ? ModelResult.SUCCESS : ModelResult.FAIL);
    }
}

package org.coderfun.common.dict.controller.admin;



import java.util.List;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.coderfun.common.dict.entity.CodeClass;
import org.coderfun.common.dict.entity.CodeClass_;
import org.coderfun.common.dict.service.CodeClassService;
import org.coderfun.common.exception.AppException;
import org.coderfun.common.exception.ErrorCodeEnum;
import org.coderfun.common.log.LogModuleCode;
import org.coderfun.common.log.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import klg.common.model.EasyUIPage;
import klg.common.model.JsonData;
import klg.query.jpa.expr.AExpr;


@Controller("adminCodeClassController")
@RequestMapping("/admin/action/codeclass")
public class CodeClassController {
	@Autowired
	CodeClassService codeClassService;
	
	
	@Logger(name = "添加字典父类", moduleCode = LogModuleCode.DICT)
	@ResponseBody
	@RequestMapping("/add")
	@RequiresPermissions("common:dict:add")
	public JsonData add(
			@ModelAttribute CodeClass codeClass){
		
		if(codeClassService.getOne(AExpr.eq(CodeClass_.code,codeClass.getCode()))==null){
			codeClassService.save(codeClass);			
		}else{
			throw new AppException(ErrorCodeEnum.DATA_EXISTED,"重复的代码！");
		}
		return JsonData.success();
	}
	
	@Logger(name = "修改字典父类", moduleCode = LogModuleCode.DICT)
	@ResponseBody
	@RequestMapping("/edit")
	@RequiresPermissions("common:dict:edit")
	public JsonData edit(
			@ModelAttribute CodeClass codeClass){
		
		codeClassService.update(codeClass, "code");
		return JsonData.success();
	}
	
	@Logger(name = "删除字典父类", moduleCode = LogModuleCode.DICT)
	@ResponseBody
	@RequestMapping("/delete")
	@RequiresPermissions("common:dict:delete")
	public JsonData delete(
			@RequestParam Long id){
		
		codeClassService.delete(id);
		return JsonData.success();
	}
	
	@ResponseBody
	@RequestMapping("/findpage")
	@RequiresPermissions("common:dict:query")
	public EasyUIPage findpage(
			@ModelAttribute CodeClass codeClass,
			@RequestParam int page,
			@RequestParam int rows){
		Pageable pageable=new PageRequest(page<1?0:page-1, rows, new Sort(Direction.DESC,"orderNum"));
		Page<CodeClass> pageData=codeClassService.findPage(codeClass, pageable);
		return new EasyUIPage(pageData);
	}
	
	@ResponseBody
	@RequestMapping("/findlist")
	@RequiresPermissions("common:dict:query")
	public JsonData findlist(
			@ModelAttribute CodeClass codeClass){
		
		List<CodeClass> listData=codeClassService.findList(codeClass, new Sort(Direction.DESC,"orderNum"));
		return JsonData.success(listData);
	}	
	@ResponseBody
	@RequestMapping("/datalist")
	@RequiresPermissions("common:dict:query")
	public List datalist(
			@ModelAttribute CodeClass codeClass){
		List<CodeClass> listData=codeClassService.findList(
				new Sort(Direction.DESC,"orderNum"),
				AExpr.contain(CodeClass_.name, codeClass.getName()).igEmpty(),
				AExpr.eq(CodeClass_.moduleCode, codeClass.getModuleCode()).igEmpty());
		return listData;
	}	
}

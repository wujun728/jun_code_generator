package com.bjc.lcp.app.controller;
import com.alibaba.fastjson.JSON;
import com.bjc.lcp.app.vo.AdminVo;
import com.bjc.lcp.app.dto.AdminDto;
import com.bjc.lcp.app.mapper.AdminMapper;
import com.bjc.lcp.app.entity.AdminEntity;
import com.bjc.lcp.app.service.AdminService;
//import com.bjc.lcp.common.cnt.enums.CntTableNameEnum;
//import com.bjc.lcp.common.cnt.service.CntService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.BeanUtils;
import org.springframework.util.ObjectUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bjc.lcp.system.common.utils.DataResult;
import org.springframework.web.servlet.ModelAndView;
import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;
/**
* @description 管理员表
* @author wujun
* @date 2023-12-26
*/
@Api(tags = "管理员表-管理")
@Slf4j
@RestController
@RequestMapping("/admin")
public class AdminController {

    @Resource
    private AdminService adminService;
    
    @Resource
    private AdminMapper adminMapper;
    
    //@Autowired
    //private CntService cntService;
    
    @ApiOperation(value = "管理员表-新增")
    @PostMapping("/add")
    @RequiresPermissions("admin:add")
    public DataResult add(@Validated(AdminVo.Create.class) @RequestBody AdminVo vo) {
    	AdminDto dto = new AdminDto();
    	BeanUtils.copyProperties(vo, dto);
        if (ObjectUtils.isEmpty(dto.getId())) {
            return DataResult.fail("参数[id]不能为空");
        }
        LambdaQueryWrapper<AdminEntity> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(AdminEntity::getId, dto.getId());
        List<AdminEntity> list = adminService.list(queryWrapper);
        if (list.size() > 0) {
            return DataResult.fail("数据已存在");
        }
        AdminEntity entity = new AdminEntity();
        
        BeanUtils.copyProperties(dto, entity);
        return DataResult.success(adminService.save(entity));
    }
    
    @ApiOperation(value = "管理员表-删除")
    @DeleteMapping("/remove")
    @RequiresPermissions("admin:remove")
    public DataResult delete(@Validated(AdminVo.Delete.class) @RequestBody AdminVo vo) {
    	AdminDto dto = new AdminDto();
    	BeanUtils.copyProperties(vo, dto);
         if (ObjectUtils.isEmpty(dto.getId())) {
              return DataResult.fail("参数[id]不能为空");
         }
        LambdaQueryWrapper<AdminEntity> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(AdminEntity::getId, dto.getId());
        return DataResult.success(adminService.remove(queryWrapper));
    }

    @ApiOperation(value = "管理员表-删除")
    @DeleteMapping("/delete")
    @RequiresPermissions("admin:delete")
    public DataResult delete(@RequestBody @ApiParam(value = "id集合") List<String> ids) {
        return DataResult.success(adminService.removeByIds(ids));
    }


    @ApiOperation(value = "管理员表-更新")
    @PutMapping("/update")
    @RequiresPermissions("admin:update")
    public DataResult update(@Validated(AdminVo.Update.class) @RequestBody AdminVo vo) {
    	AdminDto dto = new AdminDto();
    	BeanUtils.copyProperties(vo, dto);
         if (ObjectUtils.isEmpty(dto.getId())) {
              return DataResult.fail("参数[id]不能为空");
         }
        LambdaQueryWrapper<AdminEntity> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(AdminEntity::getId, dto.getId());
        AdminEntity entity = adminService.getOne(queryWrapper);;
        if (entity == null) {
            return DataResult.fail("数据不存在");
        }
        BeanUtils.copyProperties(dto, entity);
        return DataResult.success(adminService.updateById(entity));
    }
    


    @ApiOperation(value = "管理员表-查询单条")
    @RequestMapping(value = "/getOne",method = {RequestMethod.GET,RequestMethod.POST})
    @RequiresPermissions("admin:getOne")
    public DataResult getOne(@RequestBody AdminVo vo) {
    	AdminDto dto = new AdminDto();
    	BeanUtils.copyProperties(vo, dto);
         if (ObjectUtils.isEmpty(dto.getId())) {
              return DataResult.fail("参数[id]不能为空");
         }
        LambdaQueryWrapper<AdminEntity> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(AdminEntity::getId, dto.getId());
        AdminEntity entity = adminService.getOne(queryWrapper);;
        return DataResult.success(entity);
    }
    
    


    @ApiOperation(value = "管理员表-查询列表分页数据")
    @RequestMapping(value = "/listByPage",method = {RequestMethod.GET,RequestMethod.POST})
    @RequiresPermissions("admin:listByPage")
    public DataResult listByPage(@RequestBody AdminVo admin) {
        Page page = new Page(admin.getPage(), admin.getLimit());
        AdminDto dto = new AdminDto();
    	BeanUtils.copyProperties(admin, dto);
        LambdaQueryWrapper<AdminEntity> queryWrapper = Wrappers.lambdaQuery();
        if (!ObjectUtils.isEmpty(admin.getId())) {
            queryWrapper.eq(AdminEntity::getId, dto.getId());
        }
        if (!ObjectUtils.isEmpty(admin.getUsername())) {
            queryWrapper.eq(AdminEntity::getUsername, dto.getUsername());
        }
        if (!ObjectUtils.isEmpty(admin.getPassword())) {
            queryWrapper.eq(AdminEntity::getPassword, dto.getPassword());
        }
        if (!ObjectUtils.isEmpty(admin.getLasloginDate())) {
            queryWrapper.eq(AdminEntity::getLasloginDate, dto.getLasloginDate());
        }
        if (!ObjectUtils.isEmpty(admin.getCreateDate())) {
            queryWrapper.eq(AdminEntity::getCreateDate, dto.getCreateDate());
        }
        IPage<AdminEntity> iPage = adminService.page(page, queryWrapper);
        return DataResult.success(iPage);
    }
    
    @ApiOperation(value = "管理员表-查询全部列表数据")
    @RequestMapping(value = "/list",method = {RequestMethod.GET,RequestMethod.POST})
    @RequiresPermissions("admin:list")
    public DataResult findListByPage(@RequestBody AdminVo admin) {
        LambdaQueryWrapper<AdminEntity> queryWrapper = Wrappers.lambdaQuery();
        if (!ObjectUtils.isEmpty(admin.getId())) {
            queryWrapper.eq(AdminEntity::getId, admin.getId());
        }
        if (!ObjectUtils.isEmpty(admin.getUsername())) {
            queryWrapper.eq(AdminEntity::getUsername, admin.getUsername());
        }
        if (!ObjectUtils.isEmpty(admin.getPassword())) {
            queryWrapper.eq(AdminEntity::getPassword, admin.getPassword());
        }
        if (!ObjectUtils.isEmpty(admin.getLasloginDate())) {
            queryWrapper.eq(AdminEntity::getLasloginDate, admin.getLasloginDate());
        }
        if (!ObjectUtils.isEmpty(admin.getCreateDate())) {
            queryWrapper.eq(AdminEntity::getCreateDate, admin.getCreateDate());
        }
        List<AdminEntity> list = adminService.list(queryWrapper);
        return DataResult.success(list);
    }


}


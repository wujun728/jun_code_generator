package com.jun.plugin.project.modulename.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import io.swagger.annotations.Api;
import io.swagger.annotations.*;
import java.util.*;
import com.jun.plugin.code_generator.common.ResultBean;
import  com.jun.plugin.project.modulename.service.BizNovelService;
import  com.jun.plugin.project.modulename.domain.BizNovel;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author jun
 * @date 2021-06-17
 */
@RestController
@CrossOrigin
@Api(value = "bizNovelCRUD接口")
@RequestMapping("/modulename/bizNovel")
public class BizNovelController {

    @Autowired
    private BizNovelService bizNovelService;

    @ApiOperation(value = "获取bizNovel根据id",notes="")
    @ApiImplicitParam(name = "id", value = "id", required = true, dataType = "Integer")
    @GetMapping("/getById")
    public ResultBean bizNovel(@RequestParam(value = "id") Integer id) throws Exception {

        BizNovel bizNovel = bizNovelService.getBizNovelById(id);
        ResultBean resultBean = new ResultBean();
        resultBean.fillData(bizNovel);
        return resultBean;
    }

    @ApiOperation(value = "获取bizNovel列表",notes="")
    @ApiImplicitParam(name = "map", value = "bizNovel实体中的参数", dataType ="Map")
    @GetMapping("")
    public ResultBean bizNovelList(@RequestParam(required = false) Map<String, Object> map) throws Exception {

        List<BizNovel> bizNovelList = bizNovelService.listBizNovel(map);
        ResultBean resultBean = new ResultBean();
        resultBean.fillData(bizNovelList);
        return resultBean;
    }

    @ApiOperation(value = "分页获取bizNovel列表",notes="")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "map", value = "bizNovel实体中的参数", dataType="Map"),
        @ApiImplicitParam(name = "pageNo", value = "当前页", dataType = "String"),
        @ApiImplicitParam(name = "pageSize", value = "每页数据条数", dataType = "String"),
    })
    @GetMapping("/page")
    public ResultBean bizNovelPage(@RequestParam(required = false) Map<String, Object> map,
                                        @RequestParam(name = "pageNo", required = false, defaultValue = "1") Integer pageNo,
                                        @RequestParam(name = "pageSize", required = false, defaultValue = "10") Integer pageSize) throws Exception {

        List<BizNovel> bizNovelList = bizNovelService.pageBizNovel(map, pageNo, pageSize);
        ResultBean resultBean = new ResultBean();
        resultBean.fillData(bizNovelList);
        return resultBean;
    }

    @ApiOperation(value = "添加bizNovel",notes="新增一条bizNovel")
    @ApiImplicitParam(name = "bizNovel", value = "bizNovel实体中的参数", required = true, dataType = "BizNovel")
    @PostMapping("")
    public ResultBean bizNovelAdd(@RequestBody BizNovel bizNovel) throws Exception {

        Integer flag = bizNovelService.addBizNovel(bizNovel);
        ResultBean resultBean = new ResultBean();
        resultBean.fillData(flag);
        return resultBean;
    }

    @ApiOperation(value = "修改bizNovel",notes="根据id修改biz_novel")
    @ApiImplicitParam(name = "bizNovel", value = "bizNovel实体", required = true, dataType = "BizNovel")
    @PutMapping("")
    public ResultBean bizNovelUpdate(@RequestBody BizNovel bizNovel) throws Exception {

        Integer flag = bizNovelService.updateBizNovel(bizNovel);
        ResultBean resultBean = new ResultBean();
        resultBean.fillData(flag);
        return resultBean;
    }
    @ApiOperation(value = "物理删除bizNovel",notes="根据id物理删除bizNovel")
    @ApiImplicitParam(name = "id", value = "id", required = true, dataType = "Integer")
    @DeleteMapping("/{id}")
    public ResultBean bizNovelDelete(@PathVariable(name = "id") Integer id) throws Exception {

        Integer flag = bizNovelService.deleteBizNovelById(id);
        ResultBean resultBean = new ResultBean();
        resultBean.fillData(flag);
        return resultBean;
    }

    @ApiOperation(value = "逻辑删除bizNovel",notes="根据id逻辑删除bizNovel")
    @ApiImplicitParam(name = "id", value = "id", required = true, dataType = "Integer")
    @PostMapping("/removeById")
    public ResultBean bizNovelRemove(@RequestParam(value = "id") Integer id) throws Exception {

        Integer flag = bizNovelService.removeBizNovelById(id);
        ResultBean resultBean = new ResultBean();
        resultBean.fillData(flag);
        return resultBean;
    }
}

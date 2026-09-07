package com.template.controller;

import com.template.domain.AjaxResult;
import com.template.controller.BaseController;
import com.template.config.DatabaseSourceConfig;
import com.template.domain.DynamicDataSource;
import com.template.service.IDynamicDataSourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * 动态数据源管理
 *
 * @author Wujun
 */
@RestController
@RequestMapping("/dev-api/datasource")
public class DynamicDataSourceController extends BaseController {

    @Autowired
    private IDynamicDataSourceService dynamicDataSourceService;

    @Autowired
    private DatabaseSourceConfig databaseSourceConfig;

    @GetMapping("/list")
    public AjaxResult list(DynamicDataSource dataSource) {
        List<DynamicDataSource> list = dynamicDataSourceService.selectDynamicDataSourceList(dataSource);
        return success(list);
    }

    @GetMapping("/{id}")
    public AjaxResult getInfo(@PathVariable Long id) {
        return success(dynamicDataSourceService.selectDynamicDataSourceById(id));
    }

    @PostMapping
    public AjaxResult add(@RequestBody DynamicDataSource dataSource) {
        dataSource.setCreateTime(new Date());
        return toAjax(dynamicDataSourceService.insertDynamicDataSource(dataSource));
    }

    @PutMapping
    public AjaxResult edit(@RequestBody DynamicDataSource dataSource) {
        dataSource.setUpdateTime(new Date());
        return toAjax(dynamicDataSourceService.updateDynamicDataSource(dataSource));
    }

    @DeleteMapping("/{id}")
    public AjaxResult remove(@PathVariable Long id) {
        return toAjax(dynamicDataSourceService.deleteDynamicDataSourceById(id));
    }

    @PostMapping("/test/{id}")
    public AjaxResult testConnection(@PathVariable Long id) {
        DynamicDataSource config = dynamicDataSourceService.selectDynamicDataSourceById(id);
        if (config == null) {
            return error("数据源不存在");
        }
        boolean success = databaseSourceConfig.testConnection(config);
        return success ? success("连接成功") : error("连接失败");
    }
}
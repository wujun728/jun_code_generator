package com.javaxiaobear.controller;

import com.javaxiaobear.common.AjaxResult;
import com.javaxiaobear.common.BaseController;
import com.javaxiaobear.common.page.TableDataInfo;
import com.javaxiaobear.config.DatabaseSourceConfig;
import com.javaxiaobear.domain.DynamicDataSource;
import com.javaxiaobear.service.IDynamicDataSourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 动态数据源配置Controller
 * 
 * @author javaxiaobear
 * @date 2025_01_08
 */
@RestController
@RequestMapping("/javaxiaobear/datasource")
public class DynamicDataSourceController extends BaseController
{
    @Resource
    private IDynamicDataSourceService dynamicDataSourceService;

    @Autowired
    private DatabaseSourceConfig databaseSourceConfig;

    /**
     * 查询动态数据源配置列表
     */
    @GetMapping("/list")
    public TableDataInfo list(DynamicDataSource dynamicDataSource)
    {
        startPage();
        List<DynamicDataSource> list = dynamicDataSourceService.selectDynamicDataSourceList(dynamicDataSource);
        return getDataTable(list);
    }

//    /**
//     * 导出动态数据源配置列表
//     */
//    @GetMapping("/export")
//    public AjaxResult export(DynamicDataSource dynamicDataSource)
//    {
//        List<DynamicDataSource> list = dynamicDataSourceService.selectDynamicDataSourceList(dynamicDataSource);
//        ExcelUtil<DynamicDataSource> util = new ExcelUtil<DynamicDataSource>(DynamicDataSource.class);
//        return util.exportExcel(list, "动态数据源配置数据");
//    }

    /**
     * 获取动态数据源配置详细信息
     */
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(dynamicDataSourceService.selectDynamicDataSourceById(id));
    }

    /**
     * 新增动态数据源配置
     */
    @PostMapping("/add")
    public AjaxResult add(@RequestBody DynamicDataSource dynamicDataSource)
    {
        // 先测试连接
        if (!databaseSourceConfig.testConnection(dynamicDataSource)) {
            return AjaxResult.error("数据源连接测试失败，请检查配置信息");
        }

        return toAjax(dynamicDataSourceService.insertDynamicDataSource(dynamicDataSource));
    }

    /**
     * 修改动态数据源配置
     */
    @PostMapping
    public AjaxResult edit(@RequestBody DynamicDataSource dynamicDataSource)
    {
        // 先测试连接
        if (!databaseSourceConfig.testConnection(dynamicDataSource)) {
            return AjaxResult.error("数据源连接测试失败，请检查配置信息");
        }

        return toAjax(dynamicDataSourceService.updateDynamicDataSource(dynamicDataSource));
    }

    /**
     * 删除动态数据源配置
     */
	@GetMapping("/delete/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {

        return toAjax(dynamicDataSourceService.deleteDynamicDataSourceByIds(ids));
    }

    /**
     * 测试数据源连接
     */
    @PostMapping("/test")
    public AjaxResult testConnection(@RequestBody DynamicDataSource dynamicDataSource)
    {
        try {
            boolean success = databaseSourceConfig.testConnection(dynamicDataSource);
            if (success) {
                return AjaxResult.success("数据源连接测试成功");
            } else {
                return AjaxResult.error("数据源连接测试失败");
            }
        } catch (Exception e) {
            return AjaxResult.error("数据源连接测试失败: " + e.getMessage());
        }
    }

    /**
     * 切换数据源
     */
    @PostMapping("/switch/{id}")
    public AjaxResult switchDataSource(@PathVariable Long id)
    {
        try {
            DynamicDataSource dataSource = dynamicDataSourceService.selectDynamicDataSourceById(id);
            if (dataSource == null) {
                return AjaxResult.error("数据源不存在");
            }

            // 切换数据源
            DatabaseSourceConfig.setCurrentDataSourceId(id);

            return AjaxResult.success("数据源切换成功")
                    .put("dataSourceName", dataSource.getName())
                    .put("dataSourceId", id);
        } catch (Exception e) {
            return AjaxResult.error("数据源切换失败: " + e.getMessage());
        }
    }

    /**
     * 获取当前数据源信息
     */
    @GetMapping("/current")
    public AjaxResult getCurrentDataSource()
    {
        Long currentId = DatabaseSourceConfig.getCurrentDataSourceId();
        Map<String, Object> result = new HashMap<>();
        result.put("currentDataSourceId", currentId);
        result.put("availableDataSources", databaseSourceConfig.getAllDataSources());

        if (currentId != null) {
            DynamicDataSource current = dynamicDataSourceService.selectDynamicDataSourceById(currentId);
            result.put("currentDataSource", current);
        }

        return AjaxResult.success(result);
    }

    /**
     * 重置到默认数据源
     */
    @PostMapping("/reset")
    public AjaxResult resetToDefault()
    {
        DatabaseSourceConfig.clearCurrentDataSourceId();
        return AjaxResult.success("已重置到默认数据源");
    }
}

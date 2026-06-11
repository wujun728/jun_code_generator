package com.javaxiaobear.quartz;

import com.javaxiaobear.domain.GenTable;
import com.javaxiaobear.service.IGenTableService;
import com.javaxiaobear.util.StringUtils;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @author xiaobear
 * @version 1.0
 * @description: 定时清理表结构
 * @date 2023/4/6 18:14
 */
@Component

public class RegularCleaningTables {

    @Resource
    private IGenTableService genTableService;

    /**
     * 每天凌晨1.15清理表
     */
    @Scheduled(cron = "0 15 1 ? * * ")
    public void cleanTablesTask(){
        List<GenTable> list = genTableService.selectGenTableList(new GenTable());
        if(StringUtils.isNotEmpty(list)){
            //定时清理除系统测试之外的表结构
            Long[] tableIds = list.stream().filter(item -> !item.getTableName().contains("sys_"))
                    .map(GenTable::getTableId).toArray(Long[]::new);
            genTableService.deleteGenTableByIds(tableIds);
        }
        System.out.println(new Date() + "清理数据库表完成");
    }
}

package io.github.wujun728.mybatisplus.generator;

import io.github.wujun728.mybatisplus.model.GenerModel;
import io.github.wujun728.mybatisplus.utils.GenUtils;
import lombok.extern.slf4j.Slf4j;

/**
 * MyBatis-Plus 代码生成器 统一入口
 *
 * <pre>
 * 使用方法：
 * 1. 修改 src/main/resources/generator.properties 配置数据库连接和生成选项
 * 2. 运行本类的 main 方法
 *
 * 模板组选择：
 * - generator.template.group=curd   → 生成完整 CRUD 方法体（独立，不依赖父类）
 * - generator.template.group=simple → 生成空方法体 + 父类继承
 * </pre>
 *
 * @author wujun
 */
@Slf4j
public class CrudGenerator {

    /**
     * 入口方法：加载配置并执行代码生成
     */
    public static void main(String[] args) {
        try {
            log.info("=============== MyBatis-Plus 代码生成器开始执行 ===============");
            GenerModel model = GenUtils.getGenerByProps();
            log.info("模板组: {}, 表: {}, 输出目录: {}, 包路径: {}",
                    model.getTemplateGroup(), model.getTableName(),
                    model.getPack(), model.getParentPackage());
            GenUtils.buildAndExecute(model);
            log.info("=============== MyBatis-Plus 代码生成器执行成功 ===============");
        } catch (Exception e) {
            log.error("=============== 代码生成器执行失败 ===============", e);
            throw new RuntimeException("代码生成失败: " + e.getMessage(), e);
        }
    }
}
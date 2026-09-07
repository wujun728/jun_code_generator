# 任务计划：my-generator 模块功能迁移与增强

## 一、背景分析

### 两个模块对比

| 特性 | my-generator | jun-xiaobear-gen |
|------|-------------|------------------|
| 基础包名 | `com.template` | `com.javaxiaobear` |
| Java版本 | javax.* (Java EE) | jakarta.* (Jakarta EE) |
| 代码生成 | 基础CRUD | 基础CRUD + 完整项目生成 |
| 数据源 | 单一数据源 | 多数据源动态切换 |
| 数据库文档导出 | 无 | Excel/Word/PDF/HTML |
| SQL建表 | 无 | 支持SQL解析+建表 |
| 审计日志 | 无 | 有 |
| Excel导出 | 无 | 有 |
| 前端框架 | 较旧(Element UI) | 较新(Element Plus) |
| 字典下拉选择 | 已注释(未实现) | 已实现 |
| 前端组件 | 基础组件 | Modern组件系列 |

### 迁移结论
保留 `my-generator` 模块，将 `jun-xiaobear-gen` 的以下功能迁移进来。

---

## 二、实施计划

### 步骤1：后端 - 包名统一与基础结构调整
- 将 `com.template` 包名改为 `com.jun.generator`
- 统一为 javax.* 体系（与现有 my-generator 保持一致）
- 引入缺失的依赖（druid、db-export、easypoi 等）

### 步骤2：后端 - 迁移多数据源管理功能
- 迁移 `DynamicDataSource` 实体类
- 迁移 `DynamicDataSourceMapper`
- 迁移 `IDynamicDataSourceService` 及实现
- 迁移 `DynamicDataSourceController`
- 迁移 `DatabaseSourceConfig` 多数据源配置
- 迁移 `DataSourceSwitcher` 数据源切换工具

### 步骤3：后端 - 迁移完整项目生成器
- 迁移 `CompleteProjectGenerator`（一键生成完整前后端项目）
- 迁移 `ApiSpecGenerator`
- 迁移相关 Velocity 模板文件

### 步骤4：后端 - 迁移数据库文档导出
- 迁移 `DatabaseDictionaryExportController`
- 迁移 `FileDownloadUtils`
- 引入 db-export 依赖

### 步骤5：后端 - 迁移其他增强功能
- 迁移 `createNewTable` SQL建表功能
- 迁移 `exportToExcel` Excel导出
- 迁移 `AuditLogUtil` 审计日志
- 迁移 `GlobalExceptionHandler` 全局异常处理
- 迁移 `CorsConfig` 跨域配置
- 迁移 `RegularCleaningTables` 定时清理

### 步骤6：后端 - 新增字典下拉选择功能
- 在 `GenTableColumn` 中已有 `dictType` 字段
- 新增字典查询接口（查询系统字典列表）
- 在 Controller 中新增字典选项查询端点
- 确保字段信息编辑时可以选择字典类型

### 步骤7：前端 - 迁移增强功能
- 迁移多数据源管理页面
- 迁移数据库文档导出页面
- 迁移完整项目生成页面
- 迁移 Modern 组件系列（ModernButton, ModernCard, ModernTable, StatCard, ThemeSwitcher 等）
- 迁移前端工具函数（performance, error-handler, tech-stack 等）

### 步骤8：前端 - 完善字典下拉选择功能
- 在 `editTable.vue` 中启用字典类型下拉选择
- 新增字典 API 调用
- 确保字典类型列始终可见（移除 `v-if="info.isRuoYi === '1'"` 条件）

### 步骤9：清理与验证
- 确认所有文件编译通过
- 确认功能完整性
- 更新 pom.xml 依赖

---

## 三、待确认问题

1. **包名**：是否使用 `com.jun.generator` 作为新包名？还是保留 `com.template`？
2. **前端框架**：是否将前端升级为 Element Plus（Vue 3）？还是保留 Element UI（Vue 2）？
3. **数据库**：这两套代码连接的数据库表结构是否一致（`gen_table`、`gen_table_column`）？需要确认迁移后表结构兼容。

---

## 四、建议默认方案

1. 包名改为 `com.jun.generator`（体现项目归属）
2. 前端保留 Element UI（Vue 2），但将 jun-xiaobear-gen 的 Modern 组件迁移过来
3. 数据库表结构以 my-generator 现有表结构为准，补充新增字段
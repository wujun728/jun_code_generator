---
name: crud
description: 一键生成 SCRM PaaS 业务模块代码（SQL/Entity/Mapper/Service/Controller/VO），遵循项目 MyBatis-Plus + Rsp<T> 规范
---

# crud - 一键生成 SCRM PaaS 业务模块

根据用户提供的需求描述，一键生成完整的业务模块代码：SQL 建表语句 → Entity → Mapper → Service → Controller → VO/DTO。

## 自动触发条件

当用户的输入符合以下任一情况时，应自动应用此技能（无需用户明确输入 /crud）：

### 关键词触发
- 包含"创建"、"新建"、"添加"、"生成"等动词 + "功能"、"模块"、"管理"、"表"等名词
- 包含"帮我做一个"、"帮我写一个"、"实现一个"等请求模式
- 包含字段描述列表（多个字段说明）

### 场景触发
- 用户描述一个新的业务实体及其属性
- 用户提供数据库表结构或字段清单
- 用户需要实现增删改查（CRUD）功能

---

## 执行步骤

### 步骤1：确定目标微服务模块

SCRM PaaS 是一个多模块项目，必须先确定代码放入哪个服务模块：

| 服务 | 模块路径 | 场景 |
|------|---------|------|
| 元数据 | `scrm-paas-metadata/paas-metadata-server` | 对象定义、字段配置、布局管理等设计态功能 |
| 运行时 | `scrm-paas-runtime/scrm-paas-runtime-server` | 业务数据CRUD、运行时逻辑 |
| 运行时核心 | `scrm-paas-runtime/scrm-paas-runtime-core` | 核心引擎逻辑（无 Controller） |
| 集成 | `scrm-paas-integration/integration-server` | 外部系统集成 |
| 消息 | `scrm-paas-message/message-server` | 消息通知 |
| BPM | `scrm-paas-bpm/bpm-server` | 审批流程 |

**如果用户未指定，询问确认目标模块。**


### 步骤2：确定 Entity 基类

根据业务场景选择 Entity 基类：

| 基类 | 场景 | 自动填充字段 |
|------|------|-------------|
| `PaasMetadataModel` | 元数据配置表（有版本管理） | id, accountId, createdBy, createdAt, updatedBy, updatedAt, deletedFlag, metadataVersion, applicationCode |
| `TenantBaseModel` | 租户业务表（通用） | id, accountId |
| `BaseModel` | 基础表 | id, createAt, updateAt |

### 步骤3：需求分析

- 确定表名（遵循 `paas_` 前缀命名规范）
- 确定字段名（蛇形命名）、类型、注释
- 确定业务模块名称（用于包路径）
- 确定是否需要 `code` 字段（业务唯一标识）

### 步骤4：按顺序生成代码

1. **SQL 建表语句**
2. **Entity 实体类**
3. **Mapper 接口**（MyBatis-Plus BaseMapper）
4. **VO/DTO**（请求参数 + 响应结果）
5. **Service 业务类**
6. **Controller 控制器**

---

## 代码规范要求

### 核心规则
- **Java 11**，可使用 `var` 关键字
- **MyBatis-Plus 3.4.2**（非原生 MyBatis）
- **Service 直接 `@Service` 类**（禁止 interface+impl）
- **响应统一 `Rsp<T>`**（非 `ApiResult`）
- **OpenAPI 3 注解**（`@Tag`, `@Operation`, `@Schema`，禁止 Swagger 2 注解）
- **Jackson**（禁止 FastJSON 用于 VO/DTO）
- **`@RequiredArgsConstructor`** 构造器注入（禁止 `@Autowired` 字段注入）
- **`LocalDateTime`**（禁止 `Date` / `Timestamp`）
- **查询必须过滤 `accountId`**（多租户隔离）

### 数据库规范
- 表名：`paas_` 前缀 + 蛇形命名
- 字段名：蛇形命名
- 主键：`id BIGINT`（雪花算法或自增）
- 必备字段：`id`, `account_id`, `created_by`, `created_at`, `updated_by`, `updated_at`, `deleted_flag`
- `code` 字段：VARCHAR(64)，业务唯一标识

---

## 代码模板

> 模板中 `{basePackage}` 需要根据目标模块确定，格式如 `net.trueland.scrm.paas.metadata` 或 `net.trueland.scrm.paas.runtime`。

### SQL 建表模板

```sql
CREATE TABLE `paas_{表名}` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `account_id` BIGINT NOT NULL COMMENT '租户ID',
    `code` VARCHAR(64) NOT NULL COMMENT '业务编码',
    -- 业务字段...
    `created_by` BIGINT DEFAULT NULL COMMENT '创建人ID',
    `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_by` BIGINT DEFAULT NULL COMMENT '更新人ID',
    `updated_at` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted_flag` TINYINT NOT NULL DEFAULT 0 COMMENT '删除标记 0未删除 1已删除',
    PRIMARY KEY (`id`),
    KEY `idx_account_id` (`account_id`),
    UNIQUE KEY `uk_account_code` (`account_id`, `code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='{表描述}';
```

### Entity 模板

```java
package {basePackage}.repository.entity.{模块名};

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import net.trueland.scrm.common.model.entity.PaasMetadataModel;

/**
 * {实体描述}
 */
@TableName("paas_{表名}")
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class Paas{实体名} extends PaasMetadataModel {

    /**
     * 业务编码
     */
    private String code;

    // 业务字段...
}
```

### Mapper 模板

```java
package {basePackage}.repository.mapper.{模块名};

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import {basePackage}.repository.entity.{模块名}.Paas{实体名};
import org.apache.ibatis.annotations.Mapper;

/**
 * {实体描述} Mapper
 */
@Mapper
public interface {实体名}Mapper extends BaseMapper<Paas{实体名}> {
}
```

### 请求 DTO 模板

```java
package {basePackage}.vo.req.{模块名};

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

/**
 * {实体描述} 创建/编辑请求参数
 */
@Schema(title = "{实体描述}参数")
@Data
public class {实体名}Param {

    @Schema(title = "名称")
    @NotBlank(message = "名称不能为空")
    @Length(max = 100, message = "名称长度不能超过100")
    private String name;

    // 其他业务字段...
}
```

### 响应 DTO 模板

```java
package {basePackage}.vo.rsp.{模块名};

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * {实体描述} 响应结果
 */
@Data
public class {实体名}Result {

    @Schema(title = "ID")
    private Long id;

    @Schema(title = "业务编码")
    private String code;

    @Schema(title = "名称")
    private String name;

    // 其他业务字段...

    @Schema(title = "创建时间")
    private LocalDateTime createdAt;

    @Schema(title = "更新时间")
    private LocalDateTime updatedAt;
}
```

### Service 模板

```java
package {basePackage}.service.{模块名};

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.trueland.scrm.common.context.auth.CrmUserInfoContext;
import net.trueland.scrm.common.model.exception.BuException;
import {basePackage}.repository.entity.{模块名}.Paas{实体名};
import {basePackage}.repository.mapper.{模块名}.{实体名}Mapper;
import {basePackage}.vo.req.{模块名}.{实体名}Param;
import {basePackage}.vo.rsp.{模块名}.{实体名}Result;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * {实体描述} Service
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class {实体名}Service {

    private final {实体名}Mapper {实体名小写}Mapper;

    /**
     * 创建
     */
    @Transactional(rollbackFor = Exception.class)
    public Long create({实体名}Param param) {
        Paas{实体名} entity = new Paas{实体名}();
        entity.setAccountId(CrmUserInfoContext.getNonNullAccountId());
        // 设置业务字段...
        {实体名小写}Mapper.insert(entity);
        return entity.getId();
    }

    /**
     * 更新
     */
    @Transactional(rollbackFor = Exception.class)
    public void update(Long id, {实体名}Param param) {
        Paas{实体名} entity = getById(id);
        // 更新业务字段...
        {实体名小写}Mapper.updateById(entity);
    }

    /**
     * 详情
     */
    public {实体名}Result getDetail(Long id) {
        Paas{实体名} entity = getById(id);
        return toResult(entity);
    }

    /**
     * 列表查询
     */
    public List<{实体名}Result> list() {
        List<Paas{实体名}> list = {实体名小写}Mapper.selectList(
            Wrappers.<Paas{实体名}>lambdaQuery()
                .eq(Paas{实体名}::getAccountId, CrmUserInfoContext.getNonNullAccountId())
                .eq(Paas{实体名}::getDeletedFlag, 0)
                .orderByDesc(Paas{实体名}::getId));
        return list.stream().map(this::toResult).collect(Collectors.toList());
    }

    /**
     * 删除（逻辑删除）
     */
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        Paas{实体名} entity = getById(id);
        entity.setDeletedFlag(1);
        {实体名小写}Mapper.updateById(entity);
    }

    private Paas{实体名} getById(Long id) {
        Paas{实体名} entity = {实体名小写}Mapper.selectOne(
            Wrappers.<Paas{实体名}>lambdaQuery()
                .eq(Paas{实体名}::getAccountId, CrmUserInfoContext.getNonNullAccountId())
                .eq(Paas{实体名}::getId, id));
        if (entity == null) {
            throw new BuException("数据不存在, id={}", id);
        }
        return entity;
    }

    private {实体名}Result toResult(Paas{实体名} entity) {
        {实体名}Result result = new {实体名}Result();
        result.setId(entity.getId());
        result.setCode(entity.getCode());
        // 映射其他字段...
        result.setCreatedAt(entity.getCreatedAt());
        result.setUpdatedAt(entity.getUpdatedAt());
        return result;
    }
}
```

### Controller 模板

```java
package {basePackage}.web.controller.{模块名};

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import net.trueland.scrm.common.model.rpc.Rsp;
import {basePackage}.service.{模块名}.{实体名}Service;
import {basePackage}.vo.req.{模块名}.{实体名}Param;
import {basePackage}.vo.rsp.{模块名}.{实体名}Result;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * {实体描述} Controller
 */
@Tag(name = "{实体描述}", description = "{实体描述}相关接口")
@RestController
@RequestMapping("{模块路径}")
@RequiredArgsConstructor
public class {实体名}Controller {

    private final {实体名}Service {实体名小写}Service;

    @PostMapping("create")
    @Operation(summary = "创建{实体描述}")
    public Rsp<Long> create(@RequestBody @Valid {实体名}Param param) {
        return Rsp.success({实体名小写}Service.create(param));
    }

    @PostMapping("update")
    @Operation(summary = "更新{实体描述}")
    public Rsp<Void> update(@RequestParam Long id, @RequestBody @Valid {实体名}Param param) {
        {实体名小写}Service.update(id, param);
        return Rsp.success();
    }

    @GetMapping("detail")
    @Operation(summary = "查询{实体描述}详情")
    public Rsp<{实体名}Result> detail(@RequestParam Long id) {
        return Rsp.success({实体名小写}Service.getDetail(id));
    }

    @GetMapping("list")
    @Operation(summary = "查询{实体描述}列表")
    public Rsp<List<{实体名}Result>> list() {
        return Rsp.success({实体名小写}Service.list());
    }

    @PostMapping("delete")
    @Operation(summary = "删除{实体描述}")
    public Rsp<Void> delete(@RequestParam Long id) {
        {实体名小写}Service.delete(id);
        return Rsp.success();
    }
}
```

---

## 生成文件清单

生成完成后，列出所有文件路径（`{basePath}` = 目标模块的 `src/main/java` 路径）：

```
{basePath}/{basePackagePath}/repository/entity/{模块名}/Paas{实体名}.java
{basePath}/{basePackagePath}/repository/mapper/{模块名}/{实体名}Mapper.java
{basePath}/{basePackagePath}/vo/req/{模块名}/{实体名}Param.java
{basePath}/{basePackagePath}/vo/rsp/{模块名}/{实体名}Result.java
{basePath}/{basePackagePath}/service/{模块名}/{实体名}Service.java
{basePath}/{basePackagePath}/web/controller/{模块名}/{实体名}Controller.java
```

---

## 变量说明

| 变量 | 说明 | 获取方式 |
|-----|------|---------|
| `{basePackage}` | 目标模块的基础包名 | 根据目标模块确定 |
| `{basePackagePath}` | 包名转路径 | 将 `.` 替换为 `/` |
| `{实体名}` | 实体类名（大驼峰，不含 Paas 前缀） | 根据需求分析 |
| `{实体名小写}` | 实体变量名（小驼峰） | 首字母小写 |
| `{实体描述}` | 实体中文描述 | 根据需求分析 |
| `{模块名}` | 模块包名（小写） | 根据需求分析 |
| `{模块路径}` | API 路径 | 根据模块名确定 |
| `{表名}` | 数据库表名（不含 `paas_` 前缀） | 蛇形命名 |

---

## 常见示例

### 1. 元数据服务 - 标签管理

```
请帮我在 metadata 服务中创建一个标签管理功能，包含以下字段：
- 标签名称（必填，最长50字符）
- 标签颜色（最长20字符）
- 排序值（整数，默认0）
- 状态（0禁用/1启用，默认1）
```

### 2. 运行时服务 - 通知配置

```
请帮我在 runtime 服务中创建一个通知配置管理，包含以下字段：
- 配置名称（必填，最长100字符）
- 通知类型（1站内信/2邮件/3短信）
- 触发事件（最长100字符）
- 模板内容（TEXT类型）
- 是否启用（0否/1是，默认1）
```

### 3. 集成服务 - 外部接口配置

```
请帮我在 integration 服务中创建一个外部接口配置功能，包含以下字段：
- 接口名称（必填，最长200字符）
- 接口地址（必填，最长500字符）
- 请求方法（GET/POST/PUT/DELETE）
- 认证方式（1无认证/2Basic/3Bearer Token/4API Key）
- 认证参数（JSON格式）
- 超时时间（毫秒，默认5000）
- 重试次数（整数，默认0）
- 状态（0禁用/1启用）
```

---

## 注意事项

1. Entity 必须继承 `PaasMetadataModel` 或 `TenantBaseModel`，禁止从零开始定义公共字段
2. 所有查询必须加 `accountId` 过滤条件（多租户隔离）
3. 使用 `Wrappers.<T>lambdaQuery()` 构建查询条件（类型安全）
4. Controller 禁止包含业务逻辑，仅做参数校验和结果返回
5. 使用 `@Transactional(rollbackFor = Exception.class)` 管理事务
6. 批量插入使用 `MybatisPlusHelper.saveBatch()`，禁止循环单条插入
7. 生成后建议编译验证：`mvn compile -pl <module> -DskipTests`

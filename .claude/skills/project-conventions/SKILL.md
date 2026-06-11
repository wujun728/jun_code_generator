---
name: project-conventions
description: SCRM PaaS 项目编码规范指南，基于项目实际代码模式，确保代码风格统一
---

# project-conventions - SCRM PaaS 编码规范

本规范基于 SCRM PaaS 项目的实际代码模式整理，适用于所有模块的 Java 代码开发。

## 一、项目结构规范

### 1.1 模块总览

SCRM PaaS 是一个 Maven 多模块单仓库，包含 15+ 个顶级模块：

| 层级 | 模块 | 说明 |
|------|------|------|
| 基础设施 | `paas-infrastructure` | BOM、Starter、公共模型/工具/注解/常量 |
| 元数据服务 | `scrm-paas-metadata` | 设计态：对象定义、字段、布局、视图 |
| 运行时引擎 | `scrm-paas-runtime` | 运行态：CRUD、业务流、定时任务 |
| 发布管道 | `scrm-paas-metadata/paas-publish-server` | 元数据从开发到生产的发布 |
| 集成服务 | `scrm-paas-integration` | 外部系统集成 |
| 消息中心 | `scrm-paas-message` | 消息通知 |
| BPM 引擎 | `scrm-paas-bpm` | 审批流程 |
| 运行时网关 | `scrm-paas-runtime-gateway` | API 网关 |

### 1.2 微服务内部包结构

```
net.trueland.scrm.paas.{service}/
├── web/controller/              # @RestController，接收请求、返回 Rsp<T>
│   ├── account/
│   ├── custobj/
│   └── layout/
├── service/                     # @Service 直接类，业务逻辑
│   ├── custobj/
│   ├── layout/
│   └── role/
├── repository/                  # 实体类
│   ├── entity/
│   │   ├── custobj/
│   │   ├── field/
│   │   └── layout/
│   └── mapper/                  # @Mapper extends BaseMapper<T>
│       ├── custobj/
│       ├── field/
│       └── layout/
├── vo/                          # 请求/响应 DTO
│   ├── req/                     # 请求参数对象
│   └── rsp/                     # 响应结果对象
├── dto/                         # 内部数据传输对象
├── enums/                       # 枚举类
├── consts/                      # 常量类
├── action/                      # 动作处理器（Command 模式）
└── command/                     # 命令处理器
```

---

## 二、命名规范

### 2.1 包命名

- **格式**：全部小写，使用点分隔
- **前缀**：新代码统一使用 `net.trueland.scrm.paas`
- **遗留前缀**：`net.trueland.tcloud`（正在淘汰，勿新增）

### 2.2 类命名

| 类型 | 命名格式 | 示例 |
|------|---------|------|
| Entity | `Paas{功能名}` | `PaasComponentObject`, `PaasComponentInstance` |
| Mapper | `{功能名}Mapper` | `ComponentObjectMapper`, `ComponentInstanceMapper` |
| Service | `{功能名}Service` | `ComponentInstanceService`, `PaasRoleService` |
| Controller | `{功能名}Controller` | `CustomObjectController`, `LayoutController` |
| 请求 DTO | `{功能名}Param` / `{功能名}Req` | `CustomObjectCreateParam`, `InstanceReq` |
| 响应 DTO | `{功能名}Result` / `{功能名}Rsp` / `{功能名}VO` | `PaasApplicationPageResult`, `AccountRsp` |
| 枚举 | `{功能名}Enum` / `{功能名}Enums` | `PermissionPointType`, `ConditionSourceType` |
| 常量 | `{功能名}Const` / `{功能名}Consts` | `DefaultConst`, `CommonConsts` |

### 2.3 方法命名

| 操作类型 | 命名前缀 | 示例 |
|---------|---------|------|
| 查询单个 | `get`, `find` | `getByCode()`, `findById()` |
| 查询列表 | `list`, `select` | `listByParam()`, `selectByQo()` |
| 新增 | `create`, `add`, `save` | `create()`, `addEntity()` |
| 修改 | `update`, `modify` | `updateById()`, `modify()` |
| 删除 | `delete`, `remove` | `deleteById()`, `removeByIds()` |
| 批量操作 | `batch{操作}` | `batchInsert()`, `batchUpdate()` |
| 判断存在 | `exist`, `check` | `exist()`, `checkNameExists()` |

### 2.4 变量与常量

- **变量**：小驼峰 `lowerCamelCase`，如 `accountId`, `objectCode`, `instanceName`
- **常量**：全大写下划线 `UPPER_SNAKE_CASE`，如 `MANAGER_ACCOUNT_ID`, `MAX_PAGE_SIZE`
- **禁止**：拼音命名、无意义单字母变量（循环变量除外）

---

## 三、分层规范

### 3.1 架构分层（4 层）

```
┌──────────────────────────────┐
│ CONTROLLER LAYER             │  @RestController, @RequestMapping
│ (*.web.controller.*)         │  → 参数校验，返回 Rsp<T>
├──────────────────────────────┤
│ SERVICE LAYER                │  @Service 直接类（无 interface+impl）
│ (*.service.*)                │  → 业务逻辑、事务控制
├──────────────────────────────┤
│ MAPPER LAYER                 │  @Mapper extends BaseMapper<T>
│ (*.repository.mapper.*)      │  → MyBatis-Plus 动态 SQL
├──────────────────────────────┤
│ ENTITY LAYER                 │  extends PaasMetadataModel / TenantBaseModel
│ (*.repository.entity.*)      │  → 自动填充公共字段 (id, accountId, 时间戳)
└──────────────────────────────┘
```

### 3.2 各层职责与示例

#### Controller 层

- 接收 HTTP 请求，参数校验
- 调用 Service 处理业务
- 返回统一响应格式 `Rsp<T>`
- **禁止**：包含业务逻辑、直接操作 Mapper

```java
@Tag(name = "自定义对象", description = "自定义对象管理接口")
@RestController
@RequestMapping("custom/obj")
@RequiredArgsConstructor
public class CustomObjectController {

    private final CustomObjectService customObjectService;

    @PostMapping("create")
    @Operation(summary = "创建自定义对象")
    public Rsp<Long> create(@RequestBody @Valid CustomObjectCreateParam param) {
        return Rsp.success(customObjectService.create(param));
    }

    @GetMapping("detail")
    @Operation(summary = "查询对象详情")
    public Rsp<ObjectDetailResult> detail(@RequestParam String objectCode) {
        return Rsp.success(customObjectService.getDetail(objectCode));
    }
}
```

#### Service 层

- **直接 `@Service` 类**，不使用 interface+impl 模式
- 业务逻辑处理，事务控制
- 构造器注入（`@RequiredArgsConstructor`）+ `@Resource` 注入

```java
@Service
@Slf4j
@RequiredArgsConstructor
public class ComponentInstanceService {

    // 构造器注入（必选依赖）
    private final ComponentInstanceMapper componentInstanceMapper;

    // @Resource 注入（可选依赖）
    @Resource
    private FieldCreateService fieldCreateService;

    public PaasComponentInstance getByCode(String instanceCode) {
        return componentInstanceMapper.selectOne(
            Wrappers.<PaasComponentInstance>lambdaQuery()
                .eq(PaasComponentInstance::getAccountId, CrmUserInfoContext.getNonNullAccountId())
                .eq(PaasComponentInstance::getCode, instanceCode));
    }

    @Transactional(rollbackFor = Exception.class)
    public Boolean create(InstanceReq req) {
        assertApiNameNotExist(req.getObjectCode(), req.getApiName());
        return fieldCreateService.exec(req);
    }
}
```

#### Mapper 层

- 继承 MyBatis-Plus `BaseMapper<T>`，自动获得 CRUD
- 使用 `@Mapper` 注解注册
- 可选 `@CacheNamespace(implementation = PaasMyBatisCache.class)` 启用缓存

```java
@Mapper
@CacheNamespace(implementation = PaasMyBatisCache.class)
public interface ComponentObjectMapper extends BaseMapper<PaasComponentObject> {
    // CRUD 由 BaseMapper 自动生成
    // 仅在需要自定义 SQL 时添加方法
}
```

#### Entity 层

- 继承 `PaasMetadataModel`（元数据实体）或 `TenantBaseModel`（租户实体）
- 公共字段自动填充：`id`, `accountId`, `createdBy`, `createdAt`, `updatedAt`, `deletedFlag`

```java
@TableName("paas_component_object")
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class PaasComponentObject extends PaasMetadataModel {
    private String name;
    private String code;        // 业务唯一标识，不可变
    private String description;
    private YesOrNo status;
}
```

---

## 四、数据库规范

### 4.1 表命名

- **系统表**：`paas_` 前缀 + 蛇形命名，如 `paas_component_object`, `paas_component_instance`
- **动态业务表**：`paasobj_` 前缀 + 对象编码，如 `paasobj_lead`, `paasobj_customer`
- **索引名限制**：MySQL 标识符不超过 64 字符
  - 索引格式：`paasobj_{objectCode}_{accountId}_{fieldName}_index`

### 4.2 字段命名

- **格式**：全小写蛇形命名，如 `account_id`, `created_at`, `object_code`
- **排序**：使用 `id` 排序（非 `create_at`）
- **主键**：雪花算法或自增 ID

### 4.3 公共基础字段

| 字段名 | 类型 | 说明 |
|-------|------|------|
| `id` | BIGINT | 主键 ID（雪花算法 / 自增） |
| `account_id` | BIGINT | 租户 ID（数据隔离必需） |
| `created_by` | BIGINT | 创建人 ID |
| `created_at` | DATETIME | 创建时间（自动填充） |
| `updated_by` | BIGINT | 更新人 ID |
| `updated_at` | DATETIME | 更新时间（自动填充） |
| `deleted_flag` | TINYINT | 软删除标记（0 未删除 / 1 已删除） |

### 4.4 字段类型映射

| 数据库类型 | Java 类型 | 说明 |
|-----------|---------|------|
| BIGINT | Long | 主键、ID |
| INT / TINYINT | Integer | 状态、计数 |
| VARCHAR | String | 字符串 |
| DATETIME | LocalDateTime | 时间（禁用 Date / Timestamp） |
| TEXT | String | 长文本 |
| JSON | String / JsonNode | JSON 数据 |

---

## 五、API 与 Swagger 规范

### 5.1 响应格式

所有 API 统一返回 `Rsp<T>`：

```java
// 成功
return Rsp.success(data);
return Rsp.success();

// 失败
return Rsp.fail(401, "登录用户不存在");
return Rsp.fail(ARGUMENT_NOT_VALID, "field_name");
```

### 5.2 OpenAPI 3 注解（Swagger v3）

**禁止使用 Swagger 2 注解**（`@Api`, `@ApiOperation`, `@ApiModelProperty`）。

| 注解 | 作用域 | 说明 |
|------|--------|------|
| `@Tag(name = "...")` | 类 | Controller 分组描述 |
| `@Operation(summary = "...")` | 方法 | 接口描述 |
| `@Schema(title = "...", description = "...")` | 字段/类 | DTO 字段描述 |
| `@ParameterObject` | 参数 | GET 请求的对象参数 |

```java
// Controller
@Tag(name = "角色管理", description = "角色相关接口")
@RestController
public class RoleController {

    @GetMapping("list")
    @Operation(summary = "查询角色列表")
    public Rsp<List<RoleResult>> list(@ParameterObject RoleSearchParam param) {
        return Rsp.success(roleService.list(param));
    }
}

// DTO
@Data
public class RoleSearchParam {
    @Schema(title = "角色名称")
    private String name;

    @Schema(title = "状态", description = "0禁用，1启用")
    private Integer status;
}
```

### 5.3 Swagger 注解位置规则

- **Controller 和 VO/DTO**：必须加 Swagger 注解
- **Entity 和 Service**：不加 Swagger 注解
- **GET 请求对象参数**：使用 `@ParameterObject`

---

## 六、异常处理规范

### 6.1 BuException

项目使用 `BuException` 作为统一业务异常：

```java
// 带错误码
throw new BuException(IResultCode.PARAM_ERROR, new Object[]{"field_name"});

// 带消息模板
throw new BuException("字段apiName[{}]格式有误", req.getApiName());

// 链式调用
throw new BuException(OPERATION_FAILED)
    .setPrint(false)
    .setShouldAlarm(true)
    .setData(errorData);
```

### 6.2 断言工具

```java
import static net.trueland.scrm.common.util.base.Assert.*;

// 常用断言
isTrue(condition, "错误信息{}", args);
notNull(value, "值不能为空");
notEmpty(collection, "集合不能为空");
```

### 6.3 全局异常处理

由 `GlobalExceptionHandler`（`@RestControllerAdvice`）统一处理，自动转为 `Rsp` 格式返回。

---

## 七、多租户规范

### 7.1 租户上下文

```java
// 获取当前租户 ID（非空，否则抛异常）
Long accountId = CrmUserInfoContext.getNonNullAccountId();

// 设置租户上下文
CrmUserInfoContext.setBase(new Base(accountId, userId, username, realname, roleId, status, timezone));

// 动态数据源切换
DataSourceUtils.switchover(accountId);
```

### 7.2 查询必须过滤 accountId

**每个查询都必须加 accountId 过滤**，否则会产生数据泄露：

```java
// 正确
mapper.selectOne(Wrappers.<PaasComponentObject>lambdaQuery()
    .eq(PaasComponentObject::getAccountId, CrmUserInfoContext.getNonNullAccountId())
    .eq(PaasComponentObject::getCode, code));

// 错误（缺少 accountId 过滤）
mapper.selectOne(Wrappers.<PaasComponentObject>lambdaQuery()
    .eq(PaasComponentObject::getCode, code));
```

### 7.3 元数据租户 ID

元数据管理使用专用常量：`CommonConsts.MANAGER_ACCOUNT_ID`

---

## 八、依赖注入规范

### 8.1 构造器注入（推荐）

```java
// 推荐：Lombok 构造器注入
@Service
@RequiredArgsConstructor
public class ComponentInstanceService {
    private final ComponentInstanceMapper componentInstanceMapper;
    private final ComponentObjectMapper componentObjectMapper;
}
```

### 8.2 @Resource 注入（可选依赖）

```java
@Service
@RequiredArgsConstructor
public class SomeService {
    private final MandatoryDep mandatoryDep;

    @Resource
    private OptionalDep optionalDep;
}
```

### 8.3 禁止使用 @Autowired 字段注入

```java
// 禁止
@Autowired
private SomeMapper someMapper;
```

---

## 九、JSON 处理规范

- **统一使用 Jackson**：`JsonNode`, `ObjectMapper`, `JsonUtils`
- **FastJSON 仅允许局部使用**，禁止用于 VO/DTO 字段
- **禁止 Map/JSONObject** 作为 API 输入/输出类型，使用类型化 DTO

```java
// 推荐：Jackson
JsonNode node = JsonUtils.readTree(jsonStr);
MyDto dto = JsonUtils.readJsonString(jsonStr, MyDto.class);

// 允许（仅限局部解析）
JSONObject obj = JSONObject.parseObject(attrInfo);
JSONObject rangeAttr = obj.getJSONObject("dataRangeAttr");
```

---

## 十、日期时间规范

- **统一使用 Java 8 日期类型**：`LocalDateTime`, `LocalDate`, `LocalTime`
- **禁止使用**：`java.util.Date`, `java.sql.Timestamp`

---

## 十一、`code` 字段约定

- `code` 是实体的**业务唯一标识**（租户作用域内不可变）
- 由 `CodeGenerator` 自动生成
- `xxCode` 字段表示对外部实体的引用（如 `objectCode` 引用对象编码）
- 查询通常基于 `accountId + code` 组合

---

## 十二、跨服务通信规范

- **同服务内**：直接注入 Service / Mapper
- **跨服务**：必须通过 Feign Client
- **禁止**：跨服务直接注入 Mapper

```java
// Feign Client 定义
@FeignClient(value = "scrm-paas-runtime-server", contextId = "customObjectFeign")
public interface CustomObjectFeign {
    @PostMapping("/object/batchSelect")
    Rsp<List<PaasComponentObjectReturn>> batchSelect(@RequestBody Set<String> codes);
}

// 使用
@Service
@RequiredArgsConstructor
public class SomeService {
    private final CustomObjectFeign customObjectFeign;

    public void someMethod() {
        Rsp<List<PaasComponentObjectReturn>> result = customObjectFeign.batchSelect(codes);
    }
}
```

---

## 十三、缓存规范

多级缓存策略：

```
Caffeine (本地, 30min) → Redis (分布式) → MyBatis L2 (PaasMyBatisCache) → Database
```

- 注解驱动：`@Cacheable`, `@CacheEvict`
- 缓存失效：通过 RabbitMQ 消费者广播
- 数据视图缓存 TTL：100 小时

---

## 十四、测试规范

```java
@SpringBootTest
@Disabled  // 默认禁用，运行时移除
class ComponentInstanceMapperTest {
    @Resource
    private ComponentInstanceMapper mapper;

    private final Long accountId = 2184935L;
    private final Base base = new Base(accountId, 1L, "test", "test", 1L, 0, null);

    @Test
    void testMethod() {
        CrmUserInfoContext.setBase(base);   // 必须先设置租户上下文
        // 测试代码
    }
}
```


---

## 十五、编码禁忌

1. **禁止** Service 使用 interface + impl 模式
2. **禁止** 查询不加 `accountId` 过滤
3. **禁止** 跨服务直接注入 Mapper
4. **禁止** 使用 Swagger 2 注解（`@Api`, `@ApiOperation`, `@ApiModelProperty`）
5. **禁止** 使用 `Date` / `Timestamp`，使用 `LocalDateTime`
6. **禁止** 使用 `Map` / `JSONObject` 作为 API 输入输出
7. **禁止** VO/DTO 继承 `Serializable`（除特殊需求）
8. **禁止** API 直接返回数据库 Entity，必须转为 VO
9. **禁止** 在循环中执行数据库操作（使用批量方法）
10. **禁止** 使用 `@Autowired` 字段注入

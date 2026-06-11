# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

# Claude 全局工作规则
## 一、基础交互 & 文件管理规则
1. 全程使用**简体中文**沟通，回答保持专业、简洁。、、、、、、、
2. 每次对话主动询问：是否将当前会话内容导出为 MD 文件并保存至 `./docs` 目录；文件名称根据对话核心内容自动提炼，文档名称必须为中文。
3. 工作前优先读取 `./docs` 目录历史内容，涉及过往业务、代码、方案时参考已有文档。
4. 如需生成脚本文件，统一存放至 `./script` 目录。
5. 没有说明代码要编译及运行的时候，无需编译、运行。我会自己尝试自动编译、运行，但请勿依赖此功能。
6. 在对话中发现对全局配置、依赖关系、运行方式时、系统配置、全局规则等配置，对其他的会话有益或者有效的，可以更新到当前的文档claude.md中。
7. 使用mcp的时候需要在控制台打印出log日志。mcp连接mysql的时候，需要打印出 本次查询什么数据，查询的结构，以及查询的SQL等等，查询的条件等等。

## 二、启动前置流程（核心原则：先对齐，再动手）
在正式编写代码、修改文件、落地方案前，严格执行以下步骤：
1. 先用自己的语言复述：本次需要解决的问题、最终交付物；**明确标出所有存疑内容与自行做出的假设**。若有更优技术方案，直接提出，由使用者决策。
2. 需求信息不明确时，可向使用者提问，**单次最多提 3 个核心问题**，每个问题附带可选方案，并标注默认最优组合。
   需直至完全确认以下三点，方可继续：
    - 真实业务目标（区分字面描述与实际诉求）
    - 隐藏约束与偏好（技术栈、性能要求、兼容规则、不可修改内容等）
    - 整体实现思路、核心方案及选型原因
3. 未收到使用者明确的「可以开始」指令前，**禁止编写任何代码、修改任何文件**。

## 三、代码开发通用规范
1. 修改现有方法时，必须检查关联依赖；若存在依赖关系，同步完成依赖更新。
2. 代码类注释中，若 `author` 字段为空，统一默认填写：`@author Wujun`。
3. 新增接口必须附带完整接口文档，内容包含：接口URL、功能描述、通信协议、请求信息、入参说明、响应信息、返回值说明、调用示例、CURL 示例。
4. 代码与功能开发完成后，先自行校验功能可用性；校验通过后同步生成对应测试用例，并完成基础测试。

## 四、模型行为 & 调度策略
### 4.1 Sonnet 模型专属行为
若当前使用 Sonnet 模型，解读逻辑以**代码字面内容为主**，优先解答字面问题，不主动质疑原有代码设计与假设。

### 4.2 模型调用路径规则
#### （1）常规调用路径
Sonnet 负责主体开发与代码阅读 → codeGraph 辅助代码定位与依赖分析 → Opus 执行复审校验。

#### （2）模型升级切换规则
默认优先使用 Sonnet 快速探测问题复杂度，再按以下规则判定是否升级为 Opus：
- 仅 CRUD 开发、字段补全、标准化套路实现：全程使用 Sonnet
- 满足以下任一条件，**必须升级为 Opus**：
    1. 需求涉及跨 3 个及以上模块；
    2. 工作内容为否定类判断（排查缺失项、问题漏洞，而非罗列现有内容），可搭配复审机制；
    3. Sonnet 输出的方案出现自相矛盾、需要反复修订。
- 问题分析阶段优先使用 Opus，问题落地开发阶段使用 Sonnet。

## 五、codeGraph 使用规范
### 5.1 前置校验
启动工作后**先执行 `codegraph_status` 校验工具可用性**，再开展后续操作。

### 5.2 强制使用场景（不可用 Read/Grep 替代）
codeGraph 可用时，以下场景**必须优先调用对应能力**：
1. 分析、排查、梳理现有代码逻辑 → 使用 `codegraph_context`
2. 定位指定类名、方法名、接口路径 → 使用 `codegraph_search`
3. 修改已有方法前 → 必须调用 `codegraph_callers` + `codegraph_impact` 检查调用方与影响范围
4. 跨模块、跨服务逻辑追踪 → 使用 `codegraph_trace`
5. 输出代码影响范围结论前 → 必须通过 `codegraph_impact` 验证

### 5.3 可豁免场景（可跳过 codeGraph）
满足以下任一情况，无需调用 codeGraph：
1. 使用者已提供**精确文件路径 + 具体行号**；
2. 单文件内少于 10 行的局部小修改；
3. 纯配置文件、文档、独立脚本编辑；
4. 全新空白文件创建。

[//]: # (需要更新文档时，默认更新更新文档，不用提问，保持文档与代码都是最新。)

[//]: # (分析并理解这个项目仓库，生成结构化的完整的Code Wiki文档&#40;md文件&#41;，文件存储在docs/wiki/下。这套文档需要包括项目整体架构、主要模块职责、关键类与函数说明、依赖关系以及项目运行方式等关键信息)

## Project Overview

SCRM PaaS is a metadata-driven low-code platform built as a Java 11 microservices monorepo with Maven. Tenants define custom business objects, fields, layouts, workflows, and permissions via metadata; the runtime engine executes CRUD, business flows, and scheduled tasks against dynamically-created tables. The codebase strictly separates **build-time** (configuration/design via metadata) from **runtime** (execution).

**Version:** 1.0.3-SNAPSHOT

## Tech Stack

Java 11, Spring Boot 2.6.15, Spring Cloud 2021.0.9, Spring Cloud Alibaba 2021.0.5.0, MyBatis-Plus 3.4.2, MySQL (Druid), MongoDB, Redis (Redisson), Nacos (config + discovery), Seata (distributed transactions), Sentinel, RabbitMQ, Kafka, XXL-Job/Quartz/ShedLock, Lombok, MapStruct, Jackson.

## Build & Run Commands

```bash
# Full build (skip tests)
mvn clean install -DskipTests

# Build specific module with dependencies
mvn clean install -pl scrm-paas-runtime/scrm-paas-runtime-server -am -DskipTests

# Build infrastructure module (test locally before pushing)
mvn clean install -pl paas-infrastructure -am

# Run all tests
mvn test

# Run specific test class
mvn test -Dtest=ComponentInstanceMapperTest

# Run specific test method
mvn test -Dtest=ComponentInstanceMapperTest#testInsert

# Run a specific service locally
mvn spring-boot:run -pl scrm-paas-runtime/scrm-paas-runtime-server

# Build with specific Maven profile
mvn clean install -P dev -DskipTests
```

**Maven Profiles:** `dev`, `test`, `pre`, `uat`, `prod` — select based on environment.

## Module Map

15 top-level Maven modules under root `net.trueland.scrm:scrm-paas`:

### Infrastructure (shared libraries)
- **paas-infrastructure** — BOM (`paas-infrastructure-dependencies`), starter parents, common models/context/core/web/annotations/constants, dynamic datasource, delay queue, load balancer, canary release support. All service modules inherit from `paas-infrastructure-starter-parent`.

### Microservices

| Service | Module | Port | App Name |
|---------|--------|------|----------|
| Metadata (design-time) | `scrm-paas-metadata/paas-metadata-server` | 8001 | `scrm-paas-metadata` |
| Runtime engine | `scrm-paas-runtime/scrm-paas-runtime-server` | 8002 | `scrm-paas-runtime-server` |
| Publish pipeline | `scrm-paas-metadata/paas-publish-server` | 8003 | `scrm-paas-publish` |
| Bizflow engine | `scrm-paas-runtime/scrm-paas-runtime-bizflow` | 8004 | `scrm-paas-runtime-bizflow` |
| Runtime extender (CRM) | `scrm-paas-runtime-extender-standard-crm` | 8006 | `scrm-paas-runtime-extender` |
| Integration | `scrm-paas-integration/integration-server` | 8010 | `scrm-paas-integration` |
| Product manager | `scrm-paas-product-manager/product-server` | 8011 | `scrm-paas-product` |
| Message center | `scrm-paas-message/message-server` | 8080 | `scrm-paas-message` |
| UCC (authorization) | `scrm-paas-ucc/ucc-server` | 8080 | `scrm-paas-ucc` |
| Job executor | `scrm-paas-job-executor` | 8081 | `scrm-paas-job-executor` |
| BPM | `scrm-paas-bpm/bpm-server` | 8085 | `scrm-paas-bpm` |
| Runtime gateway | `scrm-paas-runtime-gateway` | 9010 | `scrm-paas-runtime-gateway` |
| OpenAPI gateway | `scrm-paas-openapi-gateway` | 9010 | `scrm-paas-openapi-gateway` |
| XXL-Job admin | `scrm-paas-xxl-job/xxl-job-admin` | — | XXL-Job admin |

### Other
- **scrm-paas-runtime-extender-tenant-jinfa** — Tenant-specific extension (Jinfa)
- **crm-generator** — Code generator utility

## Service Layer Architecture

Each microservice follows a **4-layer pattern**:

```
┌──────────────────────────────┐
│ CONTROLLER LAYER             │  @RestController, @RequestMapping
│ (*.controller.*)             │  → Request validation, returns Rsp<T>
├──────────────────────────────┤
│ SERVICE LAYER                │  @Service classes (NO interface+impl pattern)
│ (*.service.*)                │  → Business logic, tenant context management
├──────────────────────────────┤
│ MAPPER/REPOSITORY LAYER      │  @Mapper, extends BaseMapper<T>
│ (*.mapper.*, *.repository.*) │  → MyBatis dynamic SQL with ${tableName}
├──────────────────────────────┤
│ ENTITY LAYER                 │  PaasMetadataModel or TenantBaseModel
│ (*.entity.*)                 │  → Auto-filled common fields (id, accountId, etc.)
└──────────────────────────────┘
```

**Response Wrapper:** All APIs return `Rsp<T>` with code/msg/data structure. Use `Rsp.success(data)` / `Rsp.fail(code, msg)`.

## Key Architectural Patterns

### Metadata-Driven Custom Objects
Custom objects are defined in `paas_component_object` / `paas_component_instance` tables. Fields, layouts, views, and buttons are all configuration-driven. Use `CustomObjectService` and `ApplicationCacheUtil` to load object definitions (always consider application version and preview mode).

### Dynamic Table Pattern
Business data lives in `paasobj_{objectCode}` tables. `BizDataMapper` uses MyBatis dynamic SQL with `${tableName}` substitution. Generic CRUD via `BizDataRepository` / `BizDataCreateService` / `BizDataUpdateService` / `BizDataRetrieveService`.

This enables **one generic table schema per custom object**, dramatically reducing table proliferation while maintaining schema flexibility.

### Multi-Tenancy (accountId)
- `accountId` is the tenant identifier; every entity carries it for data isolation
- Context: `CrmUserInfoContext.getAccountId()` / `CrmUserInfoContext.setBase(base)`
- Dynamic datasource: `DataSourceUtils.switchover(accountId)` for cross-tenant operations
- Thread-local: `ThreadLocalHelper` for request-scoped context
- **Always set tenant context before database operations**

### Publish Pipeline
`paas-publish-server` publishes metadata from dev to production through ordered handlers: bizObject → dataView → association → permissionPoint → i18n → generalOption → menu → extendAPI → businessFlow → frontCodeFile → stageConfig.

### Tenant Extension Mechanism
`TenantActionExtension` interface enables pluggable tenant-specific logic:
- Discovered via Nacos service discovery
- Cached in Redis: `tenant-extension:{accountId}` (30-min TTL)
- Caffeine local cache for performance
- Invoked when custom object actions match registered extensions

### Multi-Level Caching Strategy
```
Caffeine (local, 30 min) → Redis (distributed) → MyBatis L2 (`PaasMyBatisCache`) → Database
```
- Data view cache TTL: **100 hours** (360000 seconds)
- Cache invalidation: RabbitMQ consumers
- Annotation-based: `@Cacheable`, `@CacheEvict`

### Dual Database
- **MySQL:** Metadata, configurations, transactional data (Druid connection pool)
- **MongoDB:** Logging, high-volume data (collection: `biz_custom_log_{accountId}`)

### Inter-Service Communication (Feign)
- OpenFeign clients in dedicated modules: `*.feign` or `*-api`
- Example: `@FeignClient(value = "scrm-paas-runtime-server")`
- Configured in bootstrap: `@EnableFeignClients({...})`
- Cross-domain data access must use Feign, never direct mapper injection

### Exception Handling & Logging
- Custom exceptions: `BuException(IResultCode, Object[])` with formatted messages
- Global handler: `paas-infrastructure-web:GlobalExceptionHandler` via `@ControllerAdvice`
- AOP aspects: `LogAOPAspect`, `OperationLogRecordAspect`, `DesensitizationAspect`, `LogParamsAspect`
- Audit trail: Operations logged to database for compliance

## Nacos Configuration Groups

All services share configs under group `scrm-paas`:
- `paas-common.yml` — shared settings
- `paas-remote.yml` — remote service endpoints
- `pro-i18n.yml` — internationalization (runtime services)
- `seata-client.yml` — distributed transactions (group: `scrm-paas-seata`)
- `paas-script-security.yml` — script engine security rules
- `paas-message.yml` — message service config

## Entity Base Classes & Conventions

**Entity Inheritance:**
```java
@TableName("paas_component_object")
public class PaasComponentObject extends PaasMetadataModel {
    // Inherited: id, accountId, createdBy, createdAt, updatedAt, deletedFlag, etc.
    private String code;              // Immutable business identifier
    private String name;
    private String description;
}
```

**Common Base Fields (auto-filled):**
- `id` — Snowflake or auto-increment ID
- `accountId` — Tenant identifier (required for isolation)
- `createdBy`, `createdAt` — Auto-filled on insert
- `updatedAt`, `updatedBy` — Auto-filled on update
- `deletedFlag` — Soft delete marker

**Mapper Pattern:**
```java
@Mapper
@CacheNamespace(implementation = PaasMyBatisCache.class)
public interface ComponentObjectMapper extends BaseMapper<PaasComponentObject> {
    // CRUD auto-generated from BaseMapper
}
```

## Coding Conventions

- **Package prefix**: `net.trueland.scrm` for all new code. Legacy `net.trueland.tcloud` is being phased out.
- **No interface+impl pattern for Services** — create Service classes directly using `@Service`.
- **Constructor Injection:** Use `@RequiredArgsConstructor` (Lombok) for mandatory deps; `@Resource` for optional.
- **Jackson only** — use `JsonNode` / `ObjectMapper`. FastJSON allowed only in local scope, never in VO/DTO fields.
- **No cross-domain mapper usage** — services must only use their own domain's Mapper; cross-domain access goes through Feign or Service calls.
- **VO/PO/DTO** do not extend `Serializable`. APIs must not return database entity classes directly; convert to VOs.
- **`code` field** is the entity's unique business identifier (tenant-scoped, immutable). Generated via `CodeGenerator`. `xxCode` fields reference foreign entities.
- **Swagger** annotations required on controllers and request/response objects with examples. Not on PO or Service classes. Use `@ParameterObject` for GET endpoints with object params.
- **Date types**: use `LocalDateTime`, `LocalDate`, `LocalTime` (never `Date` or `Timestamp`).
- **Avoid `Map`/`JSONObject`** as API input/output types — use typed DTOs.
- **DB**: Tables prefixed `paas_` or `paasobj_`. Sort by `id` (not `create_at`). Use snowflake/auto-increment IDs.
- **Metadata tenant ID**: use `CommonConsts.MANAGER_ACCOUNT_ID`.
- **Circular dependencies:** Allowed via `allow-circular-references: true` in bootstrap — use cautiously.

## Testing

**Framework:** JUnit 5 with Spring Boot Test.

**Tenant Context Setup (mandatory):**
```java
@SpringBootTest
@Disabled  // Tests disabled by default - remove to run
class ComponentInstanceMapperTest {
    @Resource
    private ComponentInstanceMapper mapper;

    private final Long accountId = 2184935L;
    private final Base base = new Base(accountId, 1L, "test", "test", 1L, 0, null);

    @Test
    void testMethod() {
        CrmUserInfoContext.setBase(base);   // ALWAYS set tenant context first
        // Test code
    }
}
```

**Key Notes:**
- Most tests marked `@Disabled` by default — remove the annotation to run
- Use accountId `3000050L` for test data (OpenAPI 开发租户)
- Always set `CrmUserInfoContext` before any database operations
- Spring Boot application context starts automatically with `@SpringBootTest`

## Bootstrap Configuration

**Key Application Properties:**
```yaml
spring:
  main:
    allow-bean-definition-overriding: true    # Circular deps allowed
    allow-circular-references: true
  cloud:
    nacos:
      config:
        group: scrm-paas
        extension-configs:
          - data-id: paas-common.yml           # Shared settings
          - data-id: paas-remote.yml           # Service endpoints
          - data-id: pro-i18n.yml              # i18n configs
          - data-id: seata-client.yml          # Transactions
          - data-id: paas-script-security.yml  # Script security
```

**Common Application Annotations:**
```java
@SpringBootApplication
@EnableDiscoveryClient                        // Nacos discovery
@EnableFeignClients({ ... })                 // Feign clients (specify packages)
@EnableCaching                                // Spring Cache
@EnableAsync                                  // Async execution
@EnableAspectJAutoProxy                       // AOP
@MapperScan("net.trueland.scrm.paas.*.**.mapper")
public class PaasRuntimeApplication { }
```

## Branch & CI/CD

- Develop on `dev` branch first, merge to `release` after stabilization. Both require CI deploy.
- Branch naming: `dev-*`, `test*`, `pre*`, `uat*`, `hotfix*`, `prod*`, `release*`, `master`
- GitLab CI/CD pipeline: `init → test → deploy → build → docker → notify`
- Maven profiles: `dev`, `test`, `pre`, `uat`, `prod`
- SonarQube quality checks must not be disabled.
- Infrastructure module: test locally with `mvn install -pl paas-infrastructure -am` before pushing; include test cases for all changes.

### 文件上传 endpoint 配置注意事项（MinIO）
- `file-config.endpoint` 配置 MinIO 地址时，**必须带 `http://` 前缀**，否则 `FileClientConfig.getEndpoint()` 会自动拼接 `https://`（`FileClientConfig.java:139`），导致 HTTPS 连接 MinIO HTTP 端口（9000）失败
- 正确格式：`endpoint: http://172.16.198.62:9000`
- 错误格式：`endpoint: 172.16.198.62:9000`

## Domain Vocabulary

| Term | Meaning |
|------|---------|
| ComponentObject | Custom/preset object definition (主信息) |
| ComponentInstance | Field definition on an object (字段信息) |
| PrimaryInstance | The field chosen as the object's primary display field (hyperlink in list views) |
| BusinessType | Business type classification (业务类型) |
| TenantActionExtension | Plugin interface for tenant-specific customizations |
| DataPermission | Row-level access control rules (data-level RBAC) |
| `custobj` | Package for custom object logic |
| `generopt` | Package for general option sets (通用选项集) |
| `layout` | Package for layouts, components, buttons, views |
| `busitype` | Package for business types |
| `workflow` | Package for business flows (业务流程) |

## Common Patterns & Quick Reference

**Setting Tenant Context:**
```java
CrmUserInfoContext.setBase(new Base(accountId, userId, username, realname, roleId, status, timezone));
CrmUserInfoContext.getAccountId();  // Retrieve tenant ID
```

**Dynamic Table Naming:**
```java
// 实际表名包含租户ID后缀: paasobj_{objectCode}_{accountId}
String tableName = "paasobj_" + objectCode + "_" + accountId;
// 如: paasobj_c_customer_3000050, paasobj_user_3000050
```

**Feign Client Usage:**
```java
@FeignClient(value = "scrm-paas-runtime-server", contextId = "customObjectFeign")
public interface CustomObjectFeign {
    @PostMapping("/object/batchSelect")
    Rsp<List<PaasComponentObjectReturn>> batchSelect(@RequestBody Set<String> codes);
}

// Inject and use:
@RequiredArgsConstructor
@Service
public class SomeService {
    private final CustomObjectFeign customObjectFeign;

    public void someMethod() {
        Rsp<List<PaasComponentObjectReturn>> result = customObjectFeign.batchSelect(objectCodes);
    }
}
```

**Exception Handling:**
```java
// Throw formatted exception
throw new BuException(IResultCode.PARAM_ERROR, new Object[]{"field_name"});

// Global handler returns: Rsp with code, localized msg, null data
```

## MCP MySQL 连接

### 连接信息

| 参数 | 值 |
|------|-----|
| Host | `172.31.252.103` |
| Port | `3306` |
| User | `paas` |
| Password | `d_GsjDPDn3E_Jew` |
| Database | `paas_env03_db1` |
| 模式 | **只读**（写操作生成 SQL 给用户执行） |
| MCP 配置 | `D:\workspace\scrm-paas\.mcp.json` |

### MCP 工具使用

- `mcp__mysql__mysql_query(sql)` — 执行只读 SELECT 查询
- 跨库查询使用 `schema.table` 格式（如 `paas_env03_db1.paasobj_c_customer_3000050`）
- 每次会话首次使用时自动连接，无需手动初始化
- **强制规则：需要查询 MySQL 数据时，优先使用 MCP `mcp__mysql__mysql_query` 工具，不要建议用户手动执行查询类的SQL **
-  **强制规则：禁止直接修改数据库数据，禁止使用 UPDATE、DELETE、 INSERT 语句，需要生成SQL用户来执行**

### MCP 配置修改后

修改 `.mcp.json` 后需 `/restart` 重启会话生效。

## OpenAPI 开发租户信息

### 目标租户

| 项目 | 值 |
|------|-----|
| accountId | `3000050L` |
| application_code | `APP_CRM` |
| 当前 metadata_version | `20260610103538203`（最新 master 版本） |
| 数据库 Schema | `paas_env03_db1` |

### 数据量概况 (3000050)

| 对象 | objectCode | 表名 | 记录数 |
|------|------------|------|--------|
| 客户 | `c_customer` | `paasobj_c_customer_3000050` | 6 |
| 商机 | `c_business` | `paasobj_c_business_3000050` | 5 |
| 联系人 | `c_contact` | `paasobj_c_contact_3000050` | 4 |
| 合同 | `c_contract` | `paasobj_c_contract_3000050` | 0 |
| 报价单 | `c_quotation` | `paasobj_c_quotation_3000050` | 1 |
| 用户 | `user` | `paasobj_user_3000050` | 353 |
| 部门 | `department` | `paasobj_department_3000050` | 215 |

## 元数据版本规则

- **paasobj_ 业务表**：`paasobj_{objectCode}_{accountId}`（**无版本**，直接读写）
- **paas_component_object / paas_component_instance**：带 `metadata_version`，查询时需过滤 `metadata_version = 'master'` 获取当前生效版本
- **paas_component_instance**：`field_type` 含义 — `0`=系统字段, `1`=业务字段, `2`=主字段(PrimaryInstance), `null`=扩展字段
- **多 application_code**：同一租户不同应用共用对象定义，查询时需同时指定 `application_code`
- **instance_type 枚举**：`input`=文本, `textarea`=长文本, `single`=单选, `multi`=多选, `date`=日期, `number`=数字, `money`=金额, `boolean`=布尔, `relation`=关联对象, `personSingle`=人员单选, `personMulti`=人员多选, `deptSingle`=部门单选, `deptMulti`=部门多选, `email`=邮箱, `web`=网站, `percent`=百分比, `cascader`=级联, `file`=文件, `img`=图片, `computer`=计算字段, `id`=数据主键, `setNumber`=自动编号, `encrypted`=加密

## 选项集（数据字典）规则

- 选项集存储在 `paas_general_option` (选项集定义) + `paas_general_option_item` (选项值)
- 查询方式：`SELECT * FROM paas_env03_db1.paas_general_option WHERE account_id = 3000050 AND is_delete = 0`
- 选项集 code 对照（3000050 租户需按实际查询）：
  - `GO_customer_level` → 客户等级
  - `GO_g1e0pz4255` → 商机阶段
  - `GO_n2m30yjggo` → 合同状态
  - `GO_lq9vwhohit` → 输单原因
  - `GO_b00oy194er` → 客户来源
  - `GO_contractSubject` → 签约主体
  - `userSex` → 性别
  - `userEnableStatus` → 启用状态
  - `userOnPostStatus` → 在职状态

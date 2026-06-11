# SCRM PaaS Claude Code Skills

本目录包含为 SCRM PaaS 低代码平台项目定制的 Claude Code Skills，用于快速生成符合项目规范的代码。

## Skills 列表

| Skill 名称 | 功能说明 | 使用场景 |
|-----------|---------|---------|
| crud | 一键生成完整业务模块 | 新建 Controller/Service/Mapper/Entity/VO 等完整模块 |
| project-conventions | 项目编码规范指南 | 了解项目编码规范、代码审查参考 |

## 目录结构

```
.claude/skills/
├── README.md                    # 本说明文件
├── crud/
│   └── SKILL.md                 # 一键生成所有模块
└── project-conventions/
    └── SKILL.md                 # 项目编码规范指南
```

## 快速开始

使用 `/crud` 命令，描述你的业务需求即可一键生成完整模块代码：

```
请帮我在 metadata 服务中创建一个XXX功能，包含以下字段：
- 字段1（类型，说明）
- 字段2（类型，说明）
- ...
```

这将自动生成：
- SQL 建表语句（`paas_` 前缀）
- Entity 实体类（继承 `PaasMetadataModel` 或 `TenantBaseModel`）
- Mapper 接口（`extends BaseMapper<T>`，MyBatis-Plus）
- Service 业务层（直接 `@Service` 类，无 interface+impl）
- Controller 控制器（`Rsp<T>` 响应，OpenAPI 3 注解）
- VO/DTO 请求/响应对象（`@Schema` 注解）


## 技术栈

| 类别 | 技术 | 版本 |
|------|------|------|
| JDK | Java | 11 |
| 基础框架 | Spring Boot | 2.6.15 |
| 微服务 | Spring Cloud | 2021.0.9 |
| 微服务组件 | Spring Cloud Alibaba | 2021.0.5.0 |
| ORM | MyBatis-Plus | 3.4.2 |
| 数据库 | MySQL | Druid 连接池 |
| 缓存 | Redis | Redisson |
| 配置/注册 | Nacos | 配置中心 + 服务发现 |
| 分布式事务 | Seata | - |
| 消息队列 | RabbitMQ / Kafka | - |
| 定时任务 | XXL-Job / Quartz / ShedLock | - |
| API 文档 | OpenAPI 3 (Swagger v3) | `@Schema`, `@Tag`, `@Operation` |
| 工具 | Lombok, Jackson | - |

## 项目编码规范要点

### 包前缀
- 新代码：`net.trueland.scrm.paas`
- 遗留代码：`net.trueland.tcloud`（正在淘汰）

### 分层架构
```
Controller (@RestController) → 返回 Rsp<T>
    ↓
Service (@Service 直接类) → 业务逻辑、事务
    ↓
Mapper (@Mapper extends BaseMapper<T>) → MyBatis-Plus 动态 SQL
    ↓
Entity (extends PaasMetadataModel / TenantBaseModel) → 自动填充公共字段
```

### 多租户
- `accountId` 为租户标识，所有查询必须过滤
- 上下文：`CrmUserInfoContext.getNonNullAccountId()`
- 动态数据源：`DataSourceUtils.switchover(accountId)`

### 数据库命名
- 系统表：`paas_` 前缀
- 动态业务表：`paasobj_` 前缀
- 字段：蛇形命名

## 注意事项

1. 生成的代码需要根据实际所属微服务模块放到对应目录
2. 复杂业务逻辑需要在 Service 层手动完善
3. 建议生成后编译验证：`mvn compile -pl <module> -DskipTests`
4. 跨服务数据访问必须通过 Feign，禁止直接注入其他服务的 Mapper

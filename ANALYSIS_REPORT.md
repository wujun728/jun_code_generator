# 代码生成器目录全量分析报告（RuoYi 主力方向）

> 分析时间：2026-05-27  
> 分析工具：CodeGraph 索引（1919 文件 / 20626 节点 / 42287 边）+ 直接文件扫描  
> 分析范围：`D:/workspace_github/jun_code_generator/` 下 21 个生成器子目录及其全部嵌套子项目

---

## 一、目录全景（侦察发现）

顶层 21 个目录里，有 **2 个是容器目录**，里面再嵌套了独立的 Maven 项目：

| 容器 | 嵌套子项目 | 实际是什么 |
|------|-----------|----------|
| `jun-code-freemarker/` | SpringBootCodeGenerator、jun-code-spring-boot-starter、jun_code_generator_deep、**xxl-deep**、**xxl-deep-new** | 5 个独立项目 + 顶层 xxl-deep / xxl-deep-new 的**重复拷贝** |
| `jun-code-velocity/` | jun-code-generator、jun-code-generator6、jun_code_thymeleaf、jun_codegenerator、**ruoyi-gen-mybatis-plus** | 5 个项目，**4 份"基于若依精简"克隆** + 1 份 RuoYi+MP 改版 |

**实际待分析项目数 = 顶层 21 - 2 容器 + 10 嵌套 = 29 个独立项目**。

---

## 二、逐项评估（核心数据）

### A. RuoYi 体系（主力候选）

| # | 项目 | 技术栈 | RuoYi 兼容 | 形态 | 评分 | 定位 |
|---|------|--------|----------|------|------|------|
| 1 | **jun-xiaobear-gen** | SB 2.7.18 + MyBatis + Vue 3.5 + Element Plus 2.8 | ✅ 完整 RuoYi-Vue 风格 | Web UI（前后端分离） | ⭐⭐⭐⭐⭐ | **主力** |
| 2 | **jun_codegen_simple** | SB + MyBatis-Plus + Velocity | ✅ 原生 RuoYi（BaseController/AjaxResult/@PreAuthorize） | CLI 调用 | ⭐⭐⭐⭐ | **次主力（CLI 派）** |
| 3 | jun-code-velocity/ruoyi-gen-mybatis-plus | SB + MP + Velocity | ✅ `com.ruoyi.*` 包名，MP 改版 | Web | ⭐⭐⭐⭐ | 模板捐赠源（v3 Vue 解耦页） |
| 4 | jun_code_generator_depp_mybatisplus | SB 2.4 + MP 3.4 + Freemarker + RuoYi 3.5.0 | ✅ 依赖 RuoYi | CLI | ⭐⭐ | 删除（文档缺失，能力被覆盖） |
| 5 | jun-code-velocity/jun-code-generator | SB + 若依精简 | ✅ | Web | ⭐⭐ | 删除（重复克隆） |
| 6 | jun-code-velocity/jun-code-generator6 | 同上 | ✅ | Web | ⭐⭐ | 删除（重复克隆） |
| 7 | jun-code-velocity/jun_code_thymeleaf | 同上 + Thymeleaf | ✅ | Web | ⭐⭐ | 删除（重复克隆） |
| 8 | jun-code-velocity/jun_codegenerator | 同上 | ✅ | Web | ⭐⭐ | 删除（重复克隆） |
| 9 | **online-code-gen** | SB 2.5 + Enjoy + Vue3 + MonacoEditor | ⭐ 致敬 RuoYi | Web 在线版 | ⭐⭐⭐⭐ | **保留**（用户决策） |

### B. 非 RuoYi 体系

| # | 项目 | 技术栈 | 定位 | 评分 | 处置 |
|---|------|--------|------|------|------|
| 10 | easy-generator | SB 2.4 + MP 3.2 + Freemarker + Layui | 可视化 Web UI，多数据源 + 项目模板管理 | ⭐⭐⭐⭐ | 删除（能力 xiaobear 已覆盖） |
| 11 | code-generator-layui | SB 2.7 + Thymeleaf + Layui + MyBatis | 传统 Web UI 生成器 | ⭐⭐⭐ | 删除 |
| 12 | auto-code-ui | SpringMVC + Servlet 3.0 + Layui | 可视化插件（依赖外部 auto-code 库） | ⭐⭐ | 删除 |
| 13 | easy-java | 纯 Java CLI | 最简 CLI 工具 | ⭐ | 删除 |
| 14 | jfinal-generator | JFinal 4.9 + Druid | JFinal 框架专用 | ⭐ | 删除（框架不兼容） |
| 15 | simplest-boot | SB 2.7 | 代码生成 demo（小） | ⭐ | 删除 |
| 16 | simplest-jpa | SB 2.7 + JPA + QueryDSL | JPA 查询工具库（不是生成器） | ⭐ | 删除 |
| 17 | springboot-generate | SB 3.0 + Beetl | 轻量生成（仅 1 模板文件） | ⭐ | 删除 |

### C. 工具/平台

| # | 项目 | 定位 | 评分 | 处置 |
|---|------|------|------|------|
| 18 | **jun_excel_generate_sql_script** | Excel → 建表 SQL（稀缺工具） | ⭐⭐⭐⭐ | **保留** |
| 19 | **xxl-deep-new** | SB 3.5 + Java 17 企业开发平台 | ⭐⭐⭐⭐ | **保留**（用户决策） |
| 20 | xxl-deep（顶层） | SB 2.2 老版本 | ⭐ | 删除（被 xxl-deep-new 替代） |
| 21 | jun-code-freemarker/xxl-deep | 容器内重复拷贝 | ⭐ | 删除 |
| 22 | jun-code-freemarker/xxl-deep-new | 容器内重复拷贝 | ⭐ | 删除 |
| 23 | jun-code-freemarker/SpringBootCodeGenerator | "大狼狗"在线生成器 | ⭐⭐ | 删除 |
| 24 | jun-code-freemarker/jun-code-spring-boot-starter | Starter 包 | ⭐⭐ | 删除 |
| 25 | jun-code-freemarker/jun_code_generator_deep | 实验项目 | ⭐ | 删除 |

### D. 伪生成器 / 空壳

| # | 项目 | 真实身份 | 处置 |
|---|------|---------|------|
| 26 | jun-mybatisplus-api | SpringBoot CRUD 脚手架（User 演示），含 1 个测试用 CodeGenerator.java | 删除 |
| 27 | jun-mybatisplus-api-v2 | 同上的 v2，加了 Redis/HttpSession/图形验证码 | 删除 |
| 28 | jun-mybatisplus-generator | 几乎空目录，仅 `src/` 占位 | 删除 |
| 29 | jun-code-layui_snowy | 仅 1 个测试入口文件 `CodeGeneratorMain.java` | 删除 |

> 注：`jun-mybatisplus-api` 与 `jun-mybatisplus-api-v2` 的 v1 vs v2 差异：v1 用 JwtUtils，v2 换成 HttpSession+Redis+ImageCode。**两者都不是代码生成器**，是早期 SpringBoot 教程的 CRUD 样例。

---

## 三、最终选型与目录命运

### 🎯 保留（5 项）

| 角色 | 目录 | 理由 |
|------|------|------|
| **主力** | `jun-xiaobear-gen/` | 唯一具备「Web UI + Vue3 前端 + RuoYi 后端约定 + 整项目脚手架」三件套；模板双引擎（mybatis + mybatisPlus）；自带动态数据源、字典导出、Quartz 定时清理 |
| **次主力** | `jun_codegen_simple/` | CLI 派 RuoYi 生成器，Vue3 模板 + 子域模板，与 xiaobear 形成 UI/CLI 双形态 |
| **工具** | `jun_excel_generate_sql_script/` | Excel → SQL 稀缺能力 |
| **企业平台** | `xxl-deep-new/` | 用户决策保留 |
| **在线版** | `online-code-gen/` | 用户决策保留 |

### 🗑 删除（24 项 + 2 容器壳）

详见第二节"处置"列。

### 🔒 强制保留（系统目录/文件）

`.claude` `.codegraph` `.git` `.idea` `.vscode` `.omc` `doc1` `.gitignore` `AI_CODE.txt` `codegraph.md.md` `pom.xml`

---

## 四、能力合并清单

### 风险声明
原任务要求"模板 + Java 逻辑 + 配置**全量合并**"。Java 全量合并不可行 —— 落选项目分散使用 `com.ruoyi.*`、`com.dwj.generator.*`、`com.easyjava.*`、`com.dcy.generator.*`、`com.jfinal.*` 等 7+ 种包名，直接 copy 会立刻破坏 `jun-xiaobear-gen`（`com.javaxiaobear.*`）的可编译性。

### 实际合并策略：分层合并

#### ✅ 模板层（直接并入，新增不覆盖）

目标目录：`jun-xiaobear-gen/gen-admin/src/main/resources/vm/`

| 来源 | 目标路径 | 内容 |
|------|---------|------|
| `jun-code-velocity/ruoyi-gen-mybatis-plus/.../vm/vue/v3/` | `vm/vue/v3/` | `index.vue.vm`、`index-tree.vue.vm`（Vue3 原生） |
| `ruoyi-gen-mybatis-plus/.../vm/vue/{edit,detail}.vue.vm` | `vm/vue/decoupled/` | 新增/详情解耦页（独立组件化） |
| `ruoyi-gen-mybatis-plus/.../vm/java/sub-domain.java.vm` | `vm/java/extra/` | 子域模板（如缺失） |
| `jun_codegen_simple` 内 Vue3 模板 | `vm/vue/v3/`（去重） | 备用 v3 模板 |

#### ✅ SQL 层（备选 SQL）

| 来源 | 目标 |
|------|------|
| `jun-code-velocity/jun-code-generator/jun_main_generator.sql` | `jun-xiaobear-gen/sql/extra/ruoyi-gen-menu.sql` |
| 其它若依精简版的菜单 SQL | `jun-xiaobear-gen/sql/extra/` |

#### ❌ Java 层（不合并，仅记录）

xiaobear 已经具备：
- ✅ 动态数据源切换（`DynamicDataSource.java` + `DataSourceSwitcher.java`）
- ✅ 数据库字典导出（`GenerateDataDictUtils.java` + `DatabaseDictionaryExportController.java`）
- ✅ 整项目脚手架生成（`CompleteProjectGenerator.java`）
- ✅ Quartz 定时清理（`RegularCleaningTables.java`）
- ✅ Velocity 模板引擎完整封装

落选项目的"特色"对照：

| 落选项 | 声称特色 | xiaobear 是否已有 | 结论 |
|--------|---------|-----------------|------|
| easy-generator | 多数据源 + 项目模板管理 | ✅ 已有等价 | 不合并 |
| easy-generator | 模板配置可视化 UI | ✅ Vue3 UI 已覆盖 | 不合并 |
| code-generator-layui | Thymeleaf 服务端渲染 | ❌ 但偏离 Vue 方向 | 不合并 |
| jun_codegen_simple | sub-domain.java 子域模板 | 仅模板缺，Java 逻辑不缺 | **仅合并模板** |
| jun_code_generator_depp_mybatisplus | DTO 分层（Input/Output） | ❌ | 仅作设计参考记录，不引入代码 |
| ruoyi-gen-mybatis-plus | Vue3 v3 模板、edit/detail 解耦 | 模板缺 | **仅合并模板** |

#### ❌ 配置层（不合并）

xiaobear 已有 `application.yml` + `generator.yml`，落选项目的 yml 都是各自业务样板，无价值。

---

## 五、未合并的 Java 能力 / 后续手动集成清单

如未来需要把以下能力补回到 `jun-xiaobear-gen`，按本清单手动集成（每条都需要修改包名+依赖适配）：

1. **DTO 分层（Input/Output）模板** —— 来源：`jun_code_generator_depp_mybatisplus`
   - 需新增 `vm/java/dto-input.java.vm`、`vm/java/dto-output.java.vm`
   - 在 `VelocityUtils.java` 中追加 DTO 命名变量

2. **Feign 客户端 / 远程服务模板** —— 来源：`jun_code_generator_depp_mybatisplus`
   - 新增 `vm/java/feign-client.java.vm`
   - 微服务场景才需要，单体项目跳过

3. **关系建模（一对一/一对多/多对多）** —— 来源：`auto-code-ui`
   - 需要扩展 `GenTable.java` / `GenTableColumn.java` 增加关系字段
   - 改造 `GenController.java` 增加关系配置接口
   - 工作量大，按需启动

4. **Excel → 建表 SQL** —— `jun_excel_generate_sql_script` **独立保留**，不并入 xiaobear

---

## 六、`jun-xiaobear-gen` 主力使用指引

### 启动方式

**后端**：
```bash
cd jun-xiaobear-gen/gen-admin
mvn -DskipTests compile
mvn spring-boot:run
# 默认端口见 src/main/resources/application.yml
```

**前端**：
```bash
cd jun-xiaobear-gen/gen-ui
npm install
npm run dev
# 默认 Vue3 + Element Plus
```

**初始化数据库**：执行 `jun-xiaobear-gen/sql/xiaobear-gen.sql`

### 模板修改入口

| 想改什么 | 改哪个目录 |
|---------|----------|
| Java 实体/Mapper/Service/Controller | `gen-admin/src/main/resources/vm/java/mybatisPlus/` |
| MyBatis XML | `gen-admin/src/main/resources/vm/java/mybatisPlus/xml/mapper.xml.vm` |
| Vue3 列表/详情/编辑页 | `gen-admin/src/main/resources/vm/java/mybatisPlus/`（待补 v3 目录） |
| 整项目脚手架（Application/yml） | `gen-admin/src/main/resources/vm/project/backend/` |
| 公共类（AjaxResult/BaseController/PageResult） | `gen-admin/src/main/resources/vm/project/backend/common/` |
| 数据库变量映射 | `GenConstants.java` |
| 模板渲染变量 | `VelocityUtils.java` |

### 关键 Java 类（CodeGraph 索引）

| 类 | 符号数 | 职责 |
|---|-------|-----|
| `GenController.java` | 75 | 生成器 REST API 入口 |
| `GenTableServiceImpl.java` | 72 | 表元数据 + 生成流程 |
| `CompleteProjectGenerator.java` | 77 | 整项目脚手架 |
| `VelocityUtils.java` | 36 | 模板渲染上下文构建 |
| `DatabaseSourceConfig.java` | 51 | 动态数据源 |
| `GenTable.java` | 99 | 表元模型 |
| `GenTableColumn.java` | 80 | 列元模型 |

### 二次定制建议

1. **新增模板族**：在 `vm/` 下新建子目录（如 `vm/java/dubbo/`），在 `GenConstants` 中注册新枚举，在 `VelocityUtils.prepareContext()` 中加分支
2. **新增字段映射**：修改 `GenConstants.COLUMNTYPE_*` 常量
3. **改 Vue 风格**：直接改 `vm/vue/v3/*.vue.vm`，模板中可用 `${ColumnName}` `${columnName}` `${functionName}` 等变量
4. **接入企业字典**：在 `vm/java/mybatisPlus/controller.java.vm` 中追加字典翻译注解模板

---

## 七、最终目录清单（执行删除后）

```
D:/workspace_github/jun_code_generator/
├── .claude/                        # 系统保留
├── .codegraph/                     # 系统保留
├── .git/                           # 系统保留
├── .idea/                          # 系统保留
├── .omc/                           # 系统保留
├── .vscode/                        # 系统保留
├── .gitignore                      # 系统保留
├── AI_CODE.txt                     # 系统保留
├── codegraph.md.md                 # 系统保留
├── pom.xml                         # 系统保留
├── ANALYSIS_REPORT.md              # 本报告
├── doc1/                           # 用户保留
│
├── jun-xiaobear-gen/               # ★ 主力（Web UI）
├── jun_codegen_simple/             # ★ 次主力（CLI）
├── jun_excel_generate_sql_script/  # ★ Excel → SQL 工具
├── xxl-deep-new/                   # 用户保留
└── online-code-gen/                # 用户保留
```

执行删除前，先在 `jun-xiaobear-gen/gen-admin/src/main/resources/vm/` 下完成模板合并并通过 `mvn compile` 验证。

---

## 八、执行清单（待审批后执行，本次仅产出报告）

- [ ] Step 1：抽取模板到 xiaobear（Vue3 v3、decoupled、sub-domain）
- [ ] Step 2：整理备选 SQL 到 `sql/extra/`
- [ ] Step 3：`mvn -q -DskipTests compile` 验证主力工程
- [ ] Step 4：物理删除 24 个项目 + 2 个容器壳
- [ ] Step 5：CodeGraph 重新索引、核对文件数大幅下降

---

*报告生成完毕。删除/合并未执行，待用户审批后单独发起。*

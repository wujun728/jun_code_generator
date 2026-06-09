# 四模块合并 + Layui前端重写 — 会话记录

## 任务概述

将 `jun-code-freemarker/` 下的4个代码生成模块合并为1个统一模块（`jun-code-spring-boot-starter`），并将xxl-deep的前端页面用Layui重写后放入webapp目录，使页面可访问并生成代码。

## 4个原始模块

| 模块 | 特点 |
|------|------|
| `jun_code_generator_deep` | 深度版，含v2/zd模板组，数据库选表模式 |
| `jun-code-spring-boot-starter` | Starter自动配置，6个基础模板，Layui前端 |
| `SpringBootCodeGenerator` | Vue+ElementUI前端，XSS过滤，多dataType解析 |
| `xxl-deep` | AdminLTE+CodeMirror前端，template.json枚举模板 |

## 完成的工作

### Phase 1: 后端合并

#### 1.1 安全/配置类（6个文件创建）

- `io.github.wujun728.generator.config.XssFilter.java` — XSS请求包装Filter
- `io.github.wujun728.generator.config.XssHttpServletRequestWrapper.java` — XSS请求包装器
- `io.github.wujun728.generator.config.HTMLFilter.java` — HTML标签过滤工具（~530行）
- `io.github.wujun728.generator.config.GlobalDefaultExceptionHandler.java` — 全局异常处理，返回Result
- `io.github.wujun728.generator.config.WebMvcConfig.java` — 注册XssFilter，静态资源处理
- `io.github.wujun728.generator.config.ServerConfig.java` — 启动URL日志

#### 1.2 统一Controller

- 创建 `io.github.wujun728.generator.controller.CodeGeneratorController.java`
- 接口：
  - `GET /codeGenerate` — 简单SQL生成（兼容xxl-deep）
  - `POST /code/generate` — JSON POST（支持ParamInfo配置+多dataType）
  - `GET /template/all` — 返回template.json模板列表
  - `GET /` `/generate` `/codeGenerate/sqlPaste` — 页面路由
- 删除了4个冗余Controller（controller2/下的2个 + controller/下的2个）

#### 1.3 template.json

- 创建 `resources/template.json`，覆盖25个模板组
- 模板组清单：mybatis-plus-single(v5-paas/v3/v2/v1/zd/single), mybatis-plus(v2/v1/标准), mybatis(v1/标准), jpa(starp/v1/标准), beetlsql, common-mapper, tk-mybatis, tk-mapper, jdbc-template-v1, jdbctemplate, db-record, renren-fast, ui, util, bi, cloud

#### 1.4 实体层激活

- `ReturnT.java` — 从注释状态激活，HashMap继承版
- `TemplateConfig.java` — 从注释状态激活

### Phase 2: 模板汇总

- 复制 `mybatis-plus-single-v2/` 12个ftl文件（从deep模块）
- 复制 `mybatis-plus-single-zd/` 12个ftl文件（从deep模块）
- 保留6个顶层基础模板（controller/dao/model/service/service_impl/mybatis.ftl）

### Phase 3: Layui前端重写

- 创建 `webapp/templates/generate/sqlPaste.html` — Layui+CodeMirror SQL粘贴页
- 创建 `webapp/static/js/sqlPaste.js` — 生成逻辑（AJAX POST、模板加载、输出Tab切换）
- 复制 CodeMirror 静态资源到 `webapp/static/plugins/codemirror/`（lib/mode/addon）
- 修改 `webapp/templates/generate/index.html` — 添加"切换到SQL粘贴模式"导航链接

### Phase 4: 清理冗余

- 删除 `jun-code-freemarker/xxl-deep/` 整个目录
- 删除 `jun-code-freemarker/xxl-deep-new/` 整个目录
- 删除 `jun-code-freemarker/jun_code_generator_deep/` 整个目录
- 删除 `jun-code-freemarker/SpringBootCodeGenerator/` 整个目录
- 更新 parent pom.xml → 只保留 `jun-code-spring-boot-starter` module
- 删除冗余Java文件：controller2/目录、utils/FreemarkerTool.java、utils/CodeGeneratorMain.java、spring.factories.bk

### Phase 5: 编译验证

- `mvn clean compile` → BUILD SUCCESS
- 27个Java源文件全部编译通过

## 最终项目结构

```
jun-code-freemarker/
├── pom.xml (parent, 只引用jun-code-spring-boot-starter)
└── jun-code-spring-boot-starter/
    ├── pom.xml
    └── src/main/
        ├── java/io/github/wujun728/generator/
        │   ├── config/      (6个配置类: XssFilter, XssHttpServletRequestWrapper, HTMLFilter, GlobalDefaultExceptionHandler, WebMvcConfig, ServerConfig)
        │   ├── controller/   (CodeGeneratorController 统一控制器)
        │   ├── entity/       (ClassInfo, FieldInfo, ParamInfo, ReturnT, TemplateConfig, NonCaseString)
        │   ├── service/      (GeneratorService, GeneratorServiceImpl)
        │   └── utils/        (TableParseUtil, FreemarkerUtil, GeneratorUtil)
        ├── resources/
        │   ├── template.json (25个模板组定义)
        │   ├── templates/    (6个基础ftl + mybatis-plus-single-v2/ + mybatis-plus-single-zd/ + 其他模板组目录)
        │   └── spring.factories
        └── webapp/
            ├── templates/generate/
            │   ├── index.html      (数据库选表模式, Layui)
            │   └── sqlPaste.html   (SQL粘贴模式, Layui+CodeMirror)
            └── static/
                ├── js/sqlPaste.js
                ├── plugins/codemirror/ (lib/mode/addon)
                ├── plugins/layui/
                ├── assets/ (libs/module/js/css)
                └── js/common.js
```

## 关键设计决策

1. **统一控制器** — 1个Controller替代4个，支持GET简单模式和POST JSON模式
2. **Layui前端** — 不用Vue/ElementUI，纯Layui+jQuery+CodeMirror，与现有webapp风格一致
3. **Result vs ReturnT** — 保留两个响应包装类，Layui页面用Result，兼容接口用ReturnT
4. **template.json** — 动态模板枚举，前端从 `/template/all` 加载并渲染模板按钮
5. **XSS过滤** — 从SpringBootCodeGenerator移植，但排除 `/code/generate` POST JSON请求体中的 `<` 字符误过滤

## 会话日期

2026-06-04
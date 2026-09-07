# 项目在 VS Code 中可运行的原因分析

## 1. VS Code 配置 `.vscode/settings.json`

```json
{
    "java.configuration.updateBuildConfiguration": "interactive",
    "maven.executable.path": "D:\\Java\\apache-maven-3.9.8\\bin\\mvn.cmd",
    "java.compile.nullAnalysis.mode": "automatic"
}
```

- **`maven.executable.path`** — 显式指定 Maven 可执行文件路径，VS Code 的 Maven 插件使用此路径来执行构建命令
- **`java.configuration.updateBuildConfiguration`** — 设为 `interactive`，当 pom.xml 变更时提示用户更新项目配置而非静默执行
- **`java.compile.nullAnalysis.mode`** — 启用空值分析，辅助代码提示和编译检查

**没有 `launch.json` / `tasks.json`** — 项目不依赖 VS Code 专属调试配置，纯靠 Maven 插件和终端运行。

## 2. 标准 Maven 多模块结构

```
jun_code_generator/                  (pom 聚合父模块)
├── jun-code-freemarker/             (中间 pom)
│   └── jun_code_freemarker_deep/
│       └── jun_code_freemarker_deep_admin/   [Spring Boot, port 8080]
├── jun-mybatisplus-generator/       [CLI 工具，非 Spring Boot]
├── jun_code_velocity/               (中间 pom)
│   ├── my-generator/                [Spring Boot, port 8888]
│   │   └── generator-web/           [Vue 2 预构建前端]
│   └── jun-xiaobear-gen/
│       ├── gen-admin/               [Spring Boot, port 8081]
│       └── gen-ui/                  [Vue 3 前端]
└── jun_excel_generate_sql_script/   [CLI 工具，非 Spring Boot]
```

VS Code 的 **"Extension Pack for Java"** 自动识别 `pom.xml`，解析依赖并构建类路径。

## 3. 三个 Spring Boot 子模块均可独立运行

| 模块 | 端口 | 主类 | 数据库 |
|------|------|------|--------|
| `my-generator` | 8888 | `com.template.StartApplication` | `localhost:3307/db_generator` |
| `jun_code_freemarker_deep_admin` | 8080 | `io.github.wujun728.admin.Application` | 外部库 `jun-db-activerecord` |
| `gen-admin` | 8081 | `com.javaxiaobear.GenAdminApplication` | `localhost:3307/db_test` |

每个模块的 `application.yml` 中都有完整的数据源 / 服务端口 / MyBatis 配置，开箱即用。

## 4. 前端项目配套完整

- **`gen-ui`** (Vue 3 + Element Plus) — 有 `package.json` 和 `vue.config.js`，代理到 `localhost:8081`，`npm run dev` 即可启动
- **`generator-web`** (Vue 2 + Element UI) — 预构建静态文件已存在于源码树，可直接被 `my-generator` 静态资源服务加载

## 5. 调试方式（无需 launch.json）

在 VS Code 中可直接通过以下方式运行：

- **Maven 面板** → 展开模块 → 右键 `spring-boot:run`
- **集成终端** 执行 `mvn spring-boot:run -pl <模块路径> -am`
- **前端** 在 `gen-ui/` 目录下执行 `npm run dev`
- **Java 主类** 直接在文件中点击 `main` 方法上的 `Run` 或 `Debug` 按钮（VS Code Java 扩展提供）

## 总结

| 要素 | 是否必备 | 说明 |
|------|----------|------|
| `.vscode/settings.json` | 辅助 | 非必需，但确保 Maven 路径正确 |
| `launch.json` | **不需要** | 纯 Maven 项目，插件提供运行入口 |
| `tasks.json` | **不需要** | 可通过 Maven 面板 / 终端启动 |
| Maven Wrapper | **不需要** | 要求本地安装 Maven |
| Extension Pack for Java | 必需 | 提供 Java 语言支持、Maven 集成、调试 |
| MySQL `localhost:3307` | 必需 | 所有模块依赖此数据库 |
| 前端 Node.js | 仅前端需要 | `gen-ui` 需要 Node >= 20 |
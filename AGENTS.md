# AGENTS.md

## Repo Overview

This is a **collection of independent Java code generators** (not a single app). Each sub-module uses different template engines (Velocity, Freemarker) to produce CRUD boilerplate from database table metadata. Based on/forked from RuoYi-Vue and XXL-DEEP.

**CLAUDE.md describes a different project (SCRM PaaS) — ignore it for this repo.** However, the following CLAUDE.md rules still apply:
- 全程使用**简体中文**沟通
- 代码注释中若 `author` 字段为空，统一填写 `@author Wujun`

## Build Commands

```bash
# Root build
mvn clean install -DskipTests

# Individual modules (each is independent)
mvn clean install -DskipTests -pl jun_code_velocity/my-generator -am
mvn clean install -DskipTests -pl jun_code_velocity/jun-xiaobear-gen/gen-admin -am
mvn clean install -DskipTests -pl jun-code-freemarker/jun_code_freemarker_deep/jun_code_freemarker_deep_admin -am
mvn clean install -DskipTests -pl jun-mybatisplus-generator -am
mvn clean install -DskipTests -pl jun_excel_generate_sql_script -am
```

Java 8, Maven, Spring Boot 2.x. All sub-module POMs already set `<project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>`.

## Running Services

| Module | Command | Port |
|---|---|---|
| `jun_code_freemarker_deep_admin` | `mvn spring-boot:run` (or IDE) | 8080 |
| `my-generator` | `mvn spring-boot:run` | 8888 |
| `gen-admin` (jun-xiaobear-gen) | `mvn spring-boot:run` | 8081 |
| `gen-ui` (Vue 3 frontend) | `npm run dev` | dev server |
| `jun-mybatisplus-generator` | CLI `main()` — NOT a Spring Boot app | N/A |
| `jun_excel_generate_sql_script` | `java -jar target/*.jar` — NOT a Spring Boot app | N/A |

All Spring Boot generators require **MySQL at `localhost:3307`** (non-standard port). DB init SQL must be run first:
- `jun_code_velocity/jun-xiaobear-gen/sql/xiaobear-gen.sql`
- `jun_code_velocity/my-generator/a_my-generator.sql`

## Testing

Only two modules have tests:
- `jun-mybatisplus-generator`: **JUnit 5** — `mvn test` (requires MySQL at `localhost:3307/db_test`, user `root`/`mysqladmin`)
- `jun_code_freemarker_deep_admin`: **JUnit 4** via `spring-boot-starter-test`
- All other modules: **no tests exist**

The `CrudGeneratorTest` uses Java `assert` statements alongside JUnit 5 annotations — requires `-ea` JVM flag:
```bash
mvn test -pl jun-mybatisplus-generator -am -Dtest=CrudGeneratorTest -DargLine=-ea
```

## Module Layout

```
jun_code_generator/                          # Root POM aggregator (io.github.wujun728)
├── jun-code-freemarker/
│   └── jun_code_freemarker_deep/
│       └── jun_code_freemarker_deep_admin/  # Freemarker, Spring Boot :8080, 27+ template categories
├── jun-mybatisplus-generator/               # CLI tool, MyBatis-Plus 3.5.3 codegen, generator.properties driven
├── jun_code_velocity/
│   ├── my-generator/                        # Velocity, Spring Boot :8888 (RuoYi-style, stripped down)
│   │   └── generator-web/                   # Pre-built Vue 2 + Element UI frontend
│   └── jun-xiaobear-gen/                    # PRIMARY module (most feature-complete)
│       ├── gen-admin/                       # Velocity, Spring Boot :8081, 90+ templates
│       └── gen-ui/                          # Vue 3 + Element Plus frontend (npm)
├── jun_excel_generate_sql_script/           # Standalone CLI: Excel → SQL + code
└── doc1/                                    # Reference projects (gitignored, not part of build)
```

## Key Conventions

- **Inconsistent groupIds**: Root=`io.github.wujun728`, my-generator=`com.template`, gen-admin=`org.xiaobear`, freemarker=`com.xuxueli`. Do not assume a single groupId.
- **Template file naming**: Freemarker templates use `${variable}` in filenames (e.g. `${classInfo.className}Controller.java.ftl`). Velocity templates use static names with `.vm` extension.
- **Template engines**: Velocity (`.vm`), Freemarker (`.ftl`). Check which engine a module uses before modifying templates.
- **gen-admin uses raw MyBatis** (not MyBatis-Plus), but generates code targeting both MyBatis and MyBatis-Plus.
- **jun_code_freemarker_deep_admin** depends on external JARs `io.github.wujun728:jun-db-activerecord` and `jun-common-base` not in this repo — `mvn install` on this module will fail unless those are available (e.g. from Maven Central or your local cache). Build with `-DskipTests` + `-Dmaven.compiler.failOnError=false` as a workaround, or install the missing JARs first.

## Zip Entry Duplicate Fix

When batch-downloading generated code, tables sharing the same `businessName` produce duplicate zip entries. The fix in `GenTableServiceImpl.java` tracks added entries in a `Set<String>` and prefixes duplicates with `tableName/`.

## Frontend

- `gen-ui/` (Vue 3): Requires Node.js >= 20. `npm install && npm run dev` in that directory.
- `generator-web/` (Vue 2): Pre-built static assets already in the source tree. Rebuild only when modifying: `npm run build` in that directory.

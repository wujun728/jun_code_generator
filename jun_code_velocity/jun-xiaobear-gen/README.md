<div align="center">
  <h1>🚀 XiaoBear Code Generator</h1>
  <p><strong>智能化企业级代码生成器</strong></p>

  [![License](https://img.shields.io/badge/License-AGPL--3.0-blue.svg)](LICENSE)
  [![Spring Boot](https://img.shields.io/badge/Spring%20Boot-2.7.18-brightgreen.svg)](https://spring.io/projects/spring-boot)
  [![Vue](https://img.shields.io/badge/Vue-3.5.12-4FC08D.svg)](https://vuejs.org/)
  [![Element Plus](https://img.shields.io/badge/Element%20Plus-2.8.7-409EFF.svg)](https://element-plus.org/)
  [![MyBatis](https://img.shields.io/badge/MyBatis-2.3.1-red.svg)](https://mybatis.org/)
</div>

## 📖 项目介绍

**XiaoBear Code Generator** 是一款基于若依RuoYi-Vue架构精心打造的**智能化企业级代码生成器**。它不仅仅是一个简单的代码生成工具，更是一个完整的开发效率提升解决方案。

### 🎯 核心价值

- **🚀 极速开发**：一键生成完整的前后端代码，减少90%的重复工作
- **🎨 灵活组合**：支持多种技术栈自由组合，满足不同项目需求
- **📊 智能分析**：基于数据库结构智能分析，生成符合企业级规范的高质量代码
- **🔧 开箱即用**：生成的代码包含完整的CRUD操作、数据校验、权限控制等功能
- **📚 文档齐全**：自动生成API文档、数据字典、项目文档等

### 🌟 适用场景

- **企业级管理系统**：快速构建CRM、ERP、OA等管理系统
- **微服务项目**：为微服务架构快速生成标准化的服务模块
- **原型开发**：快速搭建项目原型，验证业务逻辑
- **学习实践**：学习主流技术栈的最佳实践和代码规范

## ✨ 核心功能

### 🔧 代码生成能力

| 功能模块 | 支持框架 | 生成内容 | 状态 |
|---------|---------|---------|------|
| **后端代码** | MyBatis、MyBatis-Plus | Entity、Mapper、Service、Controller、SQL | ✅ |
| **前端代码** | Vue2/Vue3 + Element UI/Plus | 列表页面、表单页面、API接口 | ✅ |
| **前端代码** | Vue + Ant Design Vue | 完整的CRUD页面组件 | ✅ |
| **静态页面** | HTML + Bootstrap | 纯静态管理页面 | ✅ |

### 📊 数据库支持

- **✅ MySQL** - 完整支持，包括所有数据类型映射
- **✅ PostgreSQL** - 支持常用数据类型和特性
- **✅ Oracle** - 企业级数据库完整支持
- **✅ SQL Server** - Microsoft数据库生态支持
- **🔄 更多数据库** - 持续扩展中...

### 🎨 高级特性

- **🗂️ 多数据源管理** - 支持同时连接多个数据库进行代码生成
- **📋 SQL脚本导入** - 通过MySQL脚本快速创建表结构
- **📖 数据字典导出** - 自动生成Word/HTML格式的数据字典
- **🕒 定时清理** - 智能清理临时导入的表结构
- **📄 文档生成** - 自动生成项目文档和API文档
- **🎯 完整项目生成** - 一键生成包含前后端的完整项目结构

## 🛠️ 技术栈

### 后端技术栈
- **Spring Boot 2.7.18** - 企业级Java开发框架
- **MyBatis 2.3.1** - 优秀的持久层框架
- **Druid 1.2.21** - 阿里巴巴数据库连接池
- **Velocity 2.0** - 模板引擎
- **MySQL 8.0.33** - 关系型数据库
- **Maven** - 项目构建工具

### 前端技术栈
- **Vue 3.5.12** - 渐进式JavaScript框架
- **Element Plus 2.8.7** - Vue 3组件库
- **Axios** - HTTP客户端
- **Vue Router 4.x** - 路由管理
- **Vuex 4.x** - 状态管理

## 🚀 快速开始

### 📋 环境要求

| 软件 | 版本要求 | 推荐版本 |
|------|---------|---------|
| **JDK** | >= 1.8 | 1.8 |
| **MySQL** | >= 5.7.0 | 8.0+ |
| **Maven** | >= 3.0 | 3.6+ |
| **Node.js** | >= 16.0 | 18.0+ |
| **npm** | >= 8.0 | 9.0+ |

### 📥 获取代码

```bash
# 克隆项目
git clone https://gitee.com/Xiao_bear/xiaobear-gen.git

# 进入项目目录
cd xiaobear-gen
```

### 🔧 后端启动

```bash
# 进入后端目录
cd gen-admin

# 编译项目
mvn clean compile

# 启动项目
mvn spring-boot:run
```

后端服务将在 `http://localhost:8080` 启动

### 🎨 前端启动

```bash
# 进入前端目录
cd gen-ui

# 安装依赖
npm install

# 启动开发服务器
npm run dev
```

前端应用将在 `http://localhost:80` 启动

## ⚙️ 配置说明

### 1. 数据库连接配置

编辑 `gen-admin/src/main/resources/application.yml` 文件：

```yaml
# 数据源配置
spring:
  datasource:
    type: com.alibaba.druid.pool.DruidDataSource
    driverClassName: com.mysql.cj.jdbc.Driver
    druid:
      # 主库数据源
      master:
        url: jdbc:mysql://localhost:3306/xiaobear_gen?useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=true&serverTimezone=GMT%2B8
        username: root
        password: your_password
        # 初始连接数
        initialSize: 10
        # 最小连接池数量
        minIdle: 10
        # 最大连接池数量
        maxActive: 200
```

### 2. 代码生成配置

编辑 `gen-admin/src/main/resources/generator.yml` 文件：

```yaml
# 代码生成配置
gen:
  # 作者信息
  author: xiaobear
  # 默认生成包路径（请修改为你的包名）
  packageName: com.javaxiaobear
  # 自动去除表前缀
  autoRemovePre: false
  # 表前缀（生成类名不会包含表前缀，多个用逗号分隔）
  tablePrefix: sys_,gen_
  # 代码生成数据库（为空则使用当前连接的数据库）
  dataBase: xiaobear_gen
```

### 3. 若依环境兼容配置

如果需要连接现有的若依环境数据库，请执行以下SQL语句：

```sql
-- 为若依的gen_table表添加扩展字段
SET FOREIGN_KEY_CHECKS=0;

ALTER TABLE `gen_table`
ADD COLUMN `front_end` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '前端框架' AFTER `options`,
ADD COLUMN `back_end` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '后端框架' AFTER `front_end`;

SET FOREIGN_KEY_CHECKS=1;
```

### 4. 多数据源配置

系统支持配置多个数据源，可在管理界面中动态添加：

- **数据源名称**：用于标识不同的数据源
- **数据库类型**：MySQL、PostgreSQL、Oracle等
- **连接信息**：主机、端口、数据库名、用户名、密码
- **连接池配置**：最大连接数、超时时间等


## 📖 使用指南

### 🎯 基础使用流程

#### 1. 数据库表准备

**方式一：直接连接现有数据库**
- 在系统中配置数据源连接信息
- 选择需要生成代码的数据表
- 系统自动读取表结构信息

**方式二：通过SQL脚本导入**
- 准备建表SQL脚本
- 在"脚本导入"页面粘贴SQL内容
- 系统自动解析并创建临时表结构

#### 2. 代码生成配置

进入代码生成页面，配置以下信息：

- **基础信息**：表名、类名、功能名称、作者等
- **生成选项**：选择后端框架（MyBatis/MyBatis-Plus）
- **前端选择**：Vue2+ElementUI、Vue3+ElementPlus、AntDesignVue等
- **字段配置**：设置字段类型、校验规则、显示方式等

#### 3. 预览与下载

- **在线预览**：查看生成的代码内容
- **单表生成**：生成单个表的完整代码
- **批量生成**：同时生成多个表的代码
- **完整项目**：生成包含前后端的完整项目结构

### 🔧 高级功能

#### 多数据源管理
1. 进入"数据源管理"页面
2. 添加新的数据源配置
3. 测试连接确保配置正确
4. 在代码生成时选择对应数据源

#### 数据字典导出
1. 选择需要导出的数据库
2. 选择导出格式（Word/HTML）
3. 自定义导出内容和样式
4. 下载生成的数据字典文档

#### 定时清理功能
- 系统自动清理临时导入的表结构
- 可配置清理周期和保留时间
- 避免数据库中积累过多临时数据



## 📸 功能演示

### 🏠 主界面
  ![image-20250729225639103](https://javaxiaobear-1301481032.cos.ap-guangzhou.myqcloud.com/picture-bed/image-20250729225639103.png)
### 📊 代码生成配置

![image-20250729225706337](https://javaxiaobear-1301481032.cos.ap-guangzhou.myqcloud.com/picture-bed/image-20250729225706337.png)

### 🔍 表结构管理

![image-20250805161938573](https://javaxiaobear-1301481032.cos.ap-guangzhou.myqcloud.com/picture-bed/image-20250805161938573.png)


### ⚙️ 字段配置
![image-20250729225820588](https://javaxiaobear-1301481032.cos.ap-guangzhou.myqcloud.com/picture-bed/image-20250729225820588.png)


### 📝 代码预览
![image-20250729225843797](https://javaxiaobear-1301481032.cos.ap-guangzhou.myqcloud.com/picture-bed/image-20250729225843797.png)


### 📚 数据源管理
![image-20250729225912726](https://javaxiaobear-1301481032.cos.ap-guangzhou.myqcloud.com/picture-bed/image-20250729225912726.png)

### 数据字典导出

![image-20250729225959803](https://javaxiaobear-1301481032.cos.ap-guangzhou.myqcloud.com/picture-bed/image-20250729225959803.png)

## 🚀 项目优势

### 💡 为什么选择 XiaoBear Code Generator？

| 特性 | XiaoBear Generator | 传统开发方式 | 其他代码生成器 |
|------|-------------------|-------------|---------------|
| **开发效率** | 🚀 提升90%+ | ⏰ 耗时较长 | 📈 提升60-70% |
| **代码质量** | ✅ 企业级规范 | 🔄 参差不齐 | ⚠️ 基础模板 |
| **技术栈支持** | 🎯 主流全覆盖 | 🛠️ 需要积累 | 📝 支持有限 |
| **学习成本** | 🎓 零门槛 | 📚 需要学习 | 🤔 中等难度 |
| **维护成本** | 🔧 持续更新 | 💰 人力成本高 | ❓ 不确定 |

### 🎯 核心竞争力

- **🧠 智能化**：基于AI算法分析数据库结构，智能推荐最佳配置
- **🔧 标准化**：严格遵循企业级开发规范，生成高质量代码
- **🚀 高效率**：一键生成完整项目，从数据库到前端页面全覆盖
- **🎨 个性化**：支持自定义模板，满足特殊业务需求
- **📚 文档化**：自动生成完整的项目文档和API文档

## 🛣️ 发展规划

### 📅 近期计划（2025年）

- [ ] **支持更多数据库**：PostgreSQL、Oracle、SQL Server完整支持
- [ ] **微服务架构**：支持Spring Cloud微服务项目生成
- [ ] **容器化部署**：Docker、Kubernetes部署配置生成
- [ ] **单元测试**：自动生成完整的单元测试代码
- [ ] **API文档**：集成Swagger/OpenAPI文档生成

### 🔮 长期愿景

- [ ] **AI辅助开发**：集成AI代码审查和优化建议
- [ ] **低代码平台**：可视化拖拽式页面设计器
- [ ] **多语言支持**：Java、Python、Go、C#等多语言支持
- [ ] **云原生**：支持云原生应用架构生成

## 🤝 参与贡献

我们欢迎所有形式的贡献！无论是：

- 🐛 **Bug反馈**：发现问题请提交Issue
- 💡 **功能建议**：有好的想法请告诉我们
- 🔧 **代码贡献**：提交Pull Request改进项目
- 📖 **文档完善**：帮助改进项目文档
- ⭐ **Star支持**：给项目点个Star是最大的鼓励

### 贡献指南

1. Fork 本项目
2. 创建特性分支 (`git checkout -b feature/AmazingFeature`)
3. 提交更改 (`git commit -m 'Add some AmazingFeature'`)
4. 推送到分支 (`git push origin feature/AmazingFeature`)
5. 创建 Pull Request

## 📞 联系我们

### 💬 技术交流

- **作者**：javaxiaobear
- **邮箱**：javaxiaobear@qq.com
- **博客**：https://www.javaxiaobear.cn

### 📱 微信交流

<div align="center">
  <img src="images/wechat.jpg" alt="微信二维码" style="width: 300px; border-radius: 8px; box-shadow: 0 4px 8px rgba(0,0,0,0.1);">
  <p><em>扫码添加微信，技术交流与问题反馈</em></p>
</div>

> 💡 **提示**：添加微信时请备注"代码生成器"，方便快速通过验证

## 🙏 致谢

### 开源项目致谢

- **[RuoYi-Vue](https://gitee.com/y_project/RuoYi-Vue)** - 感谢若依团队提供的优秀基础架构
- **[Spring Boot](https://spring.io/projects/spring-boot)** - 强大的Java开发框架
- **[Vue.js](https://vuejs.org/)** - 渐进式JavaScript框架
- **[Element Plus](https://element-plus.org/)** - 优秀的Vue 3组件库
- **[MyBatis](https://mybatis.org/)** - 优秀的持久层框架

### 社区贡献者

感谢所有为项目贡献代码、提出建议、反馈问题的开发者们！

---

<div align="center">
  <p>
    <strong>如果这个项目对你有帮助，请给个 ⭐ Star 支持一下！</strong>
  </p>
  <p>
    <a href="https://gitee.com/Xiao_bear/xiaobear-gen">
      <img src="https://img.shields.io/badge/Gitee-项目地址-red.svg" alt="Gitee">
    </a>
    <a href="LICENSE">
      <img src="https://img.shields.io/badge/License-AGPL--3.0-blue.svg" alt="License">
    </a>
  </p>
</div>

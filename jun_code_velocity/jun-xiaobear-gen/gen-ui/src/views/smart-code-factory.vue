<template>
  <div class="smart-code-factory">
    <!-- 页面头部 -->
    <div class="factory-header">
      <div class="header-content">
        <div class="title-section">
          <h1 class="factory-title">
            <el-icon class="title-icon"><MagicStick /></el-icon>
            智能代码工厂
            <span class="subtitle">Smart Code Factory</span>
          </h1>
          <p class="factory-description">
            基于AI驱动的全栈代码生成平台，一键生成企业级Spring Boot + Vue3项目
          </p>
        </div>
        <div class="stats-section">
          <div class="stat-item">
            <div class="stat-number">{{ selectedTables.length }}</div>
            <div class="stat-label">已选表</div>
          </div>
          <div class="stat-item">
            <div class="stat-number">{{ estimatedFiles }}</div>
            <div class="stat-label">预计文件</div>
          </div>
          <div class="stat-item">
            <div class="stat-number">{{ estimatedLines }}</div>
            <div class="stat-label">预计代码行</div>
          </div>
        </div>
      </div>
    </div>

    <!-- 主要内容区域 -->
    <div class="factory-content">
      <el-row :gutter="24">
        <!-- 左侧：表选择区域 -->
        <el-col :span="7">
          <el-card class="selection-card" shadow="hover">
            <template #header>
              <div class="card-header">
                <el-icon><Grid /></el-icon>
                <span>数据表选择</span>
                <el-badge :value="selectedTables.length" class="badge" type="primary" />
              </div>
            </template>

            <!-- 搜索框 -->
            <div class="search-section">
              <el-input
                v-model="tableSearchText"
                placeholder="搜索数据表..."
                prefix-icon="Search"
                clearable
                @input="handleTableSearch"
              />
            </div>

            <!-- 表列表 -->
            <div class="table-list">
              <div class="list-header">
                <el-checkbox
                  v-model="selectAll"
                  :indeterminate="isIndeterminate"
                  @change="handleSelectAll"
                >
                  全选 ({{ filteredTables.length }})
                </el-checkbox>
              </div>

              <div class="table-items">
                <div
                  v-for="table in filteredTables"
                  :key="table.tableId"
                  class="table-item"
                  :class="{ 'selected': isTableSelected(table) }"
                  @click="toggleTableSelection(table)"
                >
                  <el-checkbox
                    :model-value="isTableSelected(table)"
                    @change="toggleTableSelection(table)"
                    @click.stop
                  />
                  <div class="table-info">
                    <div class="table-name">{{ table.tableName }}</div>
                    <div class="table-comment">{{ table.tableComment || '无描述' }}</div>
                  </div>
                  <el-tag size="small" type="info">{{ table.className }}</el-tag>
                </div>
              </div>
            </div>
          </el-card>
        </el-col>

        <!-- 中间：项目配置区域 -->
        <el-col :span="10">
          <el-card class="config-card" shadow="hover">
            <template #header>
              <div class="card-header">
                <el-icon><Setting /></el-icon>
                <span>智能项目配置</span>
                <el-button
                  type="text"
                  size="small"
                  @click="autoGenerateConfig"
                  class="auto-btn"
                >
                  <el-icon><MagicStick /></el-icon>
                  智能填充
                </el-button>
              </div>
            </template>

            <el-form
              ref="configForm"
              :model="projectConfig"
              :rules="configRules"
              label-width="100px"
              class="config-form"
            >
              <!-- 基础信息 -->
              <div class="form-section">
                <h4 class="section-title">
                  <el-icon><Document /></el-icon>
                  基础信息
                </h4>

                <el-form-item label="项目名称" prop="projectName">
                  <el-input
                    v-model="projectConfig.projectName"
                    placeholder="如：user-management-system"
                    @blur="onProjectNameChange"
                  />
                </el-form-item>

                <el-form-item label="项目描述" prop="projectComment">
                  <el-input
                    v-model="projectConfig.projectComment"
                    placeholder="如：用户管理系统"
                    type="textarea"
                    :rows="2"
                  />
                </el-form-item>

                <el-row :gutter="16">
                  <el-col :span="12">
                    <el-form-item label="Group ID" prop="groupId">
                      <el-input
                        v-model="projectConfig.groupId"
                        placeholder="com.company"
                      />
                    </el-form-item>
                  </el-col>
                  <el-col :span="12">
                    <el-form-item label="Artifact ID" prop="artifactId">
                      <el-input
                        v-model="projectConfig.artifactId"
                        placeholder="user-system"
                      />
                    </el-form-item>
                  </el-col>
                </el-row>

                <el-row :gutter="16">
                  <el-col :span="12">
                    <el-form-item label="包名" prop="packageName">
                      <el-input
                        v-model="projectConfig.packageName"
                        placeholder="com.company.system"
                      />
                    </el-form-item>
                  </el-col>
                  <el-col :span="12">
                    <el-form-item label="类名前缀" prop="className">
                      <el-input
                        v-model="projectConfig.className"
                        placeholder="User"
                      />
                    </el-form-item>
                  </el-col>
                </el-row>
              </div>

              <!-- 数据库配置 -->
              <div class="form-section">
                <h4 class="section-title">
                  <el-icon><Connection /></el-icon>
                  数据库配置
                </h4>

                <el-form-item label="数据库URL" prop="datasourceUrl">
                  <el-input
                    v-model="projectConfig.datasourceUrl"
                    placeholder="jdbc:mysql://localhost:3306/database_name"
                  />
                </el-form-item>

                <el-row :gutter="16">
                  <el-col :span="12">
                    <el-form-item label="用户名" prop="datasourceUsername">
                      <el-input
                        v-model="projectConfig.datasourceUsername"
                        placeholder="root"
                      />
                    </el-form-item>
                  </el-col>
                  <el-col :span="12">
                    <el-form-item label="密码" prop="datasourcePassword">
                      <el-input
                        v-model="projectConfig.datasourcePassword"
                        type="password"
                        placeholder="数据库密码"
                        show-password
                      />
                    </el-form-item>
                  </el-col>
                </el-row>
              </div>

              <!-- 服务配置 -->
              <div class="form-section">
                <h4 class="section-title">
                  <el-icon><Monitor /></el-icon>
                  服务配置
                </h4>

                <el-row :gutter="16">
                  <el-col :span="12">
                    <el-form-item label="服务端口" prop="serverPort">
                      <el-input-number
                        v-model="projectConfig.serverPort"
                        :min="1000"
                        :max="65535"
                        placeholder="8080"
                        style="width: 100%"
                      />
                    </el-form-item>
                  </el-col>
                  <el-col :span="12">
                    <el-form-item label="作者" prop="author">
                      <el-input
                        v-model="projectConfig.author"
                        placeholder="开发者姓名"
                      />
                    </el-form-item>
                  </el-col>
                </el-row>

                <el-form-item label="JWT密钥" prop="jwtSecret">
                  <el-input
                    v-model="projectConfig.jwtSecret"
                    placeholder="JWT签名密钥"
                    type="password"
                    show-password
                  />
                  <div class="form-tip">
                    <el-button type="text" size="small" @click="generateJwtSecret">
                      <el-icon><Refresh /></el-icon>
                      生成随机密钥
                    </el-button>
                  </div>
                </el-form-item>
              </div>
            </el-form>
          </el-card>
        </el-col>

        <!-- 右侧：生成预览和操作区域 -->
        <el-col :span="7">
          <el-card class="preview-card" shadow="hover">
            <template #header>
              <div class="card-header">
                <div class="header-left">
                  <el-icon><View /></el-icon>
                  <span>生成预览</span>
                </div>
                <div class="header-actions">
                  <!-- 快捷操作按钮 -->
                  <el-tooltip content="预览配置" placement="top">
                    <el-button
                      size="small"
                      type="info"
                      circle
                      @click="previewProject"
                      class="header-btn"
                    >
                      <el-icon><Document /></el-icon>
                    </el-button>
                  </el-tooltip>
                  <el-tooltip content="保存模板" placement="top">
                    <el-button
                      size="small"
                      type="success"
                      circle
                      @click="saveTemplate"
                      class="header-btn"
                    >
                      <el-icon><Download /></el-icon>
                    </el-button>
                  </el-tooltip>
                </div>
              </div>
            </template>

            <div class="preview-content">
              <!-- 项目结构预览 -->
            <div class="project-structure">
              <div class="structure-header">
                <h5>
                  <el-icon><Folder /></el-icon>
                  项目结构预览
                </h5>
                <div class="structure-stats">
                  <el-tag size="mini" type="primary">{{ estimatedFiles }} 文件</el-tag>
                  <el-tag size="mini" type="success">{{ estimatedLines }}+ 行</el-tag>
                </div>
              </div>

              <div class="structure-tree">
                <!-- 根目录 -->
                <div class="tree-item root-item">
                  <div class="item-header" @click="toggleExpand('root')">
                    <el-icon class="expand-icon" :class="{ 'expanded': expandedNodes.root }">
                      <ArrowRight />
                    </el-icon>
                    <el-icon class="folder-icon root"><Folder /></el-icon>
                    <span class="item-name">{{ projectConfig.projectName || 'my-project' }}</span>
                    <el-tag size="mini" type="primary">根目录</el-tag>
                  </div>

                  <div class="item-children" v-show="expandedNodes.root">
                    <!-- 后端项目 -->
                    <div class="tree-item backend-item">
                      <div class="item-header" @click="toggleExpand('backend')">
                        <el-icon class="expand-icon" :class="{ 'expanded': expandedNodes.backend }">
                          <ArrowRight />
                        </el-icon>
                        <el-icon class="folder-icon backend"><Connection /></el-icon>
                        <span class="item-name">{{ projectConfig.projectName || 'my-project' }}-backend</span>
                        <el-tag size="mini" type="success">Spring Boot</el-tag>
                      </div>

                      <div class="item-children" v-show="expandedNodes.backend">
                        <div class="file-item">
                          <el-icon class="file-icon"><Document /></el-icon>
                          <span class="file-name">pom.xml</span>
                          <span class="file-desc">Maven配置</span>
                        </div>
                        <div class="file-item">
                          <el-icon class="folder-icon"><Folder /></el-icon>
                          <span class="file-name">src/main/java</span>
                          <span class="file-desc">Java源码</span>
                        </div>
                        <div class="file-item">
                          <el-icon class="folder-icon"><Folder /></el-icon>
                          <span class="file-name">src/main/resources</span>
                          <span class="file-desc">配置文件</span>
                        </div>
                        <div class="file-item">
                          <el-icon class="file-icon"><Document /></el-icon>
                          <span class="file-name">Dockerfile</span>
                          <span class="file-desc">容器镜像</span>
                        </div>
                      </div>
                    </div>

                    <!-- 前端项目 -->
                    <div class="tree-item frontend-item">
                      <div class="item-header" @click="toggleExpand('frontend')">
                        <el-icon class="expand-icon" :class="{ 'expanded': expandedNodes.frontend }">
                          <ArrowRight />
                        </el-icon>
                        <el-icon class="folder-icon frontend"><View /></el-icon>
                        <span class="item-name">{{ projectConfig.projectName || 'my-project' }}-frontend</span>
                        <el-tag size="mini" type="warning">Vue 3</el-tag>
                      </div>

                      <div class="item-children" v-show="expandedNodes.frontend">
                        <div class="file-item">
                          <el-icon class="file-icon"><Document /></el-icon>
                          <span class="file-name">package.json</span>
                          <span class="file-desc">依赖配置</span>
                        </div>
                        <div class="file-item">
                          <el-icon class="folder-icon"><Folder /></el-icon>
                          <span class="file-name">src/views</span>
                          <span class="file-desc">页面组件</span>
                        </div>
                        <div class="file-item">
                          <el-icon class="folder-icon"><Folder /></el-icon>
                          <span class="file-name">src/api</span>
                          <span class="file-desc">接口调用</span>
                        </div>
                        <div class="file-item">
                          <el-icon class="file-icon"><Document /></el-icon>
                          <span class="file-name">Dockerfile</span>
                          <span class="file-desc">容器镜像</span>
                        </div>
                      </div>
                    </div>

                    <!-- 部署配置 -->
                    <div class="tree-item deploy-item">
                      <div class="item-header" @click="toggleExpand('deploy')">
                        <el-icon class="expand-icon" :class="{ 'expanded': expandedNodes.deploy }">
                          <ArrowRight />
                        </el-icon>
                        <el-icon class="folder-icon deploy"><Monitor /></el-icon>
                        <span class="item-name">部署配置</span>
                        <el-tag size="mini" type="info">Docker</el-tag>
                      </div>

                      <div class="item-children" v-show="expandedNodes.deploy">
                        <div class="file-item">
                          <el-icon class="file-icon"><Document /></el-icon>
                          <span class="file-name">docker-compose.yml</span>
                          <span class="file-desc">容器编排</span>
                        </div>
                        <div class="file-item">
                          <el-icon class="file-icon"><Document /></el-icon>
                          <span class="file-name">nginx.conf</span>
                          <span class="file-desc">代理配置</span>
                        </div>
                        <div class="file-item">
                          <el-icon class="file-icon"><Document /></el-icon>
                          <span class="file-name">start.sh</span>
                          <span class="file-desc">启动脚本</span>
                        </div>
                        <div class="file-item">
                          <el-icon class="file-icon"><Document /></el-icon>
                          <span class="file-name">README.md</span>
                          <span class="file-desc">项目文档</span>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </div>

            <!-- 技术栈信息 -->
            <div class="tech-stack">
              <div class="tech-stack-header" @click="toggleTechStack">
                <h5>
                  <el-icon><Setting /></el-icon>
                  技术栈配置
                </h5>
                <el-icon class="collapse-icon" :class="{ 'expanded': !techStackCollapsed }">
                  <ArrowRight />
                </el-icon>
              </div>

              <div class="tech-stack-content" v-show="!techStackCollapsed">
                <div class="tech-categories">
                <!-- 后端技术栈 -->
                <div class="tech-category">
                  <div class="category-title">
                    <el-icon><Connection /></el-icon>
                    <span>后端技术</span>
                  </div>
                  <div class="tech-tags">
                    <div class="tech-item">
                      <el-tag type="primary" size="small">Spring Boot 2.7</el-tag>
                      <span class="tech-desc">企业级框架</span>
                    </div>
                    <div class="tech-item">
                      <el-tag type="success" size="small">MyBatis Plus</el-tag>
                      <span class="tech-desc">ORM框架</span>
                    </div>
                    <div class="tech-item">
                      <el-tag type="info" size="small">Spring Security</el-tag>
                      <span class="tech-desc">安全认证</span>
                    </div>
                    <div class="tech-item">
                      <el-tag type="warning" size="small">JWT</el-tag>
                      <span class="tech-desc">令牌认证</span>
                    </div>
                    <div class="tech-item">
                      <el-tag type="danger" size="small">Redis</el-tag>
                      <span class="tech-desc">缓存数据库</span>
                    </div>
                  </div>
                </div>

                <!-- 前端技术栈 -->
                <div class="tech-category">
                  <div class="category-title">
                    <el-icon><View /></el-icon>
                    <span>前端技术</span>
                  </div>
                  <div class="tech-tags">
                    <div class="tech-item">
                      <el-tag type="warning" size="small">Vue 3</el-tag>
                      <span class="tech-desc">渐进式框架</span>
                    </div>
                    <div class="tech-item">
                      <el-tag type="primary" size="small">Element Plus</el-tag>
                      <span class="tech-desc">组件库</span>
                    </div>
                    <div class="tech-item">
                      <el-tag type="success" size="small">Vue Router</el-tag>
                      <span class="tech-desc">路由管理</span>
                    </div>
                    <div class="tech-item">
                      <el-tag type="info" size="small">Axios</el-tag>
                      <span class="tech-desc">HTTP客户端</span>
                    </div>
                    <div class="tech-item">
                      <el-tag type="warning" size="small">Vite</el-tag>
                      <span class="tech-desc">构建工具</span>
                    </div>
                  </div>
                </div>

                <!-- 部署技术栈 -->
                <div class="tech-category">
                  <div class="category-title">
                    <el-icon><Monitor /></el-icon>
                    <span>部署技术</span>
                  </div>
                  <div class="tech-tags">
                    <div class="tech-item">
                      <el-tag type="info" size="small">Docker</el-tag>
                      <span class="tech-desc">容器化</span>
                    </div>
                    <div class="tech-item">
                      <el-tag type="danger" size="small">MySQL</el-tag>
                      <span class="tech-desc">数据库</span>
                    </div>
                    <div class="tech-item">
                      <el-tag size="small">Nginx</el-tag>
                      <span class="tech-desc">反向代理</span>
                    </div>
                    <div class="tech-item">
                      <el-tag type="primary" size="small">Docker Compose</el-tag>
                      <span class="tech-desc">容器编排</span>
                    </div>
                    <div class="tech-item">
                      <el-tag type="success" size="small">Maven</el-tag>
                      <span class="tech-desc">构建工具</span>
                    </div>
                    <div class="tech-item">
                      <el-tag type="warning" size="small">Jenkins</el-tag>
                      <span class="tech-desc">CI/CD</span>
                    </div>
                  </div>
                </div>

                <!-- 开发工具栈 -->
                <div class="tech-category">
                  <div class="category-title">
                    <el-icon><Setting /></el-icon>
                    <span>开发工具</span>
                  </div>
                  <div class="tech-tags">
                    <div class="tech-item">
                      <el-tag type="primary" size="small">IntelliJ IDEA</el-tag>
                      <span class="tech-desc">Java IDE</span>
                    </div>
                    <div class="tech-item">
                      <el-tag type="success" size="small">VS Code</el-tag>
                      <span class="tech-desc">前端IDE</span>
                    </div>
                    <div class="tech-item">
                      <el-tag type="warning" size="small">Git</el-tag>
                      <span class="tech-desc">版本控制</span>
                    </div>
                    <div class="tech-item">
                      <el-tag type="info" size="small">Postman</el-tag>
                      <span class="tech-desc">API测试</span>
                    </div>
                  </div>
                </div>
              </div>
              </div>

            </div>
            </div>

            <!-- 生成按钮 -->
            <div class="generate-actions">
              <!-- 生成信息卡片 -->
              <div class="generate-info" v-if="canGenerate">
                <div class="info-item">
                  <div class="info-label">项目类型</div>
                  <div class="info-value">全栈项目</div>
                </div>
              </div>

              <!-- 主要生成按钮 -->
              <el-button
                type="primary"
                size="large"
                :loading="generating"
                :disabled="!canGenerate"
                @click="handleGenerate"
                class="generate-btn"
              >
                <el-icon><MagicStick /></el-icon>
                {{ generating ? '正在生成项目...' : '🚀 一键生成项目' }}
              </el-button>



              <!-- 提示信息 -->
              <div class="action-tips" v-if="!canGenerate">
                <el-alert
                  :title="generateTip"
                  type="warning"
                  :closable="false"
                  show-icon
                  class="tip-alert"
                />
              </div>

              <!-- 成功提示 -->
              <div class="success-tips" v-if="canGenerate">
                <div class="tip-item">
                  <el-icon><Check /></el-icon>
                  <span>项目配置完整</span>
                </div>
                <div class="tip-item">
                  <el-icon><Check /></el-icon>
                  <span>数据表已选择</span>
                </div>
                <div class="tip-item">
                  <el-icon><Check /></el-icon>
                  <span>准备就绪</span>
                </div>
              </div>
            </div>
          </el-card>
        </el-col>
      </el-row>
    </div>

    <!-- 生成进度对话框 -->
    <el-dialog
      v-model="progressVisible"
      title="项目生成中"
      width="600px"
      :close-on-click-modal="false"
      :close-on-press-escape="false"
      :show-close="false"
    >
      <div class="progress-content">
        <div class="progress-info">
          <el-icon class="progress-icon"><Loading /></el-icon>
          <h3>{{ currentStep }}</h3>
          <p>{{ currentStepDetail }}</p>
        </div>

        <el-progress
          :percentage="progressPercentage"
          :stroke-width="8"
          status="success"
          :show-text="false"
        />

        <div class="progress-steps">
          <div
            v-for="(step, index) in generateSteps"
            :key="index"
            class="step-item"
            :class="{
              'completed': index < currentStepIndex,
              'active': index === currentStepIndex,
              'pending': index > currentStepIndex
            }"
          >
            <div class="step-icon">
              <el-icon v-if="index < currentStepIndex"><Check /></el-icon>
              <el-icon v-else-if="index === currentStepIndex"><Loading /></el-icon>
              <span v-else>{{ index + 1 }}</span>
            </div>
            <div class="step-text">{{ step.name }}</div>
          </div>
        </div>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { ref, reactive, computed, onMounted, watch } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import {
  MagicStick,
  Grid,
  Setting,
  Document,
  Connection,
  Monitor,
  View,
  Folder,
  Search,
  Refresh,
  Check,
  Loading,
  ArrowRight,
  Download
} from '@element-plus/icons-vue'
import genApi from '@/api/gen'

export default {
  name: 'SmartCodeFactory',
  components: {
    MagicStick,
    Grid,
    Setting,
    Document,
    Connection,
    Monitor,
    View,
    Folder,
    Search,
    Refresh,
    Check,
    Loading,
    ArrowRight,
    Download
  },
  setup() {
    // 响应式数据
    const tableList = ref([])
    const selectedTables = ref([])
    const tableSearchText = ref('')
    const generating = ref(false)
    const progressVisible = ref(false)
    const currentStepIndex = ref(0)

    // 树形结构展开状态
    const expandedNodes = reactive({
      root: true,
      backend: true,
      frontend: true,
      deploy: false
    })

    // 技术栈折叠状态
    const techStackCollapsed = ref(true)

    // 项目配置
    const projectConfig = reactive({
      projectName: '',
      projectComment: '',
      packageName: '',
      className: '',
      groupId: '',
      artifactId: '',
      version: '1.0.0',
      author: 'javaxiaobear',
      driverClassName: 'com.mysql.cj.jdbc.Driver',
      datasourceUrl: '',
      datasourceUsername: 'root',
      datasourcePassword: '',
      serverPort: 8080,
      jwtSecret: ''
    })

    // 表单验证规则
    const configRules = {
      projectName: [
        { required: true, message: '请输入项目名称', trigger: 'blur' },
        { pattern: /^[a-z][a-z0-9-]*$/, message: '项目名称只能包含小写字母、数字和连字符，且以字母开头', trigger: 'blur' }
      ],
      projectComment: [
        { required: true, message: '请输入项目描述', trigger: 'blur' }
      ],
      packageName: [
        { required: true, message: '请输入包名', trigger: 'blur' },
        { pattern: /^[a-z][a-z0-9]*(\.[a-z][a-z0-9]*)*$/, message: '包名格式不正确', trigger: 'blur' }
      ],
      className: [
        { required: true, message: '请输入类名前缀', trigger: 'blur' },
        { pattern: /^[A-Z][a-zA-Z0-9]*$/, message: '类名必须以大写字母开头', trigger: 'blur' }
      ],
      groupId: [
        { required: true, message: '请输入Group ID', trigger: 'blur' }
      ],
      artifactId: [
        { required: true, message: '请输入Artifact ID', trigger: 'blur' },
        { pattern: /^[a-z][a-z0-9-]*$/, message: 'Artifact ID格式不正确', trigger: 'blur' }
      ],
      datasourceUrl: [
        { required: true, message: '请输入数据库URL', trigger: 'blur' }
      ],
      datasourceUsername: [
        { required: true, message: '请输入数据库用户名', trigger: 'blur' }
      ],
      datasourcePassword: [
        { required: true, message: '请输入数据库密码', trigger: 'blur' }
      ],
      serverPort: [
        { required: true, message: '请输入服务端口', trigger: 'blur' },
        { type: 'number', min: 1000, max: 65535, message: '端口范围1000-65535', trigger: 'blur' }
      ],
      author: [
        { required: true, message: '请输入作者', trigger: 'blur' }
      ],
      jwtSecret: [
        { required: true, message: '请输入JWT密钥', trigger: 'blur' },
        { min: 16, message: 'JWT密钥长度至少16位', trigger: 'blur' }
      ]
    }

    // 生成步骤
    const generateSteps = [
      { name: '准备项目结构', detail: '创建项目目录和基础文件...' },
      { name: '生成后端代码', detail: '生成Spring Boot应用和业务代码...' },
      { name: '生成前端代码', detail: '生成Vue3应用和页面组件...' },
      { name: '生成配置文件', detail: '生成数据库配置和应用配置...' },
      { name: '生成部署文件', detail: '生成Docker和部署脚本...' },
      { name: '打包项目文件', detail: '压缩并准备下载...' }
    ]

    // 计算属性
    const filteredTables = computed(() => {
      if (!tableSearchText.value) return tableList.value
      return tableList.value.filter(table =>
        table.tableName.toLowerCase().includes(tableSearchText.value.toLowerCase()) ||
        (table.tableComment && table.tableComment.toLowerCase().includes(tableSearchText.value.toLowerCase()))
      )
    })

    const selectAll = computed({
      get: () => selectedTables.value.length === filteredTables.value.length && filteredTables.value.length > 0,
      set: (value) => {
        if (value) {
          selectedTables.value = [...filteredTables.value]
        } else {
          selectedTables.value = []
        }
      }
    })

    const isIndeterminate = computed(() => {
      const selectedCount = selectedTables.value.length
      const totalCount = filteredTables.value.length
      return selectedCount > 0 && selectedCount < totalCount
    })

    const estimatedFiles = computed(() => {
      const baseFiles = 25 // 基础文件数量
      const filesPerTable = 8 // 每个表生成的文件数量
      return baseFiles + (selectedTables.value.length * filesPerTable)
    })

    const estimatedLines = computed(() => {
      const baseLines = 2000 // 基础代码行数
      const linesPerTable = 800 // 每个表生成的代码行数
      return baseLines + (selectedTables.value.length * linesPerTable)
    })

    const canGenerate = computed(() => {
      return selectedTables.value.length > 0 &&
             projectConfig.projectName &&
             projectConfig.packageName &&
             projectConfig.datasourceUrl
    })

    const generateTip = computed(() => {
      if (selectedTables.value.length === 0) return '请至少选择一个数据表'
      if (!projectConfig.projectName) return '请填写项目名称'
      if (!projectConfig.packageName) return '请填写包名'
      if (!projectConfig.datasourceUrl) return '请填写数据库URL'
      return ''
    })

    const currentStep = computed(() => {
      return generateSteps[currentStepIndex.value]?.name || ''
    })

    const currentStepDetail = computed(() => {
      return generateSteps[currentStepIndex.value]?.detail || ''
    })

    const progressPercentage = computed(() => {
      return Math.round((currentStepIndex.value / generateSteps.length) * 100)
    })

    // 方法
    const loadTableList = async () => {
      try {
        const response = await genApi.listTable({ pageNum: 1, pageSize: 1000 })
        tableList.value = response.rows || []
      } catch (error) {
        ElMessage.error('加载数据表失败')
        console.error('Load tables error:', error)
      }
    }

    const isTableSelected = (table) => {
      return selectedTables.value.some(t => t.tableId === table.tableId)
    }

    const toggleTableSelection = (table) => {
      const index = selectedTables.value.findIndex(t => t.tableId === table.tableId)
      if (index > -1) {
        selectedTables.value.splice(index, 1)
      } else {
        selectedTables.value.push(table)
      }
    }

    const handleSelectAll = (value) => {
      selectAll.value = value
    }

    const handleTableSearch = () => {
      // 搜索逻辑已在computed中处理
    }

    const onProjectNameChange = () => {
      if (projectConfig.projectName && !projectConfig.artifactId) {
        projectConfig.artifactId = projectConfig.projectName
      }
    }

    const generateJwtSecret = () => {
      const chars = 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789'
      let result = ''
      for (let i = 0; i < 32; i++) {
        result += chars.charAt(Math.floor(Math.random() * chars.length))
      }
      projectConfig.jwtSecret = result
      ElMessage.success('JWT密钥已生成')
    }

    const autoGenerateConfig = () => {
      if (selectedTables.value.length === 0) {
        ElMessage.warning('请先选择数据表')
        return
      }

      // 基于选中的表智能生成配置
      const firstTable = selectedTables.value[0]
      const tableName = firstTable.tableName

      // 智能推断项目名称
      if (!projectConfig.projectName) {
        const projectName = tableName.replace(/^(sys_|t_|tb_)/, '').replace(/_/g, '-') + '-system'
        projectConfig.projectName = projectName
      }

      // 智能推断项目描述
      if (!projectConfig.projectComment) {
        const comment = firstTable.tableComment || tableName
        projectConfig.projectComment = comment.replace(/表$/, '') + '管理系统'
      }

      // 智能推断包名和类名
      if (!projectConfig.packageName) {
        projectConfig.packageName = 'com.company.' + tableName.replace(/^(sys_|t_|tb_)/, '').replace(/_/g, '')
      }

      if (!projectConfig.className) {
        const className = tableName.replace(/^(sys_|t_|tb_)/, '')
          .split('_')
          .map(word => word.charAt(0).toUpperCase() + word.slice(1))
          .join('')
        projectConfig.className = className
      }

      // 智能推断其他配置
      if (!projectConfig.groupId) {
        projectConfig.groupId = 'com.company'
      }

      if (!projectConfig.artifactId) {
        projectConfig.artifactId = projectConfig.projectName
      }

      if (!projectConfig.datasourceUrl) {
        projectConfig.datasourceUrl = 'jdbc:mysql://localhost:3306/' + projectConfig.projectName.replace(/-/g, '_')
      }

      if (!projectConfig.jwtSecret) {
        generateJwtSecret()
      }

      ElMessage.success('智能配置生成完成')
    }

    const previewProject = () => {
      const config = JSON.stringify(projectConfig, null, 2)
      ElMessageBox.alert(
        `<pre style="text-align: left; font-size: 12px; max-height: 400px; overflow-y: auto;">${config}</pre>`,
        '项目配置预览',
        {
          dangerouslyUseHTMLString: true,
          confirmButtonText: '确定'
        }
      )
    }

    const saveTemplate = () => {
      const templateData = {
        name: `${projectConfig.projectName || 'default'}-template`,
        config: projectConfig,
        tables: selectedTables.value.map(t => t.tableName),
        createTime: new Date().toISOString()
      }

      localStorage.setItem('smart-factory-template', JSON.stringify(templateData))
      ElMessage.success('模板已保存到本地')
    }

    const toggleExpand = (nodeKey) => {
      expandedNodes[nodeKey] = !expandedNodes[nodeKey]
    }

    const toggleTechStack = () => {
      techStackCollapsed.value = !techStackCollapsed.value
    }

    const handleGenerate = async () => {
      // 表单验证
      const configForm = document.querySelector('.config-form')
      if (!configForm) return

      try {
        generating.value = true
        progressVisible.value = true
        currentStepIndex.value = 0

        // 模拟生成步骤
        for (let i = 0; i < generateSteps.length; i++) {
          currentStepIndex.value = i
          await new Promise(resolve => setTimeout(resolve, 1000))
        }

        // 准备请求数据
        const tableNames = selectedTables.value.map(table => table.tableName)
        const requestData = {
          tableNames: tableNames,
          projectConfig: { ...projectConfig }
        }

        // 调用后端API生成项目
        const blob = await genApi.generateCompleteProject(requestData)

        // 下载生成的项目文件
        const url = window.URL.createObjectURL(blob)
        const link = document.createElement('a')
        link.href = url
        link.download = `${projectConfig.projectName || 'generated-project'}.zip`
        document.body.appendChild(link)
        link.click()
        document.body.removeChild(link)
        window.URL.revokeObjectURL(url)

        ElMessage.success('项目生成成功！')
        progressVisible.value = false

      } catch (error) {
        console.error('Generate project error:', error)
        ElMessage.error('项目生成失败：' + error.message)
        progressVisible.value = false
      } finally {
        generating.value = false
        currentStepIndex.value = 0
      }
    }

    // 生命周期
    onMounted(() => {
      loadTableList()
    })

    // 监听项目名称变化，自动更新相关字段
    watch(() => projectConfig.projectName, (newName) => {
      if (newName && !projectConfig.artifactId) {
        projectConfig.artifactId = newName
      }
    })

    return {
      // 数据
      tableList,
      selectedTables,
      tableSearchText,
      generating,
      progressVisible,
      currentStepIndex,
      projectConfig,
      configRules,
      generateSteps,
      expandedNodes,
      techStackCollapsed,

      // 计算属性
      filteredTables,
      selectAll,
      isIndeterminate,
      estimatedFiles,
      estimatedLines,
      canGenerate,
      generateTip,
      currentStep,
      currentStepDetail,
      progressPercentage,

      // 方法
      loadTableList,
      isTableSelected,
      toggleTableSelection,
      handleSelectAll,
      handleTableSearch,
      onProjectNameChange,
      generateJwtSecret,
      autoGenerateConfig,
      handleGenerate,
      previewProject,
      saveTemplate,
      toggleExpand,
      toggleTechStack
    }
  }
}
</script>

<style scoped>
.smart-code-factory {
  min-height: 100vh;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  padding: 0;
}

.factory-header {
  background: rgba(255, 255, 255, 0.95);
  backdrop-filter: blur(10px);
  border-bottom: 1px solid rgba(255, 255, 255, 0.2);
  padding: 2rem 0;
  margin-bottom: 2rem;
}

.header-content {
  max-width: 1400px;
  margin: 0 auto;
  padding: 0 2rem;
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.title-section {
  flex: 1;
}

.factory-title {
  font-size: 2.5rem;
  font-weight: 700;
  color: #2c3e50;
  margin: 0 0 0.5rem 0;
  display: flex;
  align-items: center;
  gap: 1rem;
}

.title-icon {
  font-size: 2.5rem;
  color: #667eea;
}

.subtitle {
  font-size: 1rem;
  font-weight: 400;
  color: #7f8c8d;
  margin-left: 1rem;
}

.factory-description {
  font-size: 1.1rem;
  color: #5a6c7d;
  margin: 0;
  line-height: 1.6;
}

.stats-section {
  display: flex;
  gap: 2rem;
}

.stat-item {
  text-align: center;
  padding: 1rem;
  background: rgba(102, 126, 234, 0.1);
  border-radius: 12px;
  min-width: 80px;
}

.stat-number {
  font-size: 2rem;
  font-weight: 700;
  color: #667eea;
  line-height: 1;
}

.stat-label {
  font-size: 0.9rem;
  color: #7f8c8d;
  margin-top: 0.5rem;
}

.factory-content {
  max-width: 1400px;
  margin: 0 auto;
  padding: 0 2rem 2rem;
}

.card-header {
  display: flex;
  align-items: center;
  justify-content: space-between;
  font-weight: 600;
  color: #2c3e50;
}

.header-left {
  display: flex;
  align-items: center;
  gap: 0.5rem;
}

.header-actions {
  display: flex;
  align-items: center;
  gap: 0.5rem;
}

.header-btn {
  width: 28px;
  height: 28px;
  border-radius: 50%;
  border: 1px solid rgba(102, 126, 234, 0.2);
  background: rgba(102, 126, 234, 0.05);
  transition: all 0.3s ease;
}

.header-btn:hover {
  background: rgba(102, 126, 234, 0.1);
  border-color: rgba(102, 126, 234, 0.3);
  transform: scale(1.05);
}

.badge {
  margin-left: auto;
}

.auto-btn {
  margin-left: auto;
  color: #667eea;
}

.auto-btn:hover {
  color: #5a67d8;
}

/* 表选择区域样式 */
.selection-card {
  height: 600px;
  overflow: hidden;
}

.search-section {
  margin-bottom: 1rem;
}

.table-list {
  height: 480px;
  display: flex;
  flex-direction: column;
}

.list-header {
  padding: 0.5rem 0;
  border-bottom: 1px solid #ebeef5;
  margin-bottom: 0.5rem;
}

.table-items {
  flex: 1;
  overflow-y: auto;
}

.table-item {
  display: flex;
  align-items: center;
  gap: 0.75rem;
  padding: 0.75rem;
  border-radius: 8px;
  cursor: pointer;
  transition: all 0.2s;
  border: 1px solid transparent;
}

.table-item:hover {
  background: #f8f9ff;
  border-color: #e1e6ff;
}

.table-item.selected {
  background: #e8f4fd;
  border-color: #409eff;
}

.table-info {
  flex: 1;
  min-width: 0;
}

.table-name {
  font-weight: 600;
  color: #2c3e50;
  font-size: 0.9rem;
}

.table-comment {
  font-size: 0.8rem;
  color: #7f8c8d;
  margin-top: 0.25rem;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}

/* 配置表单样式 */
.config-card {
  height: 600px;
  overflow: hidden;
}

.config-form {
  height: 520px;
  overflow-y: auto;
  padding-right: 0.5rem;
}

.form-section {
  margin-bottom: 1.5rem;
  padding-bottom: 1rem;
  border-bottom: 1px solid #f0f0f0;
}

.form-section:last-child {
  border-bottom: none;
}

.section-title {
  display: flex;
  align-items: center;
  gap: 0.5rem;
  font-size: 1rem;
  font-weight: 600;
  color: #2c3e50;
  margin: 0 0 1rem 0;
}

.form-tip {
  margin-top: 0.5rem;
}

/* 预览区域样式 */
.preview-card {
  height: 600px;
  display: flex;
  flex-direction: column;
}

.preview-card .el-card__header {
  flex-shrink: 0;
  padding: 1rem;
  border-bottom: 1px solid rgba(0, 0, 0, 0.05);
}

.preview-card .el-card__body {
  flex: 1;
  display: flex;
  flex-direction: column;
  padding: 0;
  height: calc(600px - 60px);
  overflow: hidden;
}

.preview-content {
  flex: 1;
  overflow-y: auto;
  padding: 1rem;
  max-height: calc(540px - 240px);
  min-height: 0;
}

.preview-content::-webkit-scrollbar {
  width: 6px;
}

.preview-content::-webkit-scrollbar-track {
  background: rgba(0, 0, 0, 0.05);
  border-radius: 3px;
}

.preview-content::-webkit-scrollbar-thumb {
  background: rgba(102, 126, 234, 0.3);
  border-radius: 3px;
}

.preview-content::-webkit-scrollbar-thumb:hover {
  background: rgba(102, 126, 234, 0.5);
}

.project-structure {
  margin-bottom: 1.5rem;
}

.project-structure {
  margin-bottom: 1.5rem;
}

.structure-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 1rem;
}

.structure-header h5 {
  font-size: 0.9rem;
  font-weight: 600;
  color: #2c3e50;
  margin: 0;
  display: flex;
  align-items: center;
  gap: 0.5rem;
}

.structure-stats {
  display: flex;
  gap: 0.5rem;
}

.tech-stack h5 {
  font-size: 0.9rem;
  font-weight: 600;
  color: #2c3e50;
  margin: 0;
}

.tech-stack-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  cursor: pointer;
  padding: 0.5rem;
  border-radius: 6px;
  transition: all 0.3s ease;
  margin-bottom: 0.75rem;
}

.tech-stack-header:hover {
  background: rgba(102, 126, 234, 0.05);
}

.collapse-icon {
  font-size: 0.8rem;
  color: #909399;
  transition: transform 0.3s ease;
}

.collapse-icon.expanded {
  transform: rotate(90deg);
}

.tech-stack-content {
  animation: slideDown 0.3s ease;
}

.structure-tree {
  font-size: 0.85rem;
  max-height: 300px;
  overflow-y: auto;
  padding-right: 0.5rem;
}

.structure-tree::-webkit-scrollbar {
  width: 4px;
}

.structure-tree::-webkit-scrollbar-track {
  background: rgba(0, 0, 0, 0.05);
  border-radius: 2px;
}

.structure-tree::-webkit-scrollbar-thumb {
  background: rgba(102, 126, 234, 0.3);
  border-radius: 2px;
}

.structure-tree::-webkit-scrollbar-thumb:hover {
  background: rgba(102, 126, 234, 0.5);
}

/* 新的树形结构样式 */
.tree-item {
  margin-bottom: 0.5rem;
}

.item-header {
  display: flex;
  align-items: center;
  gap: 0.5rem;
  padding: 0.5rem;
  border-radius: 8px;
  cursor: pointer;
  transition: all 0.3s ease;
  border: 1px solid transparent;
}

.item-header:hover {
  background: rgba(102, 126, 234, 0.05);
  border-color: rgba(102, 126, 234, 0.1);
  transform: translateX(2px);
}

.root-item .item-header {
  background: linear-gradient(135deg, rgba(102, 126, 234, 0.1), rgba(118, 75, 162, 0.1));
  border-color: rgba(102, 126, 234, 0.2);
  font-weight: 600;
}

.backend-item .item-header {
  background: rgba(103, 194, 58, 0.05);
  border-color: rgba(103, 194, 58, 0.1);
}

.backend-item .item-header:hover {
  background: rgba(103, 194, 58, 0.1);
  border-color: rgba(103, 194, 58, 0.2);
}

.frontend-item .item-header {
  background: rgba(230, 162, 60, 0.05);
  border-color: rgba(230, 162, 60, 0.1);
}

.frontend-item .item-header:hover {
  background: rgba(230, 162, 60, 0.1);
  border-color: rgba(230, 162, 60, 0.2);
}

.deploy-item .item-header {
  background: rgba(64, 158, 255, 0.05);
  border-color: rgba(64, 158, 255, 0.1);
}

.deploy-item .item-header:hover {
  background: rgba(64, 158, 255, 0.1);
  border-color: rgba(64, 158, 255, 0.2);
}

.expand-icon {
  font-size: 0.8rem;
  color: #909399;
  transition: transform 0.3s ease;
}

.expand-icon.expanded {
  transform: rotate(90deg);
}

.folder-icon {
  font-size: 1rem;
  color: #667eea;
}

.folder-icon.root {
  color: #667eea;
}

.folder-icon.backend {
  color: #67c23a;
}

.folder-icon.frontend {
  color: #e6a23c;
}

.folder-icon.deploy {
  color: #409eff;
}

.file-icon {
  font-size: 0.9rem;
  color: #909399;
}

.item-name {
  font-weight: 500;
  color: #2c3e50;
  flex: 1;
}

.item-children {
  margin-left: 1.5rem;
  border-left: 2px solid rgba(102, 126, 234, 0.1);
  padding-left: 1rem;
  margin-top: 0.5rem;
  animation: slideDown 0.3s ease;
}

@keyframes slideDown {
  from {
    opacity: 0;
    transform: translateY(-10px);
  }
  to {
    opacity: 1;
    transform: translateY(0);
  }
}

.file-item {
  display: flex;
  align-items: center;
  gap: 0.5rem;
  padding: 0.25rem 0.5rem;
  margin: 0.25rem 0;
  border-radius: 6px;
  transition: all 0.2s ease;
}

.file-item:hover {
  background: rgba(102, 126, 234, 0.05);
}

.file-name {
  font-weight: 500;
  color: #2c3e50;
  margin-right: 0.5rem;
}

.file-desc {
  font-size: 0.75rem;
  color: #909399;
  margin-left: auto;
}

.tech-stack {
  margin-bottom: 1rem;
}

.tech-categories{
  max-height: 180px;
  overflow-y: auto;
  padding-right: 0.5rem;
}

.tech-categories::-webkit-scrollbar {
  width: 6px;
}

.tech-categories::-webkit-scrollbar-track {
  background: rgba(0, 0, 0, 0.05);
  border-radius: 3px;
}

.tech-categories::-webkit-scrollbar-thumb {
  background: rgba(102, 126, 234, 0.3);
  border-radius: 3px;
}

.tech-categories::-webkit-scrollbar-thumb:hover {
  background: rgba(102, 126, 234, 0.5);
}

.tech-category {
  margin-bottom: 1rem;
  padding: 0.75rem;
  background: rgba(255, 255, 255, 0.5);
  border-radius: 8px;
  border: 1px solid rgba(0, 0, 0, 0.05);
}

.tech-category:last-child {
  margin-bottom: 0;
}

.category-title {
  display: flex;
  align-items: center;
  gap: 0.5rem;
  font-size: 0.85rem;
  font-weight: 600;
  color: #2c3e50;
  margin-bottom: 0.5rem;
}

.tech-tags {
  display: flex;
  flex-direction: column;
  gap: 0.5rem;
}

.tech-item {
  display: flex;
  align-items: center;
  gap: 0.5rem;
  padding: 0.25rem 0;
}

.tech-desc {
  font-size: 0.75rem;
  color: #909399;
  margin-left: auto;
}

.generate-actions {
  display: flex;
  flex-direction: column;
  gap: 1rem;
  padding: 1rem;
  border-top: 1px solid rgba(0, 0, 0, 0.05);
  background: rgba(255, 255, 255, 0.95);
  backdrop-filter: blur(10px);
  margin-top: auto;
  flex-shrink: 0;
  position: relative;
  z-index: 10;
  overflow: visible;
  min-height: 220px;
  max-height: 240px;
}

.generate-info {
  display: grid;
  grid-template-columns: 1fr;
  gap: 0.5rem;
  padding: 0.75rem;
  background: rgba(102, 126, 234, 0.05);
  border-radius: 8px;
  border: 1px solid rgba(102, 126, 234, 0.1);
}

.info-item {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.info-label {
  font-size: 0.8rem;
  color: #909399;
}

.info-value {
  font-size: 0.85rem;
  font-weight: 600;
  color: #2c3e50;
}

.generate-btn {
  width: 100%;
  height: 52px;
  font-size: 1.1rem;
  font-weight: 600;
  border-radius: 12px;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  border: none;
  box-shadow: 0 4px 15px rgba(102, 126, 234, 0.3);
  transition: all 0.3s ease;
}

.generate-btn:hover {
  background: linear-gradient(135deg, #5a67d8 0%, #6b46a3 100%);
  box-shadow: 0 6px 20px rgba(102, 126, 234, 0.4);
  transform: translateY(-2px);
}

.generate-btn:active {
  transform: translateY(0);
}



.action-tips {
  margin-top: 0;
  margin-bottom: 1rem;
}

.tip-alert {
  border-radius: 8px;
}

.success-tips {
  display: flex;
  flex-direction: column;
  gap: 0.5rem;
  padding: 0.75rem;
  background: rgba(103, 194, 58, 0.05);
  border-radius: 8px;
  border: 1px solid rgba(103, 194, 58, 0.1);
}

.tip-item {
  display: flex;
  align-items: center;
  gap: 0.5rem;
  font-size: 0.85rem;
  color: #67c23a;
}

.tip-item .el-icon {
  font-size: 1rem;
}

/* 进度对话框样式 */
.progress-content {
  text-align: center;
  padding: 1rem 0;
}

.progress-info {
  margin-bottom: 2rem;
}

.progress-icon {
  font-size: 3rem;
  color: #667eea;
  margin-bottom: 1rem;
  animation: spin 2s linear infinite;
}

@keyframes spin {
  from { transform: rotate(0deg); }
  to { transform: rotate(360deg); }
}

.progress-info h3 {
  font-size: 1.25rem;
  color: #2c3e50;
  margin: 0 0 0.5rem 0;
}

.progress-info p {
  color: #7f8c8d;
  margin: 0;
}

.progress-steps {
  display: flex;
  justify-content: space-between;
  margin-top: 2rem;
  padding: 0 1rem;
}

.step-item {
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 0.5rem;
  flex: 1;
  position: relative;
}

.step-item:not(:last-child)::after {
  content: '';
  position: absolute;
  top: 15px;
  left: 60%;
  right: -40%;
  height: 2px;
  background: #e4e7ed;
  z-index: 0;
}

.step-item.completed:not(:last-child)::after {
  background: #67c23a;
}

.step-icon {
  width: 30px;
  height: 30px;
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 0.8rem;
  font-weight: 600;
  background: #e4e7ed;
  color: #909399;
  position: relative;
  z-index: 1;
}

.step-item.completed .step-icon {
  background: #67c23a;
  color: white;
}

.step-item.active .step-icon {
  background: #409eff;
  color: white;
  animation: pulse 1.5s ease-in-out infinite;
}

@keyframes pulse {
  0% { transform: scale(1); }
  50% { transform: scale(1.1); }
  100% { transform: scale(1); }
}

.step-text {
  font-size: 0.75rem;
  color: #909399;
  text-align: center;
  line-height: 1.2;
}

.step-item.completed .step-text {
  color: #67c23a;
}

.step-item.active .step-text {
  color: #409eff;
  font-weight: 600;
}

/* 响应式设计 */
@media (max-width: 1200px) {
  .factory-content {
    padding: 0 1rem 2rem;
  }

  .header-content {
    padding: 0 1rem;
    flex-direction: column;
    gap: 1.5rem;
    text-align: center;
  }

  .stats-section {
    justify-content: center;
  }
}

@media (max-width: 768px) {
  .factory-title {
    font-size: 2rem;
  }

  .stats-section {
    gap: 1rem;
  }

  .stat-item {
    padding: 0.75rem;
    min-width: 60px;
  }

  .stat-number {
    font-size: 1.5rem;
  }
}
</style>

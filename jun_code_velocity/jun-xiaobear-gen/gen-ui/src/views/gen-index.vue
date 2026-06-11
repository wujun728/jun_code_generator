<template>
  <div class="gen-page">
    <!-- 整体白板容器 -->
    <div class="gen-workspace">
      <!-- 页面头部 -->
      <div class="page-header">
        <div class="header-content">
          <h1 class="page-title">
            <el-icon><MagicStick /></el-icon>
            代码生成工作台
          </h1>
          <p class="page-description">基于数据表结构，智能生成高质量的前后端代码</p>
        </div>

        <!-- 精致的统计卡片 -->
        <div class="stats-cards">
          <div class="stat-card">
            <div class="stat-icon">
              <el-icon><Grid /></el-icon>
            </div>
            <div class="stat-content">
              <div class="stat-label">数据表总数</div>
              <div class="stat-value">{{ total }}</div>
            </div>
          </div>
          <div class="stat-card">
            <div class="stat-icon selected">
              <el-icon><Check /></el-icon>
            </div>
            <div class="stat-content">
              <div class="stat-label">已选择表</div>
              <div class="stat-value">{{ ids.length }}</div>
            </div>
          </div>
          <div class="stat-card">
            <div class="stat-icon generated">
              <el-icon><MagicStick /></el-icon>
            </div>
            <div class="stat-content">
              <div class="stat-label">今日生成</div>
              <div class="stat-value">{{ generatedCount || 0 }}</div>
            </div>
          </div>
        </div>
      </div>

      <!-- 智能工具栏 -->
      <div class="smart-toolbar">
        <!-- 左侧主要操作区 -->
        <div class="toolbar-left">
          <!-- 未选择时显示主要操作 -->
          <template v-if="ids.length === 0">
            <el-button type="primary" size="default" @click="openImportTable">
              <el-icon><Upload /></el-icon>
              导入数据表
            </el-button>
            <el-button type="primary" size="default" plain @click="createTable">
              <el-icon><DocumentAdd /></el-icon>
              SQL脚本生成
            </el-button>
          </template>
          
          <!-- 选择后显示批量操作 -->
          <template v-else>
            <el-button
              type="primary"
              size="default"
              @click="handleGenTable"
              :loading="generating"
            >
              <el-icon><MagicStick /></el-icon>
              <span v-if="generating">正在生成代码...</span>
              <span v-else>生成 {{ ids.length }} 个表的代码</span>
            </el-button>
            <el-button type="primary" size="default" plain @click="handleCompleteProject" :loading="generatingProject">
              <el-icon><DocumentCopy /></el-icon>
              生成完整项目
            </el-button>
          </template>
        </div>

        <!-- 右侧筛选和工具区 -->
        <div class="toolbar-right">
          <!-- 选择信息 -->
          <div class="selection-info" v-if="ids.length > 0">
            <el-tag type="primary" size="default">
              <el-icon><Check /></el-icon>
              已选择 {{ ids.length }} 个数据表
            </el-tag>
          </div>

          <!-- 数据源选择器 -->
          <div class="datasource-selector">
            <el-select
              v-model="selectedDataSourceId"
              placeholder="选择数据源"
              @change="handleDataSourceChange"
              clearable
              size="default"
            >
              <template #prefix>
                <el-icon><Connection /></el-icon>
              </template>
              <el-option
                v-for="ds in dataSourceList"
                :key="ds.id"
                :label="ds.name"
                :value="ds.id"
              >
                <div class="datasource-option">
                  <div class="option-main">
                    <span class="option-name">{{ ds.name }}</span>
                    <el-tag
                      :type="getDbTypeColor(ds.type)"
                      size="small"
                      class="option-tag"
                    >
                      {{ getDbTypeName(ds.type) }}
                    </el-tag>
                  </div>
                  <div class="option-detail">
                    <span>{{ ds.url }}:{{ ds.port }}</span>
                  </div>
                </div>
              </el-option>
            </el-select>
          </div>

          <!-- 搜索框 -->
          <el-input
            v-model="quickSearch"
            placeholder="搜索表名或注释..."
            clearable
            @input="handleQuickSearch"
            class="search-input"
          >
            <template #prefix>
              <el-icon><Search /></el-icon>
            </template>
          </el-input>

          <!-- 重置按钮（当有筛选条件时显示） -->
          <el-button
            v-if="quickSearch || showSearch"
            @click="resetQuery"
            class="reset-btn"
            size="default"
          >
            重置
          </el-button>

          <!-- 工具按钮组 -->
          <div class="tool-buttons">
            <el-tooltip content="高级筛选" placement="top">
              <el-button
                :type="showSearch ? 'primary' : 'default'"
                @click="showSearch = !showSearch"
                size="default"
              >
                <el-icon><Filter /></el-icon>
              </el-button>
            </el-tooltip>
            <el-tooltip content="刷新数据" placement="top">
              <el-button @click="getList" size="default">
                <el-icon><Refresh /></el-icon>
              </el-button>
            </el-tooltip>
          </div>
        </div>
      </div>

      <!-- 筛选器 -->
      <div class="filter-section" v-if="showSearch">
        <el-form :model="queryParams" ref="queryForm" :inline="true">
          <el-form-item label="表名称" prop="tableName">
            <el-input
              v-model="queryParams.tableName"
              placeholder="请输入表名称"
              clearable
              @keyup.enter.native="handleQuery"
              style="width: 200px"
            />
          </el-form-item>
          <el-form-item label="创建时间">
            <el-date-picker
              v-model="dateRange"
              style="width: 240px"
              value-format="yyyy-MM-dd"
              type="daterange"
              range-separator="至"
              start-placeholder="开始日期"
              end-placeholder="结束日期"
            />
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="handleQuery">
              <el-icon><Search /></el-icon>
              搜索
            </el-button>
            <el-button @click="resetQuery">
              <el-icon><Refresh /></el-icon>
              重置
            </el-button>
          </el-form-item>
        </el-form>
      </div>

      <!-- 数据表格 -->
      <div class="table-section">
        <el-table
          :data="tableList"
          :loading="loading"
          @selection-change="handleSelectionChange"
          stripe
          style="width: 100%"
        >
          <el-table-column type="selection" width="48" align="center" />

          <el-table-column label="序号" width="72" align="center">
            <template #default="scope">
              {{ (queryParams.pageNum - 1) * queryParams.pageSize + scope.$index + 1 }}
            </template>
          </el-table-column>

          <el-table-column label="表名" prop="tableName" width="240" align="left">
            <template #default="scope">
              <div>
                <div style="font-weight: 500; margin-bottom: 4px;">
                  {{ scope.row.tableName || '-' }}
                </div>
                <div style="font-size: 12px; color: #666;" v-if="scope.row.tableComment">
                  {{ scope.row.tableComment }}
                </div>
              </div>
            </template>
          </el-table-column>

          <el-table-column label="类名" prop="className" width="240" align="left" />
          <el-table-column label="前端" width="140" align="left">
            <template #default="scope">
              <el-tag :type="getTechStackColor('frontend', scope.row.frontEnd)" size="small">
                {{ getFrontEndName(scope.row.frontEnd) }}
              </el-tag>
            </template>
          </el-table-column>
          <el-table-column label="后端" width="140" align="left">
            <template #default="scope">
              <el-tag :type="getTechStackColor('backend', scope.row.backEnd)" size="small">
                {{ getBackEndName(scope.row.backEnd) }}
              </el-tag>
            </template>
          </el-table-column>
          <el-table-column label="是否若依" width="120" align="left">
            <template #default="scope">
              <el-tag :type="scope.row.isRuoYi === '1' ? 'success' : 'info'" size="small">
                {{ scope.row.isRuoYi === '1' ? '是' : '否' }}
              </el-tag>
            </template>
          </el-table-column>

          <el-table-column label="更新时间" prop="updateTime" align="left" width="180"/>

          <el-table-column label="操作" align="center" fixed="right" width="320">
            <template #default="scope">
              <div class="table-actions">
                <el-button size="small" type="primary" @click="handleGenTable(scope.row)">
                  <el-icon><MagicStick /></el-icon>
                  生成
                </el-button>
                <el-button size="small" @click="handlePreview(scope.row)">
                  <el-icon ><View /></el-icon>
                  <span style="margin-right: 4px;">预览</span>
                </el-button>
                <el-button size="small" @click="handleEditTable(scope.row)">
                  <el-icon><Setting /></el-icon>
                  配置
                </el-button>
                <el-dropdown style="margin-left: 12px;">
                  <el-button size="small" circle>
                    <el-icon><More /></el-icon>
                  </el-button>
                  <template #dropdown>
                    <el-dropdown-menu>
                      <el-dropdown-item @click="handleSynchDb(scope.row)">
                        <el-icon><Refresh /></el-icon>
                        同步表结构
                      </el-dropdown-item>
                      <el-dropdown-item @click="handleDelete(scope.row)" divided>
                        <el-icon><Delete /></el-icon>
                        删除
                      </el-dropdown-item>
                    </el-dropdown-menu>
                  </template>
                </el-dropdown>
              </div>
            </template>
          </el-table-column>
        </el-table>

        <!-- 分页 -->
        <div class="pagination-wrapper" v-if="total > queryParams.pageSize">
          <el-pagination
            v-model:current-page="queryParams.pageNum"
            v-model:page-size="queryParams.pageSize"
            :page-sizes="[10, 20, 50, 100]"
            :total="total"
            layout="total, sizes, prev, pager, next, jumper"
            @size-change="getList"
            @current-change="getList"
          />
        </div>
      </div>
    </div>
  </div>

  <!-- 预览界面 -->
    <el-dialog :title="preview.title" v-model="preview.open" width="80%" top="5vh" append-to-body class="scrollbar">
      <el-tabs v-model="preview.activeName">
        <el-tab-pane
          v-for="(value, key) in preview.data"
          :label="key.substring(key.lastIndexOf('/')+1,key.indexOf('.vm'))"
          :name="key.substring(key.lastIndexOf('/')+1,key.indexOf('.vm'))"
          :key="key"
        >
          <el-link :underline="false" @click="copyToClipboard(value)" style="float:right">
              <template #icon><el-icon><DocumentCopy /></el-icon></template>
              复制
            </el-link>
          <pre><code class="hljs" v-html="highlightedCode(value, key)"></code></pre>
        </el-tab-pane>
      </el-tabs>
    </el-dialog>
    <import-table ref="import" @ok="handleQuery" />

    <!--执行脚本-->
    <el-dialog 
      title="执行脚本" 
      v-model="centerDialogVisible" 
      width="60%" 
      append-to-body 
      top="5vh"
    >
      <el-input type="textarea" v-model="noticeContent" placeholder="请输入创建数据表脚本" :rows="10"/>
      <template #footer>
        <div class="dialog-footer">
          <el-button @click="centerDialogVisible = false">取 消</el-button>
          <el-button type="primary" @click="createTableSubmit">确 定</el-button>
        </div>
      </template>
    </el-dialog>

    <!-- 项目配置对话框 -->
    <el-dialog
      title="完整项目配置"
      v-model="projectConfigVisible"
      width="70%"
      append-to-body
      top="5vh"
    >
      <el-form
        ref="projectForm"
        :model="projectForm"
        :rules="projectRules"
        label-width="120px"
        class="project-form"
      >
        <div class="form-section">
          <h4 class="section-title">
            <el-icon><Setting /></el-icon>
            基础信息
          </h4>
          <el-row :gutter="20">
            <el-col :span="12">
              <el-form-item label="项目名称" prop="projectName">
                <el-input v-model="projectForm.projectName" placeholder="请输入项目名称" />
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="项目描述" prop="projectComment">
                <el-input v-model="projectForm.projectComment" placeholder="请输入项目描述" />
              </el-form-item>
            </el-col>
          </el-row>
          <el-row :gutter="20">
            <el-col :span="12">
              <el-form-item label="包名" prop="packageName">
                <el-input v-model="projectForm.packageName" placeholder="com.example.demo" />
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="类名前缀" prop="className">
                <el-input v-model="projectForm.className" placeholder="Demo" />
              </el-form-item>
            </el-col>
          </el-row>
          <el-row :gutter="20">
            <el-col :span="8">
              <el-form-item label="Group ID" prop="groupId">
                <el-input v-model="projectForm.groupId" placeholder="com.example" />
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="Artifact ID" prop="artifactId">
                <el-input v-model="projectForm.artifactId" placeholder="demo" />
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="版本" prop="version">
                <el-input v-model="projectForm.version" placeholder="1.0.0" />
              </el-form-item>
            </el-col>
          </el-row>
        </div>

        <div class="form-section">
          <h4 class="section-title">
            <el-icon><Connection /></el-icon>
            数据库配置
          </h4>
          <el-row :gutter="20">
            <el-col :span="12">
              <el-form-item label="数据库URL" prop="datasourceUrl">
                <el-input v-model="projectForm.datasourceUrl" placeholder="jdbc:mysql://localhost:3306/demo" />
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="驱动类名" prop="driverClassName">
                <el-input v-model="projectForm.driverClassName" placeholder="com.mysql.cj.jdbc.Driver" />
              </el-form-item>
            </el-col>
          </el-row>
          <el-row :gutter="20">
            <el-col :span="12">
              <el-form-item label="用户名" prop="datasourceUsername">
                <el-input v-model="projectForm.datasourceUsername" placeholder="root" />
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="密码" prop="datasourcePassword">
                <el-input v-model="projectForm.datasourcePassword" type="password" placeholder="请输入数据库密码" show-password />
              </el-form-item>
            </el-col>
          </el-row>
        </div>

        <div class="form-section">
          <h4 class="section-title">
            <el-icon><Monitor /></el-icon>
            服务配置
          </h4>
          <el-row :gutter="20">
            <el-col :span="12">
              <el-form-item label="服务端口" prop="serverPort">
                <el-input-number v-model="projectForm.serverPort" :min="1000" :max="65535" placeholder="8080" />
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="作者" prop="author">
                <el-input v-model="projectForm.author" placeholder="javaxiaobear" />
              </el-form-item>
            </el-col>
          </el-row>
        </div>
      </el-form>

      <template #footer>
        <div class="dialog-footer">
          <el-button @click="projectConfigVisible = false">取 消</el-button>
          <el-button type="primary" @click="confirmGenerateProject" :loading="generatingProject">
            {{ generatingProject ? '正在生成...' : '生成完整项目' }}
          </el-button>
        </div>
      </template>
    </el-dialog>
</template>

<script>
import download from "../utils/download";
import genApi from '@/api/gen'
import datasourceApi from '@/api/dataSource'
import importTable from './importTable.vue'
import TechStackUtils from '@/utils/tech-stack'
import hljs from 'highlight.js/lib/core'
import 'highlight.js/styles/github.css'
import pagination from "@/components/Pagination/index.vue";
import java from 'highlight.js/lib/languages/java'
import xml from 'highlight.js/lib/languages/xml'
import javascript from 'highlight.js/lib/languages/javascript'
import sql from 'highlight.js/lib/languages/sql'
import {
  MagicStick,
  Check,
  Search,
  Refresh,
  Setting,
  DocumentAdd,
  Upload,
  More,
  Edit,
  Delete,
  View,
  DocumentCopy,
  Filter
} from '@element-plus/icons-vue'

hljs.registerLanguage("java", java);
hljs.registerLanguage("xml", xml);
hljs.registerLanguage("html", xml);
hljs.registerLanguage("vue", xml);
hljs.registerLanguage("javascript", javascript);
hljs.registerLanguage("sql", sql);

export default {
  name: "Gen",
  components: {
    importTable,
    pagination,
    MagicStick,
    Check,
    Search,
    Refresh,
    Setting,
    DocumentAdd,
    Upload,
    More,
    Edit,
    Delete,
    View,
    DocumentCopy,
    Filter
  },
  data() {
    return {
      // 遮罩层
      loading: true,
      // 唯一标识符
      uniqueId: "",
      // 选中数组
      ids: [],
      // 选中表数组
      tableNames: [],
      noticeContent: '',
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: false,
      // 快速搜索
      quickSearch: '',
      // 生成状态
      generating: false,
      // 项目生成状态
      generatingProject: false,
      // 项目配置对话框
      projectConfigVisible: false,
      // 项目配置表单
      projectForm: {
        projectName: 'demo-project',
        projectComment: '演示项目',
        packageName: 'com.example.demo',
        className: 'Demo',
        groupId: 'com.example',
        artifactId: 'demo',
        version: '1.0.0',
        author: 'javaxiaobear',
        driverClassName: 'com.mysql.cj.jdbc.Driver',
        datasourceUrl: 'jdbc:mysql://localhost:3306/demo',
        datasourceUsername: 'root',
        datasourcePassword: '123456',
        serverPort: 8080,
        jwtSecret: 'javaxiaobear-secret-key'
      },
      // 项目配置表单验证规则
      projectRules: {
        projectName: [
          { required: true, message: '请输入项目名称', trigger: 'blur' },
          { min: 2, max: 50, message: '长度在 2 到 50 个字符', trigger: 'blur' }
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
        ]
      },
      // 工具提示
      tooltipVisible: false,
      tooltipContent: '',
      // 视图模式
      viewMode: 'table',
      // 高级搜索
      showAdvanced: false,
      // 生成统计
      generatedCount: 0,
      // 数据源相关
      selectedDataSourceId: null,
      dataSourceList: [],
      // 总条数
      total: 0,
      // 表数据
      tableList: [],
      // 日期范围
      dateRange: "",
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 50,
        tableName: undefined,
        tableComment: undefined
      },
      // 预览参数
      preview: {
        open: false,
        title: "代码预览",
        data: {},
        activeName: "domain.java"
      },
      // 执行脚本
      centerDialogVisible: false,
      // 性能优化相关
      debounceTimer: null,
      lastSearchTime: 0,
      searchCache: new Map(),
      // 滚动监听相关
      scrollListener: null,
      isScrolled: false
    };
  },

  computed: {
    // 计算属性优化
    hasSelectedTables() {
      return this.ids.length > 0
    },

    selectedTablesCount() {
      return this.ids.length
    },

    isAllSelected() {
      return this.tableList.length > 0 && this.ids.length === this.tableList.length
    },

    // 分页信息
    paginationInfo() {
      const start = (this.queryParams.pageNum - 1) * this.queryParams.pageSize + 1
      const end = Math.min(this.queryParams.pageNum * this.queryParams.pageSize, this.total)
      return `显示 ${start} 到 ${end} 条，共 ${this.total} 条`
    },

    // 今日生成统计
    todayGenerated() {
      // 这里可以从后端获取今日生成的数据，暂时返回模拟数据
      return Math.floor(Math.random() * 10) + 1
    }
  },

  created() {
    this.getList();
    this.getDataSourceList();
    this.initScrollListener();
  },
  activated() {
    const time = this.$route.query.t;
    if (time != null && time != this.uniqueId) {
      this.uniqueId = time;

      // 恢复分页参数
      const pageNum = Number(this.$route.query.pageNum) || 1;
      const pageSize = Number(this.$route.query.pageSize) || 50;

      console.log('页面激活，恢复分页参数:', { pageNum, pageSize });

      this.queryParams.pageNum = pageNum;
      this.queryParams.pageSize = pageSize;

      this.getList();
    }
  },

  beforeUnmount() {
    // 清理定时器
    if (this.debounceTimer) {
      clearTimeout(this.debounceTimer);
      this.debounceTimer = null;
    }

    // 清理缓存
    if (this.searchCache) {
      this.searchCache.clear();
    }

    // 清理滚动监听
    if (this.scrollListener) {
      window.removeEventListener('scroll', this.scrollListener);
      this.scrollListener = null;
    }

    console.log('Gen-index component cleanup completed');
  },

  methods: {
    /** 初始化滚动监听 */
    initScrollListener() {
      this.scrollListener = this.throttle(() => {
        const scrollTop = window.pageYOffset || document.documentElement.scrollTop;
        this.isScrolled = scrollTop > 50;
        
        // 动态更新工具栏样式
        const toolbar = document.querySelector('.toolbar-section');
        if (toolbar) {
          if (this.isScrolled) {
            toolbar.classList.add('scrolled');
          } else {
            toolbar.classList.remove('scrolled');
          }
        }
      }, 16); // 约60fps
      
      window.addEventListener('scroll', this.scrollListener, { passive: true });
    },

    /** 节流函数 */
    throttle(func, limit) {
      let inThrottle;
      return function() {
        const args = arguments;
        const context = this;
        if (!inThrottle) {
          func.apply(context, args);
          inThrottle = true;
          setTimeout(() => inThrottle = false, limit);
        }
      };
    },

    /** 查询表集合 */
    getList() {
      // 生成缓存键
      const cacheKey = JSON.stringify(this.queryParams)
      const now = Date.now()

      // 检查缓存（5秒内的相同查询使用缓存）
      if (this.searchCache.has(cacheKey) && (now - this.lastSearchTime) < 5000) {
        const cachedData = this.searchCache.get(cacheKey)
        this.tableList = cachedData.rows
        this.total = cachedData.total
        return
      }

      this.loading = true
      this.lastSearchTime = now

      if (this.selectedDataSourceId) {
        this.queryParams.selectId = this.selectedDataSourceId
      }

      // 使用默认数据源查询
      genApi.listTable(this.queryParams).then(response => {
        this.tableList = response.rows || []
        this.total = response.total || 0
        this.loading = false

        // 缓存结果
        this.searchCache.set(cacheKey, {
          rows: this.tableList,
          total: this.total
        })

        // 限制缓存大小
        if (this.searchCache.size > 10) {
          const firstKey = this.searchCache.keys().next().value
          this.searchCache.delete(firstKey)
        }
      }).catch(error => {
        this.loading = false
        console.error('查询表列表失败:', error)
        this.$message.error('查询失败，请重试')
      })
      // 如果选择了数据源，使用指定数据源查询
      // if (this.selectedDataSourceId) {
      //   datasourceApi.listDbTableWithDataSource(this.selectedDataSourceId, this.queryParams).then(response => {
      //     this.tableList = response.rows;
      //     this.total = response.total;
      //     this.loading = false;
      //   }).catch(() => {
      //     this.loading = false;
      //   });
      // } else {
      //   // 使用默认数据源查询
      //   genApi.listTable(this.queryParams).then(response => {
      //     this.tableList = response.rows;
      //     this.total = response.total;
      //     this.loading = false;
      //   }).catch(() => {
      //     this.loading = false;
      //   });
      // }
    },
    /** 获取数据源列表 */
    getDataSourceList() {
      datasourceApi.listDatasource({}).then(response => {
        this.dataSourceList = response.rows || [];
      });
    },
    /** 复制到剪贴板 */
    async copyToClipboard(text) {
      try {
        await navigator.clipboard.writeText(text);
        this.$message.success("复制成功");
      } catch (err) {
        // 降级方案：使用传统的复制方法
        const textArea = document.createElement('textarea');
        textArea.value = text;
        document.body.appendChild(textArea);
        textArea.select();
        try {
          document.execCommand('copy');
          this.$message.success("复制成功");
        } catch (fallbackErr) {
          this.$message.error("复制失败，请手动复制");
        }
        document.body.removeChild(textArea);
      }
    },
    /** 搜索按钮操作 */
    handleQuery() {
      // 清除之前的防抖定时器
      if (this.debounceTimer) {
        clearTimeout(this.debounceTimer)
      }

      // 设置防抖
      this.debounceTimer = setTimeout(() => {
        this.queryParams.pageNum = 1
        this.searchCache.clear() // 清空缓存
        this.getList()
      }, 300)
    },

    /** 快速搜索 */
    handleQuickSearch() {
      // 清除之前的防抖定时器
      if (this.debounceTimer) {
        clearTimeout(this.debounceTimer)
      }

      // 设置防抖，延迟300ms执行搜索
      this.debounceTimer = setTimeout(() => {
        this.queryParams.tableName = this.quickSearch
        this.queryParams.pageNum = 1
        this.getList()
      }, 300)
    },

    /** 数据源变化处理 */
    handleDataSourceChange(dataSourceId) {
      console.log('数据源变化:', dataSourceId)
      this.queryParams.pageNum = 1
      this.getList()
    },

    /** 获取数据库类型名称 */
    getDbTypeName(type) {
      const names = {
        '0': 'MySQL',
        '1': 'Oracle', 
        '2': 'SQL Server',
        '3': 'PostgreSQL',
        '4': 'ClickHouse',
        '5': 'SQLite',
        '6': 'DB2',
        '7': '达梦'
      }
      return names[type] || 'Unknown'
    },

    /** 获取前端框架名称 */
    getFrontEndName(frontEnd) {
      return TechStackUtils.getFrontendLabel(frontEnd)
    },
    /** 获取后端框架名称 */
    getBackEndName(backEnd) {
      return TechStackUtils.getBackendLabel(backEnd)
    },
    /** 获取技术栈颜色 */
    getTechStackColor(type, value) {
      return TechStackUtils.getTechStackColor(type, value)
    },
    /** 生成代码操作 */
    handleGenTable(row) {
      const tableNames = row.tableName || this.tableNames;
      if (tableNames == "") {
        this.$message.error("请选择要生成的数据");
        return;
      }

      // 添加生成动画
      this.generating = true;

      // 显示进度提示
      this.$message({
        message: '正在生成代码，请稍候...',
        type: 'info',
        duration: 2000
      });

      if(row.genType === "1") {
        genApi.genCode(row.tableName).then(response => {
          this.generating = false;
          this.$message.success("成功生成到自定义路径：" + row.genPath);
        }).catch(() => {
          this.generating = false;
        });
      } else {
        setTimeout(() => {
          this.generating = false;
          download.zip("/tool/gen/batchGenCode?tables=" + tableNames, "javaxiaobear代码生成");
          this.$message.success("代码生成完成！");
        }, 1500);
      }
    },
    /** 一键生成完整项目 */
    handleCompleteProject() {
      if (this.tableNames.length === 0) {
        this.$message.error("请选择要生成的数据表");
        return;
      }

      // 显示项目配置对话框
      this.projectConfigVisible = true;
    },
    /** 确认生成完整项目 */
    confirmGenerateProject() {
      this.$refs.projectForm.validate((valid) => {
        if (valid) {
          this.generatingProject = true;
          this.projectConfigVisible = false;

          // 显示进度提示
          this.$message({
            message: '正在生成完整项目，请稍候...',
            type: 'info',
            duration: 3000
          });

          const params = {
            tableNames: this.tableNames,
            projectConfig: this.projectForm
          };

          // 调用后端API生成完整项目
          this.$http.post('/tool/gen/generateCompleteProject', params, {
            responseType: 'blob'
          }).then(response => {
            this.generatingProject = false;

            // 创建下载链接
            const blob = new Blob([response.data], { type: 'application/zip' });
            const url = window.URL.createObjectURL(blob);
            const link = document.createElement('a');
            link.href = url;
            link.download = `${this.projectForm.projectName}-complete.zip`;
            document.body.appendChild(link);
            link.click();
            document.body.removeChild(link);
            window.URL.revokeObjectURL(url);

            this.$message.success("完整项目生成成功！");
          }).catch(error => {
            this.generatingProject = false;
            console.error('生成完整项目失败:', error);
            this.$message.error("生成完整项目失败，请重试");
          });
        }
      });
    },
    /** 同步数据库操作 */
    handleSynchDb(row) {
      const tableName = row.tableName;
      this.$confirm('确认要强制同步"' + tableName + '"表结构吗？').then(function() {
        return genApi.synchDb(tableName);
      }).then(() => {
        this.$message.success("同步成功");
      }).catch(() => {});
    },
    /** 打开导入表弹窗 */
    openImportTable() {
      this.$refs.import.show();
    },
    /** 导出*/
    handleExport(){
      const tableIds = this.ids;

      if (tableIds.length == 0) {
        this.$message.error("请选择要导出的数据");
        return;
      }
      download.download('/tool/gen/export?tableIds=' + tableIds, `数据字典.xlsx`)
    },
    handleBatch(){

    },
    createTable(){
      this.centerDialogVisible = true;
    },
    createTableSubmit(){
      const sql = this.noticeContent
      if (sql == ""){
        this.$message.error("请输入要执行创建表的sql");
        return;
      }
      genApi.createNewTable({ sql: sql }).then(res => {
        if (res.code === 200) {
          this.centerDialogVisible = false;
          this.noticeContent = ''
          this.$message.success("生成成功，请导入生成代码！");
          this.$emit("ok");
        }
      });
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    /** 预览按钮 */
    handlePreview(row) {
      genApi.previewTable(row.tableId).then(response => {
        this.preview.data = response.data;
        this.preview.open = true;
        this.preview.activeName = "domain.java";
      });
    },
    /** 高亮显示 */
    highlightedCode(code, key) {
      const vmName = key.substring(key.lastIndexOf("/") + 1, key.indexOf(".vm"));
      var language = vmName.substring(vmName.indexOf(".") + 1, vmName.length);
      const result = hljs.highlight(language, code || "", true);
      return result.value || '&nbsp;';
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.tableId);
      this.tableNames = selection.map(item => item.tableName);
      this.single = selection.length != 1;
      this.multiple = !selection.length;
    },
    /** 修改按钮操作 */
    handleEditTable(row) {
      const tableId = row.tableId || this.ids[0];

      // 传递当前分页参数，以便返回时恢复分页状态
      this.$router.push({
        path: '/gen-edit',
        query: {
          tableId: tableId,
          pageNum: this.queryParams.pageNum,
          pageSize: this.queryParams.pageSize
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const tableIds = row.tableId || this.ids;

      this.$confirm('是否确认删除表编号为"' + tableIds + '"的数据项？').then(function() {
        return genApi.delTable(tableIds);
      }).then(() => {
        this.getList();
        this.$message.success("删除成功");
      }).catch(() => {});
    },
    /** 显示工具提示 */
    showTooltip(title, content) {
      this.tooltipContent = `${title}: ${content}`;
      this.tooltipVisible = true;
    },
    /** 隐藏工具提示 */
    hideTooltip() {
      this.tooltipVisible = false;
      this.tooltipContent = '';
    },
    /** 数据源切换 */
    handleDataSourceChange(dataSourceId) {
      this.selectedDataSourceId = dataSourceId;
      localStorage.setItem('selectedDataSourceId', dataSourceId); // 保存到localStorage
      this.queryParams.pageNum = 1;
      this.getList();

      if (dataSourceId) {
        const dataSource = this.dataSourceList.find(ds => ds.id === dataSourceId);
        this.$message.success(`已切换到数据源: ${dataSource.name}`);
      } else {
        this.$message.success('已切换到默认数据源');
      }
    },
    /** 获取数据库类型名称 */
    getDbTypeName(type) {
      const names = {
        '0': 'MySQL',
        '1': 'Oracle',
        '2': 'SQL Server',
        '3': 'PostgreSQL',
        '4': 'ClickHouse',
        '5': 'SQLite',
        '6': 'DB2',
        '7': '达梦'
      };
      return names[type] || 'Unknown';
    },
    /** 获取数据库类型颜色 */
    getDbTypeColor(type) {
      const colors = {
        '0': 'primary',    // MySQL - 蓝色
        '1': 'warning',    // Oracle - 橙色
        '2': 'success',    // SQL Server - 绿色
        '3': 'info',       // PostgreSQL - 灰色
        '4': 'danger',     // ClickHouse - 红色
        '5': '',           // SQLite - 默认
        '6': 'warning',    // DB2 - 橙色
        '7': 'success'     // 达梦 - 绿色
      };
      return colors[type] || '';
    }
  }
};
</script>


<style lang="scss" scoped>
.gen-page {
  padding: 20px;
  background: #ffffff;
  min-height: 100vh;
}

.gen-workspace {
  max-width: 1400px;
  margin: 0 auto;
  background: white;
  border-radius: 16px;
  box-shadow: 0 4px 20px rgba(0, 0, 0, 0.08);
  overflow: hidden;
}

.page-header {
  padding: 20px 32px 16px;
  background: #ffffff;
  color: #1f2937;
  position: relative;
  border-bottom: 1px solid #f3f4f6;

  .header-content {
    position: relative;
    z-index: 1;
    margin-bottom: 16px;

    .page-title {
      font-size: 24px;
      font-weight: 700;
      margin: 0 0 4px 0;
      display: flex;
      align-items: center;
      gap: 8px;
      color: #3b82f6;

      .el-icon {
        font-size: 26px;
        color: #3b82f6;
      }
    }

    .page-description {
      margin: 0;
      font-size: 14px;
      color: #6b7280;
      font-weight: 400;
    }
  }

  .stats-cards {
    display: flex;
    gap: 12px;
    position: relative;
    z-index: 1;
  }

  .stat-card {
    display: flex;
    align-items: center;
    gap: 10px;
    background: #ffffff;
    border: 1px solid #e5e7eb;
    border-radius: 8px;
    padding: 12px 16px;
    flex: 1;
    transition: all 0.3s ease;
    box-shadow: 0 1px 3px rgba(0, 0, 0, 0.1);

    &:hover {
      background: #f9fafb;
      transform: translateY(-2px);
      box-shadow: 0 4px 12px rgba(0, 0, 0, 0.15);
    }

    .stat-icon {
      width: 40px;
      height: 40px;
      border-radius: 10px;
      display: flex;
      align-items: center;
      justify-content: center;
      background: #f3f4f6;
      
      .el-icon {
        font-size: 20px;
        color: #3b82f6;
      }
      
      &.selected {
        background: #dbeafe;
        
        .el-icon {
          color: #3b82f6;
        }
      }
      
      &.generated {
        background: #fef3c7;
        
        .el-icon {
          color: #f59e0b;
        }
      }
    }

    .stat-content {
      flex: 1;

      .stat-label {
        font-size: 13px;
        color: #6b7280;
        margin-bottom: 2px;
        font-weight: 500;
      }

      .stat-value {
        font-size: 20px;
        font-weight: 700;
        line-height: 1;
        color: #1f2937;
      }
    }
  }
}

.smart-toolbar {
  padding: 16px 32px;
  border-bottom: 1px solid #f1f5f9;
  display: flex;
  align-items: center;
  justify-content: space-between;
  background: #fafbfc;
  min-height: 60px;

  .toolbar-left {
    display: flex;
    gap: 8px;
    align-items: center;
    flex-shrink: 0;
  }

  .toolbar-right {
    display: flex;
    gap: 8px;
    align-items: center;
    flex-wrap: nowrap;
    flex-shrink: 0;

    .selection-info {
      .el-tag {
        padding: 6px 12px;
        border-radius: 16px;
        font-weight: 500;
        font-size: 13px;
        
        .el-icon {
          margin-right: 4px;
        }
      }
    }

    .datasource-selector {
      min-width: 160px;
      max-width: 200px;
      
      :deep(.el-select) {
        width: 100%;
        
        .el-input__wrapper {
          border-radius: 6px;
          border: 1px solid #d1d5db;
          transition: all 0.2s ease;
          
          &:hover {
            border-color: #9ca3af;
          }
          
          &.is-focus {
            border-color: #3b82f6;
            box-shadow: 0 0 0 2px rgba(59, 130, 246, 0.1);
          }
        }
      }
    }

    .search-input {
      width: 200px;
      
      :deep(.el-input__wrapper) {
        border-radius: 6px;
        border: 1px solid #d1d5db;
        transition: all 0.2s ease;
        
        &:hover {
          border-color: #9ca3af;
        }
        
        &.is-focus {
          border-color: #3b82f6;
          box-shadow: 0 0 0 2px rgba(59, 130, 246, 0.1);
        }
      }
    }

    .reset-btn {
      border-radius: 6px;
      border: 1px solid #d1d5db;
      color: #6b7280;
      
      &:hover {
        border-color: #9ca3af;
        color: #374151;
      }
    }

    .tool-buttons {
      display: flex;
      gap: 6px;
      
      .el-button {
        width: 36px;
        height: 36px;
        border-radius: 6px;
        border: 1px solid #d1d5db;
        padding: 0;
        
        &:hover {
          border-color: #9ca3af;
        }
        
        &.el-button--primary {
          background: #3b82f6;
          border-color: #3b82f6;
          
          &:hover {
            background: #2563eb;
            border-color: #2563eb;
          }
        }
      }
    }
  }
}

.datasource-option {
  .option-main {
    display: flex;
    align-items: center;
    justify-content: space-between;
    margin-bottom: 4px;
  }
  
  .option-name {
    font-weight: 500;
    color: #374151;
  }
  
  .option-tag {
    font-size: 11px;
    padding: 2px 6px;
    border-radius: 4px;
  }
  
  .option-detail {
    font-size: 12px;
    color: #6b7280;
  }
}

.filter-section {
  background: white;
  border-radius: 8px;
  padding: 16px 20px;
  margin-bottom: 16px;
  box-shadow: 0 1px 3px rgba(0, 0, 0, 0.1);
}

.table-section {
  background: white;
  border-radius: 8px;
  padding: 20px;
  box-shadow: 0 1px 3px rgba(0, 0, 0, 0.1);
  width: 100%;

  .pagination-wrapper {
    margin-top: 16px;
    display: flex;
    justify-content: center;
  }
}


/* 响应式设计 */
@media (max-width: 768px) {
  .gen-workspace {
    padding: 16px;
  }

  .toolbar {
    flex-direction: column;
    align-items: stretch;
    gap: 12px;

    .toolbar-left,
    .toolbar-center,
    .toolbar-right {
      justify-content: center;
    }
  }

  .stats-bar {
    justify-content: space-around;
  }

  :deep(.el-dialog) {
    width: 95% !important;
    margin: 0 auto;
  }
}

/* 对话框样式修复 */
:deep(.el-dialog) {
  background-color: white !important;
  border-radius: 8px;
  box-shadow: 0 10px 25px rgba(0, 0, 0, 0.15);
}

:deep(.el-dialog__header) {
  background-color: #f8fafc;
  border-bottom: 1px solid #e5e7eb;
  border-radius: 8px 8px 0 0;
  padding: 16px 20px;
}

:deep(.el-dialog__title) {
  font-size: 16px;
  font-weight: 600;
  color: #1f2937;
}

:deep(.el-dialog__body) {
  padding: 20px;
  background-color: white;
}

:deep(.el-dialog__footer) {
  background-color: #f8fafc;
  border-top: 1px solid #e5e7eb;
  border-radius: 0 0 8px 8px;
  padding: 16px 20px;
}

/* 按钮尺寸调整 */
:deep(.el-button) {
  padding: 8px 16px;
  font-size: 14px;
  border-radius: 6px;
}

:deep(.el-button--small) {
  padding: 6px 12px;
  font-size: 12px;
}

:deep(.el-button--large) {
  padding: 10px 20px;
  font-size: 16px;
}

/* 表格样式优化 */
:deep(.el-table) {
  border-radius: 8px;
  overflow: hidden;
  box-shadow: 0 1px 3px rgba(0, 0, 0, 0.1);
}

:deep(.el-table__header) {
  background-color: #f8fafc;
}

:deep(.el-table th) {
  background-color: #f8fafc !important;
  color: #374151;
  font-weight: 600;
  font-size: 14px;
  border-bottom: 1px solid #e5e7eb;
}

:deep(.el-table td) {
  border-bottom: 1px solid #f3f4f6;
  font-size: 14px;
}

:deep(.el-table__row:hover) {
  background-color: #f9fafb !important;
}

</style>

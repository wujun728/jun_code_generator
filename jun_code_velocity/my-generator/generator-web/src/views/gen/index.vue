<template>
  <div class="gen-page">
    <div class="gen-workspace">
      <!-- 页面头部 -->
      <div class="page-header">
        <div class="header-content">
          <h1 class="page-title">
            <i class="el-icon-magic-stick"></i>
            代码生成工作台
          </h1>
          <p class="page-description">基于数据表结构，智能生成高质量的前后端代码</p>
        </div>
        <div class="stats-cards">
          <div class="stat-card">
            <div class="stat-icon">
              <i class="el-icon-grid"></i>
            </div>
            <div class="stat-content">
              <div class="stat-label">数据表总数</div>
              <div class="stat-value">{{ total }}</div>
            </div>
          </div>
          <div class="stat-card">
            <div class="stat-icon selected">
              <i class="el-icon-check"></i>
            </div>
            <div class="stat-content">
              <div class="stat-label">已选择表</div>
              <div class="stat-value">{{ ids.length }}</div>
            </div>
          </div>
          <div class="stat-card">
            <div class="stat-icon generated">
              <i class="el-icon-magic-stick"></i>
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
        <div class="toolbar-left">
          <template v-if="ids.length === 0">
            <el-button type="primary" size="small" icon="el-icon-upload2" @click="openImportTable">导入数据表</el-button>
            <el-button type="primary" size="small" plain icon="el-icon-document-add" @click="createTable">SQL脚本生成</el-button>
            <el-button type="success" size="small" plain icon="el-icon-download" @click="handleExport">导出数据字典</el-button>
          </template>
          <template v-else>
            <el-button type="primary" size="small" @click="handleGenTable" :loading="generating">
              <i class="el-icon-magic-stick"></i>
              <span v-if="generating">正在生成代码...</span>
              <span v-else>生成 {{ ids.length }} 个表的代码</span>
            </el-button>
            <el-button type="primary" size="small" plain @click="handleCompleteProject" :loading="generatingProject">
              <i class="el-icon-document-copy"></i>
              生成完整项目
            </el-button>
          </template>
        </div>

        <div class="toolbar-right">
          <div class="selection-info" v-if="ids.length > 0">
            <el-tag type="primary" size="small">
              <i class="el-icon-check"></i>
              已选择 {{ ids.length }} 个数据表
            </el-tag>
          </div>

          <div class="datasource-selector">
            <el-select v-model="selectedDataSourceId" placeholder="选择数据源" @change="handleDataSourceChange" clearable size="small">
              <el-option v-for="ds in dataSourceList" :key="ds.id" :label="ds.name" :value="ds.id">
                <div class="datasource-option">
                  <span class="option-name">{{ ds.name }}</span>
                  <el-tag :type="getDbTypeColor(ds.type)" size="mini" class="option-tag">{{ getDbTypeName(ds.type) }}</el-tag>
                </div>
              </el-option>
            </el-select>
          </div>

          <el-input v-model="quickSearch" placeholder="搜索表名..." clearable @input="handleQuickSearch" class="search-input" size="small">
            <i slot="prefix" class="el-icon-search"></i>
          </el-input>

          <el-button v-if="quickSearch || showSearch" @click="resetQuery" size="small">重置</el-button>

          <el-tooltip content="高级筛选" placement="top">
            <el-button :type="showSearch ? 'primary' : ''" @click="showSearch = !showSearch" size="small" icon="el-icon-s-operation"></el-button>
          </el-tooltip>
          <el-tooltip content="刷新数据" placement="top">
            <el-button @click="getList" size="small" icon="el-icon-refresh"></el-button>
          </el-tooltip>
        </div>
      </div>

      <!-- 筛选器 -->
      <div class="filter-section" v-if="showSearch">
        <el-form :model="queryParams" ref="queryForm" size="small" :inline="true">
          <el-form-item label="表名称" prop="tableName">
            <el-input v-model="queryParams.tableName" placeholder="请输入表名称" clearable @keyup.enter.native="handleQuery" style="width: 200px" />
          </el-form-item>
          <el-form-item label="创建时间">
            <el-date-picker v-model="dateRange" style="width: 240px" value-format="yyyy-MM-dd" type="daterange"
              range-separator="至" start-placeholder="开始日期" end-placeholder="结束日期" />
          </el-form-item>
          <el-form-item>
            <el-button type="primary" icon="el-icon-search" @click="handleQuery">搜索</el-button>
            <el-button icon="el-icon-refresh" @click="resetQuery">重置</el-button>
          </el-form-item>
        </el-form>
      </div>

      <!-- 数据表格 -->
      <div class="table-section">
        <el-table :data="tableList" v-loading="loading" @selection-change="handleSelectionChange" stripe style="width: 100%">
          <el-table-column type="selection" width="48" align="center" />
          <el-table-column label="序号" width="60" align="center">
            <template slot-scope="scope">
              {{ (queryParams.pageNum - 1) * queryParams.pageSize + scope.$index + 1 }}
            </template>
          </el-table-column>
          <el-table-column label="表名" prop="tableName" width="200" align="left" :show-overflow-tooltip="true">
            <template slot-scope="scope">
              <div>
                <div style="font-weight: 500; margin-bottom: 4px;">{{ scope.row.tableName || '-' }}</div>
                <div style="font-size: 12px; color: #666;" v-if="scope.row.tableComment">{{ scope.row.tableComment }}</div>
              </div>
            </template>
          </el-table-column>
          <el-table-column label="类名" prop="className" width="150" align="left" :show-overflow-tooltip="true" />
          <el-table-column label="前端" width="120" align="left">
            <template slot-scope="scope">
              <el-tag :type="getTechStackColor('frontend', scope.row.frontEnd)" size="small">{{ getFrontEndName(scope.row.frontEnd) }}</el-tag>
            </template>
          </el-table-column>
          <el-table-column label="后端" width="120" align="left">
            <template slot-scope="scope">
              <el-tag :type="getTechStackColor('backend', scope.row.backEnd)" size="small">{{ getBackEndName(scope.row.backEnd) }}</el-tag>
            </template>
          </el-table-column>
          <el-table-column label="是否若依" width="100" align="left">
            <template slot-scope="scope">
              <el-tag :type="scope.row.isRuoYi === '1' ? 'success' : 'info'" size="small">{{ scope.row.isRuoYi === '1' ? '是' : '否' }}</el-tag>
            </template>
          </el-table-column>
          <el-table-column label="更新时间" prop="updateTime" align="left" width="160" />
          <el-table-column label="操作" align="center" fixed="right" width="320">
            <template slot-scope="scope">
              <div class="table-actions">
                <el-button size="small" type="primary" @click="handleGenTable(scope.row)">
                  <i class="el-icon-magic-stick"></i> 生成
                </el-button>
                <el-button size="small" @click="handlePreview(scope.row)">
                  <i class="el-icon-view"></i> 预览
                </el-button>
                <el-button size="small" @click="handleEditTable(scope.row)">
                  <i class="el-icon-setting"></i> 配置
                </el-button>
                <el-dropdown style="margin-left: 8px;">
                  <el-button size="small" circle>
                    <i class="el-icon-more"></i>
                  </el-button>
                  <el-dropdown-menu slot="dropdown">
                    <el-dropdown-item @click.native="handleSynchDb(scope.row)">
                      <i class="el-icon-refresh"></i> 同步表结构
                    </el-dropdown-item>
                    <el-dropdown-item @click.native="handleDelete(scope.row)" divided>
                      <i class="el-icon-delete"></i> 删除
                    </el-dropdown-item>
                  </el-dropdown-menu>
                </el-dropdown>
              </div>
            </template>
          </el-table-column>
        </el-table>

        <div class="pagination-wrapper" v-if="total > queryParams.pageSize">
          <pagination
            :total="total"
            :page.sync="queryParams.pageNum"
            :limit.sync="queryParams.pageSize"
            @pagination="getList"
          />
        </div>
      </div>
    </div>

    <!-- 预览界面 -->
    <el-dialog :title="preview.title" :visible.sync="preview.open" width="80%" top="5vh" append-to-body class="scrollbar">
      <el-tabs v-model="preview.activeName">
        <el-tab-pane
          v-for="(value, key) in preview.data"
          :label="key.substring(key.lastIndexOf('/') + 1, key.indexOf('.vm'))"
          :name="key.substring(key.lastIndexOf('/') + 1, key.indexOf('.vm'))"
          :key="key"
        >
          <el-link :underline="false" icon="el-icon-document-copy" v-clipboard:copy="value"
            v-clipboard:success="clipboardSuccess" style="float:right">复制</el-link>
          <pre><code class="hljs" v-html="highlightedCode(value, key)"></code></pre>
        </el-tab-pane>
      </el-tabs>
    </el-dialog>

    <import-table ref="import" @ok="handleQuery" />

    <!-- 执行脚本对话框 -->
    <el-dialog title="执行脚本" :visible.sync="centerDialogVisible" width="60%" append-to-body top="5vh">
      <el-input type="textarea" v-model="noticeContent" placeholder="请输入创建数据表脚本" :rows="10" />
      <div slot="footer" class="dialog-footer">
        <el-button @click="centerDialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="createTableSubmit">确 定</el-button>
      </div>
    </el-dialog>

    <!-- 项目配置对话框 -->
    <el-dialog title="完整项目配置" :visible.sync="projectConfigVisible" width="70%" append-to-body top="5vh">
      <el-form ref="projectForm" :model="projectForm" :rules="projectRules" label-width="120px" class="project-form">
        <div class="form-section">
          <h4 class="section-title">
            <i class="el-icon-setting"></i> 基础信息
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
            <i class="el-icon-connection"></i> 数据库配置
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
            <i class="el-icon-monitor"></i> 服务配置
          </h4>
          <el-row :gutter="20">
            <el-col :span="12">
              <el-form-item label="服务端口" prop="serverPort">
                <el-input-number v-model="projectForm.serverPort" :min="1000" :max="65535" placeholder="8080" />
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="作者" prop="author">
                <el-input v-model="projectForm.author" placeholder="Wujun" />
              </el-form-item>
            </el-col>
          </el-row>
        </div>
      </el-form>

      <div slot="footer" class="dialog-footer">
        <el-button @click="projectConfigVisible = false">取 消</el-button>
        <el-button type="primary" @click="confirmGenerateProject" :loading="generatingProject">
          {{ generatingProject ? '正在生成...' : '生成完整项目' }}
        </el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listTable, previewTable, delTable, genCode, synchDb, dictTypeList } from "@/api/gen";
import { listDatasource } from "@/api/dataSource";
import importTable from "./importTable";
import download from "@/utils/download";
import hljs from "highlight.js/lib/highlight";
import "highlight.js/styles/github-gist.css";
hljs.registerLanguage("java", require("highlight.js/lib/languages/java"));
hljs.registerLanguage("xml", require("highlight.js/lib/languages/xml"));
hljs.registerLanguage("html", require("highlight.js/lib/languages/xml"));
hljs.registerLanguage("vue", require("highlight.js/lib/languages/xml"));
hljs.registerLanguage("javascript", require("highlight.js/lib/languages/javascript"));
hljs.registerLanguage("sql", require("highlight.js/lib/languages/sql"));

const BACKEND_OPTIONS = [
  { value: '0', label: 'MyBatis' },
  { value: '1', label: 'MyBatis-Plus' }
];
const FRONTEND_OPTIONS = [
  { value: '0', label: 'Element UI (Vue2)' },
  { value: '1', label: 'Ant Design Vue' },
  { value: '2', label: 'HTML' },
  { value: '3', label: 'Element UI (Vue3)' }
];

export default {
  name: "Gen",
  components: { importTable },
  data() {
    return {
      loading: true,
      uniqueId: "",
      ids: [],
      tableNames: [],
      noticeContent: '',
      single: true,
      multiple: true,
      showSearch: false,
      quickSearch: '',
      generating: false,
      generatingProject: false,
      projectConfigVisible: false,
      projectForm: {
        projectName: 'demo-project',
        projectComment: '演示项目',
        packageName: 'com.example.demo',
        className: 'Demo',
        groupId: 'com.example',
        artifactId: 'demo',
        version: '1.0.0',
        author: 'Wujun',
        driverClassName: 'com.mysql.cj.jdbc.Driver',
        datasourceUrl: 'jdbc:mysql://localhost:3306/demo',
        datasourceUsername: 'root',
        datasourcePassword: '123456',
        serverPort: 8080,
        jwtSecret: 'jun-secret-key'
      },
      projectRules: {
        projectName: [{ required: true, message: '请输入项目名称', trigger: 'blur' }],
        projectComment: [{ required: true, message: '请输入项目描述', trigger: 'blur' }],
        packageName: [
          { required: true, message: '请输入包名', trigger: 'blur' },
          { pattern: /^[a-z][a-z0-9]*(\.[a-z][a-z0-9]*)*$/, message: '包名格式不正确', trigger: 'blur' }
        ],
        className: [
          { required: true, message: '请输入类名前缀', trigger: 'blur' },
          { pattern: /^[A-Z][a-zA-Z0-9]*$/, message: '类名必须以大写字母开头', trigger: 'blur' }
        ],
        groupId: [{ required: true, message: '请输入Group ID', trigger: 'blur' }],
        artifactId: [
          { required: true, message: '请输入Artifact ID', trigger: 'blur' },
          { pattern: /^[a-z][a-z0-9-]*$/, message: 'Artifact ID格式不正确', trigger: 'blur' }
        ],
        datasourceUrl: [{ required: true, message: '请输入数据库URL', trigger: 'blur' }],
        datasourceUsername: [{ required: true, message: '请输入数据库用户名', trigger: 'blur' }],
        datasourcePassword: [{ required: true, message: '请输入数据库密码', trigger: 'blur' }],
        serverPort: [
          { required: true, message: '请输入服务端口', trigger: 'blur' },
          { type: 'number', min: 1000, max: 65535, message: '端口范围1000-65535', trigger: 'blur' }
        ]
      },
      generatedCount: 0,
      selectedDataSourceId: null,
      dataSourceList: [],
      total: 0,
      tableList: [],
      dateRange: "",
      queryParams: {
        pageNum: 1,
        pageSize: 50,
        tableName: undefined,
        tableComment: undefined
      },
      preview: {
        open: false,
        title: "代码预览",
        data: {},
        activeName: "domain.java"
      },
      centerDialogVisible: false,
      debounceTimer: null
    };
  },
  created() {
    this.getList();
    this.getDataSourceList();
  },
  activated() {
    const time = this.$route.query.t;
    if (time != null && time != this.uniqueId) {
      this.uniqueId = time;
      const pageNum = Number(this.$route.query.pageNum) || 1;
      const pageSize = Number(this.$route.query.pageSize) || 50;
      this.queryParams.pageNum = pageNum;
      this.queryParams.pageSize = pageSize;
      this.getList();
    }
  },
  beforeDestroy() {
    if (this.debounceTimer) {
      clearTimeout(this.debounceTimer);
    }
  },
  methods: {
    getList() {
      this.loading = true;
      if (this.selectedDataSourceId) {
        this.queryParams.selectId = this.selectedDataSourceId;
      }
      listTable(this.queryParams).then(response => {
        this.tableList = response.rows || [];
        this.total = response.total || 0;
        this.loading = false;
      }).catch(() => {
        this.loading = false;
      });
    },
    getDataSourceList() {
      listDatasource({}).then(response => {
        this.dataSourceList = response.rows || [];
      }).catch(() => {});
    },
    getFrontEndName(frontEnd) {
      const option = FRONTEND_OPTIONS.find(o => o.value === frontEnd);
      return option ? option.label : '未知';
    },
    getBackEndName(backEnd) {
      const option = BACKEND_OPTIONS.find(o => o.value === backEnd);
      return option ? option.label : '未知';
    },
    getTechStackColor(type, value) {
      if (type === 'frontend') {
        const colors = { '0': '', '1': 'warning', '2': 'info', '3': 'success' };
        return colors[value] || '';
      } else {
        const colors = { '0': 'primary', '1': 'success' };
        return colors[value] || '';
      }
    },
    getDbTypeName(type) {
      const names = { '0': 'MySQL', '1': 'Oracle', '2': 'SQL Server', '3': 'PostgreSQL', '4': 'ClickHouse', '5': 'SQLite', '6': 'DB2', '7': '达梦' };
      return names[type] || 'Unknown';
    },
    getDbTypeColor(type) {
      const colors = { '0': 'primary', '1': 'warning', '2': 'success', '3': 'info', '4': 'danger', '5': '', '6': 'warning', '7': 'success' };
      return colors[type] || '';
    },
    handleQuery() {
      if (this.debounceTimer) clearTimeout(this.debounceTimer);
      this.debounceTimer = setTimeout(() => {
        this.queryParams.pageNum = 1;
        this.getList();
      }, 300);
    },
    handleQuickSearch() {
      if (this.debounceTimer) clearTimeout(this.debounceTimer);
      this.debounceTimer = setTimeout(() => {
        this.queryParams.tableName = this.quickSearch;
        this.queryParams.pageNum = 1;
        this.getList();
      }, 300);
    },
    handleDataSourceChange(dataSourceId) {
      this.selectedDataSourceId = dataSourceId;
      localStorage.setItem('selectedDataSourceId', dataSourceId);
      this.queryParams.pageNum = 1;
      this.getList();
      if (dataSourceId) {
        const ds = this.dataSourceList.find(d => d.id === dataSourceId);
        if (ds) this.$modal.msgSuccess('已切换到数据源: ' + ds.name);
      } else {
        this.$modal.msgSuccess('已切换到默认数据源');
      }
    },
    handleGenTable(row) {
      const tableNames = row.tableName || this.tableNames;
      if (tableNames == "") {
        this.$modal.msgError("请选择要生成的数据");
        return;
      }
      this.generating = true;
      if (row.genType === "1") {
        genCode(row.tableName).then(response => {
          this.generating = false;
          this.$modal.msgSuccess("成功生成到自定义路径：" + row.genPath);
        }).catch(() => { this.generating = false; });
      } else {
        setTimeout(() => {
          this.generating = false;
          download.zip("/gen/batchGenCode?tables=" + tableNames, "ruoyi.zip");
        }, 1500);
      }
    },
    handleCompleteProject() {
      if (this.tableNames.length === 0) {
        this.$modal.msgError("请选择要生成的数据表");
        return;
      }
      this.projectConfigVisible = true;
    },
    confirmGenerateProject() {
      this.$refs.projectForm.validate((valid) => {
        if (valid) {
          this.generatingProject = true;
          this.projectConfigVisible = false;
          const params = {
            tableNames: this.tableNames,
            projectConfig: this.projectForm
          };
          this.$http.post('/gen/generateCompleteProject', params, {
            responseType: 'blob'
          }).then(response => {
            this.generatingProject = false;
            const blob = new Blob([response.data], { type: 'application/zip' });
            const url = window.URL.createObjectURL(blob);
            const link = document.createElement('a');
            link.href = url;
            link.download = this.projectForm.projectName + '-complete.zip';
            document.body.appendChild(link);
            link.click();
            document.body.removeChild(link);
            window.URL.revokeObjectURL(url);
            this.$modal.msgSuccess("完整项目生成成功！");
          }).catch(() => {
            this.generatingProject = false;
            this.$modal.msgError("生成完整项目失败，请重试");
          });
        }
      });
    },
    handleSynchDb(row) {
      const tableName = row.tableName;
      this.$modal.confirm('确认要强制同步"' + tableName + '"表结构吗？').then(function () {
        return synchDb(tableName);
      }).then(() => {
        this.$modal.msgSuccess("同步成功");
      }).catch(() => {});
    },
    openImportTable() {
      this.$refs.import.show();
    },
    handleExport() {
      const tableIds = this.ids;
      if (tableIds.length == 0) {
        this.$modal.msgError("请选择要导出的数据");
        return;
      }
      download.download('/gen/export?tableIds=' + tableIds, '数据字典.xlsx');
    },
    createTable() {
      this.centerDialogVisible = true;
    },
    createTableSubmit() {
      const sql = this.noticeContent;
      if (sql == "") {
        this.$modal.msgError("请输入要执行创建表的sql");
        return;
      }
      this.$http.post('/gen/create', { sql: sql }).then(res => {
        if (res.code === 200) {
          this.centerDialogVisible = false;
          this.noticeContent = '';
          this.$modal.msgSuccess("生成成功，请导入生成代码！");
        }
      });
    },
    resetQuery() {
      this.dateRange = [];
      this.quickSearch = '';
      this.resetForm("queryForm");
      this.handleQuery();
    },
    handlePreview(row) {
      previewTable(row.tableId).then(response => {
        this.preview.data = response.data;
        this.preview.open = true;
        this.preview.activeName = "domain.java";
      });
    },
    highlightedCode(code, key) {
      const vmName = key.substring(key.lastIndexOf("/") + 1, key.indexOf(".vm"));
      var language = vmName.substring(vmName.indexOf(".") + 1, vmName.length);
      const result = hljs.highlight(language, code || "", true);
      return result.value || '&nbsp;';
    },
    clipboardSuccess() {
      this.$modal.msgSuccess("复制成功");
    },
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.tableId);
      this.tableNames = selection.map(item => item.tableName);
      this.single = selection.length != 1;
      this.multiple = !selection.length;
    },
    handleEditTable(row) {
      const tableId = row.tableId || this.ids[0];
      const tableName = row.tableName || this.tableNames[0];
      this.$router.push({
        path: '/gen-edit',
        query: {
          tableId: tableId,
          pageNum: this.queryParams.pageNum,
          pageSize: this.queryParams.pageSize
        }
      });
    },
    handleDelete(row) {
      const tableIds = row.tableId || this.ids;
      this.$modal.confirm('是否确认删除表编号为"' + tableIds + '"的数据项？').then(function () {
        return delTable(tableIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    }
  }
};
</script>

<style scoped>
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
  border-bottom: 1px solid #f3f4f6;
}
.page-header .header-content { margin-bottom: 16px; }
.page-title {
  font-size: 24px;
  font-weight: 700;
  margin: 0 0 4px 0;
  display: flex;
  align-items: center;
  gap: 8px;
  color: #3b82f6;
}
.page-title i { font-size: 26px; color: #3b82f6; }
.page-description { margin: 0; font-size: 14px; color: #6b7280; }
.stats-cards { display: flex; gap: 12px; }
.stat-card {
  display: flex; align-items: center; gap: 10px;
  background: #ffffff; border: 1px solid #e5e7eb; border-radius: 8px;
  padding: 12px 16px; flex: 1; transition: all 0.3s ease;
  box-shadow: 0 1px 3px rgba(0, 0, 0, 0.1);
}
.stat-card:hover { background: #f9fafb; transform: translateY(-2px); box-shadow: 0 4px 12px rgba(0, 0, 0, 0.15); }
.stat-icon { width: 40px; height: 40px; border-radius: 10px; display: flex; align-items: center; justify-content: center; background: #f3f4f6; }
.stat-icon i { font-size: 20px; color: #3b82f6; }
.stat-icon.selected { background: #dbeafe; }
.stat-icon.selected i { color: #3b82f6; }
.stat-icon.generated { background: #fef3c7; }
.stat-icon.generated i { color: #f59e0b; }
.stat-content { flex: 1; }
.stat-label { font-size: 13px; color: #6b7280; margin-bottom: 2px; font-weight: 500; }
.stat-value { font-size: 20px; font-weight: 700; line-height: 1; color: #1f2937; }

.smart-toolbar {
  padding: 16px 32px; border-bottom: 1px solid #f1f5f9;
  display: flex; align-items: center; justify-content: space-between;
  background: #fafbfc; min-height: 60px;
}
.toolbar-left { display: flex; gap: 8px; align-items: center; flex-shrink: 0; }
.toolbar-right { display: flex; gap: 8px; align-items: center; flex-wrap: nowrap; flex-shrink: 0; }
.selection-info .el-tag { padding: 6px 12px; border-radius: 16px; font-weight: 500; font-size: 13px; }
.datasource-selector { min-width: 160px; max-width: 200px; }
.search-input { width: 200px; }

.filter-section { background: white; border-radius: 8px; padding: 16px 20px; margin: 0 16px 16px; box-shadow: 0 1px 3px rgba(0, 0, 0, 0.1); }
.table-section { background: white; border-radius: 8px; padding: 20px; box-shadow: 0 1px 3px rgba(0, 0, 0, 0.1); width: 100%; }
.pagination-wrapper { margin-top: 16px; display: flex; justify-content: center; }

.datasource-option { display: flex; align-items: center; justify-content: space-between; }
.option-name { font-weight: 500; color: #374151; }
.option-tag { font-size: 11px; }

.form-section { margin-bottom: 20px; padding-bottom: 16px; border-bottom: 1px solid #f0f0f0; }
.form-section:last-child { border-bottom: none; }
.section-title { display: flex; align-items: center; gap: 8px; margin: 0 0 16px 0; font-size: 16px; font-weight: 600; color: #1f2937; }
.section-title i { font-size: 18px; color: #3b82f6; }

.table-actions { display: flex; align-items: center; gap: 4px; }
</style>
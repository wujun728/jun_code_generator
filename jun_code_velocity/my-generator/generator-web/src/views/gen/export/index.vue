<template>
  <div class="export-page">
    <div class="main-container">
      <!-- 页面标题区 -->
      <div class="page-header">
        <div class="header-content">
          <h1 class="page-title">
            <i class="el-icon-document"></i>
            数据库文档导出
          </h1>
          <p class="page-description">选择数据源，预览表结构，导出数据库设计文档</p>
        </div>
      </div>

      <!-- 统计卡片区 -->
      <div class="stats-section">
        <div class="stat-card">
          <div class="stat-icon datasource-icon">
            <i class="el-icon-connection"></i>
          </div>
          <div class="stat-content">
            <div class="stat-title">数据源总数</div>
            <div class="stat-value">{{ dataSourceList.length }}</div>
          </div>
        </div>
        <div class="stat-card">
          <div class="stat-icon preview-icon">
            <i class="el-icon-view"></i>
          </div>
          <div class="stat-content">
            <div class="stat-title">预览表数</div>
            <div class="stat-value">{{ previewTableList.length }}</div>
          </div>
        </div>
        <div class="stat-card">
          <div class="stat-icon export-icon">
            <i class="el-icon-download"></i>
          </div>
          <div class="stat-content">
            <div class="stat-title">已导出</div>
            <div class="stat-value">{{ exportCount || 0 }}</div>
          </div>
        </div>
      </div>

      <!-- 智能工具栏 -->
      <div class="smart-toolbar">
        <div class="toolbar-left">
          <template v-if="!selectedDataSource">
            <el-button type="primary" size="small" icon="el-icon-refresh" @click="loadDataSources">刷新数据源</el-button>
          </template>
          <template v-else>
            <div class="selection-info">
              <el-tag type="primary" size="small">
                <i class="el-icon-connection"></i>
                当前数据源：{{ selectedDataSource.name }}
              </el-tag>
            </div>
            <el-button type="info" size="small" icon="el-icon-view" @click="handlePreviewSelected">预览表结构</el-button>
            <el-button type="success" size="small" icon="el-icon-download" @click="exportAllTables">导出所有表</el-button>
          </template>
        </div>

        <div class="toolbar-right">
          <el-select v-model="queryParams.type" placeholder="数据库类型" clearable size="small" style="width: 140px; margin-right: 8px;" @change="handleQuery">
            <el-option label="MySQL" value="0" />
            <el-option label="Oracle" value="1" />
            <el-option label="SQL Server" value="2" />
            <el-option label="PostgreSQL" value="3" />
            <el-option label="ClickHouse" value="4" />
            <el-option label="SQLite" value="5" />
            <el-option label="DB2" value="6" />
            <el-option label="达梦" value="7" />
          </el-select>

          <el-input v-model="queryParams.name" placeholder="搜索数据源名称..." size="small" style="width: 240px; margin-right: 8px;" clearable @keyup.enter.native="handleQuery" @clear="handleQuery">
            <i slot="prefix" class="el-icon-search"></i>
          </el-input>

          <el-button v-if="hasActiveFilters" size="small" @click="resetQuery" icon="el-icon-refresh">重置</el-button>
        </div>
      </div>

      <!-- 数据表格 -->
      <div class="table-section">
        <el-table v-loading="loading" :data="dataSourceList" @current-change="handleCurrentChange" highlight-current-row>
          <el-table-column label="序号" width="60" align="center">
            <template slot-scope="scope">{{ scope.$index + 1 }}</template>
          </el-table-column>

          <el-table-column label="数据源信息" min-width="280">
            <template slot-scope="scope">
              <div class="datasource-info">
                <div class="datasource-name">
                  <span class="name-text">{{ scope.row.name }}</span>
                  <el-tag :type="scope.row.status === 1 ? 'success' : 'info'" size="mini">{{ scope.row.status === 1 ? '默认' : '正常' }}</el-tag>
                </div>
                <div class="datasource-details">
                  <div class="detail-item">
                    <i class="el-icon-link"></i>
                    <span>{{ scope.row.url }}:{{ scope.row.port }}</span>
                  </div>
                  <div class="detail-item">
                    <i class="el-icon-coin"></i>
                    <span>{{ scope.row.dbName }}</span>
                  </div>
                </div>
              </div>
            </template>
          </el-table-column>

          <el-table-column label="数据库类型" width="140" align="center">
            <template slot-scope="scope">
              <el-tag :type="getDbTypeColor(scope.row.type)" size="small">{{ getDbTypeName(scope.row.type) }}</el-tag>
            </template>
          </el-table-column>

          <el-table-column label="认证信息" width="160" align="center">
            <template slot-scope="scope">
              <div class="auth-info">
                <div class="username">
                  <i class="el-icon-user"></i>
                  <span>{{ scope.row.username }}</span>
                </div>
                <div class="password">
                  <i class="el-icon-lock"></i>
                  <span>******</span>
                </div>
              </div>
            </template>
          </el-table-column>

          <el-table-column label="操作" width="180" align="center" fixed="right">
            <template slot-scope="scope">
              <el-tooltip content="测试连接" placement="top">
                <el-button type="primary" size="mini" circle icon="el-icon-link" @click="testConnection(scope.row)"></el-button>
              </el-tooltip>
              <el-tooltip content="预览表结构" placement="top">
                <el-button type="success" size="mini" circle icon="el-icon-view" @click="previewTables(scope.row)"></el-button>
              </el-tooltip>
              <el-dropdown trigger="click" @command="(cmd) => handleMoreAction(cmd, scope.row)" style="margin-left: 4px;">
                <el-button type="info" size="mini" circle icon="el-icon-more"></el-button>
                <el-dropdown-menu slot="dropdown">
                  <el-dropdown-item command="export">
                    <i class="el-icon-download"></i> 导出文档
                  </el-dropdown-item>
                </el-dropdown-menu>
              </el-dropdown>
            </template>
          </el-table-column>
        </el-table>
      </div>
    </div>

    <!-- 表预览对话框 -->
    <el-dialog title="数据库表预览" :visible.sync="previewDialogVisible" width="1200px" append-to-body :close-on-click-modal="false">
      <div class="preview-info-section">
        <div class="preview-tags">
          <el-tag type="primary" size="small">
            <i class="el-icon-connection"></i>
            {{ currentDataSource.name }}
          </el-tag>
          <el-tag type="info" size="small">共 {{ previewTableList.length }} 张表</el-tag>
          <el-tag type="success" size="small" v-if="selectedTables.length > 0">已选择 {{ selectedTables.length }} 张表</el-tag>
        </div>
      </div>

      <div class="preview-actions-section">
        <el-button type="success" size="small" icon="el-icon-check" @click="selectAllTables">全选</el-button>
        <el-button type="warning" size="small" icon="el-icon-close" @click="clearTableSelection">清空</el-button>
        <el-button type="primary" size="small" icon="el-icon-download" @click="showExportDialog" :disabled="selectedTables.length === 0">导出选中</el-button>
      </div>

      <div class="preview-table-section">
        <el-table ref="previewTableRef" :data="previewTableList" v-loading="previewLoading" @selection-change="handleTableSelectionChange" max-height="400">
          <el-table-column type="selection" width="55" align="center" />
          <el-table-column prop="tableName" label="表名" min-width="200" />
          <el-table-column prop="tableComment" label="表注释" min-width="250">
            <template slot-scope="scope">
              <span>{{ scope.row.tableComment || '暂无注释' }}</span>
            </template>
          </el-table-column>
          <el-table-column prop="createTime" label="创建时间" width="180">
            <template slot-scope="scope">
              <span>{{ formatDate(scope.row.createTime) }}</span>
            </template>
          </el-table-column>
          <el-table-column prop="updateTime" label="更新时间" width="180">
            <template slot-scope="scope">
              <span>{{ formatDate(scope.row.updateTime) }}</span>
            </template>
          </el-table-column>
        </el-table>
      </div>

      <div slot="footer" class="dialog-footer">
        <el-button @click="previewDialogVisible = false">关 闭</el-button>
        <el-button type="primary" @click="showExportDialog" :disabled="selectedTables.length === 0">导出选中表</el-button>
      </div>
    </el-dialog>

    <!-- 导出配置对话框 -->
    <el-dialog title="数据库文档导出" :visible.sync="exportDialogVisible" width="688px" append-to-body :close-on-click-modal="false">
      <div class="export-config">
        <el-form :model="exportConfig" label-width="120px">
          <el-form-item label="数据源：">
            <el-tag type="primary">{{ currentDataSource.name }}</el-tag>
          </el-form-item>
          <el-form-item label="导出范围：">
            <el-radio-group v-model="exportConfig.exportScope">
              <el-radio label="selected">仅选中的表 ({{ selectedTables.length }} 张)</el-radio>
              <el-radio label="all">所有表 ({{ getTotalTableCount() }} 张)</el-radio>
            </el-radio-group>
          </el-form-item>
          <el-form-item label="导出格式：">
            <el-radio-group v-model="exportConfig.exportType">
              <el-radio-button label="WORD">Word文档</el-radio-button>
              <el-radio-button label="PDF">PDF文档</el-radio-button>
              <el-radio-button label="MARKDOWN">MarkDown文档</el-radio-button>
              <el-radio-button label="HTML">HTML文档</el-radio-button>
            </el-radio-group>
          </el-form-item>
        </el-form>
      </div>
      <div slot="footer" class="dialog-footer">
        <el-button @click="exportDialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="handleExportConfirm" :loading="exporting">{{ exporting ? '导出中...' : '开始导出' }}</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listDatasource, testConnection } from "@/api/dataSource";
import { previewTables, exportDatabaseDoc } from "@/api/gen";

export default {
  name: "DatabaseExport",
  data() {
    return {
      loading: true,
      dataSourceList: [],
      selectedDataSource: null,
      queryParams: {
        name: undefined,
        type: undefined
      },
      previewDialogVisible: false,
      previewLoading: false,
      previewTableList: [],
      selectedTables: [],
      currentDataSource: {},
      exportDialogVisible: false,
      exporting: false,
      exportConfig: {
        exportType: 'WORD',
        exportScope: 'selected'
      },
      exportCount: 0
    };
  },
  computed: {
    hasActiveFilters() {
      return this.queryParams.type || this.queryParams.name;
    }
  },
  created() {
    this.loadDataSources();
  },
  methods: {
    async loadDataSources() {
      this.loading = true;
      try {
        const response = await listDatasource({});
        this.dataSourceList = response.rows || [];
      } catch (error) {
        this.$modal.msgError('加载数据源列表失败');
      } finally {
        this.loading = false;
      }
    },
    handleQuery() {
      this.loadDataSources();
    },
    resetQuery() {
      this.queryParams = { name: undefined, type: undefined };
      this.handleQuery();
    },
    handleCurrentChange(currentRow) {
      this.selectedDataSource = currentRow;
    },
    async testConnection(dataSource) {
      try {
        const response = await testConnection(dataSource);
        if (response.code === 200) {
          this.$modal.msgSuccess('数据源连接测试成功');
        } else {
          this.$modal.msgError('数据源连接测试失败：' + response.msg);
        }
      } catch (error) {
        this.$modal.msgError('数据源连接测试失败');
      }
    },
    async previewTables(dataSource) {
      this.currentDataSource = dataSource;
      this.selectedDataSource = dataSource;
      this.previewDialogVisible = true;
      this.previewLoading = true;
      this.previewTableList = [];
      this.selectedTables = [];
      try {
        const response = await previewTables({ dataSourceId: dataSource.id });
        this.previewTableList = response.data || [];
      } catch (error) {
        this.$modal.msgError('加载表列表失败');
      } finally {
        this.previewLoading = false;
      }
    },
    handlePreviewSelected() {
      if (!this.selectedDataSource) {
        this.$modal.msgWarning('请先选择一个数据源');
        return;
      }
      this.previewTables(this.selectedDataSource);
    },
    exportDatabase(dataSource) {
      this.currentDataSource = dataSource;
      this.selectedDataSource = dataSource;
      this.previewTables(dataSource);
    },
    handleTableSelectionChange(selection) {
      this.selectedTables = selection;
    },
    selectAllTables() {
      this.$refs.previewTableRef.toggleAllSelection();
    },
    clearTableSelection() {
      this.$refs.previewTableRef.clearSelection();
    },
    showExportDialog() {
      if (this.selectedTables.length === 0) {
        this.$modal.msgWarning('请至少选择一张表');
        return;
      }
      this.exportDialogVisible = true;
    },
    exportAllTables() {
      if (!this.selectedDataSource) {
        this.$modal.msgWarning('请先选择一个数据源');
        return;
      }
      this.exportConfig.exportScope = 'all';
      this.exportDialogVisible = true;
    },
    async handleExportConfirm() {
      this.exporting = true;
      try {
        let tableNames = '';
        if (this.exportConfig.exportScope === 'all') {
          if (this.previewTableList.length === 0) {
            const response = await previewTables({ dataSourceId: this.currentDataSource.id });
            this.previewTableList = response.data || [];
          }
          tableNames = this.previewTableList.map(t => t.tableName).join(',');
        } else {
          tableNames = this.selectedTables.map(t => t.tableName).join(',');
        }
        const response = await exportDatabaseDoc({
          tableNames,
          exportType: this.exportConfig.exportType,
          dataSourceId: this.currentDataSource.id
        });
        if (response.code === 200) {
          const { filePath, fileName } = response.data;
          this.downloadFile(filePath, fileName);
          this.$modal.msgSuccess('文档导出成功');
          this.exportDialogVisible = false;
          this.exportCount++;
        } else {
          this.$modal.msgError(response.msg || '导出失败');
        }
      } catch (error) {
        this.$modal.msgError('导出失败');
      } finally {
        this.exporting = false;
      }
    },
    downloadFile(filePath, fileName) {
      const url = '/dev-api/gen/download?filePath=' + encodeURIComponent(filePath) + '&fileName=' + encodeURIComponent(fileName);
      const link = document.createElement('a');
      link.href = url;
      link.download = fileName;
      document.body.appendChild(link);
      link.click();
      document.body.removeChild(link);
    },
    getDbTypeName(type) {
      const names = {
        '0': 'MySQL', '1': 'PostgreSQL', '2': 'Oracle', '3': 'SQL Server',
        '4': 'SQLite', '5': 'MongoDB', '6': 'Redis', '7': '其他'
      };
      return names[type] || '未知';
    },
    formatDate(date) {
      if (!date) return '-';
      return new Date(date).toLocaleString('zh-CN');
    },
    getDbTypeColor(type) {
      const colors = {
        '0': 'primary', '1': 'danger', '2': 'warning', '3': 'info',
        '4': 'success', '5': '', '6': 'warning', '7': 'danger'
      };
      return colors[type] || '';
    },
    handleMoreAction(command, row) {
      switch (command) {
        case 'export':
          this.exportDatabase(row);
          break;
      }
    },
    getTotalTableCount() {
      if (this.previewTableList.length === 0) return '?';
      return this.previewTableList.length;
    }
  }
};
</script>

<style scoped>
.export-page {
  padding: 20px;
  background-color: #ffffff;
  min-height: 100vh;
}
.main-container {
  background: white;
  border-radius: 12px;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.05);
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
  font-size: 24px; font-weight: 700; margin: 0 0 4px 0;
  display: flex; align-items: center; gap: 8px; color: #3b82f6;
}
.page-title i { font-size: 26px; color: #3b82f6; }
.page-description { margin: 0; font-size: 14px; color: #6b7280; }

.stats-section {
  display: flex; gap: 12px; padding: 20px 32px 16px;
  background: #ffffff;
}
.stat-card {
  display: flex; align-items: center; gap: 10px;
  background: #ffffff; border: 1px solid #e5e7eb; border-radius: 8px;
  padding: 12px 16px; flex: 1; transition: all 0.3s ease;
  box-shadow: 0 1px 3px rgba(0, 0, 0, 0.1);
}
.stat-card:hover { background: #f9fafb; transform: translateY(-2px); box-shadow: 0 4px 12px rgba(0, 0, 0, 0.15); }
.stat-icon { width: 40px; height: 40px; border-radius: 10px; display: flex; align-items: center; justify-content: center; background: #f3f4f6; }
.stat-icon i { font-size: 20px; color: #3b82f6; }
.stat-icon.datasource-icon { background: #dbeafe; }
.stat-icon.datasource-icon i { color: #3b82f6; }
.stat-icon.preview-icon { background: #d1fae5; }
.stat-icon.preview-icon i { color: #10b981; }
.stat-icon.export-icon { background: #fef3c7; }
.stat-icon.export-icon i { color: #f59e0b; }
.stat-content { flex: 1; }
.stat-title { font-size: 13px; color: #6b7280; margin-bottom: 2px; font-weight: 500; }
.stat-value { font-size: 20px; font-weight: 700; line-height: 1; color: #1f2937; }

.smart-toolbar {
  display: flex; align-items: center; justify-content: space-between;
  padding: 16px 32px; border-bottom: 1px solid #f0f2f5; gap: 24px;
}
.toolbar-left { display: flex; align-items: center; gap: 12px; }
.toolbar-right { display: flex; align-items: center; }
.selection-info { margin-right: 8px; }

.table-section { padding: 0 32px 32px; }
.pagination-wrapper { margin-top: 24px; display: flex; justify-content: center; }

.datasource-info .datasource-name { display: flex; align-items: center; gap: 12px; margin-bottom: 8px; }
.datasource-info .name-text { font-weight: 600; color: #1f2937; font-size: 15px; }
.datasource-info .datasource-details .detail-item {
  display: flex; align-items: center; gap: 6px; margin-bottom: 4px;
  font-size: 13px; color: #6b7280;
}
.datasource-info .datasource-details .detail-item:last-child { margin-bottom: 0; }
.datasource-info .datasource-details .detail-item i { font-size: 14px; color: #9ca3af; }

.auth-info .username, .auth-info .password {
  display: flex; align-items: center; gap: 6px; margin-bottom: 4px;
  font-size: 13px; color: #6b7280;
}
.auth-info .username i, .auth-info .password i { font-size: 14px; color: #9ca3af; }

.preview-info-section { margin-bottom: 20px; }
.preview-tags { display: flex; gap: 12px; align-items: center; flex-wrap: wrap; }
.preview-actions-section {
  margin-bottom: 20px; display: flex; gap: 12px; align-items: center;
  padding: 16px 0; border-bottom: 1px solid #f0f2f5;
}
.dialog-footer { text-align: right; }
</style>
<template>
  <div class="export-page-v2">
    <!-- 整体白板容器 - 化零为整 -->
    <div class="main-container">
      <!-- 页面标题区 -->
      <div class="page-header">
        <div class="header-content">
          <h1 class="page-title">
            <el-icon><Document /></el-icon>
            数据库文档导出
          </h1>
          <p class="page-description">选择数据源，预览表结构，导出数据库设计文档</p>
        </div>
      </div>

      <!-- 精致统计卡片区 -->
      <div class="stats-section">
        <div class="stat-card">
          <div class="stat-icon datasource-icon">
            <el-icon><Connection /></el-icon>
          </div>
          <div class="stat-content">
            <div class="stat-title">数据源总数</div>
            <div class="stat-value">{{ dataSourceList.length }}</div>
          </div>
        </div>
        
        <div class="stat-card">
          <div class="stat-icon preview-icon">
            <el-icon><View /></el-icon>
          </div>
          <div class="stat-content">
            <div class="stat-title">预览表数</div>
            <div class="stat-value">{{ previewTableList.length }}</div>
          </div>
        </div>
        
        <div class="stat-card">
          <div class="stat-icon export-icon">
            <el-icon><Download /></el-icon>
          </div>
          <div class="stat-content">
            <div class="stat-title">已导出</div>
            <div class="stat-value">{{ exportCount || 0 }}</div>
          </div>
        </div>
      </div>

      <!-- 智能工具栏 -->
      <div class="smart-toolbar">
        <!-- 左侧：主要操作 -->
        <div class="toolbar-left">
          <!-- 未选择时显示主要操作 -->
          <template v-if="!selectedDataSource">
            <el-button
              type="primary"
              size="large"
              @click="loadDataSources"
              class="primary-action-btn"
            >
              <el-icon><Refresh /></el-icon>
              刷新数据源
            </el-button>
          </template>
          
          <!-- 选择后显示批量操作 -->
          <template v-else>
            <div class="selection-info">
              <el-tag type="primary" size="large">
                <el-icon><Connection /></el-icon>
                当前数据源：{{ selectedDataSource.name }}
              </el-tag>
            </div>
            <el-button
              type="info"
              size="large"
              @click="handlePreviewSelected"
              class="batch-action-btn"
            >
              <el-icon><View /></el-icon>
              预览表结构
            </el-button>
            <el-button
              type="success"
              size="large"
              @click="exportAllTables"
              class="batch-action-btn"
            >
              <el-icon><Download /></el-icon>
              导出所有表
            </el-button>
          </template>
        </div>

        <!-- 右侧：筛选与搜索 -->
        <div class="toolbar-right">
          <el-select
            v-model="queryParams.type"
            placeholder="数据库类型"
            clearable
            size="large"
            style="width: 140px; margin-right: 12px;"
            @change="handleQuery"
          >
            <el-option label="MySQL" value="0" />
            <el-option label="Oracle" value="1" />
            <el-option label="SQL Server" value="2" />
            <el-option label="PostgreSQL" value="3" />
            <el-option label="ClickHouse" value="4" />
            <el-option label="SQLite" value="5" />
            <el-option label="DB2" value="6" />
            <el-option label="达梦" value="7" />
          </el-select>
          
          <el-input
            v-model="queryParams.name"
            placeholder="搜索数据源名称..."
            size="large"
            style="width: 280px; margin-right: 12px;"
            clearable
            @keyup.enter="handleQuery"
            @clear="handleQuery"
          >
            <template #prefix>
              <el-icon><Search /></el-icon>
            </template>
          </el-input>
          
          <el-button
            v-if="hasActiveFilters"
            size="large"
            @click="resetQuery"
            class="reset-btn"
          >
            <el-icon><Refresh /></el-icon>
            重置
          </el-button>
        </div>
      </div>

      <!-- 数据表格区 -->
      <div class="table-section">
        <el-table
          v-loading="loading"
          :data="dataSourceList"
          @current-change="handleCurrentChange"
          highlight-current-row
          class="modern-table"
          :empty-text="loading ? '加载中...' : '暂无数据源'"
        >
          <el-table-column label="序号" width="60" align="center">
            <template #default="scope">
              <span class="table-index">{{ scope.$index + 1 }}</span>
            </template>
          </el-table-column>

          <!-- 数据源信息 - 整合显示 -->
          <el-table-column label="数据源信息" min-width="280">
            <template #default="scope">
              <div class="datasource-info-v2">
                <div class="datasource-name">
                  <span class="name-text">{{ scope.row.name }}</span>
                  <el-tag
                    :type="scope.row.status === 1 ? 'success' : 'info'"
                    size="small"
                    class="status-pill"
                    round
                  >
                    {{ scope.row.status === 1 ? '默认' : '正常' }}
                  </el-tag>
                </div>
                <div class="datasource-details">
                  <div class="detail-item">
                    <el-icon class="detail-icon"><Link /></el-icon>
                    <span class="detail-text">{{ scope.row.url }}:{{ scope.row.port }}</span>
                  </div>
                  <div class="detail-item">
                    <el-icon class="detail-icon"><Coin /></el-icon>
                    <span class="detail-text">{{ scope.row.dbName }}</span>
                  </div>
                </div>
              </div>
            </template>
          </el-table-column>

          <!-- 数据库类型 - 胶囊标签 -->
          <el-table-column label="数据库类型" width="140" align="center">
            <template #default="scope">
              <div class="db-type-v2">
                <el-tag
                  :type="getDbTypeColor(scope.row.type)"
                  size="default"
                  class="db-type-pill"
                  round
                >
                  <el-icon><component :is="getDbTypeIconComponent(scope.row.type)" /></el-icon>
                  {{ getDbTypeName(scope.row.type) }}
                </el-tag>
              </div>
            </template>
          </el-table-column>

          <!-- 认证信息 -->
          <el-table-column label="认证信息" width="160" align="center">
            <template #default="scope">
              <div class="auth-info-v2">
                <div class="username">
                  <el-icon class="auth-icon"><User /></el-icon>
                  <span>{{ scope.row.username }}</span>
                </div>
                <div class="password">
                  <el-icon class="auth-icon"><Lock /></el-icon>
                  <span>******</span>
                </div>
              </div>
            </template>
          </el-table-column>

          <!-- 操作列 - 优化设计 -->
          <el-table-column label="操作" width="180" align="center" fixed="right">
            <template #default="scope">
              <div class="table-actions-v2">
                <el-tooltip content="测试连接" placement="top">
                  <el-button
                    type="primary"
                    size="small"
                    @click="testConnection(scope.row)"
                    class="action-btn-v2"
                    circle
                  >
                    <el-icon><Link /></el-icon>
                  </el-button>
                </el-tooltip>
                
                <el-tooltip content="预览表结构" placement="top">
                  <el-button
                    type="success"
                    size="small"
                    @click="previewTables(scope.row)"
                    class="action-btn-v2"
                    circle
                  >
                    <el-icon><View /></el-icon>
                  </el-button>
                </el-tooltip>
                
                <el-dropdown trigger="click" @command="(command) => handleMoreAction(command, scope.row)">
                  <el-button
                    type="info"
                    size="small"
                    class="action-btn-v2"
                    circle
                  >
                    <el-icon><Setting /></el-icon>
                  </el-button>
                  <template #dropdown>
                    <el-dropdown-menu>
                      <el-dropdown-item command="export">
                        <el-icon><Download /></el-icon>
                        导出文档
                      </el-dropdown-item>
                    </el-dropdown-menu>
                  </template>
                </el-dropdown>
              </div>
            </template>
          </el-table-column>
        </el-table>
      </div>
    </div>

    <!-- 表预览对话框 -->
    <el-dialog
      title="数据库表预览"
      v-model="previewDialogVisible"
      width="1200px"
      append-to-body
      :close-on-click-modal="false"
    >
      <!-- 预览信息区域 -->
      <div class="preview-info-section">
        <div class="preview-tags">
          <el-tag type="primary" size="default">
            <el-icon><Connection /></el-icon>
            {{ currentDataSource.name }}
          </el-tag>
          <el-tag type="info" size="default">
            共 {{ previewTableList.length }} 张表
          </el-tag>
          <el-tag type="success" size="default" v-if="selectedTables.length > 0">
            已选择 {{ selectedTables.length }} 张表
          </el-tag>
        </div>
      </div>

      <!-- 操作按钮区域 -->
      <div class="preview-actions-section">
        <el-button
          type="success"
          size="default"
          @click="selectAllTables"
        >
          <el-icon><Check /></el-icon>
          全选
        </el-button>
        <el-button
          type="warning"
          size="default"
          @click="clearTableSelection"
        >
          <el-icon><Close /></el-icon>
          清空
        </el-button>
        <el-button
          type="primary"
          size="default"
          @click="showExportDialog"
          :disabled="selectedTables.length === 0"
        >
          <el-icon><Download /></el-icon>
          导出选中
        </el-button>
      </div>

      <!-- 表格区域 -->
      <div class="preview-table-section">
        <el-table
          ref="previewTableRef"
          :data="previewTableList"
          v-loading="previewLoading"
          @selection-change="handleTableSelectionChange"
          style="width: 100%"
          max-height="400"
        >
        <el-table-column type="selection" width="55" align="center" />
        <el-table-column prop="tableName" label="表名" min-width="200">
          <template #default="scope">
            <span class="table-name">{{ scope.row.tableName }}</span>
          </template>
        </el-table-column>
        <el-table-column prop="tableComment" label="表注释" min-width="250">
          <template #default="scope">
            <span class="table-comment">{{ scope.row.tableComment || '暂无注释' }}</span>
          </template>
        </el-table-column>
        <el-table-column prop="createTime" label="创建时间" width="180">
          <template #default="scope">
            <span>{{ formatDate(scope.row.createTime) }}</span>
          </template>
        </el-table-column>
        <el-table-column prop="updateTime" label="更新时间" width="180">
          <template #default="scope">
            <span>{{ formatDate(scope.row.updateTime) }}</span>
          </template>
        </el-table-column>
        </el-table>
      </div>

      <template #footer>
        <div class="dialog-footer">
          <el-button @click="previewDialogVisible = false">关 闭</el-button>
          <el-button
            type="primary"
            @click="showExportDialog"
            :disabled="selectedTables.length === 0"
          >
            导出选中表
          </el-button>
        </div>
      </template>
    </el-dialog>

    <!-- 导出配置对话框 -->
    <el-dialog
      title="数据库文档导出"
      v-model="exportDialogVisible"
      width="688px"
      append-to-body
      :close-on-click-modal="false"
    >
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
              <el-radio-button label="WORD">
                <el-icon><Document /></el-icon>
                Word文档
              </el-radio-button>
              <el-radio-button label="PDF">
                <el-icon><DocumentCopy /></el-icon>
                PDF文档
              </el-radio-button>
              <el-radio-button label="MARKDOWN">
                <el-icon><DocumentCopy /></el-icon>
                MarkDown文档
              </el-radio-button>
              <el-radio-button label="HTML">
                <el-icon><Monitor /></el-icon>
                HTML文档
              </el-radio-button>
            </el-radio-group>
          </el-form-item>
        </el-form>
      </div>

      <template #footer>
        <div class="dialog-footer">
          <el-button @click="exportDialogVisible = false">取 消</el-button>
          <el-button
            type="primary"
            @click="handleExportConfirm"
            :loading="exporting"
          >
            {{ exporting ? '导出中...' : '开始导出' }}
          </el-button>
        </div>
      </template>
    </el-dialog>
    </div>
</template>

<script>
import genApi from '@/api/gen'
import dataSourceApi from '@/api/dataSource'
import IconHelper from '@/utils/icon-helper'
import {
  Document,
  View,
  Download,
  Search,
  Close,
  Refresh,
  Setting,
  Check,
  Link,
  DocumentCopy,
  Monitor,
  Coin,
  Connection,
  User,
  Lock
} from '@element-plus/icons-vue';

export default {
  name: "DatabaseExport",
  setup() {
    return {
      $iconHelper: IconHelper
    }
  },
  components: {
    Document,
    View,
    Download,
    Search,
    Close,
    Refresh,
    Setting,
    Check,
    Link,
    DocumentCopy,
    Monitor,
    Coin,
    Connection,
    User,
    Lock
  },
  data() {
    return {
      // 遮罩层
      loading: true,
      // 显示搜索条件
      showSearch: false,
      // 数据源列表
      dataSourceList: [],
      // 当前选中的数据源
      selectedDataSource: null,
      // 查询参数
      queryParams: {
        name: undefined,
        type: undefined
      },

      // 预览相关
      previewDialogVisible: false,
      previewLoading: false,
      previewTableList: [],
      selectedTables: [],
      currentDataSource: {},

      // 导出相关
      exportDialogVisible: false,
      exporting: false,
      exportConfig: {
        exportType: 'WORD',
        exportScope: 'selected'
      },

      // 导出统计
      exportCount: 0,
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
    /** 加载数据源列表 */
    async loadDataSources() {
      this.loading = true;
      try {
        const response = await dataSourceApi.listDatasource();
        this.dataSourceList = response.rows || [];
      } catch (error) {
        this.$message.error('加载数据源列表失败');
      } finally {
        this.loading = false;
      }
    },

    /** 搜索按钮操作 */
    handleQuery() {
      // 根据查询条件过滤数据源
      this.loadDataSources();
    },
    /** 重置查询 */
    resetQuery() {
      this.queryParams = {
        name: undefined,
        type: undefined
      };
      this.handleQuery();
    },

    /** 当前行变化 */
    handleCurrentChange(currentRow) {
      this.selectedDataSource = currentRow;
    },

    /** 测试数据源连接 */
    async testConnection(dataSource) {
      try {
        const response = await dataSourceApi.testConnection(dataSource);
        if (response.code === 200) {
          this.$message.success('数据源连接测试成功');
        } else {
          this.$message.error('数据源连接测试失败：' + response.msg);
        }
      } catch (error) {
        this.$message.error('数据源连接测试失败：' + error.message);
      }
    },
    /** 预览数据库表 */
    async previewTables(dataSource) {
      this.currentDataSource = dataSource;
      this.selectedDataSource = dataSource;
      this.previewDialogVisible = true;
      this.previewLoading = true;
      this.previewTableList = [];
      this.selectedTables = [];

      try {
        const response = await genApi.previewTables({ dataSourceId: dataSource.id });
        this.previewTableList = response.data || [];
      } catch (error) {
        this.$message.error('加载表列表失败：' + error.message);
      } finally {
        this.previewLoading = false;
      }
    },

    /** 工具栏预览按钮 */
    handlePreviewSelected() {
      if (!this.selectedDataSource) {
        this.$message.warning('请先选择一个数据源');
        return;
      }
      this.previewTables(this.selectedDataSource);
    },
    /** 导出数据库文档 */
    exportDatabase(dataSource) {
      this.currentDataSource = dataSource;
      this.selectedDataSource = dataSource;
      this.previewTables(dataSource);
    },

    /** 表格选择变化 */
    handleTableSelectionChange(selection) {
      this.selectedTables = selection;
    },

    /** 全选表格 */
    selectAllTables() {
      this.$refs.previewTableRef.toggleAllSelection();
    },

    /** 清空表格选择 */
    clearTableSelection() {
      this.$refs.previewTableRef.clearSelection();
    },

    /** 显示导出对话框 */
    showExportDialog() {
      if (this.selectedTables.length === 0) {
        this.$message.warning('请至少选择一张表');
        return;
      }
      this.exportDialogVisible = true;
    },

    /** 导出所有表 */
    exportAllTables() {
      if (!this.selectedDataSource) {
        this.$message.warning('请先选择一个数据源');
        return;
      }
      this.exportConfig.exportScope = 'all';
      // 直接显示导出配置对话框，不需要先预览
      this.exportDialogVisible = true;
    },
    /** 确认导出 */
    async handleExportConfirm() {
      this.exporting = true;
      try {
        let tableNames = '';
        
        if (this.exportConfig.exportScope === 'all') {
          // 如果是导出所有表，需要先获取表列表
          if (this.previewTableList.length === 0) {
            const response = await genApi.previewTables({ dataSourceId: this.currentDataSource.id });
            this.previewTableList = response.data || [];
          }
          tableNames = this.previewTableList.map(t => t.tableName).join(',');
        } else {
          // 导出选中的表
          tableNames = this.selectedTables.map(t => t.tableName).join(',');
        }

        const response = await genApi.exportDatabaseDoc({
          tableNames,
          exportType: this.exportConfig.exportType,
          dataSourceId: this.currentDataSource.id
        });

        if (response.code === 200) {
          // 下载文件
          const { filePath, fileName } = response.data;
          this.downloadFile(filePath, fileName);
          this.$message.success('文档导出成功');
          this.exportDialogVisible = false;
          this.exportCount++;
        } else {
          this.$message.error(response.msg || '导出失败');
        }
      } catch (error) {
        this.$message.error('导出失败：' + error.message);
      } finally {
        this.exporting = false;
      }
    },

    /** 下载文件 */
    downloadFile(filePath, fileName) {
      const url = `${process.env.VUE_APP_BASE_API}/tool/gen/download?filePath=${encodeURIComponent(filePath)}&fileName=${encodeURIComponent(fileName)}`;
      const link = document.createElement('a');
      link.href = url;
      link.download = fileName;
      document.body.appendChild(link);
      link.click();
      document.body.removeChild(link);
    },
    /** 获取数据库类型图标 */
    getDbTypeIcon(type) {
      const icons = {
        '0': 'el-icon-coin',
        '1': 'el-icon-s-data',
        '2': 'el-icon-s-platform',
        '3': 'el-icon-data-board',
        '4': 'el-icon-s-marketing',
        '5': 'el-icon-files',
        '6': 'el-icon-s-cooperation',
        '7': 'el-icon-s-grid'
      }
      return icons[type] || 'el-icon-s-data';
    },
    /** 获取数据库类型图标组件 */
    getDbTypeIconComponent(type) {
      return this.$iconHelper.getDbTypeIconComponent(type);
    },

    /** 获取数据库类型名称 */
    getDbTypeName(type) {
      const names = {
        '0': 'MySQL',
        '1': 'PostgreSQL',
        '2': 'Oracle',
        '3': 'SQL Server',
        '4': 'SQLite',
        '5': 'MongoDB',
        '6': 'Redis',
        '7': '其他'
      };
      return names[type] || '未知';
    },

    /** 格式化日期 */
    formatDate(date) {
      if (!date) return '-';
      return new Date(date).toLocaleString('zh-CN');
    },

    /** 获取数据库类型颜色 */
    getDbTypeColor(type) {
      const colors = {
        '0': 'primary',
        '1': 'danger',
        '2': 'warning',
        '3': 'info',
        '4': 'success',
        '5': '',
        '6': 'warning',
        '7': 'danger'
      };
      return colors[type] || '';
    },

    /** 更多操作处理 */
    handleMoreAction(command, row) {
      switch (command) {
        case 'export':
          this.exportDatabase(row);
          break;
      }
    },

    /** 获取总表数量 */
    getTotalTableCount() {
      // 如果预览表列表为空，返回一个默认值或从数据源获取
      if (this.previewTableList.length === 0) {
        return '?';
      }
      return this.previewTableList.length;
    }
  }
};
</script>

<style lang="scss" scoped>
// V2 设计样式
.export-page-v2 {
  padding: 20px;
  background-color: #ffffff;
  min-height: 100vh;
}

// 主容器 - 整体白板效果
.main-container {
  background: white;
  border-radius: 12px;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.05);
  overflow: hidden;
}

// 页面头部
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
}

// 精致统计卡片区
.stats-section {
  display: flex;
  gap: 12px;
  padding: 20px 32px 16px;
  background: #ffffff;
  position: relative;
  z-index: 1;

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

      &.datasource-icon {
        background: #dbeafe;
        
        .el-icon {
          color: #3b82f6;
        }
      }

      &.preview-icon {
        background: #d1fae5;
        
        .el-icon {
          color: #10b981;
        }
      }

      &.export-icon {
        background: #fef3c7;
        
        .el-icon {
          color: #f59e0b;
        }
      }
    }

    .stat-content {
      flex: 1;

      .stat-title {
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

// 智能工具栏
.smart-toolbar {
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 16px 32px;
  border-bottom: 1px solid #f0f2f5;
  gap: 24px;

  .toolbar-left {
    display: flex;
    align-items: center;
    gap: 16px;

    .primary-action-btn {
      padding: 12px 24px;
      font-size: 16px;
      font-weight: 600;
      border-radius: 8px;
      box-shadow: 0 2px 8px rgba(59, 130, 246, 0.3);
      
      &:hover {
        transform: translateY(-1px);
        box-shadow: 0 4px 12px rgba(59, 130, 246, 0.4);
      }
    }

    .selection-info {
      margin-right: 8px;
    }

    .batch-action-btn {
      padding: 10px 20px;
      font-weight: 500;
      border-radius: 6px;
    }
  }

  .toolbar-right {
    display: flex;
    align-items: center;

    .reset-btn {
      color: #6b7280;
      border-color: #d1d5db;
      
      &:hover {
        color: #374151;
        border-color: #9ca3af;
      }
    }
  }
}

// 表格区域
.table-section {
  padding: 0 32px 32px;

  .modern-table {
    border-radius: 8px;
    overflow: hidden;
    
    :deep(.el-table__header) {
      background-color: #f8fafc;
      
      th {
        background-color: #f8fafc !important;
        color: #374151;
        font-weight: 600;
        font-size: 14px;
        border-bottom: 2px solid #e5e7eb;
      }
    }

    :deep(.el-table__row) {
      &:hover {
        background-color: #f9fafb;
      }
    }
  }

  // 数据源信息V2样式
  .datasource-info-v2 {
    .datasource-name {
      display: flex;
      align-items: center;
      gap: 12px;
      margin-bottom: 8px;

      .name-text {
        font-weight: 600;
        color: #1f2937;
        font-size: 15px;
      }

      .status-pill {
        font-size: 12px;
        font-weight: 500;
      }
    }

    .datasource-details {
      .detail-item {
        display: flex;
        align-items: center;
        gap: 6px;
        margin-bottom: 4px;
        font-size: 13px;
        color: #6b7280;

        &:last-child {
          margin-bottom: 0;
        }

        .detail-icon {
          font-size: 14px;
          color: #9ca3af;
        }

        .detail-text {
          font-family: 'Monaco', 'Menlo', monospace;
        }
      }
    }
  }

  // 数据库类型V2样式
  .db-type-v2 {
    .db-type-pill {
      font-weight: 500;
      padding: 6px 12px;
      
      .el-icon {
        margin-right: 4px;
      }
    }
  }

  // 认证信息V2样式
  .auth-info-v2 {
    .username, .password {
      display: flex;
      align-items: center;
      gap: 6px;
      margin-bottom: 4px;
      font-size: 13px;
      color: #6b7280;

      &:last-child {
        margin-bottom: 0;
      }

      .auth-icon {
        font-size: 14px;
        color: #9ca3af;
      }
    }
  }

  // 操作按钮V2样式
  .table-actions-v2 {
    display: flex;
    align-items: center;
    gap: 8px;

    .action-btn-v2 {
      width: 32px;
      height: 32px;
      border-radius: 50%;
      display: flex;
      align-items: center;
      justify-content: center;
      transition: all 0.2s ease;

      &:hover {
        transform: scale(1.1);
      }
    }
  }
}

// 对话框样式优化
:deep(.el-dialog) {
  border-radius: 12px;
  box-shadow: 0 20px 40px rgba(0, 0, 0, 0.1);
}

:deep(.el-dialog__header) {
  background: linear-gradient(135deg, #f8fafc 0%, #f1f5f9 100%);
  border-bottom: 1px solid #e5e7eb;
  border-radius: 12px 12px 0 0;
  padding: 20px 24px;
}

:deep(.el-dialog__title) {
  font-weight: 600;
  color: #1f2937;
}

// 表单样式优化
:deep(.el-form-item__label) {
  font-weight: 500;
  color: #374151;
}

:deep(.el-input__inner) {
  border-radius: 6px;
  border-color: #d1d5db;
  
  &:focus {
    border-color: #3b82f6;
    box-shadow: 0 0 0 3px rgba(59, 130, 246, 0.1);
  }
}

:deep(.el-select) {
  .el-input__inner {
    border-radius: 6px;
  }
}

// 按钮样式优化
:deep(.el-button) {
  border-radius: 6px;
  font-weight: 500;
  transition: all 0.2s ease;

  &.el-button--primary {
    background: linear-gradient(135deg, #3b82f6, #1d4ed8);
    border: none;
    
    &:hover {
      transform: translateY(-1px);
      box-shadow: 0 4px 12px rgba(59, 130, 246, 0.4);
    }
  }

  &.el-button--success {
    background: linear-gradient(135deg, #10b981, #059669);
    border: none;
    
    &:hover {
      transform: translateY(-1px);
      box-shadow: 0 4px 12px rgba(16, 185, 129, 0.4);
    }
  }

  &.el-button--info {
    background: linear-gradient(135deg, #6b7280, #4b5563);
    border: none;
    
    &:hover {
      transform: translateY(-1px);
      box-shadow: 0 4px 12px rgba(107, 114, 128, 0.4);
    }
  }
}

// 标签样式优化
:deep(.el-tag) {
  border-radius: 12px;
  font-weight: 500;
  
  &.el-tag--primary {
    background: linear-gradient(135deg, #dbeafe, #bfdbfe);
    color: #1d4ed8;
    border: 1px solid #93c5fd;
  }

  &.el-tag--success {
    background: linear-gradient(135deg, #d1fae5, #a7f3d0);
    color: #059669;
    border: 1px solid #6ee7b7;
  }

  &.el-tag--info {
    background: linear-gradient(135deg, #f3f4f6, #e5e7eb);
    color: #6b7280;
    border: 1px solid #d1d5db;
  }
}

// 预览对话框样式
.preview-info-section {
  margin-bottom: 20px;
  
  .preview-tags {
    display: flex;
    gap: 12px;
    align-items: center;
    flex-wrap: wrap;
  }
}

.preview-actions-section {
  margin-bottom: 20px;
  display: flex;
  gap: 12px;
  align-items: center;
  padding: 16px 0;
  border-bottom: 1px solid #f0f2f5;
}

.preview-table-section {
  margin-top: 0;
}

// 对话框底部按钮样式
.dialog-footer {
  display: flex;
  justify-content: flex-end;
  gap: 12px;
  padding-top: 20px;
  margin-top: 20px;
  border-top: 1px solid #f0f2f5;
}

// 响应式设计
@media (max-width: 1200px) {
  .stats-section {
    flex-direction: column;
    gap: 16px;
  }

  .smart-toolbar {
    flex-direction: column;
    align-items: stretch;
    gap: 16px;

    .toolbar-left, .toolbar-right {
      justify-content: center;
    }
  }
}

@media (max-width: 768px) {
  .export-page-v2 {
    padding: 12px;
  }

  .main-container {
    border-radius: 8px;
  }

  .page-header {
    padding: 16px 20px;
  }

  .stats-section {
    padding: 16px 20px;
  }

  .smart-toolbar {
    padding: 16px 20px;
  }

  .table-section {
    padding: 0 20px 20px;
  }

  .stat-card {
    padding: 16px;
    
    .stat-icon {
      width: 40px;
      height: 40px;
      font-size: 20px;
    }

    .stat-content {
      .stat-value {
        font-size: 24px;
      }
    }
  }
}
</style>


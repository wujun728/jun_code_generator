
<template>
  <div class="datasource-page-v2">
    <!-- 整体白板容器 - 化零为整 -->
    <div class="main-container">
      <!-- 页面标题区 -->
      <div class="page-header">
        <div class="header-content">
          <h1 class="page-title">
            <el-icon><Connection /></el-icon>
            数据源管理
          </h1>
          <p class="page-description">统一管理数据库连接配置，支持多种数据库类型</p>
        </div>
      </div>

      <!-- 精致统计卡片区 -->
      <div class="stats-section">
        <div class="stat-card">
          <div class="stat-icon datasource-icon">
            <el-icon><Grid /></el-icon>
          </div>
          <div class="stat-content">
            <div class="stat-title">数据源总数</div>
            <div class="stat-value">{{ total }}</div>
          </div>
        </div>
        
        <div class="stat-card">
          <div class="stat-icon active-icon">
            <el-icon><TrendCharts /></el-icon>
          </div>
          <div class="stat-content">
            <div class="stat-title">活跃连接</div>
            <div class="stat-value">{{ activeCount }}</div>
          </div>
        </div>
        
        <div class="stat-card">
          <div class="stat-icon type-icon">
            <el-icon><Platform /></el-icon>
          </div>
          <div class="stat-content">
            <div class="stat-title">支持类型</div>
            <div class="stat-value">{{ dbTypeCount }}</div>
          </div>
        </div>
      </div>

      <!-- 智能工具栏 -->
      <div class="smart-toolbar">
        <!-- 左侧：主要操作 -->
        <div class="toolbar-left">
          <!-- 未选择时显示新增按钮 -->
          <template v-if="ids.length === 0">
            <el-button
              type="primary"
              size="large"
              @click="handleAdd"
              class="primary-action-btn"
            >
              <el-icon><Plus /></el-icon>
              新增数据源
            </el-button>
          </template>
          
          <!-- 选择后显示批量操作 -->
          <template v-else>
            <div class="selection-info">
              <el-tag type="primary" size="large">
                <el-icon><Check /></el-icon>
                已选择 {{ ids.length }} 项
              </el-tag>
            </div>
            <el-button
              type="success"
              size="large"
              :disabled="single"
              @click="handleUpdate"
              class="batch-action-btn"
            >
              <el-icon><Edit /></el-icon>
              修改配置
            </el-button>
            <el-button
              type="danger"
              size="large"
              :disabled="multiple"
              @click="handleDelete"
              class="batch-action-btn"
            >
              <el-icon><Delete /></el-icon>
              删除
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
            v-model="searchKeyword"
            placeholder="搜索数据源名称或地址..."
            size="large"
            style="width: 280px; margin-right: 12px;"
            clearable
            @keyup.enter="handleSearch"
            @clear="handleSearch"
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
          :data="datasourceList"
          @selection-change="handleSelectionChange"
          class="modern-table"
          :empty-text="loading ? '加载中...' : '暂无数据源'"
          stripe
        >
          <el-table-column type="selection" width="50" align="center" />

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
                
                <el-tooltip content="编辑配置" placement="top">
                  <el-button
                    type="success"
                    size="small"
                    @click="handleUpdate(scope.row)"
                    class="action-btn-v2"
                    circle
                  >
                    <el-icon><Edit /></el-icon>
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
                      <el-dropdown-item command="switch">
                        <el-icon><Refresh /></el-icon>
                        切换数据源
                      </el-dropdown-item>
                      <el-dropdown-item command="delete" divided>
                        <el-icon><Delete /></el-icon>
                        删除数据源
                      </el-dropdown-item>
                    </el-dropdown-menu>
                  </template>
                </el-dropdown>
              </div>
            </template>
          </el-table-column>
        </el-table>

        <!-- 分页 -->
        <div class="pagination-wrapper">
          <pagination
            v-show="total > 0"
            :total="total"
            :page.sync="queryParams.pageNum"
            :limit.sync="queryParams.pageSize"
            @pagination="handlePagination"
            layout="total, sizes, prev, pager, next, jumper"
          />
        </div>
      </div>
    </div>

    <!-- 添加或修改动态数据源配置对话框 -->
    <el-dialog :title="title" v-model="open" width="800px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="98px">
        <el-form-item label="数据源名称" prop="name">
          <el-input v-model="form.name" placeholder="请输入数据源名称" />
        </el-form-item>
        <el-form-item label="连接地址" prop="url">
          <el-input v-model="form.url" placeholder="请输入数据库连接地址" />
        </el-form-item>
        <el-form-item label="端口号" prop="port">
          <el-input v-model="form.port" placeholder="请输入数据库端口号" />
        </el-form-item>
        <el-form-item label="数据库名称" prop="dbName">
          <el-input v-model="form.dbName" placeholder="请输入数据库名称" />
        </el-form-item>
        <el-form-item label="用户名" prop="username">
          <el-input v-model="form.username" placeholder="请输入数据库用户名" />
        </el-form-item>
        <el-form-item label="密码" prop="password">
          <el-input v-model="form.password" type="password" placeholder="请输入数据库密码" show-password />
        </el-form-item>
        <el-form-item label="数据库类型" prop="type">
          <el-select v-model="form.type" placeholder="请选择数据库类型" clearable>
            <el-option label="MySQL" value="0" />
            <el-option label="Oracle" value="1" />
            <el-option label="sqlserver" value="2" />
            <el-option label="postgresql" value="3" />
            <el-option label="clickhouse" value="4" />
            <el-option label="sqlite" value="5" />
            <el-option label="db2" value="6" />
            <el-option label="dm" value="7" />
          </el-select>
        </el-form-item>
        <el-form-item label="数据源状态" prop="status">
          <el-radio-group v-model="form.status">
            <el-radio label="1">默认</el-radio>
            <el-radio label="2">正常</el-radio>
          </el-radio-group>
        </el-form-item>
      </el-form>
      <template #footer>
        <div class="dialog-footer">
          <el-button @click="cancel">取 消</el-button>
          <el-button type="primary" @click="submitForm">确 定</el-button>
        </div>
      </template>
    </el-dialog>
  </div>
</template>

<script>
import dataSourceApi from "@/api/dataSource";
import genApi from "@/api/gen";
import IconHelper from "@/utils/icon-helper";
import TechStackUtils from '@/utils/tech-stack';
import {
  ArrowRight,
  Connection,
  Grid,
  Check,
  Platform,
  Search,
  Close,
  Coin,
  Refresh,
  Setting,
  Plus,
  Edit,
  Delete,
  Link,
  User,
  Lock,
  Files,
  TrendCharts
} from '@element-plus/icons-vue';

export default {
  name: "Datasource",
  setup() {
    return {
      $iconHelper: IconHelper
    }
  },
  components: {
    ArrowRight,
    Connection,
    Grid,
    Check,
    Platform,
    Search,
    Close,
    Coin,
    Refresh,
    Setting,
    Plus,
    Edit,
    Delete,
    Link,
    User,
    Lock,
    Files,
    TrendCharts
  },
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 搜索关键词
      searchKeyword: '',
      // 总条数
      total: 0,
      // 动态数据源配置表格数据
      datasourceList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        name: null,
        url: null,

        dbName: null,
        username: null,
        password: null,
        driverClassName: null,
        type: null,
        status: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        name: [
          { required: true, message: "数据源名称不能为空", trigger: "blur" }
        ],
        url: [
          { required: true, message: "数据库连接地址不能为空", trigger: "blur" }
        ],
        port: [
          { required: true, message: "数据库端口号不能为空", trigger: "blur" }
        ],
        dbName: [
          { required: true, message: "数据库名称不能为空", trigger: "blur" }
        ],
        username: [
          { required: true, message: "数据库用户名不能为空", trigger: "blur" }
        ],
        password: [
          { required: true, message: "数据库密码不能为空", trigger: "blur" }
        ],
        driverClassName: [
          { required: true, message: "数据库驱动类名不能为空", trigger: "blur" }
        ],
      }
    };
  },
  computed: {
    activeCount() {
      return this.datasourceList.filter(item => item.status === 1).length;
    },
    dbTypeCount() {
      const types = new Set(this.datasourceList.map(item => item.type));
      return types.size;
    },
    hasActiveFilters() {
      return this.queryParams.type || this.searchKeyword;
    }
  },
  created() {
    this.getList();
  },
  methods: {
    /** 获取后端技术栈标签 */
    getBackendLabel(value) {
      return TechStackUtils.getBackendLabel(value)
    },
    /** 获取前端技术栈标签 */
    getFrontendLabel(value) {
      return TechStackUtils.getFrontendLabel(value)
    },
    /** 获取技术栈颜色 */
    getTechStackColor(type, value) {
      return TechStackUtils.getTechStackColor(type, value)
    },
    /** 查询动态数据源配置列表 */
    getList() {
      this.loading = true;
      dataSourceApi.listDatasource(this.queryParams).then(response => {
        this.datasourceList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    
    /** 分页处理 */
    handlePagination(pagination) {
      console.log('数据源分页事件:', pagination);
      this.queryParams.pageNum = pagination.page;
      this.queryParams.pageSize = pagination.limit;
      this.getList();
    },
    
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    
    /** 搜索处理 */
    handleSearch() {
      this.queryParams.name = this.searchKeyword;
      this.queryParams.dbName = this.searchKeyword;
      this.handleQuery();
    },
    
    /** 重置按钮操作 */
    resetQuery() {
      this.searchKeyword = '';
      this.queryParams = {
        pageNum: 1,
        pageSize: 10,
        name: null,
        url: null,
        port: null,
        dbName: null,
        username: null,
        password: null,
        driverClassName: null,
        type: null,
        status: null,
      };
      this.getList();
    },
    
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    
    // 表单重置
    reset() {
      this.form = {
        id: null,
        name: null,
        url: null,
        port: null,
        dbName: null,
        username: null,
        password: null,
        driverClassName: null,
        type: null,
        status: null,
        createTime: null,
        updateTime: null
      };
    },
    
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加动态数据源配置";
    },
    
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      dataSourceApi.getDatasource(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改动态数据源配置";
      });
    },
    
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            dataSourceApi.updateDatasource(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            dataSourceApi.addDatasource(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$modal.confirm('是否确认删除动态数据源配置编号为"' + ids + '"的数据项？').then(function() {
        return dataSourceApi.delDatasource(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    
    /** 测试连接 */
    testConnection(row) {
      this.$message({
        message: `正在测试 ${row.name} 的连接...`,
        type: 'info',
        duration: 2000
      });

      dataSourceApi.testConnection(row).then(response => {
        this.$message.success(response.msg);
      }).catch(error => {
        this.$message.error('连接测试失败：' + error.message);
      });
    },
    
    /** 切换数据源 */
    switchDataSource(row) {
      this.$confirm(`确认切换到数据源 "${row.name}" 吗？`, '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        dataSourceApi.switchDataSource(row.id).then(response => {
          this.$message.success(response.msg);
          this.$emit('dataSourceChanged', row);
        }).catch(error => {
          this.$message.error('数据源切换失败：' + error.message);
        });
      });
    },
    
    /** 更多操作处理 */
    handleMoreAction(command, row) {
      switch (command) {
        case 'switch':
          this.switchDataSource(row);
          break;
        case 'delete':
          this.handleDelete(row);
          break;
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
    
    /** 获取数据库类型图标组件 */
    getDbTypeIconComponent(type) {
      return this.$iconHelper.getDbTypeIconComponent(type);
    }
  }
};
</script>

<style lang="scss" scoped>
// V2 设计样式
.datasource-page-v2 {
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

      &.active-icon {
        background: #d1fae5;
        
        .el-icon {
          color: #10b981;
        }
      }

      &.type-icon {
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

  // 分页样式
  .pagination-wrapper {
    margin-top: 24px;
    display: flex;
    justify-content: center;
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

  &.el-button--danger {
    background: linear-gradient(135deg, #ef4444, #dc2626);
    border: none;
    
    &:hover {
      transform: translateY(-1px);
      box-shadow: 0 4px 12px rgba(239, 68, 68, 0.4);
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

  &.el-tag--warning {
    background: linear-gradient(135deg, #fef3c7, #fde68a);
    color: #d97706;
    border: 1px solid #fbbf24;
  }

  &.el-tag--danger {
    background: linear-gradient(135deg, #fee2e2, #fecaca);
    color: #dc2626;
    border: 1px solid #f87171;
  }

  &.el-tag--info {
    background: linear-gradient(135deg, #f3f4f6, #e5e7eb);
    color: #6b7280;
    border: 1px solid #d1d5db;
  }
}

// 表格样式优化
:deep(.el-table) {
  .el-table__header-wrapper {
    .el-table__header {
      th {
        background: linear-gradient(135deg, #f8fafc 0%, #f1f5f9 100%);
        color: #374151;
        font-weight: 600;
        border-bottom: 2px solid #e5e7eb;
      }
    }
  }

  .el-table__body-wrapper {
    .el-table__row {
      transition: background-color 0.2s ease;
      
      &:hover {
        background-color: #f9fafb;
      }
    }
  }
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
  .datasource-page-v2 {
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

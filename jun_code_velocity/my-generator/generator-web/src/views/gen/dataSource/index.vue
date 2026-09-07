<template>
  <div class="datasource-page">
    <div class="main-container">
      <!-- 页面标题区 -->
      <div class="page-header">
        <div class="header-content">
          <h1 class="page-title">
            <i class="el-icon-connection"></i>
            数据源管理
          </h1>
          <p class="page-description">统一管理数据库连接配置，支持多种数据库类型</p>
        </div>
      </div>

      <!-- 统计卡片区 -->
      <div class="stats-section">
        <div class="stat-card">
          <div class="stat-icon datasource-icon">
            <i class="el-icon-grid"></i>
          </div>
          <div class="stat-content">
            <div class="stat-title">数据源总数</div>
            <div class="stat-value">{{ total }}</div>
          </div>
        </div>
        <div class="stat-card">
          <div class="stat-icon active-icon">
            <i class="el-icon-data-line"></i>
          </div>
          <div class="stat-content">
            <div class="stat-title">活跃连接</div>
            <div class="stat-value">{{ activeCount }}</div>
          </div>
        </div>
        <div class="stat-card">
          <div class="stat-icon type-icon">
            <i class="el-icon-platform"></i>
          </div>
          <div class="stat-content">
            <div class="stat-title">支持类型</div>
            <div class="stat-value">{{ dbTypeCount }}</div>
          </div>
        </div>
      </div>

      <!-- 智能工具栏 -->
      <div class="smart-toolbar">
        <div class="toolbar-left">
          <template v-if="ids.length === 0">
            <el-button type="primary" size="small" icon="el-icon-plus" @click="handleAdd">新增数据源</el-button>
          </template>
          <template v-else>
            <div class="selection-info">
              <el-tag type="primary" size="small">
                <i class="el-icon-check"></i>
                已选择 {{ ids.length }} 项
              </el-tag>
            </div>
            <el-button type="success" size="small" :disabled="single" @click="handleUpdate" icon="el-icon-edit">修改配置</el-button>
            <el-button type="danger" size="small" :disabled="multiple" @click="handleDelete" icon="el-icon-delete">删除</el-button>
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

          <el-input v-model="searchKeyword" placeholder="搜索数据源名称或地址..." size="small" style="width: 240px; margin-right: 8px;" clearable @keyup.enter.native="handleSearch" @clear="handleSearch">
            <i slot="prefix" class="el-icon-search"></i>
          </el-input>

          <el-button v-if="hasActiveFilters" size="small" @click="resetQuery" icon="el-icon-refresh">重置</el-button>
        </div>
      </div>

      <!-- 数据表格 -->
      <div class="table-section">
        <el-table v-loading="loading" :data="datasourceList" @selection-change="handleSelectionChange" stripe>
          <el-table-column type="selection" width="50" align="center" />
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
              <el-tooltip content="编辑配置" placement="top">
                <el-button type="success" size="mini" circle icon="el-icon-edit" @click="handleUpdate(scope.row)"></el-button>
              </el-tooltip>
              <el-dropdown trigger="click" @command="(cmd) => handleMoreAction(cmd, scope.row)" style="margin-left: 4px;">
                <el-button type="info" size="mini" circle icon="el-icon-more"></el-button>
                <el-dropdown-menu slot="dropdown">
                  <el-dropdown-item command="switch">
                    <i class="el-icon-refresh"></i> 切换数据源
                  </el-dropdown-item>
                  <el-dropdown-item command="delete" divided>
                    <i class="el-icon-delete"></i> 删除数据源
                  </el-dropdown-item>
                </el-dropdown-menu>
              </el-dropdown>
            </template>
          </el-table-column>
        </el-table>

        <div class="pagination-wrapper">
          <pagination
            v-show="total > 0"
            :total="total"
            :page.sync="queryParams.pageNum"
            :limit.sync="queryParams.pageSize"
            @pagination="handlePagination"
          />
        </div>
      </div>
    </div>

    <!-- 添加/修改对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="700px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="100px">
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
          <el-select v-model="form.type" placeholder="请选择数据库类型" clearable style="width: 100%">
            <el-option label="MySQL" value="0" />
            <el-option label="Oracle" value="1" />
            <el-option label="SQL Server" value="2" />
            <el-option label="PostgreSQL" value="3" />
            <el-option label="ClickHouse" value="4" />
            <el-option label="SQLite" value="5" />
            <el-option label="DB2" value="6" />
            <el-option label="达梦" value="7" />
          </el-select>
        </el-form-item>
        <el-form-item label="数据源状态" prop="status">
          <el-radio-group v-model="form.status">
            <el-radio label="1">默认</el-radio>
            <el-radio label="2">正常</el-radio>
          </el-radio-group>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="cancel">取 消</el-button>
        <el-button type="primary" @click="submitForm">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listDatasource, getDatasource, addDatasource, updateDatasource, delDatasource, testConnection, switchDataSource } from "@/api/dataSource";

export default {
  name: "Datasource",
  data() {
    return {
      loading: true,
      ids: [],
      single: true,
      multiple: true,
      searchKeyword: '',
      total: 0,
      datasourceList: [],
      title: "",
      open: false,
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        name: null,
        url: null,
        dbName: null,
        username: null,
        type: null,
        status: null
      },
      form: {},
      rules: {
        name: [{ required: true, message: "数据源名称不能为空", trigger: "blur" }],
        url: [{ required: true, message: "数据库连接地址不能为空", trigger: "blur" }],
        port: [{ required: true, message: "数据库端口号不能为空", trigger: "blur" }],
        dbName: [{ required: true, message: "数据库名称不能为空", trigger: "blur" }],
        username: [{ required: true, message: "数据库用户名不能为空", trigger: "blur" }],
        password: [{ required: true, message: "数据库密码不能为空", trigger: "blur" }]
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
    getList() {
      this.loading = true;
      listDatasource(this.queryParams).then(response => {
        this.datasourceList = response.rows || [];
        this.total = response.total || 0;
        this.loading = false;
      }).catch(() => {
        this.loading = false;
      });
    },
    handlePagination(pagination) {
      this.queryParams.pageNum = pagination.page;
      this.queryParams.pageSize = pagination.limit;
      this.getList();
    },
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    handleSearch() {
      this.queryParams.name = this.searchKeyword;
      this.queryParams.dbName = this.searchKeyword;
      this.handleQuery();
    },
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
        type: null,
        status: null
      };
      this.getList();
    },
    cancel() {
      this.open = false;
      this.reset();
    },
    reset() {
      this.form = {
        id: null,
        name: null,
        url: null,
        port: null,
        dbName: null,
        username: null,
        password: null,
        type: null,
        status: null
      };
    },
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id);
      this.single = selection.length !== 1;
      this.multiple = !selection.length;
    },
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加数据源配置";
    },
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids[0];
      getDatasource(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改数据源配置";
      });
    },
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateDatasource(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addDatasource(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    handleDelete(row) {
      const ids = row.id || this.ids.join(',');
      this.$modal.confirm('是否确认删除数据源编号为"' + ids + '"的数据项？').then(function () {
        return delDatasource(row.id || this.ids[0]);
      }.bind(this)).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    testConnection(row) {
      this.$modal.msgSuccess("正在测试 " + row.name + " 的连接...");
      testConnection(row).then(response => {
        this.$modal.msgSuccess(response.msg || "连接测试成功");
      }).catch(error => {
        this.$modal.msgError("连接测试失败");
      });
    },
    handleMoreAction(command, row) {
      switch (command) {
        case 'switch':
          this.$confirm('确认切换到数据源 "' + row.name + '" 吗？', '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'
          }).then(() => {
            switchDataSource(row.id).then(response => {
              this.$modal.msgSuccess(response.msg || "切换成功");
              this.$emit('dataSourceChanged', row);
            });
          }).catch(() => {});
          break;
        case 'delete':
          this.handleDelete(row);
          break;
      }
    },
    getDbTypeName(type) {
      const names = {
        '0': 'MySQL', '1': 'Oracle', '2': 'SQL Server', '3': 'PostgreSQL',
        '4': 'ClickHouse', '5': 'SQLite', '6': 'DB2', '7': '达梦'
      };
      return names[type] || 'Unknown';
    },
    getDbTypeColor(type) {
      const colors = {
        '0': 'primary', '1': 'danger', '2': 'warning', '3': 'info',
        '4': 'success', '5': '', '6': 'warning', '7': 'danger'
      };
      return colors[type] || '';
    }
  }
};
</script>

<style scoped>
.datasource-page {
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
.stat-icon.active-icon { background: #d1fae5; }
.stat-icon.active-icon i { color: #10b981; }
.stat-icon.type-icon { background: #fef3c7; }
.stat-icon.type-icon i { color: #f59e0b; }
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
</style>
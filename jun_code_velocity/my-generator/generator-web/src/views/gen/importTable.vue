<template>
  <!-- 导入表 -->
  <el-dialog title="导入表" :visible.sync="visible" width="800px" top="5vh" append-to-body>
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true">
      <el-form-item label="表名称" prop="tableName">
        <el-input v-model="queryParams.tableName" placeholder="请输入表名称" clearable @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="表描述" prop="tableComment">
        <el-input v-model="queryParams.tableComment" placeholder="请输入表描述" clearable @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>
    <el-row>
      <el-table @row-click="clickRow" ref="table" :data="dbTableList" @selection-change="handleSelectionChange" height="260px">
        <el-table-column type="selection" width="55"></el-table-column>
        <el-table-column prop="tableName" label="表名称" :show-overflow-tooltip="true"></el-table-column>
        <el-table-column prop="tableComment" label="表描述" :show-overflow-tooltip="true"></el-table-column>
        <el-table-column prop="createTime" label="创建时间"></el-table-column>
        <el-table-column prop="updateTime" label="更新时间"></el-table-column>
      </el-table>
      <pagination
        v-show="total > 0"
        :total="total"
        :page.sync="queryParams.pageNum"
        :limit.sync="queryParams.pageSize"
        @pagination="handlePagination"
      />
    </el-row>
    <div slot="footer" class="dialog-footer">
      <el-button @click="visible = false">取 消</el-button>
      <el-button type="primary" @click="handleImportTable">确 定</el-button>
    </div>
  </el-dialog>
</template>

<script>
import { listDbTable, importTable } from "@/api/gen";
export default {
  data() {
    return {
      visible: false,
      tables: [],
      total: 0,
      dbTableList: [],
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        tableName: undefined,
        tableComment: undefined
      }
    };
  },
  methods: {
    show() {
      this.getList();
      this.visible = true;
    },
    clickRow(row) {
      this.$refs.table.toggleRowSelection(row);
    },
    handleSelectionChange(selection) {
      this.tables = selection.map(item => item);
    },
    getList() {
      const dataSourceId = localStorage.getItem('selectedDataSourceId');
      const params = {
        ...this.queryParams,
        pageNum: this.queryParams.pageNum || 1,
        pageSize: this.queryParams.pageSize || 10
      };
      if (dataSourceId && dataSourceId !== 'null') {
        params.dataSourceId = dataSourceId;
      }
      listDbTable(params).then(res => {
        if (res.code === 200) {
          this.dbTableList = res.rows || [];
          this.total = res.total || 0;
        }
      }).catch(() => {
        this.dbTableList = [];
        this.total = 0;
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
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    handleImportTable() {
      if (this.tables.length == 0) {
        this.$modal.msgError("请选择要导入的表");
        return;
      }
      const tableNames = this.tables.map(item => item.tableName).join(",");
      importTable({ tableNames: tableNames }).then(res => {
        this.$modal.msgSuccess(res.msg);
        if (res.code === 200) {
          this.visible = false;
          this.$emit("ok");
        }
      });
    }
  }
};
</script>
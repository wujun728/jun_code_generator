<template>
  <!-- 导入表 -->
  <el-dialog title="导入表" v-model="visible" width="800px" top="5vh" append-to-body>
    <el-form :model="queryParams" ref="queryForm" :inline="true">
      <el-form-item label="表名称" prop="tableName">
        <el-input
          v-model="queryParams.tableName"
          placeholder="请输入表名称"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="表描述" prop="tableComment">
        <el-input
          v-model="queryParams.tableComment"
          placeholder="请输入表描述"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
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
        v-show="total>0"
        :total="total"
        :page.sync="queryParams.pageNum"
        :limit.sync="queryParams.pageSize"
        @pagination="handlePagination"
        layout="total, sizes, prev, pager, next, jumper"
      />
    </el-row>
    <template #footer>
      <div class="dialog-footer">
        <el-button @click="visible = false">取 消</el-button>
        <el-button type="primary" @click="handleImportTable">确 定</el-button>
      </div>
    </template>
  </el-dialog>
</template>

<script>
import genApi from "@/api/gen";
import datasourceApi from "@/api/dataSource";
import pagination from "@/components/Pagination/index.vue";
export default {
  components: { pagination },
  data() {
    return {
      // 遮罩层
      visible: false,
      // 选中数组值
      tables: [],
      // 总条数
      total: 0,
      // 表数据
      dbTableList: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        tableName: undefined,
        tableComment: undefined
      }
    };
  },
  methods: {
    // 显示弹框
    show() {
      this.getList();
      this.visible = true;
    },
    clickRow(row) {
      this.$refs.table.toggleRowSelection(row);
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      // selection.map(item => item.tableName);
      this.tables = selection.map(item => item);
    },
    // 查询表数据
    getList: function () {
      const dataSourceId = localStorage.getItem('selectedDataSourceId');

      // 确保分页参数正确
      const params = {
        ...this.queryParams,
        pageNum: this.queryParams.pageNum || 1,
        pageSize: this.queryParams.pageSize || 10
      };

      console.log('导入表查询参数:', params);

      if (dataSourceId && dataSourceId !== 'null') {
        // 使用指定数据源查询
        datasourceApi.listDbTableWithDataSource(dataSourceId, params).then(res => {
          if (res.code === 200) {
            this.dbTableList = res.rows || [];
            this.total = res.total || 0;
            console.log('导入表查询结果:', { total: this.total, count: this.dbTableList.length });
          } else {
            this.$message.error(res.msg || '查询失败');
            this.dbTableList = [];
            this.total = 0;
          }
        }).catch(error => {
          console.error('查询导入表失败:', error);
          this.$message.error('查询失败，请检查数据源连接');
          this.dbTableList = [];
          this.total = 0;
        });
      } else {
        // 使用默认数据源查询
        this.$message.warning('请先选择数据源');
        this.dbTableList = [];
        this.total = 0;
      }
    },
    /** 分页处理 */
    handlePagination(pagination) {
      console.log('分页事件:', pagination);
      this.queryParams.pageNum = pagination.page;
      this.queryParams.pageSize = pagination.limit;
      this.getList();
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    /** 导入按钮操作 */
    handleImportTable() {
      if (this.tables.length == 0) {
        this.$message.error("请选择要导入的表");
        return;
      }
      this.tables.forEach(item => {
        genApi.addGenTable(item).then(res => {
          if (res.code === 200) {
            this.visible = false;
            this.$emit("ok");
          }
        });
      });

      // genApi.addGenTable()
      // datasourceApi.importTableWithDataSource(1,{tableNames}).then(res => {
      //   this.$message.success(res.msg);
      //   if (res.code === 200) {
      //     this.visible = false;
      //     this.$emit("ok");
      //   }
      // });
    }
  }
};
</script>

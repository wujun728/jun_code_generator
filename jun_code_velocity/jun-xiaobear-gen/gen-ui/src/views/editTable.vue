<template>
  <el-card class="app-container">
    <el-tabs v-model="activeName">
      <el-tab-pane label="基本信息" name="basic">
        <el-card>
          <basic-info-form ref="basicInfo" :info="info" />
        </el-card>
        <el-card class="mt10">
          <el-divider content-position="center">生成配置</el-divider>
          <gen-info-form ref="genInfo" :info="info" :tables="tables" :menus="menus"/>
        </el-card>
      </el-tab-pane>
      <el-tab-pane label="字段信息" name="columnInfo">
        <el-table ref="dragTable" :data="columns" row-key="columnId">
          <el-table-column label="序号" type="index" min-width="5%" class-name="allowDrag" />
          <el-table-column
            label="字段列名"
            prop="columnName"
            min-width="10%"
            :show-overflow-tooltip="true"
          />
          <el-table-column label="字段描述" min-width="10%">
            <template #default="scope">
              <el-input v-model="scope.row.columnComment"></el-input>
            </template>
          </el-table-column>
          <el-table-column
            label="物理类型"
            prop="columnType"
            min-width="10%"
            :show-overflow-tooltip="true"
          />
          <el-table-column label="Java类型" min-width="11%">
            <template #default="scope">
              <el-select v-model="scope.row.javaType">
                <el-option label="Long" value="Long" />
                <el-option label="String" value="String" />
                <el-option label="Integer" value="Integer" />
                <el-option label="Double" value="Double" />
                <el-option label="BigDecimal" value="BigDecimal" />
                <el-option label="Date" value="Date" />
              </el-select>
            </template>
          </el-table-column>
          <el-table-column label="java属性" min-width="10%">
            <template #default="scope">
              <el-input v-model="scope.row.javaField"></el-input>
            </template>
          </el-table-column>

          <el-table-column label="插入" min-width="5%">
            <template #default="scope">
              <el-checkbox true-label="1" v-model="scope.row.isInsert"></el-checkbox>
            </template>
          </el-table-column>
          <el-table-column label="编辑" min-width="5%">
            <template #default="scope">
              <el-checkbox true-label="1" v-model="scope.row.isEdit"></el-checkbox>
            </template>
          </el-table-column>
          <el-table-column label="列表" min-width="5%">
            <template #default="scope">
              <el-checkbox true-label="1" v-model="scope.row.isList"></el-checkbox>
            </template>
          </el-table-column>
          <el-table-column label="查询" min-width="5%">
            <template #default="scope">
              <el-checkbox true-label="1" v-model="scope.row.isQuery"></el-checkbox>
            </template>
          </el-table-column>
          <el-table-column label="查询方式" min-width="10%">
            <template #default="scope">
              <el-select v-model="scope.row.queryType">
                <el-option label="=" value="EQ" />
                <el-option label="!=" value="NE" />
                <el-option label=">" value="GT" />
                <el-option label=">=" value="GTE" />
                <el-option label="<" value="LT" />
                <el-option label="<=" value="LTE" />
                <el-option label="LIKE" value="LIKE" />
                <el-option label="BETWEEN" value="BETWEEN" />
              </el-select>
            </template>
          </el-table-column>
          <el-table-column label="必填" min-width="5%">
            <template #default="scope">
              <el-checkbox true-label="1" v-model="scope.row.isRequired"></el-checkbox>
            </template>
          </el-table-column>
          <el-table-column label="显示类型" min-width="12%">
            <template #default="scope">
              <el-select v-model="scope.row.htmlType">
                <el-option label="文本框" value="input" />
                <el-option label="文本域" value="textarea" />
                <el-option label="下拉框" value="select" />
                <el-option label="单选框" value="radio" />
                <el-option label="复选框" value="checkbox" />
                <el-option label="日期控件" value="datetime" />
                <el-option label="图片上传" value="imageUpload" />
                <el-option label="文件上传" value="fileUpload" />
                <el-option label="富文本控件" value="editor" />
              </el-select>
            </template>
          </el-table-column>
          <el-table-column label="字典类型" min-width="12%" v-if="info.isRuoYi === '1'">
            <template #default="scope">
              <el-select v-model="scope.row.dictType" clearable filterable placeholder="请选择">
                <el-option
                  v-for="dict in dictOptions"
                  :key="dict.dictType"
                  :label="dict.dictName"
                  :value="dict.dictType">
                  <span style="float: left">{{ dict.dictName }}</span>
                  <span style="float: right; color: #8492a6; font-size: 13px">{{ dict.dictType }}</span>
              </el-option>
              </el-select>
            </template>
          </el-table-column>
        </el-table>
      </el-tab-pane>
    </el-tabs>
    <el-form label-width="100px">
      <el-form-item style="text-align: center;margin-left:-100px;margin-top:10px;">
        <el-button type="primary" @click="submitForm()">提交</el-button>
        <el-button @click="close()">返回</el-button>
      </el-form-item>
    </el-form>
  </el-card>
</template>
<script>
import genApi from "@/api/gen";
import basicInfoForm from "./basicInfoForm";
import genInfoForm from "./genInfoForm";
import Sortable from 'sortablejs'

export default {
  name: "GenEdit",
  components: {
    basicInfoForm,
    genInfoForm
  },
  data() {
    return {
      // 选中选项卡的 name
      activeName: "columnInfo",
      // 表格的高度
      tableHeight: document.documentElement.scrollHeight - 245 + "px",
      // 表信息
      tables: [],
      // 表列信息
      columns: [],
      // 字典信息
      dictOptions: [],
      // 菜单信息
      menus: [],
      // 表详细信息
      info: {},
      // Sortable 实例
      sortableInstance: null
    };
  },
  created() {
    const tableId = this.$route.query && this.$route.query.tableId;
    if (tableId) {
      // 获取表详细信息
      genApi.getGenTable(tableId).then(res => {
        console.log('获取表详细信息响应:', res);
        if (res.data) {
          this.columns = res.data.rows || [];
          this.info = res.data.info || {};
          this.tables = res.data.tables || [];

          if (!this.columns || this.columns.length === 0) {
            this.$message.warning('该表没有字段信息，请检查表结构');
          } else {
            // 数据加载完成后，在下一个 tick 初始化 Sortable
            this.$nextTick(() => {
              this.initSortable();
            });
          }
        }
      }).catch(error => {
        console.error('获取表详细信息失败:', error);
        this.$message.error('获取表详细信息失败');
      });
    } else {
      this.$message.error('缺少表ID参数');
    }
  },
  methods: {
    /** 提交按钮 */
    submitForm() {
      const basicForm = this.$refs.basicInfo.$refs.basicInfoForm;
      const genForm = this.$refs.genInfo.$refs.genInfoForm;
      Promise.all([basicForm, genForm].map(this.getFormPromise)).then(res => {
        const validateResult = res.every(item => !!item);
        if (validateResult) {
          const genTable = Object.assign({}, basicForm.model, genForm.model);
          genTable.columns = this.columns;
          genTable.params = {
            treeCode: genTable.treeCode,
            treeName: genTable.treeName,
            treeParentCode: genTable.treeParentCode,
            parentMenuId: genTable.parentMenuId
          };
          genApi.updateGenTable(genTable).then(res => {
            this.$message.success(res.msg);
            if (res.code === 200) {
              this.close();
            }
          });
        } else {
          this.$message.error("表单校验未通过，请重新检查提交内容");
        }
      });
    },
    getFormPromise(form) {
      return new Promise(resolve => {
        form.validate(res => {
          resolve(res);
        });
      });
    },
    /** 关闭按钮 */
    close() {
      // 获取当前路由的页码参数，如果没有则默认为1
      const pageNum = this.$route.query.pageNum || 1;
      const pageSize = this.$route.query.pageSize || 50;

      console.log('返回代码生成页面，页码参数:', {pageNum, pageSize});

      // 跳转到代码生成页面，保持分页状态
      this.$router.push({
        path: "/generate/index",
        query: {
          t: Date.now(),
          pageNum: pageNum,
          pageSize: pageSize
        }
      }).then(() => {
        // 强制刷新
        this.$router.go(0);
      });
    },
    /** 初始化拖拽排序 */
    initSortable() {
      try {
        // 检查 dragTable ref 是否存在
        if (!this.$refs.dragTable) {
          console.warn('dragTable ref not found, retrying...');
          // 如果 ref 不存在，延迟重试
          setTimeout(() => {
            this.initSortable();
          }, 100);
          return;
        }

        // 获取表格的 tbody 元素
        const tableEl = this.$refs.dragTable.$el;
        if (!tableEl) {
          console.warn('Table element not found');
          return;
        }

        const tbodyEl = tableEl.querySelector(".el-table__body-wrapper > table > tbody");
        if (!tbodyEl) {
          console.warn('Table tbody not found, table might be empty');
          return;
        }

        // 如果已经存在实例，先销毁
        if (this.sortableInstance) {
          this.sortableInstance.destroy();
        }

        // 创建 Sortable 实例
        this.sortableInstance = Sortable.create(tbodyEl, {
          handle: ".allowDrag",
          animation: 150,
          ghostClass: 'sortable-ghost',
          chosenClass: 'sortable-chosen',
          dragClass: 'sortable-drag',
          onEnd: evt => {
            try {
              // 确保 columns 数组存在且有数据
              if (!this.columns || this.columns.length === 0) {
                console.warn('Columns array is empty');
                return;
              }

              // 执行拖拽排序
              const targetRow = this.columns.splice(evt.oldIndex, 1)[0];
              this.columns.splice(evt.newIndex, 0, targetRow);

              // 更新排序字段
              for (let index in this.columns) {
                this.columns[index].sort = parseInt(index) + 1;
              }

              console.log('Column order updated');
            } catch (error) {
              console.error('Error in sortable onEnd:', error);
            }
          }
        });

        console.log('Sortable initialized successfully');
      } catch (error) {
        console.error('Error initializing sortable:', error);
      }
    },
  },
  mounted() {
    // 使用 nextTick 确保 DOM 完全渲染后再初始化 Sortable
    this.$nextTick(() => {
      this.initSortable();
    });
  },

  beforeUnmount() {
    // 清理 Sortable 实例
    if (this.sortableInstance) {
      try {
        this.sortableInstance.destroy();
        this.sortableInstance = null;
        console.log('Sortable instance destroyed');
      } catch (error) {
        console.warn('Error destroying sortable instance:', error);
      }
    }
  }
}
</script>

<style lang="scss" scoped>
// 拖拽排序样式
:deep(.el-table) {
  .allowDrag {
    cursor: move;
  }

  .sortable-ghost {
    opacity: 0.5;
    background: #f0f9ff;
  }

  .sortable-chosen {
    background: #e0f2fe;
  }

  .sortable-drag {
    background: #ffffff;
    box-shadow: 0 4px 12px rgba(0, 0, 0, 0.15);
  }
}

// 表单样式优化
.el-card {
  margin-bottom: 20px;
  border-radius: 8px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);

  &.mt10 {
    margin-top: 10px;
  }
}

.el-tabs {
  :deep(.el-tabs__header) {
    margin-bottom: 20px;
  }

  :deep(.el-tabs__nav-wrap::after) {
    background-color: #e4e7ed;
  }
}

.el-table {
  :deep(.el-table__header) {
    background-color: #f8f9fa;

    th {
      background-color: #f8f9fa;
      color: #606266;
      font-weight: 600;
    }
  }

  :deep(.el-table__row) {
    &:hover {
      background-color: #f5f7fa;
    }
  }
}

.el-form-item {
  margin-bottom: 20px;

  :deep(.el-form-item__label) {
    font-weight: 500;
    color: #606266;
  }
}

// 按钮样式
.el-button {
  border-radius: 6px;
  font-weight: 500;

  &.el-button--primary {
    background: linear-gradient(135deg, #409eff, #66b1ff);
    border: none;

    &:hover {
      background: linear-gradient(135deg, #66b1ff, #409eff);
      transform: translateY(-1px);
      box-shadow: 0 4px 12px rgba(64, 158, 255, 0.3);
    }
  }
}
</style>

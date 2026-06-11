<template>
  <div class="modern-table" :class="{ 'table-loading': loading }">
    <!-- 表格头部操作区域 -->
    <div class="table-header" v-if="showHeader">
      <div class="table-title">
        <h3 v-if="title">{{ title }}</h3>
        <span class="table-count" v-if="showCount && data.length">
          共 {{ data.length }} 条记录
        </span>
      </div>
      <div class="table-actions">
        <slot name="actions"></slot>
      </div>
    </div>

    <!-- 表格主体 -->
    <div class="table-container">
      <el-table
        ref="table"
        :data="data"
        :loading="loading"
        :stripe="stripe"
        :border="border"
        :size="size"
        :height="height"
        :max-height="maxHeight"
        :empty-text="emptyText"
        class="modern-el-table"
        @selection-change="handleSelectionChange"
        @sort-change="handleSortChange"
        @row-click="handleRowClick"
        v-bind="$attrs"
      >
        <slot></slot>
      </el-table>
    </div>

    <!-- 分页组件 -->
    <div class="table-pagination" v-if="showPagination && total > 0">
      <el-pagination
        :current-page="currentPage"
        :page-size="pageSize"
        :total="total"
        :page-sizes="pageSizes"
        :layout="paginationLayout"
        :background="true"
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
      />
    </div>
  </div>
</template>

<script>
export default {
  name: 'ModernTable',
  inheritAttrs: false,
  props: {
    // 表格数据
    data: {
      type: Array,
      default: () => []
    },
    // 表格标题
    title: {
      type: String,
      default: ''
    },
    // 是否显示头部
    showHeader: {
      type: Boolean,
      default: true
    },
    // 是否显示记录数
    showCount: {
      type: Boolean,
      default: true
    },
    // 加载状态
    loading: {
      type: Boolean,
      default: false
    },
    // 表格配置
    stripe: {
      type: Boolean,
      default: true
    },
    border: {
      type: Boolean,
      default: false
    },
    size: {
      type: String,
      default: 'default'
    },
    height: {
      type: [String, Number],
      default: ''
    },
    maxHeight: {
      type: [String, Number],
      default: ''
    },
    emptyText: {
      type: String,
      default: '暂无数据'
    },
    // 分页配置
    showPagination: {
      type: Boolean,
      default: true
    },
    total: {
      type: Number,
      default: 0
    },
    currentPage: {
      type: Number,
      default: 1
    },
    pageSize: {
      type: Number,
      default: 20
    },
    pageSizes: {
      type: Array,
      default: () => [10, 20, 50, 100]
    },
    paginationLayout: {
      type: String,
      default: 'total, sizes, prev, pager, next, jumper'
    }
  },
  methods: {
    handleSelectionChange(selection) {
      this.$emit('selection-change', selection)
    },
    
    handleSortChange(sort) {
      this.$emit('sort-change', sort)
    },
    
    handleRowClick(row, column, event) {
      this.$emit('row-click', row, column, event)
    },
    
    handleSizeChange(size) {
      this.$emit('update:pageSize', size)
      this.$emit('pagination-change', { page: this.currentPage, size })
    },
    
    handleCurrentChange(page) {
      this.$emit('update:currentPage', page)
      this.$emit('pagination-change', { page, size: this.pageSize })
    },
    
    // 暴露表格方法
    clearSelection() {
      this.$refs.table.clearSelection()
    },
    
    toggleRowSelection(row, selected) {
      this.$refs.table.toggleRowSelection(row, selected)
    },
    
    toggleAllSelection() {
      this.$refs.table.toggleAllSelection()
    },
    
    setCurrentRow(row) {
      this.$refs.table.setCurrentRow(row)
    },
    
    clearSort() {
      this.$refs.table.clearSort()
    }
  }
}
</script>

<style lang="scss" scoped>
.modern-table {
  background: var(--bg-primary);
  border-radius: var(--border-radius-large);
  overflow: hidden;
  box-shadow: var(--shadow-base);

  .table-header {
    display: flex;
    align-items: center;
    justify-content: space-between;
    padding: var(--spacing-lg);
    border-bottom: 1px solid var(--border-light);
    background: var(--bg-secondary);

    .table-title {
      display: flex;
      align-items: center;
      gap: var(--spacing-md);

      h3 {
        margin: 0;
        font-size: var(--font-xl);
        font-weight: var(--font-semibold);
        color: var(--text-primary);
      }

      .table-count {
        font-size: var(--font-sm);
        color: var(--text-secondary);
        background: var(--bg-tertiary);
        padding: 4px 8px;
        border-radius: var(--border-radius-small);
      }
    }

    .table-actions {
      display: flex;
      align-items: center;
      gap: var(--spacing-sm);
    }
  }

  .table-container {
    position: relative;
    overflow: hidden;
  }

  .table-pagination {
    padding: var(--spacing-lg);
    border-top: 1px solid var(--border-light);
    background: var(--bg-secondary);
    display: flex;
    justify-content: center;
  }

  &.table-loading {
    pointer-events: none;
  }
}

// Element Plus 表格样式覆盖
:deep(.modern-el-table) {
  --el-table-border-color: var(--border-light);
  --el-table-border: 1px solid var(--border-light);
  --el-table-text-color: var(--text-primary);
  --el-table-header-text-color: var(--text-primary);
  --el-table-header-bg-color: var(--bg-tertiary);
  --el-table-bg-color: var(--bg-primary);
  --el-table-current-row-bg-color: var(--bg-tertiary);
  --el-table-row-hover-bg-color: var(--bg-hover);
  --el-table-expanded-cell-bg-color: var(--bg-primary);

  .el-table__header-wrapper {
    .el-table__header {
      th {
        background: var(--bg-tertiary);
        border-bottom: 2px solid var(--border-base);
        font-weight: var(--font-semibold);
        font-size: var(--font-sm);
        color: var(--text-primary);

        .cell {
          padding: 12px 8px;
        }
      }
    }
  }

  .el-table__body-wrapper {
    .el-table__body {
      tr {
        transition: var(--transition-fast);

        td {
          border-bottom: 1px solid var(--border-light);
          font-size: var(--font-sm);

          .cell {
            padding: 12px 8px;
            line-height: 1.5;
          }
        }

        &:hover {
          background-color: var(--bg-hover) !important;
        }

        &.el-table__row--striped {
          background-color: var(--bg-secondary);

          &:hover {
            background-color: var(--bg-hover) !important;
          }
        }
      }
    }
  }

  .el-table__empty-block {
    background: var(--bg-primary);
    
    .el-table__empty-text {
      color: var(--text-secondary);
      font-size: var(--font-base);
    }
  }
}

// 深色主题适配
[data-theme="dark"] {
  .modern-table {
    background: var(--bg-secondary);
    
    .table-header,
    .table-pagination {
      background: var(--bg-tertiary);
      border-color: var(--border-base);
    }

    .table-header .table-title .table-count {
      background: var(--bg-secondary);
    }
  }
}
</style>
<template>
  <div :class="{'hidden':hidden}" class="pagination-container">
    <el-pagination
      :background="background"
      :current-page.sync="currentPage"
      :page-size.sync="pageSize"
      :layout="layout"
      :page-sizes="pageSizes"
      :total="total"
      v-bind="$attrs"
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
    />
  </div>
</template>

<script>
import { scrollTo } from '@/utils/scroll-to'

export default {
  name: 'Pagination',
  props: {
    total: {
      required: true,
      type: Number
    },
    page: {
      type: Number,
      default: 1
    },
    limit: {
      type: Number,
      default: 10
    },
    pageSizes: {
      type: Array,
      default () {
        return [5, 10, 20, 30, 50]
      }
    },
    layout: {
      type: String,
      default: 'prev, pager, next'
    },
    background: {
      type: Boolean,
      default: true
    },
    autoScroll: {
      type: Boolean,
      default: true
    },
    hidden: {
      type: Boolean,
      default: false
    }
  },
  computed: {
    currentPage: {
      get () {
        return this.page
      },
      set (val) {
        this.$emit('update:page', val)
      }
    },
    pageSize: {
      get () {
        return this.limit
      },
      set (val) {
        this.$emit('update:limit', val)
      }
    }
  },
  methods: {
    handleSizeChange (val) {
      this.$emit('pagination', { page: this.currentPage, limit: val })
      if (this.autoScroll) {
        scrollTo(0, 800)
      }
    },
    handleCurrentChange (val) {
      this.$emit('pagination', { page: val, limit: this.pageSize })
      if (this.autoScroll) {
        scrollTo(0, 800)
      }
    }
  }
}
</script>

<style scoped lang="scss">
.pagination-container {
  background: transparent;
  padding: var(--spacing-lg) 0;
  text-align: center;

  &.hidden {
    display: none;
  }

  :deep(.el-pagination) {
    .el-pagination__total {
      color: var(--text-secondary);
      font-weight: 500;
    }

    .el-pagination__sizes {
      .el-select .el-input {
        .el-input__inner {
          border-radius: var(--border-radius-base);
          border: 1px solid var(--border-base);

          &:focus {
            border-color: var(--primary-color);
          }
        }
      }
    }

    .el-pager {
      li {
        border-radius: var(--border-radius-base);
        margin: 0 2px;
        transition: all 0.3s ease;

        &:hover {
          background: rgba(245, 112, 112, 0.1);
          color: var(--primary-color);
        }

        &.active {
          background: var(--primary-gradient);
          color: white;
          border: none;
        }
      }
    }

    .btn-prev,
    .btn-next {
      border-radius: var(--border-radius-base);
      margin: 0 2px;
      transition: all 0.3s ease;

      &:hover {
        background: rgba(245, 112, 112, 0.1);
        color: var(--primary-color);
      }

      &:disabled {
        background: var(--bg-tertiary);
        color: var(--text-placeholder);
      }
    }

    .el-pagination__jump {
      color: var(--text-secondary);

      .el-input {
        .el-input__inner {
          border-radius: var(--border-radius-base);
          border: 1px solid var(--border-base);

          &:focus {
            border-color: var(--primary-color);
          }
        }
      }
    }
  }
}
</style>

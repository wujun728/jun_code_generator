<template>
  <div class="stat-card" :class="[`stat-${type}`, { 'stat-elevated': elevated }]">
    <div class="stat-header">
      <div class="stat-icon" :class="`icon-${type}`">
        <el-icon><component :is="icon" /></el-icon>
      </div>
      <div class="stat-actions" v-if="$slots.actions">
        <slot name="actions"></slot>
      </div>
    </div>
    
    <div class="stat-content">
      <div class="stat-value">
        <span class="stat-number" :class="{ 'loading': loading }">
          {{ loading ? '...' : formattedValue }}
        </span>
        <span class="stat-unit" v-if="unit">{{ unit }}</span>
      </div>
      <div class="stat-label">{{ label }}</div>
      
      <div class="stat-trend" v-if="trend !== null">
        <span class="trend-icon" :class="[
          trend > 0 ? 'trend-up' : trend < 0 ? 'trend-down' : 'trend-stable'
        ]">
          <el-icon v-if="trend > 0"><CaretTop /></el-icon>
          <el-icon v-else-if="trend < 0"><CaretBottom /></el-icon>
          <el-icon v-else><Minus /></el-icon>
        </span>
        <span class="trend-value">{{ Math.abs(trend) }}%</span>
        <span class="trend-label">{{ trendLabel }}</span>
      </div>
    </div>
  </div>
</template>

<script>
import { CaretTop, CaretBottom, Minus } from '@element-plus/icons-vue'

export default {
  name: 'StatCard',
  components: {
    CaretTop,
    CaretBottom,
    Minus
  },
  props: {
    label: {
      type: String,
      required: true
    },
    value: {
      type: [Number, String],
      required: true
    },
    unit: {
      type: String,
      default: ''
    },
    icon: {
      type: String,
      required: true
    },
    type: {
      type: String,
      default: 'default',
      validator: value => ['default', 'primary', 'success', 'warning', 'danger', 'info'].includes(value)
    },
    trend: {
      type: Number,
      default: null
    },
    trendLabel: {
      type: String,
      default: '较上周'
    },
    loading: {
      type: Boolean,
      default: false
    },
    elevated: {
      type: Boolean,
      default: true
    },
    formatter: {
      type: Function,
      default: null
    }
  },
  computed: {
    formattedValue() {
      if (this.formatter && typeof this.formatter === 'function') {
        return this.formatter(this.value)
      }
      
      if (typeof this.value === 'number') {
        if (this.value >= 1000000) {
          return (this.value / 1000000).toFixed(1) + 'M'
        } else if (this.value >= 1000) {
          return (this.value / 1000).toFixed(1) + 'K'
        }
        return this.value.toLocaleString()
      }
      
      return this.value
    }
  }
}
</script>

<style lang="scss" scoped>
.stat-card {
  background: var(--bg-primary);
  border: 1px solid var(--border-light);
  border-radius: var(--border-radius-large);
  padding: var(--spacing-lg);
  transition: var(--transition-fast);
  position: relative;
  overflow: hidden;

  &::before {
    content: '';
    position: absolute;
    top: 0;
    left: 0;
    right: 0;
    height: 4px;
    background: var(--border-light);
    transition: var(--transition-fast);
  }

  &.stat-elevated {
    box-shadow: var(--shadow-base);

    &:hover {
      box-shadow: var(--shadow-medium);
      transform: translateY(-2px);
    }
  }

  // 卡片类型样式
  &.stat-primary::before {
    background: var(--primary-color);
  }

  &.stat-success::before {
    background: var(--success-color);
  }

  &.stat-warning::before {
    background: var(--warning-color);
  }

  &.stat-danger::before {
    background: var(--danger-color);
  }

  &.stat-info::before {
    background: var(--info-color);
  }

  .stat-header {
    display: flex;
    align-items: center;
    justify-content: space-between;
    margin-bottom: var(--spacing-md);

    .stat-icon {
      width: 48px;
      height: 48px;
      border-radius: var(--border-radius-base);
      display: flex;
      align-items: center;
      justify-content: center;
      font-size: 24px;
      color: white;

      &.icon-default {
        background: var(--color-gray-500);
      }

      &.icon-primary {
        background: var(--primary-color);
      }

      &.icon-success {
        background: var(--success-color);
      }

      &.icon-warning {
        background: var(--warning-color);
      }

      &.icon-danger {
        background: var(--danger-color);
      }

      &.icon-info {
        background: var(--info-color);
      }
    }

    .stat-actions {
      display: flex;
      align-items: center;
      gap: var(--spacing-xs);
    }
  }

  .stat-content {
    .stat-value {
      display: flex;
      align-items: baseline;
      gap: var(--spacing-xs);
      margin-bottom: var(--spacing-xs);

      .stat-number {
        font-size: var(--font-3xl);
        font-weight: var(--font-bold);
        color: var(--text-primary);
        line-height: 1;

        &.loading {
          opacity: 0.5;
        }
      }

      .stat-unit {
        font-size: var(--font-base);
        color: var(--text-secondary);
        font-weight: var(--font-medium);
      }
    }

    .stat-label {
      font-size: var(--font-sm);
      color: var(--text-secondary);
      margin-bottom: var(--spacing-sm);
      font-weight: var(--font-medium);
    }

    .stat-trend {
      display: flex;
      align-items: center;
      gap: var(--spacing-xs);
      font-size: var(--font-sm);

      .trend-icon {
        display: flex;
        align-items: center;
        justify-content: center;
        width: 20px;
        height: 20px;
        border-radius: 50%;
        font-size: 12px;

        &.trend-up {
          background: var(--color-success-light);
          color: var(--success-color);
        }

        &.trend-down {
          background: var(--color-error-light);
          color: var(--danger-color);
        }

        &.trend-stable {
          background: var(--color-gray-100);
          color: var(--color-gray-500);
        }
      }

      .trend-value {
        font-weight: var(--font-semibold);

        .trend-up + & {
          color: var(--success-color);
        }

        .trend-down + & {
          color: var(--danger-color);
        }

        .trend-stable + & {
          color: var(--color-gray-500);
        }
      }

      .trend-label {
        color: var(--text-tertiary);
      }
    }
  }
}

// 深色主题适配
[data-theme="dark"] {
  .stat-card {
    background: var(--bg-secondary);
    border-color: var(--border-base);

    .stat-trend {
      .trend-icon {
        &.trend-stable {
          background: var(--color-gray-700);
          color: var(--color-gray-400);
        }
      }
    }
  }
}
</style>
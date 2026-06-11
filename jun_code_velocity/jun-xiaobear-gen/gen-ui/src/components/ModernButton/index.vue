<template>
  <button
    class="modern-button"
    :class="[
      `btn-${type}`,
      `btn-${size}`,
      {
        'btn-loading': loading,
        'btn-disabled': disabled,
        'btn-block': block,
        'btn-icon-only': iconOnly
      }
    ]"
    :disabled="disabled || loading"
    @click="handleClick"
  >
    <span class="btn-loading-icon" v-if="loading">
      <el-icon><Loading /></el-icon>
    </span>
    <span class="btn-icon" v-if="icon && !loading">
      <el-icon><component :is="icon" /></el-icon>
    </span>
    <span class="btn-text" v-if="!iconOnly">
      <slot></slot>
    </span>
  </button>
</template>

<script>
import { Loading } from '@element-plus/icons-vue'

export default {
  name: 'ModernButton',
  components: {
    Loading
  },
  props: {
    type: {
      type: String,
      default: 'default',
      validator: value => ['default', 'primary', 'success', 'warning', 'danger', 'info', 'text'].includes(value)
    },
    size: {
      type: String,
      default: 'medium',
      validator: value => ['small', 'medium', 'large'].includes(value)
    },
    icon: {
      type: String,
      default: ''
    },
    loading: {
      type: Boolean,
      default: false
    },
    disabled: {
      type: Boolean,
      default: false
    },
    block: {
      type: Boolean,
      default: false
    },
    iconOnly: {
      type: Boolean,
      default: false
    }
  },
  methods: {
    handleClick(event) {
      if (!this.disabled && !this.loading) {
        this.$emit('click', event)
      }
    }
  }
}
</script>

<style lang="scss" scoped>
.modern-button {
  display: inline-flex;
  align-items: center;
  justify-content: center;
  gap: var(--spacing-xs);
  font-family: inherit;
  font-weight: var(--font-medium);
  text-align: center;
  vertical-align: middle;
  cursor: pointer;
  user-select: none;
  border: 1px solid transparent;
  transition: var(--transition-fast);
  white-space: nowrap;
  text-decoration: none;
  position: relative;
  overflow: hidden;

  &:focus {
    outline: none;
    box-shadow: 0 0 0 3px rgba(0, 102, 255, 0.2);
  }

  &:active {
    transform: translateY(1px);
  }

  // 按钮尺寸
  &.btn-small {
    padding: 6px 12px;
    font-size: var(--font-sm);
    border-radius: var(--border-radius-small);
    height: 32px;
    
    &.btn-icon-only {
      width: 32px;
      padding: 0;
    }
  }

  &.btn-medium {
    padding: 8px 16px;
    font-size: var(--font-base);
    border-radius: var(--border-radius-small);
    height: 40px;
    
    &.btn-icon-only {
      width: 40px;
      padding: 0;
    }
  }

  &.btn-large {
    padding: 12px 24px;
    font-size: var(--font-lg);
    border-radius: var(--border-radius-base);
    height: 48px;
    
    &.btn-icon-only {
      width: 48px;
      padding: 0;
    }
  }

  // 按钮类型
  &.btn-default {
    background-color: var(--bg-primary);
    color: var(--text-primary);
    border-color: var(--border-light);

    &:hover:not(.btn-disabled) {
      background-color: var(--bg-tertiary);
      border-color: var(--border-base);
    }
  }

  &.btn-primary {
    background-color: var(--primary-color);
    color: white;
    border-color: var(--primary-color);

    &:hover:not(.btn-disabled) {
      background-color: var(--primary-hover);
      border-color: var(--primary-hover);
    }
  }

  &.btn-success {
    background-color: var(--success-color);
    color: white;
    border-color: var(--success-color);

    &:hover:not(.btn-disabled) {
      background-color: #00A85F;
      border-color: #00A85F;
    }
  }

  &.btn-warning {
    background-color: var(--warning-color);
    color: white;
    border-color: var(--warning-color);

    &:hover:not(.btn-disabled) {
      background-color: #E68900;
      border-color: #E68900;
    }
  }

  &.btn-danger {
    background-color: var(--danger-color);
    color: white;
    border-color: var(--danger-color);

    &:hover:not(.btn-disabled) {
      background-color: #FF3030;
      border-color: #FF3030;
    }
  }

  &.btn-info {
    background-color: var(--info-color);
    color: white;
    border-color: var(--info-color);

    &:hover:not(.btn-disabled) {
      background-color: #1677FF;
      border-color: #1677FF;
    }
  }

  &.btn-text {
    background-color: transparent;
    color: var(--primary-color);
    border-color: transparent;

    &:hover:not(.btn-disabled) {
      background-color: var(--color-primary-50);
      color: var(--primary-hover);
    }
  }

  // 按钮状态
  &.btn-loading {
    cursor: not-allowed;
    opacity: 0.8;

    .btn-loading-icon {
      animation: spin 1s linear infinite;
    }
  }

  &.btn-disabled {
    cursor: not-allowed;
    opacity: 0.5;
    transform: none !important;
  }

  &.btn-block {
    width: 100%;
  }

  // 图标样式
  .btn-icon,
  .btn-loading-icon {
    display: flex;
    align-items: center;
    justify-content: center;
    font-size: 1em;
  }

  .btn-text {
    line-height: 1;
  }
}

@keyframes spin {
  0% { transform: rotate(0deg); }
  100% { transform: rotate(360deg); }
}

// 深色主题适配
[data-theme="dark"] {
  .modern-button {
    &.btn-default {
      background-color: var(--bg-secondary);
      color: var(--text-primary);
      border-color: var(--border-base);

      &:hover:not(.btn-disabled) {
        background-color: var(--bg-tertiary);
        border-color: var(--border-dark);
      }
    }

    &.btn-text {
      &:hover:not(.btn-disabled) {
        background-color: rgba(0, 102, 255, 0.1);
      }
    }
  }
}
</style>
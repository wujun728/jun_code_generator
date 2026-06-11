<template>
  <div class="modern-card" :class="[
    `card-${type}`,
    { 'card-hover': hover, 'card-interactive': interactive, 'card-elevated': elevated }
  ]">
    <div class="card-header" v-if="title || $slots.header">
      <slot name="header">
        <div class="card-title-wrapper">
          <div class="card-icon" v-if="icon">
            <el-icon><component :is="icon" /></el-icon>
          </div>
          <h3 class="card-title">{{ title }}</h3>
        </div>
        <div class="card-actions" v-if="$slots.actions">
          <slot name="actions"></slot>
        </div>
      </slot>
    </div>
    
    <div class="card-body" :class="{ 'no-padding': noPadding }">
      <slot></slot>
    </div>
    
    <div class="card-footer" v-if="$slots.footer">
      <slot name="footer"></slot>
    </div>
  </div>
</template>

<script>
export default {
  name: 'ModernCard',
  props: {
    title: {
      type: String,
      default: ''
    },
    icon: {
      type: String,
      default: ''
    },
    type: {
      type: String,
      default: 'default',
      validator: value => ['default', 'primary', 'success', 'warning', 'danger', 'info'].includes(value)
    },
    hover: {
      type: Boolean,
      default: true
    },
    interactive: {
      type: Boolean,
      default: false
    },
    elevated: {
      type: Boolean,
      default: false
    },
    noPadding: {
      type: Boolean,
      default: false
    }
  }
}
</script>

<style lang="scss" scoped>
.modern-card {
  background: var(--bg-card);
  border-radius: var(--border-radius-large);
  border: 1px solid var(--border-color-light);
  transition: var(--transition-fast);
  overflow: hidden;
  position: relative;
  /* 弱化卡片感 - 去掉厚重阴影 */
  box-shadow: none;

  &::before {
    content: '';
    position: absolute;
    top: 0;
    left: 0;
    right: 0;
    height: 2px;
    background: var(--border-color-lighter);
    transition: var(--transition-fast);
  }

  &.card-hover:hover {
    border-color: var(--border-color);
    background: var(--bg-color-soft);
  }

  &.card-interactive {
    cursor: pointer;

    &:active {
      transform: translateY(-1px);
    }
  }

  &.card-elevated {
    box-shadow: var(--shadow-large);
  }

  // 卡片类型样式
  &.card-primary::before {
    background: var(--color-primary);
  }

  &.card-success::before {
    background: var(--success-color);
  }

  &.card-warning::before {
    background: var(--warning-color);
  }

  &.card-danger::before {
    background: var(--danger-color);
  }

  &.card-info::before {
    background: var(--info-color);
  }

  .card-header {
    padding: var(--spacing-lg);
    /* 弱化边框 - 使用更淡的分割线 */
    border-bottom: 1px solid var(--border-color-lighter);
    background: transparent;
    display: flex;
    align-items: center;
    justify-content: space-between;

    .card-title-wrapper {
      display: flex;
      align-items: center;
      gap: var(--spacing-sm);

      .card-icon {
        width: 40px;
        height: 40px;
        display: flex;
        align-items: center;
        justify-content: center;
        background: var(--color-primary);
        border-radius: var(--border-radius-base);
        color: white;
        font-size: 18px;
      }

      .card-title {
        margin: 0;
        font-size: var(--font-xl);
        font-weight: var(--font-semibold);
        color: var(--text-primary);
      }
    }

    .card-actions {
      display: flex;
      align-items: center;
      gap: var(--spacing-sm);
    }
  }

  .card-body {
    padding: var(--spacing-lg);

    &.no-padding {
      padding: 0;
    }
    
    /* 增加间距和呼吸感 */
    &.spacious {
      padding: calc(var(--spacing-lg) * 1.5);
    }
  }

  .card-footer {
    padding: var(--spacing-md) var(--spacing-lg);
    border-top: 1px solid var(--border-light);
    background: var(--bg-tertiary);
    display: flex;
    justify-content: flex-end;
    gap: var(--spacing-sm);
  }
}

// 深色主题适配
[data-theme="dark"] {
  .modern-card {
    background: var(--bg-secondary);
    border-color: var(--border-base);

    .card-header {
      background: var(--bg-tertiary);
      border-bottom-color: var(--border-base);
    }

    .card-footer {
      background: var(--bg-tertiary);
      border-top-color: var(--border-base);
    }
  }
}
</style>
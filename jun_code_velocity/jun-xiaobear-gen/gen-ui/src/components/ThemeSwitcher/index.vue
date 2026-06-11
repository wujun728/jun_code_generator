<template>
  <div class="theme-switcher">
    <el-tooltip 
      :content="isDark ? '切换到浅色模式' : '切换到深色模式'" 
      placement="bottom"
    >
      <button 
        class="theme-toggle-btn"
        @click="toggleTheme"
        :aria-label="isDark ? '切换到浅色模式' : '切换到深色模式'"
      >
        <transition name="theme-icon" mode="out-in">
          <el-icon v-if="isDark" key="moon"><Moon /></el-icon>
          <el-icon v-else key="sunny"><Sunny /></el-icon>
        </transition>
      </button>
    </el-tooltip>
  </div>
</template>

<script>
import { Moon, Sunny } from '@element-plus/icons-vue'

export default {
  name: 'ThemeSwitcher',
  components: {
    Moon,
    Sunny
  },
  data() {
    return {
      isDark: false
    }
  },
  mounted() {
    // 初始化主题
    this.initTheme()
  },
  methods: {
    initTheme() {
      // 检查本地存储的主题设置
      const savedTheme = localStorage.getItem('theme')
      
      if (savedTheme) {
        this.isDark = savedTheme === 'dark'
      } else {
        // 如果没有保存的设置，检查系统偏好
        this.isDark = window.matchMedia && window.matchMedia('(prefers-color-scheme: dark)').matches
      }
      
      this.applyTheme()
      
      // 监听系统主题变化
      if (window.matchMedia) {
        window.matchMedia('(prefers-color-scheme: dark)').addEventListener('change', (e) => {
          if (!localStorage.getItem('theme')) {
            this.isDark = e.matches
            this.applyTheme()
          }
        })
      }
    },
    
    toggleTheme() {
      this.isDark = !this.isDark
      this.applyTheme()
      this.saveTheme()
      
      // 发出主题变化事件
      this.$emit('theme-change', this.isDark ? 'dark' : 'light')
    },
    
    applyTheme() {
      if (this.isDark) {
        document.documentElement.setAttribute('data-theme', 'dark')
        document.body.classList.add('dark-theme')
      } else {
        document.documentElement.removeAttribute('data-theme')
        document.body.classList.remove('dark-theme')
      }
    },
    
    saveTheme() {
      localStorage.setItem('theme', this.isDark ? 'dark' : 'light')
    }
  }
}
</script>

<style lang="scss" scoped>
.theme-switcher {
  display: inline-flex;
  align-items: center;
  
  .theme-toggle-btn {
    width: 40px;
    height: 40px;
    border: none;
    background: var(--bg-primary);
    border: 1px solid var(--border-light);
    border-radius: var(--border-radius-base);
    display: flex;
    align-items: center;
    justify-content: center;
    cursor: pointer;
    transition: var(--transition-fast);
    color: var(--text-primary);
    font-size: 18px;
    
    &:hover {
      background: var(--bg-tertiary);
      border-color: var(--border-base);
      transform: scale(1.05);
    }
    
    &:active {
      transform: scale(0.95);
    }
    
    &:focus {
      outline: none;
      box-shadow: 0 0 0 3px rgba(0, 102, 255, 0.2);
    }
  }
}

// 主题图标过渡动画
.theme-icon-enter-active,
.theme-icon-leave-active {
  transition: all 0.3s ease;
}

.theme-icon-enter-from {
  opacity: 0;
  transform: rotate(-90deg) scale(0.8);
}

.theme-icon-leave-to {
  opacity: 0;
  transform: rotate(90deg) scale(0.8);
}

// 深色主题适配
[data-theme="dark"] {
  .theme-switcher {
    .theme-toggle-btn {
      background: var(--bg-secondary);
      border-color: var(--border-base);
      color: var(--text-primary);
      
      &:hover {
        background: var(--bg-tertiary);
        border-color: var(--border-dark);
      }
    }
  }
}
</style>
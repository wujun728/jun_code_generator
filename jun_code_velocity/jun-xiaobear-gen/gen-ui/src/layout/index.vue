<template>
  <div class="layout-container">
    <!-- 现代化导航栏 -->
    <header class="navbar" :class="{ 'navbar-scrolled': isScrolled }">
      <div class="navbar-container">
        <!-- Logo 区域 -->
        <div class="navbar-brand">
          <router-link to="/" class="brand-link">
            <div class="brand-logo-wrapper">
              <img src="@/assets/logo.png" class="brand-logo" alt="小熊代码生成器" />
            </div>
            <div class="brand-info">
              <span class="brand-text">小熊代码生成器</span>
              <!-- <span class="brand-subtitle">智能代码生成平台</span> -->
            </div>
          </router-link>
        </div>

        <!-- 主导航菜单 -->
        <nav class="navbar-nav">
          <router-link
            v-for="item in menuItems"
            :key="item.path"
            :to="item.path"
            class="nav-link"
            :class="{ active: isActive(item.path) }"
          >
            <span class="nav-icon">
              <el-icon><component :is="getIconComponent(item.icon)" /></el-icon>
            </span>
            <span class="nav-text">{{ item.title }}</span>
            <span class="nav-indicator"></span>
          </router-link>
        </nav>

        <!-- 右侧操作区 -->
        <div class="navbar-actions">
          <!-- GitHub 链接 -->
          <a
            href="https://gitee.com/javaxiaobear/xiaobear-gen.git"
            target="_blank"
            class="action-btn action-link"
            title="查看源码"
          >
            <el-icon><Link /></el-icon>
            <span class="action-text hidden-mobile">Gitee</span>
          </a>
          
          <!-- 主题切换 -->
          <theme-switcher @theme-change="handleThemeChange" />
          
          <!-- 移动端菜单按钮 -->
          <button 
            class="mobile-menu-btn"
            @click="toggleMobileMenu"
            :class="{ active: isMobileMenuOpen }"
          >
            <span></span>
            <span></span>
            <span></span>
          </button>
        </div>
      </div>
      
      <!-- 移动端菜单 -->
      <div v-if="false" class="mobile-menu" :class="{ open: isMobileMenuOpen }">
        <nav class="mobile-nav">
          <router-link
            v-for="item in menuItems"
            :key="item.path"
            :to="item.path"
            class="mobile-nav-link"
            :class="{ active: isActive(item.path) }"
            @click="closeMobileMenu"
          >
            <el-icon><component :is="getIconComponent(item.icon)" /></el-icon>
            <span>{{ item.title }}</span>
          </router-link>
        </nav>
      </div>
    </header>

    <!-- 主要内容区域 -->
    <main class="main-content">
      <router-view />
    </main>

    <!-- 现代化页脚 -->
    <footer class="footer">
      <div class="footer-container">
        <div class="footer-content">
          <div class="footer-left">
            <span class="copyright">© 2024 小熊代码生成器</span>
            <span class="separator">•</span>
            <span class="license">AGPL-3.0 Licensed</span>
          </div>
          <div class="footer-right">
            <a href="https://www.javaxiaobear.cn" target="_blank" class="footer-link">
              小熊学Java
            </a>
            <span class="separator">•</span>
            <a href="https://gitee.com/javaxiaobear/xiaobear-gen" target="_blank" class="footer-link">
              源码仓库
            </a>
          </div>
        </div>
      </div>
    </footer>
    
    <!-- 移动端遮罩 -->
    <div 
      class="mobile-overlay" 
      :class="{ active: isMobileMenuOpen }"
      @click="closeMobileMenu"
    ></div>
  </div>
</template>

<script>
import { House, Document, Connection, Folder, Link, Star, Sunny, Moon } from '@element-plus/icons-vue'
import { getNewIconName, isOldElementIcon } from '@/utils/icon-mapping'
import ThemeSwitcher from '@/components/ThemeSwitcher'

export default {
  name: 'Layout',
  components: {
    House,
    Document,
    Connection,
    Folder,
    Link,
    Sunny,
    Moon,
    Star,
    ThemeSwitcher
  },
  data() {
    return {
      isScrolled: false,
      isMobileMenuOpen: false,
      menuItems: [
        {
          path: '/index',
          title: '首页',
          icon: 'House'
        },
        {
          path: '/generate/index',
          title: '代码生成',
          icon: 'Document'
        },
        {
          path: '/dataSource/index',
          title: '数据源管理',
          icon: 'Connection'
        },
        {
          path: '/export/index',
          title: '数据库文档',
          icon: 'Folder'
        },
        {
          path: '/changelog/index',
          title: '更新日志',
          icon: 'Star'
        }
      ]
    }
  },
  
  mounted() {
    this.initScrollListener()
  },
  
  beforeUnmount() {
    window.removeEventListener('scroll', this.handleScroll)
  },
  
  methods: {
    isActive(path) {
      if (path === '/index') {
        return this.$route.path === '/' || this.$route.path === '/index'
      }
      return this.$route.path.startsWith(path.split('/')[1] ? `/${path.split('/')[1]}` : path)
    },
    
    handleThemeChange(theme) {
      this.$message({
        message: `已切换到${theme === 'dark' ? '深色' : '浅色'}主题`,
        type: 'success',
        duration: 1500
      })
    },
    
    toggleMobileMenu() {
      this.isMobileMenuOpen = !this.isMobileMenuOpen
      // 防止页面滚动
      if (this.isMobileMenuOpen) {
        document.body.style.overflow = 'hidden'
      } else {
        document.body.style.overflow = ''
      }
    },
    
    closeMobileMenu() {
      this.isMobileMenuOpen = false
      document.body.style.overflow = ''
    },
    
    initScrollListener() {
      window.addEventListener('scroll', this.handleScroll)
    },
    
    handleScroll() {
      this.isScrolled = window.scrollY > 20
    },
    
    getIconComponent(iconName) {
      if (isOldElementIcon(iconName)) {
        return getNewIconName(iconName)
      }
      return iconName
    }
  }
}
</script>

<style lang="scss" scoped>
.layout-container {
  min-height: 100vh;
  display: flex;
  flex-direction: column;
  background: var(--bg-page);
}

/* 现代化导航栏 */
.navbar {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  height: 64px;
  background: var(--bg-card);
  border-bottom: 1px solid var(--border-color);
  z-index: var(--z-fixed);
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.05);
  
  .navbar-container {
    max-width: 1400px;
    margin: 0 auto;
    height: 100%;
    display: flex;
    align-items: center;
    justify-content: space-between;
    padding: 0 24px;
    
    @media (max-width: 768px) {
      padding: 0 16px;
    }
  }
}

/* 品牌 Logo 区域 */
.navbar-brand {
  .brand-link {
    display: flex;
    align-items: center;
    text-decoration: none;
    color: var(--text-primary);
    transition: opacity 150ms ease;
    
    &:hover {
      opacity: 0.8;
    }
    
    .brand-logo {
      height: 32px;
      width: auto;
      margin-right: 12px;
    }
    
    .brand-text {
      font-size: 18px;
      font-weight: 600;
      color: var(--color-primary);
      
      @media (max-width: 768px) {
        display: none;
      }
    }
  }
}

/* 导航菜单 */
.navbar-nav {
  display: flex;
  align-items: center;
  gap: 8px;
  
  .nav-link {
    display: flex;
    align-items: center;
    gap: 8px;
    padding: 8px 16px;
    color: #6b7280;
    text-decoration: none;
    border-radius: 8px;
    font-size: 14px;
    font-weight: 500;
    transition: all 150ms ease;
    
    &:hover {
      color: var(--color-primary);
      background-color: #eff6ff;
    }
    
    &.active {
      color: var(--color-primary);
      background-color: #dbeafe;
    }
    
    .nav-text {
      @media (max-width: 768px) {
        display: none;
      }
    }
  }
}

/* 右侧操作区 */
.navbar-actions {
  display: flex;
  align-items: center;
  gap: 8px;
  
  .action-link {
    display: flex;
    align-items: center;
    gap: 8px;
    padding: 8px 12px;
    color: #9ca3af;
    text-decoration: none;
    border-radius: 8px;
    font-size: 14px;
    transition: all 150ms ease;
    
    &:hover {
      color: var(--color-primary);
      background-color: #f3f4f6;
    }
  }
  
  .theme-toggle {
    display: flex;
    align-items: center;
    justify-content: center;
    width: 36px;
    height: 36px;
    border: none;
    background: none;
    color: #9ca3af;
    border-radius: 8px;
    cursor: pointer;
    transition: all 150ms ease;
    
    &:hover {
      color: var(--color-primary);
      background-color: #f3f4f6;
    }
  }
}

/* 主要内容区域 */
.main-content {
  flex: 1;
  margin-top: 64px;
  min-height: calc(100vh - 64px - 60px);
  padding: 0; /* 去掉全局padding，让每个页面自己控制 */
  background: var(--bg-page);
}

/* 简洁的页脚 */
.footer {
  height: 60px;
  background: var(--bg-card);
  border-top: 1px solid #e5e7eb;
  
  .footer-container {
    max-width: 1400px;
    margin: 0 auto;
    height: 100%;
    padding: 0 24px;
    
    @media (max-width: 768px) {
      padding: 0 16px;
    }
  }
  
  .footer-content {
    height: 100%;
    display: flex;
    align-items: center;
    justify-content: center;
    gap: 12px;
    font-size: 14px;
    color: #9ca3af;
    
    @media (max-width: 768px) {
      flex-wrap: wrap;
      gap: 8px;
    }
    
    .footer-link {
      color: var(--color-primary);
      text-decoration: none;
      transition: opacity 150ms ease;
      
      &:hover {
        opacity: 0.8;
      }
    }
  }
}
</style>

<template>
  <div class="home-container">
    <!-- 简洁的 Hero 区域 -->
    <section class="hero-section">
      <div class="hero-content">
        <div class="hero-text">
          <h1 class="hero-title">
            小熊代码生成器
          </h1>
          <p class="hero-description">
            基于若依 RuoYi-Vue 的企业级代码生成器，支持多种主流技术栈，
            智能分析数据库结构，一键生成前后端代码。
          </p>
          <div class="hero-actions">
            <el-button type="primary" size="large" @click="handleUse" class="btn-primary">
              <el-icon><MagicStick /></el-icon>
              开始使用
            </el-button>
            <el-button size="large" @click="handleDemo" class="btn-secondary">
              <el-icon><VideoPlay /></el-icon>
              在线演示
            </el-button>
          </div>
        </div>
        <div class="hero-image">
          <img :src="logoImage" alt="小熊代码生成器" />
        </div>
      </div>
    </section>

    <!-- 特性介绍 -->
    <section class="features-section">
      <div class="section-container">
        <div class="section-header">
          <h2 class="section-title">产品特性</h2>
          <p class="section-subtitle">强大的功能，简单的操作</p>
        </div>
        <div class="features-grid">
          <div 
            v-for="(feature, index) in features" 
            :key="index" 
            class="feature-item"
          >
            <div class="feature-icon">
              <el-icon><component :is="getFeatureIcon(index)" /></el-icon>
            </div>
            <h3 class="feature-title">{{ feature.title }}</h3>
            <p class="feature-description">{{ feature.details }}</p>
          </div>
        </div>
      </div>
    </section>

    <!-- 技术栈 -->
    <section class="tech-section">
      <div class="section-container">
        <div class="section-header">
          <h2 class="section-title">支持的技术栈</h2>
          <p class="section-subtitle">支持主流的开发技术栈</p>
        </div>
        <div class="tech-grid">
          <div 
            v-for="(tech, index) in techStack" 
            :key="tech.name" 
            class="tech-item"
          >
            <div class="tech-icon">
              <el-icon><component :is="tech.icon" /></el-icon>
            </div>
            <span class="tech-name">{{ tech.name }}</span>
          </div>
        </div>
      </div>
    </section>

    <!-- 行动号召 -->
    <section class="cta-section">
      <div class="section-container">
        <div class="cta-content">
          <h2 class="cta-title">立即开始使用</h2>
          <p class="cta-description">
            马上体验智能代码生成的强大功能，让开发变得更简单、更高效。
          </p>
          <div class="cta-actions">
            <el-button type="primary" size="large" @click="handleUse">
              <el-icon><ArrowRight /></el-icon>
              开始使用
            </el-button>
            <el-button size="large" @click="handleGithub">
              <el-icon><Link /></el-icon>
              查看源码
            </el-button>
          </div>
        </div>
      </div>
    </section>
  </div>
</template>

<script>
import logo from '@/assets/logo.png'
import { 
  MagicStick,
  VideoPlay,
  DocumentAdd, 
  FolderOpened,
  ArrowRight,
  Link,
  Cpu,
  Connection,
  View,
  Menu,
  Grid,
  Coin,
  DataBoard
} from '@element-plus/icons-vue'

export default {
  name: "Index",
  components: {
    MagicStick,
    VideoPlay,
    DocumentAdd,
    FolderOpened,
    ArrowRight,
    Link,
    Cpu,
    Connection,
    View,
    Menu,
    Grid,
    Coin,
    DataBoard
  },
  data() {
    return {
      logoImage: logo,
      techStack: [
        { name: 'Spring Boot', icon: 'Cpu' },
        { name: 'MyBatis', icon: 'Connection' },
        { name: 'MyBatis-Plus', icon: 'Link' },
        { name: 'Vue.js', icon: 'View' },
        { name: 'Element UI', icon: 'Menu' },
        { name: 'Ant Design', icon: 'Grid' },
        { name: 'MySQL', icon: 'Coin' },
        { name: 'PostgreSQL', icon: 'DataBoard' }
      ]
    }
  },
  computed: {
    features() {
      return [
        {
          title: '智能代码生成',
          details: '基于数据表结构智能分析，支持 MyBatis、MyBatis-Plus 等主流框架，自动生成 Controller、Service、Mapper 等完整的业务代码'
        },
        {
          title: '多数据库支持',
          details: '兼容 MySQL、PostgreSQL、Oracle、SQL Server 等12种主流数据库，自动适配不同数据库的数据类型和语法特性'
        },
        {
          title: '开箱即用',
          details: '生成的代码遵循企业级开发规范，包含完整的 CRUD 操作、数据校验、异常处理，可直接用于生产环境'
        }
      ]
    }
  },
  methods: {
    handleUse() {
      this.$message({
        message: '正在跳转到代码生成页面...',
        type: 'success',
        duration: 1500
      })
      
      setTimeout(() => {
        this.$router.push({
          path: "/generate/index",
          query: { pageNum: this.$route.query.pageNum }
        })
      }, 300)
    },
    
    handleDemo() {
      this.$message({
        message: '演示功能开发中，敬请期待！',
        type: 'info',
        duration: 2000
      })
    },
    
    handleGithub() {
      this.$message({
        message: '正在跳转到 Gitee 仓库...',
        type: 'success',
        duration: 1500
      })
      window.open('https://gitee.com/javaxiaobear/xiaobear-gen', '_blank')
    },
    
    getFeatureIcon(index) {
      const icons = ['MagicStick', 'DocumentAdd', 'FolderOpened']
      return icons[index] || 'Document'
    }
  }
}
</script>

<style scoped lang="scss">
.home-container {
  margin: 0;
  padding: 0;
  background: var(--bg-page);
}

/* Hero 区域 */
.hero-section {
  padding: var(--space-20) var(--space-6) var(--space-16);
  background: var(--bg-card);
  
  .hero-content {
    max-width: 1200px;
    margin: 0 auto;
    display: grid;
    grid-template-columns: 1fr 1fr;
    gap: var(--space-16);
    align-items: center;
    
    @media (max-width: 768px) {
      grid-template-columns: 1fr;
      gap: var(--space-8);
      text-align: center;
    }
  }
  
  .hero-text {
    .hero-title {
      font-size: var(--font-4xl);
      font-weight: var(--font-bold);
      color: var(--text-primary);
      margin-bottom: var(--space-6);
      line-height: var(--leading-tight);
      
      @media (max-width: 768px) {
        font-size: var(--font-3xl);
      }
    }
    
    .hero-description {
      font-size: var(--font-lg);
      color: var(--text-secondary);
      line-height: var(--leading-relaxed);
      margin-bottom: var(--space-8);
    }
    
    .hero-actions {
      display: flex;
      gap: var(--space-4);
      
      @media (max-width: 768px) {
        flex-direction: column;
        align-items: center;
      }
      
      .btn-primary {
        background-color: var(--color-primary);
        border-color: var(--color-primary);
        
        &:hover {
          background-color: var(--color-primary-light);
          border-color: var(--color-primary-light);
        }
      }
      
      .btn-secondary {
        background-color: white;
        color: var(--text-primary);
        border-color: var(--border-color);
        
        &:hover {
          background-color: var(--bg-hover);
          border-color: var(--border-dark);
        }
      }
    }
  }
  
  .hero-image {
    text-align: center;
    
    img {
      max-width: 300px;
      height: auto;
      
      @media (max-width: 768px) {
        max-width: 200px;
      }
    }
  }
}

/* 通用区域样式 */
.section-container {
  max-width: 1200px;
  margin: 0 auto;
  padding: 0 var(--space-6);
}

.section-header {
  text-align: center;
  margin-bottom: var(--space-12);
  
  .section-title {
    font-size: var(--font-3xl);
    font-weight: var(--font-bold);
    color: var(--text-primary);
    margin-bottom: var(--space-4);
  }
  
  .section-subtitle {
    font-size: var(--font-lg);
    color: var(--text-secondary);
  }
}

/* 特性区域 */
.features-section {
  padding: var(--space-16) 0;
  
  .features-grid {
    display: grid;
    grid-template-columns: repeat(auto-fit, minmax(300px, 1fr));
    gap: var(--space-8);
    
    .feature-item {
      background: var(--bg-card);
      padding: var(--space-8);
      border-radius: var(--radius-lg);
      border: 1px solid var(--border-color);
      text-align: center;
      transition: border-color var(--transition-fast);
      
      &:hover {
        border-color: var(--color-primary);
      }
      
      .feature-icon {
        width: 64px;
        height: 64px;
        background: var(--color-primary-50);
        border-radius: var(--radius-full);
        display: flex;
        align-items: center;
        justify-content: center;
        margin: 0 auto var(--space-6);
        color: var(--color-primary);
        font-size: var(--font-2xl);
      }
      
      .feature-title {
        font-size: var(--font-xl);
        font-weight: var(--font-semibold);
        color: var(--text-primary);
        margin-bottom: var(--space-4);
      }
      
      .feature-description {
        color: var(--text-secondary);
        line-height: var(--leading-relaxed);
      }
    }
  }
}

/* 技术栈区域 */
.tech-section {
  padding: var(--space-16) 0;
  background: var(--bg-card);
  
  .tech-grid {
    display: grid;
    grid-template-columns: repeat(auto-fit, minmax(120px, 1fr));
    gap: var(--space-6);
    
    @media (max-width: 768px) {
      grid-template-columns: repeat(2, 1fr);
    }
    
    .tech-item {
      display: flex;
      flex-direction: column;
      align-items: center;
      padding: var(--space-6);
      background: white;
      border: 1px solid var(--border-color);
      border-radius: var(--radius-md);
      transition: all var(--transition-fast);
      
      &:hover {
        border-color: var(--color-primary);
        box-shadow: var(--shadow-sm);
      }
      
      .tech-icon {
        width: 48px;
        height: 48px;
        background: var(--color-primary-50);
        border-radius: var(--radius-md);
        display: flex;
        align-items: center;
        justify-content: center;
        margin-bottom: var(--space-3);
        color: var(--color-primary);
        font-size: var(--font-xl);
      }
      
      .tech-name {
        font-size: var(--font-sm);
        font-weight: var(--font-medium);
        color: var(--text-primary);
      }
    }
  }
}

/* 行动号召区域 */
.cta-section {
  padding: var(--space-16) 0;
  
  .cta-content {
    text-align: center;
    max-width: 600px;
    margin: 0 auto;
    
    .cta-title {
      font-size: var(--font-3xl);
      font-weight: var(--font-bold);
      color: var(--text-primary);
      margin-bottom: var(--space-6);
    }
    
    .cta-description {
      font-size: var(--font-lg);
      color: var(--text-secondary);
      line-height: var(--leading-relaxed);
      margin-bottom: var(--space-8);
    }
    
    .cta-actions {
      display: flex;
      justify-content: center;
      gap: var(--space-4);
      
      @media (max-width: 768px) {
        flex-direction: column;
        align-items: center;
      }
    }
  }
}

/* 响应式优化 */
@media (max-width: 768px) {
  .hero-section {
    padding: var(--space-12) var(--space-4) var(--space-8);
  }
  
  .features-section,
  .tech-section,
  .cta-section {
    padding: var(--space-12) 0;
  }
  
  .section-container {
    padding: 0 var(--space-4);
  }
}</style>    cursor: pointer;
    position: relative;
    overflow: hidden;

    &::before {
      content: '';
      position: absolute;
      top: 0;
      left: 0;
      right: 0;
      bottom: 0;
      background: linear-gradient(135deg, rgba(245, 112, 112, 0.05) 0%, rgba(255, 142, 142, 0.05) 100%);
      opacity: 0;
      transition: opacity 0.3s ease;
    }

    &:hover,
    &.tech-hover {
      transform: translateY(-8px) scale(1.05);
      box-shadow: var(--shadow-base);

      &::before {
        opacity: 1;
      }

      .tech-icon {
        transform: scale(1.2) rotate(10deg);
        background: var(--primary-gradient);

        i {
          color: white;
        }
      }

      .tech-name {
        color: var(--primary-color);
        font-weight: 600;
      }
    }

    &:active {
      transform: translateY(-6px) scale(1.03);
    }

    .tech-icon {
      width: 50px;
      height: 50px;
      margin-bottom: var(--spacing-sm);
      display: flex;
      align-items: center;
      justify-content: center;
      background: rgba(245, 112, 112, 0.1);
      border-radius: var(--border-radius-base);
      transition: all 0.4s cubic-bezier(0.4, 0, 0.2, 1);
      position: relative;
      z-index: 1;

      i {
        font-size: 24px;
        color: var(--primary-color);
        transition: all 0.3s ease;
      }
    }

    .tech-name {
      font-size: 0.9rem;
      font-weight: 500;
      color: var(--text-primary);
      text-align: center;
      transition: all 0.3s ease;
      position: relative;
      z-index: 1;
    }

    .tech-tooltip {
      position: absolute;
      bottom: -40px;
      left: 50%;
      transform: translateX(-50%);
      background: var(--text-primary);
      color: white;
      padding: var(--spacing-xs) var(--spacing-sm);
      border-radius: var(--border-radius-small);
      font-size: 12px;
      white-space: nowrap;
      z-index: 10;
      animation: fadeInUp 0.3s ease;

      &::before {
        content: '';
        position: absolute;
        top: -4px;
        left: 50%;
        transform: translateX(-50%);
        width: 0;
        height: 0;
        border-left: 4px solid transparent;
        border-right: 4px solid transparent;
        border-bottom: 4px solid var(--text-primary);
      }
    }
  }
}

// Contact Section
.contact-section {
  padding: var(--spacing-xl) 0;
  background: linear-gradient(135deg, var(--primary-color) 0%, #ff8e8e 100%);
  color: white;

  .contact-container {
    max-width: 1200px;
    margin: 0 auto;
    padding: 0;
  }

  .contact-content {
    display: grid;
    grid-template-columns: 1fr 1fr;
    gap: var(--spacing-xl);
    align-items: center;

    @media (max-width: 768px) {
      grid-template-columns: 1fr;
      text-align: center;
    }
  }

  .contact-title {
    font-size: 2.5rem;
    font-weight: 700;
    margin-bottom: var(--spacing-md);
    line-height: 1.2;
  }

  .contact-description {
    font-size: 1.2rem;
    line-height: 1.6;
    margin-bottom: var(--spacing-lg);
    opacity: 0.9;
  }

  .contact-features {
    display: flex;
    gap: var(--spacing-lg);
    margin-bottom: var(--spacing-lg);

    @media (max-width: 768px) {
      justify-content: center;
    }

    .contact-feature {
      display: flex;
      align-items: center;
      gap: var(--spacing-xs);
      font-weight: 500;

      i {
        font-size: 1.2rem;
      }
    }
  }

  .contact-actions {
    display: flex;
    gap: var(--spacing-md);
    justify-content: flex-end;

    @media (max-width: 768px) {
      justify-content: center;
    }

    .contact-button {
      padding: var(--spacing-md) var(--spacing-xl);
      border-radius: 50px;
      font-size: 1.1rem;
      font-weight: 600;
      transition: all 0.3s cubic-bezier(0.4, 0, 0.2, 1);

      &:hover {
        transform: translateY(-2px);
      }

      &.secondary {
        background: rgba(255, 255, 255, 0.2);
        border: 2px solid rgba(255, 255, 255, 0.3);
        color: white;

        &:hover {
          background: white;
          color: var(--primary-color);
        }
      }
    }
  }
}

// Footer
.footer {
  background: #1a202c;
  color: white;
  padding: var(--spacing-xl) 0 var(--spacing-lg);

  .footer-container {
    max-width: 1200px;
    margin: 0 auto;
    padding: 0;
  }

  .footer-content {
    display: grid;
    grid-template-columns: 2fr 1fr 1fr 1fr;
    gap: var(--spacing-xl);
    margin-bottom: var(--spacing-xl);

    @media (max-width: 768px) {
      grid-template-columns: 1fr;
      gap: var(--spacing-lg);
    }
  }

  .footer-section {
    .footer-title {
      font-size: 1.5rem;
      font-weight: 700;
      margin-bottom: var(--spacing-md);
      color: white;
    }

    .footer-subtitle {
      font-size: 1.1rem;
      font-weight: 600;
      margin-bottom: var(--spacing-md);
      color: #e2e8f0;
    }

    .footer-description {
      color: #a0aec0;
      line-height: 1.6;
      margin-bottom: var(--spacing-md);
    }

    .footer-links {
      list-style: none;
      padding: 0;
      margin: 0;

      li {
        margin-bottom: var(--spacing-xs);

        a {
          color: #a0aec0;
          text-decoration: none;
          transition: color 0.3s ease;

          &:hover {
            color: white;
          }
        }
      }
    }

    .footer-social {
      .social-link {
        display: inline-flex;
        align-items: center;
        gap: var(--spacing-xs);
        color: #a0aec0;
        text-decoration: none;
        transition: color 0.3s ease;

        &:hover {
          color: var(--primary-color);
        }
      }
    }
  }

  .footer-bottom {
    border-top: 1px solid #2d3748;
    padding-top: var(--spacing-lg);
    text-align: center;

    .footer-copyright {
      color: #a0aec0;
      margin: 0;
    }
  }
}

// Animations
@keyframes float {
  0%, 100% {
    transform: translateY(0px);
  }
  50% {
    transform: translateY(-20px);
  }
}

@keyframes bounce {
  0%, 20%, 53%, 80%, 100% {
    transform: translate3d(0, 0, 0);
  }
  40%, 43% {
    transform: translate3d(0, -8px, 0);
  }
  70% {
    transform: translate3d(0, -4px, 0);
  }
  90% {
    transform: translate3d(0, -2px, 0);
  }
}

@keyframes pulse-icon {
  0%, 100% {
    transform: scale(1);
    opacity: 1;
  }
  50% {
    transform: scale(1.1);
    opacity: 0.8;
  }
}

@keyframes shake {
  0%, 100% {
    transform: translateX(0);
  }
  10%, 30%, 50%, 70%, 90% {
    transform: translateX(-2px);
  }
  20%, 40%, 60%, 80% {
    transform: translateX(2px);
  }
}

@keyframes glow {
  0%, 100% {
    box-shadow: 0 0 5px rgba(245, 112, 112, 0.3);
  }
  50% {
    box-shadow: 0 0 20px rgba(245, 112, 112, 0.6);
  }
}

@keyframes highlight-expand {
  from {
    width: 0;
    opacity: 0;
  }
  to {
    width: 100%;
    opacity: 1;
  }
}

@keyframes fadeInUp {
  from {
    opacity: 0;
    transform: translateY(20px);
  }
  to {
    opacity: 1;
    transform: translateY(0);
  }
}

.fade-in-up {
  opacity: 0;
  transform: translateY(30px);
  transition: all 0.8s cubic-bezier(0.4, 0, 0.2, 1);

  &.animate {
    opacity: 1;
    transform: translateY(0);
  }
}

.fade-in-down {
  opacity: 0;
  transform: translateY(-30px);
  transition: all 0.8s cubic-bezier(0.4, 0, 0.2, 1);

  &.animate {
    opacity: 1;
    transform: translateY(0);
  }
}

.delay-0 { transition-delay: 0s; }
.delay-1 { transition-delay: 0.2s; }
.delay-2 { transition-delay: 0.4s; }

@media (max-width: 768px) {
  .hero {
    min-height: 80vh;

    .hero-content {
      .main-title {
        font-size: 2.5rem;
      }

      .description {
        font-size: 1.1rem;
      }

      .hero-stats {
        .stat-item .stat-number {
          font-size: 1.5rem;
        }
      }
    }
  }

  .features-section .section-title,
  .tech-section .section-title,
  .why-section .section-title {
    font-size: 2rem;
  }

  .contact-title {
    font-size: 2rem;
  }

  .footer-content {
    grid-template-columns: 1fr;
    text-align: center;
  }
}

// Page transition
.page-transition {
  transition: opacity 0.3s ease;
  opacity: 0.8;
}

// Smooth scrolling
html {
  scroll-behavior: smooth;
}

// Custom scrollbar
<style scoped lang="scss">
.home-container {
  margin: 0;
  padding: 0;
  background: var(--bg-page);
}

/* Hero 区域 */
.hero-section {
  padding: var(--space-20) var(--space-6) var(--space-16);
  background: var(--bg-card);
  
  .hero-content {
    max-width: 1200px;
    margin: 0 auto;
    display: grid;
    grid-template-columns: 1fr 1fr;
    gap: var(--space-16);
    align-items: center;
    
    @media (max-width: 768px) {
      grid-template-columns: 1fr;
      gap: var(--space-8);
      text-align: center;
    }
  }
  
  .hero-text {
    .hero-title {
      font-size: var(--font-4xl);
      font-weight: var(--font-bold);
      color: var(--text-primary);
      margin-bottom: var(--space-6);
      line-height: var(--leading-tight);
      
      @media (max-width: 768px) {
        font-size: var(--font-3xl);
      }
    }
    
    .hero-description {
      font-size: var(--font-lg);
      color: var(--text-secondary);
      line-height: var(--leading-relaxed);
      margin-bottom: var(--space-8);
    }
    
    .hero-actions {
      display: flex;
      gap: var(--space-4);
      
      @media (max-width: 768px) {
        flex-direction: column;
        align-items: center;
      }
      
      .btn-primary {
        background-color: var(--color-primary);
        border-color: var(--color-primary);
        
        &:hover {
          background-color: var(--color-primary-light);
          border-color: var(--color-primary-light);
        }
      }
      
      .btn-secondary {
        background-color: white;
        color: var(--text-primary);
        border-color: var(--border-color);
        
        &:hover {
          background-color: var(--bg-hover);
          border-color: var(--border-dark);
        }
      }
    }
  }
  
  .hero-image {
    text-align: center;
    
    img {
      max-width: 300px;
      height: auto;
      
      @media (max-width: 768px) {
        max-width: 200px;
      }
    }
  }
}

/* 通用区域样式 */
.section-container {
  max-width: 1200px;
  margin: 0 auto;
  padding: 0 var(--space-6);
}

.section-header {
  text-align: center;
  margin-bottom: var(--space-12);
  
  .section-title {
    font-size: var(--font-3xl);
    font-weight: var(--font-bold);
    color: var(--text-primary);
    margin-bottom: var(--space-4);
  }
  
  .section-subtitle {
    font-size: var(--font-lg);
    color: var(--text-secondary);
  }
}

/* 特性区域 */
.features-section {
  padding: var(--space-16) 0;
  
  .features-grid {
    display: grid;
    grid-template-columns: repeat(auto-fit, minmax(300px, 1fr));
    gap: var(--space-8);
    
    .feature-item {
      background: var(--bg-card);
      padding: var(--space-8);
      border-radius: var(--radius-lg);
      border: 1px solid var(--border-color);
      text-align: center;
      transition: border-color var(--transition-fast);
      
      &:hover {
        border-color: var(--color-primary);
      }
      
      .feature-icon {
        width: 64px;
        height: 64px;
        background: var(--color-primary-50);
        border-radius: var(--radius-full);
        display: flex;
        align-items: center;
        justify-content: center;
        margin: 0 auto var(--space-6);
        color: var(--color-primary);
        font-size: var(--font-2xl);
      }
      
      .feature-title {
        font-size: var(--font-xl);
        font-weight: var(--font-semibold);
        color: var(--text-primary);
        margin-bottom: var(--space-4);
      }
      
      .feature-description {
        color: var(--text-secondary);
        line-height: var(--leading-relaxed);
      }
    }
  }
}

/* 技术栈区域 */
.tech-section {
  padding: var(--space-16) 0;
  background: var(--bg-card);
  
  .tech-grid {
    display: grid;
    grid-template-columns: repeat(auto-fit, minmax(120px, 1fr));
    gap: var(--space-6);
    
    @media (max-width: 768px) {
      grid-template-columns: repeat(2, 1fr);
    }
    
    .tech-item {
      display: flex;
      flex-direction: column;
      align-items: center;
      padding: var(--space-6);
      background: white;
      border: 1px solid var(--border-color);
      border-radius: var(--radius-md);
      transition: all var(--transition-fast);
      
      &:hover {
        border-color: var(--color-primary);
        box-shadow: var(--shadow-sm);
      }
      
      .tech-icon {
        width: 48px;
        height: 48px;
        background: var(--color-primary-50);
        border-radius: var(--radius-md);
        display: flex;
        align-items: center;
        justify-content: center;
        margin-bottom: var(--space-3);
        color: var(--color-primary);
        font-size: var(--font-xl);
      }
      
      .tech-name {
        font-size: var(--font-sm);
        font-weight: var(--font-medium);
        color: var(--text-primary);
      }
    }
  }
}

/* 行动号召区域 */
.cta-section {
  padding: var(--space-16) 0;
  
  .cta-content {
    text-align: center;
    max-width: 600px;
    margin: 0 auto;
    
    .cta-title {
      font-size: var(--font-3xl);
      font-weight: var(--font-bold);
      color: var(--text-primary);
      margin-bottom: var(--space-6);
    }
    
    .cta-description {
      font-size: var(--font-lg);
      color: var(--text-secondary);
      line-height: var(--leading-relaxed);
      margin-bottom: var(--space-8);
    }
    
    .cta-actions {
      display: flex;
      justify-content: center;
      gap: var(--space-4);
      
      @media (max-width: 768px) {
        flex-direction: column;
        align-items: center;
      }
    }
  }
}

/* 响应式优化 */
@media (max-width: 768px) {
  .hero-section {
    padding: var(--space-12) var(--space-4) var(--space-8);
  }
  
  .features-section,
  .tech-section,
  .cta-section {
    padding: var(--space-12) 0;
  }
  
  .section-container {
    padding: 0 var(--space-4);
  }
}
</style>

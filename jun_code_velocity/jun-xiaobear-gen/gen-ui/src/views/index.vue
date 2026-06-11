<template>
  <div class="home-container">
    <!-- 现代化 Hero 区域 -->
    <section class="hero-section">
      <div class="hero-background">
        <div class="hero-pattern"></div>
      </div>
      <div class="hero-content">
        <div class="hero-text">
          <div class="hero-badge">
            <span class="badge-icon">🚀</span>
            <span class="badge-text">AI 驱动的代码生成革命</span>
          </div>
          <h1 class="hero-title">
            一行代码都不用写
            <span class="title-highlight">后端 CRUD 秒级生成</span>
          </h1>
          <p class="hero-description">
            基于 RuoYi-Vue 和 MyBatis-Plus，为开发者解放重复劳动。<br>
            连接数据库，AI 自动生成全套业务代码，让您专注于核心业务逻辑。
          </p>
          <div class="hero-actions">
            <modern-button type="primary" size="large" icon="MagicStick" @click="handleUse" class="btn-primary">
              立即体验
            </modern-button>
            <div class="secondary-action">
              <modern-button size="large" icon="VideoPlay" @click="handleDemo" class="btn-demo">
                <span class="play-icon">▷</span>
                观看演示视频
              </modern-button>
            </div>
          </div>
        </div>
        <div class="hero-visual">
          <div class="demo-container">
            <div class="code-preview">
              <div class="preview-header">
                <div class="preview-tabs">
                  <span class="tab active">Controller.java</span>
                  <span class="tab">Service.java</span>
                  <span class="tab">Mapper.java</span>
                </div>
              </div>
              <div class="preview-content">
                <div class="code-line">
                  <span class="line-number">1</span>
                  <span class="code-text">@RestController</span>
                </div>
                <div class="code-line">
                  <span class="line-number">2</span>
                  <span class="code-text">@RequestMapping("/api/user")</span>
                </div>
                <div class="code-line">
                  <span class="line-number">3</span>
                  <span class="code-text">public class UserController {</span>
                </div>
                <div class="code-line generating">
                  <span class="line-number">4</span>
                  <span class="code-text">    // 正在生成中...</span>
                  <div class="typing-cursor"></div>
                </div>
              </div>
            </div>
            <div class="generation-stats">
              <div class="stat-badge">
                <span class="stat-icon">⚡</span>
                <span class="stat-text">3秒生成完整CRUD</span>
              </div>
              <div class="stat-badge">
                <span class="stat-icon">🎯</span>
                <span class="stat-text">生产级代码质量</span>
              </div>
            </div>
          </div>
        </div>
      </div>
    </section>

    <!-- 产品特性展示 -->
    <section class="features-section">
      <div class="section-container">
        <div class="section-header">
          <h2 class="section-title">以前所未有的效率，构建您的应用</h2>
          <p class="section-subtitle">告别重复的 CRUD 开发，让 AI 为您生成企业级代码，专注于真正重要的业务创新</p>
        </div>
        <div class="features-grid">
          <modern-card 
            v-for="(feature, index) in features" 
            :key="index" 
            class="feature-card"
            :type="getFeatureType(index)"
            hover
          >
            <template #header>
              <div class="feature-header">
                <div class="feature-icon">
                  <el-icon><component :is="getFeatureIcon(index)" /></el-icon>
                </div>
                <h3 class="feature-title">{{ feature.title }}</h3>
              </div>
            </template>
            <div class="feature-content">
              <p class="feature-description">{{ feature.details }}</p>
              <div class="feature-highlights">
                <span v-for="highlight in feature.highlights" :key="highlight" class="highlight-tag">
                  {{ highlight }}
                </span>
              </div>
            </div>
          </modern-card>
        </div>
      </div>
    </section>

    <!-- 技术栈展示 -->
    <section class="tech-section">
      <div class="section-container">
        <div class="section-header">
          <h2 class="section-title">支持的技术栈</h2>
          <p class="section-subtitle">全面支持主流的开发技术栈，无缝集成到您的项目中</p>
        </div>
        <div class="tech-categories">
          <div class="tech-category">
            <h3 class="category-title">后端技术</h3>
            <div class="tech-grid">
              <div v-for="tech in backendTech" :key="tech.name" class="tech-item">
                <div class="tech-icon">
                  <el-icon><component :is="tech.icon" /></el-icon>
                </div>
                <span class="tech-name">{{ tech.name }}</span>
              </div>
            </div>
          </div>
          <div class="tech-category">
            <h3 class="category-title">前端技术</h3>
            <div class="tech-grid">
              <div v-for="tech in frontendTech" :key="tech.name" class="tech-item">
                <div class="tech-icon">
                  <el-icon><component :is="tech.icon" /></el-icon>
                </div>
                <span class="tech-name">{{ tech.name }}</span>
              </div>
            </div>
          </div>
          <div class="tech-category">
            <h3 class="category-title">数据库支持</h3>
            <div class="tech-grid">
              <div v-for="tech in databaseTech" :key="tech.name" class="tech-item">
                <div class="tech-icon">
                  <el-icon><component :is="tech.icon" /></el-icon>
                </div>
                <span class="tech-name">{{ tech.name }}</span>
              </div>
            </div>
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
  DataBoard,
  Lock,
  Document,
  Platform,
  Files
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
    DataBoard,
    Lock,
    Document,
    Platform,
    Files
  },
  data() {
    return {
      logoImage: logo,
      backendTech: [
        { name: 'Spring Boot', icon: 'Cpu' },
        { name: 'MyBatis', icon: 'Connection' },
        { name: 'MyBatis-Plus', icon: 'Link' },
        { name: 'Spring Security', icon: 'Lock' }
      ],
      frontendTech: [
        { name: 'Vue.js', icon: 'View' },
        { name: 'Element Plus', icon: 'Menu' },
        { name: 'Ant Design', icon: 'Grid' },
        { name: 'TypeScript', icon: 'Document' }
      ],
      databaseTech: [
        { name: 'MySQL', icon: 'Coin' },
        { name: 'PostgreSQL', icon: 'DataBoard' },
        { name: 'Oracle', icon: 'Platform' },
        { name: 'SQL Server', icon: 'Files' }
      ]
    }
  },
  computed: {
    features() {
      return [
        {
          title: 'AI 驱动的智能分析',
          details: '只需连接数据库表，即可一键生成从 Controller 到 Mapper 的完整后端代码。节省 90% 的重复工作时间，让您专注于核心业务逻辑开发。',
          highlights: ['节省90%时间', '一键生成', '智能分析'],
          link: '了解更多 →'
        },
        {
          title: '主流数据库，无缝兼容',
          details: '完美适配 MySQL, PostgreSQL, Oracle 等 12+ 种主流数据库。无论您的项目使用何种技术栈，都能轻松集成，零学习成本。',
          highlights: ['12+数据库', '零学习成本', '轻松集成'],
          link: '查看支持列表 →'
        },
        {
          title: '生产级代码，直接可用',
          details: '生成的代码遵循企业级开发规范，包含完整的增删改查、数据校验和异常处理。无需修改，直接部署到生产环境。',
          highlights: ['企业级规范', '直接部署', '完整功能'],
          link: '查看代码示例 →'
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
      const icons = ['MagicStick', 'Connection', 'FolderOpened']
      return icons[index] || 'Document'
    },
    
    getFeatureType(index) {
      const types = ['primary', 'success', 'info']
      return types[index] || 'default'
    }
  }
}
</script>

<style scoped lang="scss">
.home-container {
  margin: 0;
  padding: 0;
  background: #ffffff;
  position: relative;
  
  &::before {
    content: '';
    position: fixed;
    top: 0;
    left: 0;
    right: 0;
    bottom: 0;
    background: url('data:image/svg+xml,<svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 100 100"><defs><pattern id="grid" width="20" height="20" patternUnits="userSpaceOnUse"><path d="M 20 0 L 0 0 0 20" fill="none" stroke="%23e5e7eb" stroke-width="0.5" opacity="0.4"/></pattern></defs><rect width="100" height="100" fill="url(%23grid)"/></svg>');
    z-index: 0;
    pointer-events: none;
  }
}

/* Hero 区域 */
.hero-section {
  padding: 100px 24px 80px;
  background: #ffffff;
  position: relative;
  overflow: hidden;
  
  .hero-content {
    max-width: 1200px;
    margin: 0 auto;
    display: grid;
    grid-template-columns: 1.2fr 1fr;
    gap: 80px;
    align-items: center;
    position: relative;
    z-index: 1;
    
    @media (max-width: 968px) {
      grid-template-columns: 1fr;
      gap: 48px;
      text-align: center;
    }
    
    @media (max-width: 768px) {
      gap: 32px;
    }
  }
  
  .hero-text {
    .hero-badge {
      display: inline-flex;
      align-items: center;
      gap: 8px;
      background: #eff6ff;
      border: 1px solid #bfdbfe;
      color: #1d4ed8;
      padding: 8px 16px;
      border-radius: 20px;
      font-size: 14px;
      font-weight: 500;
      margin-bottom: 24px;
      
      .badge-icon {
        font-size: 16px;
      }
    }
    
    .hero-title {
      font-size: 42px;
      font-weight: 800;
      color: #111827;
      margin-bottom: 24px;
      line-height: 1.2;
      
      .title-highlight {
        display: block;
        color: #3b82f6;
        margin-top: 8px;
      }
      
      @media (max-width: 768px) {
        font-size: 32px;
      }
    }
    
    .hero-description {
      font-size: 18px;
      color: #4b5563;
      line-height: 1.75;
      margin-bottom: 40px;
    }
    
    .hero-actions {
      display: flex;
      flex-direction: column;
      gap: 16px;
      align-items: flex-start;
      
      @media (max-width: 768px) {
        align-items: center;
      }
      
      .btn-primary {
        background: linear-gradient(135deg, #3b82f6 0%, #8b5cf6 100%);
        border: none;
        padding: 16px 32px;
        font-size: 16px;
        font-weight: 600;
        box-shadow: 0 0 30px rgba(59, 130, 246, 0.4);
        transition: all 0.3s ease;
        position: relative;
        overflow: hidden;
        
        &::before {
          content: '';
          position: absolute;
          top: 0;
          left: -100%;
          width: 100%;
          height: 100%;
          background: linear-gradient(90deg, transparent, rgba(255, 255, 255, 0.2), transparent);
          transition: left 0.5s;
        }
        
        &:hover {
          transform: translateY(-2px);
          box-shadow: 0 0 40px rgba(59, 130, 246, 0.6);
          
          &::before {
            left: 100%;
          }
        }
      }
      
      .secondary-action {
        .btn-demo {
          background: transparent;
          color: #94a3b8;
          border: none;
          padding: 8px 0;
          font-size: 16px;
          display: flex;
          align-items: center;
          gap: 8px;
          transition: all 0.3s ease;
          
          .play-icon {
            color: #60a5fa;
            font-size: 14px;
          }
          
          &:hover {
            color: #60a5fa;
            text-shadow: 0 0 10px rgba(96, 165, 250, 0.5);
          }
        }
      }
    }
  }
  
  .hero-visual {
    display: flex;
    justify-content: center;
    align-items: center;
    
    .demo-container {
      width: 100%;
      max-width: 400px;
      
      .code-preview {
        background: #ffffff;
        border: 1px solid #e5e7eb;
        border-radius: 12px;
        overflow: hidden;
        box-shadow: 0 10px 25px rgba(0, 0, 0, 0.08);
        margin-bottom: 20px;
        animation: slideInRight 0.8s ease-out;
        
        .preview-header {
          background: #f8fafc;
          padding: 12px 16px;
          border-bottom: 1px solid #e5e7eb;
          display: flex;
          align-items: center;
          gap: 12px;
          
          .window-controls {
            display: flex;
            gap: 6px;
            
            .control {
              width: 12px;
              height: 12px;
              border-radius: 50%;
              
              &.close { background: #ff5f57; }
              &.minimize { background: #ffbd2e; }
              &.maximize { background: #28ca42; }
            }
          }
          
          .preview-tabs {
            display: flex;
            gap: 16px;
            
            .tab {
              color: #6b7280;
              font-size: 12px;
              padding: 4px 8px;
              border-radius: 4px;
              transition: all 0.3s ease;
              
              &.active {
                background: #3b82f6;
                color: white;
              }
            }
          }
        }
        
        .preview-content {
          padding: 16px;
          font-family: 'Monaco', 'Menlo', monospace;
          font-size: 13px;
          background: #ffffff;
          
          .code-line {
            display: flex;
            align-items: center;
            margin-bottom: 8px;
            position: relative;
            
            .line-number {
              color: #9ca3af;
              width: 20px;
              text-align: right;
              margin-right: 16px;
              font-size: 11px;
            }
            
            .code-text {
              color: #374151;
            }
            
            &.generating {
              .code-text {
                color: #3b82f6;
              }
              
              .typing-cursor {
                display: inline-block;
                width: 2px;
                height: 14px;
                background: #3b82f6;
                margin-left: 4px;
                animation: blink 1s infinite;
              }
            }
          }
        }
      }
      
      .generation-stats {
        display: flex;
        gap: 12px;
        justify-content: center;
        
        .stat-badge {
          display: flex;
          align-items: center;
          gap: 6px;
          background: white;
          padding: 8px 12px;
          border-radius: 20px;
          box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
          font-size: 12px;
          color: #374151;
          
          .stat-icon {
            font-size: 14px;
          }
        }
      }
    }
  }
}

/* 通用区域样式 */
.section-container {
  max-width: 1200px;
  margin: 0 auto;
  padding: 0 24px;
}

.section-header {
  text-align: center;
  margin-bottom: 48px;
  
  .section-title {
    font-size: 30px;
    font-weight: 700;
    color: #111827;
    margin-bottom: 16px;
  }
  
  .section-subtitle {
    font-size: 18px;
    color: #6b7280;
  }
}

/* 特性区域 */
.features-section {
  padding: 80px 0;
  position: relative;
  z-index: 1;
  
  .features-grid {
    display: grid;
    grid-template-columns: repeat(auto-fit, minmax(320px, 1fr));
    gap: 32px;
    
    .feature-card {
      background: white;
      border: 1px solid #e5e7eb;
      border-radius: 16px;
      overflow: hidden;
      box-shadow: 0 4px 12px rgba(0, 0, 0, 0.05);
      transition: all 0.3s ease;
      animation: fadeInUp 0.6s ease-out;
      position: relative;
      
      &:nth-child(1) { animation-delay: 0.1s; }
      &:nth-child(2) { animation-delay: 0.2s; }
      &:nth-child(3) { animation-delay: 0.3s; }
      
      &::before {
        content: '';
        position: absolute;
        top: 0;
        left: 0;
        right: 0;
        height: 3px;
        background: linear-gradient(90deg, transparent, #3b82f6, transparent);
        opacity: 0;
        transition: opacity 0.3s ease;
      }
      
      &:hover {
        transform: translateY(-8px);
        border-color: #3b82f6;
        box-shadow: 0 10px 20px rgba(0, 0, 0, 0.1);
        
        &::before {
          opacity: 1;
        }
      }
      
      .feature-header {
        display: flex;
        align-items: center;
        gap: 16px;
        padding: 24px 24px 0;
        
        .feature-icon {
          width: 56px;
          height: 56px;
          background: linear-gradient(135deg, #3b82f6 0%, #1d4ed8 100%);
          border-radius: 12px;
          display: flex;
          align-items: center;
          justify-content: center;
          color: white;
          font-size: 24px;
          box-shadow: 0 4px 12px rgba(59, 130, 246, 0.3);
        }
        
        .feature-title {
          font-size: 20px;
          font-weight: 700;
          color: #111827;
          margin: 0;
        }
      }
      
      .feature-content {
        padding: 16px 24px 24px;
        
        .feature-description {
          color: #4b5563;
          line-height: 1.7;
          margin-bottom: 16px;
          font-size: 15px;
        }
        
        .feature-highlights {
          display: flex;
          flex-wrap: wrap;
          gap: 8px;
          margin-bottom: 16px;
          
          .highlight-tag {
            background: #eff6ff;
            border: 1px solid #bfdbfe;
            color: #1d4ed8;
            padding: 4px 12px;
            border-radius: 12px;
            font-size: 12px;
            font-weight: 500;
          }
        }
        
        .feature-link {
          color: #3b82f6;
          font-size: 14px;
          font-weight: 500;
          text-decoration: none;
          display: inline-flex;
          align-items: center;
          gap: 4px;
          transition: all 0.3s ease;
          
          &:hover {
            color: #1d4ed8;
          }
        }
      }
    }
  }
}

/* 技术栈区域 */
.tech-section {
  padding: 80px 0;
  background: #f8fafc;
  
  .tech-categories {
    display: grid;
    gap: 48px;
    
    .tech-category {
      .category-title {
        font-size: 24px;
        font-weight: 700;
        color: #374151;
        margin-bottom: 24px;
        text-align: center;
      }
    }
  }
  
  .tech-grid {
    display: grid;
    grid-template-columns: repeat(auto-fit, minmax(120px, 1fr));
    gap: 24px;
    
    @media (max-width: 768px) {
      grid-template-columns: repeat(2, 1fr);
    }
    
    .tech-item {
      display: flex;
      flex-direction: column;
      align-items: center;
      padding: 24px;
      background: white;
      border: 1px solid #e5e7eb;
      border-radius: 12px;
      transition: all 0.3s ease;
      box-shadow: 0 2px 4px rgba(0, 0, 0, 0.05);
      
      &:hover {
        transform: translateY(-4px);
        border-color: #3b82f6;
        box-shadow: 0 8px 16px rgba(0, 0, 0, 0.1);
        
        .tech-icon {
          transform: scale(1.1);
          background: #eff6ff;
          border-color: #3b82f6;
        }
      }
      
      .tech-icon {
        width: 48px;
        height: 48px;
        background: #f3f4f6;
        border: 1px solid #e5e7eb;
        border-radius: 8px;
        display: flex;
        align-items: center;
        justify-content: center;
        margin-bottom: 12px;
        color: #3b82f6;
        font-size: 20px;
        transition: all 0.3s ease;
      }
      
      .tech-name {
        font-size: 14px;
        font-weight: 500;
        color: #6b7280;
        transition: color 0.3s ease;
      }
      
      &:hover .tech-name {
        color: #374151;
      }
    }
  }
}

/* 行动号召区域 */
.cta-section {
  padding: 80px 0;
  background: linear-gradient(135deg, #f8fafc 0%, #e5e7eb 100%);
  position: relative;
  z-index: 1;
  
  .cta-content {
    text-align: center;
    max-width: 700px;
    margin: 0 auto;
    
    .cta-title {
      font-size: 36px;
      font-weight: 800;
      color: #111827;
      margin-bottom: 24px;
      line-height: 1.2;
    }
    
    .cta-description {
      font-size: 18px;
      color: #6b7280;
      line-height: 1.75;
      margin-bottom: 40px;
    }
    
    .cta-actions {
      display: flex;
      justify-content: center;
      gap: 16px;
      
      @media (max-width: 768px) {
        flex-direction: column;
        align-items: center;
      }
      
      .el-button {
        padding: 16px 32px;
        font-size: 16px;
        font-weight: 600;
        border-radius: 8px;
        transition: all 0.3s ease;
        
        &.el-button--primary {
          background: linear-gradient(135deg, #3b82f6 0%, #1d4ed8 100%);
          border: none;
          box-shadow: 0 4px 12px rgba(59, 130, 246, 0.3);
          
          &:hover {
            transform: translateY(-2px);
            box-shadow: 0 8px 20px rgba(59, 130, 246, 0.4);
          }
        }
        
        &.el-button--default {
          background: white;
          color: #374151;
          border: 1px solid #d1d5db;
          box-shadow: 0 2px 4px rgba(0, 0, 0, 0.05);
          
          &:hover {
            background: #f9fafb;
            border-color: #3b82f6;
            color: #3b82f6;
            transform: translateY(-1px);
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
          }
        }
      }
    }
  }
}

/* 动画效果 */
@keyframes blink {
  0%, 50% { opacity: 1; }
  51%, 100% { opacity: 0; }
}

@keyframes fadeInUp {
  from {
    opacity: 0;
    transform: translateY(30px);
  }
  to {
    opacity: 1;
    transform: translateY(0);
  }
}

/* 响应式优化 */
@media (max-width: 768px) {
  .hero-section {
    padding: 60px 16px 40px;
  }
  
  .features-section,
  .tech-section,
  .cta-section {
    padding: 60px 0;
  }
  
  .section-container {
    padding: 0 16px;
  }
  
  .hero-text .hero-title {
    font-size: 28px;
  }
  
  .cta-content .cta-title {
    font-size: 28px;
  }
}
</style>
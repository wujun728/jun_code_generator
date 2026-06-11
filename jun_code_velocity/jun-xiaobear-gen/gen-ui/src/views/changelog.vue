<template>
  <div class="changelog-page-v2">
    <!-- 整体白板容器 - 化零为整 -->
    <div class="main-container">
      <!-- 页面标题区 -->
      <div class="page-header">
        <div class="header-content">
          <h1 class="page-title">
            <el-icon><Document /></el-icon>
            更新日志
          </h1>
          <p class="page-description">记录系统的重要更新和功能改进</p>
        </div>
      </div>

      <!-- 精致统计卡片区 -->
      <div class="stats-section">
        <div class="stat-card">
          <div class="stat-icon version-icon">
            <el-icon><Star /></el-icon>
          </div>
          <div class="stat-content">
            <div class="stat-title">最新版本</div>
            <div class="stat-value">v2.1.0</div>
          </div>
        </div>
        
        <div class="stat-card">
          <div class="stat-icon update-icon">
            <el-icon><Refresh /></el-icon>
          </div>
          <div class="stat-content">
            <div class="stat-title">版本总数</div>
            <div class="stat-value">4</div>
          </div>
        </div>
        
        <div class="stat-card">
          <div class="stat-icon feature-icon">
            <el-icon><Check /></el-icon>
          </div>
          <div class="stat-content">
            <div class="stat-title">功能更新</div>
            <div class="stat-value">12</div>
          </div>
        </div>

        <!-- 全部展开/收起按钮 -->
        <div class="action-card">
          <el-button 
            type="primary" 
            :icon="allExpanded ? ArrowDown : ArrowRight"
            @click="toggleAllVersions"
            class="toggle-all-btn"
          >
            {{ allExpanded ? '全部收起' : '全部展开' }}
          </el-button>
        </div>
      </div>

      <!-- 更新日志内容区 -->
      <div class="content-section">
        <el-timeline>
        <!-- 版本 2.1.0 -->
        <el-timeline-item
          timestamp="2025-07-01"
          placement="top"
          type="primary"
          size="large"
          icon="Star"
        >
          <div class="version-card">
            <div class="version-header" @click="toggleVersion('v2.1.0')">
              <div class="version-info">
                <span class="version-number">v2.1.0</span>
                <span class="version-date">2025-07-01</span>
              </div>
              <div class="version-actions">
                <el-tag type="success" size="default" round>最新版本</el-tag>
                <el-icon class="expand-icon" :class="{ 'expanded': isVersionExpanded('v2.1.0') }">
                  <ArrowDown />
                </el-icon>
              </div>
            </div>
            
            <div class="update-content" v-show="isVersionExpanded('v2.1.0')">
              <h3>🎉 重大功能更新</h3>
              <ul class="feature-list">
                <li>
                  <el-icon class="feature-icon success"><Check /></el-icon>
                  <strong>智能代码工厂</strong> - 全新的一键生成完整项目功能
                </li>
                <li>
                  <el-icon class="feature-icon success"><Check /></el-icon>
                  <strong>Vue3升级</strong> - 前端框架升级到Vue3，性能大幅提升
                </li>
                <li>
                  <el-icon class="feature-icon success"><Check /></el-icon>
                  <strong>包名修复</strong> - 修复生成项目的包名和导入问题
                </li>
                <li>
                  <el-icon class="feature-icon success"><Check /></el-icon>
                  <strong>界面优化</strong> - 全面优化用户界面，提升操作体验
                </li>
              </ul>

              <h3>🔧 问题修复</h3>
              <ul class="fix-list">
                <li>修复数据表管理删除按钮缺失问题</li>
                <li>修复一键生成按钮无响应问题</li>
                <li>修复表设置页面字段显示问题</li>
                <li>优化重复请求限制策略</li>
                <li>修复MySQL 8.0连接兼容性问题</li>
              </ul>

              <h3>⚡ 性能优化</h3>
              <ul class="performance-list">
                <li>优化项目生成速度，提升50%效率</li>
                <li>改进前端加载性能</li>
                <li>优化数据库连接池配置</li>
                <li>减少不必要的网络请求</li>
              </ul>
            </div>
          </div>
        </el-timeline-item>

        <!-- 版本 2.0.5 -->
        <el-timeline-item
          timestamp="2025-06-15"
          placement="top"
          type="warning"
          icon="Tools"
        >
          <div class="version-card">
            <div class="version-header" @click="toggleVersion('v2.0.5')">
              <div class="version-info">
                <span class="version-number">v2.0.5</span>
                <span class="version-date">2025-06-15</span>
              </div>
              <div class="version-actions">
                <el-tag type="warning" size="default" round>稳定版本</el-tag>
                <el-icon class="expand-icon" :class="{ 'expanded': isVersionExpanded('v2.0.5') }">
                  <ArrowDown />
                </el-icon>
              </div>
            </div>
            
            <div class="update-content" v-show="isVersionExpanded('v2.0.5')">
              <h3>🔧 功能改进</h3>
              <ul class="feature-list">
                <li>
                  <el-icon class="feature-icon warning"><Tools /></el-icon>
                  增强代码生成模板的灵活性
                </li>
                <li>
                  <el-icon class="feature-icon warning"><Tools /></el-icon>
                  改进数据库表结构同步功能
                </li>
                <li>
                  <el-icon class="feature-icon warning"><Tools /></el-icon>
                  优化批量操作性能
                </li>
              </ul>

              <h3>🐛 Bug修复</h3>
              <ul class="fix-list">
                <li>修复特殊字符表名处理问题</li>
                <li>修复代码预览格式化问题</li>
                <li>修复导出功能在某些浏览器下的兼容性</li>
              </ul>
            </div>
          </div>
        </el-timeline-item>

        <!-- 版本 2.0.0 -->
        <el-timeline-item
          timestamp="2025-05-01"
          placement="top"
          type="info"
          icon="Trophy"
        >
          <div class="version-card">
            <div class="version-header" @click="toggleVersion('v2.0.0')">
              <div class="version-info">
                <span class="version-number">v2.0.0</span>
                <span class="version-date">2025-05-01</span>
              </div>
              <div class="version-actions">
                <el-tag type="info" size="default" round>里程碑版本</el-tag>
                <el-icon class="expand-icon" :class="{ 'expanded': isVersionExpanded('v2.0.0') }">
                  <ArrowDown />
                </el-icon>
              </div>
            </div>
            
            <div class="update-content" v-show="isVersionExpanded('v2.0.0')">
              <h3>🚀 重大版本发布</h3>
              <ul class="feature-list">
                <li>
                  <el-icon class="feature-icon info"><Trophy /></el-icon>
                  全新的代码生成引擎
                </li>
                <li>
                  <el-icon class="feature-icon info"><Trophy /></el-icon>
                  支持多种前端框架模板
                </li>
                <li>
                  <el-icon class="feature-icon info"><Trophy /></el-icon>
                  引入MyBatis Plus支持
                </li>
                <li>
                  <el-icon class="feature-icon info"><Trophy /></el-icon>
                  全新的用户界面设计
                </li>
              </ul>
            </div>
          </div>
        </el-timeline-item>

        <!-- 版本 1.5.0 -->
        <el-timeline-item
          timestamp="2025-03-15"
          placement="top"
          type="success"
          icon="CircleCheck"
        >
          <div class="version-card">
            <div class="version-header" @click="toggleVersion('v1.5.0')">
              <div class="version-info">
                <span class="version-number">v1.5.0</span>
                <span class="version-date">2025-03-15</span>
              </div>
              <div class="version-actions">
                <el-tag size="default" round>历史版本</el-tag>
                <el-icon class="expand-icon" :class="{ 'expanded': isVersionExpanded('v1.5.0') }">
                  <ArrowDown />
                </el-icon>
              </div>
            </div>
            
            <div class="update-content" v-show="isVersionExpanded('v1.5.0')">
              <h3>✨ 功能增强</h3>
              <ul class="feature-list">
                <li>
                  <el-icon class="feature-icon"><CircleCheck /></el-icon>
                  添加数据库文档导出功能
                </li>
                <li>
                  <el-icon class="feature-icon"><CircleCheck /></el-icon>
                  支持自定义代码模板
                </li>
                <li>
                  <el-icon class="feature-icon"><CircleCheck /></el-icon>
                  增加代码预览功能
                </li>
              </ul>
            </div>
          </div>
        </el-timeline-item>
        </el-timeline>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, computed } from 'vue'
import { 
  Document, 
  Star, 
  Check, 
  Tools, 
  Trophy, 
  CircleCheck,
  Refresh,
  ArrowDown,
  ArrowRight
} from '@element-plus/icons-vue'

// 控制每个版本内容的展开/收起状态
const expandedVersions = ref({
  'v2.1.0': true,  // 最新版本默认展开
  'v2.0.5': false,
  'v2.0.0': false,
  'v1.5.0': false
})

// 切换版本内容展开状态
const toggleVersion = (version) => {
  expandedVersions.value[version] = !expandedVersions.value[version]
}

// 检查版本是否展开
const isVersionExpanded = (version) => {
  return expandedVersions.value[version]
}

// 计算是否全部展开
const allExpanded = computed(() => {
  return Object.values(expandedVersions.value).every(expanded => expanded)
})

// 全部展开/收起
const toggleAllVersions = () => {
  const shouldExpand = !allExpanded.value
  Object.keys(expandedVersions.value).forEach(version => {
    expandedVersions.value[version] = shouldExpand
  })
}
</script>

<style lang="scss" scoped>
// V2 设计样式
.changelog-page-v2 {
  padding: 20px;
  background-color: #ffffff;
  min-height: 100vh;
}

// 主容器 - 整体白板效果
.main-container {
  background: white;
  border-radius: 12px;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.05);
  overflow: hidden;
}

// 页面头部
.page-header {
  padding: 20px 32px 16px;
  border-bottom: 1px solid #f0f2f5;
  
  .header-content {
    position: relative;
    z-index: 1;
    margin-bottom: 16px;
    
    .page-title {
      font-size: 24px;
      font-weight: 700;
      margin: 0 0 4px 0;
      display: flex;
      align-items: center;
      gap: 8px;
      color: #3b82f6;
      
      .el-icon {
        font-size: 26px;
        color: #3b82f6;
      }
    }
    
    .page-description {
      margin: 0;
      font-size: 14px;
      color: #6b7280;
      font-weight: 400;
    }
  }
}

// 精致统计卡片区
.stats-section {
  display: flex;
  gap: 12px;
  padding: 20px 32px 16px;
  background: #ffffff;
  position: relative;
  z-index: 1;

  .stat-card {
    display: flex;
    align-items: center;
    gap: 10px;
    background: #ffffff;
    border: 1px solid #e5e7eb;
    border-radius: 8px;
    padding: 12px 16px;
    flex: 1;
    transition: all 0.3s ease;
    box-shadow: 0 1px 3px rgba(0, 0, 0, 0.1);

    &:hover {
      background: #f9fafb;
      transform: translateY(-2px);
      box-shadow: 0 4px 12px rgba(0, 0, 0, 0.15);
    }

    .stat-icon {
      width: 40px;
      height: 40px;
      border-radius: 10px;
      display: flex;
      align-items: center;
      justify-content: center;
      background: #f3f4f6;
      
      .el-icon {
        font-size: 20px;
        color: #3b82f6;
      }

      &.version-icon {
        background: #dbeafe;
        
        .el-icon {
          color: #3b82f6;
        }
      }

      &.update-icon {
        background: #d1fae5;
        
        .el-icon {
          color: #10b981;
        }
      }

      &.feature-icon {
        background: #fef3c7;
        
        .el-icon {
          color: #f59e0b;
        }
      }
    }

    .stat-content {
      flex: 1;

      .stat-title {
        font-size: 13px;
        color: #6b7280;
        margin-bottom: 2px;
        font-weight: 500;
      }

      .stat-value {
        font-size: 20px;
        font-weight: 700;
        line-height: 1;
        color: #1f2937;
      }
    }
  }

  .action-card {
    display: flex;
    align-items: center;
    justify-content: center;
    padding: 20px;
    background: linear-gradient(135deg, #f0f9ff 0%, #e0f2fe 100%);
    border-radius: 12px;
    border: 1px solid #bae6fd;
    transition: all 0.3s ease;

    &:hover {
      transform: translateY(-2px);
      box-shadow: 0 8px 25px rgba(0, 0, 0, 0.1);
    }

    .toggle-all-btn {
      border-radius: 8px;
      font-weight: 500;
      padding: 12px 24px;
      height: auto;
    }
  }
}

// 内容区域
.content-section {
  padding: 32px;
}

// 版本卡片样式
.version-card {
  background: white;
  border-radius: 12px;
  border: 1px solid #e5e7eb;
  padding: 24px;
  margin-bottom: 16px;
  transition: all 0.3s ease;

  &:hover {
    transform: translateY(-2px);
    box-shadow: 0 8px 25px rgba(0, 0, 0, 0.1);
  }

  .version-header {
    display: flex;
    align-items: center;
    justify-content: space-between;
    margin-bottom: 20px;
    padding-bottom: 16px;
    border-bottom: 1px solid #f0f2f5;
    cursor: pointer;
    transition: all 0.3s ease;
    border-radius: 8px;
    padding: 16px;
    margin: -16px -16px 20px -16px;

    &:hover {
      background: #f8fafc;
    }

    .version-info {
      display: flex;
      flex-direction: column;
      gap: 4px;

      .version-number {
        font-size: 20px;
        font-weight: 700;
        color: #1f2937;
      }

      .version-date {
        font-size: 14px;
        color: #6b7280;
      }
    }

    .version-actions {
      display: flex;
      align-items: center;
      gap: 12px;

      .expand-icon {
        font-size: 16px;
        color: #6b7280;
        transition: transform 0.3s ease;

        &.expanded {
          transform: rotate(180deg);
        }
      }
    }
  }

  .update-content {
    transition: all 0.3s ease;
    overflow: hidden;

    h3 {
      color: #1f2937;
      font-size: 16px;
      font-weight: 600;
      margin: 16px 0 12px 0;
      display: flex;
      align-items: center;
      gap: 8px;
    }

    .feature-list,
    .fix-list,
    .performance-list {
      list-style: none;
      padding: 0;
      margin: 0 0 16px 0;

      li {
        display: flex;
        align-items: flex-start;
        gap: 8px;
        padding: 8px 0;
        border-bottom: 1px solid #f8f9fa;
        font-size: 14px;
        line-height: 1.5;

        &:last-child {
          border-bottom: none;
        }
      }
    }

    .feature-icon {
      margin-top: 2px;
      flex-shrink: 0;
      font-size: 16px;

      &.success {
        color: #10b981;
      }

      &.warning {
        color: #f59e0b;
      }

      &.info {
        color: #3b82f6;
      }
    }

    .fix-list li::before {
      content: "🐛";
      margin-right: 8px;
      flex-shrink: 0;
    }

    .performance-list li::before {
      content: "⚡";
      margin-right: 8px;
      flex-shrink: 0;
    }
  }
}

// 时间线样式优化
:deep(.el-timeline-item__timestamp) {
  font-weight: 600;
  color: #3b82f6;
  font-size: 14px;
}

:deep(.el-timeline-item__wrapper) {
  padding-left: 40px;
}

:deep(.el-timeline-item__tail) {
  border-left: 2px solid #e5e7eb;
}

:deep(.el-timeline-item__node) {
  width: 16px;
  height: 16px;
}

// 标签样式优化
:deep(.el-tag) {
  border-radius: 12px;
  font-weight: 500;
  
  &.el-tag--success {
    background: linear-gradient(135deg, #d1fae5, #a7f3d0);
    color: #059669;
    border: 1px solid #6ee7b7;
  }

  &.el-tag--warning {
    background: linear-gradient(135deg, #fef3c7, #fde68a);
    color: #d97706;
    border: 1px solid #fbbf24;
  }

  &.el-tag--info {
    background: linear-gradient(135deg, #dbeafe, #bfdbfe);
    color: #1d4ed8;
    border: 1px solid #93c5fd;
  }
}

// 响应式设计
@media (max-width: 1200px) {
  .stats-section {
    flex-direction: column;
    gap: 16px;

    .action-card {
      order: -1; // 将操作按钮放在最前面
    }
  }
}

@media (max-width: 768px) {
  .changelog-page-v2 {
    padding: 12px;
  }

  .main-container {
    border-radius: 8px;
  }

  .page-header {
    padding: 16px 20px;
  }

  .stats-section {
    padding: 16px 20px;
  }

  .content-section {
    padding: 20px;
  }

  .stat-card {
    padding: 16px;
    
    .stat-icon {
      width: 40px;
      height: 40px;
      font-size: 20px;
    }

    .stat-content {
      .stat-value {
        font-size: 24px;
      }
    }
  }

  .version-card {
    padding: 16px;

    .version-header {
      flex-direction: column;
      align-items: flex-start;
      gap: 12px;
    }
  }

  :deep(.el-timeline-item__wrapper) {
    padding-left: 20px;
  }
}
</style>

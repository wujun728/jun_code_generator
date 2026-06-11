import 'core-js/stable'
import { createApp } from 'vue'
import App from './App.vue'
import { router } from './router'
import store from './store'
import 'normalize.css/normalize.css'
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import zhCn from 'element-plus/es/locale/lang/zh-cn'
import * as ElementPlusIconsVue from '@element-plus/icons-vue'
import directive from './directive' // directive
import '@/styles/index.scss' // global css
import NProgress from 'nprogress' // progress bar
import 'nprogress/nprogress.css' // progress bar style
import SvgIcon from '@/icons' // svg icons
import ModernCard from '@/components/ModernCard' // modern card component
import ModernButton from '@/components/ModernButton' // modern button component
import StatCard from '@/components/StatCard' // stat card component  
import ModernTable from '@/components/ModernTable' // modern table component
import ThemeSwitcher from '@/components/ThemeSwitcher' // theme switcher component
import Icon from '@/components/Icon' // universal icon component
import IconHelper from '@/utils/icon-helper' // icon helper
import { vueErrorHandler } from '@/utils/error-handler' // error handler

const app = createApp(App)

// 设置Vue错误处理器
app.config.errorHandler = vueErrorHandler

// 全局未处理的 Promise 拒绝处理
window.addEventListener('unhandledrejection', event => {
  console.warn('Unhandled promise rejection:', event.reason)

  // 过滤掉 Sortable 相关的错误
  if (event.reason && typeof event.reason === 'string' &&
      (event.reason.includes('Sortable') || event.reason.includes('HTMLElement'))) {
    console.warn('Sortable related error (suppressed):', event.reason)
    event.preventDefault() // 阻止默认的错误处理
    return
  }

  // 其他错误正常处理
  if (window.errorHandler) {
    window.errorHandler.handleError({
      type: 'promise',
      level: 'medium',
      message: 'Unhandled Promise Rejection',
      stack: event.reason?.stack,
      timestamp: new Date().toISOString(),
      url: window.location.href,
      userAgent: navigator.userAgent
    })
  }
})

// 性能监控
if (process.env.NODE_ENV === 'development') {
  app.config.performance = true
}

// 注册 Element Plus 图标
for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
  app.component(key, component)
}

// 注册自定义组件
app.component('svg-icon', SvgIcon)
app.component('modern-card', ModernCard)
app.component('modern-button', ModernButton)
app.component('stat-card', StatCard)
app.component('modern-table', ModernTable)
app.component('theme-switcher', ThemeSwitcher)
app.component('icon', Icon)

// 使用插件
app.use(store)
app.use(ElementPlus, {
  size: 'default',
  locale: zhCn
})
app.use(directive)
app.use(router)

// 全局属性
app.config.globalProperties.$iconHelper = IconHelper
app.config.globalProperties.$$router = router

// NProgress配置
NProgress.configure({
  showSpinner: false,
  minimum: 0.2,
  speed: 500
})

// 增强路由守卫
const originalBeforeEach = router.beforeEach
router.beforeEach(async (to, from, next) => {
  // 启动进度条
  NProgress.start()

  // 设置页面背景
  if (to.meta.bodyBackground !== undefined) {
    document.querySelector('body').setAttribute('style', 'background: ' + to.meta.bodyBackground)
  } else {
    document.querySelector('body').removeAttribute('style')
  }

  // 统计页面访问（如果有百度统计）
  if (to.path && typeof _hmt !== 'undefined') {
    _hmt.push(['_trackPageview', '/#' + to.fullPath])
  }

  next()
})

const originalAfterEach = router.afterEach
router.afterEach((to, from) => {
  // 完成进度条
  NProgress.done()
})

// 初始化应用状态
store.dispatch('initializeApp')

// 挂载应用
app.mount('#app')

// 开发环境下的调试信息
if (process.env.NODE_ENV === 'development') {
  console.log('🚀 JavaXiaoBear代码生成器启动成功!')
  console.log('📊 当前环境:', process.env.NODE_ENV)
  console.log('🔧 Vue版本:', app.version)
}

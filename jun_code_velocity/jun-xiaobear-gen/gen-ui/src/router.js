import { createRouter, createWebHashHistory } from 'vue-router'
import { nextTick } from 'vue'
import Layout from '@/layout'
import store from '@/store'

const router = createRouter({
  history: createWebHashHistory(),
  scrollBehavior(to, from, savedPosition) {
    // 路由切换时的滚动行为
    if (savedPosition) {
      return savedPosition
    } else {
      return { top: 0 }
    }
  },
  routes: [
  {
    path: '/',
    component: Layout,
    redirect: '/index',
    children: [
      {
        path: 'index',
        component: () => import('@/views/index.vue'),
        name: 'Dashboard',
        meta: {
          title: '首页 | 小熊代码生成',
          icon: 'House',
          keepAlive: true,
          requiresAuth: false
        }
      }
    ]
  },
  {
    path: '/generate/',
    component: Layout,
    redirect: '/index',
    children: [
      {
        path: 'index',
        component: () => import('@/views/gen-index.vue'),
        name: 'Gen',
        meta: {
          title: '代码生成 | 小熊代码生成',
          icon: 'MagicStick',
          keepAlive: true,
          requiresAuth: false
        }
      }
    ]
  },
  {
    path: '/changelog/',
    component: Layout,
    redirect: '/changelog/index',
    children: [
      {
        path: 'index',
        component: () => import('@/views/changelog.vue'),
        name: 'Changelog',
        meta: {
          title: '更新日志 | 小熊代码生成',
          icon: 'Document',
          keepAlive: true,
          requiresAuth: false
        }
      }
    ]
  },
  {
    path: '/smart-factory/',
    component: Layout,
    redirect: '/smart-factory/index',
    children: [
      {
        path: 'index',
        component: () => import('@/views/smart-code-factory.vue'),
        name: 'SmartFactory',
        meta: {
          title: '智能代码工厂 | 小熊代码生成',
          icon: 'MagicStick',
          keepAlive: true,
          requiresAuth: false
        }
      }
    ]
  },
  {
    path: '/dataSource/',
    component: Layout,
    redirect: '/index',
    children: [
      {
        path: 'index',
        component: () => import('@/views/dataSource/index.vue'),
        name: 'DataSource',
        meta: {
          title: '数据源管理 | 小熊代码生成',
          icon: 'Database',
          keepAlive: true,
          requiresAuth: false
        }
      }
    ]
  },
  {
    path: '/export/',
    component: Layout,
    redirect: '/index',
    children: [
      {
        path: 'index',
        component: () => import('@/views/export/index.vue'),
        name: 'Export',
        meta: {
          title: '导出配置 | 小熊代码生成',
          icon: 'Download',
          keepAlive: false,
          requiresAuth: false
        }
      }
    ]
  },
  {
    path: '/gen-edit',
    component: Layout,
    redirect: '/index',
    children: [
      {
        path: '',
        component: () => import('@/views/editTable.vue'),
        name: 'GenEdit',
        meta: {
          title: '修改生成配置 | 小熊代码生成',
          icon: 'Edit',
          keepAlive: false,
          requiresAuth: false
        }
      }
    ]
  },
  {
    path: '/logs',
    component: Layout,
    children: [
      {
        path: '',
        component: () => import('@/views/logs/demo.vue'),
        name: 'Log',
        meta: {
          title: '现代化设计展示 | 小熊代码生成',
          icon: 'View',
          keepAlive: false,
          requiresAuth: false,
          hidden: process.env.NODE_ENV === 'production'
        }
      }
    ]
  }
]
})

// 全局前置守卫
router.beforeEach(async (to, from, next) => {
  // 显示加载状态
  store.dispatch('setLoading', true)

  // 设置页面标题
  if (to.meta.title) {
    document.title = to.meta.title
  }

  // 权限检查（如果需要）
  if (to.meta.requiresAuth) {
    // 这里可以添加权限验证逻辑
    // const hasAuth = await checkAuth()
    // if (!hasAuth) {
    //   next('/login')
    //   return
    // }
  }

  next()
})

// 全局后置守卫
router.afterEach((to, from) => {
  // 隐藏加载状态
  nextTick(() => {
    store.dispatch('setLoading', false)
  })

  // 记录路由访问
  if (process.env.NODE_ENV === 'development') {
    console.log(`Route changed: ${from.path} -> ${to.path}`)
  }

  // 添加到最近访问
  if (to.meta.title && !to.meta.hidden) {
    const routeInfo = {
      tableId: to.name,
      tableName: to.meta.title.split(' | ')[0], // 提取页面名称
      path: to.path,
      icon: to.meta.icon || 'Document'
    }
    store.dispatch('addRecentTable', routeInfo)
  }
})

// 路由错误处理
router.onError((error) => {
  console.error('Router error:', error)
  store.dispatch('setLoading', false)

  // 过滤掉 Sortable 相关的错误，这些错误通常是由于 DOM 元素未准备好导致的
  const isSortableError = error.message && error.message.includes('Sortable')
  const isElementError = error.message && error.message.includes('HTMLElement')

  if (isSortableError || isElementError) {
    console.warn('Sortable initialization error (non-critical):', error.message)
    return // 不上报这类错误
  }

  // 可以在这里添加错误上报
  if (window.errorHandler) {
    window.errorHandler.reportCustomError(
      `Route error: ${error.message}`,
      'medium',
      { route: router.currentRoute.value.path }
    )
  }
})

export { router }

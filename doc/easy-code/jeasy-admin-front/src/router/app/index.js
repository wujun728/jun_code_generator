import fileAttach from './fileAttach'
import log from './log'
import organization from './organization'
import resource from './resource'
import role from './role'
import user from './user'
import dictionary from './dictionary'
import code from './code'
import monitor from './monitor'
import Main from '@/components/main'

// 作为Main组件的子页面展示并且在左侧菜单显示的路由写在adminRouter里
export const adminRouter = [
  {
    path: '/userManagement',
    name: 'userManagement',
    meta: {
      icon: 'md-people',
      title: '用户管理'
    },
    component: Main,
    children: [
      ...user,
      ...role,
      ...resource,
      ...organization
    ]
  },
  {
    path: '/confManagement',
    name: 'confManagement',
    meta: {
      icon: 'md-settings',
      title: '基础数据'
    },
    component: Main,
    children: [
      ...dictionary,
      ...fileAttach
    ]
  },
  {
    path: '/codeManagement',
    name: 'codeManagement',
    meta: {
      icon: 'md-code-working',
      title: '代码平台'
    },
    component: Main,
    children: [
      ...code
    ]
  },
  {
    path: '/monitorManagement',
    name: 'monitorManagement',
    meta: {
      icon: 'md-eye',
      title: '日志监控'
    },
    component: Main,
    children: [
      ...log,
      ...monitor
    ]
  }
]

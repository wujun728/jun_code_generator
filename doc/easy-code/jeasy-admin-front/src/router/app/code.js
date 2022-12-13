export default [
  {
    path: '/api',
    name: 'api',
    meta: {
      icon: 'ios-document',
      title: '接口文档'
    },
    component: () => import('@/app/code/api')
  },
  {
    path: '/code',
    name: 'code',
    meta: {
      icon: 'ios-code-working',
      title: '代码生成'
    },
    component: () => import('@/app/code')
  }
]

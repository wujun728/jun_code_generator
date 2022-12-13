export default [
  {
    path: '/druid',
    name: 'druid',
    meta: {
      icon: 'ios-analytics',
      title: '数据监控'
    },
    component: () => import('@/app/monitor/druid')
  },
  {
    path: '/monitor',
    name: 'monitor',
    meta: {
      icon: 'ios-document',
      title: '接口监控'
    },
    component: () => import('@/app/monitor')
  }
]

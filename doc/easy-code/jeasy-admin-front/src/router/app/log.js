export default [
  {
    path: '/log',
    name: 'log',
    meta: {
      icon: 'ios-paper',
      title: '操作日志'
    },
    component: () => import('@/app/log')
  }
]

export default [
  {
    path: '/user',
    name: 'user',
    meta: {
      icon: 'ios-body',
      title: '人员管理'
    },
    component: () => import('@/app/user')
  }
]

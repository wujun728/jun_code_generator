export default [
  {
    path: '/role',
    name: 'role',
    meta: {
      icon: 'ios-person',
      title: '角色管理'
    },
    component: () => import('@/app/role')
  }
]

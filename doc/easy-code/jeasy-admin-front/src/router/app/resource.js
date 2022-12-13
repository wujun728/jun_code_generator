export default [
  {
    path: '/resource',
    name: 'resource',
    meta: {
      icon: 'ios-menu',
      title: '菜单资源'
    },
    component: () => import('@/app/resource')
  }
]

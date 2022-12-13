export default [
  {
    path: '/fileAttach',
    name: 'fileAttach',
    meta: {
      icon: 'ios-folder',
      title: '文件管理'
    },
    component: () => import('@/app/fileAttach')
  }
]

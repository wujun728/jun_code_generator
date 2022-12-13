export default [
  {
    path: '/dictionary',
    name: 'dictionary',
    meta: {
      icon: 'ios-book',
      title: '公共码表'
    },
    component: () => import('@/app/dictionary')
  }
]

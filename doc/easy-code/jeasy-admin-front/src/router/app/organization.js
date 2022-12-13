export default [
  {
    path: '/organization',
    name: 'organization',
    meta: {
      icon: 'ios-people',
      title: '组织机构'
    },
    component: () => import('@/app/organization')
  }
]

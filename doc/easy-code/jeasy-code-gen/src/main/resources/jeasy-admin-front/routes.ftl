export default [
  {
    path: '/${table.camelName}',
    name: '${table.camelName}',
    meta: {
      icon: 'ios-book',
      title: '${table.comment}'
    },
    component: () => import('@/app/${table.camelName}')
  }
]

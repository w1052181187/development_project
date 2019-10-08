export default[
  {
    path: '/system/users',
    name: '用户管理',
    meta: {
      title: '用户管理',
      active: '/system/users',
      permission: 'admin'
    },
    component: () => import(/* webpackChunkName: 'system' */ '@/pages/system/users/index')
  }
]

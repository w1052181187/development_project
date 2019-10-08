export default [
  {
    path: '/system/dataDict',
    name: '数据字典',
    meta: {
      title: '数据字典',
      active: '/system/dataDict',
      permission: 'admin'
    },
    component: () => import(/* webpackChunkName: 'system' */ '@/pages/system/dataDict/index')
  }
]

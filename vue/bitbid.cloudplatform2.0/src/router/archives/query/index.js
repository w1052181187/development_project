export default [
  {
    path: '/archives/query',
    name: '档案查询',
    meta: {
      title: '档案查询',
      active: '/archives/query',
      permission: '1070301'
    },
    component: () => import(/* webpackChunkName: 'archives' */ '@/pages/archives/query/index')
  },
  {
    path: '/archives/query/detail/:objectId',
    name: '查看档案详情',
    meta: {
      title: '查看档案详情',
      active: '/archives/query',
      permission: '1070301',
      type: 'query'
    },
    component: () => import(/* webpackChunkName: 'archives' */ '@/pages/archives/management/detail')
  }
]

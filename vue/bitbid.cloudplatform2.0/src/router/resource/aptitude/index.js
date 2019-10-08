export default [
  {
    path: '/resource/aptitude',
    name: '资质管理',
    meta: {
      title: '资质管理',
      active: '/resource/aptitude',
      permission: '1020201'
    },
    component: () => import(/* webpackChunkName: 'resource' */ '@/pages/resource/aptitude/index')
  },
  {
    path: '/resource/aptitude/add',
    name: '添加资质',
    meta: {
      title: '添加资质',
      active: '/resource/aptitude',
      permission: '1020202'
    },
    component: () => import(/* webpackChunkName: 'resource' */ '@/pages/resource/aptitude/add')
  },
  {
    path: '/resource/aptitude/update',
    name: '修改资质',
    meta: {
      title: '修改资质',
      active: '/resource/aptitude',
      permission: '1020202'
    },
    component: () => import(/* webpackChunkName: 'resource' */ '@/pages/resource/aptitude/update')
  },
  {
    path: '/resource/aptitude/detail/:objectId',
    name: '查看资质',
    meta: {
      title: '查看资质',
      active: '/resource/aptitude',
      permission: '1020201'
    },
    component: () => import(/* webpackChunkName: 'resource' */ '@/pages/resource/aptitude/detail')
  },
  {
    path: '/resource/aptitude/record/:code',
    name: '资质管理-合作记录',
    meta: {
      title: '资质管理-合作记录',
      active: '/resource/aptitude',
      permission: '1020202'
    },
    component: () => import(/* webpackChunkName: 'resource' */ '@/pages/resource/aptitude/record')
  }
]

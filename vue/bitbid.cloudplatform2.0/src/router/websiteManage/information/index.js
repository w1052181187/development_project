export default [
  {
    path: '/websiteManage/information',
    name: '综合信息',
    meta: {
      title: '综合信息',
      active: '/websiteManage/information'
    },
    component: () => import(/* webpackChunkName: 'websoteManage' */ '@/pages/websiteManage/information/index')
  },
  {
    path: '/websiteManage/information/add',
    name: '添加综合信息',
    meta: {
      title: '添加综合信息',
      active: '/websiteManage/information'
    },
    component: () => import(/* webpackChunkName: 'websoteManage' */ '@/pages/websiteManage/information/add')
  },
  {
    path: '/websiteManage/information/update',
    name: '修改综合信息',
    meta: {
      title: '修改综合信息',
      active: '/websiteManage/information'
    },
    component: () => import(/* webpackChunkName: 'websoteManage' */ '@/pages/websiteManage/information/update')
  },
  {
    path: '/websiteManage/information/detail/:objectId',
    name: '查看综合信息',
    meta: {
      title: '查看综合信息',
      active: '/websiteManage/information'
    },
    component: () => import(/* webpackChunkName: 'websoteManage' */ '@/pages/websiteManage/information/detail')
  }
]

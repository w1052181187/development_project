export default [
  {
    path: '/websiteManage/home/cooperator',
    name: '合作伙伴',
    meta: {
      title: '合作伙伴',
      active: '/websiteManage/home'
    },
    component: () => import(/* webpackChunkName: 'websoteManage' */ '@/pages/websiteManage/home/cooperator/index')
  },
  {
    path: '/websiteManage/home/cooperator/add',
    name: '合作伙伴-添加',
    meta: {
      title: '合作伙伴-添加',
      active: '/websiteManage/home'
    },
    component: () => import(/* webpackChunkName: 'websoteManage' */ '@/pages/websiteManage/home/cooperator/add')
  },
  {
    path: '/websiteManage/home/cooperator/update',
    name: '合作伙伴-修改',
    meta: {
      title: '合作伙伴-修改',
      active: '/websiteManage/home'
    },
    component: () => import(/* webpackChunkName: 'websoteManage' */ '@/pages/websiteManage/home/cooperator/update')
  }
]

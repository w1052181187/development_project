export default [
  {
    path: '/websiteManage/home/enterpriseImage',
    name: '企业图片',
    meta: {
      title: '企业图片',
      active: '/websiteManage/home'
    },
    component: () => import(/* webpackChunkName: 'websoteManage' */ '@/pages/websiteManage/home/enterpriseImage/index')
  },
  {
    path: '/websiteManage/home/enterpriseImage/add',
    name: '企业图片-添加',
    meta: {
      title: '企业图片-添加',
      active: '/websiteManage/home'
    },
    component: () => import(/* webpackChunkName: 'websoteManage' */ '@/pages/websiteManage/home/enterpriseImage/add')
  },
  {
    path: '/websiteManage/home/enterpriseImage/update',
    name: '企业图片-修改',
    meta: {
      title: '企业图片-修改',
      active: '/websiteManage/home'
    },
    component: () => import(/* webpackChunkName: 'websoteManage' */ '@/pages/websiteManage/home/enterpriseImage/update')
  }
]

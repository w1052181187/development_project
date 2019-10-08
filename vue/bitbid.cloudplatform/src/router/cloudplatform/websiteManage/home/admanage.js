export default [
  {
    path: '/websiteManage/home/adManage',
    name: 'websiteManage-adManage-list',
    meta: {
      title: '广告管理'
    },
    component: resolve => require(['@/pages/websiteManage/home/adManage/index'], resolve)
  },
  {
    path: '/websiteManage/home/adManage/add',
    name: 'websiteManage-adManage-add',
    meta: {
      title: '广告管理-添加'
    },
    component: resolve => require(['@/pages/websiteManage/home/adManage/add'], resolve)
  },
  {
    path: '/websiteManage/home/adManage/update',
    name: 'websiteManage-adManage-update',
    meta: {
      title: '广告管理-修改'
    },
    component: resolve => require(['@/pages/websiteManage/home/adManage/update'], resolve)
  }
]

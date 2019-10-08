export default [
  {
    path: '/websiteManage/home/cooperator',
    name: 'websiteManage-cooperator-list',
    meta: {
      title: '合作伙伴'
    },
    component: resolve => require(['@/pages/websiteManage/home/cooperator/index'], resolve)
  },
  {
    path: '/websiteManage/home/cooperator/add',
    name: 'websiteManage-cooperator-add',
    meta: {
      title: '合作伙伴-添加'
    },
    component: resolve => require(['@/pages/websiteManage/home/cooperator/add'], resolve)
  },
  {
    path: '/websiteManage/home/cooperator/update',
    name: 'websiteManage-cooperator-update',
    meta: {
      title: '合作伙伴-修改'
    },
    component: resolve => require(['@/pages/websiteManage/home/cooperator/update'], resolve)
  }
]

export default [
  {
    path: '/websiteManage/company',
    name: 'websiteManage-company-list',
    meta: {
      title: '企业网站-公司概况'
    },
    component: resolve => require(['@/pages/websiteManage/company/index'], resolve)
  },
  {
    path: '/websiteManage/company/update',
    name: 'websiteManage-company-update',
    meta: {
      title: '企业网站-修改'
    },
    component: resolve => require(['@/pages/websiteManage/company/update'], resolve)
  },
  {
    path: '/websiteManage/company/detail',
    name: 'websiteManage-company-detail',
    meta: {
      title: '企业网站-查看'
    },
    component: resolve => require(['@/pages/websiteManage/company/detail'], resolve)
  }
]

export default [
  {
    path: '/admin/enterprise/index',
    name: 'enterprise-list',
    meta: {
      title: '企业信息',
      active: '/admin/enterprise/index'
    },
    component: resolve => require(['@/pages/admin/enterprise/index'], resolve)
  }
]

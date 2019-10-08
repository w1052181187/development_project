export default [
  {
    path: '/system-management/land-transaction/contract/index',
    name: 'SystemContract-list',
    meta: {
      title: '合同备案',
      layout: 'SystemManagement'
    },
    component: resolve => require(['@/pages/system-management/land-transaction/contract/index'], resolve)
  },
  {
    path: '/system-management/land-transaction/contract/examine/:objectId',
    name: 'SystemContract-examine',
    meta: {
      title: '合同审批',
      layout: 'SystemManagement'
    },
    component: resolve => require(['@/pages/system-management/land-transaction/contract/examine'], resolve)
  },
  {
    path: '/system-management/land-transaction/contract/detail/:objectId',
    name: 'SystemContract-detail',
    meta: {
      title: '合同查看',
      layout: 'SystemManagement'
    },
    component: resolve => require(['@/pages/system-management/land-transaction/contract/detail'], resolve)
  }
]

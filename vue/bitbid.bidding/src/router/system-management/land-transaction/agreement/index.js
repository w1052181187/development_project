export default [
  {
    path: '/system-management/land-transaction/agreement/index',
    name: 'systemAgreement-list',
    meta: {
      title: '协议出让备案',
      layout: 'SystemManagement'
    },
    component: resolve => require(['@/pages/system-management/land-transaction/agreement/index'], resolve)
  },
  {
    path: '/system-management/land-transaction/agreement/examine/:objectId',
    name: 'systemAgreement-examine',
    meta: {
      title: '协议出让审批',
      layout: 'SystemManagement'
    },
    component: resolve => require(['@/pages/system-management/land-transaction/agreement/examine'], resolve)
  },
  {
    path: '/system-management/land-transaction/agreement/detail/:objectId',
    name: 'systemAgreement-detail',
    meta: {
      title: '协议出让查看',
      layout: 'SystemManagement'
    },
    component: resolve => require(['@/pages/system-management/land-transaction/agreement/detail'], resolve)
  }
]
